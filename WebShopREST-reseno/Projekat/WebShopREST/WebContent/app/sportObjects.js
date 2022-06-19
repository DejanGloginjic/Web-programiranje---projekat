var app = new Vue({
	el: '#sportObjects',
	data: {
		sportObjects: null,
		filter: "",
		criterium: ""
	},
	mounted() {
		axios.get('rest/sportobjects')
			.then(response => (this.sportObjects = response.data))
	},
	methods: {
		searchObject: function(){
			axios.get('rest/sportobjects/search', { params: { searchValue: this.filter, criterion: this.criterium} } )
				.then(response => (this.sportObjects = response.data))
		}
	}
});