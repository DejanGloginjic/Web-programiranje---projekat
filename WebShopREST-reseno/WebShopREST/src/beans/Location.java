package beans;

public class Location {
	
	private int id;
	private double longitude;
	private double latitude;
	private String street;
	private String number;
	private String place;
	private String zipCode;
	
	public Location(int id, double longitude, double latitude, String street, String number, String place,
			String zipCode) {
		super();
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.street = street;
		this.number = number;
		this.place = place;
		this.zipCode = zipCode;
	}

	public Location() {
		super();
	}
<<<<<<< HEAD
	
	public Location(int id) {
		super();
		this.id = id;
	}
=======
>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	
}
