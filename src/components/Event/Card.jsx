import "./Card.css";
import LoginLogo from "../../assets/login_pic.jpg";
import { Link } from "react-router-dom";

import { FaArrowRight } from "react-icons/fa6";
import { Info } from "./Info";
import { MdCurrencyRupee } from "react-icons/md";

export default function Card({ event,isPurchaseButton }) {
  const handlePayment = async () => {
    const res = await fetch("/payment/create-order?amount=500");
    const data = await res.json();

    const options = {
      key: "YOUR_KEY",
      amount: data.amount,
      currency: "INR",
      order_id: data.orderId,
      handler: async function (response) {
        await fetch("/payment/verify", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(response),
        });
      },
    };

    const rzp = new window.Razorpay(options);
    rzp.open();
  };
  return (
    <>
      <div id="event">
        <img src={LoginLogo} alt="" id="event_img" />
        <p id="event_status">{event.status}</p>
        <p id="event_day">{event.day}</p>
        <div id="event_textbox">
          <h4 id="event_heading">{event.name}</h4>
          <p id="event_description">{event.description}</p>
          <Info
            loc={event.location}
            date={`${event.startDate}  to  ${event.endDate}`}
            participants={event.participants}
          />
          <div id="lower_text_box">
            <div id="event_price">
              <span id="strike_price">
                <MdCurrencyRupee />
                <h6 id="strike_money">{event.strike_price || 0}</h6>
              </span>
              <span id="real_price">
                <MdCurrencyRupee />
                <h6>{event.price}</h6>
              </span>
            </div>
            <div id="event_details_box">
              <p>
               {isPurchaseButton ? (
                    <Link to={`/events/${event.id}`} id="event_text_details">
                        Purchase
                    </Link>
                  ) : (
                    <Link to={`/events/${event.id}`} id="event_text_details">
                        View Details
                    </Link>
                  )}
              </p>
              <FaArrowRight />
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
