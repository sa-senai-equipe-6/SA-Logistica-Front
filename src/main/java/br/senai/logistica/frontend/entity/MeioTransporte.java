package br.senai.logistica.frontend.entity;

import java.time.LocalDate;

import br.senai.logistica.frontend.route.Entity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MeioTransporte implements Cadastravel {
	Integer id;
	Tipo tipoVeiculo;
	LocalDate dataRevisao;
	String descricao;
	Motorista motorista;
	
	@Override
	public Entity getTipoEntity() {
		return Entity.MEIO_TRANSPORTE;
	}
}
