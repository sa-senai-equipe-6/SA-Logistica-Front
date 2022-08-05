package br.senai.logistica.frontend.ui.telas;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.senai.logistica.frontend.entity.Motorista;
import br.senai.logistica.frontend.service.MotoristaService;

@Component
public class TelaModificacaoMeioDeTransporte extends JFrame {

	private static final long serialVersionUID = 1L;

	@Autowired
	@Lazy
	private TelaListagemMeioDeTransporte telaTransporte;
	
	@Autowired
	@Lazy
	private TelaLogin telaLogin;
	
	@Autowired
	private MotoristaService motoristaService;
	
	private JPanel contentPane;
	private JComboBox<Motorista> boxMotorista;
	private JComboBox<String> boxTipo;
	private JTextArea txtDescricao;
	private JFormattedTextField txtRevisao;

	public TelaModificacaoMeioDeTransporte() {
		setTitle("Meio de Transporte (INSERÇÃO/EDIÇÃO) - SA System 1.6");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnConsultar = new JButton("Consultar");
		
		JLabel lblRevisao = new JLabel("Revisão");
		
		txtRevisao = new JFormattedTextField();
		
		boxTipo = new JComboBox<String>();
		boxTipo.addItem("CARRO");
		boxTipo.addItem("MOTO");
		
		JLabel lblTipo = new JLabel("Tipo");
		
		JLabel lblMotorista = new JLabel("Motorista");
		
		boxMotorista = new JComboBox<Motorista>();
		
		JLabel lblDescricao = new JLabel("Descrição");
		
		txtDescricao = new JTextArea();
		
		JButton btnSalvar = new JButton("Salvar");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnConsultar, Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(txtRevisao, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblRevisao))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTipo, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addComponent(boxTipo, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblMotorista, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addComponent(boxMotorista, 0, 430, Short.MAX_VALUE)
						.addComponent(lblDescricao)
						.addComponent(txtDescricao, GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
						.addComponent(btnSalvar, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnConsultar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRevisao)
						.addComponent(lblTipo))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtRevisao, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(boxTipo, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblMotorista)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(boxMotorista, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblDescricao)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtDescricao, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSalvar)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		configurarFechamento();
	}
	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		try {
			var motoristas = motoristaService.listarTodosMotoristas();
			motoristas.forEach(m -> boxMotorista.addItem(m));
		} catch (JsonProcessingException e) {
			JOptionPane.showMessageDialog(contentPane, e.getMessage());
		}
	}

	public void cadastrar() {
		this.limparCampos();
		this.setVisible(true);
	}

	public void botarEmEdicao(Motorista motorista) {
		this.setVisible(true);
	}
	
	private void limparCampos() {
		//TODO: fazer limpar campos
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
}
