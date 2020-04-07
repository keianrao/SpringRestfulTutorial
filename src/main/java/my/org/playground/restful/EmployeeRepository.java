package my.org.playground.restful;

import org.springframework.data.jpa.repository.JpaRepository;


interface EmployeeRepository extends JpaRepository<Employee, Long> { }
