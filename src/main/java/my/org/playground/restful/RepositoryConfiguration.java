package my.org.playground.restful;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;

@Configuration
@Slf4j
class RepositoryConfiguration {
	
@Bean
CommandLineRunner initDatabase(
		EmployeeRepository employeeRepository, OrderRepository orderRepository) {
	return new CommandLineRunner() {
		public void run(String... args) {
			employeeRepository.save(new Employee("Frodo", "Baggins", "Burglar"));
			employeeRepository.save(new Employee("Bilbo", "Baggins", "Thief"));
			employeeRepository.findAll().forEach( employee -> {
				log.info("Successfully preloaded " + employee);
			});
			
			orderRepository.save(new Order("Pinebook", Status.COMPLETED));
			orderRepository.save(new Order("U Core", Status.IN_PROGRESS));
			orderRepository.findAll().forEach( order -> {
				log.info("Successfully preloaded " + order);
			});
			/*
			 * Note: U Core is a nice tester object here, as 
			 * you can see the available operations on it.
			 * 
			 * However, if you are browsing through Firefox, all clicked links
			 * are accessed with a GET request, so it will error out. This is 
			 * expected behaviour. You have to use something like cURL,
			 * per the tutorial, and do '-X PUT' or '-X DELETE'. 
			 */
		}
	};
}

}
