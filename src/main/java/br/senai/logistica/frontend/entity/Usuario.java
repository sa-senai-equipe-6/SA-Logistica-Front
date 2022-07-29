package br.senai.logistica.frontend.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Usuario {
	Integer id;

	@NonNull
	String nomeCompleto, login, senha;
	
	@NonNull
	Perfil perfil;
}
