const apiBaseUrl = "http://localhost:8080";
var gameState = null
var hasDrew = false;
var trimPhase = false
const timeoutSeconds = 0

async function startGame() {
    try {
        const response = await fetch(`${apiBaseUrl}/start`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                scenerio: document.getElementById("scenerios").value,
            }),
        });
        const result = await response.text();
        console.log(result);
        addLog(result)
        addLog("Enter [DRAW] to draw an event card")

    } catch (error) {
        console.error("Error in startGame:", error);
    }
}

async function update() {
    try {
        const response = await fetch(`${apiBaseUrl}/state`);
        gameState = await response.json();
        console.log(gameState)
    } catch (error) {
        console.error("Error updating game state:", error);
    }
}

async function populatePlayerDetails(id) {
    try {
        await update();
        const player1 = gameState.players[0];
        const player2 = gameState.players[1];
        const player3 = gameState.players[2];
        const player4 = gameState.players[3];

        const player1Cards = player1.hand
        const player2Cards = player2.hand
        const player3Cards = player3.hand
        const player4Cards = player4.hand

        const p1gridContainer = document.getElementById("p1-grid");
        const p2gridContainer = document.getElementById("p2-grid");
        const p3gridContainer = document.getElementById("p3-grid");
        const p4gridContainer = document.getElementById("p4-grid");

        p1gridContainer.innerHTML = "";
        p2gridContainer.innerHTML = "";
        p3gridContainer.innerHTML = "";
        p4gridContainer.innerHTML = "";

        player1Cards.forEach((card, index) => {
            const cardElement = document.createElement("div");
            cardElement.classList.add("card");
            cardElement.textContent = `${index + 1}. ${card}`;
            p1gridContainer.appendChild(cardElement);
        });

        player2Cards.forEach((card, index) => {
            const cardElement = document.createElement("div");
            cardElement.classList.add("card");
            cardElement.textContent = `${index + 1}. ${card}`;
            p2gridContainer.appendChild(cardElement);
        });

        player3Cards.forEach((card, index) => {
            const cardElement = document.createElement("div");
            cardElement.classList.add("card");
            cardElement.textContent = `${index + 1}. ${card}`;
            p3gridContainer.appendChild(cardElement);
        });

        player4Cards.forEach((card, index) => {
            const cardElement = document.createElement("div");
            cardElement.classList.add("card");
            cardElement.textContent = `${index + 1}. ${card}`;
            p4gridContainer.appendChild(cardElement);
        });

        document.getElementById("current-player-header").innerText = "P" + gameState.currentPlayer.id + "'s Turn";

        document.getElementById("p1-shields").textContent = player1.shields;
        document.getElementById("p2-shields").textContent = player2.shields;
        document.getElementById("p3-shields").textContent = player3.shields;
        document.getElementById("p4-shields").textContent = player4.shields;

        //document.getElementById("player-info-header").textContent = "P"+id+ " Details";

        document.getElementById("adventure-cards").textContent = gameState.adventureDeckSize;
        document.getElementById("event-cards").textContent = gameState.eventDeckSize;
        document.getElementById("adventure-discards").textContent = gameState.adventureDiscardPileSize;
        document.getElementById("event-discards").textContent = gameState.eventDiscardPileSize;

    } catch (error) {
        console.error("Error populating ANY player details:", error);
    }
}

async function getNextPlayerTurn() {
    try {
        const response = await fetch(`${apiBaseUrl}/next-player`);
        blankScreen();
        await populatePlayerDetails();
        await checkPlayerNeedTrim(gameState.currentPlayer.id);
        addLog("Enter [DRAW] to draw an event card")

    } catch (error) {
        console.error("Error getting next player turn:", error);
    }
}

