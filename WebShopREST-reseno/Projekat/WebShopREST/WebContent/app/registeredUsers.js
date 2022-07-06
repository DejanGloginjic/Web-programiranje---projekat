var app = new Vue({
	el: '#rUsers',
	data: {
		users: null,
		searchName: "",
		searchSurname: "",
		searchUsername: "",
		loggedUser: {}
	},
	mounted() {
		axios.get('rest/users')
			.then(response => (this.users = response.data))
		axios.get('rest/currentUser').then((response) => {
			this.loggedUser = response.data
			})
	},
	methods: {
		findUsers: function() {
			axios.get('rest/users/search', { params: { searchName: this.searchName, searchSurname: this.searchSurname, searchUsername: this.searchUsername } })
				.then(response => (this.users = response.data))
		}


	}
});