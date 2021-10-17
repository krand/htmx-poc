INSERT INTO manufacturer(id,name) VALUES
(1,'Toyota'),
(2,'Ford'),
(3,'Citroën')
;

INSERT INTO model(id,name,release_year,manufacturer_id)
VALUES (1, 'Camry', 2017, 1)
,(2, 'Yaris', 2010, 1)
,(3, 'Corolla', 2018, 2)
,(4, 'Focus', 2018, 2)
,(5, 'Fiesta', 2017, 2)
,(6, 'DS4', 2015, 3)
;

INSERT INTO Vehicle(id,color,description,mileage,price,manufacture_year,manufacturer_id,model_id)
VALUES (1,'black',null,100000,10000.0,2019,1,1)
,(2,'white',null,70000, 11000.0,2019,2,4)
;