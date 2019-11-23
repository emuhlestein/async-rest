package com.intelliviz;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BookResourceTest extends JerseyTest {
    private String book1_id;
    private String book2_id;

    protected Application configure() {
//        enable(TestProperties.LOG_TRAFFIC);
//        enable(TestProperties.DUMP_ENTITY);

        // TODO this dao object is being instantiated in more than one place.
        // TODO There are two copies of this object: one for the test and one for the app.
        // TODO It would be better to have an interface that two different classes implements.
        // TODO the one class would be used for the application. The other would be mock
        // TODO class used for testing.
        final BookDao dao = new BookDao();
        return new BookApplication(dao);
//        return new ResourceConfig().packages("com.intelliviz"). register(new AbstractBinder() {
//            protected void configure() {
//                bind(dao).to(BookDao.class);
//            }
//        });
    }

    protected Response addBook(String author, String title, Date published, String isbn) {
        Book book = new Book();
        book.setAuthor(author);
        book.setTitle(title);
        book.setPublished(published);
        book.setIsbn(isbn);
        Entity<Book> booKEntity = Entity.entity(book, MediaType.APPLICATION_JSON_TYPE);
        return target("books").request().post(booKEntity);
    }

    @Before
    public void setupBooks() {
        book1_id = addBook("author1", "title1", new Date(), "1234").readEntity(Book.class).getId();
        book2_id = addBook("author2", "title2", new Date(), "2345").readEntity(Book.class).getId();
    }

    @Test
    public void testAddBook() {
        Date thisDate = new Date();
        Response response = addBook("author", "title", thisDate, "3456");

        assertEquals(200, response.getStatus());

        Book responseBook = response.readEntity(Book.class);
        assertNotNull(responseBook.getId());
        assertEquals("title", responseBook.getTitle());
        assertEquals("author", responseBook.getAuthor());
        assertEquals(thisDate, responseBook.getPublished());
        assertEquals("3456", responseBook.getIsbn());
    }

    @Test
    public void testGetBook() {
        Book response = target("books").path(book1_id).request().get(Book.class);
        assertNotNull(response);
    }

    @Test
    public void testGetBooks() {
        Collection<Book> response = target("books").request().get(new GenericType<Collection<Book>>(){});
        assertEquals(2, response.size());
    }
}
