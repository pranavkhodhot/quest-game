import React from "react";

interface PlayerProps {
  player: {
    id: number;
    name: string;
    shields: number;
    hand: string[];
  };
}

const Player: React.FC<PlayerProps> = ({ player }) => {
  return (
    <div className="player-info">
      <h2 className="player-info-header">P{player.id}</h2>
      <div className="player-stats">
        <img src="/Shields.svg" alt="" />
        <h3 id={`p${player.id}-shields`}>{player.shields}</h3>
        <img src="/Cards.svg" alt="" />
        <div id={`p${player.id}-grid`} className="grid-container">
          {player.hand.map((card, index) => (
            <div key={index} className="card">{card}</div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Player;
