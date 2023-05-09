-- Path: src\main\resources\import.sql
INSERT INTO proveedor(codigo,nombre,categoria,retencion) VALUES ('01001', 'Proveedor A', 'A', 'No');
INSERT INTO proveedor(codigo,nombre,categoria,retencion) VALUES ('01002', 'Proveedor B', 'B', 'Si');
INSERT INTO proveedor(codigo,nombre,categoria,retencion) VALUES ('01003', 'Proveedor C', 'C', 'No');
INSERT INTO proveedor(codigo,nombre,categoria,retencion) VALUES ('02004', 'Proveedor D', 'A', 'No');
INSERT INTO proveedor(codigo,nombre,categoria,retencion) VALUES ('03005', 'Proveedor E', 'B', 'Si');
INSERT INTO proveedor(codigo,nombre,categoria,retencion) VALUES ('04006', 'Proveedor F', 'C', 'No');
INSERT INTO proveedor(codigo,nombre,categoria,retencion) VALUES ('05007', 'Proveedor G', 'D', 'No');


INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/17','M','01001','50');
INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/18','T','01001','50');
INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/18','M','01003','35');
INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/18','M','01002','30');
INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/18','T','02004','25');

INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/10','M','01001','50');
INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/10','T','01001','100');
INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/12','N','01003','35');
INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/13','M','01002','30');
INSERT INTO dato_proveedor(fecha,turno,proveedor,kls_leche) VALUES ('2023/03/14','T','02004','25');

INSERT INTO dato_laboratorio(proveedor,porcentaje_grasa,porcentaje_solido_total,quincena) VALUES ('01001','25','24','2023/03/Q2');
INSERT INTO dato_laboratorio(proveedor,porcentaje_grasa,porcentaje_solido_total,quincena) VALUES ('01002','8','11','2023/03/Q2');
INSERT INTO dato_laboratorio(proveedor,porcentaje_grasa,porcentaje_solido_total,quincena) VALUES ('01003','30','40','2023/03/Q2');
INSERT INTO dato_laboratorio(proveedor,porcentaje_grasa,porcentaje_solido_total,quincena) VALUES ('02004','40','27','2023/03/Q2');

INSERT INTO dato_laboratorio(proveedor,porcentaje_grasa,porcentaje_solido_total,quincena) VALUES ('01001','25','14','2023/03/Q1');
INSERT INTO dato_laboratorio(proveedor,porcentaje_grasa,porcentaje_solido_total,quincena) VALUES ('01002','8','11','2023/03/Q1');
INSERT INTO dato_laboratorio(proveedor,porcentaje_grasa,porcentaje_solido_total,quincena) VALUES ('01003','30','50','2023/03/Q1');
INSERT INTO dato_laboratorio(proveedor,porcentaje_grasa,porcentaje_solido_total,quincena) VALUES ('02004','40','27','2023/03/Q1');