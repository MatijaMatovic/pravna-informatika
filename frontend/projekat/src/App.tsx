import "./App.css";
import Navbar from "./components/elements/Navbar/navbar";
import { Route, Routes, Outlet } from "react-router-dom";

export default function App() {
  return (
    <div className="w-100 h-100 d-flex flex-column">
      <Navbar />
      <div className="content-outlet overflow-hidden">
        <Outlet />
      </div>
    </div>
  );
}
