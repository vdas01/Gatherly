import "./Info.css";
import { FaRegCalendarAlt } from "react-icons/fa";
import { IoLocationOutline } from "react-icons/io5";
import { BsPeople } from "react-icons/bs";

export function Info({ loc, date, participants }) {
  return (
    <>
      {date && (
        <span id="date_box" className="eventinfo_box">
          <FaRegCalendarAlt />
          <p>{date}</p>
        </span>
      )}
      {loc && (
        <span id="loc_box" className="eventinfo_box">
          <IoLocationOutline />
          <p>{loc}</p>
        </span>
      )}
      {participants && (
        <span id="participant_box" className="eventinfo_box">
          <BsPeople />
          <p>{participants} Participants</p>
        </span>
      )}
    </>
  );
}