async function displayDrawnCard() {
    try {
        await update();
        console.log("Before Drawing CArd",gameState.currentPlayer.hand);
        const response = await fetch(`${apiBaseUrl}/draw-event-card`);
        const result = await response.text();
        console.log(result);
        console.log("After Drawing CArd",gameState.currentPlayer.hand);
        document.getElementById("drawn-card").textContent = result;
        document.getElementById("drawn-card").classList.add("card");
        addLog("P" + gameState.currentPlayer.id + " drew " + result)
        await update();
        console.log("After Update in Drawn Card",gameState.currentPlayer.hand);

    } catch (error) {
        console.error("Error displaying drawn card:", error);
    }
}

async function handleDrawnCard() {
    console.log("Coming from handleDrawnCard: ",gameState)
    try {
        console.log("HandleDrawnCard. ",gameState.currentPlayer.hand)
        if(gameState.drawnCard.length == 2){
            document.getElementById("log-messages").textContent = "";
            let res = await findQuestSponsor();
            if(res == "A sponsor has been found!"){
                await buildQuest()
                await startQuest()
                await resolveQuest()
            } else {
                addLog(res)
            }
            blankScreen()
            populatePlayerDetails()

        } else {
            const response = await fetch(`${apiBaseUrl}/handle-event-card`);
            const result = await response.text();
            await populatePlayerDetails();
            addLog(result)
            await checkPlayerNeedTrim(gameState.currentPlayer.id);
        }
    } catch (error) {
        console.error("Error in Card Handeling:", error);
    }
}

async function findQuestSponsor() {
    console.log("findQuestSponsor Line 1. ",gameState.currentPlayer.hand)
    let sponsorId = -1;
    for(let i=0;i<4;i++){
        let askedPlayerId = ((gameState.currentPlayer.id - 1 + i) % gameState.players.length) + 1;
        if(askedPlayerId != gameState.currentPlayer.id){
            blankScreen();
            await populatePlayerDetails(askedPlayerId);
        }
        console.log("findQuestSponsor Line 9.",gameState.currentPlayer.hand)

        const response1 = await fetch(`${apiBaseUrl}/able-to-sponsor`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                playerId: gameState.currentPlayer.id,
                stages: parseInt(gameState.drawnCard.substr(gameState.drawnCard.length-1)),
            }),
        });
        const result1 = await response1.text();
        if(result1 == "true"){
            addLog("Would P" + askedPlayerId + " like to sponsor the quest [yes/no]")
            const answer = await getUserInput();
            if(answer.toLowerCase() == "yes"){
                sponsorId = askedPlayerId;
                console.log("After if findQuestSponsor",gameState.currentPlayer.hand)
                break;
            }
        }
        console.log("end findQuestSponsor",gameState.currentPlayer.hand)
    }
    console.log("before creating quest",gameState.currentPlayer.hand)

    const response2 = await fetch(`${apiBaseUrl}/create-quest`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            sponsorId: sponsorId,
            stages: parseInt(gameState.drawnCard.substr(gameState.drawnCard.length-1)),
        }),
    });
    const result2 = await response2.text();
    console.log("after creating quest",gameState.currentPlayer.hand)
    return result2
}

async function buildQuest() {
    try {
        await update()
        document.getElementById("log-messages").textContent = "";
        addLog("P" + gameState.currentQuest.sponsor.id + " Will Begin the Stage Building Process");
        var count = 0
        while(count < gameState.currentQuest.stages){
            addLog("Previous Stage Value: " + gameState.currentQuest.stageValues[gameState.currentQuest.stageValues.length-1]);
            addLog("Current Stage Value: " + gameState.currentQuest.currStageValue);
            addLog("Current Cards: " + cardBuildString(gameState.currentQuest.currStageCards));
            console.log(cardBuildString(gameState.currentQuest.currStageCards))
            addLog("Select the position of the card to include in Stage #" + (count+1) + " or enter Quit to stop building stage");
            const input = await getUserInput();
            const response = await fetch(`${apiBaseUrl}/add-card-to-stage`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    answer: input,
                }),
            });
            const result = await response.text();
            document.getElementById("log-messages").textContent = "";
            addLog(result)
            if(result == "Stage has been built"){
                count++;
            }
            await populatePlayerDetails(gameState.currentQuest.sponsor.id);
        }
        addLog("All stages are built");
        for(let i=1;i<gameState.currentQuest.stageValues.length;i++){
            addLog("Stage #" + i + " Value: " + gameState.currentQuest.stageValues[i]);
        }
        addLog("Click the button to confirm");
        await getUserInput();
        
    } catch (error) {
        console.error("Error in Building Quest:", error);
    }
}

