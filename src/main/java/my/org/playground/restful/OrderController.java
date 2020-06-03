package my.org.playground.restful;

import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import java.net.URISyntaxException;

@RestController
public class OrderController {

//  Members     //  \\  //  \\  //  \\
	
private final OrderRepository orderRepository;
private final OrderModelAssembler assembler;



//  Constructors    \\  //  \\  //  \\

OrderController(
		OrderRepository orderRepository, OrderModelAssembler assembler) {
	this.orderRepository = orderRepository;
	this.assembler = assembler;
}



//  Public interface    // \\  //  \\

@GetMapping("/orders")
CollectionModel<EntityModel<Order>> getAll() {
	List<EntityModel<Order>> models =
		orderRepository.findAll().stream()
			.map(assembler::toModel)
			.collect(Collectors.toList());
	
	return new CollectionModel<EntityModel<Order>>(
		models
	);
}

@GetMapping("/orders/{id}")
EntityModel<Order> getById(@PathVariable Long id) {
	Order order = orderRepository.findById(id)
		.orElseThrow( () -> new OrderNotFoundException(id) );
	
	return assembler.toModel(order);
}

@PostMapping
ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order) {
	order.setStatus(Status.IN_PROGRESS);
	Order newOrder = orderRepository.save(order);
	
	return ResponseEntity
		.created(
			linkTo(methodOn(OrderController.class).getById(order.getId()))
				.toUri()
		)
		.body(assembler.toModel(newOrder));
}

}
