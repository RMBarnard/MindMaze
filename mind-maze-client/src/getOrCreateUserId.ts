export function getOrCreateUserId(): number {
  const key = "mindmaze_user_id";
  const existing = localStorage.getItem(key);
  if (existing) {
    return parseInt(existing, 10);
  }

  const newId = Math.floor(Math.random() * 1_000_000_000);
  localStorage.setItem(key, newId.toString());
  return newId;
}