async function startQuest() {
    let currStage = gameState.currentQuest.currentStage
    console.log("currStage: ",currStage)
    console.log("stages: ",gameState.currentQuest.stages)
    while(gameState.currentQuest.currentStage <= gameState.currentQuest.stages){
        console.log("first here")
        await getParticipantsForQuest();
        if(gameState.currentQuest.participants.length == 0){
            addLog("No Participants Left")
            break;
        }
        await update()
        document.getElementById("current-player-header").innerText = "Stage " + gameState.currentQuest.currentStage + " begins!";
        for(let i=0;i<gameState.currentQuest.participants.length;i++){
            let currPlayer = gameState.currentQuest.participants[i]
            await populatePlayerDetails(currPlayer.id)
            const response = await fetch(`${apiBaseUrl}/draw-one`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    playerId: currPlayer.id,
                }),
            });
            const result = await response.text();
            addLog("P"+currPlayer.id + " drew a "+ result)
            await populatePlayerDetails(currPlayer.id)
            await checkPlayerNeedTrim(currPlayer.id)
            addLog("P"+ currPlayer.id+" will build an attack")
            if(!await attackStage(currPlayer.id)){
                i--;
            }
            await update()
        }
        const response = await fetch(`${apiBaseUrl}/move-to-next-stage`);
        await update()
    }
}

async function resolveQuest() {
    await update()
    if(gameState.currentQuest.successfulParticipants.length > 0){
        addLog("Participant(s) have completed the quest")
    }
    const response = await fetch(`${apiBaseUrl}/finish-quest`);
    addLog("Sponsor will now regain Cards, Click the button to begin card redraw")
    await getUserInput()
    await update()
    await checkPlayerNeedTrim(gameState.currentQuest.sponsor.id)
}

async function checkWinners() {
    try {
        const response = await fetch(`${apiBaseUrl}/check-winners`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
        });

        const winners = await response.json();

        if (winners.length > 0) {
            const winnerNames = winners.map(winner => `P${winner.id}`).join(", ");
            document.getElementById("current-player-header").innerText = `The winners are: ${winnerNames}`;
            return true;
        } else {
            addLog("No winners yet!");
            return false;
        }
    } catch (error) {
        console.error("Error checking winners:", error);
    }
}

async function attackStage(participant) {
    while(true){
        await populatePlayerDetails(participant)
        addLog("Current Attack Value: " + gameState.currentQuest.currAttackValue);
        addLog("Current Cards: " + cardBuildString(gameState.currentQuest.currAttackCards));
        addLog("Select the position of the card to include in your attack or type 'Quit' when finished");    
        const input = await getUserInput();
        const response = await fetch(`${apiBaseUrl}/add-card-to-attack`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                participantId: participant,
                answer: input,
            }),
        });
        const result = await response.text();
        document.getElementById("log-messages").textContent = "";
        addLog(result)
        if(result == "Attack has been built"){
            break;
        }
    }
    const response = await fetch(`${apiBaseUrl}/resolve-attack`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            participantId: participant,
        }),
    });
    const result = await response.text();
    addLog(result)
    addLog("Click the button to confirm");
    await getUserInput();
    if(result.includes("succeeded")){
        return true;
    }
    return false;
}

