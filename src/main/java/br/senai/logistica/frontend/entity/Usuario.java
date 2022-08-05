package br.senai.logistica.frontend.entity;

import br.senai.logistica.frontend.route.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Usuario implements Cadastravel {
	
	Integer id;

	@NonNull
	String nomeCompleto, login, senha;
	
	@NonNull
	Perfil perfil;

	@Override
	public Entity getTipoEntity() {
		return Entity.USUARIO;
	}
}
