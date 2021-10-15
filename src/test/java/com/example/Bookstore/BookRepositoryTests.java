package com.example.Bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.Bookstore.domain.Book;
import com.example.Bookstore.domain.BookRepository;
import com.example.Bookstore.domain.Category;
import com.example.Bookstore.domain.User;
import com.example.Bookstore.domain.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookRepositoryTests {

	@Autowired
	private BookRepository repository;

	@Autowired
	UserRepository urepository;

	@Test
	public void findByAuthorShouldReturnBook() {
		List<Book> books = repository.findByAuthor("J.K. Rowling");

		assertThat(books).hasSize(1);
		assertThat(books.get(0).getTitle()).isEqualTo("Harry Potter");

	}

	@Test
	public void createNewBook() {
		Book book = new Book("Mielensäpahoittaja", "Tuomas Kyrö", "2007", "44444", 22.50, new Category("Horror"));
		repository.save(book);
		assertThat(book.getId()).isNotNull();
	}

	@Test
	public void findByUsername() {
		User user = urepository.findByUsername("user");
		assertThat(user.getUsername()).isNotNull();

	}
}
