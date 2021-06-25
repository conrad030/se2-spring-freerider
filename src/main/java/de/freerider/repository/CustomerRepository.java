package de.freerider.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import de.freerider.model.Customer;

import org.springframework.stereotype.Component;

@Component
public class CustomerRepository implements CrudRepository<Customer, String> {
	//
	private final IDGenerator idGen = new IDGenerator("C", IDGenerator.IDTYPE.NUM, 6);
	private HashMap<String, Customer> entities;

	public CustomerRepository() {
		this.entities = new HashMap<>();
	}

	@Override
	public <S extends Customer> S save(S entity) throws IllegalArgumentException {
		if (entity != null) {
			if (entity.getId() == null || entity.getId().equals("")) {
				entity.setId(this.idGen.nextId());
				while (true) {
					Customer existingEntity = this.entities.get(entity.getId());
					if (existingEntity == null) {
						this.entities.put(entity.getId(), entity);
						return entity;
					}
				}
			} else {
				S previousEntity = (S) this.entities.get(entity.getId());
				this.entities.put(entity.getId(), entity);
				return previousEntity != null ? previousEntity : entity;
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) throws IllegalArgumentException {
		if (entities == null) {
			throw new IllegalArgumentException();
		}
		for (Customer entity: entities) {
			entity = this.save(entity);
		}
		return entities;
	}

	@Override
	public Optional<Customer> findById(String id) throws IllegalArgumentException {
		if (id != null) {
			Customer entity = this.entities.get(id);
			Optional<Customer> returnValue;
			if (entity == null) {
				returnValue = Optional.empty();
			} else {
				returnValue = Optional.of(entity);
			}
			return returnValue;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public boolean existsById(String id) throws IllegalArgumentException {
		if (this.findById(id).isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Iterable<Customer> findAll() {
		return this.entities.values();
	}

	@Override
	public Iterable<Customer> findAllById(Iterable<String> ids) throws IllegalArgumentException {
		if (ids == null) {
			throw new IllegalArgumentException();
		}
		ArrayList<Customer> returnValues = new ArrayList<>();
		for (String id: ids) {
			if (this.existsById(id)) {
				returnValues.add(this.findById(id).get());
			}
		}
		return returnValues;
	}

	@Override
	public long count() {
		return this.entities.size();
	}

	@Override
	public void deleteById(String id) throws IllegalArgumentException {
		if (id != null) {
			this.entities.remove(id);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void delete(Customer entity) {
		if (entity != null) {
			this.deleteById(entity.getId());
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void deleteAllById(Iterable<? extends String> ids) throws IllegalArgumentException {
		if (ids == null) {
			throw new IllegalArgumentException();
		}
		for (String id: ids) {
			this.deleteById(id);
		}
	}

	@Override
	public void deleteAll(Iterable<? extends Customer> entities) throws IllegalArgumentException {
		if (entities == null) {
			throw new IllegalArgumentException();
		}
		for (Customer entity: entities) {
			this.delete(entity);
		}
	}

	@Override
	public void deleteAll() {
		this.entities.clear();
	}

}
