import welcome from "../components/images/welcome.jpeg";
import "./Welcome.css";
import WelcomeHeader from "./WelcomeHeader";

const Welcome = () => {
  return (
    <div>
      <WelcomeHeader></WelcomeHeader>

      <div className="container2 mt-5">
        <div>
          <p className="welcome-text">
            Welcome to <strong>New Horizon Bank</strong>
          </p>
          <p className="fingertips">Banking at your finger tips...!</p>
        </div>
        <img className="img-fluid" src={welcome} alt="Bank" />
      </div>
    </div>
  );
};
export default Welcome;
