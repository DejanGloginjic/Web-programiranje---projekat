var app = new Vue({
	el: '#createTraining',
	data: {
		newTraining: {},
		error: '',
		loggedUser: {},
		sportObject: {},
		trainers: [],
		selectedTrainer: {}
	},
	mounted() {
		this.newTraining = { id: '', trainingName: null, trainingType: null, sportObject: null, duration: null, coach: null, description: null, image: '' }
		axios.get('rest/currentUser')
			.then((response) => {
				this.loggedUser = response.data;

				axios.get('rest/sportobjects/' + this.loggedUser.sportObject.id)
					.then((response) => {
						this.sportObject = response.data;
						this.newTraining.sportObject = this.sportObject;
					})


			})

		axios.get('rest/users/trainers')
			.then((response) => {
				this.trainers = response.data;
			})

	},
	methods: {
		createT: function(event) {
			this.newTraining.coach = this.selectedTrainer;
			axios.post('rest/trainings/', this.newTraining)
				.then((response) => {

				})
			event.preventDefault();
		}
	}
});