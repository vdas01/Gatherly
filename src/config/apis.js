import api from "./axios"

export const LOCATION_API = (lat,lon) => 
    `https://nominatim.openstreetmap.org/reverse?lat=${lat}&lon=${lon}&format=json`

export const LOGIN_API = (loginData) => api.get('user/login', { params: loginData });

export const SIGNUP_API = (signupData,isUpdateUser) => api.post('user', signupData , {
    params:{
        "isUpdateUser": isUpdateUser
    }
});

export const GET_USER_INFO_API = () => api.get('user');

export const CREATE_EVENT_API = (eventData) => api.post('event', eventData);

export const GET_ALL_EVENTS_API = () => api.get('event');

export const GET_EVENT_DETAILS_API = (eventId) => api.get(`event/${eventId}`);

export const GET_ALL_SUBSCRIPTIONS_API = () => api.get('subscription');

