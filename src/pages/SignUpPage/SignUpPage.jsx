import { Navbar } from "../../components/Navbar/Navbar";
import "./SignUpPage.css";
import { SIGNUP_API } from "../../config/apis";
import { useState } from "react";
import { useNavigate } from "react-router-dom";



export function SignUpPage() {
  const navigate = useNavigate();
     const [userData, setUserData] = useState({
        firstName: "",
        lastName: "",
        userName: "",
        email: "",
        phone: "",
        password: "",
        gender: ""
      });
    
      function handleChange(e) {
        const { name, value } = e.target;
        setUserData(prev => ({
          ...prev,
          [name]: value
        }));
      }

      const handleSignUp = async (e) => {
        e.preventDefault();
        const response = await SIGNUP_API(userData,false);
        setUserData(null);
        navigate("/profile"); 
        console.log("Sign Up successful:", response.data);
      }
    return (
        <>
        <div id="signup_container">
           <Navbar/>
            <h2 id="signup_heading">Sign Up</h2>
           <div id="signup_form">
            <input type="text" name="firstName" className="signup_input" placeholder="First Name" value={userData.firstName} onChange={handleChange}/>
            <input type="text" name="lastName" className="signup_input" placeholder="Last Name" value={userData.lastName} onChange={handleChange}/>
            <input type="text" name="userName" className="signup_input" placeholder="Username" value={userData.userName} onChange={handleChange}/>
            <input type="email" name="email" className="signup_input" placeholder="Email" value={userData.email} onChange={handleChange}/>
            <input type="number" name="phone"  placeholder="Mobile Number" className="signup_input" minLength={10} maxLength={10} value={userData.phone} onChange={handleChange}/>
            <input type="password" name="password" className="signup_input" placeholder="Password" value={userData.password} onChange={handleChange}/>
            <div id="gender_container">
                <span id="gender_text">Gender:-</span>
                <span className="gender-box">
                    <label className="gender-option">
                        <input type="radio" name="gender" value="male" checked={userData.gender === "male"} onChange={handleChange}/>
                        <span>Male</span>
                    </label>

                    <label className="gender-option">
                        <input type="radio" name="gender" value="female" checked={userData.gender === "female"} onChange={handleChange}/>
                        <span>Female</span>
                    </label>
                </span>
            </div>
          
            <button id="signup_btn" onClick={handleSignUp}>Sign Up</button>
           </div>
        </div>
          </>
    )
}