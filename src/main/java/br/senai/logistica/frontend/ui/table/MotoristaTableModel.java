package br.senai.logistica.frontend.ui.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.senai.logistica.frontend.entity.Motorista;

public class MotoristaTableModel extends AbstractTableModel {

private static final long serialVersionUID = 4219244341278160651L;
	
	private List<Motorista> motoristas;
	private final Integer QTDE_COLUNAS = 2;
	
	public MotoristaTableModel(List<Motorista> motoristas) {
		this.motoristas = motoristas;
	}
	
	@Override
	public int getRowCount() {
		return motoristas.size();
	}

	@Override
	public int getColumnCount() {
		return QTDE_COLUNAS;
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0: return "ID";
		case 1: return "Nome Completo";
		default:
			throw new IllegalArgumentException("Índice inválido");
		}
	}
	
	public void removerPor(int rowIndex) {
		this.motoristas.remove(rowIndex);
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0)
			return this.motoristas.get(rowIndex).getId();
		if (columnIndex == 1)
			return this.motoristas.get(rowIndex).getUsuario().getNomeCompleto();
		throw new IllegalArgumentException("Índice inválido");
	}

	public Motorista getPor(int linhaSelecionada) {
		return motoristas.get(linhaSelecionada);
	}

}
