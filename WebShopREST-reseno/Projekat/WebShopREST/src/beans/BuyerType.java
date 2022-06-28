package beans;

import beans.Enums.BuyerTypeEnum;

public class BuyerType {
	
	private int id;
	private BuyerTypeEnum buyerType;
	private double discount;
	private int points;
	
	public BuyerType(int id, BuyerTypeEnum buyerType, double discount, int points) {
		super();
		this.id = id;
		this.buyerType = buyerType;
		this.discount = discount;
		this.points = points;
	}
	
	public BuyerType(int id) {
		super();
		this.id = id;
	}

	public BuyerType() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BuyerTypeEnum getBuyerType() {
		return buyerType;
	}

	public void setBuyerType(BuyerTypeEnum buyerType) {
		this.buyerType = buyerType;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String fileLine() {
		return id + ";" + buyerType + ";" + discount + ";" + points;
	}
	
	
	
	
	
}
