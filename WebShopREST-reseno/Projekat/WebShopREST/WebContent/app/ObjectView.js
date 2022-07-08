/**
 * 
 */var app = new Vue({
	el: '#viewSO',
	data: {
		so: {},
		error: '',
		trainings: [],
		komentar: {},
		loggedUser: {},
		isVisited: false,
		isCommented: false
	},
	mounted() {

		this.komentar = {id: '', buyerComment: null, sportObjectComment: null, comment: '', commentMark: 1, status: 'OnHold'}

		axios.get('rest/sportobjects/getSelected')
		.then((response) => {

			this.so = response.data;
			axios.get('rest/trainings/getTrainingsForObject/' + this.so.id)
				.then((response) => {
				   this.trainings = response.data
				})

			axios.get('rest/currentUser')
				.then(response=>{

					this.loggedUser = response.data;
					axios.get('rest/trainingHistory/getIsVisited', { params: { objectId: this.so.id, userId: this.loggedUser.id} })
						.then((response)=>{ this.isVisited = response.data;	})

					axios.get('rest/comments/getIsCommented', { params: { objectId: this.so.id, userId: this.loggedUser.id} })
						.then((response)=>{ this.isCommented = response.data;	})
			})
		})
},
	methods: {
		TrainingEntrance: function(training){
		},
		addComment: function(){
			this.komentar.buyerComment = this.loggedUser;
			this.komentar.sportObjectComment = this.so;
			
			axios.post('rest/comments', this.komentar)
				.then(response=>{
					alert('Comment succesfuly!');
				}).catch(() => {
					alert('Comment unsuccesfuly!')
				})
			this.isCommented = true;
		},
		
	}
});