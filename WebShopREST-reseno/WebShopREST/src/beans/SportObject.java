package beans;

import java.time.LocalTime;
import java.util.ArrayList;

import beans.Enums.ObjectTypeEnum;
import beans.Enums.SportObjectStatusEnum;
import beans.Enums.TrainingTypeEnum;

public class SportObject {
	
	private String objectName;
	private ObjectTypeEnum objectType;
	private ArrayList<TrainingTypeEnum> content;
	private SportObjectStatusEnum objectStatus;
	//private Location location;
	private String logoPicture;
	private double objectMark;
	private LocalTime startTime;
	private LocalTime endTime;
	
	

}
