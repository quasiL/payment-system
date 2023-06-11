import {useNavigate, Link} from "react-router-dom";
import {useRef, useState} from "react";
import {useStateContext} from "../contexts/ContextProvider.jsx";
import createAxiosInstance from "../axios-client.js";

export default function Signup() {

    const firstNameRef = useRef();
    const lastNameRef = useRef();
    const emailRef = useRef();
    const passwordRef = useRef();
    const passwordConfirmationRef = useRef();

    const [errors, setErrors] = useState(null);
    const {setUser, setToken} = useStateContext();
    const navigate = useNavigate();
    const axiosClient = createAxiosInstance(import.meta.env.VITE_API_BASE_US_URL);

    const validateInput = () => {
        if (passwordRef.current.value !== passwordConfirmationRef.current.value) {
            setErrors((errors) => ({
                ...errors,
                2: "Passwords are not the same",
            }));
        }
        if (!firstNameRef.current.value || !lastNameRef.current.value || !emailRef.current.value ||
            !passwordRef.current.value || !passwordConfirmationRef.current.value) {
            setErrors((errors) => ({
                ...errors,
                3: "All fields must be filled",
            }));
        }
    }

    const onSubmit = (ev) => {
        ev.preventDefault();
        validateInput();

        const payload = {
            firstname: firstNameRef.current.value,
            lastname: lastNameRef.current.value,
            email: emailRef.current.value,
            password: passwordRef.current.value
        }

        if (!payload.firstname || !payload.lastname || !payload.email || !payload.password || !passwordConfirmationRef.current.value ) {
            return;
        }
        if (payload.password !== passwordConfirmationRef.current.value) {
            return;
        }

        axiosClient.post('/auth/register', payload)
            .then(({data}) => {
                //setUser(data.user)
                //setToken(data.token);
                navigate('/login');
            })
            .catch(err => {
                setErrors((errors) => ({
                    ...errors,
                    1: "Server error =(",
                }));
            })


    };

    return (
        <div className="login-signup-form animated fadeInDown">
            <div className="form">
                <form onSubmit={onSubmit}>
                    <h1 className="title">Signup for Free</h1>
                    {errors &&
                        <div className="alert">
                            {Object.keys(errors).map(key => (
                                <p key={key}>{errors[key]}</p>
                            ))}
                        </div>
                    }
                    <input ref={firstNameRef} type="text" placeholder="First Name"/>
                    <input ref={lastNameRef} type="text" placeholder="Last Name"/>
                    <input ref={emailRef} type="email" placeholder="Email Address"/>
                    <input ref={passwordRef} type="password" placeholder="Password"/>
                    <input ref={passwordConfirmationRef} type="password" placeholder="Repeat Password"/>
                    <button className="btn btn-block">Signup</button>
                    <p className="message">Already registered? <Link to="/login">Sign In</Link></p>
                </form>
            </div>
        </div>
    )
}