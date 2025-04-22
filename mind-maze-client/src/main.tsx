import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import GameComponent from "./components/GameComponent";
import MainMenu from "./components/MainMenu";
import Lobby from "./components/Lobby";

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainMenu />} />
        <Route path="/lobby/:joinCode" element={<Lobby />} />
        <Route path="/game" element={<GameComponent />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);
