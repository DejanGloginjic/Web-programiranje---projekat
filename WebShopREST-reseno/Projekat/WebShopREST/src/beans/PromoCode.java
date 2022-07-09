package beans;

import java.time.LocalDate;

import beans.Enums.DateHelper;

public class PromoCode {

	private int id;
	private String code;
	private LocalDate startDate;
	private LocalDate endDate;
	private int numberOfUses;
	private int percentage;
	
	public PromoCode(int id, String code, LocalDate startDate, LocalDate endDate, int numberOfUses, int percentage) {
		super();
		this.id = id;
		this.code = code;
		this.startDate = startDate;
		this.endDate = endDate;
		this.numberOfUses = numberOfUses;
		this.percentage = percentage;
	}

	public PromoCode() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getNumberOfUses() {
		return numberOfUses;
	}

	public void setNumberOfUses(int numberOfUses) {
		this.numberOfUses = numberOfUses;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	
	public String fileLine() {
		return id + ";" + code + ";" + DateHelper.dateToString(startDate) + ";" + DateHelper.dateToString(endDate) + ";" + numberOfUses + ";" + percentage;
	}
	
}
