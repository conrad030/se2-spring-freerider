package de.freerider;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.freerider.datamodel.Customer;
import de.freerider.datamodel.Customer.Status;

@SpringBootTest
public class CustomerTests {

	private Customer mats;
	private Customer thomas;
	
	@BeforeEach
	public void setupEach() {
		this.mats = new Customer("Hummels", "Mats", "mats@hummels.de");
		this.thomas = new Customer(null, null, null);
	}
	
	// Id-Tests
	
	@Test
	public void idNull() {
		assertEquals(this.mats.getId(), null);
	}
	
	@Test
	public void testSetId() {
		String id = "matsesId";
		this.mats.setId(id);
		assertEquals(this.mats.getId(), id);
	}
	
	@Test
	public void testSetIdOnlyOnce() {
		String id = "matsesId";
		String id2 = "matsesZweiteId";
		this.mats.setId(id);
		this.mats.setId(id2);
		assertEquals(this.mats.getId(), id);
	}
	
	@Test
	public void testResetId() {
		this.mats.setId(null);
		assertEquals(this.mats.getId(), null);
	}
	
	//Name-Tests
	
	@Test
	public void testNameInitial() {
		assertNotEquals(this.thomas.getFirstName(), null);
		assertNotEquals(this.thomas.getLastName(), null);
	}
	
	@Test
	public void testNamesSetNull() {
		this.mats.setFirstName(null);
		this.mats.setLastName(null);
		assertEquals(this.mats.getFirstName(), "");
		assertEquals(this.mats.getLastName(), "");
	}
	
	@Test
	public void testSetNames() {
		String firstName = "Michael";
		String lastName = "Ballack";
		this.mats.setFirstName(firstName);
		this.mats.setLastName(lastName);
		assertEquals(this.mats.getFirstName(), firstName);
		assertEquals(this.mats.getLastName(), lastName);
	}
	
	//Contact-Tests
	
	@Test
	public void testContactInitial() {
		assertNotEquals(this.thomas.getContact(), null);
	}
	
	@Test
	public void testContactsSetNull() {
		this.mats.setContact(null);
		assertEquals(this.mats.getContact(), "");
	}
	
	@Test
	public void testSetContact() {
		String contact = "neuer@kontakt.de";
		this.mats.setContact(contact);
		assertEquals(this.mats.getContact(), contact);
	}
	
	//Status-Tests
	
	@Test
	public void testStatusInitial() {
		assertEquals(this.mats.getStatus(), Status.New);
	}
	
	@Test
	public void testSetStatus() {
		this.mats.setStatus(Status.InRegistration);
		assertEquals(this.mats.getStatus(), Status.InRegistration);
		this.mats.setStatus(Status.Active);
		assertEquals(this.mats.getStatus(), Status.Active);
		this.mats.setStatus(Status.Suspended);
		assertEquals(this.mats.getStatus(), Status.Suspended);
		this.mats.setStatus(Status.Deleted);
		assertEquals(this.mats.getStatus(), Status.Deleted);
	}
}
