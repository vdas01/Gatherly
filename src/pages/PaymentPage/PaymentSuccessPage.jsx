import { useSearchParams } from "react-router-dom";


export function PaymentSuccessPage() { 
   const [params] = useSearchParams();

  useEffect(() => {
    const sessionId = params.get("session_id");
    const updateOrderResult = UPDATE_ORDER_API(sessionId, "SUCCESS");
  }, []);

  return <h2>Payment Successful ✅</h2>;
}