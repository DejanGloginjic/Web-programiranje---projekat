package beans;

import java.time.LocalDate;

import beans.Enums.MembershipStatusEnum;
import beans.Enums.MembershipTypeEnum;

public class Membership {

	private String ID;
	private MembershipTypeEnum membershipType;
	private LocalDate paymentDay;
	private LocalDate startDay;
	private LocalDate expirationDay;
	private double price;
	private User buyer;
	private MembershipStatusEnum membershipStatus;
	private int numberOfAppointment;
	
	
}
