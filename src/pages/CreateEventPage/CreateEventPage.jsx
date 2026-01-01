import { Navbar } from '../../components/Navbar/Navbar';
import './CreateEventPage.css';
import { useState } from "react";

export function CreateEventPage() {
  const [userData, setUserData] = useState({
    heading: "",
    description: "",
    dateTime: "",
    location: "",
    noOfParticipants: ""
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
   <div className="create_event_container">
    <Navbar/>
    <h2 id="create_event_heading">Create New Event</h2>
     <form className='form_container'>
        <label>
          Heading
          <input name='heading' className='input' type="text" value={userData.heading} onChange={handleChange} id='heading_input'/>
        </label>

        <label>
          Description
          <textarea name='description' className='input' value={userData.description} onChange={handleChange} id='desc_input'/>
        </label>

        <label>
          Date
          <input name='date' className='input' type="datetime-local" value={userData.dateTime} onChange={handleChange} id='date_input'/>
        </label>

         <label>
          Location
          <input name='location' className='input' type="text" value={userData.location} onChange={handleChange} id='location_input'/>
        </label>


         <label>
          No of Participants
          <input name='participants' className='input' type="number" value={userData.noOfParticipants} onChange={handleChange} id='participant_input'/>
        </label>


        <input type="submit" value="Submit" id='submit_btn'/>
      </form>
   </div>
   </>
);

}