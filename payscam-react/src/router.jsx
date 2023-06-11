import GuestLayout from "./components/GuestLayout.jsx";
import Login from "./views/Login.jsx";
import Signup from "./views/Signup.jsx";
import NotFound from "./views/NotFound.jsx";
import DefaultLayout from "./components/DefaultLayout.jsx";
import Account from "./views/Account.jsx";
import Payments from "./views/Payments.jsx";
import {createBrowserRouter, Navigate} from "react-router-dom";
import Send from "./views/Send.jsx";


const router = createBrowserRouter([
    {
        path: '/',
        element: <DefaultLayout />,
        children: [
            {
                path: '/',
                element: <Navigate to="/account" />
            },
            {
                path: '/payments',
                element: <Payments />
            },
            {
                path: '/account',
                element: <Account />
            },
            {
                path: '/send',
                element: <Send />
            }
        ]
    },
    {
        path: '/',
        element: <GuestLayout />,
        children: [
            {
                path: '/login',
                element: <Login />
            },
            {
                path: '/signup',
                element: <Signup />
            }
        ]
    },
    {
        path: '*',
        element: <NotFound />
    }
])

export default router;