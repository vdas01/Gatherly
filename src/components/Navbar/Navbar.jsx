import './Navbar.css';
import { FaRegCircleUser } from "react-icons/fa6";
import { Link } from "react-router-dom";
import USerPic from '../../assets/user_pic.jpg'

export function Navbar(){
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
        </div>
        <div id="right">
                <Link className='btn-login' to={`/login`}>Login</Link>
                <Link className='btn-sign' to={`/signup`}>Sign up</Link>
        </div>
         {/* <div id="profile_pic">
                   <img src={USerPic} alt="profile_pic" id='user_profile_pic'/> 
        </div> */}
    </div>
        </>
    )
}