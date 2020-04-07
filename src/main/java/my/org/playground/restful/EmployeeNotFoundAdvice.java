package my.org.playground.restful;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class EmployeeNotFoundAdvice {

@ResponseBody
@ResponseStatus(HttpStatus.NOT_FOUND)
@ExceptionHandler(EmployeeNotFoundException.class)
String handleEmployeeNotFoundException(EmployeeNotFoundException eEnf)
{
	return eEnf.getMessage();
}
	
}
