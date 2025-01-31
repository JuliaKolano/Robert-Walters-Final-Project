create table Books (
	id int identity(1,1) primary key,
	title varchar(80) not null,
	author varchar(50) not null,
	genre varchar(50) not null,
	pageCount smallint not null,
	coverUrl nvarchar(2083),
	isRead bit not null
);

create table UserBook (
	id int identity(1,1) primary key,
	user_id int foreign key references Users(id) not null,
	book_id int foreign key references Books(id) not null
);