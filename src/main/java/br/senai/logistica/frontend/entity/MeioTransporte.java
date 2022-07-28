package br.senai.logistica.frontend.entity;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MeioTransporte {
	Integer id;
	Tipo tipoVeiculo;
	LocalDate dataRevisao;
	String descricao;
	Motorista motorista;
}
