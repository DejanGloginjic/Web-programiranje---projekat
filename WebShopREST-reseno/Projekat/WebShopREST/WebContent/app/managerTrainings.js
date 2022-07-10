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
        
    },
	methods: {
		updateTraining: function(training) {
			axios.post('rest/trainings/setSelected', training)
			 .then((response) => {
				 window.location.href = 'http://localhost:8080/WebShopREST/TrainingEdit.html';
			 })
		 }
	}

});