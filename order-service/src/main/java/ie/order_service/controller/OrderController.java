package ie.order_service.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
@CrossOrigin(origins = "http://127.0.0.1:5500",exposedHeaders = "ETag")
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
		List<Order> orders = repository.findAll();
		 if (orders.isEmpty()) {
		        return orders; 
		    }

		return orders;
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
		            // .eTag(etag)
		            // .body(orderResponse);
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

		if (itemClient.getItemById(order.getItemId()) == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		};
		// if(order.getItemId() != null){

		// }
		Order savedOrder = repository.save(order);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedOrder.getOrderId()).toUri();
		return ResponseEntity.created(location).build();
	}

@PutMapping("/{id}")
public ResponseEntity<Order> editOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {

    Optional<Order> existingOrderCheck = repository.findById(id);

    if (existingOrderCheck.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    Order existingOrder = existingOrderCheck.get();

    // Update fields explicitly
    existingOrder.setOrderName(updatedOrder.getOrderName());
    existingOrder.setQuantity(updatedOrder.getQuantity());
    existingOrder.setItemId(updatedOrder.getItemId());
    existingOrder.setAddress(updatedOrder.getAddress());

    repository.save(existingOrder);

    return ResponseEntity.ok(existingOrder);
}

}
