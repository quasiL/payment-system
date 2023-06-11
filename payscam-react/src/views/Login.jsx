import {useRef, useState} from "react";
import {useStateContext} from "../contexts/ContextProvider.jsx";
import {Link} from "react-router-dom";
import createAxiosInstance from "../axios-client.js";

export default function Login() {

    const emailRef = useRef();
    const passwordRef = useRef();

    const {setUser, setToken} = useStateContext();
    const [errors, setErrors] = useState(null);
    const axiosClient = createAxiosInstance(import.meta.env.VITE_API_BASE_US_URL);

    const onSubmit = (ev) => {
        ev.preventDefault();

        const payload = {
            email: emailRef.current.value,
            password: passwordRef.current.value
        };

        axiosClient.post('/auth/authenticate', payload)
            .then(({data}) => {
                setUser(data);
                console.log(data);
                setToken(data.token);
            })
            .catch(err => {
                setErrors({1: "Incorrect email or password"});
            })
    }

    return (
        <div className="login-signup-form animated fadeInDown">
            <div className="form">
                <form onSubmit={onSubmit}>
                    <h1 className="title">Login into your account</h1>
                    {errors &&
                        <div className="alert">
                            {Object.keys(errors).map(key => (
                                <p key={key}>{errors[key]}</p>
                            ))}
                        </div>
                    }
                    <input ref={emailRef} type="email" placeholder="Email"/>
                    <input ref={passwordRef} type="password" placeholder="Password"/>
                    <button className="btn btn-block">Login</button>
                    <p className="message">
                        Not Registered? <Link to="/signup">Create an account</Link>
                    </p>
                </form>
            </div>
        </div>
    )
}