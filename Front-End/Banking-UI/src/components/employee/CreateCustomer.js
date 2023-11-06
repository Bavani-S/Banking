import "./CreateCustomer.css";
import EmployeeHeader from "./EmployeeHeader";
import createCustomer2 from "../images/createCustomer2.jpg";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import SuccessAlert from "../SuccessAlert";
import "react-toastify/dist/ReactToastify.css";
import { toast, ToastContainer } from "react-toastify";

const CreateCustomer = () => {
  const navigate = useNavigate("");

  const [enteredCustomerName, setCustomerName] = useState("");
  const [enteredPassword, setPassword] = useState("");
  const [enteredDob, setDob] = useState("");
  const [enteredPan, setPan] = useState("");
  const [enteredInitiationDate, setInitiationDate] = useState("");
  const [enteredOpeningAmount, setOpeningAmount] = useState("");
  const [enteredAddress, setAddress] = useState("");
  const [successAlert, setSuccessAlert] = useState(false);

  const [isNameValid, setIsNameValid] = useState(true);
  const [isPasswordValid, setIsPasswordValid] = useState(true);
  const [isDobValid, setIsDobValid] = useState(true);
  const [isInitiationDateValid, setIsInitiationDateValid] = useState(true);
  const [isPanValid, setIsPanValid] = useState(true);
  const [isOpeningAmountValid, setIsOpeningAmountValid] = useState(true);
  const [isAddressValid, setIsAddressValid] = useState(true);

  const customerNameChangeHandler = (event) => {
    const inputValue = event.target.value;
    const namePattern = /^[A-Za-z\s]+$/;

    if (inputValue === "") {
      setIsNameValid(true);
    } else if (inputValue.length > 30 || !namePattern.test(inputValue)) {
      setIsNameValid(false);
    } else {
      setIsNameValid(true);
    }

    setCustomerName(inputValue);
  };
  const passwordChangeHandler = (event) => {
    const inputValue = event.target.value;
    const passwordPattern =
      /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@#$%^&*!])[A-Za-z\d@#$%^&*!]+$/;

    if (inputValue === "") {
      setIsPasswordValid(true);
    } else if (inputValue.length < 8 || !passwordPattern.test(inputValue)) {
      setIsPasswordValid(false);
    } else {
      setIsPasswordValid(true);
    }

    setPassword(event.target.value);
  };
  const dobChangeHandler = (event) => {
    const inputValue = event.target.value;
    const currentDate = new Date().toISOString().split("T")[0];

    if (inputValue <= currentDate) {
      setIsDobValid(true);
    } else {
      setIsDobValid(false);
    }

    setDob(event.target.value);
  };
  const panChangeHandler = (event) => {
    const inputValue = event.target.value;
    const panPattern = /^[A-Z]{5}[0-9]{4}[A-Z]$/;

    if (inputValue.length === 10) {
      if (panPattern.test(inputValue)) {
        setIsPanValid(true);
      } else {
        setIsPanValid(false);
      }
    } else {
      setIsPanValid(true);
    }

    setPan(event.target.value);
  };
  const addressChangeHandler = (event) => {
    const inputValue = event.target.value;

    if (inputValue === "") {
      setIsAddressValid(true);
    } else if (inputValue.length >= 5) {
      setIsAddressValid(true);
    } else {
      setIsAddressValid(false);
    }

    setAddress(event.target.value);
  };

  const initiationDateChangeHandler = (event) => {
    const inputValue = event.target.value;
    const currentDate = new Date().toISOString().split("T")[0];

    if (inputValue <= currentDate) {
      setIsInitiationDateValid(true);
    } else {
      setIsInitiationDateValid(false);
    }

    setInitiationDate(event.target.value);
  };
  const openingAmountChangeHandler = (event) => {
    const inputValue = event.target.value;

    if (inputValue === "") {
      setIsOpeningAmountValid(true);
    } else if (/^\d+$/.test(inputValue)) {
      setIsOpeningAmountValid(true);
    } else {
      setIsOpeningAmountValid(false);
    }

    setOpeningAmount(event.target.value);
  };

  const handleCreateCustomerSubmit = async (e) => {
    e.preventDefault();
    const customerData = {
      customerName: enteredCustomerName,
      password: enteredPassword,
      dateOfBirth: enteredDob,
      pan: enteredPan,
      initiationDate: enteredInitiationDate,
      openingAmount: enteredOpeningAmount,
      address: enteredAddress,
    };

    console.log("Customer created successfully");
    console.log(customerData);

    setSuccessAlert(true);
  };

  const closeAlert = () => {
    setSuccessAlert(false);
    navigate("/employee-dashboard");
  };

  const handleCreateCustomerCancel = () => {
    // setCustomerId("");
    setCustomerName("");
    setPassword("");
    setDob("");
    setPan("");
    setInitiationDate("");
    setOpeningAmount("");
    setAddress("");

    setIsNameValid(true);
    setIsPasswordValid(true);
    setIsDobValid(true);
    setIsInitiationDateValid(true);
    setIsPanValid(true);
    setIsOpeningAmountValid(true);
    setIsAddressValid(true);
  };

  return (
    <div>
      <EmployeeHeader></EmployeeHeader>
      <div class="container mt-5">
        <div class="row">
          <div class="col-md-5 create-customer-form form-bg">
            <div>
              <div>
                <form onSubmit={handleCreateCustomerSubmit}>
                  <h2 class="text-center mb-4 mt-4 create-cus-head">
                    Create Customer
                  </h2>

                  <div class="form-group form-grp mt-3">
                    <label for="customer-name">Customer Name</label>
                    <input
                      type="text"
                      className={`form-control ${
                        isNameValid ? "" : "is-invalid"
                      }`}
                      id="customer-name"
                      placeholder="Enter Customer Name"
                      required
                      value={enteredCustomerName}
                      onChange={customerNameChangeHandler}
                      maxLength="30"
                    />
                    {!isNameValid && (
                      <div className="invalid-feedback">
                        Please enter a valid name
                      </div>
                    )}
                  </div>
                  <div class="form-group form-grp mt-3">
                    <label for="password">Password</label>
                    <input
                      type="password"
                      className={`form-control ${
                        isPasswordValid ? "" : "is-invalid"
                      }`}
                      id="password"
                      placeholder="Enter Password"
                      required
                      value={enteredPassword}
                      onChange={passwordChangeHandler}
                      minLength="8"
                      maxLength="25"
                    />
                    {!isPasswordValid && (
                      <div className="invalid-feedback">Weak password</div>
                    )}
                  </div>

                  <div class="form-group form-grp mt-3">
                    <div class="row">
                      <div class="col-md-6">
                        <label for="dob">Date of Birth</label>
                        <input
                          type="date"
                          className={`form-control ${
                            isDobValid ? "" : "is-invalid"
                          }`}
                          id="dob"
                          required
                          value={enteredDob}
                          onChange={dobChangeHandler}
                        />
                        {!isDobValid && (
                          <div className="invalid-feedback">
                            Date must be less than or equal to the current date.
                          </div>
                        )}
                      </div>
                      <div class="col-md-6">
                        <label for="pan">PAN</label>
                        <input
                          type="text"
                          className={`form-control ${
                            isPanValid ? "" : "is-invalid"
                          }`}
                          id="pan"
                          placeholder="Enter PAN"
                          required
                          value={enteredPan}
                          onChange={panChangeHandler}
                          maxLength="10"
                        />
                        {!isPanValid && (
                          <div className="invalid-feedback">
                            Please enter valid PAN
                          </div>
                        )}
                      </div>
                    </div>
                  </div>
                  <div class="form-group form-grp mt-3">
                    <div class="row">
                      <div class="col-md-6">
                        <label for="dob">Initiation Date</label>
                        <input
                          type="date"
                          className={`form-control ${
                            isInitiationDateValid ? "" : "is-invalid"
                          }`}
                          id="initiation-date"
                          required
                          value={enteredInitiationDate}
                          onChange={initiationDateChangeHandler}
                        />
                        {!isInitiationDateValid && (
                          <div className="invalid-feedback">
                            Date must be less than or equal to the current date.
                          </div>
                        )}
                      </div>
                      <div class="col-md-6">
                        <label for="pan">Opening Amount</label>
                        <input
                          type="number"
                          className={`form-control ${
                            isOpeningAmountValid ? "" : "is-invalid"
                          }`}
                          id="opening-amount"
                          placeholder="Enter Opening Amount"
                          required
                          value={enteredOpeningAmount}
                          onChange={openingAmountChangeHandler}
                        />
                        {!isOpeningAmountValid && (
                          <div className="invalid-feedback">
                            Please enter a valid Amount.
                          </div>
                        )}
                      </div>
                    </div>
                  </div>
                  <div class="form-group form-grp mt-3">
                    <label for="address">Address</label>
                    <textarea
                      className={`form-control ${
                        isAddressValid ? "" : "is-invalid"
                      }`}
                      id="address"
                      rows="3"
                      placeholder="Enter Address"
                      required
                      value={enteredAddress}
                      onChange={addressChangeHandler}
                    ></textarea>
                    {!isAddressValid && (
                      <div className="invalid-feedback">
                        Please enter a valid address with a minimum length of 5
                        characters.
                      </div>
                    )}
                  </div>
                  {/* </div> */}
                  <button type="submit" class="btn btn-primary form-grp mt-4">
                    Create
                  </button>
                  {successAlert && (
                    <SuccessAlert
                      message={"Customer created successfully!"}
                      onClose={closeAlert}
                    />
                  )}
                  <button
                    type="button"
                    class="btn btn-secondary btn2 form-grp mt-4"
                    onClick={handleCreateCustomerCancel}
                  >
                    Cancel
                  </button>
                </form>
              </div>
            </div>
          </div>
          <div class="col-md-6 createCustomer-img-container">
            <img
              src={createCustomer2}
              alt="create customer"
              class="img-fluid"
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default CreateCustomer;
