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

@RestController
class EmployeeController {

private final EmployeeRepository repository;

//  \\  //  \\  //  \\


@PostMapping("/employees")
Employee newEmployee(@RequestBody Employee newEmployee) 
{
	return repository.save(newEmployee);	
}

@GetMapping("/employees")
CollectionModel<EntityModel<Employee>> getAll() 
{
	List<EntityModel<Employee>> models =
		repository.findAll().stream()
			.map(employee -> new EntityModel<>(
				employee,
				linkTo(
					methodOn(EmployeeController.class)
						.getById(employee.getId())
				)
				.withSelfRel(),
				linkTo(
					methodOn(EmployeeController.class)
						.getAll()
				)
				.withRel("employees")
			))
			.collect(Collectors.toList());
			
	return new CollectionModel<>(
		models,
		linkTo(methodOn(EmployeeController.class).getAll()).withSelfRel()
	);
	
}

@GetMapping("/employees/{id}")
EntityModel<Employee> getById(@PathVariable Long id)
{
	Employee employee = repository
			.findById(id)
			.orElseThrow( () -> new EmployeeNotFoundException(id) );
	
	return new EntityModel<>(
		employee,
		linkTo(methodOn(EmployeeController.class).getById(id)).withSelfRel(),
		linkTo(methodOn(EmployeeController.class).getAll()).withRel("employees")
	);
}

@PutMapping("/employees/{id}")
Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id)
{
	return 
		repository
			.findById(id).map( employee -> {
				employee.setName(newEmployee.getName());
				employee.setRole(newEmployee.getRole());
				return repository.save(employee);
			})
			.orElseGet( () -> {
				newEmployee.setId(id);
				return repository.save(newEmployee);
			});
}


@DeleteMapping("/employees/{id}")
void deleteEmployee(@PathVariable Long id)
{
	repository.deleteById(id);
}


//  \\  //  \\  //  \\

EmployeeController(EmployeeRepository repository)
{
	this.repository = repository;
}
	
}
