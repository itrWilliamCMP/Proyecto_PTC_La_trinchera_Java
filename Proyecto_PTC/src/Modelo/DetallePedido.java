package Modelo;

import Vista.FrmDetallePedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JComboBox;

public class DetallePedido {
    
    private int id_producto;
    private String producto;
    private double precioVenta;

    // Getters and Setters
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

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    // Método para cargar productos en el JComboBox
public void cargarProductos(FrmDetallePedido vista) {
    System.out.println("ENTRO AL METODO CARGAR PRODUCTOS");
    Connection conexion = Conexion.getConexion();
    
    if (conexion == null) {
        System.out.println("Error: No se pudo establecer la conexión a la base de datos.");
        return;
    } else {
        System.out.println("Conexión a la base de datos establecida correctamente.");
    }
    
    try {
        PreparedStatement ps = conexion.prepareStatement("SELECT id_producto, producto, precioventa FROM Detalle_Productos_PTC");
        
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String productoNombre = rs.getString("producto");
            double precio = rs.getDouble("precioventa");
            
            // Mensajes de verificación
            System.out.println("Producto obtenido: " + productoNombre);
            System.out.println("Precio obtenido: " + precio);
            
            if (productoNombre != null && !productoNombre.isEmpty() && precio >= 0) {
                String item = productoNombre + " - " + precio;
                vista.cmb_productos.addItem(item); // Agrega el item directamente al JComboBox de la vista
            } else {
                System.out.println("Advertencia: Valores no válidos para el producto o precio.");
            }
        }
    } catch (Exception e) {
        System.out.println("Error al cargar productos: " + e);
    }
}



}
