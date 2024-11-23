import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { login } from "../api/auth";
import 'bootstrap/dist/css/bootstrap.min.css';

const LoginPage = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const data = await login(username, password);
            localStorage.setItem("token", data.token); // 토큰 저장
            navigate("/main"); // 메인 페이지로 이동
        } catch (error) {
            console.error("로그인 실패", error);
            alert("로그인에 실패했습니다.");
        }
    };

    return (
        <div className="container vh-100 d-flex justify-content-center align-items-center">
            <div className="card p-4 shadow" style={{ maxWidth: "400px", width: "100%" }}>
                <h3 className="text-center mb-4">로그인</h3>
                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                        <label htmlFor="username" className="form-label">아이디</label>
                        <input
                            type="text"
                            id="username"
                            placeholder="아이디"
                            className="form-control"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="password" className="form-label">비밀번호</label>
                        <input
                            type="password"
                            id="password"
                            placeholder="비밀번호"
                            className="form-control"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>
                    <button type="submit" className="btn btn-primary w-100 mb-3">로그인</button>
                </form>
                <div className="text-center">
                    <span>계정이 없으신가요? </span>
                    <Link to="/signup" className="text-decoration-none">회원가입</Link>
                </div>
            </div>
        </div>
    );
};

export default LoginPage;
