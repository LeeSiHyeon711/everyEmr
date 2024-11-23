import axios from "axios";

// MASTER 전용 기능 (권한 변경 기능)
export const changeUserRole = async (username, newRole) => {
    try {
        const token = localStorage.getItem("token"); // 저장된 JWT 토큰 가져오기
        const headers = {
            Authorization: `Bearer + ${token}`, // Authorization 헤더 추가
        };

        const response = await axios.put(
            `http://localhost:8080/api/master/change`,
            { username, newRole },
            { headers }
        );

        return response.data;
    } catch (error) {
        console.error("Failed to change user role:", error);
        throw error;
    }
};
