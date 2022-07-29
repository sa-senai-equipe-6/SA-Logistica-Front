package br.senai.logistica.frontend.service;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senai.logistica.frontend.entity.MeioTransporte;
import br.senai.logistica.frontend.route.Entity;
import br.senai.logistica.frontend.route.Rota;

@Service
public class MeioTransporteService {

	@Autowired
	private Rota rota;
	
	public JSONArray listarTodosMotoristas() {
		return new JSONArray(rota.listar(Entity.MOTORISTA));
	}

	public void excluir(MeioTransporte transporteSelecionado) {
		//TODO: excluir meio de transporte
	}
}
