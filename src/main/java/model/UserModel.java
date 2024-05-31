package model;

import java.io.File;
import javax.servlet.http.Part;

public class UserModel {
	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String address;
	private String userName;
	private String password;
	private int hasRole;
	private String ImageUrlFromPart;

	public UserModel(String firstName, String lastName, String userName, String phoneNumber, String address,
			String email, String password, Part imagePart) {
		if (email == null & password == null) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.userName = userName;
			this.phoneNumber = phoneNumber;
			this.address = address;
			this.ImageUrlFromPart = getImageUrl(imagePart);
		} else {
			this.firstName = firstName;
			this.lastName = lastName;
			this.userName = userName;
			this.phoneNumber = phoneNumber;
			this.address = address;
			this.email = email;
			this.password = password;
			this.ImageUrlFromPart = getImageUrl(imagePart);
		}
	}

	public UserModel() {

	}

	public int getHasRole() {
		return hasRole;
	}

	public void setHasRole(int hasRole) {
		this.hasRole = hasRole;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getuserName() {
		return userName;
	}

	public void setuserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImageUrlFromPart() {
		return ImageUrlFromPart;
	}

	public void setImageUrlFromPart(String imageUrlFromPart) {
		ImageUrlFromPart = imageUrlFromPart;
	}

	private String getImageUrl(Part imagePart) {
		String savePath = "/home/arch/eclipse-workspace/SonicePulse/src/main/webapp/resources/images";
		File fileSaveDir = new File(savePath);
		String imageUrlFromPart = null;
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}
		String contentDisp = imagePart.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				imageUrlFromPart = s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		if (imageUrlFromPart == null || imageUrlFromPart.isEmpty()) {
			imageUrlFromPart = "default.jpg";
		}
		return imageUrlFromPart;
	}

}
