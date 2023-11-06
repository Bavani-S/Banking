import "./EmployeeDashboard.css";
import { Link, useNavigate } from "react-router-dom";
import createCustomer from "../images/createCustomer.jpg";
import createAccount from "../images/createAccount.jpg";
import viewCustomer from "../images/viewCustomer.jpg";
///
import WelcomeHeader from "../WelcomeHeader";

const EmployeeDashboard = () => {
  const navigate = useNavigate();
  const navigateToCreateCustomer = () => {
    navigate("/create-customer");
  };
  const navigateToCreateAccount = () => {
    navigate("/create-account");
  };
  const navigateToViewCustomer = () => {
    navigate("/view-customer");
  };
  return (
    <div>
      <WelcomeHeader></WelcomeHeader>
      {/* <nav className="navbar navbar-expand-lg navbar-dark ">
        <div className="container">
          <a className="navbar-brand" href="#">
            ABCD BANK
          </a>

          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav ms-auto">
              <li className="nav-item active">
                <Link className="login-navigate" to="/logout">
                  Logout
                </Link>
              </li>
            </ul>
          </div>
        </div>
      </nav> */}

      <div className="container text-center emp-dash-cont">
        <div className="row">
          <div className="col-sm-4">
            <div className="border custom-border border-3 text-white p-3">
              <img
                className="img-fluid"
                src={createCustomer}
                alt="create customer"
              />
              <button
                className="btn btn-primary w-100 mt-auto"
                onClick={navigateToCreateCustomer}
              >
                Create Customer
              </button>
            </div>
          </div>
          {/* <div className="col-sm-4">
            <div className="border custom-border border-3 text-white p-3">
              <img
                className="img-fluid"
                src={createAccount}
                alt="create account"
              />
              <button
                className="btn btn-primary w-100 mt-auto mt-5"
                onClick={navigateToCreateAccount}
              >
                Create Account
              </button>
            </div>
          </div> */}
          <div className="col-sm-4">
            <div className="border custom-border border-3 text-white p-3">
              <img
                className="img-fluid"
                src={viewCustomer}
                alt="view customer"
              />
              <button
                className="btn btn-primary w-100 mt-auto"
                onClick={navigateToViewCustomer}
              >
                View Customer
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EmployeeDashboard;
