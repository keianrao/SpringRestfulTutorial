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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.net.URISyntaxException;

@RestController
public class OrderController {

//  Members     //  \\  //  \\  //  \\
	
private final OrderRepository repository;
private final OrderModelAssembler assembler;



//  Constructors    \\  //  \\  //  \\

OrderController(
		OrderRepository repository, OrderModelAssembler assembler) {
	this.repository = repository;
	this.assembler = assembler;
}



//  Public interface    // \\  //  \\

@GetMapping("/orders")
CollectionModel<EntityModel<Order>> getAll() {
	List<EntityModel<Order>> models =
		repository.findAll().stream()
			.map(assembler::toModel)
			.collect(Collectors.toList());
	
	return new CollectionModel<EntityModel<Order>>(
		models,
		linkTo(methodOn(OrderController.class).getAll())
			.withSelfRel()
	);
}

@GetMapping("/orders/{id}")
EntityModel<Order> getById(@PathVariable Long id) {
	Order order = repository.findById(id)
		.orElseThrow( () -> new OrderNotFoundException(id) );
	
	return assembler.toModel(order);
}


@PostMapping("/orders")
ResponseEntity<?> newOrder(@RequestBody Order order) {
	order.setStatus(Status.IN_PROGRESS);
	EntityModel<Order> model = assembler.toModel(order);
	
	return ResponseEntity
		.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
		.body(model);
}

@PutMapping("/orders/{id}")
ResponseEntity<?> newOrder(
		@RequestBody Order order, @PathVariable Long id) {
	Order updatedOrder = repository
		.findById(id).map( existingOrder -> {
			existingOrder.setDescription(order.getDescription());
			existingOrder.setStatus(order.getStatus());
			return repository.save(existingOrder);
		})
		.orElseGet( () -> {
			order.setId(id);
			return repository.save(order);
		});
	
	EntityModel<Order> model = assembler.toModel(updatedOrder);
	
	return ResponseEntity
		.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
		.body(model);
}

@DeleteMapping("orders/{id}/cancel")
ResponseEntity<?> cancel(@PathVariable Long id) {
	Order order = repository.findById(id)
		.orElseThrow( () -> new OrderNotFoundException(id) );
	
	if (order.getStatus() == Status.IN_PROGRESS) {
		order.setStatus(Status.CANCELLED);
		EntityModel<Order> model = assembler.toModel(repository.save(order));
		return ResponseEntity.ok(model);
	}
	else {
		return ResponseEntity
			.status(HttpStatus.METHOD_NOT_ALLOWED)
			.body(
				"Cannot cancel order " + order.getId() +
				" as it is not currently in progress..."
			);
		/*
		 * Tutorial suggests using Problem#create here, but
		 * there is no such class with such a method?
		 * 
		 * If you set the return type of this method to ResponseEntity<?>,
		 * you can give any response body you'd like, so I returned a plain
		 * string here.
		 *
		 * I think a more RESTful response would be to give a model of the
		 * order again, or maybe a blank model with a link back to /orders.
		 */
	}
}

@PutMapping("/orders/{id}/complete")
ResponseEntity<?> complete(@PathVariable Long id) {
	Order order = repository.findById(id)
			.orElseThrow( () -> new OrderNotFoundException(id) );
		
		if (order.getStatus() == Status.IN_PROGRESS) {
			order.setStatus(Status.COMPLETED);
			EntityModel<Order> model = assembler.toModel(repository.save(order));
			return ResponseEntity.ok(model);
		}
		else {
			return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body(
					"Cannot complete order " + order.getId() +
					" as it is not currently in progress..."
				);
		}
}

}
