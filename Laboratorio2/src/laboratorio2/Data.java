package laboratorio2;

import java.sql.Statement;

import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Data {
	
	private Connection conn;
	private Statement statement;

	public Data(String pAddress, String pPort, String pUser, String pPass) throws SQLException {
		
		try{
			Class.forName("org.gjt.mm.mysql.Driver");
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
		conn = DriverManager.getConnection("jdbc:mysql://"+pAddress+":"+pPort+"/DBay",pUser,pPass);
		JOptionPane.showMessageDialog(null, "Conexión establecida \n IP: "+pAddress+" : "+pPort +"\n Usuario: "+pUser+"\n Contraseña: "+pPass);
		conn.setAutoCommit(true);
		statement = conn.createStatement();
	}
	
	
	public void close() {
		try {
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*************************************************************************
     * Método para realizar consultas del tipo: SELECT * FROM tabla WHERE..."*
     *************************************************************************/
     
	public String[] consultaDatos(String pQuery) {
		// se devuelve un vector de dos posiciones
		// la primera posicion contiene el resultado de la consulta
		// la segunda posicion contiene el número de filas que contiene dicho resultado
		// en caso de error, la primera posición estará vacía y la segunda contendrá el mensaje de error
        String[] result = new String[2];
        try {
        	ResultSet rs = statement.executeQuery(pQuery);
	        StringBuilder rowText = new StringBuilder();
	        int rowcount = 0;
        	int colcount = rs.getMetaData().getColumnCount();
			for(int col = 1; col <= colcount; col++) {
				rowText.append(rs.getMetaData().getColumnLabel(col) + " | ");
			}
			rowText.append(System.lineSeparator() + System.lineSeparator());
			while (rs.next()) {
				for(int col = 1; col <= colcount; col++) {
					rowText.append(rs.getString(col) + " | ");
				}
				rowText.append(System.lineSeparator());
				rowcount++;
			}
			result[0] = rowText.toString();
			result[1] = Integer.toString(rowcount);
		} catch (SQLException e) {
			e.printStackTrace();
			result[0] = "Error";
				String respuesta =  e.getMessage();
			//if(respuesta.contains(" command denied")) {
				String asplitear = respuesta.replace(" command denied", "");
				String[] spliteado = asplitear.split(" ");
				String Operacion = spliteado[0];
				String towhom = spliteado[1]+" "+spliteado[2]+" "+ spliteado[3];
				String forwhat = spliteado[4]+" "+spliteado[5]+" "+ spliteado[6];
				result[1] = Operacion+" "+forwhat+" "+towhom;
				
			//}
			//else {
			//	result[1] = "Syntax Error";
			//}
		}
        return result;
    }
	
	
	/*****************************************************************************************************************
     * Método para realizar consultas de actualización, creación o eliminación (DROP,INSERT INTO, ALTER..., NO SELECT) * 
     *****************************************************************************************************************/
    
    public String[] consultaActualiza(String pSentence) {
		// se devuelve un vector de dos posiciones
		// la primera posicion siempre está vacía
		// la segunda posicion contiene el número de filas afectadas por la operación
		// en caso de error, la primera posición estará vacía y la segunda contendrá el mensaje de error
        String[] result = new String[2];
        try {
			result[1] = Integer.toString(statement.executeUpdate(pSentence));
        } catch (SQLException e) {
        	e.printStackTrace();
			result[1] = e.getMessage();
        }
        return result;
    }

    /*
	public void conectar(String pAddress, String pPort, String pUser, String pPass) throws SQLException {
		
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
		} catch(ClassNotFoundException e){
			e.printStackTrace();
			System.exit(1);
		}
		
		//Open connection
		conn = DriverManager.getConnection(server+pAddress+":"+pPort + "/",pUser,pPass);
		conn.setAutoCommit(true);
		statement = conn.createStatement();
		
	}*/
    
}    