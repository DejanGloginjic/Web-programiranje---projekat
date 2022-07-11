var app = new Vue({
	el: '#managerBuyers',
	data: {
        loggedUser: {},
		buyers: null
	},
	mounted() { axios.get('rest/currentUser')
    .then((response) => {
        this.loggedUser = response.data;
        axios.get('rest/users/getBuyersForObject/' + this.loggedUser.sportObject.id )
            .then((responsee) => {
            this.buyers = responsee.data;
        })
})
		
        
    },
	methods: {
	
	}

});