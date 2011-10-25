
create table test_bean(
  id int primary key identity,
  description varchar(1024),
  create_date timestamp default current_timestamp
);