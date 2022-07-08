var app = new Vue({
	el: '#coachTrainings',
	data: {
		loggedUser: {},
		personalTrainings: null,
        viewPersonal: false,
        groupTrainings: null,
        viewGroup: false
	},
	mounted() {
		axios.get('rest/currentUser')
			.then((response) => {
				this.loggedUser = response.data;
			})
        
        
	},
	methods: {
        showPersonal: function(){
            axios.get('rest/scheduledTrainings/getPersonalTrainings', { params: { idKorisnika: this.loggedUser.id } })
			    .then((response) => {
				    this.personalTrainings = response.data;
			    })
            this.viewPersonal = true;
            this.viewGroup = false;
        },
        showGroup: function(){
            axios.get('rest/scheduledTrainings/getGroupTrainings', { params: { idKorisnika: this.loggedUser.id } })
			    .then((response) => {
				    this.groupTrainings = response.data;
			    })
            this.viewGroup = true;
            this.viewPersonal = false;
        }
	}
});