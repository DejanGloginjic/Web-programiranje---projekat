var app = new Vue({
	el: '#sportObjects',
	data: {
		sportObjects: null,
		filter: ""
	},
	mounted() {
		axios.get('rest/sportobjects')
			.then(response => (this.sportObjects = response.data))
	},
	methods: {}
});