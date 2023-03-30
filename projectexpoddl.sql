DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS exhibitorNotification;
DROP TABLE IF EXISTS event;
DROP TABLE IF EXISTS exhibit;
DROP TABLE IF EXISTS exhibitor;
DROP TABLE IF EXISTS exhibitArea;
DROP TABLE IF EXISTS visitor;
DROP TABLE IF EXISTS expo;

CREATE TABLE expo(
	expoId INT AUTO_INCREMENT NOT NULL,
	province VARCHAR(2),
	comune VARCHAR(50),
	address VARCHAR(200),
	streetNumber VARCHAR(10),
	expoStartDate DATE,
	expoEndDate DATE,
	PRIMARY KEY(expoId)
);

CREATE TABLE visitor(
	fiscalCode VARCHAR(16) NOT NULL,
	expoId INT NOT NULL,
	name VARCHAR(50),
	surname VARCHAR(50),
	email VARCHAR(200),
	PRIMARY KEY(fiscalCode)
);

CREATE TABLE exhibitArea(
	exhibitAreaId INT AUTO_INCREMENT NOT NULL,
	expoId INT NOT NULL,
	PRIMARY KEY(exhibitAreaId),
	FOREIGN KEY(expoId) REFERENCES expo(expoId)
);

CREATE TABLE exhibitor(
	exhibitorId INT AUTO_INCREMENT NOT NULL,
	exhibitorName VARCHAR(100),
	PRIMARY KEY(exhibitorId)
);


CREATE TABLE exhibit(
	exhibitId INT AUTO_INCREMENT NOT NULL,
	exhibitAreaId INT NOT NULL,
	exhibitorId INT NOT NULL,
	exhibitName VARCHAR(200),
	exhibitStartDate DATE,
	exhibitStartTime TIME,
	exhibitEndDate DATE,
	exhibitEndTime TIME,
	PRIMARY KEY(exhibitId),
	FOREIGN KEY(exhibitAreaId) REFERENCES exhibitArea(exhibitAreaId),
	FOREIGN KEY(exhibitorId) REFERENCES exhibitor(exhibitorId)
);

CREATE TABLE event(
	eventId INT AUTO_INCREMENT NOT NULL,
	exhibitAreaId INT NOT NULL,
	exhibitorId INT NOT NULL,
	eventName VARCHAR(200),
	eventStartDate DATE,
	eventStartTime TIME,
	eventEndDate DATE,
	eventEndTime TIME,
	eventTotalSeats INT UNSIGNED,
	eventAvailableSeats INT UNSIGNED,
	PRIMARY KEY(eventId),
	FOREIGN KEY(exhibitAreaId) REFERENCES exhibitArea(exhibitAreaId),
	FOREIGN KEY(exhibitorId) REFERENCES exhibitor(exhibitorId)
);

CREATE TABLE exhibitorNotification(
	notificationId INT NOT NULL AUTO_INCREMENT,
	exhibitorId INT NOT NULL,
	message VARCHAR(1000) DEFAULT "Unspecified notification",
	PRIMARY KEY(notificationId),
	FOREIGN KEY(exhibitorId) REFERENCES exhibitor(exhibitorId)
);

CREATE TABLE ticket(
	ticketCode INT NOT NULL AUTO_INCREMENT,
	eventId INT NOT NULL,
	fiscalCode VARCHAR(16) NOT NULL,
	PRIMARY KEY(ticketCode),
	FOREIGN KEY(eventId) REFERENCES event(eventId),
	FOREIGN KEY(fiscalCode) REFERENCES visitor(fiscalCode)
);

INSERT INTO expo (province, comune, expoStartDate, expoEndDate) VALUES
('FI', 'Rufina', '2023-03-05', '2023-05-12'),
('FI', 'Firenze', '2023-12-31', '2024-01-15');

INSERT INTO exhibitArea (expoId) VALUES
(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),
(2),(2),(2),(2),(2),(2);

INSERT INTO exhibitor (exhibitorName) VALUES
('BCC'),
('IBM'),
('Microsoft'),
('Avanade'),
('Google'),
('Oracle'),
('Samsung');

INSERT INTO exhibit (exhibitAreaId, exhibitorId, exhibitName, exhibitStartDate,	exhibitStartTime, exhibitEndDate, exhibitEndTime) VALUES
(1, 2, 'IBM presents stuff', '2023-03-15', '00:00', '2023-04-20', '15:00'),
(2, 1, 'BCCex', '2023-03-15', '00:00', '2023-04-20', '15:00'),
(6, 2, 'IBMex2', '2023-03-15', '00:00', '2023-03-20', '15:00'),
(14, 4, 'Avanadeex1', '2023-03-15', '00:00', '2023-03-20', '15:00'),
(4, 1, 'BCCex2', '2023-03-15', '00:00', '2023-04-20', '15:00')
;

INSERT INTO event (exhibitAreaId, exhibitorId, eventName, eventStartDate, eventStartTime, eventEndDate,	eventEndTime, eventTotalSeats, eventAvailableSeats) VALUES
(3, 1, 'BCCev', '2023-03-15', '00:00', '2023-03-20', '15:00', 1, 1),
(1, 3, 'Microsoftev1', '2023-04-10', '00:00', '2023-04-20', '15:00', 100, 100);

INSERT INTO visitor (fiscalCode, expoId, name, surname, email) VALUES
('RMEG123', 1, 'Egidio', 'Romolo', 'egrm@gm.co');

INSERT INTO exhibitorNotification(exhibitorId, message) VALUES
(1, 'Test notification');