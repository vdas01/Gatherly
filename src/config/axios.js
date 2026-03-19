import axios from "axios";
import { GET_CURRENT_LOCATION } from "./config";

const api = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL || "/api/",
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

api.interceptors.response.use(
  res => res,
  async err => {
    const originalRequest = err.config;

    if (
      err.response?.status === 401 &&
      !originalRequest._retry &&
      !originalRequest.url.includes("/user/refresh")
    ) {
      originalRequest._retry = true;

      const refresh = await api.post(
        "/user/refresh",
        {},
        { withCredentials: true }
      );

      const newToken = refresh.data?.accessToken;
      if (newToken) {
        sessionStorage.setItem("accessToken", newToken);
      }

      originalRequest.headers["Authorization"] = `Bearer ${newToken}`;

      setAccessToken(newToken);

      return axios(originalRequest);
    }

    throw err;
  }
);

export default api;