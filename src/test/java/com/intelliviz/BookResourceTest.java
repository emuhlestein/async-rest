package com.intelliviz;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BookResourceTest extends JerseyTest {
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

    @Test
    public void testGetBook() {
        Book response = target("books").path("1").request().get(Book.class);
        assertNotNull(response);
    }

    @Test
    public void testGetBooks() {
        Collection<Book> response = target("books").request().get(new GenericType<Collection<Book>>(){});
        assertEquals(2, response.size());
    }

    @Test
    public void testDao() {
        Book response1 = target("books").path("1").request().get(Book.class);
        Book response2 = target("books").path("1").request().get(Book.class);
        assertEquals(response1.getPublished().getTime(), response2.getPublished().getTime());
    }
}
