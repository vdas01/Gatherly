import { IoIosSearch } from "react-icons/io";
import { Navbar } from "../../components/Navbar/Navbar";
import './EventPage.css'
import { EVENT_DETAILS } from "../../config/config";
import Card from "../../components/Event/Card";
import { IoCloseSharp } from "react-icons/io5";
import { Header } from "../../components/Header/Header";
import { useEffect, useState } from "react";
import { GET_ALL_EVENTS_API } from "../../config/apis";
import { EventBox } from "../../components/EventBox/EventBox";

export function EventPage() {
     const [activeTab, setActiveTab] = useState("price_menu");
     const [price, setPrice] = useState(500);
     const [showFilter, setShowFilter] = useState(false);
     const[inputSearch,setInputSearch]=useState("");
     const [eventList,setEventList] = useState([]);
    const [pageInfo, setPageInfo] = useState({});

         useEffect(() => {
             const loadAllEvents = async () => {
                 const response = await GET_ALL_EVENTS_API();
                  const transformedEvents = response.data?.content.map(event => ({
        id: event.id,
        name: event.eventName,
        description: event.description,
        startDate: event.eventStartDate,
        endDate: event.eventEndDate,
        price: event.eventPrice,
        strike_price: event.eventStrikePrice,
        status: event.eventStatus,
        type: event.eventType,
        location: event.location,
        imageUrl: event.imageUrl,
        participants: event.noOfParticipants
      }));
      setEventList(transformedEvents);
        setPageInfo({
          page: response.data.page,
          size: response.data.size,
          totalElements: response.data.totalElements,
          totalPages: response.data.totalPages,
          last: response.data.last
        });
                 console.log("Event page data:-", response?.data.content[0]);
             }
             loadAllEvents();
         },[])

  const renderContent = () => {
    switch (activeTab) {
      case "price_menu":
        return <div className="price-container">
                    <label className="price-label">
                        Price: ₹<span>{price}</span>
                    </label>
                    <br/>
                    <input  type="range" min="0" max="5000" value={price} 
                     onChange={(e) => setPrice(e.target.value)} className="price-range"/>
                </div>;
      case "date_menu":
        return <div className="date_container">
            date
        </div>;
      default:
        return <div>Select an option</div>;
    }
  };

    return (
        <>
        <div id="event_conatiner">
            <Navbar />
            <Header heading={'Discover Events That Match Your Mood'} subHeading={'Where New Stories Begin'}/>
            <div id="filter_conatiner">
                <div className="search-box">
                    <input type="text" placeholder="Search for event,location..." id="search_input" 
                    value={inputSearch} onChange={(e) => setInputSearch(e.target.value)}/>
                    <IoIosSearch className="search-icon" /> 
                </div>
                <button id="filter_btn" onClick={() => setShowFilter(!showFilter)}>Filter</button>
            </div>
            {showFilter &&
            <div className="popup-box">
                <div className="popup-left">
                    <h3 onClick={() => setActiveTab("price_menu")} className={`popup_menu ${activeTab === "price_menu" ? "popup_active" : ""}`}>
                        Price
                    </h3>
                    <h3 onClick={() => setActiveTab("date_menu")} className={`popup_menu ${activeTab === "date_menu" ? "popup_active" : ""}`}>
                        Date
                    </h3>
                    <button id="apply_btn">Apply</button>
                    <IoCloseSharp className="close_icon" onClick={() => setShowFilter(false)}/>
                </div>
                <div className="popup-right">{renderContent()}</div>
             </div>
            }
            <EventBox EVENT_LIST={eventList} inputSearch = {inputSearch}/>
        </div>
        </>
    )
}