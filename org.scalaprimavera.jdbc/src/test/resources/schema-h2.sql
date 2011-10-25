create table test_bean(
  id int primary key auto_increment,
  description varchar(1024),
  create_date timestamp default current_timestamp
);