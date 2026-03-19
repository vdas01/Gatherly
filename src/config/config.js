import axios from "axios";
import { LOCATION_API } from './apis';

export const EVENT_DETAILS = [
    {
        id: 1,
        name: "Harry Potter",
        description: "Step into the world",
        date: "12 Dec, 2024",
        location: "New York, USA",
        participants: 150,
        status: "Available",
        day: "Tomorrow",
        strike_price: 999,
        real_price: 499,
        image: "path/to/harry_potter_image.jpg"
    },
    {

        id: 2,
        name: "Friends",
        description: "Step into the world 2",
        date: "12 Dec, 2025",
        location: "New JErsey",
        participants: 15,
        status: "Not available",
        day: "2 weeks later",
        strike_price: 1500,
        real_price: 500,
        image: "path/to/harry_potter_image.jpg"
    }
]


export const PASS_DETAILS = [
    {
        id: 1,
        name: "Monthly Pass",
        description: "Your social calendar sorted for a month   with unlimited access to events.",
        date: null,
        location: null,
        participants: null,
        status: "Available",
        day: "Tomorrow",
        strike_price: 999,
        real_price: 499,
        image: "path/to/harry_potter_image.jpg"
    },
    {

        id: 2,
        name: "Friends",
        description: "Step into the world 2",
        date: null,
        location: null,
        participants: null,
        status: "Not available",
        day: "2 weeks later",
        strike_price: 1500,
        real_price: 500,
        image: "path/to/harry_potter_image.jpg"
    }
]

export const GET_CURRENT_LOCATION = () => {
  return new Promise((resolve, reject) => {
    if (!navigator.geolocation) {
      resolve("Unknown location");
      return;
    }

    navigator.geolocation.getCurrentPosition(
      async (position) => {
        try {
          const lat = position.coords.latitude;
          const lon = position.coords.longitude;

          const res = await axios.get(LOCATION_API(lat, lon));
          const data = res.data;
          const userCity =
            data.address.city ||
            data.address.town ||
            data.address.village ||
            "Unknown location";

          resolve(userCity); // ✅
        } catch (err) {
          console.error(err);
          resolve("Unknown location");
        }
      },
      (error) => {
        console.error("Error obtaining location:", error);
        resolve("Unknown location");
      }
    );
  });
};


