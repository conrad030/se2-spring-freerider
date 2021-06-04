package de.freerider;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import de.freerider.model.Customer;
import de.freerider.repository.CustomerRepository;

@SpringBootApplication
public class Se2SpringFreeriderApplication {

	@EventListener(ApplicationReadyEvent.class)
	public void doWhenApplicationReady() {
		Customer customer1 = new Customer("Martin", "Aston", "a@b.com");
		customer1.setId("123hew3er23fbf");
		Customer customer2 = new Customer("Benz", "Mercedes", "a@b.com");
		customer2.setId("123hew3er23fc1");
		Customer customer3 = new Customer("Laren", "Mc", "a@b.com");
		customer3.setId("123hew3er23fc2");
		Customer customer4 = new Customer("Royce", "Rolls", "a@b.com");
		customer4.setId("123hew3er23fc3");
		Customer customer5 = new Customer("MW", "B", "a@b.com");

		CustomerRepository repository = new CustomerRepository();

		// Should be 0
		System.out.println(repository.count());

		repository.save(customer1);
		// Should be 1
		System.out.println(repository.count());

		ArrayList<Customer> entities = new ArrayList<>();
		entities.add(customer2);
		entities.add(customer3);
		entities.add(customer4);
		repository.saveAll(entities);

		// Should be 4
		System.out.println(repository.count());

		// Should be true
		System.out.println(repository.findById(customer1.getId()).get().getId().equals(customer1.getId()));

		// Should be true
		System.out.println(repository.existsById(customer1.getId()));

		// Should contain 4 values
		System.out.println(repository.findAll());

		ArrayList<String> ids = new ArrayList<>();
		ids.add(customer1.getId());
		ids.add(customer2.getId());
		ids.add("123test");
		// Should contain 2 values
		System.out.println(repository.findAllById(ids));

		repository.deleteById(customer1.getId());
		// Should be 3
		System.out.println(repository.count());

		repository.delete(customer2);
		// Should be 2
		System.out.println(repository.count());

		entities.clear();
		entities.add(customer2);
		entities.add(customer3);
		repository.deleteAll(entities);
		// Should be 1
		System.out.println(repository.count());

		repository.deleteAll();
		// Should be 0
		System.out.println(repository.count());

		repository.save(customer1);
		// Should be 1
		System.out.println(repository.count());
		repository.save(customer1);
		// Should still be 1
		System.out.println(repository.count());
		repository.save(customer1);
	}

	public static void main(String[] args) {
		SpringApplication.run(Se2SpringFreeriderApplication.class, args);
	}

}
