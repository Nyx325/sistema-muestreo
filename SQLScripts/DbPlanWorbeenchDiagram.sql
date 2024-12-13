DROP DATABASE IF EXISTS muestreos;
CREATE DATABASE muestreos;
USE muestreos;

CREATE TABLE Logs(
  folio INT PRIMARY KEY NOT NULL,
  message TEXT NOT NULL
);

CREATE TABLE Signataries(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  first_name VARCHAR(50) NOT NULL,
  mid_name VARCHAR(50),
  father_lastname VARCHAR(50) NOT NULL,
  mother_lastname VARCHAR(50)
);

CREATE TABLE Standards(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  name VARCHAR(100) NOT NULL
);

/* Categorias para distintas pruebas
 * Ej:
 * Pruebas: 
 * - Coliformes totales
 * - Coliformes fecales
 * Categoria (Parametro) de ambos: Microbiologia
 */
CREATE TABLE Parameters(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE Units(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  units VARCHAR(10),
  window_type ENUM("1", "2", "3")
);

CREATE TABLE Clients(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  name VARCHAR(50) NOT NULL
);

/*
 * Encapsular las acciones que pueden realizar
 * un signatario en una tabla. Por ejemplo
 * Pruebas a una muestra o directamente insertar
 * muestras
 */
CREATE TABLE Actions(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  name VARCHAR(50) NOT NULL,
  param VARCHAR(32),
  FOREIGN KEY (param) REFERENCES Parameters(id)
    ON DELETE SET NULL ON UPDATE CASCADE
);


/* 
 * Tabla cruzada para relacionar signatarios con 
 * las acciones que pueden realizar
 */
CREATE TABLE SignatariesDetail(
  folio INT PRIMARY KEY NOT NULL,
  signatary VARCHAR(32),
  action VARCHAR(32),
  FOREIGN KEY (action) REFERENCES Actions(id)
    ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (signatary) REFERENCES Signataries(id)
    ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE UnitsPerStandard(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  standard VARCHAR(32),
  unit VARCHAR(32),
  FOREIGN KEY (standard) REFERENCES Standards(id)
    ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (unit) REFERENCES Units(id)
    ON DELETE SET NULL ON UPDATE CASCADE
);


CREATE TABLE ParameterDetail(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  parameter VARCHAR(32),
  standard VARCHAR(32),
  FOREIGN KEY (parameter) REFERENCES Parameters(id)
    ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (standard) REFERENCES Standards(id)
    ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE Sites(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  clave VARCHAR(100) NOT NULL,
  nombre VARCHAR(100) NOT NULL,
  cuenca VARCHAR(50),
  clave_acuifero VARCHAR(50),
  acuifero VARCHAR(50),
  organismo VARCHAR(50),
  direccion_local VARCHAR(50),
  estado VARCHAR(25),
  municipio VARCHAR(25),
  cuerpo_agua VARCHAR(50),
  tipo_cuerpo VARCHAR(50),
  subtipo_cuerpo VARCHAR(50),
  latitud VARCHAR(50),
  longitud VARCHAR(50),
  uso VARCHAR(50),
  lugar_toma VARCHAR(100),
  cliente VARCHAR(32),
  FOREIGN KEY (cliente) REFERENCES Clients(id)
    ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE Samples(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  receipt_date DATE NOT NULL,
  sample_date DATETIME NOT NULL,
  conagua_ai_id INT NOT NULL UNIQUE,
  sample_number INT NOT NULL,
  project VARCHAR(50) NOT NULL,
  sampling_plan VARCHAR(10) NOT NULL,
  sampler VARCHAR(32),
  site VARCHAR(32),
  standard VARCHAR(32),
  FOREIGN KEY (sampler) REFERENCES Signataries(id)
    ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (site) REFERENCES Sites(id)
    ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE Result(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  result_json TEXT NOT NULL,
  analysis_date DATE NOT NULL,
  analyst VARCHAR(32),
  test VARCHAR(32),
  standard VARCHAR(32),
  sample VARCHAR(32),
  FOREIGN KEY (analyst) REFERENCES Signataries(id)
    ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (test) REFERENCES Actions(id)
    ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (standard) REFERENCES Standards(id)
    ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (sample) REFERENCES Samples(id)
    ON DELETE SET NULL ON UPDATE CASCADE
);
