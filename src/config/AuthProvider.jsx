import { createContext, useState } from "react";

export const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [username, setUserName] = useState(() => {
    return sessionStorage.getItem("username") || null;
  });

  return (
    <AuthContext.Provider value={{ username, setUserName }}>
      {children}
    </AuthContext.Provider>
  );
}
