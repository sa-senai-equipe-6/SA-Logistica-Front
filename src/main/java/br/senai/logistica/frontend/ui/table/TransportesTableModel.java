package br.senai.logistica.frontend.ui.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.senai.logistica.frontend.entity.MeioTransporte;

public class TransportesTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 4219244341278160651L;
	
	private List<MeioTransporte> transportes = new ArrayList<>();
	private final Integer QTDE_COLUNAS = 3;
	
	public TransportesTableModel(List<MeioTransporte> transportes) {
		this.transportes.addAll(transportes);
	}
	
	@Override
	public int getRowCount() {
		return transportes.size();
	}

	@Override
	public int getColumnCount() {
		return QTDE_COLUNAS;
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0: return "ID";
		case 1: return "Revisão";
		case 2: return "Motorista";
		default:
			throw new IllegalArgumentException("Índice inválido");
		}
	}
	
	public void removePor(int id) {
		this.transportes.remove(id);
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0)
			return this.transportes.get(rowIndex).getId();
		if (columnIndex == 1)
			return this.transportes.get(rowIndex).getDataRevisao();
		if (columnIndex == 2)
			return this.transportes.get(rowIndex).getMotorista().getUsuario().getNomeCompleto();
		throw new IllegalArgumentException("Índice inválido");
	}

	public MeioTransporte getPor(int linhaSelecionada) {
		return transportes.get(linhaSelecionada);
	}

}
