import "./Error.css";
import error from "./images/error.jpg";

const Error = () => {
  return (
    <div>
      <div class="container">
        <div class="error-container">
          <h1 class="display-4">An Error Occurred</h1>
          <p class="lead">Sorry, we couldn't load this page at the moment</p>
          <img src={error} alt="Error Image" className="error" />
          <p class="mt-4">Please try again later</p>
        </div>
      </div>
    </div>
  );
};

export default Error;
