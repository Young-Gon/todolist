import axios from 'axios';

const http = axios.create({
  baseURL: process.env.VUE_APP_API,
});

export const setHeader = (access_token) => {
  http.defaults.headers.contentType='application/json';
  http.defaults.headers.Authorization = access_token === null
    ? null : `Bearer ${access_token}`;
};

export default http;

