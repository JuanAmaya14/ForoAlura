ALTER TABLE topicos
ADD INDEX FK_TOPICO_AUTOR_USUARIO_idx (autor ASC) VISIBLE;
;
ALTER TABLE topicos
ADD CONSTRAINT FK_TOPICO_AUTOR_USUARIO
  FOREIGN KEY (autor)
  REFERENCES usuarios (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;