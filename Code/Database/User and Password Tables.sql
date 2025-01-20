create table Users(
	id int primary key,
	username varchar(15)
);

create table Passwords (
	id int primary key,
	password varchar(20),
	user_id int foreign key references Users(id)
);