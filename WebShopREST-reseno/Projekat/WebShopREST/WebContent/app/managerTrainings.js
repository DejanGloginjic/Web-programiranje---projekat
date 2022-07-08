var app = new Vue({
	el: '#managerTrainings',
	data: {
        loggedUser: {},
		trainings: null
	},
	mounted() {
		axios.get('rest/currentUser')
			.then((response) => {
				this.loggedUser = response.data;
                axios.get('rest/trainings/getTrainingsForObject/' + this.loggedUser.sportObject.id )
                    .then((responsee) => {
                    this.trainings = responsee.data;
                })
        })
        
    }

});