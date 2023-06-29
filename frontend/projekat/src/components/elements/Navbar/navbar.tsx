import "./navbar.css";
import { NavLink } from "react-router-dom";

interface NavLinkParams {
    isActive: boolean,
    isPending: boolean
}

export default function Navbar() {
    const getNavLinkStyle = ({ isActive, isPending }: NavLinkParams) =>
        `navbar-link ${isActive ? "active" : isPending ? "pending" : ""}`;

    return (
        <nav className="navbar">
        <ul className="navbar-list">
            <li className="navbar-item">
                <NavLink to="/law" className={getNavLinkStyle}>
                    Zakoni
                </NavLink>
            </li>
            <li className="navbar-item">
                <NavLink to="/verdicts" className={getNavLinkStyle}>
                    Presude
                </NavLink>
            </li>
            <li className="navbar-item">
                <NavLink to="/verdict-reasoning" className={getNavLinkStyle}>
                    Rasudjivanje
                </NavLink>
            </li>
        </ul>
        </nav>
    );
}