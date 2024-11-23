import axios from "axios";

const axiosInstance = axios.create({
    baseURL: "http://localhost:8080", // 백엔드 URL
    headers: {
        "Content-Type": "application/json",
    },
});

// 요청 시 토큰 자동으로 추가
axiosInstance.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export default axiosInstance;
