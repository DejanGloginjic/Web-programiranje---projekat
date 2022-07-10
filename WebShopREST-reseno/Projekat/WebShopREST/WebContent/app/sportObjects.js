var app = new Vue({
	el: '#sportObjects',
	data: {
		sportObjects: [],
		filter: '',
		criterium: '',
		loggedUser: {},
		unsortedSportObject: []
	},
	mounted() {
		axios.get('rest/sportobjects')
			.then(response => {
				
				this.unsortedSportObject = response.data;
				for(let u of this.unsortedSportObject){
					if(u.objectStatus === 'Open'){
						this.sportObjects.push(u);
					}
				}
				for(let u of this.unsortedSportObject){
					if(u.objectStatus === 'Close'){
						this.sportObjects.push(u);
					}
				}
			})
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