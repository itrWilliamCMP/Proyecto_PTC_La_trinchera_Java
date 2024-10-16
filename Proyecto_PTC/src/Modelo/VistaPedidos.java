package Modelo;

import Vista.FrmPedidos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

public class VistaPedidos {
    int id_pedido;
    String fecha_hora_pedido;
    String nombre_clie;
    String estado;

    // Getters y Setters
    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getFecha_hora_pedido() {
        return fecha_hora_pedido;
    }

    public void setFecha_hora_pedido(String fecha_hora_pedido) {
        this.fecha_hora_pedido = fecha_hora_pedido;
    }

    public String getNombre_clie() {
        return nombre_clie;
    }

    public void setNombre_clie(String nombre_clie) {
        this.nombre_clie = nombre_clie;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Método para actualizar el estado del pedido
    // Método para actualizar pedido
public void Actualizar(String EstadoPedido) {
    Connection conexion = Conexion.getConexion();
    try {
        PreparedStatement updatePedido = conexion.prepareStatement("UPDATE PEDIDOS_PTC SET Estado = ? WHERE id_pedido = ?");
        
        updatePedido.setString(1, EstadoPedido);
        //updatePedido.setString(1, getEstado());
        updatePedido.setInt(2, getId_pedido());

        updatePedido.executeUpdate();

    } catch (SQLException ex) {
        System.out.println("Error en el modelo: método Actualizar " + ex);
    }
}

    // Método para mostrar los pedidos en la tabla
    public void Mostrar(JTable tabla) {
        Connection conexion = Conexion.getConexion();
        DefaultTableModel modeloPedidos = new DefaultTableModel();
        modeloPedidos.setColumnIdentifiers(new Object[]{"id_pedido", "fecha_hora_pedido", "nombre_clie", "Estado"});
        
        try {
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery(
                "SELECT p.id_pedido, p.fecha_hora_pedido, c.nombre_clie, p.Estado " +
                "FROM PEDIDOS_PTC p " +
                "INNER JOIN clientes_PTC c ON p.id_cliente = c.id_cliente"
            );

            // Verifica si hay datos en el ResultSet
            if (!rs.isBeforeFirst()) {
                System.out.println("No se encontraron datos");
            } else {
                while (rs.next()) {
                    modeloPedidos.addRow(new Object[]{
                        rs.getInt("id_pedido"), 
                        rs.getString("fecha_hora_pedido"), 
                        rs.getString("nombre_clie"), 
                        rs.getString("Estado")
                    });
                }
            }
            tabla.setModel(modeloPedidos);
        } catch (SQLException e) {
            System.out.println("Error en el modelo: método Mostrar " + e);
        }
    }

    // Método para cargar datos de la tabla
    public void cargarDatosTabla(FrmPedidos vista) {
        int filaSeleccionada = vista.jtblPedidos.getSelectedRow();
        
        if (filaSeleccionada != -1) {
            String id_pedido = vista.jtblPedidos.getValueAt(filaSeleccionada, 0).toString();
            String estado = vista.jtblPedidos.getValueAt(filaSeleccionada, 3).toString();

            setId_pedido(Integer.parseInt(id_pedido));
            setEstado(estado);

            // Colocar el estado en el campo de texto txtEstado
            vista.txtEstado.setText(estado);
      
        }
    }

   
}
