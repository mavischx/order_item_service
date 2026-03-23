package ie.order_service.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ie.order_service.items.Item;
import ie.order_service.items.ItemClient;
import ie.order_service.model.Order;
import ie.order_service.model.OrderResponse;
import ie.order_service.repository.OrderRepository;

@RestController
@RequestMapping("/order")
public class OrderController {

	private final OrderRepository repository;
	private ItemClient itemClient;

	public OrderController(OrderRepository repository,ItemClient itemClient) {
		super();
		this.repository = repository;
		this.itemClient = itemClient;
	}

	@GetMapping
	public List<Order> retrieveOrders() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderResponse> retrieveOneOrder(@PathVariable long id) {
		Optional<Order> order = repository.findById(id);

		if (order.isEmpty()) {
			System.out.println("Order not found");
			return ResponseEntity.notFound().build();
		} else {
			Item item = itemClient.getItemById(order.get().getItemId());
			OrderResponse orderResponse = new OrderResponse(order.get(),item);
			return ResponseEntity.ok(orderResponse);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOrder(@PathVariable long id) {
		Optional<Order> item = repository.findById(id);

		if (item.isEmpty()) {
			System.out.println("order not found");
			return ResponseEntity.notFound().build();
		} else {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteAllOrders() {
		repository.deleteAll();
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody Order order) {

		Order savedOrder = repository.save(order);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedOrder.getOrderId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Order> editOrder(@PathVariable Long id,@RequestBody Order order) {
		
		Optional<Order> checkOrder = repository.findById(id);
		if(checkOrder.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else if(id != order.getOrderId()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		order.setOrderId(id);
		repository.save(order);
		return ResponseEntity.ok(order);

	}

}
