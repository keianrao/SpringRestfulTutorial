package my.org.playground.restful;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data @Entity
class Employee {
	
private @Id @GeneratedValue Long id;
private String firstName, lastName;
private String role;

//  \\  //  \\  //  \\


public String getName() 
{
	return firstName + " " + lastName;
}

public void setName(String name)
{
	String[] splitName = name.split(" ", 2);
	firstName = splitName[0];
	if (splitName.length > 1) lastName = splitName[1];
}


//  \\  //  \\  //  \\

Employee() { }

Employee(String firstName, String lastName, String role)
{
	this.firstName = firstName; 
	this.lastName = lastName;
	this.role = role;
}

}
