var app = new Vue({
	el: '#Profile',
	data: {
		newUser: {},
		error: '',
		historyTraining: null
	},
	mounted() {
		axios.get('rest/currentUser')
			.then((response) => {
				this.newUser = response.data;
				axios.get('rest/trainingHistory/getITforUser', { params: { idKorisnika: this.newUser.id } }).
					then((response) => {
						this.historyTraining = response.data;
					})
			})
	},
	methods: {
		editUser: function(event) {
			axios.put('rest/users/', this.newUser)
				.then((response) => {
					alert('User profile edit successfully.')
				})
			event.preventDefault();
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