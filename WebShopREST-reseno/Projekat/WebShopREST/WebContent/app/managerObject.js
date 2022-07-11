var app = new Vue({
	el: '#managerObject',
	data: {
		loggedUser: {},
		showTrainers: false,
		showBuyers: false,
		trainers: null,
		buyers: null
	},
	mounted() {

        axios.get('rest/currentUser').then(response=>(this.loggedUser = response.data))
		
		
            
	},
	methods: {
		showTrainersForm: function(){
			window.location.href = 'http://localhost:8080/WebShopREST/menagerCoaches.html';
		},
		showBuyersForm: function(){
			window.location.href = 'http://localhost:8080/WebShopREST/menagerBuyers.html';
		},
		showAllTrainings: function(){
			window.location.href = 'http://localhost:8080/WebShopREST/managerTrainings.html';
		}
	}
});