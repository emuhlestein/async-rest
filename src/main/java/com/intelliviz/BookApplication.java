package com.intelliviz;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

public class BookApplication extends ResourceConfig {
    public BookApplication(final BookDao dao) {
        packages("com.intelliviz").
        // This is so dao can be injected into a resorce
        register(new AbstractBinder() {
            protected void configure() {
                bind(dao).to(BookDao.class);
            }
        });
    }
}
