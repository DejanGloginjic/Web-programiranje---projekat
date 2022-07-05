var app = new Vue({
	el: '#sportObjects',
	data: {
		sportObjects: null,
		filter: '',
		criterium: '',
		loggedUser: {}
	},
	mounted() {
		axios.get('rest/sportobjects')
			.then(response => (this.sportObjects = response.data))
		axios.get('rest/currentUser').then(response=>(this.loggedUser = response.data))
	},
	methods: {
		searchObject: function(){
			axios.get('rest/sportobjects/search', { params: { searchValue: this.filter, criterion: this.criterium} } )
				.then(response => (this.sportObjects = response.data))
		},
		Selected: function(sp) {
			axios.post('rest/sportobjects/setSelected',  {id: sp.id}).then(response=>{
				window.location.href = 'http://localhost:8080/WebShopREST/ObjectView.html';
			})
			
		}
	}
});