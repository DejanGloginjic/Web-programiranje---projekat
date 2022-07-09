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
        acceptComment: function(comment){
			comment.status = 'Accepted';
            axios.put('rest/comments/' + comment.id, comment)
				.then(response=>{ alert('Comment succesfuly updated!')
				window.location.href = 'http://localhost:8080/WebShopREST/manageComments.html'
			})
			
			
        },
		rejectComment: function(comment){
			comment.status = 'Rejected';
            axios.put('rest/comments/' + comment.id, comment)
				.then(response=> {alert('Comment succesfuly updated!')
				window.location.href = 'http://localhost:8080/WebShopREST/manageComments.html'
       	 })
			
	}}
});