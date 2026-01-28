import api from "./axios"

export const LOCATION_API = (lat,lon) => 
    `https://nominatim.openstreetmap.org/reverse?lat=${lat}&lon=${lon}&format=json`

export const LOGIN_API = (loginData) => api.get('user/login', { params: loginData });

export const SIGNUP_API = (signupData,isUpdateUser) => api.post('user', signupData , {
    params:{
        "isUpdateUser": isUpdateUser
    }
});

export const GET_USER_INFO_API = (username) => api.get('user',{
    params: {
        userName: username
    }
});

