package my.org.playground.restful;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class EmployeeModelAssembler implements 
RepresentationModelAssembler<Employee, EntityModel<Employee>> {

public EntityModel<Employee> toModel(Employee employee) {
	return new EntityModel<Employee>(
		employee,
		linkTo(methodOn(EmployeeController.class).getById(employee.getId()))
			.withSelfRel(),
		linkTo(methodOn(EmployeeController.class).getAll())
			.withRel("employees")
	);
}
	
}
