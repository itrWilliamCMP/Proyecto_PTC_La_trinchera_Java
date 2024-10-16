package Modelo;

import Vista.FrmProductosMenu;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ProductoMenu {
    int id_producto;
    String producto;
    String descripcion;
    double precioventa;
    int stock;
    String imagen_comida;

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

    public String getImagen_comida() {
        return imagen_comida;
    }

    public void setImagen_comida(String imagen_comida) {
        this.imagen_comida = imagen_comida;
    }

    // Método para cargar la imagen y guardar la ruta
    public void cargarImagen(FrmProductosMenu vista) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar imagen");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "png", "gif"));

        if (fileChooser.showOpenDialog(vista) == JFileChooser.APPROVE_OPTION) {
            File archivoImagen = fileChooser.getSelectedFile();
            String rutaImagen = archivoImagen.getAbsolutePath();
            setImagen_comida(rutaImagen);
            System.out.println("Ruta de imagen seleccionada: " + rutaImagen); // Línea de depuración
            
            try {
                vista.imgLogoInsertar.setIcon(new ImageIcon(rutaImagen));
            } catch (Exception e) {
                System.out.println("Error al mostrar la imagen: " + e);
                JOptionPane.showMessageDialog(null, "Error al cargar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para guardar el producto en la base de datos
    public void Guardar() {
        Connection conexion = Conexion.getConexion();

        // Validaciones
        if (producto.isEmpty() || descripcion.isEmpty() || imagen_comida.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            PreparedStatement addProducto = conexion.prepareStatement("INSERT INTO Detalle_Productos_PTC (producto, descripcion, precioventa, stock, imagen_comida) VALUES (?, ?, ?, ?, ?)");
            addProducto.setString(1, getProducto());
            addProducto.setString(2, getDescripcion());
            addProducto.setDouble(3, getPrecioventa());
            addProducto.setInt(4, getStock());
            addProducto.setString(5, getImagen_comida()); // Guardar la ruta de la imagen
            addProducto.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            System.out.println("Error en el modelo: método Guardar " + ex);
            JOptionPane.showMessageDialog(null, "Error al guardar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para actualizar el producto
    public void Actualizar(JTable tabla) {
        Connection conexion = Conexion.getConexion();

        if (getId_producto() <= 0) {
            JOptionPane.showMessageDialog(null, "Producto no válido para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validaciones
        if (producto.isEmpty() || descripcion.isEmpty() || imagen_comida.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            PreparedStatement updateProducto = conexion.prepareStatement("UPDATE Detalle_Productos_PTC SET producto = ?, descripcion = ?, precioventa = ?, stock = ?, imagen_comida = ? WHERE id_producto = ?");
            updateProducto.setString(1, getProducto());
            updateProducto.setString(2, getDescripcion());
            updateProducto.setDouble(3, getPrecioventa());
            updateProducto.setInt(4, getStock());
            updateProducto.setString(5, getImagen_comida()); // Guardar la ruta de la imagen
            updateProducto.setInt(6, getId_producto());
            updateProducto.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            System.out.println("Error en el modelo: método Actualizar " + ex);
            JOptionPane.showMessageDialog(null, "Error al actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para eliminar el producto
    public void Eliminar(JTable tabla) {
        Connection conexion = Conexion.getConexion();

        if (getId_producto() <= 0) {
            JOptionPane.showMessageDialog(null, "Producto no válido para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            PreparedStatement deleteProducto = conexion.prepareStatement("DELETE FROM Detalle_Productos_PTC WHERE id_producto = ?");
            deleteProducto.setInt(1, getId_producto());
            deleteProducto.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            System.out.println("Error en el modelo: método Eliminar " + ex);
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para mostrar todos los productos en la tabla
    public void Mostrar(JTable tabla) {
        Connection conexion = Conexion.getConexion();
        DefaultTableModel modeloProductos = new DefaultTableModel();
        modeloProductos.setColumnIdentifiers(new Object[]{"id_producto", "producto", "descripcion", "precioventa", "stock", "imagen_comida"});

        try {
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Detalle_Productos_PTC");

            if (!rs.isBeforeFirst()) {  // Verificar si hay resultados
                JOptionPane.showMessageDialog(null, "No se encontraron productos.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }

            while (rs.next()) {
                modeloProductos.addRow(new Object[]{
                    rs.getInt("id_producto"),
                    rs.getString("producto"),
                    rs.getString("descripcion"),
                    rs.getDouble("precioventa"),
                    rs.getInt("stock"),
                    rs.getString("imagen_comida") // Ruta de la imagen
                });
            }
            tabla.setModel(modeloProductos);
        } catch (Exception e) {
            System.out.println("Error en el modelo: método Mostrar " + e);
            JOptionPane.showMessageDialog(null, "Error al mostrar productos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para cargar datos de la tabla en el formulario
    public void cargarDatosTabla(FrmProductosMenu vista) {
        int filaSeleccionada = vista.jtblProductos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id_producto = vista.jtblProductos.getValueAt(filaSeleccionada, 0).toString();
        String producto = vista.jtblProductos.getValueAt(filaSeleccionada, 1).toString();
        String descripcion = vista.jtblProductos.getValueAt(filaSeleccionada, 2).toString();
        String precioventa = vista.jtblProductos.getValueAt(filaSeleccionada, 3).toString();
        String stock = vista.jtblProductos.getValueAt(filaSeleccionada, 4).toString();
        String imagen_comida = vista.jtblProductos.getValueAt(filaSeleccionada, 5).toString();

        setId_producto(Integer.parseInt(id_producto));
        vista.txtNombreProducto.setText(producto);
        vista.txtDescripcion.setText(descripcion);
        vista.txtPrecio.setText(precioventa);
        vista.txtStock.setText(stock);
        vista.txtImagen.setText(imagen_comida);

        // Mostrar la imagen en el label imgLogoInsertar
        vista.imgLogoInsertar.setIcon(new ImageIcon(imagen_comida));
    }
}
