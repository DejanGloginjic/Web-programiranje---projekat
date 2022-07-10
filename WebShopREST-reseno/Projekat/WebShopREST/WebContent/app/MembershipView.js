var app = new Vue({
	el: '#viewMembership',
	data: {
		memberships : [],
        loggedUser: {}
	},
	mounted() {
		axios.get('rest/currentUser')
		.then((response) => { this.loggedUser = response.data;
			membership1 = { id: 1, membershipId: "852963", membershipType: 'Day', paymentDay: null, startDay: null, expirationDay: null, price: 500, buyer: null, membershipStatus: 'Inactive' , numberOfAppointment: 1 }
            membership2 = { id: 2, membershipId: "741852", membershipType: 'Month', paymentDay: null, startDay: null, expirationDay: null, price: 3000, buyer: null, membershipStatus: 'Inactive' , numberOfAppointment: 30 }
			membership3 = { id: 3, membershipId: "357159", membershipType: 'Year', paymentDay: null, startDay: null, expirationDay: null, price: 18000, buyer: null, membershipStatus: 'Inactive' , numberOfAppointment: 365 }
			this.memberships.push(membership1);
			this.memberships.push(membership2);
			this.memberships.push(membership3);


		})
},
	methods: {  Selected: function(membership) {
		axios.post('rest/memberships/setSelected', membership).then(response => {
			window.location.href = 'http://localhost:8080/WebShopREST/SelectedMembership.html';
		})

	}
}
});