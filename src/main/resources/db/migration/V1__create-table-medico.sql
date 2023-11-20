CREATE TABLE medicos (
	ID BIGINT auto_increment NOT NULL,
	NOME varchar(100) NOT NULL,
	EMAIL varchar(100) NOT NULL,
	CRM varchar(8) NOT NULL,
	ESPECIALIDADE varchar(100) NOT NULL,
	LOGRADOURO varchar(150) NOT NULL,
	BAIRRO varchar(100) NOT NULL,
	CEP varchar(10) NOT NULL,
	COMPLEMENTO varchar(100) NULL,
	NUMERO varchar(20) NULL,
	UF varchar(2) NOT NULL,
	CIDADE varchar(100) NOT NULL,
	CONSTRAINT medico_pk PRIMARY KEY (ID),
	CONSTRAINT medico_un_email UNIQUE KEY (EMAIL),
	CONSTRAINT medico_un_crm UNIQUE KEY (CRM)
);

