package beans;

import java.time.LocalDate;

import beans.Enums.MembershipStatusEnum;
import beans.Enums.MembershipTypeEnum;

public class Membership {

	private int id;
	private String MembershipId;
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
		MembershipId = membershipId;
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
		return MembershipId;
	}

	public void setMembershipId(String membershipId) {
		MembershipId = membershipId;
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

	public void setPaymentDay(LocalDate paymentDay) {
		this.paymentDay = paymentDay;
	}

	public LocalDate getStartDay() {
		return startDay;
	}

	public void setStartDay(LocalDate startDay) {
		this.startDay = startDay;
	}

	public LocalDate getExpirationDay() {
		return expirationDay;
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
		return id + ";" + MembershipId + ";" + membershipType + ";" + paymentDay + ";" + startDay + ";" + expirationDay + ";"
				+ price + ";" + buyer + ";" + membershipStatus + ";" + numberOfAppointment;
	}
	
	
	
	
}
