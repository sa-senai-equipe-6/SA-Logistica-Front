package br.senai.logistica.frontend.entity;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Motorista {
	
	Integer id;
	String cnh;
	LocalDate dataRenovacao;
	Character categoria;
	Usuario usuario;
}
