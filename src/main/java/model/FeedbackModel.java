package model;

public class FeedbackModel {
	private int feedbackId;
	private int userId;
	private String fullName;
	private String message;
	private String email;

	public FeedbackModel(int feedbackId, int userId, String fullName, String message, String email) {
		super();
		this.feedbackId = feedbackId;
		this.userId = userId;
		this.fullName = fullName;
		this.message = message;
		this.email = email;
	}

	public FeedbackModel() {

	}

	public int getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
