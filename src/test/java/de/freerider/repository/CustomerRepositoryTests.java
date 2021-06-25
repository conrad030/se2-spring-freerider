package de.freerider.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.freerider.model.Customer;

@SpringBootTest
public class CustomerRepositoryTests {

	@Autowired
	CrudRepository<Customer, String> customerRepository;
	// two sample customers
	private Customer mats;
	private Customer thomas;

	@BeforeEach
	public void setupEach() {
		this.customerRepository = new CustomerRepository();
		this.mats = new Customer("1", "Customer", "customer@one.com");
		this.thomas = new Customer("2", "Customer", "customer@two.com");
		this.mats.setId("1");
		this.thomas.setId("2");
	}

	@Test
	public void testInitial() {
		assertEquals(this.customerRepository.count(), 0);
	}
	
	//save()-Tests:

	@Test
	public void testSaveEntities() {
		this.customerRepository.save(this.mats);
		this.customerRepository.save(this.thomas);
		assertEquals(this.customerRepository.count(), 2);
		assertEquals(this.customerRepository.findById("1").get(), this.mats);
		assertEquals(this.customerRepository.findById("2").get(), this.thomas);
	}

	@Test
	public void testSaveWithoutId() {
		this.mats.setId(null);
		assertNotNull(this.customerRepository.save(this.mats).getId());
		assertEquals(this.customerRepository.count(), 1);
	}

	@Test
	public void testSaveWithId() {
		assertEquals(this.customerRepository.save(this.mats).getId(), "1");
	}
	
