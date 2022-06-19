var app = new Vue({
	el: '#registrationFrom',
	data: {
		newUser: {},
		errorName: '',
		errorSurname: '',
		errorUsername: '',
		error: ''
	},
	mounted() {
		this.newUser = { id: '', name: null, surname: null, dateOfBirth: null, userGender: null, username: null, password: null }
	},
	methods:{
		createUser: function (event) {
			this.error = ""
			this.errorName = ""
			this.errorSurname = ""
			this.errorUsername = ""

			if(!this.newUser.name){
				this.errorName = "This field can not be empty!"
			}

			if(this.newUser.name && /\d/.test(this.newUser.name)){
				this.errorName = "This field can not contain numbers!"
			}

			if(!this.newUser.surname){
				this.errorSurname = "This field can not be empty!"
			}

			if(this.newUser.surname && /\d/.test(this.newUser.surname)){
				this.errorSurname = "This field can not contain numbers!"
			}

			if(this.newUser.username.length < 6){
				this.errorUsername = "Username must contain at least 6 characters!"
			}


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
