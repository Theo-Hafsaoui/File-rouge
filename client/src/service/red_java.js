import axios from 'axios'
const URI ='http://localhost:8080/server/api/' 

const get_all_orders = (page) =>{
		let request = axios.get(URI+"orders?page="+page)
		return request.then(response => response.data)
}

const get_all_clients = () =>{
		let request = axios.get(URI+"clients")
		return request.then(response => response.data)
}


export default { 
		get_all_orders: get_all_orders,
		get_all_clients: get_all_clients,
}
