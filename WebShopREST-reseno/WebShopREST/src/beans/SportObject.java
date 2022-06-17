package beans;

import java.time.LocalTime;
import java.util.ArrayList;

import beans.Enums.ObjectTypeEnum;
import beans.Enums.SportObjectStatusEnum;
import beans.Enums.TrainingTypeEnum;

public class SportObject {
	
	private int id;
	private String objectName;
	private ObjectTypeEnum objectType;
	private ArrayList<TrainingTypeEnum> content;
	private SportObjectStatusEnum objectStatus;
	private Location location;
	private String logoPicture;
	private double objectMark;
	private LocalTime startTime;
	private LocalTime endTime;
	
<<<<<<< HEAD
	public SportObject(int id, String objectName, ObjectTypeEnum objectType, ArrayList<TrainingTypeEnum> content,
			SportObjectStatusEnum objectStatus, Location location, String logoPicture, double objectMark,
			LocalTime startTime, LocalTime endTime) {
		super();
		this.id = id;
		this.objectName = objectName;
		this.objectType = objectType;
		this.content = content;
		this.objectStatus = objectStatus;
		this.location = location;
		this.logoPicture = logoPicture;
		this.objectMark = objectMark;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public SportObject(int id) {
		super();
		this.id = id;
=======
	public SportObject(int id) {
		this.id = id;
	}
	
	public SportObject(int id, String objectName, ObjectTypeEnum objectType, ArrayList<TrainingTypeEnum> content,
			SportObjectStatusEnum objectStatus, Location location, String logoPicture, double objectMark,
			LocalTime startTime, LocalTime endTime) {
		super();
		this.id = id;
		this.objectName = objectName;
		this.objectType = objectType;
		this.content = content;
		this.objectStatus = objectStatus;
		this.location = location;
		this.logoPicture = logoPicture;
		this.objectMark = objectMark;
		this.startTime = startTime;
		this.endTime = endTime;
>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb
	}

	public SportObject() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public ObjectTypeEnum getObjectType() {
		return objectType;
	}

	public void setObjectType(ObjectTypeEnum objectType) {
		this.objectType = objectType;
	}

	public ArrayList<TrainingTypeEnum> getContent() {
		return content;
	}

	public void setContent(ArrayList<TrainingTypeEnum> content) {
		this.content = content;
	}

	public SportObjectStatusEnum getObjectStatus() {
		return objectStatus;
	}

	public void setObjectStatus(SportObjectStatusEnum objectStatus) {
		this.objectStatus = objectStatus;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getLogoPicture() {
		return logoPicture;
	}

	public void setLogoPicture(String logoPicture) {
		this.logoPicture = logoPicture;
	}

	public double getObjectMark() {
		return objectMark;
	}

	public void setObjectMark(double objectMark) {
		this.objectMark = objectMark;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	
	
	
}
