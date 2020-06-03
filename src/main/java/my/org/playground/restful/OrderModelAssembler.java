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
	return new EntityModel(
		order
	);
}
	
}
