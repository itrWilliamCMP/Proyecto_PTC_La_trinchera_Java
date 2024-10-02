package Modelo;

import Vista.FrmProductosMenu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ProductoMenu {
    // Variables que almacenan los datos de la tabla Detalle_Productos_PTC
    int id_producto;
    String producto;
    String descripcion;
    double precioventa;
    int stock;

    // Getters y Setters
    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioventa() {
        return precioventa;
    }

    public void setPrecioventa(double precioventa) {
        this.precioventa = precioventa;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Método para guardar un producto (Insertar)
    public void Guardar() {
        Connection conexion = Conexion.getConexion();

        try {
            // Query de inserción para la tabla Detalle_Productos_PTC
            PreparedStatement addProducto = conexion.prepareStatement("INSERT INTO Detalle_Productos_PTC (producto, descripcion, precioventa, stock) VALUES (?, ?, ?, ?)");

            // Se llenan los valores a insertar
            addProducto.setString(1, getProducto());
            addProducto.setString(2, getDescripcion());
            addProducto.setDouble(3, getPrecioventa());
            addProducto.setInt(4, getStock());

            addProducto.executeUpdate();  // Ejecuta la inserción

        } catch (SQLException ex) {
            System.out.println("Error en el modelo: método Guardar " + ex);
        }
    }

    // Método para actualizar un producto (solo producto, descripción, precio y stock)
    public void Actualizar(JTable tabla) {
        Connection conexion = Conexion.getConexion();

        try {
            // Query para actualizar los datos del producto
            PreparedStatement updateProducto = conexion.prepareStatement("UPDATE Detalle_Productos_PTC SET producto = ?, descripcion = ?, precioventa = ?, stock = ? WHERE id_producto = ?");

            // Se llenan los valores a actualizar
            updateProducto.setString(1, getProducto());
            updateProducto.setString(2, getDescripcion());
            updateProducto.setDouble(3, getPrecioventa());
            updateProducto.setInt(4, getStock());
            updateProducto.setInt(5, getId_producto());

            updateProducto.executeUpdate();  // Ejecuta la actualización

        } catch (SQLException ex) {
            System.out.println("Error en el modelo: método Actualizar " + ex);
        }
    }

    // Método para limpiar los campos del formulario FrmProductos
    public void limpiar(FrmProductosMenu vista) {
        vista.txtNombreProducto.setText("");
        vista.txtDescripcion.setText("");
        vista.txtPrecio.setText("");
        vista.txtStock.setText("");
    }

    // Método para eliminar un producto 
    public void Eliminar(JTable tabla) {
        Connection conexion = Conexion.getConexion();
        try {
            // Query para eliminar el producto según su id
            PreparedStatement deleteProducto = conexion.prepareStatement("DELETE FROM Detalle_Productos_PTC WHERE id_producto = ?");

            deleteProducto.setInt(1, getId_producto());  // Se establece el id del producto a eliminar

            deleteProducto.executeUpdate();  // Ejecuta la eliminación

        } catch (SQLException ex) {
            System.out.println("Error en el modelo: método Eliminar " + ex);
        }
    }

    // Método para mostrar todos los productos en la tabla
    public void Mostrar(JTable tabla) {
        Connection conexion = Conexion.getConexion();
        DefaultTableModel modeloProductos = new DefaultTableModel();
        // Definir las columnas de la tabla
        modeloProductos.setColumnIdentifiers(new Object[]{"id_producto", "producto", "descripcion", "precioventa", "stock"});

        try {
            Statement statement = conexion.createStatement();
            // Query para obtener todos los productos
            ResultSet rs = statement.executeQuery("SELECT id_producto, producto, descripcion, precioventa, stock FROM Detalle_Productos_PTC");

            while (rs.next()) {
                // Agregar los datos a la tabla
                modeloProductos.addRow(new Object[]{
                    rs.getInt("id_producto"), 
                    rs.getString("producto"), 
                    rs.getString("descripcion"), 
                    rs.getDouble("precioventa"), 
                    rs.getInt("stock")
                });
            }
            tabla.setModel(modeloProductos);  // Se actualiza la tabla con los nuevos datos
        } catch (Exception e) {
            System.out.println("Error en el modelo: método Mostrar " + e);
        }
    }

    // Método para cargar los datos seleccionados de la tabla en los campos del formulario
    public void cargarDatosTabla(FrmProductosMenu vista) {
        int filaSeleccionada = vista.jtblProductos.getSelectedRow();

        if (filaSeleccionada != -1) {
            // Obtener los datos de la fila seleccionada en la tabla
            String id_producto = vista.jtblProductos.getValueAt(filaSeleccionada, 0).toString();            
            String producto = vista.jtblProductos.getValueAt(filaSeleccionada, 1).toString();
            String descripcion = vista.jtblProductos.getValueAt(filaSeleccionada, 2).toString();
            String precioventa = vista.jtblProductos.getValueAt(filaSeleccionada, 3).toString();
            String stock = vista.jtblProductos.getValueAt(filaSeleccionada, 4).toString();

            // Llenar los datos en los campos del formulario
            setId_producto(Integer.parseInt(id_producto));
            vista.txtNombreProducto.setText(producto);
            vista.txtDescripcion.setText(descripcion);
            vista.txtPrecio.setText(precioventa);
            vista.txtStock.setText(stock);
        }
    }
}
