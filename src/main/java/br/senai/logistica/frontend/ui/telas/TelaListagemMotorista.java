package br.senai.logistica.frontend.ui.telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.senai.logistica.frontend.entity.Motorista;
import br.senai.logistica.frontend.service.MotoristaService;
import br.senai.logistica.frontend.ui.table.MotoristaTableModel;

@Component
public class TelaListagemMotorista extends JFrame {

	@Autowired
	private MotoristaService service;
	
	@Autowired
	private TelaModificacaoMotorista telaModMotorista;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	public TelaListagemMotorista() {
		
		JTable table = new JTable();
		setTitle("Motorista (LISTAGEM) - SA System 1.6");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				telaModMotorista.cadastrar();
			}
		});
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel lblFiltro = new JLabel("Filtro");
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaListagemMotorista.this.atualizarTabela(table);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerRegistroDa(table);
			}
		});
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarRegistroDa(table);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAdicionar, Alignment.TRAILING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(lblFiltro)
							.addGap(372))
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
						.addComponent(btnListar, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnRemover, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnAdicionar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblFiltro)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnListar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRemover)
						.addComponent(btnEditar))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}
	
	protected void removerRegistroDa(JTable table) {
		try {
			var motoristaSelecionado = getMotoristaSelecionadoNa(table);
			int opcaoSelecionada = JOptionPane.showConfirmDialog(
					contentPane, 
					"Deseja remover?",
					"Remoção", 
					JOptionPane.YES_NO_OPTION);
			
			if (opcaoSelecionada == JOptionPane.YES_OPTION) {
				this.service.excluir(motoristaSelecionado);
				((MotoristaTableModel)table.getModel()).removerPor(opcaoSelecionada);
				table.updateUI();
				JOptionPane.showMessageDialog(contentPane, "Motorista removido com sucesso");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, e.getMessage());
		}
	}

	protected void editarRegistroDa(JTable table) {
		
	}

	private void atualizarTabela(JTable table) {
		try {
			var motoristas = service.listarTodosMotoristas();
			var motoristasTableModel = new MotoristaTableModel(motoristas);
			table.setModel(motoristasTableModel);
			var cm = table.getColumnModel();
			cm.getColumn(0).setPreferredWidth(20);
			cm.getColumn(1).setPreferredWidth(240);
			table.updateUI();
		} catch (JsonProcessingException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		
	}
	
	private Motorista getMotoristaSelecionadoNa(JTable tabela) {
		int linhaSelecionada = tabela.getSelectedRow();
		if (linhaSelecionada < 0)
			throw new IllegalArgumentException("Nenhuma linha selecionada");
		
		var model = (MotoristaTableModel) tabela.getModel();
		var itemSelecionado = model.getPor(linhaSelecionada);
		return itemSelecionado;
	}
}
