create table books
(
    id             serial primary key,
    title          varchar(255)                                                                 not null,
    author         varchar(255)                                                                 not null,
    published_year int check ( published_year between 1000 and extract(year from current_date)) not null,
    price          decimal(10, 2)                                                               not null
);
INSERT INTO books (title, author, published_year, price)
VALUES ('Clean Code', 'Robert C. Martin', 2008, 29.99),
       ('Effective Java', 'Joshua Bloch', 2018, 35.50),
       ('Design Patterns', 'Erich Gamma', 1994, 42.00),
       ('Refactoring', 'Martin Fowler', 2019, 38.75),
       ('The Pragmatic Programmer', 'Andrew Hunt', 1999, 33.20);

create or replace procedure check_book_exist(
    in title_in varchar(255),
    in author_in varchar(255),
    out is_Exist boolean
)
    language plpgsql
as
$$
begin
    select exists(select 1
                  from books b
                  where b.title = title_in
                    and b.author = author_in)
    into is_Exist;

end;
$$;

create or replace procedure add_book(title_in varchar(255), author_in varchar(255), published_year_in int,
                                     price_in decimal(10, 2))
    language plpgsql
as
$$
begin
    insert into books (title, author, published_year, price) values (title_in, author_in, published_year_in, price_in);
end;
$$;

create or replace function list_all_books()
    returns table
            (
                id             int,
                title          varchar(255),
                author         varchar(255),
                published_year int,
                price          decimal(10, 2)
            )
as
$$
begin
    return query select * from books;
end;
$$ language plpgsql;

create or replace procedure delete_book(id_in int)
    language plpgsql
as
$$
begin
    delete from books b where b.id = id_in ;
end;
$$;

create or replace function find_books_by_author(author_in varchar(255))
    returns table
            (
                id             int,
                title          varchar(255),
                author         varchar(255),
                published_year int,
                price          decimal(10, 2)
            )
as
$$
declare
    name_author varchar(255);
begin
    name_author := concat('%', author_in, '%');
    return query select * from books b where b.author like name_author;
end;
$$ language plpgsql;

create or replace procedure update_book(id_in int, title_in varchar(255), author_in varchar(255), published_year_in int,
                                        price_in decimal(10, 2))
    language plpgsql
as
$$
begin
    update books
    set author        = author_in,
        title=title_in,
        published_year=published_year_in,
        price=price_in
    where id = id_in;
end;
$$;

create or replace function find_book_by_id(id_in int)
    returns table
            (
                id             int,
                title          varchar(255),
                author         varchar(255),
                published_year int,
                price          decimal(10, 2)
            )
as
$$
begin
    return query select * from books b where b.id = id_in;
end;
$$ language plpgsql;

