package beans;

import java.time.LocalDate;

import beans.Enums.DateHelper;
import beans.Enums.MembershipStatusEnum;
import beans.Enums.MembershipTypeEnum;

public class Membership {

	private int id;
	private String membershipId;
	private MembershipTypeEnum membershipType;
	private LocalDate paymentDay;
	private LocalDate startDay;
	private LocalDate expirationDay;
	private double price;
	private User buyer;
	private MembershipStatusEnum membershipStatus;
	private int numberOfAppointment;
	
	public Membership(int id, String membershipId, MembershipTypeEnum membershipType, LocalDate paymentDay,
			LocalDate startDay, LocalDate expirationDay, double price, User buyer,
			MembershipStatusEnum membershipStatus, int numberOfAppointment) {
		super();
		this.id = id;
		this.membershipId = membershipId;
		this.membershipType = membershipType;
		this.paymentDay = paymentDay;
		this.startDay = startDay;
		this.expirationDay = expirationDay;
		this.price = price;
		this.buyer = buyer;
		this.membershipStatus = membershipStatus;
		this.numberOfAppointment = numberOfAppointment;
	}
	
	public Membership(int id) {
		super();
		this.id = id;
	}

	
	public Membership() {
		super();
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public LocalDate getPaymentDay() {
		return paymentDay;
	}
	public void setPaymentDay(String paymentDay) {
		if(paymentDay == null || paymentDay.equals("")) {
			return;
		}
		this.paymentDay = DateHelper.stringToDate(paymentDay);
	}
	
	public LocalDate getExpirationDay() {
		
		return expirationDay;
	}
	public void setExpirationDay(String expirationDay) {
		if(expirationDay == null || expirationDay.equals("")) {
			return;
		}
		this.expirationDay = DateHelper.stringToDate(expirationDay);
	}


	public LocalDate getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		if(startDay == null || startDay.equals("")) {
			return;
		}
		this.startDay = DateHelper.stringToDate(startDay);
	}


	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public MembershipStatusEnum getMembershipStatus() {
		return membershipStatus;
	}

	public void setMembershipStatus(MembershipStatusEnum membershipStatus) {
		this.membershipStatus = membershipStatus;
	}

	public int getNumberOfAppointment() {
		return numberOfAppointment;
	}

	public void setNumberOfAppointment(int numberOfAppointment) {
		this.numberOfAppointment = numberOfAppointment;
	}

	public String fileLine() {
		return id + ";" + membershipId + ";" + membershipType.ordinal() + ";" + DateHelper.dateToString(paymentDay) + ";" + DateHelper.dateToString(startDay) 
		+ ";" + DateHelper.dateToString(expirationDay) + ";" + price + ";" + buyer.getId() + ";" + membershipStatus.ordinal() + ";" + numberOfAppointment;
	}
	
	
	
	
}
