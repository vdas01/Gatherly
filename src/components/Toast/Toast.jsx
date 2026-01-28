import { RxCross1 } from "react-icons/rx";
import './Toast.css'

export function Toast({message, onClose,className}) {
    return(
        <div id="res_error_toast">
            <span id="toast_txt">Error:- {message}</span>
            <RxCross1 id={`toast_cross_${className}`} onClick={onClose}/>
        </div>
    )
}