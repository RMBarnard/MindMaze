import React from 'react'
import ReactDOM from 'react-dom/client'
import GameComponent from './components/GameComponent'

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <GameComponent userId={1} />
  </React.StrictMode>
)
