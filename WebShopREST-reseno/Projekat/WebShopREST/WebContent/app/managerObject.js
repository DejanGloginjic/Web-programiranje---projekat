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
			axios.get('rest/users/getTrainers/' + this.loggedUser.sportObject.id).then(response=>(this.trainers = response.data))
			this.showTrainers = true
		},
		showBuyersForm: function(){
			axios.get('rest/users/getBuyers/' + this.loggedUser.sportObject.id).then(response=>(this.buyers = response.data))
			this.showBuyers = true
		},
		showAllTrainings: function(){
			window.location.href = 'http://localhost:8080/WebShopREST/managerTrainings.html';
		}
	}
});