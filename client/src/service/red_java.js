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

const add_client = (json_new_client) =>{
		let request = axios.put(URI+"clients",json_new_client)
		request.catch(response => console.log(response))
		return request.then(response => response.data)
                .catch(response => console.log("ERR:"+response))
}

const delete_order = (json_order) =>{
		let request = axios.delete(URI+"orders",json_order)
		request.catch(response => console.log(response))
		return request.then(response => response.data)
                .catch(response => console.log("ERR:"+response))
}

const add_product = (json_new_product) =>{
		let request = axios.get(URI+"clients",json_new_product)
		return request.then(response => response.data)
                .catch(response => console.log(response))
}


export default { 
		get_all_orders: get_all_orders,
		add_client: add_client,
		get_all_clients: get_all_clients,
		add_product: add_product,
		delete_order: delete_order,
}
