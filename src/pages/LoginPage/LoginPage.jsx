import { IoLogoApple } from "react-icons/io";
import { IoLogoGoogle } from "react-icons/io";
import './LoginPage.css'
import { Navbar } from "../../components/Navbar/Navbar";

export function LoginPage (){
     const [loginData, setLoginData] = useState({
            email: "",
            password: ""
          });
        
          function handleChange(e) {
            const { name, value } = e.target;
            setUserData(prev => ({
              ...prev,
              [name]: value
            }));
          }

    return(
        <>
        <Navbar/>
            <div id="login_container">
                <div id="left_login_box">
                    <h3 id="heading">Sign In</h3>
                    <input type="text" name="email" id="email_input" className="input_btn" placeholder="Email" value={loginData.email} onChange={handleChange}/> <br/>
                    <input type="text" name="password" id="pass_input" className="input_btn" placeholder="Password" value={loginData.password} onChange={handleChange}/>
                    <p className="info_text" id="pwd_text">Forgot your password ?</p>
                    <button className="input_btn" id="sign_in_btn">Sign In</button>
                    <p className="info_text">OR CONTINUE WITH</p>

                    <span id="apple_login_container" >
                        <IoLogoApple/>
                        <p>Sign in with Apple</p>
                    </span>
                    <span id="google_login_container" >
                        <IoLogoGoogle/>
                        <p>Sign in with Google</p>
                    </span>
                </div>
                {/* <div id="right_login_box">
                   <img src={LoginLogo} alt="login_logo" id="login_img"/>
                </div> */}
            </div>
        </>
    ) 
}