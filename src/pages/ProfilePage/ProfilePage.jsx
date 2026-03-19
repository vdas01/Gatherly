import "./ProfilePage.css";
import { FaInstagram } from "react-icons/fa";
import { FaLinkedin } from "react-icons/fa";
import { useEffect, useState,useContext } from "react";
import { Navbar } from "../../components/Navbar/Navbar";
import { AuthContext } from "../../config/AuthProvider";
import { GET_USER_INFO_API } from "../../config/apis";
import { SIGNUP_API } from "../../config/apis";

export function ProfilePage() {
    const {username} = useContext(AuthContext);
    const [userData,setUserData] = useState([]);
    const[isEditing,setIsEditing] = useState(false);
    useEffect(() => { 
        const fetchUserData = async () => {
            const response = await GET_USER_INFO_API(username);
            setUserData(response.data);
            console.log("User data fetched:", response.data);
        }
        console.log("Username from context:", username);
        fetchUserData(username);
    }, [username]);

    function handleChange(e) {
        const { name, value } = e.target;
        setUserData(prev => ({
          ...prev,
          [name]: value
        }));
      }

    const updateUserData = async () => {  
       console.log("Updated user data to be sent:", userData);
       const response = await SIGNUP_API(userData,true);
       setUserData(response.data);
       setIsEditing(false);
       sessionStorage.setItem("username", response.data?.userName);
       console.log("User data updated:", response.data);
    }


    return(
        <>
         <div id="profilePage_container">
            <Navbar/>
            <div className="user_info_top">
                <div id="user_info_left_box">
                    <h3 id="user_info_heading">{userData?.firstName && userData?.lastName ? 
                    `${userData.firstName} ${userData.lastName}`: "No data"}</h3>
                    <p id="user_info_profession">Software Developer</p>
                    <div id="profilepic_box">
                        <img src="https://www.w3schools.com/howto/img_avatar.png" alt="user_profile_pic" id="user_profile_pic"/>
                    </div>
                    <div id="reputation_box">
                        <p className="reputation_child">
                            <span className="reputation_count">32</span>
                            <span className="reputation_label">Followers</span>
                        </p>
                         <p className="reputation_child">
                            <span className="reputation_count">50</span>
                            <span className="reputation_label">Following</span>
                        </p>
                         <p className="reputation_child">
                            <span className="reputation_count">32</span>
                            <span className="reputation_label">Trust Score</span>
                        </p>
                    </div>
                    {/* <div id="badge_box">
                        <h6>Badges:-</h6>
                        <p className="badge">Best Host</p>
                        <p className="badge">Best Entertainer</p>
                        <p className="badge">Best Dancer</p>
                    </div> */}
                    <div id="social_media_box">
                        <FaInstagram className="social_icon"/>
                        <FaLinkedin className="social_icon"/>
                    </div>
                    {!isEditing &&
                    <button id="edit_profile_btn" onClick={() => setIsEditing(!isEditing)}>Edit Profile</button>
                    }
                    {isEditing &&
                        <div id="edit_buttons">
                        <button id="edit_profile_btn" onClick={updateUserData}>Save</button>
                        <button id="edit_profile_btn" onClick={() => setIsEditing(!isEditing)}>Cancel</button>
                        </div>
                    }
                    
                </div>
               
                <div id="user_info_right_box">
                    <h4 id="bio_heading">Bio & Other Details</h4>
                    <div id="bio_info">
                        {!isEditing && 
                        <>
                        <p className="bio_info_box"><span className="bio_label">First Name:</span> <span className="bio_text">{userData?.firstName ? userData?.firstName : "No data"}</span></p>
                        <p className="bio_info_box"><span className="bio_label">Last Name:</span> <span className="bio_text">{userData?.lastName ? userData?.lastName : "No data"}</span></p>
                        <p className="bio_info_box"><span className="bio_label">UserName:</span> <span className="bio_text">{userData?.userName ? userData?.userName : "No data"}</span></p>
                        <p className="bio_info_box"><span className="bio_label">Email:</span> <span className="bio_text">{userData?.email ? userData?.email : "No data"}</span></p>
                        <p className="bio_info_box"><span className="bio_label">Phone:</span> <span className="bio_text">{userData?.phone ? userData?.phone : "No data"}</span></p>
                        <p className="bio_info_box"><span className="bio_label">Location:</span> <span className="bio_text">{userData?.location ? userData?.location : "No data"}</span></p>
                        <p className="bio_info_box"><span className="bio_label">Badges:</span> <span className="bio_text">{userData?.badges ? userData?.badges : "No data"}</span></p>
                        <p className="bio_info_box"><span className="bio_label">Hobbies:</span> <span className="bio_text">{userData?.hobbies ? userData?.hobbies : "No data"}</span></p>
                        <p className="bio_info_box"><span className="bio_label">Date of Birth:</span> <span className="bio_text">{userData?.dateOfBirth ? userData?.dateOfBirth : "No data"}</span></p>
                        <p className="bio_info_box"><span className="bio_label">Languages known:</span> <span className="bio_text">{userData?.languagesKnown ? userData?.languagesKnown : "No data"}</span></p>
                        <p className="bio_info_box"><span className="bio_label">Bio:</span> <span className="bio_text">{userData?.biography ? userData?.biography : "No data"}</span></p>  
                        </>
                        }
                        {isEditing &&
                        <>
                        <input type="text" name="firstName" className="bio_info_box" onChange={handleChange} placeholder="First Name:" value={userData?.firstName ? userData.firstName : ""}/>
                        <input type="text" name="lastName" className="bio_info_box" onChange={handleChange} placeholder="Last Name:" value={userData?.lastName ? userData.lastName : ""}/>
                        <input type="text" name="userName" className="bio_info_box" onChange={handleChange} placeholder="UserName:" value={userData?.userName ? userData.userName : ""}/>
                        <input type="text" name="email" className="bio_info_box" onChange={handleChange} placeholder="Email:" value={userData?.email ? userData.email : ""}/>
                        <input type="text" name="phone" className="bio_info_box" onChange={handleChange} placeholder="Phone:" value={userData?.phone ? userData.phone : ""}/>
                        <input type="text" name="hobbies" className="bio_info_box" onChange={handleChange} placeholder="Hobbies:" value={userData?.hobbies ? userData.hobbies : ""}/>
                        <input type="text" name="dateOfBirth" className="bio_info_box" onChange={handleChange} placeholder="Date of Birth:" value={userData?.dateOfBirth ? userData.dateOfBirth : ""}/>
                        <input type="text" name="languagesKnown" className="bio_info_box" onChange={handleChange} placeholder="Languages known:" value={userData?.languagesKnown ? userData.languagesKnown : ""}/>
                        <input type="text" name="biography" className="bio_info_box" onChange={handleChange} placeholder="Bio:" value={userData?.biography ? userData.biography : ""}/>
                        </> 
                        }
                    </div>
                </div>
            </div>
        </div>
        </>
       
    )
}