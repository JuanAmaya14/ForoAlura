ALTER TABLE topicos
CHANGE COLUMN fecha_creacion fecha_creacion DATETIME(6) NOT NULL,
CHANGE COLUMN estatus estatus TINYINT NOT NULL;