package ie.order_service.items;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// @FeignClient(name = "item-service", url = "http://localhost:8081")
@FeignClient(name = "item-service", url = "${item.service.url}")
public interface ItemClient {
	@GetMapping("/item/{id}")
	Item getItemById(@PathVariable long id);

	//Course getCourseById(String courseId);

}
