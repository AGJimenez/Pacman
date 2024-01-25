import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Bdd {
	private static final String CONTROLADOR = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://bxwm1kxr5svmeuproay4-mysql.services.clever-cloud.com:3306/bxwm1kxr5svmeuproay4?useSSL=false";
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
	

	   
	   public boolean sacarPuntos(Score score, int posicion) {
		    try (Connection conexion = conectar()) {
		        String sql = "SELECT nickname, puntos FROM registro ORDER BY puntos DESC LIMIT ?, 7";

		        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
		            pstmt.setInt(1, Math.max(0, posicion * 7));  // Asegurarse de que no sea menos de 0

		            try (ResultSet rs = pstmt.executeQuery()) {
		                List<String> nicknames = new ArrayList<>();
		                List<Integer> puntos = new ArrayList<>();

		                int contador = 0;

		                while (rs.next() && contador < 7) {
		                    String nickname = rs.getString("nickname");
		                    int puntuacion = rs.getInt("puntos");

		                    nicknames.add(nickname);
		                    puntos.add(puntuacion);

		                    contador++;
		                }

		                // Actualizar la interfaz de usuario con los datos obtenidos
		                score.actualizarInterfazUsuario(nicknames, puntos, contador);

		                // Devolver true si hay más elementos disponibles para avanzar
		                return contador == 7;

		            }
		        }
		    } catch (SQLException e) {
		        System.out.println("Error al obtener puntos");
		        e.printStackTrace();
		        // Devolver false en caso de error
		        return false;
		    }
		}

	    
	    public int obtenerTotalRegistros() {
	        try (Connection conexion = conectar()) {
	            String sql = "SELECT COUNT(*) AS total FROM registro";

	            try (PreparedStatement pstmt = conexion.prepareStatement(sql);
	                 ResultSet rs = pstmt.executeQuery()) {

	                if (rs.next()) {
	                    return rs.getInt("total");
	                }

	            }
	        } catch (SQLException e) {
	            System.out.println("Error al obtener el total de registros");
	            e.printStackTrace();
	        }

	        return 0;
	    }

}
