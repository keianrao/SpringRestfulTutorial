package my.org.playground.restful;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class OrderModelAssembler 
implements RepresentationModelAssembler<Order, EntityModel<Order>> {

public EntityModel<Order> toModel(Order order) {
	EntityModel<Order> model = new EntityModel<Order>(
		order,
		linkTo(methodOn(OrderController.class).getById(order.getId()))
			.withSelfRel(),
		linkTo(methodOn(OrderController.class).getAll())
			.withRel("orders")
	);
	
	if (order.getStatus() == Status.IN_PROGRESS) {
		model.add(
			linkTo(methodOn(OrderController.class).complete(order.getId()))
				.withRel("complete"),
			linkTo(methodOn(OrderController.class).cancel(order.getId()))
				.withRel("cancel")
		);
	}
	
	return model;
}

}
