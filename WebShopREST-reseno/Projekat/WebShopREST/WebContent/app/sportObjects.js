var app = new Vue({
	el: '#sportObjects',
	data: {
		sportObjects: null
	},
	mounted() {
		axios.get('rest/sportobjects')
			.then(response => (this.sportObjects = response.data))
	},
	methods: {
		
		
		
	}
});
