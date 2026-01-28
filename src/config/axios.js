import axios from "axios";
import { GET_CURRENT_LOCATION } from "./config";

const api = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api/",
    timeout: 10000,
    headers: {
    "Content-Type": "application/json"
    }
});

// 🔥 Add async header here
api.interceptors.request.use(async (config) => {
  const city = await GET_CURRENT_LOCATION();
  config.headers["X-LOCATION-ID"] = city;
  return config;
});

export default api;