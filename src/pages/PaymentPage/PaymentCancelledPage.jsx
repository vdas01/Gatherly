


 const PaymentCancelledPage = () => {

    const [params] = useSearchParams();

  useEffect(() => {
    const sessionId = params.get("session_id");
    const updateOrderResult = UPDATE_ORDER_API(sessionId, "CANCELLED");
    
  }, []);

  return <h2>Payment Cancelled ❌</h2>;
};

export default PaymentCancelledPage;