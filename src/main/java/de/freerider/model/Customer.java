package de.freerider.model;

public class Customer {

	private String id;
	private String lastName;
	private String firstName;
	private String contact;
	private Status status;

	public Customer(String lastName, String firstName, String contact) {
		this.lastName = lastName != null ? lastName : "";
		this.firstName = firstName != null ? firstName : "";
		this.contact = contact != null ? contact : "";
		this.id = null;
		this.status = Status.New;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (this.id == null || id == null) {
			this.id = id;
		}
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName != null ? lastName : "";
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName != null ? firstName : "";
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact != null ? contact : "";
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
