import CustomerHeader from "./CustomerHeader";
import "./Profile.css";
import React, { useState } from "react";
import {useSelector } from "react-redux";
import WelcomeHeader from "../WelcomeHeader";
///
import { useEffect } from 'react'
import { Button, Card, CardBody, CardFooter, Col, Container, Row, Table } from 'reactstrap'
import { getCustomer } from "../../services/user.service";
// import { getCurrentUserDetail, isLoggedIn } from '../auth'
// const ViewUserProfile = ({ user, updateProfileClick }) => {


//     const [currentUser, setCurrentUser] = useState(null)
//     const [login, setLogin] = useState(false)
//     useEffect(() => {
//         setCurrentUser(getCurrentUserDetail())
//         setLogin(isLoggedIn())
//     }, [])

// const Profile = ({ customerData }) => {
const Profile = () => {
  const { user: currentUser } = useSelector((state) => state.auth);
  console.log("user:",currentUser.userName);
  const customerData = currentUser ? getCustomer(currentUser.userName) : null;
  const updateProfileClick = () => {

  }
  return (
    <div>
      <WelcomeHeader></WelcomeHeader>
      <div class="container mt-5">
        <h2 className="heading-text">Profile </h2>
        <Card className='mt-2 border-0 rounded-0 shadow-sm'>
            <CardBody>
                {/* <h3 className='text-uppercase'>user Information</h3> */}

                <Container className='text-center'>
                    <img style={{ maxWidth: '200px', maxHeight: '200px' }} src= 'https://cdn.dribbble.com/users/6142/screenshots/5679189/media/1b96ad1f07feee81fa83c877a1e350ce.png?compress=1&resize=400x300&vertical=top' alt="user profile picture" className='img-fluid  rounded-circle' />
                </Container>
                <Table responsive striped hover bordered={true} className='text-left mt-5'>
                    <tbody>
                        <tr>
                            <td >
                                Customer ID
                            </td>
                            <td>
                                {customerData.customerId}
                            </td>
                        </tr>
                        <tr>
                            <td >
                                USER NAME
                            </td>
                            <td>
                                {/* {user.name} */}
                            </td>
                        </tr>
                        <tr>
                            <td >
                                USER EMAIL
                            </td>
                            <td>
                                {/* {user.email} */}
                            </td>
                        </tr>
                        <tr>
                            <td >
                                ABOUT
                            </td>
                            <td>
                                {/* {user.about} */}
                            </td>

                        </tr>
                        <tr>
                            <td>
                                ROLE
                            </td>
                            <td>
                                {/* {user.roles.map((role) => {
                                    return (
                                        <div key={role.id}>{role.name}</div>
                                    )
                                })} */}
                            </td>
                        </tr>
                    </tbody>
                </Table>

                {currentUser ? (currentUser.userName == customerData.userName) ? (
                    <CardFooter className='text-center'>
                        <Button onClick={updateProfileClick} color='warning' >Update Profile</Button>
                    </CardFooter>
                ) : '' : ''}

            </CardBody>
        </Card>
      </div>
    </div>
  );
};

export default Profile;
