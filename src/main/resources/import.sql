-- Path: src\main\resources\import.sql
INSERT INTO proveedor(codigo,nombre,categoria,retencion) VALUES ('01003', 'QUISPE S. ALCIDES', 'A', 'No');
INSERT INTO proveedor(codigo,nombre,categoria,retencion) VALUES ('01011', 'BELLO R. PHILIP', 'B', 'Si');
INSERT INTO proveedor(codigo,nombre,categoria,retencion) VALUES ('01025', 'ROMAN A. PABLO', 'C', 'No');
INSERT INTO proveedor(codigo,nombre,categoria,retencion) VALUES ('01078', 'A Proveedor', 'A', 'No');
INSERT INTO proveedor(codigo,nombre,categoria,retencion) VALUES ('00303', 'B Proveedor', 'B', 'Si');
INSERT INTO proveedor(codigo,nombre,categoria,retencion) VALUES ('00401', 'C Proveedor', 'C', 'No');
INSERT INTO proveedor(codigo,nombre,categoria,retencion) VALUES ('00402', 'D Proveedor', 'D', 'No');

INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/10','M','01003','50');
INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/10','T','01003','100');
INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/18','N','01025','35');
INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/18','M','01011','30');
INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/18','T','01078','25');
INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/17','M','01003','50');
INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/18','T','01003','50');
INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/12','N','01025','35');
INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/13','M','01011','30');
INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/14','T','01078','25');

INSERT INTO dato_laboratorio(proveedor,porcentaje_grasa,porcentaje_solido_total,quincena) VALUES ('01003','25','14','2023/03/Q2');
INSERT INTO dato_laboratorio(proveedor,porcentaje_grasa,porcentaje_solido_total,quincena) VALUES ('01011','8','11','2023/03/Q2');
INSERT INTO dato_laboratorio(proveedor,porcentaje_grasa,porcentaje_solido_total,quincena) VALUES ('01025','30','50','2023/03/Q2');
INSERT INTO dato_laboratorio(proveedor,porcentaje_grasa,porcentaje_solido_total,quincena) VALUES ('01078','40','27','2023/03/Q2');

INSERT INTO dato_laboratorio(proveedor,porcentaje_grasa,porcentaje_solido_total,quincena) VALUES ('01003','25','14','2023/03/Q1');
INSERT INTO dato_laboratorio(proveedor,porcentaje_grasa,porcentaje_solido_total,quincena) VALUES ('01011','8','11','2023/03/Q1');
INSERT INTO dato_laboratorio(proveedor,porcentaje_grasa,porcentaje_solido_total,quincena) VALUES ('01025','30','50','2023/03/Q1');
INSERT INTO dato_laboratorio(proveedor,porcentaje_grasa,porcentaje_solido_total,quincena) VALUES ('01078','40','27','2023/03/Q1');