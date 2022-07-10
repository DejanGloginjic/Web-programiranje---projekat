var app = new Vue({
	el: '#sportObjects',
	data: {
		sportObjects: [],
		loggedUser: {},
		searchedSportObjects: [],
		searchName: "",
		searchType: "",
		searchCity: "",
		searchMinGrade: "",
		searchMaxGrade: "",
		searchTypeFilter: "",
		searchOpenFilter: false,
		unsortedSportObject: []
	},
	mounted() {
		axios.get('rest/sportobjects')
			.then(response => {
				this.sportObjects = response.data
				this.unsortedSportObject = response.data;
				for(let u of this.unsortedSportObject){
					if(u.objectStatus === 'Open'){
						this.searchedSportObjects.push(u);
					}
				}
				for(let u of this.unsortedSportObject){
					if(u.objectStatus === 'Close'){
						this.searchedSportObjects.push(u);
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
		searchObjects: function(){
			this.searchedSportObjects = []

			if(this.searchMinGrade === '' || this.searchMaxGrade === ''){
				this.searchMinGrade = 0
				this.searchMaxGrade = 5
			}

			for(let object of this.sportObjects){
				if(object.objectName.toLowerCase().includes(this.searchName.toLowerCase()) && object.objectType.includes(this.searchType) 
					&& object.location.place.toLowerCase().includes(this.searchCity.toLowerCase()) && (object.objectMark >= this.searchMinGrade)
						&& (object.objectMark <= this.searchMaxGrade)){
					this.searchedSportObjects.push(object)
				}
			}
		},
		filterObject: function(){
			this.searchedSportObjects = []

			for(let object of this.sportObjects){
				if(object.objectType.includes(this.searchTypeFilter) && this.searchOpenFilter === true){
					if(object.objectStatus.includes('Open')){
						this.searchedSportObjects.push(object)
					}
					
				}

				if(object.objectType.includes(this.searchTypeFilter) && this.searchOpenFilter === false){
						this.searchedSportObjects.push(object)
					
				}
			}
		},
		Selected: function(sp) {
			axios.post('rest/sportobjects/setSelected',  {id: sp.id}).then(response=>{
				window.location.href = 'http://localhost:8080/WebShopREST/ObjectView.html';
			})
			
		},
		Sort: function(n) {
			//function found on site: https://www.w3schools.com/howto/howto_js_sort_table.asp
			  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
			  table = document.getElementById("tabela");
			  switching = true;
			  dir = "asc";
			  while (switching) {
			    switching = false;
			    rows = table.rows;
			    for (i = 1; i < (rows.length - 1); i++) {
			      shouldSwitch = false;
			      x = rows[i].getElementsByTagName("TD")[n];
			      y = rows[i + 1].getElementsByTagName("TD")[n];
			      if (dir == "asc") {
			        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
			          shouldSwitch = true;
			          break;
			        }
			      } else if (dir == "desc") {
			        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
			          shouldSwitch = true;
			          break;
			        }
			      }
			    }
			    if (shouldSwitch) {
			      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
			      switching = true;
			      switchcount ++;
			    } else {
			      if (switchcount == 0 && dir == "asc") {
			        dir = "desc";
			        switching = true;
			      }
			    }
			  }
		}
	}
});