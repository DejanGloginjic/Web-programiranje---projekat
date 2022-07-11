var app = new Vue({
	el: '#managerCoaches',
	data: {
        loggedUser: {},
		coaches: null
	},
	mounted() {
        axios.get('rest/currentUser')
			.then((response) => {
				this.loggedUser = response.data;
                axios.get('rest/users/getTrainersForObject/' + this.loggedUser.sportObject.id )
                    .then((responsee) => {
                    this.coaches = responsee.data;
                })
        })
        
		
        
    },
	methods: {
	
	}

});