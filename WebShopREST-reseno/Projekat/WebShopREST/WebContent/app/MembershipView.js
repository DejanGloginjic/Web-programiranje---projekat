var app = new Vue({
	el: '#viewMembership',
	data: {
		memberships : [],
        loggedUser: {}
	},
	mounted() {
		axios.get('rest/currentUser')
		.then((response) => { this.loggedUser = response.data;
			membership1 = { id: 1, MembershipId: "852963", membershipType: 'Day', paymentDay: null, startDay: null, expirationDay: null, price: 2500, buyer: null, membershipStatus: 'Active' , numberOfAppointment: 5 }
			this.memberships.push(membership1);
            membership2 = { id: 2, MembershipId: "741852", membershipType: 'Month', paymentDay: null, startDay: null, expirationDay: null, price: 10000, buyer: null, membershipStatus: 'Inactive' , numberOfAppointment: 25 }
			this.memberships.push(membership2);


		})
},
	methods: {
		
	}
});