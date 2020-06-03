package my.org.playground.restful;

import lombok.Data;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data @Entity
class Employee {
	
//  Variables   //  \\  //  \\  //  \\
	
private @Id @GeneratedValue Long id;
private String firstName, lastName;
private String role;



//  Constructors    \\  //  \\  //  \\

Employee() { }

Employee(String firstName, String lastName, String role) {
	this.firstName = firstName; 
	this.lastName = lastName;
	this.role = role;
}



//  Public interface    //  \\  //  \\

public String getFirstName() {
	return firstName;
}

public String getLastName() {
	return lastName;
}

public String getName() {
	return firstName + " " + lastName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public void setName(String name) {
	String[] splitName = name.split(" ", 2);
	setFirstName(splitName[0]);
	if (splitName.length > 1) setLastName(splitName[1]);
}


public void setId(Long id) {
	this.id = id;
}


public void setRole(String role) {
	this.role = role;
}


public boolean equals(Object other) {
	if (!(other instanceof Order))
		return false;
	return Objects.hashCode(other) == Objects.hashCode(this);
}

public int hashCode() {
	return Objects.hash(id, firstName, lastName, role);
}

}
