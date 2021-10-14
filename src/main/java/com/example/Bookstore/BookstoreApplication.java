package com.example.Bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.Bookstore.domain.Book;
import com.example.Bookstore.domain.BookRepository;
import com.example.Bookstore.domain.Category;
import com.example.Bookstore.domain.CategoryRepository;
import com.example.Bookstore.domain.User;
import com.example.Bookstore.domain.UserRepository;

@SpringBootApplication
public class BookstoreApplication {
	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner bookDemo(BookRepository brepository, CategoryRepository crepository,
			UserRepository urepository) {
		return (args) -> {
			log.info("save a couple of books");
			crepository.save(new Category("Non-Fiction"));
			crepository.save(new Category("Science"));
			crepository.save(new Category("Poetry"));
			crepository.save(new Category("Fiction"));

			brepository.save(new Book("Harry Potter", "J.K. Rowling", "1997", "123456", 23.40,
					crepository.findByCategoryName("Non-Fiction").get(0)));
			brepository.save(new Book("Twilight", "Stephenie Meyer", "2005", "234567", 3.99,
					crepository.findByCategoryName("Fiction").get(0)));

			// Create users: admin/admin user/user
			User user1 = new User("user", "$2a$10$1814xC2KWOz2qpyKvUpuR.Nab1qt/Ew28q.2nHhfagDxAyVcW4ciq", "USER",
					"user@email.com");
			User user2 = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN",
					"admin@email.com");
			urepository.save(user1);
			urepository.save(user2);

			log.info("fetch all books");
			for (Book book : brepository.findAll()) {
				log.info(book.toString());
			}

		};
	}
}
