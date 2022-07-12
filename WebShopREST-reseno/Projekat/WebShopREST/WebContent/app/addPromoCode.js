var app = new Vue({
	el: '#addPromoCode',
	data: {
		loggedUser: {},
        newPromoCode: {}

	},
	mounted() {
		axios.get('rest/currentUser')
			.then((response) => {
				this.loggedUser = response.data;
			})
        
        this.newPromoCode = { id: '', code: '', startDate: null, endDate: null, numberOfUses: '', percentage: ''}
	},
	methods: {
        addPromoCode: function(){
            axios.post('rest/promoCodes', this.newPromoCode)
			    .then((response) => {
				    alert('Promo code succesfuly created!')
					window.location.href = 'http://localhost:8080/WebShopREST/sportObjects.html'
			    })
        }
	}
});