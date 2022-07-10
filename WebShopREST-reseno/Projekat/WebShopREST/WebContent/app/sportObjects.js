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