	@Test
	public void testSaveNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			this.customerRepository.save(null);
		});
	}
	
	@Test
	public void TestSaveMultiple() {
		this.customerRepository.save(this.mats);
		this.customerRepository.save(this.mats);
		assertEquals(this.customerRepository.count(), 1);
	}
	
	@Test
	public void testSaveWithSameId() {
		this.thomas.setId(null);
		this.thomas.setId("1");
		this.customerRepository.save(this.mats);
		this.customerRepository.save(this.thomas);
		assertEquals(this.customerRepository.findById("1").get(), this.thomas);
		assertEquals(this.customerRepository.count(), 1);
	}
	
	//saveAll()-Tests:
	
	@Test
	public void testSaveAllEntities() {
		ArrayList<Customer> customers = new ArrayList<>();
		customers.add(this.mats);
		customers.add(this.thomas);
		this.customerRepository.saveAll(customers);
		assertEquals(this.customerRepository.count(), 2);
	}
	
	@Test
	public void testSaveAllWithoutIds() {
		ArrayList<Customer> customers = new ArrayList<>();
		this.mats.setId(null);
		this.thomas.setId(null);
		customers.add(this.mats);
		customers.add(this.thomas);
		this.customerRepository.saveAll(customers);
		assertEquals(this.customerRepository.count(), 2);
		for (Customer customer: this.customerRepository.findAll()) {
			assertNotNull(customer.getId());
		}
	}
	
	@Test
	public void testSaveAllNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			this.customerRepository.saveAll(null);
		});
	}
	
	@Test
	public void testSaveAllMultiple() {
		ArrayList<Customer> customers = new ArrayList<>();
		customers.add(this.mats);
		customers.add(this.mats);
		this.customerRepository.saveAll(customers);
		assertEquals(this.customerRepository.count(), 1);
	}
	
	//findById()-Tests:
	
	@Test
	public void testFindById() {
		this.customerRepository.save(this.mats);
		assertEquals(this.customerRepository.findById(this.mats.getId()).get(), this.mats);
	}
	
	@Test
	public void testFindByIdMultiple() {
		this.mats.setId(this.thomas.getId());
		this.customerRepository.save(this.mats);
		this.customerRepository.save(this.thomas);
		assertEquals(this.customerRepository.findById(this.thomas.getId()).get(), this.thomas);
	}
	
	@Test
	public void testFindByIdNotExist() {
		assertEquals(this.customerRepository.findById("ungueltigeId"), Optional.empty());
	}
	
	@Test
	public void testFindByIdNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			this.customerRepository.findById(null);
		});
	}
	
	//findAll()-Tests:
	
	@Test
	public void testFindAll() {
		ArrayList<Customer> customers = new ArrayList<>();
		customers.add(this.mats);
		customers.add(this.thomas);
		this.customerRepository.saveAll(customers);
		assertEquals(this.customerRepository.count(), 2);
		for (Customer customer: this.customerRepository.findAll()) {
			assertTrue(customer.equals(this.mats) || customer.equals(this.thomas));
		}
	} 
	
	@Test
	public void testFindAllEmpty() {
		assertFalse(this.customerRepository.findAll().iterator().hasNext());
	}
	
	//findAllById()-Tests:
	
	@Test
	public void testFindAllById() {
		ArrayList<Customer> customers = new ArrayList<>();
		customers.add(this.mats);
		customers.add(this.thomas);
		this.customerRepository.saveAll(customers);
		ArrayList<String> ids = new ArrayList<>();
		ids.add(this.thomas.getId());
		ids.add(this.mats.getId());
		assertEquals(this.customerRepository.count(), 2);
		for (Customer customer: this.customerRepository.findAllById(ids)) {
			assertTrue(customer.equals(this.mats) || customer.equals(this.thomas));
		}
	}
	
	@Test
	public void testFindAllByIdNotExist() {
		ArrayList<String> ids = new ArrayList<>();
		ids.add("ungueltigeId");
		ids.add("undNochEine");
		assertFalse(this.customerRepository.findAllById(ids).iterator().hasNext());
	}
	
	@Test
	public void testFindAllByIdEmptyIterable() {
		ArrayList<String> ids = new ArrayList<>();
		assertFalse(this.customerRepository.findAllById(ids).iterator().hasNext());
	}
	
	@Test
	public void testFindAllByIdNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			this.customerRepository.findAllById(null);
		});
	}
	
	//count()-Tests:
	
	@Test
	public void testCount() {
		this.customerRepository.save(this.mats);
		this.customerRepository.save(this.thomas);
		assertEquals(this.customerRepository.count(), 2);
	}
	
	@Test
	public void testCountEmpty() {
		assertEquals(this.customerRepository.count(), 0);
	}
	
	//deleteAllById()-Tests:
	
	@Test
	public void testDeleteById() {
		this.customerRepository.save(this.mats);
		assertEquals(this.customerRepository.count(), 1);
		this.customerRepository.deleteById(this.mats.getId());
		assertEquals(this.customerRepository.count(), 0);
	}
	
	@Test
	public void testDeleteByIdNotExist() {
		this.customerRepository.save(this.mats);
		this.customerRepository.save(this.thomas);
		this.customerRepository.deleteById("ungueltigeId");
		assertEquals(this.customerRepository.count(), 2);
	}
	
	@Test
	public void testDeleteByIdNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			this.customerRepository.deleteById(null);
		});
	}
	
	@Test
	public void testDeleteByIdMultiple() {
		this.customerRepository.save(this.mats);
		this.customerRepository.save(this.thomas);
		assertEquals(this.customerRepository.count(), 2);
		this.customerRepository.deleteById(this.mats.getId());
		this.customerRepository.deleteById(this.mats.getId());
		assertEquals(this.customerRepository.count(), 1);
	}
	
	//delete()-Tests:
	
	@Test
	public void testDelete() {
		this.customerRepository.save(this.mats);
		assertEquals(this.customerRepository.count(), 1);
		this.customerRepository.delete(this.mats);
		assertEquals(this.customerRepository.count(), 0);
	}
	
	@Test
	public void testDeleteNotExist() {
		this.customerRepository.save(this.mats);
		assertEquals(this.customerRepository.count(), 1);
		this.customerRepository.delete(this.thomas);
		assertEquals(this.customerRepository.count(), 1);
	}
	
	@Test
	public void testDeleteNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			this.customerRepository.delete(null);
		});
	}
	
	@Test
	public void testDeleteMultiple() {
		this.customerRepository.save(this.mats);
		this.customerRepository.save(this.thomas);
		assertEquals(this.customerRepository.count(), 2);
		this.customerRepository.delete(this.mats);
		this.customerRepository.delete(this.mats);
		assertEquals(this.customerRepository.count(), 1);
	}
	
	//deleteAllById()-Tests:
	
	@Test
	public void testDeleteAllById() {
		this.customerRepository.save(this.mats);
		this.customerRepository.save(this.thomas);
		assertEquals(this.customerRepository.count(), 2);
		ArrayList<String> ids = new ArrayList<>();
		ids.add(this.mats.getId());
		ids.add(this.thomas.getId());
		this.customerRepository.deleteAllById(ids);
		assertEquals(this.customerRepository.count(), 0);
	}
	
	@Test
	public void testDeleteAllByIdNotExist() {
		this.customerRepository.save(this.mats);
		this.customerRepository.save(this.thomas);
		assertEquals(this.customerRepository.count(), 2);
		ArrayList<String> ids = new ArrayList<>();
		ids.add("ungueltig");
		ids.add("auch");
		this.customerRepository.deleteAllById(ids);
		assertEquals(this.customerRepository.count(), 2);
	}
	
	@Test
	public void testDeleteAllByIdNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			this.customerRepository.deleteAllById(null);
		});
	}
	
	@Test
	public void testDeleteAllByIdMultiple() {
		this.customerRepository.save(this.mats);
		this.customerRepository.save(this.thomas);
		assertEquals(this.customerRepository.count(), 2);
		ArrayList<String> ids = new ArrayList<>();
		ids.add(this.mats.getId());
		ids.add(this.mats.getId());
		this.customerRepository.deleteAllById(ids);
		assertEquals(this.customerRepository.count(), 1);
	}
	
	//deleteAll()-Tests:
	
	@Test
	public void testDeleteAll() {
		this.customerRepository.save(this.mats);
		this.customerRepository.save(this.thomas);
		assertEquals(this.customerRepository.count(), 2);
		this.customerRepository.deleteAll();
		assertEquals(this.customerRepository.count(), 0);
	}
	
	@Test 
	public void testDeleteAllEmpty() {
		this.customerRepository.deleteAll();
		assertEquals(this.customerRepository.count(), 0);
	}
	
	//deleteAll(Iterable<> entities)-Tests:
	
	@Test
	public void testDeleteAllEntities() {
		ArrayList<Customer> customers = new ArrayList<>();
		customers.add(this.mats);
		customers.add(this.thomas);
		this.customerRepository.saveAll(customers);
		assertEquals(this.customerRepository.count(), 2);
		this.customerRepository.deleteAll(customers);
		assertEquals(this.customerRepository.count(), 0);
	}
	
	@Test
	public void testDeleteAllNotExist() {
		ArrayList<Customer> customers = new ArrayList<>();
		customers.add(this.mats);
		this.customerRepository.saveAll(customers);
		assertEquals(this.customerRepository.count(), 1);
		customers.clear();
		customers.add(this.thomas);
		this.customerRepository.deleteAll(customers);
		assertEquals(this.customerRepository.count(), 1);
	}
	
	@Test
	public void testDeleteAllNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			this.customerRepository.deleteAll(null);
		});
	}
	
	@Test
	public void testDeleteAllMultiple() {
		ArrayList<Customer> customers = new ArrayList<>();
		customers.add(this.mats);
		customers.add(this.thomas);
		this.customerRepository.saveAll(customers);
		assertEquals(this.customerRepository.count(), 2);
		customers.clear();
		customers.add(this.mats);
		customers.add(this.mats);
		this.customerRepository.deleteAll(customers);
		assertEquals(this.customerRepository.count(), 1);
	}
}
