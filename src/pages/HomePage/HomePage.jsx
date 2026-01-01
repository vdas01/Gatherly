import axios from 'axios';
import './Homepage.css'
import { Navbar } from '../../components/Navbar/Navbar'
import { useEffect, useState } from "react";
import { FaLocationDot } from "react-icons/fa6";
import { LOCATION_API } from '../../config/apis';

export function HomePage() {
    const [city, setCity] = useState("");

    useEffect(() => {
    navigator.geolocation.getCurrentPosition(async (pos) => {
      const lat = pos.coords.latitude;
      const lon = pos.coords.longitude;

      const res = await axios.get(LOCATION_API(lat,lon));
      const data = res.data
    
      const userCity =
        data.address.city ||
        data.address.town ||
        data.address.village ||
        "Unknown location";

      setCity(userCity);
    });
  }, []);

    return (
    <>
    <div id="header">
        <Navbar/>
        <div id='first_tab'>
            <div id='location_box'>
                <FaLocationDot />
                <h4 id='location_heading'>{city}</h4>
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