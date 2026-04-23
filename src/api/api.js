import axios from "axios";

export default axios.create({
baseURL: import.meta.env.VITE_BACKEND_URL,
});
// import axios from "axios";

// const api = axios.create({
//   baseURL: import.meta.env.VITE_BACKEND_URL,
// });

// // ✅ Attach token automatically to every request
// api.interceptors.request.use(
//   (config) => {
//     const token = localStorage.getItem("token"); // or wherever you store it

//     if (token) {
//       config.headers.Authorization = `Bearer ${token}`;
//     }

//     return config;
//   },
//   (error) => Promise.reject(error)
// );

// export default api;

// import axios from "axios";

// export const shortenUrlApi = async (url, token) => {
//   const res = await axios.post(
//     "http://localhost:8080/api/urls/shorten",
//     {
//       originalUrl: url, // ✅ MUST match backend DTO
//     },
//     {
//       headers: {
//         Authorization: `Bearer ${token}`, // ✅ important
//       },
//     }
//   );

//   return res.data;
// };