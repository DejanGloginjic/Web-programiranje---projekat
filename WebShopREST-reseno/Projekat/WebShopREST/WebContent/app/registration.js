var app = new Vue({
	el: '#registrationFrom',
	data: {
		newUser: {},
		error: ''
	},
	mounted() {
		this.newUser = { id: '', name: null, surname: null, dateOfBirth: null, userGender: null, username: null, password: null }
	},
	methods: {
		createUser: function (event) {
			this.error = ""
			if (!this.newUser.name || !this.newUser.surname || !this.newUser.dateOfBirth || !this.newUser.userGender || !this.newUser.username || !this.newUser.password) {
				this.error = "You did not complete the entire form.";
				event.preventDefault();
				return;
			}


			
			axios.post('rest/users/', this.newUser)
				.then((response) => {
					alert('User created successfully')
                })
			event.preventDefault();
		}
	}
});
