import {useEffect, useState} from "react";
import createAxiosInstance from "../axios-client.js";
import {useStateContext} from "../contexts/ContextProvider.jsx";

let account;

const formatDate = (dateString) => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hour = String(date.getHours()).padStart(2, '0');
    const minute = String(date.getMinutes()).padStart(2, '0');
    const second = String(date.getSeconds()).padStart(2, '0');
    return `${year}-${month}-${day} ${hour}:${minute}:${second}`;
}

const Transaction = (props) => {
    let transaction = props.transaction;
    return (
        <tr className={transaction.fromAccount.account === account ? "table-danger" : "table-success"}>
            <td>{transaction.id}</td>
            <td>{formatDate(transaction.paymentDate)}</td>
            <td>{transaction.fromAccount.account === account ? transaction.toAccount.account : account}</td>
            <td>{transaction.amount}</td>
        </tr>
    );
};

export default function Payments() {

    const [loading, setLoading] = useState(false);
    const [transactions, setTransactions] = useState([]);
    const [filteredTransactions, setFilteredTransactions] = useState([]);
    const [filter, setFilter] = useState('');
    const axiosClient = createAxiosInstance(import.meta.env.VITE_API_BASE_PPS_URL);
    const {user} = useStateContext();
    account = user.account;

    useEffect(() => {
        getPayments();
    }, [])

    useEffect(() => {
        const filteredData = transactions.filter(transaction => {
            return transaction.toAccount.account.toLowerCase().includes(filter.toLowerCase());
        });
        setFilteredTransactions(filteredData);
    }, [transactions, filter]);

    const handleFilter = (filter, event) => {
        if (event.currentTarget && event.currentTarget.value) {
            setFilter(filter);
            setFilteredTransactions(filteredTransactions);
        } else {
            setFilter('');
            setFilteredTransactions(transactions);
        }
    };

    const getPayments = () => {
        setLoading(true);
        axiosClient.get(`/payment/user_payments/${user.account}`, user)
            .then(({data}) => {
                setLoading(false);
                setTransactions(data.sort((a, b) => a.id - b.id));
                setFilteredTransactions(data.sort((a, b) => a.id - b.id));
            })
            .catch(() => {
                setLoading(false);
            })
    }

    return (
        <div id="payments">
            <h2>Your payments</h2>
            <div className="card animated fadeInDown">
                <div id="filter">
                    <input
                        type='text'
                        onChange={event => {
                            let filter = event.currentTarget.value;
                            handleFilter(filter, event);
                        }}
                        placeholder='Filter by Account'>
                    </input>
                </div>
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Date</th>
                        <th>Account</th>
                        <th>Amount</th>
                    </tr>
                    </thead>
                    {loading &&
                        <tbody>
                        <tr>
                            <td colSpan="5" className="text-center">Loading...</td>
                        </tr>
                        </tbody>
                    }
                    {!loading &&
                        <tbody>
                        {filteredTransactions.map(transaction => <Transaction key={transaction.id} transaction={transaction} />)}
                        </tbody>
                    }
                </table>
            </div>
        </div>
    )
}