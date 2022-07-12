var app = new Vue({
	el: '#registrationFrom',
	data: {
		newUser: {},
		loggedUser: {},
		errorName: '',
		errorSurname: '',
		errorUsername: '',
		error: ''
	},
	mounted() {
		this.newUser = { id: '', name: null, surname: null, dateOfBirth: null, userGender: null, username: null, password: null, userType: 'Buyer' }
		axios.get('rest/currentUser')
			.then(response=>(this.loggedUser = response.data))
			.catch({})
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

			if(/\d/.test(this.newUser.name) || /\d/.test(this.newUser.surname) || this.newUser.username.length < 6){
				event.preventDefault();
				return;
			}


			
			axios.post('rest/users/', this.newUser)
				.then((response) => {
					if(response.data === ""){
						alert('Username already exists!')
					}else{
						alert('User created successfully!')
						window.location.href = 'http://localhost:8080/WebShopREST/sportObjects.html'
					}
                }).catch()
			event.preventDefault();
		}
	}
});
