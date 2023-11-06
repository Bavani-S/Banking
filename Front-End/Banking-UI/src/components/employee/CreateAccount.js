import EmployeeHeader from "./EmployeeHeader";
import "./CreateAccount.css";
import createAccount2 from "../images/createAccount2.jpg";
import { useState } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import SuccessAlert from "../SuccessAlert";

const CreateAccount = () => {
  const navigate = useNavigate("");

  const [enteredCustomerId, setCustomerId] = useState("");
  const [enteredOpeningAmount, setOpeningAmount] = useState("");
  const [enteredOpeningDate, setOpeningDate] = useState("");
  const [enteredAccountType, setAccountType] = useState("");
  const [enteredOwnerName, setOwnerName] = useState("");
  const [successAlert, setSuccessAlert] = useState(false);

  const customerIdHandler = (event) => {
    setCustomerId(event.target.value);
  };
  const openingAmountHandler = (event) => {
    setOpeningAmount(event.target.value);
  };
  const openingDateHandler = (event) => {
    setOpeningDate(event.target.value);
  };
  const accountTypeHandler = (event) => {
    setAccountType(event.target.value);
  };
  const ownerNameHandler = (event) => {
    setOwnerName(event.target.value);
  };

  const createAccountSubmitHandler = (e) => {
    e.preventDefault();
    const accountDate = {
      customerId: enteredCustomerId,
      openingAmount: enteredOpeningAmount,
      openingDate: enteredOpeningDate,
      accountType: enteredAccountType,
      ownerName: enteredOwnerName,
    };
    console.log("Account created successfully");
    console.log(accountDate);

    setSuccessAlert(true);
  };

  const closeAlert = () => {
    setSuccessAlert(false);
    navigate("/employee-dashboard");
  };

  const createAccountCancelHandler = () => {
    setCustomerId("");
    setOpeningAmount("");
    setOpeningDate("");
    setAccountType("");
    setOwnerName("");
  };
  return (
    <div>
      <EmployeeHeader></EmployeeHeader>
      <div class="container mt-5">
        <div class="row">
          <div class="col-md-5 create-account-form form-bg2 mt-5">
            <div>
              <div>
                <form onSubmit={createAccountSubmitHandler}>
                  <h2 class="text-center mb-4 mt-5 create-acc-head">
                    Create Account
                  </h2>
                  <div class="form-group form-grp">
                    <label for="customer-id">Customer ID</label>
                    <input
                      type="text"
                      class="form-control"
                      id="customer-id"
                      placeholder="Enter Customer ID"
                      required
                      value={enteredCustomerId}
                      onChange={customerIdHandler}
                    />
                  </div>
                  <div class="form-group form-grp mt-3">
                    <label for="opening-amount">Opening Amount</label>
                    <input
                      type="number"
                      class="form-control"
                      id="opening-amount"
                      placeholder="Enter Opening Amount"
                      required
                      value={enteredOpeningAmount}
                      onChange={openingAmountHandler}
                    />
                  </div>

                  <div class="form-group form-grp mt-3">
                    <div class="row">
                      <div class="col-md-6">
                        <label for="opening-date">Opening Date</label>
                        <input
                          type="date"
                          class="form-control"
                          id="opening-date"
                          required
                          value={enteredOpeningDate}
                          onChange={openingDateHandler}
                        />
                      </div>
                      <div class="col-md-6">
                        <label for="select-box">Account Type</label>
                        <select
                          class="form-control"
                          id="select-box"
                          required
                          value={enteredAccountType}
                          onChange={accountTypeHandler}
                        >
                          <option value="">Select Account Type</option>
                          <option value="current">Current</option>
                          <option value="savings">Savings</option>
                        </select>
                      </div>
                    </div>
                  </div>
                  <div class="form-group form-grp mt-3">
                    <label for="owner-name">Owner Name</label>
                    <input
                      type="text"
                      class="form-control"
                      id="owner-name"
                      placeholder="Enter Owner Name"
                      required
                      value={enteredOwnerName}
                      onChange={ownerNameHandler}
                    />
                  </div>
                  <button type="submit" class="btn btn-primary form-grp mt-4 ">
                    Create
                  </button>
                  {successAlert && (
                    <SuccessAlert
                      message={"Account created successfully!"}
                      onClose={closeAlert}
                    />
                  )}
                  <button
                    type="button"
                    class="btn btn-secondary btn2 form-grp mt-4 "
                    onClick={createAccountCancelHandler}
                  >
                    Cancel
                  </button>
                </form>
              </div>
            </div>
          </div>
          <div class="col-md-6 createCustomer-img-container2 ">
            <img src={createAccount2} alt="create customer" class="img-fluid" />
          </div>
        </div>
      </div>
    </div>
  );
};

export default CreateAccount;
