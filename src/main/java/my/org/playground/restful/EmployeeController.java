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
class EmployeeController {

//  Members     //  \\  //  \\  //  \\
	
private final EmployeeRepository repository;

private final EmployeeModelAssembler assembler;



//  Constructors    \\  //  \\  //  \\

EmployeeController(
		EmployeeRepository repository, 
		EmployeeModelAssembler assembler) {
	this.repository = repository;
	this.assembler = assembler;
}



//  Public interface    //  \\  //  \\

@GetMapping("/employees")
CollectionModel<EntityModel<Employee>> getAll() {
	List<EntityModel<Employee>> models =
		repository.findAll().stream()
			.map(assembler::toModel)
			.collect(Collectors.toList());
			
	return new CollectionModel<>(
		models,
		linkTo(methodOn(EmployeeController.class).getAll()).withSelfRel()
	);
	
}

@GetMapping("/employees/{id}")
EntityModel<Employee> getById(@PathVariable Long id) {
	Employee employee = repository
			.findById(id)
			.orElseThrow( () -> new EmployeeNotFoundException(id) );
	
	return assembler.toModel(employee);
}


@PostMapping("/employees")
ResponseEntity<EntityModel<Employee>> newEmployee(@RequestBody Employee employee)
throws URISyntaxException {
	EntityModel<Employee> model = assembler.toModel(repository.save(employee));
	
	return ResponseEntity
		.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
		.body(model);
}

@PutMapping("/employees/{id}")
ResponseEntity<EntityModel<Employee>> replaceEmployee(
		@RequestBody Employee newEmployee, @PathVariable Long id) {
	Employee updatedEmployee = repository
		.findById(id).map( employee -> {
			employee.setName(newEmployee.getName());
			employee.setRole(newEmployee.getRole());
			return repository.save(employee);
		})
		.orElseGet( () -> {
			newEmployee.setId(id);
			return repository.save(newEmployee);
		});
	
	EntityModel<Employee> model = assembler.toModel(updatedEmployee);
	
	return ResponseEntity
		.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
		.body(model);
}


@DeleteMapping("/employees/{id}")
ResponseEntity<EntityModel<Employee>> deleteEmployee(@PathVariable Long id) {
	if (repository.existsById(id)) {
		repository.deleteById(id);
	}
	return ResponseEntity.noContent().build();
}

}
