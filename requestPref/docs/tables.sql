CREATE TABLE pessoa(
	cpf VARCHAR(14) NOT NULL,
	nome VARCHAR(40) NOT NULL,
	sexo CHAR(1) NOT NULL,
	nacionalidade VARCHAR(15),
	estadoCivil VARCHAR(14),
	rg VARCHAR(15),
	endereco VARCHAR(50) NOT NULL,
	numero VARCHAR(8),
	bairro VARCHAR(35) NOT NULL,
	cidade VARCHAR(20) NOT NULL,
	estado VARCHAR(2) NOT NULL,
	
	CONSTRAINT pessoa_pk PRIMARY KEY (cpf)
);


CREATE TABLE empresa(
	cnpj VARCHAR(18) NOT NULL,
	razao VARCHAR(40) NOT NULL,
	inscMunicipal VARCHAR(6),
	atividade VARCHAR(100),
	dataInicio date,
	endereco VARCHAR(50) NOT NULL,
	numero VARCHAR(8),
	bairro VARCHAR(30) NOT NULL,
	cidade VARCHAR(20) NOT NULL,
	estado VARCHAR(2) NOT NULL,
	
	CONSTRAINT empresa_pk PRIMARY KEY(cnpj)
	
);

CREATE TABLE tools(
	id INTEGER NOT NULL,
	pathFile VARCHAR(200) NOT NULL,
	pronome VARCHAR(20),--Exm. Sr
	nome VARCHAR(40) NOT NULL,
	tratamento VARCHAR(20),--DD
	cargo VARCHAR(40),
	cidadeEstado VARCHAR (50),
	var VARCHAR(20),
	
	CONSTRAINT tools_pk PRIMARY KEY(id)
)

/* ALGUNS COMANDOS UTILIZADOS:
Adicionar colunas: alter table EMPRESA add column notas varchar(100)
Deletar: delete from pessoa where cpf = '084.886.916-88';
UPDATE tudo: UPDATE empresa SET STATUS = 'ATIVO';
UPDATE : UPDATE empresa SET STATUS = 'ATIVO' where inscMunicipal = '4476';
SELECT: SELECT razao, cnpj, classifica from empresa where inscMunicipal = '3549'

*/

INSERT INTO tools VALUES(1,'D:\Usu�rios\Mateus_2\Documents\SECRETARIA\Secretaria 2017\REQUERIMENTOS 2017', 'Exm Sr.','Luiz Rosa da Silva','DD','Prefeito Municipal','Gon�alves - MG');

INSERT INTO empresa VALUES('05.124.393/0001-69','JACIRA PEREIRA DA COSTA RIBEIRO EPP','400','COM�RCIO VAREJISTA DE PRODUTOS ALIMENT�CIOS N�O ESPECIFICADOS OU N�O CLASSIFICADOS','RUA CONSELHEIRO ALFREDO PINTO','240','CENTRO','GON�ALVES','MG');

INSERT INTO pessoa VALUES('055.640.626-55','MATEUS FERREIRA DE SOUZA','M','BRASILEIRO','CASADO','37.031.453-0','RUA CAPIT�O ANT�NIO CARLOS','235','CENTRO','GON�ALVES','MG');
INSERT INTO pessoa(cpf,nome,sexo,nacionalidade,estadoCivil,endereco,numero,bairro,cidade,estado) VALUES ('084.886.916-88','ELIANA APARECIDA DE SILVA','F','BRASILEIRA','CASADA','RUA CAPIT�O ANT�NIO CARLOS','235','CENTRO','GON�ALVES','MG');
INSERT INTO pessoa VALUES ('519.303.156-00','RICARDO REZENDE DE SOUZA','M','BRASILEIRO','CASADO','M-3.837.343','PRA�A MONSENHOR DUTRA','161','CENTRO','GON�ALVES','MG');
INSERT INTO pessoa VALUES('079.035.036-00','NATANAEL HELENO DE SOUZA', 'M','BRASILEIRO','SOLTEIRO','39.835.078-4','RUA CONSELHEIRO ALFREDO PINTO','365','CENTRO','GON�ALVES','MG');
INSERT INTO pessoa (cpf,nome,sexo,nacionalidade,rg,endereco,numero,bairro,cidade,estado) VALUES('237.082.396-87','ARLETE VIEIRA MACEDO','F','BRASILEIRA','11.293.292','CORONEL JO�O VIEIRA','96','CENTRO','GON�ALVES','MG');

