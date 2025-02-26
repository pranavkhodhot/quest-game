import React from "react";

interface StartScreenProps {
  setGameStarted: (started: boolean) => void;
  setScenario: (scenario: string) => void;
}

const StartScreen: React.FC<StartScreenProps> = ({ setGameStarted, setScenario }) => {
  return (
    <div className="start-screen">
      <h1>Welcome to the Quest Game</h1>
      <select id="scenerios" onChange={(e) => setScenario(e.target.value)}>
        <option value="Normal">Normal</option>
        <option value="A1_scenario">A1_scenario</option>
        <option value="2_winner">2_winner</option>
        <option value="1_winner">1_winner</option>
        <option value="0_winner">0_winner</option>
      </select>
      <button id="start-game-btn" onClick={() => setGameStarted(true)}>Start Game</button>
    </div>
  );
};

export default StartScreen;
