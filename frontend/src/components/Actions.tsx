import React, { useState } from "react";

interface ActionsProps {
  onUserInput: (input: string) => void;
  logMessages: string[];
  drawnCard?: string;
  adventureCards: number;
  eventCards: number;
  adventureDiscards: number;
  eventDiscards: number;
}

const Actions: React.FC<ActionsProps> = ({
  onUserInput,
  logMessages,
  drawnCard,
  adventureCards,
  eventCards,
  adventureDiscards,
  eventDiscards,
}) => {
  const [inputValue, setInputValue] = useState("");

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(event.target.value);
  };

  const handleSubmit = () => {
    if (inputValue.trim() === "") return;
    onUserInput(inputValue.trim());
    setInputValue(""); 
  };

  return (
    <div id="actions">
      <h2>Actions</h2>


      <div className="input-bar-container">
        <input
          type="text"
          id="input-bar"
          className="input-bar"
          value={inputValue}
          onChange={handleInputChange}
          placeholder="Enter command..."
        />
        <button id="enter-input-btn" onClick={handleSubmit}>
          Enter
        </button>
      </div>


      <div>
        <h2>Drawn Card</h2>
        <div className="drawn-card">{drawnCard || "No card drawn yet"}</div>
      </div>

      {/* Game Log */}
      <div id="log">
        <h2>Game Log</h2>
        <div id="log-messages">
          {logMessages.map((msg, index) => (
            <div key={index}>{msg}</div>
          ))}
        </div>
      </div>


      <div>
        <h2>Game Stats</h2>
        <div className="stat-container" style={{ maxWidth: "80%" }}>
          <div>
            <h4>Adventure Cards</h4>
            <p id="adventure-cards" className="stat-header">{adventureCards}</p>
          </div>
          <div>
            <h4>Event Cards</h4>
            <p id="event-cards" className="stat-header">{eventCards}</p>
          </div>
          <div>
            <h4>Adventure Discard</h4>
            <p id="adventure-discards" className="stat-header">{adventureDiscards}</p>
          </div>
          <div>
            <h4>Event Discard</h4>
            <p id="event-discards" className="stat-header">{eventDiscards}</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Actions;
