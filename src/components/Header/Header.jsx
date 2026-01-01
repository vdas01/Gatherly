import { Info } from '../Event/Info';
import './Header.css';

export function Header({heading,subHeading}){
    return (
        <>
         <div id="heading_container">
                <h2 id="header_heading">{heading}</h2>
                <h5 id="header_sub_heading">{subHeading}</h5>
                {/* <div id="info_box">
<Info loc={'rkl'} date={'12.22'} participants={'150'}/>
                </div> */}
            </div>
        </>
    )
}