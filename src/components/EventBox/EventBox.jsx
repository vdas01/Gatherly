import "./EventBox.css";
import Card from "../../components/Event/Card";

export function EventBox({ EVENT_LIST,inputSearch }) {
  {
    console.log(EVENT_LIST);
  }
  return (
    <div id="event_box">
      {
        EVENT_LIST
          .filter(content =>
            content?.name?.toLowerCase().includes(inputSearch.trim().toLowerCase()) ||
            content?.description?.toLowerCase().includes(inputSearch.trim().toLowerCase()) ||
            content?.location?.toLowerCase().includes(inputSearch.trim().toLowerCase())
          )
          .map(card => <Card key={card.id} event={card} />)
      }
    </div>
  );
}

