var app = new Vue({
	el: '#viewMembership',
	data: {
		memberships : [],
        loggedUser: {}
	},
	mounted() {
		axios.get('rest/currentUser')
		.then((response) => { this.loggedUser = response.data;
			membership1 = { id: 1, membershipId: "852963", membershipType: 'Day', paymentDay: null, startDay: null, expirationDay: null, price: 2500, buyer: null, membershipStatus: 'Inactive' , numberOfAppointment: 5 }
            membership2 = { id: 2, membershipId: "741852", membershipType: 'Month', paymentDay: null, startDay: null, expirationDay: null, price: 10000, buyer: null, membershipStatus: 'Inactive' , numberOfAppointment: 25 }
			this.memberships.push(membership1);
			this.memberships.push(membership2);


		})
},
	methods: {  Selected: function(membership) {
		axios.post('rest/memberships/setSelected', membership).then(response => {
			window.location.href = 'http://localhost:8080/WebShopREST/SelectedMembership.html';
		})

	}
}
});