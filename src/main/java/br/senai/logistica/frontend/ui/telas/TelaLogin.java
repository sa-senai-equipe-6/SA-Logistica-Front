package br.senai.logistica.frontend.ui.telas;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import br.senai.logistica.frontend.entity.Usuario;
import br.senai.logistica.frontend.service.UsuarioService;

@Component
public class TelaLogin extends JFrame {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private TelaPrincipalGestor telaGestor;
	
	@Autowired
	private TelaPrincipalMotorista telaMotorista;
	
	private ActionListener loginListener = e -> tentarLogin();
	
	private JPanel contentPane;
	private JTextField txtLogin;
	private JPasswordField fldSenha;

	public TelaLogin() {
		setTitle("Login - SA System 1.6");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 240, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnLogin = new JButton("Logar");
		btnLogin.addActionListener(loginListener);
		
		JLabel lblLogin = new JLabel("Login");
		
		txtLogin = new JTextField();
		txtLogin.setColumns(10);
		txtLogin.addActionListener(loginListener);
		
		JLabel lblSenha = new JLabel("Senha");
		
		fldSenha = new JPasswordField();
		fldSenha.addActionListener(loginListener);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblLogin)
								.addComponent(lblSenha)
								.addComponent(txtLogin, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
								.addComponent(fldSenha)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(49)
							.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(33, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblLogin)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblSenha)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(fldSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnLogin)
					.addContainerGap(33, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		setResizable(false);
		setLocationRelativeTo(null);
		this.setVisible(true);
	}

	protected boolean validarCampos() {
		this.resetarCampos();
		var login = txtLogin.getText();
		var senha = new String(fldSenha.getPassword());
		if (login == null || login.isBlank()) {
			txtLogin.setBorder(BorderFactory.createLineBorder(Color.red));
			JOptionPane.showMessageDialog(this, "O login é obrigatório");
			return false;
		} else if (senha == null || senha.isBlank()) {
			fldSenha.setBorder(BorderFactory.createLineBorder(Color.red));
			JOptionPane.showMessageDialog(this, "A senha é obrigatória");
			return false;
		}
		return true;
	}

	private void tentarLogin() {
		try {
			if (validarCampos()) {
				var usuarioLogado = usuarioService.loginCom(txtLogin.getText(), new String(fldSenha.getPassword()));
				limparCampos();
				trocarTelaPara(usuarioLogado);
			}
		} catch (ResourceAccessException rae) {
			rae.printStackTrace();
			JOptionPane.showMessageDialog(TelaLogin.this, "Um erro inesperado ocorreu, tente novamente");
		} catch (Exception e2) {
			e2.printStackTrace();
			JOptionPane.showMessageDialog(TelaLogin.this, e2.getMessage());
		}
	}
	
	private void limparCampos() {
		this.txtLogin.setText("");
		this.fldSenha.setText("");
	}

	private void resetarCampos() {
		txtLogin.setBorder(null);
		fldSenha.setBorder(null);
	}

	private void trocarTelaPara(Usuario usuario) {
		this.setVisible(false);
		
		switch (usuario.getPerfil()) {
		case MOTORISTA:
			telaMotorista.trocarPara(usuario.getId());
			telaMotorista.setVisible(true);
			break;
			
		case GESTOR:
			telaGestor.trocarPara(usuario);
			telaGestor.setVisible(true);
			break;
		}
	}
	
}
