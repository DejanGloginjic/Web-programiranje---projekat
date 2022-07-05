var app = new Vue({
	el: '#Profile',
	data: {
		newUser: {},
		error: '',
		historyTraining: null
	},
	mounted() {
		axios.get('rest/currentUser')
		.then((response) => {this.newUser = response.data})
		},
	methods: {
		editUser: function (event) {
			axios.put('rest/users/' + this.newUser.id, this.newUser)
				.then((response) => {
					alert('User profile edit successfully.')
				})
			event.preventDefault();
		},
		getTreninzi: function() {
			axios.get('rest/trainingHistory/getITforUser', { params: { idKorisnika: this.newUser.id } }).
				then((response) => {
					this.historyTraining = response.data;
				})
		},
	}
});