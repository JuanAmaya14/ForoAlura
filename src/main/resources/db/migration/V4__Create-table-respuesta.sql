CREATE TABLE respuestas (
  id BIGINT NOT NULL AUTO_INCREMENT,
  mensaje_respuesta LONGTEXT NOT NULL,
  fecha_respuesta DATE NOT NULL,
  id_topico BIGINT NOT NULL,
  id_autor BIGINT NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,
  INDEX FK_RESPUESTA_TIPICO_idx (id_topico ASC) VISIBLE,
  INDEX FK_RESPUESTA_AUTOR_idx (id_autor ASC) VISIBLE,
    CONSTRAINT FK_RESPUESTA_TIPICO
      FOREIGN KEY (id_topico)
      REFERENCES topicos (id)
      ON DELETE  NO ACTION
      ON UPDATE NO ACTION,
    CONSTRAINT FK_RESPUESTA_AUTOR
      FOREIGN KEY (id_autor)
      REFERENCES usuarios (id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION);