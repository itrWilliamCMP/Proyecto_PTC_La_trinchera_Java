package Modelo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.sql.SQLException;


public class CrearPedido{
    private int id_producto;
    private String producto;
    private double precio;
    
        public CrearPedido(){
                this.id_producto = 0;
                this.producto = "";
                this.precio = 0;
        }

        public CrearPedido(int idProducto, String nombreProducto, double precioProducto){
                this.id_producto = idProducto;
                this.producto = nombreProducto;
                this.precio = precioProducto;
        }

        public double getPrecio() {
            return precio;
        }
                
        public int getIdProducto() {
            return id_producto;
        }

        public String getProducto(){
              return producto;
        }

        @Override
        public String toString() {
            return producto;
        }  
    
        public void cargarProductos(JComboBox comboBox, JTextField txtprecio) {
        Connection conexion = Conexion.getConexion();
        
        try {
            // Preparamos la consulta para obtener id_producto y producto
            PreparedStatement ps = conexion.prepareStatement("SELECT id_producto, producto,precioventa FROM Detalle_Productos_PTC");
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idProducto = rs.getInt("id_producto");
                String productoNombre = rs.getString("producto"); 
                double precioProducto = rs.getDouble("precioventa");
                
                // Mensajes de verificación
                System.out.println("ID obtenido: " + idProducto);
                System.out.println("Producto obtenido: " + productoNombre);
                System.out.println("Precio: " + precioProducto);

                comboBox.addItem(new CrearPedido(idProducto,productoNombre,precioProducto)); // Agrega el item directamente al JComboBox de la vista                
            }
            
            comboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    CrearPedido productoSeleccionado = (CrearPedido) comboBox.getSelectedItem();
                    if (productoSeleccionado != null){
                            txtprecio.setText(String.format("%.2f",productoSeleccionado.getPrecio()));
                              System.out.println("Precio desde combobox: " + precio);
                    }
                }
            });
            
            
        } catch (Exception e) {
            System.out.println("Error al cargar productos: " + e);
        }
    }  
        
            // Método para insertar los datos en la tabla DetallePedidos_PTC
    public boolean guardarDetallePedido(int idProducto, int cantidad, double precio) {
       
        try {
                Connection conexion = Conexion.getConexion();
                PreparedStatement ps = conexion.prepareStatement("INSERT INTO DetallePedidos_PTC (id_producto, cantidad, precio) VALUES (?, ?, ?)");
            
            // Asignar los valores a los parámetros de la consulta SQL
            ps.setInt(1, idProducto);
            ps.setInt(2, cantidad);
            ps.setDouble(3, precio);
            
            // Ejecutar la consulta
            int rowsAffected = ps.executeUpdate();
            
            return rowsAffected > 0;  // Retorna true si se insertó correctamente
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    
}


