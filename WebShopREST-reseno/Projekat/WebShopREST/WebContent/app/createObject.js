var app = new Vue({
	el: '#createSOForm',
	data: {
		newSO: {},
		newLocation: {},
		error: '',
		freeManagers: null,
		haveManagers: 'true',
		selectedManager: {},
		showRegisterForm: false
	},
	mounted() {
		axios.get('rest/users/freeManagers')
			.then((response) => {
				console.log(response.data)
				this.freeManagers = response.data
			})
		this.newSO = { id: '', objectName: null, objectType: null, content: null, objectStatus: 'Close', location: null, logoPicture: null, objectMark: null, startTime: null, endTime: null };
		this.newLocation = { id: '', longitude: null, latitude: null, street: null, number: null, zipCode: null };


	},
	methods: {
		createNewSportObject: function(event) {
			this.error = ""
			if (!this.newSO.objectName || !this.newSO.objectType || !this.newSO.logoPicture || !this.newSO.startTime || !this.newSO.endTime) {
				this.error = "Fill all input fields.";
				event.preventDefault();
				return;
			}
			if (!this.newLocation.longitude || !this.newLocation.latitude || !this.newLocation.street || !this.newLocation.number || !this.newLocation.place || !this.newLocation.zipCode) {
				this.error = "Fill all input fields.";
				event.preventDefault();
				return;
			}
			if (this.selectedManager === null) {
				this.selectedManager = this.freeManagers[0]
			}

			this.newSO.location = this.newLocation
			axios.post('rest/sportobjects/', this.newSO)
				.then((response) => {
					alert('New sport object created.');
					this.newSO = response.data;
					this.selectedManager.sportObject = this.newSO;
					axios.put('rest/users/', this.selectedManager)
						.then((response) => {
							alert('Sport object added to manager')
						}).catch(() => {
							alert('This sport object already exists.USER');
							this.error = "This sport object already exists.";
							event.preventDefault();
							return;
						})
				})
				.catch(() => {
					alert('This sport object already exists.');
					this.error = "This sport object already exists.";
					event.preventDefault();
					return;
				})


			event.preventDefault();
		},
		selectManager: function(manager) {
			this.selectedManager = manager;
		},
		showForm: function() {
			this.showRegisterForm = true;
		},
		createUser: function(event) {
			if (!this.selectedManager.name || !this.selectedManager.surname || !this.selectedManager.dateOfBirth || !this.selectedManager.userGender || !this.selectedManager.username || !this.selectedManager.password) {
				this.error = "Fill all input fields.";
				event.preventDefault();
				return;
			}
			this.selectedManager.userType = 'Manager';
			axios.post('rest/users/', this.selectedManager)
				.then((response) => {
					alert('New user registered.')
					this.selectedManager = response.data
				})
				.catch(() => {
					alert('username already exists.')
					this.error = "username already exists.";
					event.preventDefault();
					return;
				})
			event.preventDefault();
		},
		uploadImage: function(event) {
			var fileData = event.target.files[0];
			this.newSO.logoPicture = fileData.name;

		}
	}
});