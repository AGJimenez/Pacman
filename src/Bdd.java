import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Bdd {
	private static final String CONTROLADOR = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://bxwm1kxr5svmeuproay4-mysql.services.clever-cloud.com:3306/bxwm1kxr5svmeuproay4";
	private static final String USUARIO = "uk6bvuigo3ihabpa";
	private static final String CLAVE = "gkKxwznW6jIqe4yG2ngq";

	static {
		try {
			Class.forName(CONTROLADOR);
		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el controlador");
			e.printStackTrace();
		}
	}
	
	public Connection conectar() {
		Connection conexion = null;
		
		try {
			conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
			System.out.println("Conexión OK");

		} catch (SQLException e) {
			System.out.println("Error en la conexión");
			e.printStackTrace();
		}
		
		return conexion;
	}
	
	   public void insertarPuntos(String nickname, int puntos) {
	        try (Connection conexion = conectar()) {
	            String sql = "INSERT INTO registro (nickname, puntos) VALUES (?, ?)";

	            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
	                pstmt.setString(1, nickname);
	                pstmt.setInt(2, puntos);

	                int filasAfectadas = pstmt.executeUpdate();
	                System.out.println("Filas afectadas: " + filasAfectadas);
	            }
	        } catch (SQLException e) {
	            System.out.println("Error al insertar puntos");
	          
	        }
	    }
	
	   public List<Registro> sacarPuntos() {
	        List<Registro> listaRegistros = new ArrayList<>();

	        try (Connection conexion = conectar()) {
	            String sql = "SELECT * FROM registro";

	            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

	                // Ejecutar la consulta SELECT
	                try (ResultSet resultSet = pstmt.executeQuery()) {
	                    while (resultSet.next()) {
	                        // Aquí puedes procesar los resultados
	                        String resultadoNickname = resultSet.getString("nickname");
	                        int resultadoPuntos = resultSet.getInt("puntos");

	                        // Crear un objeto Registro y agregarlo a la lista
	                        Registro registro = new Registro(resultadoNickname, resultadoPuntos);
	                        listaRegistros.add(registro);
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            System.out.println("Error al ejecutar la consulta");
	            e.printStackTrace();
	        }
	        return listaRegistros;
	    }
	
}
