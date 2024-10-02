package Controlador;

import Modelo.ProductoMenu;
import Vista.FrmProductosMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ctrl_ProductosMenu implements ActionListener {

    private FrmProductosMenu vista;
    private ProductoMenu modelo;

    // Constructor que recibe la vista y el modelo
    public Ctrl_ProductosMenu(FrmProductosMenu Vista, ProductoMenu Modelo) {
        this.vista = Vista;
        this.modelo = Modelo;

        // Agregar listeners a los botones
        vista.btnAgregar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnActualizar.addActionListener(this);
        vista.btnRegresar.addActionListener(this);
        vista.btnIimpiar.addActionListener(this);
        
        // Agregar listener para seleccionar un producto desde la tabla
        vista.jtblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                modelo.cargarDatosTabla(vista); // Cargar datos seleccionados en el formulario
            }
        });

        modelo.Mostrar(vista.jtblProductos); // Mostrar los productos en la tabla
    }

    // Método para limpiar los campos del formulario
    private void limpiarCampos() {
        vista.txtNombreProducto.setText("");
        vista.txtDescripcion.setText("");
        vista.txtPrecio.setText("");
        vista.txtStock.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Guardar producto
        if (e.getSource() == vista.btnAgregar) {
            // Obtener datos desde el formulario
            modelo.setProducto(vista.txtNombreProducto.getText());
            modelo.setDescripcion(vista.txtDescripcion.getText());
            modelo.setPrecioventa(Double.parseDouble(vista.txtPrecio.getText()));
            modelo.setStock(Integer.parseInt(vista.txtStock.getText()));

            modelo.Guardar();  // Guardar producto en la base de datos
            modelo.Mostrar(vista.jtblProductos);  // Actualizar tabla con los datos guardados
            limpiarCampos();  // Limpiar los campos después de guardar
        }

        // Eliminar producto
        if (e.getSource() == vista.btnEliminar) {
            modelo.Eliminar(vista.jtblProductos);  // Eliminar el producto seleccionado
            modelo.Mostrar(vista.jtblProductos);  // Actualizar la tabla después de eliminar
            limpiarCampos();  // Limpiar campos después de eliminar
        }

        // Actualizar producto
        if (e.getSource() == vista.btnActualizar) {
            // Obtener datos desde el formulario
            modelo.setProducto(vista.txtNombreProducto.getText());
            modelo.setDescripcion(vista.txtDescripcion.getText());
            modelo.setPrecioventa(Double.parseDouble(vista.txtPrecio.getText()));
            modelo.setStock(Integer.parseInt(vista.txtStock.getText()));

            modelo.Actualizar(vista.jtblProductos);  // Actualizar el producto en la base de datos
            modelo.Mostrar(vista.jtblProductos);  // Actualizar la tabla con los datos modificados
            limpiarCampos();  // Limpiar campos después de actualizar
        }

        // Limpiar campos manualmente
        if (e.getSource() == vista.btnIimpiar) {
            limpiarCampos();  // Llamar al método de limpiar campos
        }

        // Redirigir al menú principal al presionar btnRegresar
        if (e.getSource() == vista.btnRegresar) {
            vista.dispose();  // Cerrar el formulario FrmProductosMenus
        }
    }
}
