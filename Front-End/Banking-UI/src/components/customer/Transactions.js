import CustomerHeader from "./CustomerHeader";
import "./Transactions.css";
import transactions2 from "../images/transactions2.jpg";
import { useState, useEffect } from "react";

const Transactions = ({ transactionData, customerData }) => {
  const [enteredFromDate, setFromDate] = useState("");
  const [enteredToDate, setToDate] = useState("");
  const [filteredTransactions, setFilteredTransactions] = useState([]);
  const [showTableHead, setShowTableHead] = useState(false);
  const [searchPerformed, setSearchPerformed] = useState(false);

  useEffect(() => {
    setSearchPerformed(true);

    const sortedTransactions = transactionData
      .slice()
      .sort((a, b) => new Date(b.initiationDate) - new Date(a.initiationDate));

    setFilteredTransactions(sortedTransactions);
    setShowTableHead(true);
  }, []);

  const dateFromHandler = (event) => {
    setFromDate(event.target.value);
  };
  const dateToHandler = (event) => {
    setToDate(event.target.value);
  };

  const viewTransactionsHandler = (e) => {
    e.preventDefault();
    const fromDate = new Date(enteredFromDate);
    const toDate = new Date(enteredToDate);

    const filtered = transactionData.filter((transaction) => {
      const transactionDate = new Date(transaction.initiationDate);
      return transactionDate >= fromDate && transactionDate <= toDate;
    });

    const sortedFiltered = filtered
      .slice()
      .sort((a, b) => new Date(b.initiationDate) - new Date(a.initiationDate));

    setShowTableHead(true);
    setFilteredTransactions(sortedFiltered);
    setSearchPerformed(true);
  };

  const containerStyle = {
    marginLeft: "60px",
    maxWidth: "910px",
  };

  const styles = {
    container: {
      display: "flex",
      justifyContent: "center",
      alignItems: "center",
      marginTop: "90px",
    },
    content: {
      textAlign: "center",
    },
    text: {
      fontSize: "44px",
      color: "rgb(76, 124, 197)",
    },
  };

  return (
    <div>
      <CustomerHeader></CustomerHeader>
      <div class="container-fluid">
        <div class="row">
          <div class="col-md-8 p-4 mt-5">
            <h2 className="table-container mb-5 t-heading">Transactions</h2>

            <div class="container mt-5 " style={containerStyle}>
              <form
                class="row align-items-center trans-form"
                onSubmit={viewTransactionsHandler}
              >
                <div class="col-md-3 div-mr">
                  <label>Account No</label>
                  <input
                    type="text"
                    class="form-control "
                    required
                    placeholder="Account Number"
                    value={customerData[0].accountNo}
                    disabled
                    style={{ color: "#999" }}
                  />
                </div>
                <div class="col-md-3">
                  <label>Date From</label>
                  <input
                    type="date"
                    class="form-control"
                    required
                    onChange={dateFromHandler}
                  />
                </div>
                <div class="col-md-3">
                  <label>Date To</label>
                  <input
                    type="date"
                    class="form-control"
                    required
                    onChange={dateToHandler}
                  />
                </div>
                <div class="col-md-3 mt-4">
                  <button className="btn btn-primary">View Transactions</button>
                </div>
              </form>
            </div>

            <div className="table-responsive table-container mt-5">
              <table className="table table-bordered text-center table-striped">
                {showTableHead && filteredTransactions.length > 0 && (
                  <thead>
                    <tr>
                      <th>Source Account Type</th>
                      <th>Target Account ID</th>
                      <th>Amount</th>
                      <th>Initiation Date</th>
                    </tr>
                  </thead>
                )}

                {filteredTransactions.length > 0 && (
                  <tbody>
                    {filteredTransactions.map((transaction, index) => (
                      <tr key={index}>
                        <td>{transaction.sourceAccountType}</td>
                        <td>{transaction.targetAccountId}</td>
                        <td>&#x20B9;{transaction.amount}</td>
                        <td>{transaction.initiationDate}</td>
                      </tr>
                    ))}
                  </tbody>
                )}
              </table>
            </div>
          </div>

          <div class="col-md-4 img-container mt-5">
            <div class="p-1">
              <img src={transactions2} alt="transactions" class="img-fluid" />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Transactions;
