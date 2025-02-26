import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import Player from "./Player";
import Actions from "./Actions"; // Import the new component

interface GameScreenProps {
  scenario: string;
}

const apiBaseUrl = "http://localhost:8080";

const GameScreen: React.FC<GameScreenProps> = ({ scenario }) => {
  const [gameState, setGameState] = useState<any>(null);
  const [logMessages, setLogMessages] = useState<string[]>([]);
  const [drawnCard, setDrawnCard] = useState<string | null>(null);
  const [hasDrew, setHasDrew] = useState(false);
  const [currentPlayerHeader, setCurrentPlayerHeader] = useState<string>("");
  const hasStarted = useRef(false);
  const gameStateRef = useRef<any>(null);
  const waitingForInput = useRef<boolean>(false);
  const resolveUserInput = useRef<((value: string) => void) | null>(null);

  useEffect(() => {
    if (!hasStarted.current) {
      hasStarted.current = true;
      startGame();
    }
  }, []);

  const waitForUserClick = (): Promise<void> => {
    return new Promise((resolve) => {
      const button = document.getElementById("enter-input-btn") as HTMLButtonElement | null;
  
      if (!button) {
        return;
      }
  
      const handleClick = () => {
        button.removeEventListener("click", handleClick);
        resolve();
      };
  
      button.addEventListener("click", handleClick, { once: true });
    });
  };

  const getUserInput = (): Promise<string> => {
    return new Promise((resolve) => {
      waitingForInput.current = true;
      resolveUserInput.current = (value: string) => {
        waitingForInput.current = false;
        resolveUserInput.current = null;
        resolve(value);
      };
    });
  };

  const startGame = async () => {
    try {
      console.log("Starting game with scenario:", scenario);
      const response = await axios.post(
        `${apiBaseUrl}/start`,
        { scenerio: scenario },
        { headers: { "Content-Type": "application/json" } }
      );

      console.log("Server response:", response.data);
      if (response.status === 200) {
        setLogMessages((prevLogs) => ["Enter [DRAW] to draw an event card",response.data, ...prevLogs]);
        fetchGameState();
      }
    } catch (error: any) {
      console.error("Error starting game:", error?.response?.data || error.message);
    }
  };

  const fetchGameState = async () => {
    try {
      const response = await axios.get(`${apiBaseUrl}/state`);
      await setGameState(response.data);
      gameStateRef.current = response.data;
      console.log("useState: ",gameState)
      console.log("useRef: ",gameStateRef.current)
    } catch (error) {
      console.error("Error fetching game state:", error);
    }
  };

  const handleUserInput = async (input: string) => {
    if (waitingForInput.current && resolveUserInput.current) {
      resolveUserInput.current(input);
      return;
    }
  
    const commands: { [key: string]: () => Promise<void> } = {
      "draw": async () => {
        if (!hasDrew) {
          setHasDrew(true);
          setLogMessages([]);
          await displayDrawnCard();
          await handleDrawnCard();
          if (!await checkWinners()) {
            addLog("Enter [END] to end turn");
          }
        }
      },
      "end": async () => {
        if (hasDrew) {
          setHasDrew(false);
          setLogMessages([]);
          await getNextPlayerTurn();
        }
      }
    };
  
    if (commands[input.toLowerCase()]) {
      await commands[input.toLowerCase()]();
    } else {
      addLog(`Unknown command: "${input}"`);
    }
  };
  

  const addLog = (message: string) => {
    setLogMessages((prevLogs) => [message, ...prevLogs]);
  };

  const displayDrawnCard = async () => {
    try {
      const response = await axios.get(`${apiBaseUrl}/draw-event-card`);
      await fetchGameState()
      await setDrawnCard(response.data);
      addLog(`Drew card: ${response.data}`);
    } catch (error) {
      console.error("Error drawing card:", error);
    }
  };

  const handleDrawnCard = async () => {
    if (!gameState) return;
  
    try {
      await fetchGameState();
      console.log("Before drawing card:", gameState);
      console.log("Before drawing card useRef", gameStateRef.current)
      if (gameStateRef.current && gameStateRef.current.drawnCard.length === 2) {
        setLogMessages([]);
        let res = await findQuestSponsor();
        if (res === "A sponsor has been found!") {
          await buildQuest();
          await startQuest();
          await resolveQuest();
        } else {
          addLog(res);
        }
      } else {
        const response = await axios.get(`${apiBaseUrl}/handle-event-card`);
        addLog(response.data);
  
        await fetchGameState();
        await checkPlayerNeedTrim(gameStateRef.current.currentPlayer.id);


      }
    } catch (error) {
      console.error("Error handling drawn card:", error);
    }
  };
  

  const findQuestSponsor = async () => {
    if (!gameState) return "";
    let sponsorId = -1;
    for (let i = 0; i < 4; i++) {
      let askedPlayerId = ((gameState.currentPlayer.id - 1 + i) % gameState.players.length) + 1;
      const response = await axios.post(`${apiBaseUrl}/able-to-sponsor`, {
        playerId: gameStateRef.current.currentPlayer.id,
        stages: parseInt(gameStateRef.current.drawnCard.substr(gameStateRef.current.drawnCard.length - 1))
      });
      console.log(response)
      if (response.data) {
        console.log(`Would P${askedPlayerId} like to sponsor the quest? [yes/no]`)
        addLog(`Would P${askedPlayerId} like to sponsor the quest? [yes/no]`);
        const answer = await getUserInput(); // Replace with UI input later
        setLogMessages([])
        if (answer?.toLowerCase() === "yes") {
          sponsorId = askedPlayerId;
          break;
        } 
      }
    }

    const createResponse = await axios.post(`${apiBaseUrl}/create-quest`, {
      sponsorId,
      stages: parseInt(gameStateRef.current.drawnCard.substr(gameStateRef.current.drawnCard.length - 1))
    });

    return createResponse.data;
  };

  const buildQuest = async () => {
    await fetchGameState()
    if (!gameState) return;
    try {
      let count = 0;
      while (count < gameStateRef.current.currentQuest.stages) {
        setLogMessages([]);
        addLog(`Previous Stage Value: ${gameStateRef.current.currentQuest.stageValues.at(-1)}`);
        addLog(`Current Stage Value: ${gameStateRef.current.currentQuest.currStageValue}`);
        addLog(`Current Cards: ${gameStateRef.current.currentQuest.currStageCards.join(", ")}`);
        const input = await getUserInput()
        const response = await axios.post(`${apiBaseUrl}/add-card-to-stage`, { answer: input });
        addLog(response.data);
        if (response.data === "Stage has been built") count++;
        await fetchGameState();
      }
    } catch (error) {
      console.error("Error building quest:", error);
    }
  };

  const getParticipantsForQuest = async () => {
    if (!gameState) return;
  
    setLogMessages([]); // Clear previous logs
    let participantsSize = gameStateRef.current.currentQuest.participants.length;
  
    for (let i = 0; i < participantsSize; i++) {
      await fetchGameState(); // Ensure state is updated
      participantsSize = gameStateRef.current.currentQuest.participants.length;
  
      if (participantsSize === 0) {
        addLog("No participants remaining.");
        break; // Stop the loop if there are no more participants
      }
  
      const currentParticipant = gameStateRef.current.currentQuest.participants[i];
  
      if (!currentParticipant.declinedToParticipate) {
        await fetchGameState(); // Ensure we have the latest game state
  
        addLog(`Would P${currentParticipant.id} like to participate? [Yes/No]`);
        
        const input = await getUserInput();
  
        try {
          const response = await axios.post(`${apiBaseUrl}/ask-participant`, {
            answer: input.toLowerCase(),
            playerId: currentParticipant.id,
          });
  
          addLog(response.data); // Log response from the server
  
          if (input.toLowerCase() === "no") {
            i--; // Re-check the current participant
          }
        } catch (error) {
          console.error("Error in getParticipantsForQuest:", error);
        }
      }
    }
  
    setLogMessages([]); 
  };
  

  const startQuest = async () => {
    if (!gameStateRef.current) return;
  
    let currStage = gameStateRef.current.currentQuest.currentStage;
    console.log("currStage:", currStage);
    console.log("stages:", gameStateRef.current.currentQuest.stages);
  
    while (gameStateRef.current.currentQuest.currentStage <= gameStateRef.current.currentQuest.stages) {
      console.log("Starting a new quest stage...");
      
      await getParticipantsForQuest();
  
      if (gameStateRef.current.currentQuest.participants.length === 0) {
        addLog("No Participants Left");
        break;
      }
  
      await fetchGameState();
      setLogMessages((prev) => [`Stage ${gameStateRef.current.currentQuest.currentStage} begins!`, ...prev]);
  
      for (let i = 0; i < gameStateRef.current.currentQuest.participants.length; i++) {
        const currPlayer = gameStateRef.current.currentQuest.participants[i];
  
  
        try {
          const response = await axios.post(`${apiBaseUrl}/draw-one`, {
            playerId: currPlayer.id,
          });
  
          addLog(`P${currPlayer.id} drew a ${response.data}`);
          await fetchGameState();
          await checkPlayerNeedTrim(currPlayer.id);
          addLog(`P${currPlayer.id} will build an attack`);
  
          if (!(await attackStage(currPlayer.id))) {
            i--; 
          }
  
          await fetchGameState();
  
        } catch (error) {
          console.error("Error during quest stage:", error);
          addLog(`Error processing P${currPlayer.id}'s turn`);
        }
      }
  
      await axios.get(`${apiBaseUrl}/move-to-next-stage`);
      await fetchGameState();
    }
  };

  const resolveQuest = async () => {
    if (!gameStateRef.current) return;
  
    await fetchGameState();
  
    if (gameStateRef.current.currentQuest.successfulParticipants.length > 0) {
      addLog("Participant(s) have completed the quest");
    }
  
    try {
      await axios.get(`${apiBaseUrl}/finish-quest`);
      addLog("Sponsor will now regain Cards, Click the button to begin card redraw");
  
      await waitForUserClick();
      await fetchGameState();
      if (gameStateRef.current.currentQuest.sponsor) {
        await checkPlayerNeedTrim(gameStateRef.current.currentQuest.sponsor.id);
      }
    } catch (error) {
      console.error("Error resolving quest:", error);
      addLog("Error resolving the quest.");
    }
  };

  const attackStage = async (participantId: number): Promise<boolean> => {
    if (!gameStateRef.current) return false;
  
    while (true) {      
      addLog(`Current Attack Value: ${gameStateRef.current.currentQuest.currAttackValue}`);
      addLog(`Current Cards: ${gameStateRef.current.currentQuest.currAttackCards.join(", ")}`);
      addLog("Select the position of the card to include in your attack or type 'Quit' when finished");
  
      const input = await getUserInput();
      if (!input || input.toLowerCase() === "quit") break;
  
      try {
        console.log("input",input)
        const response = await axios.post(`${apiBaseUrl}/add-card-to-attack`, {
          participantId,
          answer: input,
        });
        console.log("Response",response)
        await fetchGameState();
        setLogMessages([])
        
        if (response.data === "Attack has been built") break;
      } catch (error) {
        console.error("Error adding card to attack:", error);
        addLog("Failed to add card to attack.");
      }
    }
  
    try {
      const response = await axios.post(`${apiBaseUrl}/resolve-attack`, {
        participantId,
      });
  
      addLog(response.data);
      addLog("Click the button to confirm.");
      await waitForUserClick();
  
      return response.data.includes("succeeded");
    } catch (error) {
      console.error("Error resolving attack:", error);
      addLog("Error resolving the attack.");
      return false;
    }
  };

  const checkPlayerNeedTrim = async (playerId: number) => {
    try {  

      if (!gameState || !gameState.players || gameState.players.length < playerId) {
        console.log("Invalid game state or player ID.");
        return;
      }
  
      let trimPhase = gameStateRef.current.players[playerId - 1].hand.length > 12;
      while (trimPhase) {
        addLog("Select a position to discard");
  
        const position = await getUserInput(); // Assuming getUserInput() returns a Promise<string>
  
        const response = await fetch(`${apiBaseUrl}/discard-card`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            playerId: gameState.players[playerId - 1].id,
            position: position, 
          }),
        });
  
        const result = await response.text();
        addLog(result);
  
        await fetchGameState(); // Ensure state updates
        trimPhase = gameStateRef.current.players[playerId - 1].hand.length > 12;
      } 
      setLogMessages([])
  
    } catch (error) {
      console.error("Error in checking player trim:", error);
    }
  };

  const checkWinners = async () => {
    try {
      const response = await axios.post(`${apiBaseUrl}/check-winners`);
      const winners = response.data;
  
      if (winners.length > 0) {
        const winnerNames = winners.map((winner: any) => `P${winner.id}`).join(", ");
        setCurrentPlayerHeader(`🏆 The winners are: ${winnerNames}! 🎉`);
        addLog(`🏆 The winners are: ${winnerNames}! 🎉`);
        return true;
      } else {
        addLog("No winners yet!");
        setCurrentPlayerHeader(`P${gameState.currentPlayer?.id || "?"} Turn`);
        return false;
      }
    } catch (error) {
      console.error("Error checking winners:", error);
    }
  };
  


  const getNextPlayerTurn = async () => {
    try {
      const response = await axios.get(`${apiBaseUrl}/next-player`);
      addLog(response.data);
      await fetchGameState();
      await checkPlayerNeedTrim(gameStateRef.current.currentPlayer.id);
      addLog("Enter [DRAW] to draw an event card")
    } catch (error) {
      console.error("Error getting next player turn:", error);
    }
  };

  return (
    <div className="game-screen">
    {gameState ? ( 
      <>
        <h1 id="current-player-header">{currentPlayerHeader || `P${gameState.currentPlayer?.id || "?"} Turn`}</h1>
        <div className="game-area">
          <Player player={gameState.players?.[0]} />
          <Player player={gameState.players?.[1]} />
          <Actions
            onUserInput={handleUserInput}
            logMessages={logMessages}
            drawnCard={drawnCard || ""}
            adventureCards={gameState.adventureDeckSize || 0}
            eventCards={gameState.eventDeckSize || 0}
            adventureDiscards={gameState.adventureDiscardPileSize || 0}
            eventDiscards={gameState.eventDiscardPileSize || 0}
          />
          <Player player={gameState.players?.[2]} />
          <Player player={gameState.players?.[3]} />
        </div>
      </>
    ) : (
      <h1>Loading game state...</h1>
    )}
  </div>
  );
};

export default GameScreen;