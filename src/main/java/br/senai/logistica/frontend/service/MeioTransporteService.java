package br.senai.logistica.frontend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senai.logistica.frontend.entity.MeioTransporte;
import br.senai.logistica.frontend.route.Rota;

@Service
public class MeioTransporteService {

	@Autowired
	private Rota rota;
	
	public void excluir(MeioTransporte transporteSelecionado) {
		//TODO: excluir meio de transporte
	}
	
	public void cadastrar(MeioTransporte transporte) {
		//TODO: inserir meio de transporte
	}
	
	public MeioTransporte buscarPor(Integer id) {
		//TODO: buscar transporte
		return null;
	}
	
	public void editar(MeioTransporte transporte) {
		//TODO: editar transporte
	}
	
	public List<MeioTransporte> listarPorMotorista(String nomeMotorista) {
		//TODO: listar transportes
		return null;
	}
	
}
