import "./Login.css";
import { useNavigate } from "react-router-dom";
import React, { useState } from "react";
import loginImage from "../components/images/login.jpeg";
import { useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Navigate } from "react-router-dom";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import { login } from "../actions/auth";
import WelcomeHeader from "./WelcomeHeader";

const required = (value) => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
      </div>
    );
  }
};

const Login = ({ onLogin }) => {
  const navigate = useNavigate();
  const { user: currentUser } = useSelector((state) => state.auth);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const form = useRef();
  const checkBtn = useRef();
  const [loading, setLoading] = useState(false);

  const { isLoggedIn } = useSelector((state) => state.auth);
  const { message } = useSelector((state) => state.message);
  const dispatch = useDispatch();

  const onChangeUsername = (e) => {
    const username = e.target.value;
    setUsername(username);
  };

  const onChangePassword = (e) => {
    const password = e.target.value;
    setPassword(password);
  };

  const handleLogin = (e) => {
    e.preventDefault();
    setLoading(true);
    form.current.validateAll();
    if (checkBtn.current.context._errors.length === 0) {
      dispatch(login(username, password))
        .then(() => {
          if (currentUser && currentUser.roles.includes("ROLE_EMPLOYEE")){
            console.log("kkk: ",currentUser.roles.includes("ROLE_EMPLOYEE"))
            navigate("/employee-dashboard");
          }else{
            console.log("kkk2: ",currentUser.roles.includes("ROLE_EMPLOYEE"))
            navigate("/customer-dashboard");
          }
          
          window.location.reload();
        })
        .catch(() => {
          setLoading(false);
        });
    } else {
      setLoading(false);
    }
  };
  if (isLoggedIn) {
    console.log("role: ",currentUser.roles)
    return currentUser.roles.includes("ROLE_EMPLOYEE") ? <Navigate to="/employee-dashboard" /> : <Navigate to="/customer-dashboard" />
  }

  return (
    <div>
      <WelcomeHeader></WelcomeHeader>
      <section className="vh-100">
        <div className="container py-5 h-100">
          <div className="row d-flex align-items-center justify-content-center h-100">
            <div className="col-md-8 col-lg-7 col-xl-6">
              <img className="img-fluid" src={loginImage} alt="login" />
            </div>
            <div className="login-box col-md-7 col-lg-5 col-xl-5 offset-xl-1">
                           <Form onSubmit={handleLogin} ref={form}>
          <div className="form-group">
            <label htmlFor="username">Username</label>
            <Input
              type="text"
              className="form-control"
              name="username"
              value={username}
              onChange={onChangeUsername}
              validations={[required]}
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">Password</label>
            <Input
              type="password"
              className="form-control"
              name="password"
              value={password}
              onChange={onChangePassword}
              validations={[required]}
            />
          </div>

          <div className="form-group">
            <button className="btn btn-primary btn-block" disabled={loading}>
              {loading && (
                <span className="spinner-border spinner-border-sm"></span>
              )}
              <span>Login</span>
            </button>
          </div>

          {message && (
            <div className="form-group">
              <div className="alert alert-danger" role="alert">
                {message}
              </div>
            </div>
          )}
          <CheckButton style={{ display: "none" }} ref={checkBtn} />
        </Form>

            </div>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Login;
