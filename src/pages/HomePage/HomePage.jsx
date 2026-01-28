import axios from 'axios';  
import './Homepage.css'
import { Navbar } from '../../components/Navbar/Navbar'
import { useEffect, useState } from "react";
import { FaLocationDot } from "react-icons/fa6";
import { LOCATION_API } from '../../config/apis';
import { GET_CURRENT_LOCATION } from '../../config/config';

export function HomePage() {
    const [city, setCity] = useState("");

//     useEffect(() => {
//       const fetchLocation = async () => {
//         const userCity = await GET_CURRENT_LOCATION;
//         setCity(userCity);
//       };
//       fetchLocation();  
//   }, []);

    return (
    <>
    <div id="header">
        <Navbar/>
        <div id='first_tab'>
            <div id='location_box'>
                <FaLocationDot />
                {/* <h4 id='location_heading'>{city ? city : "Unknown location"}</h4> */}
            </div>
            <h2 id='center_head'>Weekly hangouts<br/> that turn vibes <br/>into friendships.</h2>
            <p id="group_btn">
                Find your group
            </p>
        </div>
    </div>
    </>
    )
}