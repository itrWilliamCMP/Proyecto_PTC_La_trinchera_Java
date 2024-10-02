 package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class IngresoUsuario {
    
    public static boolean ValidarUsuario(String usuario_empleado, String contrasena_empleado){
        Connection conexion = Conexion.getConexion();
        
        String hash512 = HashUtil.generateSHA512Hash(contrasena_empleado.trim());
        
        boolean esUsusarioValido = false;
        try {
            //Creamos el PreparedStatement que ejecutará la Query
            PreparedStatement stmtUsuario = conexion.prepareStatement("select * from Empleados_PTC where usuario_empleado=? and contrasena_empleado=?");
            //Establecer valores de la consulta SQL
            stmtUsuario.setString(1, usuario_empleado.trim());
            stmtUsuario.setString(2, hash512);

            // Ejecutar la consulta
            ResultSet rs = stmtUsuario.executeQuery();
           
            // Procesar el resultado
            if (rs.next()) {
                // Si se encontró un resultado, mostrar mensaje de éxito
                JOptionPane.showMessageDialog(null, "Usuario validado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                esUsusarioValido = true;
            } else {
                // Si no se encontró ningún resultado, mostrar mensaje de error
                System.out.println("USUARIO O CLAVE NO VALIDO. USUARIO: "+usuario_empleado+" CLAVE: "+contrasena_empleado);
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                rs.close();
                esUsusarioValido = false;
            }

            // Cerrar el ResultSet y el PreparedStatement
            rs.close();
            stmtUsuario.close();
            conexion.close();
            
           
        } catch (SQLException ex) {
             System.out.println("Este es el error en el modelo: método ValidarUsuario " + ex);
             JOptionPane.showMessageDialog(null, "Error en la conexión a la base de datos: " + 
                     ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
             esUsusarioValido = false;
        }        
        
        return esUsusarioValido;
    }
}
