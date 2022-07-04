var app = new Vue({
	el: '#Profile',
	data: {
		newUser: {},
		error: ''
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
		}
	}
});