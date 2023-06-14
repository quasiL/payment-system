import createAxiosInstance from "../axios-client.js";
import {useRef, useState} from "react";
import {useStateContext} from "../contexts/ContextProvider.jsx";

export default function Send() {

    const axiosClient = createAxiosInstance(import.meta.env.VITE_API_BASE_PPS_URL);
    const {user} = useStateContext();
    const [errors, setErrors] = useState(null);
    const accountToRef = useRef();
    const amountRef = useRef();

    const [isChecked, setIsChecked] = useState(false);
    const [selectedOption, setSelectedOption] = useState('');

    const handleCheckboxChange = (event) => {
        setIsChecked(event.target.checked);
    };

    const handleSelectChange = (event) => {
        setSelectedOption(event.target.value);
    };

    const onSubmit = (ev) => {
        ev.preventDefault();

        if (user.balance < amountRef.current.value || amountRef.current.value <= 0) {
            setErrors({2: "Wrong amount"});
            return;
        }

        const payload = {
            fromAccount: user.account,
            toAccount: accountToRef.current.value,
            amount: parseFloat(amountRef.current.value),
            internal: !isChecked,
            paymentSystem: isChecked ? selectedOption : "PayScam"
        };
        axiosClient.post('/payment/add', payload)
            .then(({data}) => {

            })
            .catch(err => {
                setErrors({1: "Server error =("});
            })
    }

    return (
        <div id="send">
            <h2>Send money</h2>
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
                        <input ref={accountToRef} type="text" placeholder="Recipient's account number"/>
                        <div id="checkbox-section">
                            <input type="checkbox" name="checkbox" id="checkbox_id" checked={isChecked} onChange={handleCheckboxChange}/>
                            <label htmlFor="checkbox_id">External Payment system</label>
                        </div>
                        {isChecked &&
                            <select id="systems" value={selectedOption} onChange={handleSelectChange}>
                                <option value="PayPal">PayPal</option>
                                <option value="Visa">Visa</option>
                                <option value="UnionPay">UnionPay</option>
                            </select>
                        }
                        <input ref={amountRef} type="text" placeholder="Amount"/>
                        <button className="btn btn-block">Send</button>
                    </form>
                </div>
            </div>
        </div>
    )
}