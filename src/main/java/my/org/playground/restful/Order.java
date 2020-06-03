package my.org.playground.restful;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "CUSTOMER_ORDER")
public class Order {
	
private @Id @GeneratedValue Long id;

private String description;
private Status status;

Order() { }

Order(String description, Status status) {
	this.description = description;
	this.status = status;
}

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

}
