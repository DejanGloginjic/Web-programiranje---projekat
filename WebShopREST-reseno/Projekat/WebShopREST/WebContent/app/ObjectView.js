/**
 * 
 */var app = new Vue({
	el: '#viewSO',
	data: {
		so: {},
		error: '',
		loggedUser: {},
		komentar: {},
		grade: ""
	},
	mounted() {
		this.komentar = {id: '', buyerComment: null, sportObjectComment: null, comment: '', commentMark: 1, commentStatus: 'OnHold'}

		axios.get('rest/sportobjects/getSelected')
			.then((response) => {this.so = response.data})

		axios.get('rest/currentUser')
			.then(response=>(this.loggedUser = response.data))
		
	},
	methods: {
		addComment: function(){
			this.komentar.buyerComment = this.loggedUser;
			this.komentar.sportObjectComment = this.so;
			
			axios.post('rest/comments', this.komentar)
				.then(response=>{
					alert('Comment succesfuly!');
				}).catch(() => {
					alert('Comment unsuccesfuly!')
				})
		},
		addGrade: function(){
			axios.post('/rest/sportobjects/calculateGrade', this.grade, this.so)
				.then(response=>{
					this.so.objectMark = response.data;
				})
		}
	}
});