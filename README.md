# Banking Management
##

### Problem statement 

The purpose of the requirements document is to systematically capture requirements for the project and the system “Bank Management System” to be developed. Both functional and non-functional requirements are captured in this document. It also serves as the input for the project scoping. 

### About the System 

The client would like to develop an independent application Bank Management System (BMS) application in order to automate the process of managing the activities of bank like opening an account, transaction etc.  
The following section will cover aspects related to Bank Management System. 
- Customer Registration 
- Apply Loan 
- Update Account Details 

## Running instructions
1. Download the repository as .zip file
2. Extract the .zip file in the local machine
3. Navigate to Back-End folder to find the Microservice source codes
4. Import the applications into a JAVA IDE (i.e Eclipse/Intellij)
5. Run all the microservices as Spring boot application
6. Navigate to the Front-End/Banking-UI to run the front-end application
7. Either in Commnd prompt/terminal run the command "npm install"
8. Run the command "npm start"
9. If not automaticaly redirected to the browser, use the URL - http://localhost:3000/ in your browser to explore the application

Note: Please make sure the database credentials are correct before running the applications

## URLs
Front-end app: http://localhost:3000/
Service Discovery (Eureka) : http://localhost:8761
Api gateway : http://localhost:8765/
Swagger: 
1. Auth Service: http://localhost:8100/swagger-ui/index.html
2. Customer Service: http://localhost:8200/swagger-ui/index.html
3. Account Service: http://localhost:8300/swagger-ui/index.html
4. Loan Service: http://localhost:8400/swagger-ui/index.html
