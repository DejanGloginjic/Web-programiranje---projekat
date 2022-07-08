var app = new Vue({
	el: '#manageComments',
	data: {
		comments: null,
        so: {},
        loggedUser: {}
	},
	mounted() {
		axios.get('rest/sportobjects/getSelected')
		    .then((response) => {

			this.so = response.data;

			axios.get('rest/currentUser')
				.then(response=>{ this.loggedUser = response.data})

			axios.get('rest/comments/getOnHoldComments/' + this.so.id)
				.then((response)=> this.comments = response.data)
		})
	},
	methods: {
        
	}
});