import axios from "axios"

const http = axios.create({
	baseURL: process.env.VUE_APP_API,
})

export const setHeader = (accessToken: string | null) => {
	axios.defaults.headers.contentType = "application/json"
	axios.defaults.headers.Authorization = accessToken === null
		? null : `Bearer ${accessToken}`
}

export default http
