create table ReadingGoals (
	id int identity(1,1) primary key,
	pageCount smallint not null,
	dateSet date not null,
	isReached bit not null,
	user_id int foreign key references Users(id) not null
);