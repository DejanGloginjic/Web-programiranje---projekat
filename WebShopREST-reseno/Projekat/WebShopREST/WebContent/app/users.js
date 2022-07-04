var app = new Vue({
	el: '#users',
	data: {
		users: {}
	},
	mounted() {
		axios.get('rest/users/')
		    .then((response) => {this.users = response.data})
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