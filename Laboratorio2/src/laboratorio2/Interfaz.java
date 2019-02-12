package laboratorio2;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Interfaz extends JFrame{

	private JTextField serverAddrField;
	private JTextField mysqlPortField;
	private JTextArea sqlSentenceArea;
	private JTextArea informationArea;
	private static JTextArea notificationArea;
	public JButton btnLogin;
	public JButton btnLogout;
	public JButton btnQuery;
	public JButton btnExecute;
	Data data;
	

	/**
	 * Create the application.
	 */
	public Interfaz() {
		initialize();
	}

	private void initialize() {
		
		this.setBounds(100, 100, 450, 655);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		// campo de texto del servidor
		serverAddrField = new JTextField();
		serverAddrField.setToolTipText("Server address");
		serverAddrField.setBounds(20, 30, 100, 20);
		this.getContentPane().add(serverAddrField);
		serverAddrField.setColumns(10);
		
		// campo de texto del puerto
		mysqlPortField = new JTextField();
		mysqlPortField.setToolTipText("Port");
		mysqlPortField.setBounds(150, 30, 55, 20);
		this.getContentPane().add(mysqlPortField);
		mysqlPortField.setColumns(10);
		
		// bot�n de login
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(230, 30, 89, 23);
		this.getContentPane().add(btnLogin);
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// se instancia una ventana LoginInterfaz con informaci�n de
				// la direcci�n y el puerto del servidor
				String port = mysqlPortField.getText();
				String address = serverAddrField.getText();
				
				if(port.isEmpty() || address.isEmpty()){
					JOptionPane.showMessageDialog(Interfaz.this, "�No dejes campos vac�os!");
				}
				else{
					
					String user = JOptionPane.showInputDialog(Interfaz.this, "Introduce el usuario MySQL");
					String pass = JOptionPane.showInputDialog(Interfaz.this, "Introduce la contrase�a");
					
					try {
						data = new Data(address, port, user, pass);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					mysqlPortField.setEnabled(false);
					serverAddrField.setEnabled(false);
				}
			}
			
		});
		
		// bot�n de logout
		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds(325, 30, 89, 23);
		this.getContentPane().add(btnLogout);
		btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// se cierra la conexi�n establecida
				data.close();
				data = null;
				JOptionPane.showMessageDialog(Interfaz.this, "Conexi�n cerrada");
				mysqlPortField.setEnabled(true);
				serverAddrField.setEnabled(true);
			}
			
		});
		
		// campo de texto de la sentencia sql
		sqlSentenceArea = new JTextArea();
		sqlSentenceArea.setToolTipText("SQL Sentence");
		sqlSentenceArea.setBounds(15, 100, 300, 100);
		this.getContentPane().add(sqlSentenceArea);
		sqlSentenceArea.setColumns(10);
		
		// bont�n de ejecutar consulta
		JButton btnQuery = new JButton("Query");
		btnQuery.setBounds(340, 110, 89, 23);
		this.getContentPane().add(btnQuery);
		btnQuery.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// se recoge y ejecuta la consulta
				// lo resultados se escriben en la interfaz y por consola
				String query = sqlSentenceArea.getText();
				String[] info = data.consultaDatos(query);
				informationArea.setText(info[0]);
				notificationArea.setText(info[1]);
				System.out.println("information:\n" + info[0] + "\n");
				System.out.println("notification:\n" + info[1]);
			}
			
		});
		
		// bont�n de ejecutar operaci�n
		JButton btnExecute = new JButton("Execute");
		btnExecute.setBounds(340, 160, 89, 23);
		this.getContentPane().add(btnExecute);
		btnExecute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// se recoge y ejecuta la operaci�n
				// lo resultados se escriben en la interfaz y por consola
				String sentence = sqlSentenceArea.getText();
				String info[] = data.consultaActualiza(sentence);
				informationArea.setText(info[0]);
				notificationArea.setText(info[1]);
				System.out.println("information:\n" + info[0] + "\n");
				System.out.println("notification:\n" + info[1]);
			}
			
		});
		
		// campo de texto de informaci�n
		informationArea = new JTextArea();
		informationArea.setToolTipText("Information Area");
		informationArea.setBounds(15, 260, 400, 150);
		this.getContentPane().add(informationArea);
		informationArea.setColumns(10);
		
		// campo de texto de notificaciones
		notificationArea = new JTextArea();
		notificationArea.setToolTipText("Notification Area");
		notificationArea.setBounds(15, 450, 400, 150);
		this.getContentPane().add(notificationArea);
		notificationArea.setColumns(10);
		
	}
	


}