async function getParticipantsForQuest() {
    document.getElementById("log-messages").textContent = "";
    let participantsSize = gameState.currentQuest.participants.length;
    for (let i=0;i<participantsSize;i++) {
        await update()
        participantsSize = gameState.currentQuest.participants.length;

        if (participantsSize == 0) {
            addLog("No participants remaining.");
            break; // Stop the loop if there are no more participants
        }
        if (!gameState.currentQuest.participants[i].declinedToParticipate) {
            //blankScreen();
            await populatePlayerDetails(gameState.currentQuest.participants[i].id);
            addLog("Would P" + gameState.currentQuest.participants[i].id + " like to participate? [Yes/No]");
            let input = await getUserInput();
            console.log(input + " ")
            const response = await fetch(`${apiBaseUrl}/ask-participant`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    answer: input,
                    playerId: gameState.currentQuest.participants[i].id,
                }),
            });
            const result = await response.text();
            addLog(result)
            if(input.toLowerCase() == "no"){
                i--;
            }
        }
    }
    document.getElementById("log-messages").textContent = "";
}

async function checkPlayerNeedTrim(player) {
    try {
        await populatePlayerDetails(player);
        trimPhase = gameState.players[player-1].hand.length > 12;
        while(trimPhase){
            addLog("P" + player + "'s hand is over 12 cards, you must discard a card until you're back to 12")
            addLog("Select a position to discard")
            const position = await getUserInput();
            const response = await fetch(`${apiBaseUrl}/discard-card`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    playerId: gameState.players[player-1].id,
                    position: position,
                }),
            });
            const result = await response.text();
            await populatePlayerDetails(player);
            addLog(result);
            trimPhase = gameState.players[player-1].hand.length > 12;
            document.getElementById("log-messages").textContent = "";
        }
        
    } catch (error) {
        console.error("Error in Checking Player Needs Trim:", error);
    }
}

function getUserInput() {
    return new Promise((resolve) => {
        const inputBtn = document.getElementById("enter-input-btn");
        const inputBar = document.getElementById("input-bar");

        inputBtn.addEventListener(
            "click",
            () => {
                const userInput = inputBar.value.trim();
                inputBar.value = "";
                resolve(userInput);
            },
            { once: true }
        );
    });
}

function addLog(message){
    let logEntry = document.createElement("p");
    logEntry.textContent = message;
    document.getElementById("log-messages").appendChild(logEntry);
}

function blankScreen(){
    document.getElementById("game-area").style.display = 'none';
    setTimeout(function(){document.getElementById("game-area").style.display = 'flex';},timeoutSeconds)
}

function cardBuildString(build) {
    let ret = "";
    for(let i=0;i<build.length;i++){
        ret += build[i] + " ";
    }
    return ret;
}

document.addEventListener("DOMContentLoaded", () => {
  const startScreen = document.getElementById("start-screen");
  const gameScreen = document.getElementById("game-screen");
  const gameArea = document.getElementById("game-area");
  const startGameBtn = document.getElementById("start-game-btn");
  const inputBtn = document.getElementById("enter-input-btn");
  const inputBar = document.getElementById("input-bar");

  startGameBtn.addEventListener("click", async () => {
    startScreen.style.display = "none";
    gameArea.style.display = "flex";
    await startGame();
    populatePlayerDetails();
  });

  inputBtn.addEventListener("click", async () => {
    if(!trimPhase){
        if (inputBar.value.toLowerCase() === "draw" && !hasDrew) {
            hasDrew = true;
            inputBar.value = "";
            await displayDrawnCard();
            await handleDrawnCard();
            if(!await checkWinners()){
                addLog("Enter [END] to end turn")
            }
        } else if (inputBar.value.toLowerCase() === "end" && hasDrew) {
            inputBar.value = "";
            document.getElementById("drawn-card").textContent = "";
            document.getElementById("drawn-card").classList.remove("card");
            console.log("Inside Event Handler", gameState);
            hasDrew = false;
            document.getElementById("log-messages").textContent = "";
            await getNextPlayerTurn();
        }
    }

});
});
