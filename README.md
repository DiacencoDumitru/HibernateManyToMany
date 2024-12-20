## Hibernate ManyToMany

1. Здесь мы можем не можем сказать кто владеет owning side (так как ни одна из сущностей не владеет внешним ключом), для
   этого есть отдельная таблица @JoinTable
2. @JoinTable мы можем поставить на любой сущности.
3. Здесь при работе с транзакциями мы уже используем `try-with-resources` (раньше был обычный try-catch-finally)

* Array.asList() изменяемый, но просто в него нельзя добавлять новые элементы (изменяемый но не расширяемый)
* List.of() полностью не изменяемый список

#### Для @JoinTable сопоставляем:

1. @JoinTable(name = "Actor_Movie",
2. joinColumns = @JoinColumn(name = "actor_id),
3. inverseJoinColumns = @JoinColumn(name = "movie_id))

```postgresql
CREATE TABLE Actor
(
    id   INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    age  INT CHECK (age > 0)
);

CREATE TABLE Movie
(
    id                 INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name               VARCHAR(200) NOT NULL,
    year_of_production INT CHECK (year_of_production > 1900)
);

-- Создаем связывающую таблицу (JOIN TABLE)
CREATE TABLE Actor_Movie
(
    actor_id INT REFERENCES Actor (id),
    movie_id INT REFERENCES Movie (id),
    PRIMARY KEY (actor_id, movie_id)
);
```