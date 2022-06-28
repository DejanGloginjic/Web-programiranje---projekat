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
		createUser: function (event) {
			axios.post('rest/users/', this.newUser)
				.then((response) => {
					if(response.data){
						alert('User created successfully!')
					}else{
						alert('Username already exists!')
					}
                }).catch()
			event.preventDefault();
		}
	}
});