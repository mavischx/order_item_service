package ie.item.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ie.item.model.Item;
import ie.item.repository.ItemRepository;

@RestController
@RequestMapping("/item")
@CrossOrigin(origins = "*",exposedHeaders = "ETag")
public class ItemController {

	private final ItemRepository repository;

	public ItemController(ItemRepository repository) {
		super();
		this.repository = repository;
	}

	@GetMapping
	public List<Item> retrieveItem() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Item> retrieveItemById(@PathVariable long id) {
		Optional<Item> item = repository.findById(id);

		if (item.isEmpty()) {
			System.out.println("Item not found");
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(item.get());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteItem(@PathVariable long id) {
		Optional<Item> item = repository.findById(id);

		if (item.isEmpty()) {
			System.out.println("Item not found");
			return ResponseEntity.notFound().build();
		} else {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteAllItems() {
		repository.deleteAll();
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<Item> createItem(@RequestBody Item item) {

		Item savedItem = repository.save(item);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedItem.getItemId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Item> editItem(@PathVariable Long id, @RequestBody Item item) {

		Optional<Item> checkItem = repository.findById(id);
		if (checkItem.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else if (id != item.getItemId()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} else {
			item.setItemId(id);
			repository.save(item);
			return ResponseEntity.ok(item);
		}

	}

}
