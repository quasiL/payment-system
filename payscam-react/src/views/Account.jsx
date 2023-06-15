import {useStateContext} from "../contexts/ContextProvider.jsx";
import {useEffect, useState, useRef} from "react";
import createAxiosInstance from "../axios-client.js";

export default function Account() {

    const avatarsUrl = import.meta.env.VITE_API_AVATARS_GENERATION;
    const axiosClient = createAxiosInstance(import.meta.env.VITE_API_BASE_PPS_URL);
    const axiosClientUser = createAxiosInstance(import.meta.env.VITE_API_BASE_US_URL);
    const [loading, setLoading] = useState(false);
    const [balance, setBalance] = useState(0);
    const [account, setAccount] = useState(0);
    const {user, setUser} = useStateContext();
    const [avatar, setAvatar] = useState(null);
    const seedRef = useRef();

    const [isOpen, setIsOpen] = useState(false);

    const handleOpenDialog = () => {
        setIsOpen(true);
    };

    const handleCloseDialog = (seed) => {
        setIsOpen(false);

        if (seed !== '') {
            const payload = {
                email: user.email,
                avatar: avatarsUrl + "?seed=" + seed
            };
            axiosClientUser.interceptors.request.use((config) => {
                const token = localStorage.getItem('ACCESS_TOKEN');
                config.headers.Authorization = `Bearer ${token}`;
                return config;
            });

            axiosClientUser.post('/auth/avatar', payload, { withCredentials: true })
                .then(({data}) => {
                    let updatedUser = user;
                    user.avatar = payload.avatar;
                    setUser(updatedUser);
                })
                .catch(err => {

                })
        }
    };

    useEffect(() => {
        getAccountInformation();
    }, [])

    const getAccountInformation = () => {
        setLoading(true);
        setAvatar(user.avatar);
        axiosClient.get(`/payment/user_account/id/${user.id}`, user)
            .then(({data}) => {
                setLoading(false);
                setBalance(data.balance);
                setAccount(data.account);
                let updatedUser = user;
                user.account = data.account;
                user.balance = data.balance;
                setUser(updatedUser);
            })
            .catch(() => {
                setLoading(false)
            })
    }

    const handleAvatarChange = (seed) => {
        setAvatar("https://api.dicebear.com/6.x/avataaars/svg?seed=" + seed);
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
                        src={user.avatar}
                        alt="User avatar"/>
                    <div id="user-info">
                        <p>{user.firstname}&nbsp;{user.lastname}</p>
                        <p>{user.email}</p>
                        <p>{account}</p>
                        <p>{balance.toFixed(2)}$</p>
                        <div>
                            <button className="btn btn-block" onClick={handleOpenDialog}>Change Avatar</button>
                            {isOpen && (
                                <dialog className="dialog-container" open={isOpen} onClose={handleCloseDialog}>
                                    <h2>Create your own avatar</h2>
                                    <img
                                        src={avatar}
                                        alt="User avatar"/>
                                    <p>Enter any sequence of characters</p>
                                    <input ref={seedRef} type="text" placeholder="Seed"/>
                                    <button className="btn btn-block" onClick={() => handleAvatarChange(seedRef.current.value)}>Generate</button>&nbsp;
                                    <button className="btn btn-block" onClick={() => handleCloseDialog(seedRef.current.value)}>Save and close</button>
                                </dialog>
                            )}
                        </div>
                    </div>
                </div>
            }
        </div>
    )
}