package com.intelliviz;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Books endpoint.
 */
@Path("/books") // This is appended to the BASE_URI defined in Main class
public class BookResource {
    // This gets recreated for each endpoint invocation BAD-Need to inject instead of instantiate.
    //BookDao dao = new BookDao();
    @Context
    BookDao dao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Book> getBooks() {
        return dao.getBooks();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Book getBook(@PathParam("id") String id) {
        return dao.getBook(id);
    }
}
