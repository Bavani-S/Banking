import CustomerHeader from "./CustomerHeader";
import "./Withdraw.css";
import withdrawatm from "../images/withdrawatm.jpg";
import { useState } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import SuccessAlert from "../SuccessAlert";

const Withdraw = ({ customerData }) => {
  const navigate = useNavigate("");

  const [enteredFromAccount, setFromAccount] = useState("savings");
  const [enteredAmount, setAmount] = useState("");
  const [successAlert, setSuccessAlert] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const fromAccountHandler = (event) => {
    setFromAccount(event.target.value);
  };
  const amountHandler = (event) => {
    setAmount(event.target.value);
  };

  console.log("custome data", customerData);

  const withdrawSubmitHandler = (e) => {
    e.preventDefault();

    const isConfirmed = window.confirm("Are you sure you want to withdraw?");

    if (isConfirmed) {
      const selectedAccountType = enteredFromAccount;
      const withdrawalAmount = enteredAmount;

      const customer = customerData.find(
        (c) => c.accountType === selectedAccountType
      );

      if (!customer) {
        console.log("Account type not found");
        return;
      }

      const currentBalance = customer.currentBalance;

      if (withdrawalAmount > currentBalance) {
        setErrorMessage("Withdrawal amount exceeds current balance");
        return;
      }

      const withdrawData = {
        fromAccount: enteredFromAccount,
        amount: enteredAmount,
      };

      setErrorMessage("");

      console.log("Withdraw successful");
      console.log(withdrawData);

      setSuccessAlert(true);
    }
  };

  const closeAlert = () => {
    setSuccessAlert(false);
    navigate("/customer-dashboard");
  };

  const withdrawCancelHandler = () => {
    setFromAccount("savings");
    setAmount("");
    setErrorMessage("");
  };
  return (
    <div>
      <CustomerHeader></CustomerHeader>
      <div class="container mt-5">
        <div class="row">
          <div class="col-md-6">
            <img src={withdrawatm} alt="atm" class="img-fluid" />
          </div>

          <div class="form-box container-form-withdraw col-md-4 mt-5">
            <div class="container mt-3 ">
              <h2 className="heading-withdraw">Withdraw</h2>
              <form onSubmit={withdrawSubmitHandler}>
                <div class=" form-group mb-4">
                  <label for="selectA">From Account</label>

                  <div class="input-group">
                    <select
                      class="form-select"
                      id="accountType"
                      name="accountType"
                      value={enteredFromAccount}
                      onChange={fromAccountHandler}
                    >
                      <option value="savings">savings</option>
                      <option value="current">current</option>
                    </select>
                  </div>
                </div>
                {errorMessage && <p className="text-danger">{errorMessage}</p>}
                <div class="form-group mb-4">
                  <label for="inputB">Amount</label>
                  <input
                    type="number"
                    class="form-control"
                    id="inputB"
                    placeholder="Enter amount"
                    required
                    value={enteredAmount}
                    onChange={amountHandler}
                  />
                </div>

                <div class="form-group ">
                  <button type="submit" class="btn btn-primary button-space">
                    Withdraw
                  </button>
                  {successAlert && (
                    <SuccessAlert
                      message={"Withdrawal successful!"}
                      onClose={closeAlert}
                    />
                  )}
                  <button
                    type="button"
                    class="btn btn-secondary"
                    onClick={withdrawCancelHandler}
                  >
                    Cancel
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Withdraw;
