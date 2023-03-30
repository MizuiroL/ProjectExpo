package cli;

public class ExhibitorNotification {
	private Integer notificationId;
	private Integer exhibitorId;
	private String message;
	
	
	
	public ExhibitorNotification(Integer notificationId, Integer exhibitorId, String message) {
		super();
		this.notificationId = notificationId;
		this.exhibitorId = exhibitorId;
		this.message = message;
	}
	
	public Integer getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}
	public Integer getExhibitorId() {
		return exhibitorId;
	}
	public void setExhibitorId(Integer exhibitorId) {
		this.exhibitorId = exhibitorId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ExhibitorNotification [notificationId=" + notificationId + ", message=" + message + "]";
	}
	
	
}
