import axios from 'axios'
const URI ='http://localhost:8080/red/Orders' 

const get_all_orders = () =>{
		let request = axios.get(URI)
		return request.then(response => response.data)
}


export default { 
		get_all_orders: get_all_orders,
}
