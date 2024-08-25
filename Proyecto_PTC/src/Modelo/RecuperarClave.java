package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class RecuperarClave {
    
     public static void enviarCodigo(String usuario_empleado, String correoElectronico){
         
        var codigoVerificacion = CodigoRamdon.generarCodigo();
         
        Connection conexion = Conexion.getConexion();
         
        try {
            //Creamos el PreparedStatement que ejecutará la Query
            PreparedStatement stmtUsuario = conexion.prepareStatement("update Empleados_PTC set CodigoTemporal=? where usuario_empleado=? and correoElectronico=?");
            //Establecer valores de la consulta SQL
           
            stmtUsuario.setString(1, codigoVerificacion);
            stmtUsuario.setString(2, usuario_empleado.trim());
            stmtUsuario.setString(3, correoElectronico.trim());

            // Ejecutar la consulta
            int filasAfectadas = stmtUsuario.executeUpdate();
                
            if (filasAfectadas > 0) {
                System.out.println("El código temporal se ha actualizado correctamente para el usuario: " + usuario_empleado);
                var result = EnviarCorreo.enviarCorreoConCodigo(correoElectronico,codigoVerificacion);
                
                if (result){
                    JOptionPane.showMessageDialog(null, "Código de verificación ha sido enviado a su correo", "Error", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, "No se pudo realizar el envio del correo", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("Usuario o Correo no son validos, no se puede enviar el codigo.");
                JOptionPane.showMessageDialog(null, "Usuario o Correo no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Cerrar el PreparedStatement
            stmtUsuario.close();
            conexion.close();
                
        } catch (SQLException ex) {
             System.out.println("Este es el error en el modelo: método ValidarUsuario " + ex);
             JOptionPane.showMessageDialog(null, "Error en la conexión a la base de datos: " + 
                     ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }  
     }
     
     
     public static boolean actualizarClave(String usuario_empleado, String codigoVerificacion, String nuevaClave){
         
        Connection conexion = Conexion.getConexion();
        
        codigoVerificacion = codigoVerificacion.trim();
        usuario_empleado = usuario_empleado.trim();
        nuevaClave = nuevaClave.trim();
                
        
        if (codigoVerificacion.length()!=8){
            JOptionPane.showMessageDialog(null, "El código de verificación no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }        
        
        var nuevoHash = HashUtil.generateSHA512Hash(nuevaClave);
        
        boolean seActualizoClave = false;
        
        try {
            //Creamos el PreparedStatement que ejecutará la Query
            PreparedStatement stmtUsuario = conexion.prepareStatement("update Empleados_PTC set contrasena_empleado=?, CodigoTemporal='' where usuario_empleado=? and CodigoTemporal=?");
            //Establecer valores de la consulta SQL
            
            stmtUsuario.setString(1, nuevoHash);
            stmtUsuario.setString(2, usuario_empleado);
            stmtUsuario.setString(3, codigoVerificacion);

            // Ejecutar la consulta
            int filasAfectadas = stmtUsuario.executeUpdate();
            
            // Cerrar el PreparedStatement
            stmtUsuario.close();
            conexion.close();
            
            if (filasAfectadas > 0) {
                System.out.println("La clave se ha actualizado correctamente para el usuario: " + usuario_empleado);
                JOptionPane.showMessageDialog(null, "Clave actualizada correctamente.", "Error", JOptionPane.INFORMATION_MESSAGE);
                seActualizoClave = true;
            } else {
                System.out.println("No se pudo actualizar la clave del usuario: "+ usuario_empleado);
                JOptionPane.showMessageDialog(null, 
                        "No se pudo actualizar la clave, verifique el nombre de usuario y código de verificación", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                seActualizoClave = false;
            }

        } catch (SQLException ex) {
             System.out.println("Este es el error en el modelo: método ValidarUsuario " + ex);
             JOptionPane.showMessageDialog(null, "Error en la conexión a la base de datos: " + 
                     ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
              seActualizoClave = false;
        }          
         return seActualizoClave;
     }
}

