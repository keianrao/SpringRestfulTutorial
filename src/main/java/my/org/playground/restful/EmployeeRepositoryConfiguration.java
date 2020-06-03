package my.org.playground.restful;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;

@Configuration
@Slf4j
class EmployeeRepositoryConfiguration {
	
@Bean
CommandLineRunner initDatabase(
		EmployeeRepository employeeRepository, OrderRepository orderRepository) {
	return new CommandLineRunner() {
		public void run(String... args) {
			Employee frodo = new Employee("Frodo", "Baggins", "Burglar");
			Employee bilbo = new Employee("Bilbo", "Baggins", "Thief");
			log.info("Preloading " + employeeRepository.save(frodo));
			log.info("Preloading " + employeeRepository.save(bilbo));
		}
	};
}

}
