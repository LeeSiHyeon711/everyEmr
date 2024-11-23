import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import 'bootstrap/dist/css/bootstrap.min.css';

const SignupPage = () => {
    const [formData, setFormData] = useState({
        username: "",
        password: "",
        email: "",
    });
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post("/api/authentication/sign-up", formData);
            alert("회원가입이 완료되었습니다! 로그인해주세요.");
            navigate("/login"); // 회원가입 성공 후 로그인 페이지로 이동
        } catch (error) {
            console.error("회원가입 실패", error);
            alert("회원가입에 실패했습니다.");
        }
    };

    return (
        <div className="container vh-100 d-flex justify-content-center align-items-center">
            <div className="card p-4 shadow" style={{ maxWidth: "400px", width: "100%" }}>
                <h3 className="text-center mb-4">회원가입</h3>
                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                        <label htmlFor="username" className="form-label">아이디</label>
                        <input
                            type="text"
                            id="username"
                            name="username"
                            placeholder="아이디"
                            className="form-control"
                            value={formData.username}
                            onChange={handleChange}
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="password" className="form-label">비밀번호</label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            placeholder="비밀번호"
                            className="form-control"
                            value={formData.password}
                            onChange={handleChange}
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">이메일</label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            placeholder="example@example.com"
                            className="form-control"
                            value={formData.email}
                            onChange={handleChange}
                        />
                    </div>
                    <button type="submit" className="btn btn-primary w-100">회원가입</button>
                </form>
            </div>
        </div>
    );
};

export default SignupPage;
