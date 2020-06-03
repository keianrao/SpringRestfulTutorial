package my.org.playground.restful;

import lombok.Data;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Data @Entity
@Table(name = "CUSTOMER_ORDER")
public class Order {

//  Variables   //  \\  //  \\  //  \\
	
private @Id @GeneratedValue Long id;

private String description;
private Status status;



//  Constructors    \\  //  \\  //  \\

Order() { }

Order(String description, Status status) {
	this.description = description;
	this.status = status;
}



//  Public interface    //  \\  //  \\

public Long getId() {
	return id;
}

public String getDescription() {
	return description;
}

public Status getStatus() {
	return status;
}

public void setId(Long id) {
	this.id = id;
}

public void setDescription(String description) {
	this.description = description;
}

public void setStatus(Status status) {
	this.status = status;
}


public boolean equals(Object other) {
	if (!(other instanceof Order))
		return false;
	return Objects.hashCode(other) == Objects.hashCode(this);
}

public int hashCode() {
	return Objects.hash(id, description, status);
}

}
