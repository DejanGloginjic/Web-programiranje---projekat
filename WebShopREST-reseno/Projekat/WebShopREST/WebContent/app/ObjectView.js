/**
 * 
 */var app = new Vue({
	el: '#viewSO',
	data: {
		so: {},
		error: ''
	},
	mounted() {
		axios.get('rest/sportobjects/getSelected')
		.then((response) => {this.so = response.data})
		},
	methods: {
		
	}
});