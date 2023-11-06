import Login from "./components/Login";
import Welcome from "./components/Welcome";
import CustomerDashboard from "./components/customer/CustomerDashboard";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import EmployeeDashboard from "./components/employee/EmployeeDashboard";
import Profile from "./components/customer/Profile";
import Transactions from "./components/customer/Transactions";
import Withdraw from "./components/customer/Withdraw";
import Transfer from "./components/customer/Transfer";
import CreateCustomer from "./components/employee/CreateCustomer";
import CreateAccount from "./components/employee/CreateAccount";
import ViewCustomer from "./components/employee/ViewCustomer";
import Deposit from "./components/employee/Deposit";
import ServiceCharge from "./components/employee/ServiceCharge";
import DeleteCustomer from "./components/employee/DeleteCustomer";
import Error from "./components/Error";
import { useNavigate } from "react-router-dom";
import React, { useState } from "react";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
  Link,
} from "react-router-dom";

function App() {
  const [customerData, setCustomerData] = useState([]);
  const [transactionData, setTransactionData] = useState([]);

  const onLogin = (customerData, transactionData) => {
    setCustomerData(customerData);
    setTransactionData(transactionData);
  };
  console.log("set customer data :", customerData);
  console.log("set transaction data :", transactionData);

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Welcome />} />
        <Route path="/login" element={<Login onLogin={onLogin} />} />
        <Route path="/customer-dashboard" element={<CustomerDashboard />} />
        <Route path="/profile" element={<Profile/>}/>
        <Route
          path="/customer-profile"
          element={<Profile customerData={customerData} />}
        />
        <Route
          path="/customer-transactions"
          element={
            <Transactions
              transactionData={transactionData}
              customerData={customerData}
            />
          }
        />
        <Route
          path="/customer-withdraw"
          element={<Withdraw customerData={customerData} />}
        />
        <Route
          path="/customer-transfer"
          element={
            <Transfer
              transactionData={transactionData}
              customerData={customerData}
            />
          }
        />
        <Route path="/employee-dashboard" element={<EmployeeDashboard />} />
        <Route path="/create-customer" element={<CreateCustomer />} />
        <Route
          path="/view-customer"
          element={<ViewCustomer customerData={customerData} />}
        />

        <Route path="/deposit" element={<Deposit />} />
        <Route
          path="/service-charge"
          element={<ServiceCharge customerData={customerData} />}
        />
        <Route path="/delete-customer" element={<DeleteCustomer />} />

        <Route path="/logout" element={<Login onLogin={onLogin} />} />
        <Route path="*" element={<Error />} />
      </Routes>
    </Router>
  );
}

export default App;
