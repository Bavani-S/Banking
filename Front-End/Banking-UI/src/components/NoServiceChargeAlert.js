import "./NoServiceChargeAlert.css";

const NoServiceChargeAlert = ({ message, onClose }) => {
  return (
    <div className="custom-alert">
      <div className="alert-content">
        <p>
          {message}
          {"\u{1F60A}"}
        </p>
        <button className="close-button" onClick={onClose}>
          Close
        </button>
      </div>
    </div>
  );
};

export default NoServiceChargeAlert;
