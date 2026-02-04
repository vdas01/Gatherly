import { Navbar } from '../../components/Navbar/Navbar';
import './CreateEventPage.css';
import { useState } from "react";
import { CREATE_EVENT_API } from '../../config/apis';

export function CreateEventPage() {
  const [eventData, setEventData] = useState({
    eventName: "",
    description: "",
    eventStartDate: "",
    eventEndDate: "",
    eventPrice: "",
    location: "",
    eventType: "TRIVIA_NIGHT",
    noOfParticipants: ""
  });

  function handleChange(e) {
    const { name, value } = e.target;
    setEventData(prev => ({
      ...prev,
      [name]: value
    }));
  }

  function isValidData(){
    return eventData.eventName?.trim() !== "" &&
           eventData.description?.trim() !== "" &&
           eventData.eventStartDate?.trim() !== "" &&
           eventData.eventEndDate?.trim() !== "" &&
           eventData.location?.trim() !== "" &&
           eventData.eventPrice?.trim() !== "" &&
           eventData.noOfParticipants?.trim() != null ;
  }

  const submitEventData = async(e) =>{
    console.log("clicked");
    e.preventDefault();
    if (!isValidData()) {
      alert("Please fill in all fields.");
      return;
    }
    console.log("Event Data Submitted:", eventData);
    const response = await CREATE_EVENT_API(eventData);
    console.log("Event created successfully:", response.data);
  }

 return (
   <>
   <div className="create_event_container">
    <Navbar/>
    <h2 id="create_event_heading">Create New Event</h2>
     <form className='form_container' onSubmit={submitEventData}>
        <label>
          Event Name
          <input name='eventName' className='input' type="text" value={eventData.eventName} onChange={handleChange} id='heading_input'/>
        </label>

        <label>
          Description
          <textarea name='description' className='input' value={eventData.description} onChange={handleChange} id='desc_input'/>
        </label>

        <label>
          Start Date
          <input name='eventStartDate' className='input' type="datetime-local" value={eventData.eventStartDate} onChange={handleChange} id='date_input'/>
        </label>

        <label>
          End Date
          <input name='eventEndDate' className='input' type="datetime-local" value={eventData.eventEndDate} onChange={handleChange} id='end_date_input'/>
        </label>

         <label>
          Location
          <input name='location' className='input' type="text" value={eventData.location} onChange={handleChange} id='location_input'/>
        </label>

        <label for="event_type">Event Type:
        <select id="event_type" name="eventType" value={eventData.eventType} onChange={handleChange} className='input'>
          <option value="TRIVIA_NIGHT">Trivia Night</option>
          <option value="DINNER_WITH_STRANGERS">Dinner with Strangers</option>
          <option value="BOARD_GAMES">Board Games</option>
        </select>
        </label> 

          <label>
          Price
          <input name='eventPrice' className='input' type="number" value={eventData.eventPrice} onChange={handleChange} id='price_input'/>
        </label>

         <label>
          No of Participants
          <input name='noOfParticipants' className='input' type="number" value={eventData.noOfParticipants} onChange={handleChange} id='participant_input'/>
        </label>

        <input type="submit" value="Submit" id='submit_btn'/>
      </form>
   </div>
   </>
);

}