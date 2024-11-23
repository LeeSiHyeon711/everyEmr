import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import LoginPage from "../pages/LoginPage";
import MainPage from "../pages/MainPage";
import NotFoundPage from "../pages/NotFoundPage";
import ProtectedRoute from "../components/ProtectedRoute";
import SignupPage from "../pages/SignupPage";
import MasterPage from "../pages/MasterPage";

const AppRouter = () => {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginPage />} />
                <Route path="/signup" element={<SignupPage />} />
                <Route path="/main" element={<ProtectedRoute><MainPage /></ProtectedRoute>}/>
                <Route path="*" element={<NotFoundPage />} />
                <Route path="/master" element={<MasterPage />} />
            </Routes>
        </Router>
    );
};

export default AppRouter;
