create table Users(
	id int identity(1,1) primary key,
	username varchar(15) not null
);

create table Passwords (
	id int identity(1,1) primary key,
	password varbinary(max) not null,
	user_id int foreign key references Users(id) not null
);