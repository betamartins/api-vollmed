CREATE TABLE pacientes (
	ID BIGINT auto_increment NOT NULL,
	NOME varchar(100) NOT NULL,
	EMAIL varchar(100) NOT NULL,
	CPF varchar(11) NOT NULL,
	LOGRADOURO varchar(150) NOT NULL,
	BAIRRO varchar(100) NOT NULL,
	CEP varchar(10) NOT NULL,
	COMPLEMENTO varchar(100) NULL,
	NUMERO varchar(20) NULL,
	UF varchar(2) NOT NULL,
	CIDADE varchar(100) NOT NULL,
	ATIVO TINYINT NOT NULL,
	CONSTRAINT paciente_pk PRIMARY KEY (ID),
	CONSTRAINT paciente_un_email UNIQUE KEY (EMAIL),
	CONSTRAINT paciente_un_cpf UNIQUE KEY (CPF)
);

