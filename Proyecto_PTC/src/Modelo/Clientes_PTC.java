package Modelo;

import Vista.FrmClientes_PTC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Clientes_PTC {
    int id_cliente;
    String nombre_clie;
    String correoElectronico;
    String direccion_entrega;

    // Getters y Setters
    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre_clie() {
        return nombre_clie;
    }

    public void setNombre_clie(String nombre_clie) {
        this.nombre_clie = nombre_clie;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDireccion_entrega() {
        return direccion_entrega;
    }

    public void setDireccion_entrega(String direccion_entrega) {
        this.direccion_entrega = direccion_entrega;
    }

    // Método para mostrar los datos en la tabla
    public void Mostrar(JTable tabla) {
        Connection conexion = Conexion.getConexion();
        DefaultTableModel modeloClientes = new DefaultTableModel();
        modeloClientes.setColumnIdentifiers(new Object[]{"id_cliente", "nombre_clie", "correoElectronico", "direccion_entrega"});
        
        try {
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id_cliente, nombre_clie, correoElectronico, direccion_entrega FROM clientes_PTC");
            
            while (rs.next()) {
                modeloClientes.addRow(new Object[]{
                    rs.getInt("id_cliente"), 
                    rs.getString("nombre_clie"), 
                    rs.getString("correoElectronico"), 
                    rs.getString("direccion_entrega")
                });
            }
            tabla.setModel(modeloClientes);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos: " + ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para actualizar cliente
    public void Actualizar(JTable tabla) {
        Connection conexion = Conexion.getConexion();

        try {
            PreparedStatement updateCliente = conexion.prepareStatement(
                "UPDATE clientes_PTC SET nombre_clie = ?, correoElectronico = ?, direccion_entrega = ? WHERE id_cliente = ?");

            updateCliente.setString(1, getNombre_clie());
            updateCliente.setString(2, getCorreoElectronico());
            updateCliente.setString(3, getDireccion_entrega());
            updateCliente.setInt(4, getId_cliente());

            int result = updateCliente.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Cliente actualizado correctamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el cliente", "Mensaje", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el cliente: " + ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para eliminar cliente
    public void Eliminar(JTable tabla) {
        Connection conexion = Conexion.getConexion();
        try {
            PreparedStatement deleteCliente = conexion.prepareStatement("DELETE FROM clientes_PTC WHERE id_cliente = ?");

            deleteCliente.setInt(1, getId_cliente());

            int result = deleteCliente.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el cliente", "Mensaje", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el cliente: " + ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Método para limpiar los campos del formulario
    public void limpiar(FrmClientes_PTC vista) {
        vista.txtNombreCliente.setText("");
        vista.txtCorreo.setText("");
        vista.txtDirrecion.setText("");
    }

    // Método para cargar datos de la tabla del formulario
    public void cargarDatosTabla(FrmClientes_PTC vista) {
        int filaSeleccionada = vista.jtblClientes.getSelectedRow();
    
        if (filaSeleccionada != -1) {
            Object id_cliente = vista.jtblClientes.getValueAt(filaSeleccionada, 0);
            Object nombre_clie = vista.jtblClientes.getValueAt(filaSeleccionada, 1);
            Object correoElectronico = vista.jtblClientes.getValueAt(filaSeleccionada, 2);
            Object direccion_entrega = vista.jtblClientes.getValueAt(filaSeleccionada, 3);

            // Validar si los valores son null antes de convertirlos a string
            setId_cliente(id_cliente != null ? Integer.parseInt(id_cliente.toString()) : 0);
        
            vista.txtNombreCliente.setText(nombre_clie != null ? nombre_clie.toString() : "");
            vista.txtCorreo.setText(correoElectronico != null ? correoElectronico.toString() : "");
            vista.txtDirrecion.setText(direccion_entrega != null ? direccion_entrega.toString() : "");
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún cliente", "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }
}
