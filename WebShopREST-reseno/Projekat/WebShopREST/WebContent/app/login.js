var app = new Vue({
	el: '#LoginForm',
	data: {
		username: '',
        password: '',
		errorUsername: '',
		error: ''
	},
	mounted() {},
	methods:{
		loginUser: function (event) {
			axios.post('rest/login', {username:this.username, password: this.password})
			.then((response) => {
				alert('User logged in successfully.')
			}).catch(() =>{
				alert('not valid username and/or password')
				event.preventDefault();
				return;
			})
			event.preventDefault();
			return;
		}
	}
});
