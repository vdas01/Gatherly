import { Header } from "../../components/Header/Header";
import { Navbar } from "../../components/Navbar/Navbar";
import { EventBox } from "../../components/EventBox/EventBox";
import { useEffect,useState } from "react";
import { GET_ALL_SUBSCRIPTIONS_API } from "../../config/apis";


export function Subscription(){
      const [subscriptionList,setSubscriptionList] = useState([]);

    const formatDateTime = (dateString) => {
        if (!dateString) return "";
        const date = new Date(dateString);
        return date.toLocaleString("en-IN", {
            year: "numeric",
            month: "2-digit",
            day: "2-digit",
            hour: "2-digit",
            minute: "2-digit",
            hour12: true
        });
    };

    useEffect(() => {
        const loadAllSubscriptions = async () => {
           const response = await GET_ALL_SUBSCRIPTIONS_API();
            const transformedSubscriptions = response.data?.map(subscription => ({
                id:subscription.id, // Generating a unique ID for each subscription
                name: subscription.subscriptionName,
                status: subscription.subscriptionStatus,
                price: subscription.discountPrice,
                strike_price: subscription.price,
                startDate: formatDateTime(subscription.startTime),
                endDate: formatDateTime(subscription.endTime),
                description: subscription.description
            }));
            setSubscriptionList(transformedSubscriptions);
        }
        loadAllSubscriptions();
      }, []);
    return (
        <>
            <div id="subscription_container">
                <Navbar/>
                <Header heading={'Discover Exclusive events passes'} subHeading={'Go whenever you want'}/>
                <EventBox EVENT_LIST={subscriptionList} inputSearch = {''} isPurchaseButton={true}/>
            </div>
        </>
    )
}