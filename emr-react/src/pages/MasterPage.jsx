import React, { useState } from "react";
import { changeUserRole } from "../api/master";
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";
import "./css/MasterPage.css";

const MasterPage = () => {
    const navigate = useNavigate();
    const token = localStorage.getItem("token");
    let decodedToken;

    if (token) {
        try {
            decodedToken = jwtDecode(token);
            console.log(decodedToken); // 디코딩된 토큰 확인
        } catch (error) {
            console.error("JWT 디코딩 실패:", error);
        }
    } else {
        alert("로그인이 필요합니다.");
        navigate("/login");
    }

    const [userName, setUserName] = useState("");
    const [newRole, setNewRole] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await changeUserRole(userName, newRole);
            alert(`권한 변경 성공!`);
        } catch (error) {
            console.error("Failed to change user role:", error);
            alert("MASTER 만 이용할 수 있습니다");
        }
    };

    return (
        <div className="master-page">
            <h1>마스터 권한 관리</h1>
            <form onSubmit={handleSubmit} className="master-form">
                <div className="form-group">
                    <label htmlFor="username">유저 이름</label>
                    <input
                        type="text"
                        id="username"
                        placeholder="변경할 유저 name"
                        value={userName}
                        onChange={(e) => setUserName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="newRole">새 권한</label>
                    <select
                        id="newRole"
                        value={newRole}
                        onChange={(e) => setNewRole(e.target.value)}
                        required
                    >
                        <option value="">-- 권한 선택 --</option>
                        <option value="USER">USER</option>
                        <option value="ADMIN">ADMIN</option>
                        <option value="MASTER">MASTER</option>
                    </select>
                </div>
                <button type="submit" className="submit-btn">
                    권한 변경
                </button>
            </form>
        </div>
    );
};

export default MasterPage;
