INSERT INTO ROLES VALUES(1,'ROLE_USER');
INSERT INTO ROLES VALUES(2,'ROLE_ADMIN');

INSERT INTO COUNTRY VALUES(1,'Hungary');
INSERT INTO COUNTRY VALUES(2,'Germany');

INSERT INTO CITY VALUES(1,'Budapest',1);
INSERT INTO CITY VALUES(2,'Kaposvar',1);
INSERT INTO CITY VALUES(3,'Balatonboglar',1);
INSERT INTO CITY VALUES(4,'Hamburg',2);
INSERT INTO CITY VALUES(5,'Saltzburg',2);

INSERT INTO SPECIES VALUES(1,'Dog');
INSERT INTO SPECIES VALUES(2,'Cat');

INSERT INTO BREED VALUES(1,'French buldog',1);
INSERT INTO BREED VALUES(2,'Hungarian vizsla',1);
INSERT INTO BREED VALUES(3,'i dont know really',1);
INSERT INTO BREED VALUES(4,'catmat',2);
INSERT INTO BREED VALUES(5,'Actually a cat',2);

INSERT INTO USER VALUES(1,'magyarb0616@gmail.com','Bence','$2a$10$3pPaFVs92rG2aszyDdiMeeaOIsae05vNNHjYY9pNWyB.J7GdjdHzu','06305030967','Magyar','magyarb',1);
INSERT INTO USER VALUES(2,'kisjani@gmail.com','Janos','$2a$10$3pPaFVs92rG2aszyDdiMeeaOIsae05vNNHjYY9pNWyB.J7GdjdHzu','06305870967','Kis','jancsi',1);
INSERT INTO USER VALUES(3,'blabla@gmail.com','Tunde','$2a$10$3pPaFVs92rG2aszyDdiMeeaOIsae05vNNHjYY9pNWyB.J7GdjdHzu','063057532967','Toth','tundike',2);

INSERT INTO USER_ROLES VALUES(1,1);
INSERT INTO USER_ROLES VALUES(1,2);
INSERT INTO USER_ROLES VALUES(2,1);
INSERT INTO USER_ROLES VALUES(3,1);

INSERT INTO PET VALUES(1,2,'lorem ipsum',1,2,'Yoda',0,1,1,1,1,1);
INSERT INTO PET VALUES(2,4,'lorem ipsum',1,3,'Drazse',0,1,1,1,2,1);
INSERT INTO PET VALUES(3,7,'lorem ipsum',3,2,'Cucli',0,0,2,2,3,2);
INSERT INTO PET VALUES(4,1,'lorem ipsum',2,1,'Kampó',0,1,3,2,3,1);

INSERT INTO IMAGE VALUES(1,'http://127.0.0.1:8081/nodelete/1.jpg',1);
INSERT INTO IMAGE VALUES(2,'http://127.0.0.1:8081/nodelete/2.jpg',1);
INSERT INTO IMAGE VALUES(3,'http://127.0.0.1:8081/nodelete/3.jpg',2);
INSERT INTO IMAGE VALUES(4,'http://127.0.0.1:8081/nodelete/4.jpg',2);
INSERT INTO IMAGE VALUES(5,'http://127.0.0.1:8081/nodelete/5.jpg',3);
INSERT INTO IMAGE VALUES(6,'http://127.0.0.1:8081/nodelete/6.jpg',3);
INSERT INTO IMAGE VALUES(7,'http://127.0.0.1:8081/nodelete/7.jpg',4);
INSERT INTO IMAGE VALUES(8,'http://127.0.0.1:8081/nodelete/8.jpg',4);

