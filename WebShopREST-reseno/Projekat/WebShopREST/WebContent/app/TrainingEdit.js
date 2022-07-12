var app = new Vue({
    el: '#trainingEdit',
    data: {
        newTraining: {},
        error: '',
        loggedUser: {},
        sportObject: {},
        trainers: [],
        selectedTrainer: {}
    },
    mounted() {
        axios.get('rest/trainings/getSelected')
            .then((response) => {
                this.newTraining = response.data
                axios.get('rest/users/' + this.newTraining.coachID)
                    .then((response) => {
                        this.selectedTrainer = response.data;
                    })
            })

        axios.get('rest/users/trainers')
            .then((response) => {
                this.trainers = response.data;
            })
    },
    methods: {
        trainingEdit: function(event) {
           axios.put('rest/trainings/', this.newTraining)
            .then((response) => {
                window.location.href = 'http://localhost:8080/WebShopREST/managerTrainings.html'
            })
            event.preventDefault()
        },
        uploadImage: function(event) {
            var fileData = event.target.files[0];
            this.newTraining.image= fileData.name;
    
        }
    }
});