import axios from "axios";

const API_URL = "http://localhost:8100/";

const login = (userName, password) => {
  return axios
    .post(API_URL + "auth/login", {
      userName,
      password,
    })
    .then((response) => {
      if (response.data.token) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }
      return response.data;
    });
};

const logout = () => {
  localStorage.removeItem("user");
};

export default { login, logout };
