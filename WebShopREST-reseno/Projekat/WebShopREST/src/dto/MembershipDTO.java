package dto;

import java.time.LocalDate;

import beans.Membership;
import beans.Enums.DateHelper;
import beans.Enums.MembershipStatusEnum;
import beans.Enums.MembershipTypeEnum;

public class MembershipDTO {
	
		private String membershipId;
		private int id;
		private MembershipTypeEnum membershipType; 
		private LocalDate paymentDay;
		private LocalDate startDay;
		private LocalDate expirationDay;
		private double price;
		private MembershipStatusEnum membershipStatus;
		private int numberOfAppointment;
		
		public MembershipDTO() {
			
		}
		

		public MembershipDTO(Membership me) {
			this.membershipId = me.getMembershipId();
			this.id = me.getId();
			this.membershipType = me.getMembershipType();
			this.paymentDay = me.getPaymentDay();
			this.startDay = me.getStartDay();
			this.expirationDay = me.getExpirationDay();
			this.price = me.getPrice();
			this.membershipStatus = me.getMembershipStatus();
			this.numberOfAppointment = me.getNumberOfAppointment();
			
		}
		
		

		public String getMembershipId() {
			return membershipId;
		}


		public void setMembershipId(String membershipId) {
			this.membershipId = membershipId;
		}


		public MembershipTypeEnum getMembershipType() {
			return membershipType;
		}


		public void setMembershipType(MembershipTypeEnum membershipType) {
			this.membershipType = membershipType;
		}


		public String getStartDay() {
			if(startDay == null) {
				return "";
			}
			return DateHelper.dateToString(startDay);
		}

		public void setStartDay(LocalDate startDay) {
			this.startDay = startDay;
		}


		public MembershipStatusEnum getMembershipStatus() {
			return membershipStatus;
		}


		public void setMembershipStatus(MembershipStatusEnum membershipStatus) {
			this.membershipStatus = membershipStatus;
		}


		public int getId() {
			return id;
		}
		
		public void setId(int id) {
			this.id = id;
		}
		
		
		public String getPaymentDay() {
			return DateHelper.dateToString(paymentDay);
		}
		
		public void setPaymentDay(LocalDate paymentDay) {
			this.paymentDay = paymentDay;
		}
		
		public String getExpirationDay() {
			if(expirationDay == null) {
				return "";
			}
			return DateHelper.dateToString(expirationDay);
		}
		
		public void setExpirationDay(LocalDate expirationDay) {
			this.expirationDay = expirationDay;
		}
		
		public double getPrice() {
			return price;
		}
		
		public void setPrice(double price) {
			this.price = price;
		}


		public int getNumberOfAppointment() {
			return numberOfAppointment;
		}


		public void setNumberOfAppointment(int numberOfAppointment) {
			this.numberOfAppointment = numberOfAppointment;
		}
		
		
		
		
}

