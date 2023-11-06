import EmployeeHeader from "./EmployeeHeader";
import "./ViewCustomer.css";
import { useNavigate } from "react-router-dom";

import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

import {
  faTrash,
  faMinusSquare,
  faPlusSquare,
} from "@fortawesome/free-solid-svg-icons";

const ViewCustomer = ({ customerData }) => {
  const navigate = useNavigate();
  const [enteredAccountNo, setAccountNo] = useState("");
  const [enteredAccountType, setAccountType] = useState("savings");
  const [searchResults, setSearchResults] = useState([]);
  const [searchPerformed, setSearchPerformed] = useState(false);
  const [isHovered, setIsHovered] = useState(false);
  const [isServiceChargeHovered, setIsServiceChargeHovered] = useState(false);
  const [isDeleteHovered, setIsDeleteHovered] = useState(false);
  const [allTransactions, setAllTransactions] = useState([]);

  useEffect(() => {
    const savingsTransactions = customerProfile.filter(
      (profile) => profile.accountType === "savings"
    );
    setAllTransactions(savingsTransactions);
  }, []);

  const sortedTransactions = allTransactions.sort(
    (a, b) => b.accountNo - a.accountNo
  );

  const customerProfile = [
    {
      accountNo: 1236603040001,
      name: "Amal Varma",
      dob: "3-4-1999",
      pan: "DGTRE2366P",
      address: "Abc house, Calicut, Kerala, 675453",
      accountType: "savings",
      currentBalance: 100,
    },
    {
      accountNo: 1236603040001,
      name: "Amal Varma",
      dob: "3-4-1999",
      pan: "DGTRE2366P",
      address: "Abc house, Calicut, Kerala, 675453",
      accountType: "current",
      currentBalance: 10000,
    },
    {
      accountNo: 1788598740002,
      name: "Vishnu Ram",
      dob: "9-10-1997",
      pan: "GHTEI3455P",
      address: "Def house, Kochi, Kerala, 653239",
      accountType: "savings",
      currentBalance: 78000,
    },
    {
      accountNo: 1788598740002,
      name: "Vishnu Ram",
      dob: "9-10-1997",
      pan: "GHTEI3455P",
      address: "Def house, Kochi, Kerala, 653239",
      accountType: "current",
      currentBalance: 30000,
    },
    {
      accountNo: 1909803040003,
      name: "Lalu Surya",
      dob: "20-4-19996",
      pan: "FGJUY6766K",
      address: "Xyz house, kannur, Kerala, 675432",
      accountType: "savings",
      currentBalance: 9000,
    },
    {
      accountNo: 1909803040003,
      name: "Lalu Surya",
      dob: "20-4-19996",
      pan: "FGJUY6766K",
      address: "Xyz house, Kannur, Kerala, 675432",
      accountType: "current",
      currentBalance: 15000,
    },
  ];

  const searchCustomerHandler = (e) => {
    e.preventDefault();
    const filteredProfiles = customerProfile.filter(
      (profile) =>
        profile.accountNo === Number(enteredAccountNo) &&
        profile.accountType === enteredAccountType
    );
    console.log(enteredAccountNo);
    console.log(enteredAccountType);

    setSearchResults(filteredProfiles);
    setSearchPerformed(true);
  };

  const depositHandler = (accountNo) => {
    navigate("/deposit", { state: { accountNo } });
  };

  const servicechargeHandler = (accountNo, accountType) => {
    navigate("/service-charge", {
      state: { accountNo, accountType },
    });
    console.log(accountNo + " " + accountType);
  };

  const deleteCustomerHandler = (accountNo) => {
    navigate("/delete-customer", { state: { accountNo } });
  };

  const styles = {
    container: {
      display: "flex",
      justifyContent: "center",
      alignItems: "center",
      marginTop: "130px",
    },
    content: {
      textAlign: "center",
    },
    text: {
      fontSize: "44px",
      color: "rgb(76, 124, 197)",
    },

    formBackground: {
      backgroundColor: "rgba(3, 98, 252, 0.275)",
      paddingTop: "25px",
      paddingBottom: "25px",
      paddingLeft: "130px",
      borderRadius: "0.5em",
      boxShadow: "0px 0px 10px rgba(0, 0, 0, 0.09)",
    },
  };
  return (
    <div>
      <EmployeeHeader></EmployeeHeader>
      <div>
        <h2 className="view-cus-head mt-4 ">View Customer</h2>

        <div class="container col-sm-7 mt-5">
          <form
            class="row"
            style={styles.formBackground}
            onSubmit={searchCustomerHandler}
          >
            <div class="col-sm-4 form-group ">
              <label for="accountNo">Account No:</label>
              <input
                type="text"
                class="form-control"
                id="accountNo"
                name="accountNo"
                placeholder="Enter Account No"
                required
                onChange={(e) => setAccountNo(e.target.value)}
              />
            </div>

            <div class="col-sm-4 form-group">
              <label for="accountType">Account Type:</label>
              <div class="input-group">
                <select
                  class="form-select"
                  id="accountType"
                  name="accountType"
                  onChange={(e) => setAccountType(e.target.value)}
                >
                  <option value="savings">savings</option>
                  <option value="current">current</option>
                </select>
              </div>
            </div>

            <div class="col-sm-4 mt-4">
              <button type="submit" class="btn btn-primary">
                Search
              </button>
            </div>
          </form>
        </div>

        <div className="table-service-container">
          <div class="container mt-5 ">
            <table class="table table-bordered table-striped text-center">
              {((searchPerformed && searchResults != 0) ||
                (searchPerformed && sortedTransactions > 0) ||
                !searchPerformed) && (
                <thead class="thead-dark">
                  <tr>
                    <th>Account No</th>
                    <th>Name</th>
                    <th>DOB</th>
                    <th>PAN</th>
                    <th>Address</th>
                    <th>Account type</th>
                    <th>Current Balance</th>
                    <th>Action</th>
                  </tr>
                </thead>
              )}

              <tbody>
                {searchPerformed
                  ? searchResults.map((profile) => (
                      <tr key={profile.accountNo}>
                        <td className="align-middle">{profile.accountNo}</td>
                        <td className="align-middle">{profile.name}</td>
                        <td className="align-middle">{profile.dob}</td>
                        <td className="align-middle">{profile.pan}</td>
                        <td className="align-middle">{profile.address}</td>
                        <td className="align-middle">{profile.accountType}</td>
                        <td className="align-middle">
                          &#x20B9;{profile.currentBalance}
                        </td>
                        <td>
                          <div className="custom-list-container">
                            <ul className="custom-list">
                              <li
                                className="icon-container"
                                onMouseEnter={() => setIsHovered(true)}
                                onMouseLeave={() => setIsHovered(false)}
                              >
                                <span
                                  className="icon"
                                  onClick={() =>
                                    depositHandler(profile.accountNo)
                                  }
                                >
                                  <FontAwesomeIcon
                                    className="cursor"
                                    icon={faPlusSquare}
                                  />
                                  {isHovered && (
                                    <span className="text">Deposit</span>
                                  )}
                                </span>
                              </li>

                              <li
                                className="icon-container"
                                onMouseEnter={() =>
                                  setIsServiceChargeHovered(true)
                                }
                                onMouseLeave={() =>
                                  setIsServiceChargeHovered(false)
                                }
                              >
                                <span
                                  className={`icon ${
                                    isServiceChargeHovered ? "hovered" : ""
                                  }`}
                                  // onClick={() =>
                                  //   servicechargeHandler(
                                  //     enteredAccountNo,
                                  //     enteredAccountType
                                  //   )
                                  // }
                                  onClick={() =>
                                    servicechargeHandler(
                                      profile.accountNo,
                                      profile.accountType
                                    )
                                  }
                                >
                                  <FontAwesomeIcon
                                    icon={faMinusSquare}
                                    className="cursor"
                                  />
                                  {isServiceChargeHovered && (
                                    <span className="text">Service Charge</span>
                                  )}
                                </span>
                              </li>

                              <li
                                className="icon-container"
                                onMouseEnter={() => setIsDeleteHovered(true)}
                                onMouseLeave={() => setIsDeleteHovered(false)}
                              >
                                <span
                                  className={`icon ${
                                    isDeleteHovered ? "hovered" : ""
                                  }`}
                                  onClick={() =>
                                    deleteCustomerHandler(profile.accountNo)
                                  }
                                >
                                  <FontAwesomeIcon
                                    icon={faTrash}
                                    className="cursor"
                                  />
                                  {isDeleteHovered && (
                                    <span className="text">
                                      Delete customer
                                    </span>
                                  )}
                                </span>
                              </li>
                            </ul>
                          </div>
                        </td>
                      </tr>
                    ))
                  : sortedTransactions.map((transaction) => (
                      <tr key={transaction.id}>
                        <td className="align-middle">
                          {transaction.accountNo}
                        </td>
                        <td className="align-middle">{transaction.name}</td>
                        <td className="align-middle">{transaction.dob}</td>
                        <td className="align-middle">{transaction.pan}</td>
                        <td className="align-middle">{transaction.address}</td>
                        <td className="align-middle">
                          {transaction.accountType}
                        </td>
                        <td className="align-middle">
                          &#x20B9;{transaction.currentBalance}
                        </td>
                        <td>
                          <div className="custom-list-container">
                            <ul className="custom-list">
                              <li
                                className="icon-container"
                                onMouseEnter={() => setIsHovered(true)}
                                onMouseLeave={() => setIsHovered(false)}
                              >
                                <span
                                  className="icon"
                                  onClick={() =>
                                    depositHandler(transaction.accountNo)
                                  }
                                >
                                  <FontAwesomeIcon
                                    className="cursor"
                                    icon={faPlusSquare}
                                  />
                                  {isHovered && (
                                    <span className="text">Deposit</span>
                                  )}
                                </span>
                              </li>

                              <li
                                className="icon-container"
                                onMouseEnter={() =>
                                  setIsServiceChargeHovered(true)
                                }
                                onMouseLeave={() =>
                                  setIsServiceChargeHovered(false)
                                }
                              >
                                <span
                                  className={`icon ${
                                    isServiceChargeHovered ? "hovered" : ""
                                  }`}
                                  onClick={() =>
                                    // servicechargeHandler(transaction.accountNo)
                                    servicechargeHandler(
                                      transaction.accountNo,
                                      transaction.accountType
                                    )
                                  }
                                >
                                  <FontAwesomeIcon
                                    icon={faMinusSquare}
                                    className="cursor"
                                  />
                                  {isServiceChargeHovered && (
                                    <span className="text">Service Charge</span>
                                  )}
                                </span>
                              </li>

                              <li
                                className="icon-container"
                                onMouseEnter={() => setIsDeleteHovered(true)}
                                onMouseLeave={() => setIsDeleteHovered(false)}
                              >
                                <span
                                  className={`icon ${
                                    isDeleteHovered ? "hovered" : ""
                                  }`}
                                  onClick={() =>
                                    deleteCustomerHandler(transaction.accountNo)
                                  }
                                >
                                  <FontAwesomeIcon
                                    icon={faTrash}
                                    className="cursor"
                                  />
                                  {isDeleteHovered && (
                                    <span className="text">
                                      Delete customer
                                    </span>
                                  )}
                                </span>
                              </li>
                            </ul>
                          </div>
                        </td>
                      </tr>
                    ))}
              </tbody>
            </table>
            {searchPerformed && searchResults == 0 && (
              <div style={styles.container}>
                <div style={styles.content}>
                  <h1 style={styles.text}>Customer Does Not Exist</h1>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewCustomer;
