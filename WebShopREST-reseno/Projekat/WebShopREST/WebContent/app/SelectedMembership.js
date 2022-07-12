var app = new Vue({
	el: '#Membership',
	data: {
		memberShip: {},
		error: '',
		trainings: [],
		minDate: Date.now(),
		isValid: '',
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
			if(this.promoCode === ''){
				axios.post('rest/memberships/', this.memberShip)
					.then((response) => {
						this.memberShip = response.data;
						alert('Membership successfuly paid!')
						window.location.href = 'http://localhost:8080/WebShopREST/sportObjects.html'
					})
					event.preventDefault();
					return;
			}else{
				axios.get('rest/promoCodes/isValid', {params: {code: this.promoCode}})
				.then((response)=>{
					this.promoCodeFromFile = response.data
					
					if(this.promoCodeFromFile === ""){
						alert('Entered promo code is not valid!')
						event.preventDefault();
						return;
					}

					let oldPrice = this.memberShip.price
					this.memberShip.price = oldPrice - (this.promoCodeFromFile.percentage/100)*oldPrice
					axios.post('rest/memberships/', this.memberShip)
						.then((response) => {
							this.memberShip = response.data;
							alert('Entered promo code is valid! Price of membership is now: ' + this.memberShip.price + '.')
					})
					event.preventDefault();
					return;
					
				})


			}
	}
}
});