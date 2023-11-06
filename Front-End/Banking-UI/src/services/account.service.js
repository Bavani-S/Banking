import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8100/";
const CUSTOMER_URL = "http://localhost:8200/";

const getHealth = () => {
  return axios.get(API_URL + "auth/health");
};

const getAllUser = () => {
  return axios.get(API_URL + "auth/all", { headers: authHeader() });
};

export const getCustomer = (id) => {
  return axios.get(CUSTOMER_URL + "user/profile/" + id, { headers: authHeader() });
};

export default { getHealth, getAllUser };
