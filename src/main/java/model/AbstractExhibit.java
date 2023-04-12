
package model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractExhibit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "exhibitId")
	private Integer exhibitId;
	@ManyToOne
	@JoinColumn(name = "exhibitAreaId")
	private ExhibitArea exhibitArea;
	@ManyToOne
	@JoinColumn(name = "exhibitorId")
	private Exhibitor exhibitor;
	@Column(name = "exhibitName")
	private String exhibitName;
	@Column(name = "exhibitStartDate")
	private LocalDate exhibitStartDate;
	@Column(name = "exhibitEndDate")
	private LocalTime exhibitStartTime;
	@Column(name = "exhibitStartTime")
	private LocalTime exhibitEndTime;
	@Column(name = "exhibitEndTime")
	private LocalDate exhibitEndDate;
	
	public Integer getExhibitId() {
		return exhibitId;
	}
	public void setExhibitId(Integer exhibitId) {
		this.exhibitId = exhibitId;
	}
	public ExhibitArea getExhibitArea() {
		return exhibitArea;
	}
	public void setExhibitArea(ExhibitArea exhibitArea) {
		this.exhibitArea = exhibitArea;
	}
	public Exhibitor getExhibitor() {
		return exhibitor;
	}
	public void setExhibitor(Exhibitor exhibitor) {
		this.exhibitor = exhibitor;
	}
	public String getExhibitName() {
		return exhibitName;
	}
	public void setExhibitName(String exhibitName) {
		this.exhibitName = exhibitName;
	}
	public LocalDate getExhibitStartDate() {
		return exhibitStartDate;
	}
	public void setExhibitStartDate(LocalDate exhibitStartDate) {
		this.exhibitStartDate = exhibitStartDate;
	}
	public LocalTime getExhibitStartTime() {
		return exhibitStartTime;
	}
	public void setExhibitStartTime(LocalTime exhibitStartTime) {
		this.exhibitStartTime = exhibitStartTime;
	}
	public LocalTime getExhibitEndTime() {
		return exhibitEndTime;
	}
	public void setExhibitEndTime(LocalTime exhibitEndTime) {
		this.exhibitEndTime = exhibitEndTime;
	}
	public LocalDate getExhibitEndDate() {
		return exhibitEndDate;
	}
	public void setExhibitEndDate(LocalDate exhibitEndDate) {
		this.exhibitEndDate = exhibitEndDate;
	}
	
	

}
