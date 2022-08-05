package br.senai.logistica.frontend.ui.telas;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
import javax.swing.text.MaskFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.senai.logistica.frontend.entity.MeioTransporte;
import br.senai.logistica.frontend.entity.Motorista;
import br.senai.logistica.frontend.entity.Tipo;
import br.senai.logistica.frontend.service.MeioTransporteService;
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
	private MeioTransporteService service;
	
	@Autowired
	private MotoristaService motoristaService;
	
	private JPanel contentPane;
	private JComboBox<Motorista> boxMotorista;
	private JComboBox<Tipo> boxTipo;
	private JTextArea txtDescricao;
	private JFormattedTextField txtRevisao;

	private MeioTransporte meioTransporte;

	private DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Autowired
	private ObjectMapper mapper;

	public TelaModificacaoMeioDeTransporte() {
		setTitle("Meio de Transporte (INSERÇÃO/EDIÇÃO) - SA System 1.6");
		setBounds(100, 100, 480, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(e -> {
			this.setVisible(false);
			this.telaTransporte.setVisible(true);
		});
		
		JLabel lblRevisao = new JLabel("Revisão");
		
		txtRevisao = new JFormattedTextField(getMascara());
		
		boxTipo = new JComboBox<Tipo>();
		boxTipo.addItem(Tipo.CARRO);
		boxTipo.addItem(Tipo.MOTO);
		
		JLabel lblTipo = new JLabel("Tipo");
		
		JLabel lblMotorista = new JLabel("Motorista");
		
		boxMotorista = new JComboBox<Motorista>();
		
		JLabel lblDescricao = new JLabel("Descrição");
		
		txtDescricao = new JTextArea();
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(e -> {
			salvarTransporte();
		});
		
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
		setLocationRelativeTo(null);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		configurarFechamento();
	}
	
	private void salvarTransporte() {
		try {
			getMeioTranporte();
			
			if (this.meioTransporte.getId() == null) {
				MeioTransporte cadastrado = service.cadastrar(this.meioTransporte);
				this.meioTransporte.setId(cadastrado.getId());
				this.meioTransporte.setMotorista(cadastrado.getMotorista());
				JOptionPane.showMessageDialog(contentPane, "Motorista cadastrado com sucesso!");				
			} else {
				service.editar(this.meioTransporte);
				JOptionPane.showMessageDialog(contentPane, "Motorista editado com sucesso!");				
			}
		} catch (DateTimeParseException dtpe) {
			JOptionPane.showMessageDialog(contentPane, "Data inválida, tente novamente");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(contentPane, e.getMessage());
		}
	}

	private void getMeioTranporte() {
		this.meioTransporte.setDescricao(txtDescricao.getText());
		this.meioTransporte.setTipoVeiculo((Tipo) boxTipo.getSelectedItem());
		formatarData();
		getMotorista();
	}

	private void getMotorista() {
		var selecionado = (Motorista) boxMotorista.getSelectedItem();
		try {
			System.out.println(mapper.writeValueAsString(selecionado));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		this.meioTransporte.setMotorista(selecionado);
	}

	private void formatarData() {
		this.meioTransporte.setDataRevisao(LocalDate.parse(txtRevisao.getText(), formatoData));
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if (b) {
			try {
				var motoristas = motoristaService.listarTodosMotoristas();
				motoristas.forEach(m -> boxMotorista.addItem(m));
			} catch (JsonProcessingException e) {
				JOptionPane.showMessageDialog(contentPane, e.getMessage());
			}			
		}
	}

	public void cadastrar() {
		this.limparCampos();
		this.meioTransporte = new MeioTransporte();
		this.setVisible(true);
	}

	public void botarEmEdicao(MeioTransporte meioTransporteSelecionado) {
		this.setVisible(true);
		this.meioTransporte = meioTransporteSelecionado;
		try {
			System.out.println(mapper.writeValueAsString(this.meioTransporte));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		this.txtDescricao.setText(meioTransporteSelecionado.getDescricao());
		this.txtRevisao.setText(meioTransporteSelecionado.getDataRevisao().format(formatoData));
		this.boxTipo.setSelectedItem(meioTransporteSelecionado.getTipoVeiculo());
		this.boxMotorista.setSelectedItem(meioTransporteSelecionado.getMotorista());
	}
	
	private MaskFormatter getMascara() {
		try {
			return new MaskFormatter("##/##/####");
		} catch (ParseException e) {
			return null;
		}
	}
	
	private void limparCampos() {
		boxMotorista.setSelectedIndex(-1);
		boxTipo.setSelectedIndex(-1);
		txtDescricao.setText("");
		txtRevisao.setText("");
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
