package org.example;

import org.example.model.Actor;
import org.example.model.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Actor.class).addAnnotatedClass(Movie.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        // Здесь мы уже используем try-with-resources (раньше был обычный try-catch-finally)
        try (sessionFactory) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Movie movie = new Movie("Pulp fiction", 1994);
            Actor actor1 = new Actor("Harvey Keitel", 81);
            Actor actor2 = new Actor("Samuel L. Jackson", 72);

            // Array.asList() изменяемый, но просто в него нельзя добавлять новые элементы (изменяемый но не расширяемый)
            // List.of() полностью не изменяемый список
            movie.setActors(new ArrayList<>(List.of(actor1, actor2)));

            actor1.setMovies(new ArrayList<>(Collections.singletonList(movie)));
            actor2.setMovies(new ArrayList<>(Collections.singletonList(movie)));

            session.save(movie);

            session.save(actor1);
            session.save(actor2);

            session.getTransaction().commit();
        }
    }
}