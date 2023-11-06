import CustomerHeader from "./CustomerHeader";
import "./Transfer.css";
import transfer2 from "../images/transfer2.jpg";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import SuccessAlert from "../SuccessAlert";

const Transfer = ({ transactionData, customerData }) => {
  const navigate = useNavigate("");

  const [enteredFromAccount, setFromAccount] = useState("savings");
  const [enteredTargetAccount, setTargetAccount] = useState("");
  const [enteredAmount, setAmount] = useState("");
  const [successAlert, setSuccessAlert] = useState(false);
  const [fromAccountError, setFromAccountError] = useState("");
  const [amountError, setAmountError] = useState("");

  const fromAccountHandler = (event) => {
    setFromAccount(event.target.value);
  };
  const targetAccountHandler = (event) => {
    setTargetAccount(event.target.value);
  };
  const amountHandler = (event) => {
    setAmount(event.target.value);
  };

  const transferSubmitHandler = (e) => {
    e.preventDefault();

    const isConfirmed = window.confirm("Are you sure you want to transfer?");

    if (isConfirmed) {
      const transferData = {
        fromAccount: enteredFromAccount,
        targetAccount: enteredTargetAccount,
        amount: enteredAmount,
      };

      const selectedFromAccountType = enteredFromAccount;
      const selectedTargetAccount = parseInt(enteredTargetAccount);
      const transferAmount = parseFloat(enteredAmount);

      const targetAccountExists = transactionData.some(
        (transaction) => transaction.targetAccountId === selectedTargetAccount
      );

      const fromCustomer = customerData.find(
        (c) => c.accountType === selectedFromAccountType
      );

      setFromAccountError("");
      setAmountError("");

      if (!fromCustomer) {
        setFromAccountError("From account type not found");
      }

      if (!targetAccountExists) {
        setFromAccountError("Target account does not exist.");
      }

      if (transferAmount > fromCustomer.currentBalance) {
        setAmountError("Transfer amount exceeds current balance");
      }

      if (
        targetAccountExists &&
        transferAmount <= fromCustomer.currentBalance
      ) {
        console.log("Transfer successful");
        console.log(transferData);
        setSuccessAlert(true);
      }
    }
  };

  const closeAlert = () => {
    setSuccessAlert(false);
    navigate("/customer-dashboard");
  };

  const transferCancelHandler = () => {
    setFromAccount("savings");
    setTargetAccount("");
    setAmount("");
    setFromAccountError("");
    setAmountError("");
  };

  return (
    <div>
      <CustomerHeader></CustomerHeader>
      <div class="container mt-5">
        <div class="row">
          <div class="col-md-6">
            <img src={transfer2} alt="transfer" class="img-fluid" />
          </div>

          <div class="form-box container-form2 col-md-4 mt-5">
            <div class="container mt-3">
              <h2 className="heading2">Transfer</h2>
              <form onSubmit={transferSubmitHandler}>
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

                {fromAccountError && (
                  <p className="text-danger">{fromAccountError}</p>
                )}
                <div class="form-group mb-4">
                  <label for="inputB">Target Account</label>
                  <input
                    type="number"
                    class="form-control"
                    id="inputB"
                    required
                    value={enteredTargetAccount}
                    placeholder="Enter target account"
                    onChange={targetAccountHandler}
                  />
                </div>
                {amountError && <p className="text-danger">{amountError}</p>}
                <div class="form-group mb-4">
                  <label for="inputB">Amount</label>
                  <input
                    type="number"
                    class="form-control"
                    id="inputB"
                    required
                    value={enteredAmount}
                    placeholder="Enter amount"
                    onChange={amountHandler}
                  />
                </div>
                <div class="form-group ">
                  <button type="submit" class="btn btn-primary button-space">
                    Transfer
                  </button>
                  {successAlert && (
                    <SuccessAlert
                      message={"Transfer successful!"}
                      onClose={closeAlert}
                    />
                  )}
                  <button
                    type="button"
                    class="btn btn-secondary"
                    onClick={transferCancelHandler}
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

export default Transfer;
