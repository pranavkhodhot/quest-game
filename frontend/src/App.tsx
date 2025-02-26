import React, { useState } from "react";
import StartScreen from "./components/StartScreen";
import GameScreen from "./components/GameScreen";
import './App.css'


const App: React.FC = () => {
  const [gameStarted, setGameStarted] = useState(false);
  const [scenario, setScenario] = useState("Normal");

  return (
    <div className="app-container">
      {!gameStarted ? (
        <StartScreen setGameStarted={setGameStarted} setScenario={setScenario} />
      ) : (
        <GameScreen scenario={scenario} />
      )}
    </div>
  );
};

export default App;
