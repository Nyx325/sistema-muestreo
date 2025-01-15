DROP DATABASE IF EXISTS muestreos;
CREATE DATABASE muestreos;
USE muestreos;

CREATE TABLE Logs(
  folio INT PRIMARY KEY NOT NULL,
  message TEXT NOT NULL
);

CREATE TABLE Signataries(
  id UUID PRIMARY KEY NOT NULL,
  active BOOLEAN NOT NULL,
  first_name VARCHAR(50) NOT NULL,
  mid_name VARCHAR(50),
  father_lastname VARCHAR(50) NOT NULL,
  mother_lastname VARCHAR(50)
);

CREATE TABLE Standards(
  id UUID PRIMARY KEY NOT NULL,
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
  id UUID PRIMARY KEY NOT NULL,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE Units(
  id UUID PRIMARY KEY NOT NULL,
  active BOOLEAN NOT NULL, 
  long_name TEXT NOT NULL,
  short_name VARCHAR(10) NOT NULL,
  window_type TEXT NOT NULL,
  regex TEXT
);

CREATE TABLE Clients(
  id UUID PRIMARY KEY NOT NULL,
  name VARCHAR(50) NOT NULL
);

/*
 * Encapsular las acciones que pueden realizar
 * un signatario en una tabla. Por ejemplo
 * Pruebas a una muestra o directamente insertar
 * muestras
 */
CREATE TABLE Actions(
  id UUID PRIMARY KEY NOT NULL,
  name VARCHAR(50) NOT NULL,
  param UUID,
  FOREIGN KEY (param) REFERENCES Parameters(id)
    ON DELETE SET NULL ON UPDATE CASCADE
);


/* 
 * Tabla cruzada para relacionar signatarios con 
 * las acciones que pueden realizar
 */
CREATE TABLE SignatariesDetail(
  folio INT PRIMARY KEY NOT NULL,
  signatary UUID,
  action UUID,
  FOREIGN KEY (action) REFERENCES Actions(id)
    ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (signatary) REFERENCES Signataries(id)
    ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE UnitsPerStandard(
  id UUID PRIMARY KEY NOT NULL,
  standard UUID,
  unit UUID,
  FOREIGN KEY (standard) REFERENCES Standards(id)
    ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (unit) REFERENCES Units(id)
    ON DELETE SET NULL ON UPDATE CASCADE
);


CREATE TABLE ParameterDetail(
  id UUID PRIMARY KEY NOT NULL,
  parameter UUID,
  standard UUID,
  FOREIGN KEY (parameter) REFERENCES Parameters(id)
    ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (standard) REFERENCES Standards(id)
    ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE Sites(
  id UUID PRIMARY KEY NOT NULL,
  active BOOLEAN NOT NULL,
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
  cliente VARCHAR(50),
);

CREATE TABLE Samples(
  id UUID PRIMARY KEY NOT NULL,
  receipt_date DATE NOT NULL,
  sample_date DATETIME NOT NULL,
  conagua_ai_id INT NOT NULL UNIQUE,
  sample_number INT NOT NULL,
  project VARCHAR(50) NOT NULL,
  sampling_plan VARCHAR(10) NOT NULL,
  sampler UUID,
  site UUID,
  standard UUID,
  FOREIGN KEY (sampler) REFERENCES Signataries(id)
    ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (site) REFERENCES Sites(id)
    ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE Result(
  id UUID PRIMARY KEY NOT NULL,
  result_json TEXT NOT NULL,
  analysis_date DATE NOT NULL,
  analyst UUID,
  test UUID,
  standard UUID,
  sample UUID,
  FOREIGN KEY (analyst) REFERENCES Signataries(id)
    ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (test) REFERENCES Actions(id)
    ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (standard) REFERENCES Standards(id)
    ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (sample) REFERENCES Samples(id)
    ON DELETE SET NULL ON UPDATE CASCADE
);
