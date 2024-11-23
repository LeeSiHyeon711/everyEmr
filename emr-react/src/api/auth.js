import axios from "../utils/axiosInstance";

// 로그인 요청
export const login = async (username, password) => {
    const response = await axios.post("/api/authentication/sign-in", {
        username,
        password,
    });
    return response.data;
};

// 회원가입 요청
export const register = async (userInfo) => {
    const response = await axios.post("/api/authentication/sign-up", userInfo);
    return response.data;
};

// 권한 변경 요청
export const updateRole = async (userId, role) => {
    const response = await axios.put(`/api/admin/update-role/${userId}`, { role });
    return response.data;
};
