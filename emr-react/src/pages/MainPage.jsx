import {Link} from "react-router-dom";

const MainPage = () => {
    return (
        <div>
            <h1>메인 페이지</h1>
            <p>여기는 로그인 후 접근 가능합니다.</p>
            <div className="text-center">
                <span> MASTER 관리 모드 </span>
                <Link to="/master" className="text-decoration-none">권한 변경 하기</Link>
            </div>
        </div>
    );
};

export default MainPage;
