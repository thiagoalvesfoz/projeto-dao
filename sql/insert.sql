-- -------------------------------------------------------------------------
-- CRIA A TABELA DEPARTMENT
-- --------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS department (
	`id` int primary key auto_increment,
    `name` varchar(45) not null
);

-- -------------------------------------------------------------------------
-- CRIA A TABELA DEPARTMENT
-- --------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS seller (
	`id` int primary key auto_increment,
    `name` varchar(60) not null,
	`email` varchar(255) not null,
    `birth_date` date not null,
    `base_salary` decimal(10,2),
    `department_id` int null,
    foreign key (`department_id`) references department (`id`)
);

-- -------------------------------------------------------------------------
-- INSERE DADOS NA TABELA DEPARTMENT
-- -------------------------------------------------------------------------
INSERT INTO department (name) VALUES ("Computers");
INSERT INTO department (name) VALUES ("Perfumes"); 
INSERT INTO department (name) VALUES ("Drinks"); 
INSERT INTO department (name) VALUES ("Clothes"); 


-- -------------------------------------------------------------------------
-- INSERE DADOS NA TABELA SELLER
-- -------------------------------------------------------------------------
INSERT INTO seller (name, email, birth_date, base_salary, department_id) VALUES ('Umbelina Neves','umbelina_neves@usa.net', '2000-10-07', 3230.00, 4);
INSERT INTO seller (name, email, birth_date, base_salary, department_id) VALUES ('Godinho ou Godim Felgueiras','godinho_ou_godim_felgueiras@hermanos.com.ar', '1987-11-17', 3230.00, 4);
INSERT INTO seller (name, email, birth_date, base_salary, department_id) VALUES ('Ricardo Garcés','ricardo_garces@discovery.channel.com', '1991-06-23', 2230.00, 3);
INSERT INTO seller (name, email, birth_date, base_salary, department_id) VALUES ('Adélia Sobral','adelia_sobral@usa.net', '1990-04-01', 3230.00, 2);
INSERT INTO seller (name, email, birth_date, base_salary, department_id) VALUES ('Zenaide Peçanha','zenaide_pecanha@vaildochaves.com.mx', '1982-06-12', 2332.00, 4);
INSERT INTO seller (name, email, birth_date, base_salary, department_id) VALUES ('Sabrina SantAnna','sabrina_santanna@vaildochaves.com.mx', '1989-07-27', 1870.00, 3);
INSERT INTO seller (name, email, birth_date, base_salary, department_id) VALUES ('Eduardo Rua','eduardo_rua@samba.br', '1998-05-13', 3230.00, 1);
INSERT INTO seller (name, email, birth_date, base_salary, department_id) VALUES ('Cesário Brião','cesario_briao@sertanejo.com.br', '1997-02-08', 2330.00, 1);
INSERT INTO seller (name, email, birth_date, base_salary, department_id) VALUES ('Alexandra Ramírez','alexandra_ramirez@hbo.com', '1993-07-29', 2460.00, 3);
INSERT INTO seller (name, email, birth_date, base_salary, department_id) VALUES ('Luzia Rivas','luzia_rivas@globo.com', '1983-07-14', 2348.00, 2);