import EmployeeHeader from "./EmployeeHeader";
import "./DeleteCustomer.css";
import ViewCustomerHeader from "./ViewCustomerHeader";
import deleteCustomer from "../images/deleteCustomer.jpg";
import { useState } from "react";
import { Navigate, useNavigate, useLocation } from "react-router-dom";
import SuccessAlert from "../SuccessAlert";

const DeleteCustomer = () => {
  const navigate = useNavigate("");

  const location = useLocation();

  const accountNo = location.state && location.state.accountNo;
  const [successAlert, setSuccessAlert] = useState(false);

  const deleteCustomerSubmitHandler = (e) => {
    e.preventDefault();
    const isConfirmed = window.confirm(
      "Are you sure you want to delete this customer?"
    );

    if (isConfirmed) {
      console.log("Customer deleted successfully");
      console.log("Account No", accountNo);
      setSuccessAlert(true);
    }
  };

  const closeAlert = () => {
    setSuccessAlert(false);
    navigate("/view-customer");
  };

  return (
    <div>
      <ViewCustomerHeader></ViewCustomerHeader>
      <div class="container mt-5">
        <div class="row">
          <div class="col-md-6 ">
            <img src={deleteCustomer} alt="Image 1" class="img-fluid" />
          </div>
          <div className="form-box form-delete col-md-4 mt-5">
            <div class="container mt-3">
              <h2 className="heading-delete">Delete Customer</h2>
              <form onSubmit={deleteCustomerSubmitHandler}>
                <div class="form-group mb-4">
                  <label for="inputB">Account No</label>
                  <input
                    type="number"
                    class="form-control"
                    id="inputB"
                    required
                    placeholder="Enter account no"
                    value={accountNo}
                    disabled
                    style={{ color: "#999" }}
                  />
                </div>

                <div class="form-group ">
                  <button
                    type="submit"
                    class="btn btn-primary button-space mt-3"
                  >
                    Delete
                  </button>
                  {successAlert && (
                    <SuccessAlert
                      message={"Customer deleted successfully!"}
                      onClose={closeAlert}
                    />
                  )}
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DeleteCustomer;
