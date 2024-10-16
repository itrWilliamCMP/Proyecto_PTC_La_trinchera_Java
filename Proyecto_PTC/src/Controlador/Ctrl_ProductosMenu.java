package Controlador;

import Modelo.ProductoMenu;
import Vista.FrmProductosMenu;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Ctrl_ProductosMenu implements ActionListener {

    private FrmProductosMenu vista;
    private ProductoMenu modelo;

    // Constructor que recibe la vista y el modelo
    public Ctrl_ProductosMenu(FrmProductosMenu vista, ProductoMenu modelo) {
        this.vista = vista;
        this.modelo = modelo;

        // Agregar listeners a los botones
        vista.btnAgregar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnActualizar.addActionListener(this);
        vista.btnRegresar.addActionListener(this);
        vista.btnIimpiar.addActionListener(this); // Corrección aquí

        // Agregar listener para insertar imagen
        vista.imgLogoInsertar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                seleccionarImagen();  // Seleccionar e insertar una imagen
            }
        });

        // Agregar listener para seleccionar un producto desde la tabla
        vista.jtblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cargarImagenDesdeSeleccion(); // Cargar imagen cuando se selecciona una fila
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
        vista.txtImagen.setText("");
        vista.imgLogoInsertar.setIcon(null); // Limpia la imagen
    }

    // Método para seleccionar e insertar una imagen
    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona una imagen");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(vista);
        if (result == JFileChooser.APPROVE_OPTION) {
            File imagenElegida = fileChooser.getSelectedFile();
            String urlSubida = imagenElegida.getAbsolutePath();
            vista.txtImagen.setText(urlSubida); // Guardamos la ruta de la imagen en el campo de texto

            try {
                Image img = new ImageIcon(urlSubida).getImage();
                if (img == null) {
                    throw new Exception("Error al cargar la imagen: La imagen no se puede cargar.");
                }
                Image imagenEscalada = img.getScaledInstance(vista.imgLogoInsertar.getWidth(), vista.imgLogoInsertar.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
                vista.imgLogoInsertar.setIcon(iconoEscalado); // Mostramos la imagen redimensionada en el JLabel
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error subiendo la imagen: " + ex.getMessage());
            }
        }
    }

    // Método para cargar la imagen en el evento de selección de tabla
    private void cargarImagenDesdeSeleccion() {
        try {
            int filaSeleccionada = vista.jtblProductos.getSelectedRow();
            if (filaSeleccionada != -1) {
                String rutaImagen = (String) vista.jtblProductos.getValueAt(filaSeleccionada, 4); // Ajusta el índice según la columna de imagen
                if (rutaImagen != null && !rutaImagen.isEmpty()) {
                    Image img = new ImageIcon(rutaImagen).getImage();
                    if (img == null) {
                        throw new Exception("Error al cargar la imagen: La imagen no se puede cargar.");
                    }
                    Image imagenEscalada = img.getScaledInstance(vista.imgLogoInsertar.getWidth(), vista.imgLogoInsertar.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
                    vista.imgLogoInsertar.setIcon(iconoEscalado);
                } else {
                    throw new Exception("La ruta de la imagen está vacía o no existe.");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error cargando la imagen: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Guardar producto
        if (e.getSource() == vista.btnAgregar) {
            try {
                // Obtener datos desde el formulario
                modelo.setProducto(vista.txtNombreProducto.getText());
                modelo.setDescripcion(vista.txtDescripcion.getText());
                modelo.setPrecioventa(Double.parseDouble(vista.txtPrecio.getText()));
                modelo.setStock(Integer.parseInt(vista.txtStock.getText()));
                modelo.setImagen_comida(vista.txtImagen.getText()); // Guardar la imagen

                modelo.Guardar();  // Guardar producto en la base de datos
                modelo.Mostrar(vista.jtblProductos);  // Actualizar tabla con los datos guardados
                limpiarCampos();  // Limpiar los campos después de guardar
            } catch (NumberFormatException ex) {
                // Manejo de error: si el precio o stock no son números válidos
                JOptionPane.showMessageDialog(vista, "Por favor ingresa valores numéricos válidos para Precio y Stock.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Eliminar producto
        if (e.getSource() == vista.btnEliminar) {
            modelo.Eliminar(vista.jtblProductos);  // Eliminar el producto seleccionado
            modelo.Mostrar(vista.jtblProductos);  // Actualizar la tabla después de eliminar
            limpiarCampos();  // Limpiar campos después de eliminar
        }

        // Actualizar producto
        if (e.getSource() == vista.btnActualizar) {
            try {
                // Obtener datos desde el formulario
                modelo.setProducto(vista.txtNombreProducto.getText());
                modelo.setDescripcion(vista.txtDescripcion.getText());
                modelo.setPrecioventa(Double.parseDouble(vista.txtPrecio.getText()));
                modelo.setStock(Integer.parseInt(vista.txtStock.getText()));
                modelo.setImagen_comida(vista.txtImagen.getText()); // Actualizar la imagen

                modelo.Actualizar(vista.jtblProductos);  // Actualizar el producto en la base de datos
                modelo.Mostrar(vista.jtblProductos);  // Actualizar la tabla con los datos modificados
                limpiarCampos();  // Limpiar campos después de actualizar
            } catch (NumberFormatException ex) {
                // Manejo de error: si el precio o stock no son números válidos
                JOptionPane.showMessageDialog(vista, "Por favor ingresa valores numéricos válidos para Precio y Stock.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
            }
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
