package com.intelliviz;

import javax.ws.rs.*;
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book addBook(Book book) {
        return dao.addBook(book);
    }
}
