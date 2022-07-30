package br.senai.logistica.frontend.ui.telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.senai.logistica.frontend.entity.Motorista;
import br.senai.logistica.frontend.entity.Perfil;
import br.senai.logistica.frontend.entity.Usuario;
import br.senai.logistica.frontend.service.MotoristaService;

@Component
public class TelaModificacaoMotorista extends JFrame {

	@Autowired
	private MotoristaService service;
	
	@Autowired
	@Lazy
	private TelaListagemMotorista telaMotoristas;
	
	private static final long serialVersionUID = 1L;
	private DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private JPanel contentPane;
	private JTextField txtLogin;
	private JTextField txtSenha;
	private JTextField txtCnh;
	private JTextField txtNomeCompleto;
	private JLabel lblRenovacao;
	private JLabel lblCnh;
	private JLabel lblCategoria;
	private JLabel lblLogin;
	private JLabel lblSenha;
	private JComboBox<Character> boxCategoria;
	private JFormattedTextField txtRenovacao;

	public TelaModificacaoMotorista() {
		setResizable(false);
		setTitle("Motorista (INSERÇÃO/EDIÇÃO) - SA System 1.6");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 399, 288);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		txtLogin = new JTextField();
		txtLogin.setColumns(10);
		
		txtSenha = new JTextField();
		txtSenha.setColumns(10);
		
		txtCnh = new JTextField();
		txtCnh.setColumns(10);
		
		txtNomeCompleto = new JTextField();
		txtNomeCompleto.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome Completo");
		
		DateFormat formatoDataTxt = new SimpleDateFormat("dd/MM/yyyy");
		txtRenovacao = new JFormattedTextField(formatoDataTxt);
		
		lblRenovacao = new JLabel("Renovação");
		
		lblCnh = new JLabel("CNH");
		
		lblCategoria = new JLabel("Categoria");
		
		lblLogin = new JLabel("Login");
		
		lblSenha = new JLabel("Senha");
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrarMotorista();
			}
		});
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				TelaModificacaoMotorista.this.telaMotoristas.setVisible(true);
			}
		});
		
		boxCategoria = new JComboBox<Character>();
		boxCategoria.addItem('A');
		boxCategoria.addItem('B');
		boxCategoria.addItem('C');
		boxCategoria.addItem('D');
		boxCategoria.addItem('E');
		
		boxCategoria.setName("Teste");
		boxCategoria.setToolTipText("Selecione uma categoria");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblNome)
											.addGap(162))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(txtNomeCompleto, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)))
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblRenovacao, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtRenovacao, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnConsultar)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(txtCnh, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
										.addComponent(txtLogin, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
										.addComponent(lblCnh, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblLogin, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
											.addComponent(boxCategoria, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addComponent(txtSenha, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblSenha, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)))
										.addComponent(lblCategoria, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))))))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnConsultar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNome)
						.addComponent(lblRenovacao))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtRenovacao, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtNomeCompleto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCategoria)
						.addComponent(lblCnh))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtCnh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(boxCategoria, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSenha)
						.addComponent(lblLogin))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(txtSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSalvar)
					.addGap(119))
		);
		contentPane.setLayout(gl_contentPane);
		setLocationRelativeTo(null);
	}

	protected void cadastrarMotorista() {
		try {
			var novoMotorista = new Motorista();
			novoMotorista.setCnh(txtCnh.getText());
			novoMotorista.setCategoria((Character) boxCategoria.getSelectedItem());
			novoMotorista.setUsuario(
					new Usuario(txtNomeCompleto.getText(),
							txtLogin.getText(),
							txtSenha.getText(),
							Perfil.MOTORISTA));
			novoMotorista.setDataRenovacao(LocalDate.parse(txtRenovacao.getText(), formatoData));
			service.cadastrar(novoMotorista);
			JOptionPane.showMessageDialog(contentPane, "Motorista cadastrado com sucesso!");
		} catch (DateTimeParseException dtpe) {
			JOptionPane.showMessageDialog(contentPane, "Data inválida, tente novamente\nFormato da data: XX/XX/XXXX");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, e.getMessage());
		}
	}

	public void cadastrar() {
		this.limparCampos();
		this.setVisible(true);
	}

	public void botarEmEdicao(Motorista motorista) {
		txtCnh.setText(motorista.getCnh());
		txtLogin.setText(motorista.getUsuario().getLogin());
		txtNomeCompleto.setText(motorista.getUsuario().getNomeCompleto());
		txtSenha.setText(motorista.getUsuario().getSenha());
		this.setVisible(true);
	}
	
	private void limparCampos() {
		txtCnh.setText("");
		txtLogin.setText("");
		txtNomeCompleto.setText("");
		txtSenha.setText("");
	}
}
