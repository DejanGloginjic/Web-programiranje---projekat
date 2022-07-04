var app = new Vue({
	el: '#createSOForm',
	data: {
		newSO: {},
		newLocation: {},
		freeManagers: null,
		haveManagers: 'true',
		selectedManager: null,
		newManager: {},
		newManagerExist: false,
		showRegisterForm: false,
		errorName: '',
		errorSurname: '',
		errorUsername: '',
		error: ''
	},
	mounted() {
		axios.get('rest/users/freeManagers')
			.then((response) => {
				console.log(response.data)
				this.freeManagers = response.data
			})
		this.newSO = { id: '', objectName: null, objectType: null, content: null, objectStatus: 'Close', location: {}, logoPicture: null, objectMark: null, startTime: null, endTime: null };
		this.newLocation = { id: '', longitude: '', latitude: '', street: '', number: '', place: '', zipCode: '' };
		//this.newManager = {id: '', username: '', password: '', name: '', surname: '', userGender: null, dateOfBirth: null, userType: 'Manager', trainingHistory: null, membership: null, sportObject: null, visitedObject: null, points: 0, buyerType: null}


	},
	methods: {
		createNewSportObject: function(event) {
			this.error = ""
			if (!this.newSO.objectName || !this.newSO.objectType || !this.newSO.logoPicture) {
				this.error = "Fill all input fields.";
				event.preventDefault();
				return;
			}
			if (!this.newLocation.longitude || !this.newLocation.latitude || !this.newLocation.street || !this.newLocation.number || !this.newLocation.place || !this.newLocation.zipCode) {
				this.error = "Fill all input fields.";
				event.preventDefault();
				return;
			}
			if(this.selectedManager === null){
			   this.selectedManager = this.freeManagers[0]
		   }

		   axios.post('rest/locations/', this.newLocation)
				.then((response) => {
					this.newLocation = response.data;
					alert('New location created.')
				})
				.catch(() => {
					alert('This location already exists.')
					this.error = "This location already exists.";
					event.preventDefault();
					return;
				})

			this.newSO.location = this.newLocation
			axios.post('rest/sportobjects/', this.newSO)
				.then((response) => {
					this.newSO = response.data;
					alert('New sport object created.')
				})
				.catch(() => {
					alert('This sport object already exists.')
					this.error = "This sport object already exists.";
					event.preventDefault();
					return;
				})

			this.newManager.sportObject = this.newSO;
			axios.put('rest/users/' + this.newManager.id, this.newManager)
				.then((response) => {
					alert('Manager updated succesfuly')
				}).catch(() => {
					alert('Manager is not updated');
					event.preventDefault();
					return;
				})
			event.preventDefault();
		},
		selectManager: function(manager) {
			this.newManager = manager;
		},
		showForm: function(){
		   this.showRegisterForm = true;
	   },
	   createUser: function (event) {
			this.newManager.userType = 'Manager'
			axios.post('rest/users/', this.newManager)
				.then((response) => {
					this.newManager = response.data;
					alert('Manager created succesfuly!')
				}).catch(() => {
					alert('This username already exists.')
					event.preventDefault();
					return;
				})
			this.newManagerExist = true;
			this.showRegisterForm = false;
			event.preventDefault();
		}
	}
});