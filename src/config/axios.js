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
  const city = await GET_CURRENT_LOCATION() || "Bengaluru";
  config.headers["X-LOCATION-ID"] = city;
  config.headers["Authorization"] = `Bearer ${sessionStorage.getItem("accessToken") || ""}`;
  return config;
});

axios.interceptors.response.use(
  res => res,
  async err => {
    const originalRequest = err.config;

    if (
      err.response?.status === 401 &&
      !originalRequest._retry &&
      !originalRequest.url.includes("/auth/refresh")
    ) {
      originalRequest._retry = true;

      const refresh = await axios.post(
        "/auth/refresh",
        {},
        { withCredentials: true }
      );

      setAccessToken(refresh.data.accessToken);

      return axios(originalRequest);
    }

    throw err;
  }
);

export default api;