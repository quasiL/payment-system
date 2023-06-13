import {useStateContext} from "../contexts/ContextProvider.jsx";
import {useEffect, useState} from "react";
import createAxiosInstance from "../axios-client.js";

export default function Account() {

    const axiosClient = createAxiosInstance(import.meta.env.VITE_API_BASE_PPS_URL);
    const [loading, setLoading] = useState(false);
    const [balance, setBalance] = useState(0);
    const [account, setAccount] = useState(0);
    const {user, setUser} = useStateContext();

    useEffect(() => {
        getAccountInformation();
    }, [])

    const getAccountInformation = () => {
        setLoading(true)
        axiosClient.get(`/payment/user_account/id/${user.id}`, user)
            .then(({data}) => {
                setLoading(false);
                setBalance(data.balance);
                setAccount(data.account);
                let updatedUser = user;
                user.account = data.account;
                setUser(updatedUser);
            })
            .catch(() => {
                setLoading(false)
            })
    }

    return (
        <div id="account">
            <h2>Your Account</h2>
            {loading &&
                <div className="card animated fadeInDown" id="about">
                    <p>Loading...</p>
                </div>
            }
            {!loading &&
                <div className="card animated fadeInDown" id="about">
                    <img
                        src="https://cdns.iconmonstr.com/wp-content/releases/preview/2018/240/iconmonstr-user-circle-thin.png"
                        alt=""/>
                    <div id="user-info">
                        <p>{user.firstname}&nbsp;{user.lastname}</p>
                        <p>{user.email}</p>
                        <p>{account}</p>
                        <p>{balance.toFixed(2)}$</p>
                    </div>
                </div>
            }
        </div>
    )
}