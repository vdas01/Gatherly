import "./EventDetails.css";
import { Navbar } from "../../components/Navbar/Navbar";
import { Header } from "../../components/Header/Header";
import { MdCurrencyRupee } from "react-icons/md";
import { FaCircleMinus } from "react-icons/fa6";
import { AiFillPlusCircle } from "react-icons/ai";
import { useState } from "react";

export function EventDetails({ heading }) {
  const [ticketQuantity, setTicketQuantity] = useState(1);
  return (
    <>
      <div id="event_details_container">
        <Navbar />
        <Header heading={"Board Games Night in Kormangla"} subHeading={""} />
        <div id="middle_box">
          <div id="desc_box">
            About this event <br /><br/> Board Games Night Board Games Night is all
            about connecting with people over exciting board games. It’s a space
            where like-minded individuals come together to relax, laugh,
            strategise, and form genuine friendships through shared fun and
            unforgettable moments. <br /> <br /> Inclusions: INR 199 is
            redeemable at the venue on F&B. A friendly community host to set the
            vibe 10–12 engaging board games to choose from Fun ice-breaker
            sessions to spark conversations Who Can Join? <br />
            <br />
            Anyone who loves board games Anyone open to meeting new people
            Anyone willing to try something new Board Games Include: Sequence,
            Catan, Secret Hitler, Mafia, Codenames, Uno, Bluff, Blokus, and many
            more. We Match You by Skill Level: From Sequence to Catan — whether
            you’re a beginner or a strategist, we’ll match you with players at
            your level so everyone has an amazing time.
            <br /> <br />
            How It Works 📅 <br /> <br />
            Book Your Seat → 🤝 Get Matched → 🌟 Show Up → 💬 Connect → 🌈
            <br /> <br/>
            Continue the Story Lorem ipsum dolor sit amet, consectetur
            adipisicing elit. Quam vel ipsum libero? Blanditiis recusandae
            officiis cum officia excepturi pariatur magni?
            <br /> <br/>
            Continue the Story Lorem ipsum dolor sit amet, consectetur
            adipisicing elit. Quam vel ipsum libero? Blanditiis recusandae
            officiis cum officia excepturi pariatur magni?
          </div>
          <div id="price_cart">
            <div id="event_price">
              <span id="strike_price">
                <MdCurrencyRupee />
                <h6 id="strike_money">77</h6>
              </span>
              <span id="real_price">
                <MdCurrencyRupee />
                <h6 id="real_price_text">499</h6>
              </span>
            </div>
            <p id="quantity_head">Select quantity</p>
            <div id="middle">
              <div id="middle_left">
                <span id="ticket_quant">{ticketQuantity}</span> x rs499
              </div>
              <div id="middle_right">
                <FaCircleMinus
                  onClick={() => setTicketQuantity(ticketQuantity - 1)}
                />
                <p id="quantity_text">{ticketQuantity}</p>
                <AiFillPlusCircle
                  onClick={() => setTicketQuantity(ticketQuantity + 1)}
                />
              </div>
            </div>
            <div id="middle_last">
              <p id="total_quantity_text">Total (incl. selected quantity)</p>
              <p id="total_price">
                <MdCurrencyRupee />
                {ticketQuantity * 499}
              </p>
            </div>
            <button id="checkout_btn">Checkout</button>
          </div>
        </div>
      </div>
    </>
  );
}
