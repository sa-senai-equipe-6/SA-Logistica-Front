package br.senai.logistica.frontend.entity;

import java.time.LocalDate;

import br.senai.logistica.frontend.route.Entity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Motorista implements Cadastravel {
	
	Integer id;
	String cnh;
	LocalDate dataRenovacao;
	Character categoria;
	Usuario usuario;
	
	@Override
	public Entity getTipoEntity() {
		return Entity.MOTORISTA;
	}
	
	@Override
	public String toString() {
		return this.usuario.getNomeCompleto();
	}
}
