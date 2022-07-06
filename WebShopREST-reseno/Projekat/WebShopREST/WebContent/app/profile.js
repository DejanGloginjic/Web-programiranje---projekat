var app = new Vue({
	el: '#Profile',
	data: {
		newUser: {},
		error: '',
		historyTraining: null
	},
	mounted() {
		axios.get('rest/currentUser')
			.then((response) => {
				this.newUser = response.data;
				axios.get('rest/trainingHistory/getITforUser', { params: { idKorisnika: this.newUser.id } }).
					then((response) => {
						this.historyTraining = response.data;
					})
			})
	},
	methods: {
		editUser: function(event) {
			axios.put('rest/users/', this.newUser)
				.then((response) => {
					alert('User profile edit successfully.')
				})
			event.preventDefault();
		},

	}
});