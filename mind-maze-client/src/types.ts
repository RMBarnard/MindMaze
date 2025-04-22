export type ClientToServerMessage =
  | { typeId: 1; payloadJson: { mapSize: string }}; // NEW_GAME_REQUEST

export type ServerToClientMessage =
  | { typeId: 0; payloadJson: string }
