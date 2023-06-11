import {Link, Navigate, Outlet} from "react-router-dom";
import {useStateContext} from "../contexts/ContextProvider.jsx";
import {BiUserCircle, BiMoneyWithdraw} from 'react-icons/bi';
import {RiLogoutBoxRFill} from 'react-icons/ri';
import {SiMoneygram} from 'react-icons/si';
import {FaMoneyCheckAlt} from 'react-icons/fa';
import {GiPayMoney} from 'react-icons/gi';

export default function DefaultLayout() {

    const {user, token, setUser, setToken} = useStateContext();

    if (!token) {
        return <Navigate to="/login"/>
    }

    const onLogout = (event) => {
        event.preventDefault();
        setUser({})
        setToken(null)
    }

    return (
        <div id="defaultLayout">
            <aside>
                <Link to="/account"><div className="icon"><FaMoneyCheckAlt/></div>Account</Link>
                <Link to="/send"><div className="icon"><GiPayMoney/></div>Send</Link>
                <Link to="/payments"><div className="icon"><BiMoneyWithdraw/></div>History</Link>
            </aside>
            <div className="content">
                <header>
                    <div className="header-items">
                        <div className="header-item"><div className="icon"><SiMoneygram/></div></div>
                        <div className="header-item"><div className="icon">PayScam</div></div>
                    </div>
                    <div className="header-items">
                        <div className="header-item">
                            <div className="icon"><BiUserCircle/></div>
                        </div>
                        <div className="header-item">
                            {user.firstname}&nbsp;{user.lastname}
                        </div>
                        <div className="header-item">
                            <a href="#" onClick={onLogout} className="btn-logout">
                                <div className="icon"><RiLogoutBoxRFill/></div>
                                Logout
                            </a>
                        </div>
                    </div>
                </header>
                <main>
                    <Outlet/>
                </main>
            </div>
        </div>
    )

}