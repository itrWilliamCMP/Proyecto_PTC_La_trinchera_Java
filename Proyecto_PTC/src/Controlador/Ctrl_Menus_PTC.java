package Controlador;

import Modelo.Menus_PTC;
import Vista.frmMenus_PTC;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Ctrl_Menus_PTC implements ActionListener {

    private frmMenus_PTC vista;
    private Menus_PTC modelo;

    public Ctrl_Menus_PTC(frmMenus_PTC Vista, Menus_PTC Modelo) {
        this.vista = Vista;
        this.modelo = Modelo;

        vista.btnGuardar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnActualizar.addActionListener(this);
        vista.btnLimpiar.addActionListener(this);

        // Listener para el botón de insertar imagen
        vista.imgLogoInsertar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                seleccionarImagen();
            }
        });

        vista.jtbMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cargarImagenDesdeSeleccion(); // Cargar imagen cuando se selecciona una fila
                modelo.cargarDatosTabla(vista);
            }
        });

        modelo.Mostrar(vista.jtbMenu);
    }

    // Método para limpiar los campos
    private void limpiarCampos() {
        vista.txtCategoria.setText("");
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
            int filaSeleccionada = vista.jtbMenu.getSelectedRow();
            if (filaSeleccionada != -1) {
                String rutaImagen = (String) vista.jtbMenu.getValueAt(filaSeleccionada, 2); // Asegúrate de que el índice de la columna sea correcto
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
        if (e.getSource() == vista.btnGuardar) {
            modelo.setCategoria(vista.txtCategoria.getText());
            modelo.setImagen_categoria(vista.txtImagen.getText());

            modelo.Guardar();
            modelo.Mostrar(vista.jtbMenu);
            limpiarCampos();
        }

        if (e.getSource() == vista.btnEliminar) {
            modelo.Eliminar(vista.jtbMenu);
            modelo.Mostrar(vista.jtbMenu);
        }

        if (e.getSource() == vista.btnActualizar) {
            modelo.setCategoria(vista.txtCategoria.getText());
            modelo.setImagen_categoria(vista.txtImagen.getText());

            modelo.Actualizar(vista.jtbMenu);
            modelo.Mostrar(vista.jtbMenu);
        }

        if (e.getSource() == vista.btnLimpiar) {
            limpiarCampos(); 
        }
    }
}
