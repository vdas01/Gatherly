// OAuthSuccess.jsx
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function OAuthSuccess() {
  const navigate = useNavigate();

  useEffect(() => {
    const token = new URLSearchParams(window.location.search).get("token");

    if (token) {
      localStorage.setItem("token", token);
      navigate("/home"); // redirect after login
    } else {
      navigate("/login");
    }
  }, []);

  return <div>Logging you in...</div>;
}

export default OAuthSuccess;