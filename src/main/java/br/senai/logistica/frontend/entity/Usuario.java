package br.senai.logistica.frontend.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Usuario {
	Integer id;
	String nomeCompleto, login, senha;
	Perfil perfil;
}
