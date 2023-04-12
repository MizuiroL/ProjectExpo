package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "exhibitorNotification")
public class ExhibitorNotification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notificationId")
	private Integer notificationId;
	@ManyToOne
	@JoinColumn(name = "exhibitorId")
	private Exhibitor exhibitor;
	@Column(name = "message")
	private String message;
	
	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public Exhibitor getExhibitor() {
		return exhibitor;
	}

	public void setExhibitor(Exhibitor exhibitor) {
		this.exhibitor = exhibitor;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ExhibitorNotification [notificationId=" + notificationId + ", exhibitor=" + exhibitor + ", message="
				+ message + "]";
	}
}
