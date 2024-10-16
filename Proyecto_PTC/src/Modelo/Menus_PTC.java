package Modelo;

import Vista.frmMenus_PTC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Menus_PTC {
    int id_menu;
    String categoria;
    String imagen_categoria;

    // Getters y Setters
    public int getId_menu() {
        return id_menu;
    }

    public void setId_menu(int id_menu) {
        this.id_menu = id_menu;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagen_categoria() {
        return imagen_categoria;
    }

    public void setImagen_categoria(String imagen_categoria) {
        this.imagen_categoria = imagen_categoria;
    }

    // Método para guardar menú
    public void Guardar() {
        Connection conexion = Conexion.getConexion();

        // Validaciones
        if (categoria.isEmpty() || imagen_categoria.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            PreparedStatement addMenu = conexion.prepareStatement("INSERT INTO Menus_PTC (categoria, imagen_categoria) VALUES (?, ?)");
            addMenu.setString(1, getCategoria());
            addMenu.setString(2, getImagen_categoria());
            addMenu.executeUpdate();
            JOptionPane.showMessageDialog(null, "Menú guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            System.out.println("Error en el modelo: método Guardar " + ex);
            JOptionPane.showMessageDialog(null, "Error al guardar el menú.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para actualizar menú
    public void Actualizar(JTable tabla) {
        Connection conexion = Conexion.getConexion();

        if (getId_menu() <= 0) {
            JOptionPane.showMessageDialog(null, "Menú no válido para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validaciones
        if (categoria.isEmpty() || imagen_categoria.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            PreparedStatement updateMenu = conexion.prepareStatement("UPDATE Menus_PTC SET categoria = ?, imagen_categoria = ? WHERE id_menu = ?");
            updateMenu.setString(1, getCategoria());
            updateMenu.setString(2, getImagen_categoria());
            updateMenu.setInt(3, getId_menu());
            updateMenu.executeUpdate();
            JOptionPane.showMessageDialog(null, "Menú actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            System.out.println("Error en el modelo: método Actualizar " + ex);
            JOptionPane.showMessageDialog(null, "Error al actualizar el menú.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para eliminar menú
    public void Eliminar(JTable tabla) {
        Connection conexion = Conexion.getConexion();

        if (getId_menu() <= 0) {
            JOptionPane.showMessageDialog(null, "Menú no válido para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            PreparedStatement deleteMenu = conexion.prepareStatement("DELETE FROM Menus_PTC WHERE id_menu = ?");
            deleteMenu.setInt(1, getId_menu());
            deleteMenu.executeUpdate();
            JOptionPane.showMessageDialog(null, "Menú eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            System.out.println("Error en el modelo: método Eliminar " + ex);
            JOptionPane.showMessageDialog(null, "Error al eliminar el menú.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para mostrar todos los menús en la tabla
    public void Mostrar(JTable tabla) {
        Connection conexion = Conexion.getConexion();
        DefaultTableModel modeloMenus = new DefaultTableModel();
        modeloMenus.setColumnIdentifiers(new Object[]{"id_menu", "categoria", "imagen_categoria"});

        try {
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Menus_PTC");

            if (!rs.isBeforeFirst()) {  // Verificar si hay resultados
                JOptionPane.showMessageDialog(null, "No se encontraron menús.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }

            while (rs.next()) {
                modeloMenus.addRow(new Object[]{
                    rs.getInt("id_menu"),
                    rs.getString("categoria"),
                    rs.getString("imagen_categoria")
                });
            }
            tabla.setModel(modeloMenus);
        } catch (Exception e) {
            System.out.println("Error en el modelo: método Mostrar " + e);
            JOptionPane.showMessageDialog(null, "Error al mostrar menús.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para cargar datos de la tabla en el formulario
    public void cargarDatosTabla(frmMenus_PTC vista) {
        int filaSeleccionada = vista.jtbMenu.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id_menu = vista.jtbMenu.getValueAt(filaSeleccionada, 0).toString();
        String categoria = vista.jtbMenu.getValueAt(filaSeleccionada, 1).toString();
        String imagen_categoria = vista.jtbMenu.getValueAt(filaSeleccionada, 2).toString();

        setId_menu(Integer.parseInt(id_menu));
        vista.txtCategoria.setText(categoria);
        vista.txtImagen.setText(imagen_categoria);
    }
}
