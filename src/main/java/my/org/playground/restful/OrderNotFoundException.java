package my.org.playground.restful;

class OrderNotFoundException extends RuntimeException {

OrderNotFoundException(Long id) { 
	super("Could not find employee " + id + ".");
}

}
