package br.senai.logistica.frontend.ui.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.senai.logistica.frontend.entity.Usuario;

@Component
public class TelaPrincipalGestor extends JFrame {

	private static final long serialVersionUID = 1L;

	@Autowired
	private TelaListagemMotorista telaMotoristas;
	
	@Autowired
	@Lazy
	private TelaLogin telaLogin;
	
	@Autowired
	private TelaListagemMeioDeTransporte telaTransportes;
	
	private JPanel contentPane;
	private JTextField lblUsuario;
	private JTextField txtUsuario;

	public TelaPrincipalGestor() {
		setTitle("Principal (Acesso Gestor) - SA System 1.6");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnMotoristas = new JButton("Motoristas");
		btnMotoristas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paraMotoristas();
			}
		});
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		lblUsuario = new JTextField();
		lblUsuario.setFont(lblUsuario.getFont().deriveFont(lblUsuario.getFont().getStyle() | Font.BOLD));
		lblUsuario.setText("Usuario Logado");
		lblUsuario.setColumns(10);
		
		txtUsuario = new JTextField();
		txtUsuario.setColumns(10);
		
		JButton btnMeiosDeTransporte = new JButton("Meios de Transporte");
		btnMeiosDeTransporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paraMeiosDeTransporte();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblUsuario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtUsuario, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(133)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnMotoristas, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnMeiosDeTransporte, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnSair, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGap(58)
					.addComponent(btnMotoristas)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnMeiosDeTransporte)
					.addGap(10)
					.addComponent(btnSair)
					.addPreferredGap(ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsuario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtUsuario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		configurarFechamento();
	}

	public void trocarPara(Usuario usuario) {
		txtUsuario.setText(usuario.getNomeCompleto());
	}
	
	protected void paraMeiosDeTransporte() {
		this.setVisible(false);
		telaTransportes.setVisible(true);
	}

	protected void paraMotoristas() {
		this.setVisible(false);
		telaMotoristas.setVisible(true);
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
