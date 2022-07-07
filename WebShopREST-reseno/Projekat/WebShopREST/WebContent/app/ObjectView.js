/**
 * 
 */var app = new Vue({
	el: '#viewSO',
	data: {
		so: {},
		error: '',
		trainings: []
	},
	mounted() {
		axios.get('rest/sportobjects/getSelected')
		.then((response) => {
			this.so = response.data;
			axios.get('rest/trainings/getTrainingsForObject/' + this.so.id)
				.then((response) => {
				   this.trainings = response.data
				})


		})
},
	methods: {
		TrainingEntrance: function(training){
		}
		
	}
});