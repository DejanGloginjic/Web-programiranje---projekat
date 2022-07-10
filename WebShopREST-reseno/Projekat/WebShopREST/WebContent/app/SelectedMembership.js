var app = new Vue({
	el: '#Membership',
	data: {
		memberShip: {},
		error: '',
		trainings: [],
		minDate: Date.now(),
		promoCodeFromFile: {},
		promoCode: ''
	},
	mounted() {
		axios.get('rest/memberships/getSelected')
			.then((response) => {
				this.memberShip = response.data;
			})
			this.minDate = new Date().toISOString().split("T")[0];
	},
	methods: {
		bookTraining: function(event) {
				axios.post('rest/memberships/', this.memberShip)
				.then((response) => {
					this.membership = response.data;
				})
	},
		applyPromoCode: function(event){
			axios.post('rest/promoCodes/' + this.promoCode)
					.then((response)=>{
						if(response.data === null){
							alert('Entered promo code is not valid!');
							event.preventDefault();
							return;
						}
			})
		}
}
});