export type ClientToServerMessage =
  | { typeId: 1; payloadJson: { mapSize: string }}; // NEW_GAME_REQUEST
  | { typeId: 3; payloadJson: { joinCode: string; playerCount: number; mapSize: mapSize; }

export type ServerToClientMessage =
  | { typeId: 0; payloadJson: string } // CONNECT
  | { typeId: 2; payloadJson: { success: boolean; joinCode: string }} // NEW_GAME_RESPONSE 
