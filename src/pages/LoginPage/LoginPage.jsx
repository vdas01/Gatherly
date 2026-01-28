import { IoLogoApple } from "react-icons/io";
import { IoLogoGoogle } from "react-icons/io";
import './LoginPage.css'
import { useState ,useContext, useEffect} from "react"; 
import { AuthContext } from "../../config/AuthProvider";
import { useNavigate } from "react-router-dom";
import { Navbar } from "../../components/Navbar/Navbar";
import { LOGIN_API } from "../../config/apis";
import { Toast } from "../../components/Toast/Toast";

export function LoginPage (){
    const navigate = useNavigate();
     const [loginData, setLoginData] = useState({
            username: "",
            password: ""
          });
    const { setUserName } = useContext(AuthContext);
    const [responseData, setResponseData] = useState(null);

        
          function handleChange(e) {
            const { name, value } = e.target;
            setLoginData(prev => ({
              ...prev,
              [name]: value
            }));
          }

        const handleSignIn = async (e) => {
            e.preventDefault();
            try {
                console.log("Login data submitted:", loginData);
                const response = await LOGIN_API(loginData);
               if (response?.data?.errorCode) {
                    setResponseData(response.data);
                }else if(response?.status === 200){
                    setUserName(response.data?.userName);
                    sessionStorage.setItem("username", response.data?.userName);
                    navigate("/");
                }
                
            } catch (error) {
                console.error("Login failed:", error);
            }
        }

    return(
        <>
        
            <div id="login_container">
                <Navbar/>
                <div id="left_login_box">
                    <h3 id="heading">Sign In</h3>
                    <input type="text" name="username" id="email_input" className="input_btn" placeholder="Username" value={loginData.username} onChange={handleChange}/> <br/>
                    <input type="text" name="password" id="pass_input" className="input_btn" placeholder="Password" value={loginData.password} onChange={handleChange}/>
                    <p className="info_text" id="pwd_text">Forgot your password ?</p>
                    {responseData?.message && (
                            <Toast message={responseData.message} onClose={() => setResponseData(null)} className="error"/>
                    )}  
                    <button className="input_btn" id="sign_in_btn" onClick={handleSignIn} disabled = {loginData.username && loginData.password ? false : true}>Sign In</button>
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