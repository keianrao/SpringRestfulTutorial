package my.org.playground.restful;

class EmployeeNotFoundException extends RuntimeException /*[1]*/ {

EmployeeNotFoundException(Long id) { 
	super("Could not find employee " + id + ".");
}

}

/*
[1] I'd rather not make this unchecked, but 
	not sure how Employee#getById will handle it. 
*/