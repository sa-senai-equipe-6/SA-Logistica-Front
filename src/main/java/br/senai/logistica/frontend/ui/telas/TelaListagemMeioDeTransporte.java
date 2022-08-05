package br.senai.logistica.frontend.ui.telas;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.NoSuchElementException;

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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.senai.logistica.frontend.entity.MeioTransporte;
import br.senai.logistica.frontend.service.MeioTransporteService;
import br.senai.logistica.frontend.ui.table.MotoristaTableModel;
import br.senai.logistica.frontend.ui.table.TransportesTableModel;

@Component
public class TelaListagemMeioDeTransporte extends JFrame {

	@Autowired
	private MeioTransporteService service;

	@Autowired
	@Lazy
	private TelaLogin telaLogin;
	
	@Autowired
	private TelaModificacaoMeioDeTransporte telaModTransporte;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFiltro;

	public TelaListagemMeioDeTransporte() {
		JTable tabela = new JTable();
		setResizable(false);
		setTitle("Meio de Transporte (LISTAGEM) - SA System 1.6");
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		txtFiltro = new JTextField();
		txtFiltro.setColumns(10);

		JLabel lblFiltro = new JLabel("Filtro");
		JScrollPane scrollPane = new JScrollPane();

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(e -> adicionarTransporte());

		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(e -> atualizar(tabela));

		JButton btnRemover = new JButton("Remover");
		btnRemover.addActionListener(e -> removerRegistroDa(tabela));

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(e -> editarRegistroNa(tabela));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING).addComponent(btnAdicionar, Alignment.TRAILING)
						.addGroup(Alignment.TRAILING,
								gl_contentPane.createSequentialGroup().addComponent(lblFiltro).addGap(372))
						.addComponent(txtFiltro, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
						.addComponent(btnListar, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 87,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
								.addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnRemover, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		gl_contentPane
				.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(btnAdicionar)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblFiltro)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(txtFiltro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnListar)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnRemover).addComponent(btnEditar))
								.addContainerGap(14, Short.MAX_VALUE)));

		scrollPane.setViewportView(tabela);
		contentPane.setLayout(gl_contentPane);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		configurarFechamento();
	}

	private void configurarFechamento() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				telaLogin.setVisible(true);
			}
		});
	}

	private void adicionarTransporte() {
		this.setVisible(false);
		telaModTransporte.cadastrar();
	}

	private void removerRegistroDa(JTable tabela) {
		try {
			int linhaSelecionada = tabela.getSelectedRow();
			var transporteSelecionado = getTransporteSelecionadoNa(tabela, linhaSelecionada);
			int opcaoSelecionada = JOptionPane.showConfirmDialog(contentPane, "Deseja remover?", "Remoção",
					JOptionPane.YES_NO_OPTION);

			if (opcaoSelecionada == JOptionPane.YES_OPTION) {
				this.service.excluir(transporteSelecionado);
				((MotoristaTableModel) tabela.getModel()).removerPor(linhaSelecionada);
				tabela.updateUI();
				JOptionPane.showMessageDialog(contentPane, "Motorista removido com sucesso");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, e.getMessage());
		}
	}
	
	private void editarRegistroNa(JTable tabela) {
		int linhaSelecionada = tabela.getSelectedRow();
		var motoristaSelecionado = getTransporteSelecionadoNa(tabela, linhaSelecionada);
		this.setVisible(false);
		telaModTransporte.botarEmEdicao(motoristaSelecionado);
	}

	private void atualizar(JTable tabela) {
		try {
			String filtro = txtFiltro.getText();
			if (filtro == null || filtro.isBlank()) {
				throw new IllegalArgumentException("O filtro é obrigatório");
			}
			var transportes = service.listarPorMotorista(filtro);
			if (transportes == null || transportes.isEmpty()) {
				throw new NoSuchElementException("Nenhum transporte encontrado com esse motorista");
			}
			var transportesTableModel = new TransportesTableModel(transportes);
			tabela.setModel(transportesTableModel);
			var cm = tabela.getColumnModel();
			cm.getColumn(0).setPreferredWidth(20);
			cm.getColumn(1).setPreferredWidth(240);
			tabela.updateUI();
		} catch (JsonProcessingException | IllegalArgumentException | NoSuchElementException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	private MeioTransporte getTransporteSelecionadoNa(JTable tabela, int linhaSelecionada) {
		if (linhaSelecionada < 0)
			throw new IllegalArgumentException("Nenhuma linha selecionada");

		var model = (TransportesTableModel) tabela.getModel();
		var itemSelecionado = model.getPor(linhaSelecionada);
		return itemSelecionado;
	}

}
