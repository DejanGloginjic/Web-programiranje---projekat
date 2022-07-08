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
		isCommented: false,
		grade: '',
		isEmpty: false,
		buyerComments: null,
		allComments: null
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

			axios.get('rest/comments/getComments/' + this.so.id)
				.then((response)=> this.buyerComments = response.data)
			axios.get('rest/comments/getCommentsForManagerAndAdministrator/' + this.so.id)
				.then((response)=> this.allComments = response.data)
		})
},
	methods: {
		TrainingEntrance: function(training){
		},
		addComment: function(event){
			this.komentar.buyerComment = this.loggedUser;
			this.komentar.sportObjectComment = this.so;

			if(this.grade === '' || this.komentar.comment === ''){
				this.isEmpty = true
				event.preventDefault();
				return
			}
			
			axios.post('rest/comments', this.komentar)
				.then(response=>{
					alert('Comment succesfuly!');
				}).catch(() => {
					alert('Comment unsuccesfuly!')
				})

			this.isCommented = true;
		},
		manageComments: function(){
			window.location.href = 'http://localhost:8080/WebShopREST/manageComments.html';
		}
		
	}
});