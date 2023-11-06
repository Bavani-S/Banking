import "./CustomerDashboard.css";
import { Link, useNavigate } from "react-router-dom";
import profile2 from "../images/profile2.jpg";
import transactions from "../images/transactions.jpg";
import transfer from "../images/transfer.jpg";
import withdraw from "../images/withdraw.jpg";
///
import WelcomeHeader from "../WelcomeHeader";

const CustomerDashboard = () => {
  const navigate = useNavigate();
  const navigateToProfile = () => {
    navigate("/customer-profile");
  };
  const navigateToTransactions = () => {
    navigate("/customer-transactions");
  };

  const navigateToWithdraw = () => {
    navigate("/customer-withdraw");
  };
  const navigateToTransfer = () => {
    navigate("/customer-transfer");
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

      <div class="container mt-5">
        <div class="text-container">
          <div class="jumbotron text-center">
            <h6 class="display-4 text-primary">
              Empowering Your Financial Journey, One Click at a Time.
            </h6>
            <p>
              Explore your accounts, manage transactions, and take control of
              your financial future with confidence.
            </p>
          </div>
        </div>
      </div>

      <div class="box-container">
        <div className="row">
          <div className="col-md-3">
            <div className="each-box p-3 d-flex flex-column p-3 border-2 ">
              <img className="img-fluid" src={profile2} alt="profile" />
              <button
                className="btn btn-primary w-100 mt-auto"
                onClick={navigateToProfile}
              >
                Profile
              </button>
            </div>
          </div>

          <div class="col-md-3">
            <div class="each-box p-3 d-flex flex-column p-3 border-2">
              <img
                className="img-fluid"
                src={transactions}
                alt="transactions"
              />
              <button
                className="btn btn-primary w-100 mt-auto"
                onClick={navigateToTransactions}
              >
                Transactions
              </button>
            </div>
          </div>

          <div class="col-md-3">
            <div class="each-box p-3 d-flex flex-column p-3 border-2">
              <img className="img-fluid" src={withdraw} alt="withdraw" />
              <button
                className="btn btn-primary w-100 mt-auto"
                onClick={navigateToWithdraw}
              >
                Withdraw
              </button>
            </div>
          </div>

          <div class="col-md-3">
            <div class="each-box p-3 d-flex flex-column p-3 border-2">
              <img className="img-fluid" src={transfer} alt="transfer" />
              <button
                className="btn btn-primary w-100 mt-auto"
                onClick={navigateToTransfer}
              >
                Transfer
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CustomerDashboard;
