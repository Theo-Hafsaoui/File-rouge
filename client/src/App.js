import { useState, useEffect, useCallback} from 'react'
import hash from 'object-hash';
import debounce from 'lodash.debounce';
import './index.css'
import red_java from './service/red_java'

const App = () => {

    const [orders, setOrders] = useState([])
    const [clients, setClients] = useState([])
    const [Search, setSearch] = useState('')
    const [page, setPage] = useState(0)
    const [isFiltered, setIsFiltered] = useState(false);


    const Dialog = (props) => {
        const entity = props.entity
        const [formState, setformState] = useState(false);
        const [formData, setFormData] = useState({
            recipient: '',
            suplier: '',
            name: '',
            description: ''
        });

        const handleToggleDialog = () => {
            setformState((prevIsOpen) => !prevIsOpen);
            if (!formState) {
                setFormData({
                    name: '',
                    description: ''
                });
            }
        };

        const handleFormSubmit = (event) => {
            event.preventDefault();
            switch(formData.entity) {
                case 'produit':
                    console.log('Send:', formData);
                    delete formData.entity
                    red_java.add_client(formData)
                    break;
                case 'commande':
                    console.log('Send:', formData);
                    delete formData.entity;
                    red_java.add_client(formData).catch()
                    break;
                case 'pays':
                    console.log('Send:', formData);
                    delete formData.entity;
                    red_java.add_product(formData).catch()
                    break;
                default:
                    console.log(`Err: cannot understand ${formData.entity}.`);

            }
            handleToggleDialog();
        };


        const handleInputChange = (event) => {
            const { name, value } = event.target;
            setFormData((prevFormData) => ({
                ...prevFormData,
                [name]: value
            }));
            setFormData((prevFormData) => ({
                ...prevFormData,
                "entity": entity
            }));
        };

        return (
            <div>
                <button id="add_client" onClick={handleToggleDialog}>
                    {formState ? 'Annuler' : `Ajouter ${entity}`}
                </button>
                {formState && (
                    <dialog open>
                        <form onSubmit={handleFormSubmit}>
                            <h3 id="title">Ajout {entity}</h3>
                            <label>
                                <p>Nom:</p>
                                <input
                                    type="text"
                                    name="name"
                                    value={formData.name}
                                    onChange={handleInputChange}
                                    required
                                />
                            </label>
                            <br />
                            <label>
                                <p>Description:</p>
                                <textarea
                                    name="description"
                                    value={formData.description}
                                    onChange={handleInputChange}
                                    required
                                ></textarea>
                            </label>
                            {entity === 'commande' && (
                                <div>
                                    <label>

                                        <p>Receveur:</p>
                                        <input
                                            type="text"
                                            name="recipient"
                                            value={formData.recipient}
                                            onChange={handleInputChange}
                                        />

                                    </label>

                                    <label>
                                        <p>Delivreur:</p>
                                        <input
                                            type="text"
                                            name="supplier"
                                            value={formData.suplier}
                                            onChange={handleInputChange}
                                        />

                                    </label>
                                </div>
                            )}
                            <br />
                            <div>
                                <button type="submit">Envoyer</button>
                                <button id="cancel" onClick={handleToggleDialog} >Annuler</button>
                            </div>
                        </form>
                    </dialog>
                )}
            </div>
        );
    };

    const handleOrderDeletion = (event) => {
        var order_to_remove = event.target.parentNode.childNodes
        var json_to_remove={}
        const date = order_to_remove[4].outerText
        const product = order_to_remove[3].outerText
        const recipient = order_to_remove[2].outerText
        const supplier = order_to_remove[1].outerText
        json_to_remove.Date=date
        json_to_remove.Product=product
        json_to_remove.Recipient=recipient
        json_to_remove.Supplier=supplier
        red_java.delete_order(json_to_remove)
        console.log(json_to_remove)
        setOrders(prevOrders => prevOrders.filter(order => (order.Date !== date)
            &&(order.Product !== product)
            &&(order.Recipient !== recipient)
            &&(order.supplier !== supplier)
        ));
    };


    const Order = (props) => {
        return (
            <div className="item">
                <div className="color_accent"> </div>
                <div className="suplier">{props.suplier}</div>
                <div className="recipient">{props.recipient}</div>
                <div className="product">{props.product}</div>
                <div className="date">{props.date}</div>
                <button className='delete'onClick={handleOrderDeletion}> 🗑 </button>
            </div>
        )
    }

    const ClientOption = (props) =>{
        return (
            <option value={props.country}>{props.country}</option>
        )
    }

    useEffect(() => {
        const handleScroll = debounce(() => {
            let scrollPercentage =
                (document.documentElement.scrollTop + document.body.scrollTop) /
                    (document.documentElement.scrollHeight - document.documentElement.clientHeight);
            if (scrollPercentage > 0.95) {
                red_java.get_all_orders(page)
                    .then(new_order => {
                        setOrders(prevOrders => [...prevOrders, ...new_order])
                        setPage(prevPage => prevPage + 1)
                        window.scroll({
                            top: 0,
                            behavior: "smooth",
                        })
                    })
                    .catch(error => {
                        console.error("Error fetching orders:", error)
                    });
            }
        }, 50)

        window.addEventListener("scroll", handleScroll);

        return () => {
            window.removeEventListener("scroll", handleScroll);
        };
    }, [page]);

    useEffect( () => {
        red_java.get_all_orders(page)
            .then(order => setOrders(order))
    }, [])

    useEffect( () => {
        red_java.get_all_clients()
            .then(client => setClients(client))
    }, [])

    const debouncedSearch = useCallback(
        debounce((searchTerm) => {
            setIsFiltered(true);
            setSearch(searchTerm);
        }, 50), 
        []
    );

    const handleSearchChange = (event) => {
        debouncedSearch(event.target.value);
    }

    const Header = () => {
        return (
            <div id="header">
                <div id="inner_header">
                    <h1 id="title">File Rouge</h1>
                    <input id="search_bar" key="searchbar" value={Search} onChange={handleSearchChange}/>
                </div>
            </div>
        )
    }

    const Clients = () => {
        clients.sort((contryx,contryy) => {
            const contryX = contryx.name.toUpperCase(); // ignore upper and lowercase
            const contryY = contryy.name.toUpperCase(); // ignore upper and lowercase
            if (contryX < contryY) {
                return -1;
            }
            if (contryX > contryY) {
                return 1;
            }
            return 0;
        })
        return (
            clients.map(c => <ClientOption country={c.name} />)
        )
    }

    const Control_Pannel = () => {
        return (
            <div id="control_panel">
                <div id="left_ornement" className="ornement">◈</div>
                <div >   </div>
                <div >   </div>
                <button type="button">Non-Persistant</button>
                <div > </div>
                <select id="country-select">
                    <option value="">⚑ Pays</option>
                    {Clients()}
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

                <div id="header_crud">
                    <Dialog entity="pays" />
                    <Dialog entity="commande" />
                    <Dialog entity="produit" />
                </div>

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
        if (isFiltered){
            var filteredOrders = orders.filter(p =>
                p.Product.toLowerCase().includes(Search.toLowerCase()) ||
                    p.Recipient.toLowerCase().includes(Search.toLowerCase()) ||
                    p.Suplier.toLowerCase().includes(Search.toLowerCase()) ||
                    p.Date.toLowerCase().includes(Search.toLowerCase())
            )
            return (
                filteredOrders.map(o => <Order key={o.key} suplier={o.Suplier} recipient={o.Recipient} product={o.Product} date={o.Date}/>)
            )
        }
        return (
            orders.map((o,index) => <Order data_id={`${hash(o)}_${index}`} key={`${hash(o)}_${index}`} suplier={o.Suplier} recipient={o.Recipient} product={o.Product} date={o.Date}/>)
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
