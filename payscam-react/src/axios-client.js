import axios from "axios";

const createAxiosInstance = (baseURL) => {
    const axiosInstance = axios.create({
        baseURL: `${baseURL}/api/v1`,
    });

    axiosInstance.interceptors.request.use((config) => {
        // const token = localStorage.getItem('ACCESS_TOKEN');
        // config.headers.Authorization = `Bearer ${token}`;
        return config;
    });

    axiosInstance.interceptors.response.use(
        (response) => {
            return response;
        },
        (error) => {
            try {
                const { response } = error;
                if (response.status === 401) {
                    localStorage.removeItem('ACCESS_TOKEN');
                } else {
                    // Handle other error codes
                }
            } catch (e) {
                console.log(e);
            }
        }
    );

    return axiosInstance;
};

export default createAxiosInstance;