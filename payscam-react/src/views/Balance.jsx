import createAxiosInstance from "../axios-client.js";
import {useRef, useState} from "react";
import {useStateContext} from "../contexts/ContextProvider.jsx";

export default function Balance() {

    const axiosClient = createAxiosInstance(import.meta.env.VITE_API_BASE_PPS_URL);
    const {user} = useStateContext();
    const [errors, setErrors] = useState(null);
    const amountRef = useRef();

    const onSubmit = (ev) => {
        ev.preventDefault();

        if (amountRef.current.value <= 0) {
            setErrors({2: "Wrong amount"});
            return;
        }

        const payload = {
            account: user.account,
            amount: parseFloat(amountRef.current.value)
        };
        axiosClient.post('/payment/user_account/add', payload)
            .then(({data}) => {
                amountRef.current.value = null;
            })
            .catch(err => {
                setErrors({1: "Server error =("});
            })
    }

    return (
        <div id="send">
            <h2>Deposit funds to your balance</h2>
            <div className="card animated fadeInDown">
                <div className="form">
                    <form onSubmit={onSubmit}>
                        {errors &&
                            <div className="alert">
                                {Object.keys(errors).map(key => (
                                    <p key={key}>{errors[key]}</p>
                                ))}
                            </div>
                        }
                        <p id="current-balance">Your current balance: {user.balance}$</p>
                        <input ref={amountRef} type="text" placeholder="Amount"/>
                        <button type="submit" className="btn btn-block">Make Deposit</button>
                    </form>
                </div>
            </div>
        </div>
    )
}