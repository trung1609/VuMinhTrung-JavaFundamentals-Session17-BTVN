create table tasks
(
    id        serial primary key,
    task_name varchar(255) not null,
    status    boolean default true
);

INSERT INTO tasks (task_name, status)
VALUES ('Design database schema', true),
       ('Implement login feature', true),
       ('Write unit tests', false),
       ('Fix bug in payment module', true),
       ('Deploy application to server', false);

create or replace procedure add_task(task_name_in varchar(255), status_in boolean)
    language plpgsql
as
$$
begin
    insert into tasks (task_name, status) values (task_name_in, status_in);
end;
$$;

create or replace function list_task()
    returns table
            (
                id        int,
                task_name varchar(255),
                status    boolean
            )
as
$$
begin
    return query select * from tasks;
end;
$$ language plpgsql;


create or replace procedure update_task_status(id_in int, status_in boolean)
    language plpgsql
as
$$
begin
    update tasks set status = status_in where id = id_in;
end;
$$;

create or replace function find_task_by_id(id_in int)
    returns table
            (
                id        int,
                task_name varchar(255),
                status    boolean
            )
as
$$
begin
    return query select * from tasks t where t.id = id_in;
end;
$$ language plpgsql;

create or replace procedure delete_task(id_in int)
    language plpgsql
as
$$
begin
    delete from tasks where id = id_in;
end;
$$;


create or replace function get_task_by_name(task_name_in varchar(255))
    returns table
            (
                id        int,
                task_name varchar(255),
                status    boolean
            )
as
$$
declare
    name_search varchar(255);
begin
    name_search := concat('%', task_name_in, '%');
    return query select * from tasks t where t.task_name like name_search;
end;
$$ language plpgsql;

create or replace procedure task_statistics(out count_task_done int,
                                            out count_task_not_done int)
    language plpgsql
as
$$
begin
    SELECT COUNT(*) INTO count_task_done FROM tasks WHERE status = true;
    SELECT COUNT(*) INTO count_task_not_done FROM tasks WHERE status = false;
end;
$$;

