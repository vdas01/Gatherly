import { Header } from "../../components/Header/Header";
import { Navbar } from "../../components/Navbar/Navbar";
import { EventBox } from "../../components/EventBox/EventBox";
import { PASS_DETAILS } from "../../config/config";


export function Subscription(){
    return (
        <>
            <div id="subscription_container">
                <Navbar/>
                <Header heading={'Discover Exclusive events passes'} subHeading={'Go whenever you want'}/>
                <EventBox EVENT_LIST={PASS_DETAILS} inputSearch = {''}/>
            </div>
        </>
    )
}