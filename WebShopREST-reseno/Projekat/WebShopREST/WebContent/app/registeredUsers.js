var app = new Vue({
	el: '#rUsers',
	data: {
		users: null,
		searchName: "",
		searchSurname: "",
		searchUsername: "",
		loggedUser: {},
		searchedUser: []
	},
	mounted() {
		axios.get('rest/users')
			.then(response => {
				this.users = response.data;
				
				this.searchedUser = response.data;
			})
		axios.get('rest/currentUser').then((response) => {
			this.loggedUser = response.data
			})
	},
	methods: {
		findUsers: function() {
			this.searchedUser = []

			for(let user of this.users){
				if(user.name.toLowerCase().includes(this.searchName.toLowerCase()) && user.surname.toLowerCase().includes(this.searchSurname.toLowerCase())
					&& user.username.toLowerCase().includes(this.searchUsername.toLowerCase()))
					{
						this.searchedUser.push(user)
				}
			}
		},
		filterUsers: function(){
			this.searchedUser = []

			for(let user of this.users){
				if(user.userType.includes(this.searchTypeFilter)){
					if(user.userType.includes('Administrator')){
						this.searchedUser.push(user)
					}
					if(user.userType.includes('Manager')){
						this.searchedUser.push(user);
					}
					if(user.userType.includes('Coach')){
						this.searchedUser.push(user);
					}
					if(user.userType.includes('Buyer')){
						this.searchedUser.push(user);
					}
					
				}

			}
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