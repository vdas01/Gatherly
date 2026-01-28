import './Navbar.css';
import { FaRegCircleUser } from "react-icons/fa6";
import { Link } from "react-router-dom";
import USerPic from '../../assets/user_pic.jpg'
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useContext } from 'react';
import { AuthContext } from '../../config/AuthProvider';

export function Navbar(){
    const navigate = useNavigate();
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const { username, setUserName } = useContext(AuthContext);
    
    const handleLogOut = () => {
        setUserName(null);
        sessionStorage.removeItem("username");
        setIsMenuOpen(false);
        navigate(0);
    }

    return(
        <>
    <div id="navbar_container">
        <div id="left">
                <Link id="logo_text" to={`/`}>Gatherly</Link>
        </div>
        <div id="center">
            <p>About</p>
            <p><Link to={`/events`} className='event_bar'>Events</Link></p>
            <p><Link to={`/subscriptions`} className='event_bar'>Subscriptions</Link></p>
            <p><Link to={`/createEvent`} className='event_bar'>Create Event</Link></p>
            {/* <p><Link to={`/profile`} className='event_bar'>User profile</Link></p> */}
        </div>
        <div id="right">
            {username == null && 
            <>
            <Link className='btn-login' to={`/login`}>Login</Link>
                <Link className='btn-sign' to={`/signup`}>Sign up</Link>
            </>
            }
            {username != null &&
                <div className='user_icon_container'>
                    <img src={USerPic} alt="profile_pic" id='user_profile_pic' onClick={() => setIsMenuOpen(!isMenuOpen)}/> 
                    {isMenuOpen &&
                        <div id='profile_menu'>
                            <Link to={`/profile`} className='event_bar profile_menu_option'>My Profile</Link>
                            <hr />
                            <Link className='event_bar profile_menu_option' onClick={handleLogOut}>Logout</Link>
                        </div>
                    }
                </div>
            }    
        </div>
     
    </div>
        </>
    )
}