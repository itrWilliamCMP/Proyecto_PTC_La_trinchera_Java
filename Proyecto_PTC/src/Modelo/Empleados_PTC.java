package Modelo;

import Vista.FrmEmpleados_PTC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Empleados_PTC {
    int id_empleado;
    String usuario_empleado;
    String correoElectronico;
    String contrasena_empleado;
    String CodigoTemporal;

    // Getters and Setters
    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getUsuario_empleado() {
        return usuario_empleado;
    }

    public void setUsuario_empleado(String usuario_empleado) {
        this.usuario_empleado = usuario_empleado;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena_empleado() {
        return contrasena_empleado;
    }

    public void setContrasena_empleado(String contrasena_empleado) {
        this.contrasena_empleado = contrasena_empleado;
    }

    public String getCodigoTemporal() {
        return CodigoTemporal;
    }

    public void setCodigoTemporal(String CodigoTemporal) {
        this.CodigoTemporal = CodigoTemporal;
    }

    // Método para guardar empleado (Insertar)
    public void Guardar() {
        Connection conexion = Conexion.getConexion();
        
        System.out.println("usuario: "+getUsuario_empleado());
         System.out.println("correoElectronico: "+getCorreoElectronico());
          System.out.println("contrasena_empleado: "+getContrasena_empleado());
        
        try {
            PreparedStatement addEmpleado = conexion.prepareStatement("INSERT INTO Empleados_PTC (usuario_empleado, correoElectronico, contrasena_empleado) VALUES (?, ?, ?)");

            addEmpleado.setString(1, getUsuario_empleado());
            addEmpleado.setString(2, getCorreoElectronico());
            addEmpleado.setString(3, getContrasena_empleado());

            addEmpleado.executeUpdate();
 
        } catch (SQLException ex) {
            System.out.println("Error en el modelo: método Guardar " + ex);
        }
    }

    // Método para actualizar empleado (solo usuario, correo y contraseña)
    public void Actualizar(JTable tabla) {
        Connection conexion = Conexion.getConexion();
        try {
            PreparedStatement updateEmpleado = conexion.prepareStatement("UPDATE Empleados_PTC SET usuario_empleado = ?, correoElectronico = ?, contrasena_empleado = ? WHERE id_empleado = ?");

            updateEmpleado.setString(1, getUsuario_empleado());
            updateEmpleado.setString(2, getCorreoElectronico());
            updateEmpleado.setString(3, getContrasena_empleado());
            updateEmpleado.setInt(4, getId_empleado());

            updateEmpleado.executeUpdate();
 
        } catch (SQLException ex) {
            System.out.println("Error en el modelo: método Actualizar " + ex);
        }
    }
    
    public void limpiar(FrmEmpleados_PTC vista) {
        vista.txtUsuarios.setText("");
        vista.txtCorreo.setText("");
        vista.txtContrasena.setText("");
    }

    // Método para eliminar empleado 
    public void Eliminar(JTable tabla) {
        Connection conexion = Conexion.getConexion();
        try {
            PreparedStatement deleteEmpleado = conexion.prepareStatement("DELETE FROM Empleados_PTC WHERE id_empleado = ?");

            deleteEmpleado.setInt(1, getId_empleado());

            deleteEmpleado.executeUpdate();
 
        } catch (SQLException ex) {
            System.out.println("Error en el modelo: método Eliminar " + ex);
        }
    }

    // Método para mostrar todos los empleados en la tabla
    public void Mostrar(JTable tabla) {
        Connection conexion = Conexion.getConexion();
        DefaultTableModel modeloEmpleados = new DefaultTableModel();
        modeloEmpleados.setColumnIdentifiers(new Object[]{"id_empleado", "usuario_empleado", "correoElectronico", "contrasena_empleado", "CodigoTemporal"});
        try {
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Empleados_PTC");
            while (rs.next()) {
                modeloEmpleados.addRow(new Object[]{
                    rs.getInt("id_empleado"), 
                    rs.getString("usuario_empleado"), 
                    rs.getString("correoElectronico"), 
                    rs.getString("contrasena_empleado"), 
                    rs.getString("CodigoTemporal") // Se muestra aunque esté vacío
                });
            }
            tabla.setModel(modeloEmpleados);
        } catch (Exception e) {
            System.out.println("Error en el modelo: método Mostrar " + e);
        }
    }

    // Método para cargar datos de la tabla del formulario
    public void cargarDatosTabla(FrmEmpleados_PTC vista) {
        int filaSeleccionada = vista.jTblEmpleados.getSelectedRow();
        
        System.out.println("filaSeleccionada = "+filaSeleccionada);
        
        System.out.println(vista.jTblEmpleados.getValueAt(filaSeleccionada, 0));
        
        if (filaSeleccionada != -1) {
            String id_empleado = vista.jTblEmpleados.getValueAt(filaSeleccionada, 0).toString();            
            String usuario_empleado = vista.jTblEmpleados.getValueAt(filaSeleccionada, 1).toString();
            String correoElectronico = vista.jTblEmpleados.getValueAt(filaSeleccionada, 2).toString();
            String contrasena_empleado = vista.jTblEmpleados.getValueAt(filaSeleccionada, 3).toString();
            //tring CodigoTemporal = vista.jTblEmpleados.getValueAt(filaSeleccionada, 4).toString();

            setId_empleado(Integer.parseInt(id_empleado));
                        
            vista.txtUsuarios.setText(usuario_empleado);
            vista.txtCorreo.setText(correoElectronico);
            vista.txtContrasena.setText(contrasena_empleado);
           
        }
    }
}

