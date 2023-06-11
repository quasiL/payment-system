import {useStateContext} from "../contexts/ContextProvider.jsx";

export default function Account() {

    const {user} = useStateContext();

    return (
        <div id="account">
            <h2>Your Account</h2>
            <div className="card animated fadeInDown" id="about">
                <img src="https://cdns.iconmonstr.com/wp-content/releases/preview/2018/240/iconmonstr-user-circle-thin.png" alt=""/>
                <div id="user-info">
                    <p>{user.firstname}&nbsp;{user.lastname}</p>
                    <p>{user.email}</p>
                    <p>Balance: 0.00$</p>
                </div>
            </div>
        </div>
    )
}