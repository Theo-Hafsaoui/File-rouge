import { useState, useEffect } from 'react'
import './index.css'
import red_java from './service/red_java'

const Order = (props) => {
    return (
        <div className="item">
            <div className="color_accent"> </div>
            <div className="suplier">{props.suplier}</div>
            <div className="recipient">{props.recipient}</div>
            <div className="product">{props.product}</div>
            <div className="date">{props.date}</div>
        </div>
    )
}

const App = () => {
    const [orders, setOrders] = useState([])

    useEffect( () => {
        red_java.get_all_orders()
            .then(numbers => setOrders(numbers))
    }, [])

    const Header = () => {
        return (
            <div id="header">
                <div id="inner_header">
                    <h1 id="title">File Rouge</h1>
                    <input id="search_bar" type="search" name="q" />
                </div>
            </div>
        )
    }

    const Control_Pannel = () => {
        return (
        <div id="control_panel">
                <div id="left_ornement" className="ornement">◈</div>
                <div >   </div>

                <div >   </div>
                <button className="favorite styled" type="button">Non-Persistant</button>

                <div > </div>
                <select id="country-select">
                  <option value="">⚑ Pays</option>
                  <option value="Algerie">Algerie</option>
                  <option value="Chine">Chine</option>
                </select>

                <div >   </div>
                    <input id="year-select" type="number" min="1950" max="2022" step="1" placeholder="⌛ Date" />

                <div >   </div>

                <div id="right_ornement" className="ornement">◈</div>

            </div>
        )
    }

    const Order_Pannel = () => {
        return (
        <div id="order_panel">
                <div id="header_item">
                    <div className="suplier"> ⟠ Vendeur</div>
                    <div className="recipient">⟠ Acheteur</div>
                    <div className="product">⟠ Produit</div>
                    <div className="date">⟠ Date</div>
                </div>
                {Orders()}
            </div>
        )
    }

    const Orders = () => {
        return (
            orders.map(o => <Order suplier={o.Suplier} recipient={o.Recipient} product={o.Product} date={o.Date}/>)
        )
    }

    return (
        <>
                {Header()}
                {Control_Pannel()}
                {Order_Pannel()}
        </>
    )
}

export default App
