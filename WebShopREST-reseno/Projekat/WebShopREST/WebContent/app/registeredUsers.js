var app = new Vue({
	el: '#rUsers',
	data: {
		users: null,
		searchName: "",
		searchSurname: "",
		searchUsername: "",
		loggedUser: {},
		searchedUser: []
	},
	mounted() {
		axios.get('rest/users')
			.then(response => {
				this.users = response.data;
				
				this.searchedUser = response.data;
			})
		axios.get('rest/currentUser').then((response) => {
			this.loggedUser = response.data
			})
	},
	methods: {
		findUsers: function() {
			this.searchedUser = []
			for(let user of this.users){
				if(user.name.includes(this.searchName) && user.surname.includes(this.searchSurname) && user.username.includes(this.searchUsername)){
					this.searchedUser.push(user)
				}
			}
		}


	}
});