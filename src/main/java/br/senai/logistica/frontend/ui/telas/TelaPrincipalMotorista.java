package br.senai.logistica.frontend.ui.telas;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.senai.logistica.frontend.service.MotoristaService;

@Component
public class TelaPrincipalMotorista extends JFrame {

	private static final long serialVersionUID = 1L;

	@Autowired
	private MotoristaService service;
	
	@Autowired
	@Lazy
	private TelaLogin telaLogin;
	
	private JPanel contentPane;
	private JTextField txtUsuarioLogado;
	private JTextField txtUsuario;

	public TelaPrincipalMotorista() {
		setTitle("Principal (Acesso Motorista) - SA System 1.6");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnMotorista = new JButton("Motorista");
		
		JButton btnSair = new JButton("Sair");
		
		btnSair.addActionListener(e -> {
			this.setVisible(false);
			telaLogin.setVisible(true);
		});
		
		txtUsuarioLogado = new JTextField();
		txtUsuarioLogado.setText("Usuario Logado");
		txtUsuarioLogado.setColumns(10);
		
		txtUsuario = new JTextField();
		txtUsuario.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(txtUsuarioLogado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtUsuario, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(161)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnMotorista, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnSair, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED, 145, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(87)
					.addComponent(btnMotorista)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSair)
					.addPreferredGap(ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtUsuarioLogado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtUsuario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		configurarFechamento();
	}

	public void trocarPara(Integer id) {
		try {
			var recebido = service.buscarPor(id);
			this.txtUsuario.setText(recebido.getUsuario().getNomeCompleto());
		} catch (JsonProcessingException e) {
			JOptionPane.showMessageDialog(contentPane, e.getMessage());
		}
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
