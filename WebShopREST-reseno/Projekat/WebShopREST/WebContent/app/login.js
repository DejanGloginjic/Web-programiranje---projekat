var app = new Vue({
	el: '#registrationFrom',
	data: {
		username: '',
        password: '',
		errorUsername: '',
		error: ''
	},
	mounted() {},
	methods:{
		loginUser: function (event) {
			axios.post('rest/login', this.username, this.password)
				.then((response) => {
					alert('User created successfully')
                })
		}
	}
});
