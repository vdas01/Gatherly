import { Navbar } from "../../components/Navbar/Navbar";
import "./SignUpPage.css";
import { useState } from "react";



export function SignUpPage() {
     const [userData, setUserData] = useState({
        name: "",
        username: "",
        email: "",
        phone_number: "",
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
    return (
        <>
         <Navbar/>
        <div id="signup_container">
            <h2 id="signup_heading">Sign Up</h2>
           <div id="signup_form">
            <input type="text" name="name" className="signup_input" placeholder="Full Name" value={userData.name} onChange={handleChange}/>
            <input type="text" name="username" className="signup_input" placeholder="Username" value={userData.username} onChange={handleChange}/>
            <input type="email" name="email" className="signup_input" placeholder="Email" value={userData.email} onChange={handleChange}/>
            <input type="number" name="phone_number"  placeholder="Mobile Number" className="signup_input" minLength={10} maxLength={10} value={userData.phone_number} onChange={handleChange}/>
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
          

            <button id="signup_btn">Sign Up</button>
           </div>
        </div>
          </>
    )
}