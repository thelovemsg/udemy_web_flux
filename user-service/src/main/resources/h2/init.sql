create table usersA (
    id bigint auto_increment,
    name varchar(200),
    balance int,
    primary key (id)
);

create table user_transaction(
    id bigint auto_increment,
    user_id bigint,
    amount int,
    transaction_date timestamp,
    foreign key (user_id) references usersA(id) on delete cascade,
    primary key (id)
);

insert into usersA (
   name, balance
)
values
(
    'sam', 1000
),
(
    'mike', 400
),
(
    'jack', 400
)