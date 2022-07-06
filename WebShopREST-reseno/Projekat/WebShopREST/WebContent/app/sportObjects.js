var app = new Vue({
	el: '#sportObjects',
	data: {
		sportObjects: null,
		searchedSportObjects: [],
		searchName: "",
		searchType: "",
		searchCity: "",
		searchMinGrade: "",
		searchMaxGrade: "",
		loggedUser: {}
	},
	mounted() {
		axios.get('rest/sportobjects')
			.then(response => (
				this.sportObjects = response.data,
				this.searchedSportObjects = response.data
				))
		axios.get('rest/currentUser').then(response=>(this.loggedUser = response.data))
	},
	methods: {
		/*searchObject: function(){
			axios.get('rest/sportobjects/search', { params: { searchValue: this.filter, criterion: this.criterium} } )
				.then(response => (this.sportObjects = response.data))
		},*/
		searchObjects: function(){
			this.searchedSportObjects = []
			for(let object of this.sportObjects){
				if(object.objectName.toLowerCase().includes(this.searchName.toLowerCase()) && object.objectType.includes(this.searchType) 
					&& object.location.place.toLowerCase().includes(this.searchCity.toLowerCase())){
					this.searchedSportObjects.push(object)
				}
			}
		},
		Selected: function(sp) {
			axios.post('rest/sportobjects/setSelected',  {id: sp.id}).then(response=>{
				window.location.href = 'http://localhost:8080/WebShopREST/ObjectView.html';
			})
			
		}
	}
});