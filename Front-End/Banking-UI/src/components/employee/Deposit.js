import EmployeeHeader from "./EmployeeHeader";
import "./Deposit.css";
import ViewCustomerHeader from "./ViewCustomerHeader";
import deposit2 from "../images/deposit2.jpg";
import { useState, useEffect } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import SuccessAlert from "../SuccessAlert";
import { useLocation } from "react-router-dom";

const Deposit = () => {
  const navigate = useNavigate("");
  const location = useLocation();

  const accountNo = location.state && location.state.accountNo;

  const [enteredAmount, setAmount] = useState("");
  const [successAlert, setSuccessAlert] = useState(false);

  const amountHandler = (event) => {
    setAmount(event.target.value);
  };

  const depositSubmitHandler = (e) => {
    e.preventDefault();
    const isConfirmed = window.confirm(
      "Are you sure you want to deposit to this account?"
    );

    if (isConfirmed) {
      const depositData = {
        amount: enteredAmount,
      };
      console.log("Deposit successful");
      console.log(depositData);
      console.log("Account No", accountNo);
      setSuccessAlert(true);
    }
  };

  const closeAlert = () => {
    setSuccessAlert(false);
    navigate("/view-customer");
  };

  const depositCancelHandler = () => {
    setAmount("");
  };
  return (
    <div>
      <ViewCustomerHeader></ViewCustomerHeader>
      <div class="container mt-5">
        <div class="row">
          <div class="col-md-6">
            <img src={deposit2} alt="deposit" class="img-fluid" />
          </div>

          <div class="form-box container-form-deposit mt-5 col-md-4 ">
            <div class="container mt-3">
              <h2 className="heading-deposit">Deposit</h2>
              <form onSubmit={depositSubmitHandler}>
                <div class="form-group mb-4">
                  <label for="inputAccountNo">Account No</label>
                  <input
                    type="text"
                    class="form-control"
                    id="inputAccountNo"
                    value={accountNo}
                    disabled
                    style={{ color: "#999" }}
                  />
                </div>
                <div class="form-group mb-4">
                  <label for="inputB">Amount</label>
                  <input
                    type="number"
                    class="form-control"
                    id="inputB"
                    required
                    placeholder="Enter amount"
                    value={enteredAmount}
                    onChange={amountHandler}
                  />
                </div>

                <div class="form-group ">
                  <button type="submit" class="btn btn-primary button-space">
                    Deposit
                  </button>
                  {successAlert && (
                    <SuccessAlert
                      message={"Depositted Successfully!"}
                      onClose={closeAlert}
                    />
                  )}
                  <button
                    type="button"
                    class="btn btn-secondary"
                    onClick={depositCancelHandler}
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

export default Deposit;
