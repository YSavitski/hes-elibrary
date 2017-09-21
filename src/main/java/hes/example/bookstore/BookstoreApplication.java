package hes.example.bookstore;

import hes.example.bookstore.domain.User;
import hes.example.bookstore.domain.security.Role;
import hes.example.bookstore.domain.security.UserRole;
import hes.example.bookstore.service.UserService;
import hes.example.bookstore.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user1 = new User();
		user1.setFirstName("John");
		user1.setLastName("Adams");
		user1.setUsername("j");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("p"));
		user1.setEmail("JAdams@gmail.com");
		user1.setModified(LocalDateTime.now());
		Set<UserRole> userRoles = new HashSet<>();
		Role role1= new Role();
		role1.setRoleId(1);
		role1.setName("ROLE_USER");
		userRoles.add(new UserRole(user1, role1));

		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
		admin.setEmail("admin@gmail.com");
		admin.setModified(LocalDateTime.now());
		Set<UserRole> adminRoles = new HashSet<>();
		Role role2= new Role();
		role2.setRoleId(0);
		role2.setName("ROLE_ADMIN");
		adminRoles.add(new UserRole(admin, role2));

		userService.createUser(user1, userRoles);
		userService.createUser(admin, adminRoles);


		/*System.out.println("Created: " + userService.findByUsername(user1.getUsername()).getCreated());
		System.out.println("Modified: " + userService.findByUsername(user1.getUsername()).getModified());*/
	}
}
