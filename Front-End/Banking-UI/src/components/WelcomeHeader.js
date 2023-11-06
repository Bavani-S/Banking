import "./WelcomeHeader.css";
import { Link, useNavigate } from "react-router-dom";
import logo from "../components/images/logo.png";
import React, { useState, useEffect, useCallback } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Routes, Route, useLocation } from "react-router-dom";
import { logout } from "../actions/auth";
import { clearMessage } from "../actions/message";

const WelcomeHeader = () => {
  const navigate = useNavigate();
const navigateToLogin = () => {
  navigate("/login");
};
const { user: currentUser } = useSelector((state) => state.auth);
const dispatch = useDispatch();

let location = useLocation();

useEffect(() => {
  if (["/login", "/register"].includes(location.pathname)) {
    dispatch(clearMessage()); // clear message when changing location
  }
}, [dispatch, location]);

const logOut = useCallback(() => {
  dispatch(logout());
  navigate("/login")
}, [dispatch]);

const dashboard = currentUser ? (currentUser.roles.includes("ROLE_EMPLOYEE") ? "/employee-dashboard" : "/customer-dashboard") : "";

  return (
    <div>
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
        {/* <div className="container"> */} 
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav ml-auto">
              <li className="nav-item active">
              <a className="navbar-brand" href="/">
          <img src={logo} className="logo"/>
          </a>
            </li>
            
              <li className="nav-item active dash">
              {currentUser ?
                (<Link className="login-navigate dash" to={dashboard}>
                  Dashboard
                </Link>):
                (<Link className="login-navigate dash" to="/offers">
                Loan Offers
              </Link>)}
              </li>
            </ul>
            <ul className="navbar-nav ms-auto">
               
            </ul>
          </div>
        {/* </div> */}
        {currentUser ? (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/profile"} className="nav-link">
                <img src="https://cdn-icons-png.flaticon.com/128/3135/3135715.png" className="profile-img"/>
                {currentUser.userName}
              </Link>
            </li>
            <li className="nav-item">
             
              <button
                    className="btn btn-primary btn-lg"
                    onClick={logOut}
                  >
                    Log Out
                  </button>
            </li>
          </div>
        ) : (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
             
                <button
                    className="btn btn-primary btn-lg"
                    onClick={navigateToLogin}
                  >
                    Login
                  </button>
            </li>
          </div>
        )}
      </nav>
    </div>
  );
};

export default WelcomeHeader;
