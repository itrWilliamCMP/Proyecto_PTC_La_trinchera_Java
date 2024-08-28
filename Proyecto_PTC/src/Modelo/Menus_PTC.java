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
 
//
    public int getid_menu() {
    return id_menu;
    }

    public void setid_menu(int id_menu) {
        this.id_menu = id_menu;
    }
    
    public String getcategoria() {
    return categoria;
    }

    public void setcategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getimagen_categoria() {
    return imagen_categoria;
    }

    public void setimagen_categoria(String imagen_categoria) {
        this.imagen_categoria = imagen_categoria;
    }
    
//
    
        public void Guardar() {
        Connection conexion = Conexion.getConexion();
        try {
            PreparedStatement addProducto = conexion.prepareStatement("INSERT INTO Menus_PTC(id_menu, categoria, imagen_categoria) VALUES (?, ?, ?)");

            addProducto.setInt(1, getid_menu());
            addProducto.setString(2, getcategoria());
            addProducto.setString(3, getimagen_categoria());
 
            addProducto.executeUpdate();
 
        } catch (SQLException ex) {
            System.out.println("este es el error en el modelo:metodo guardar " + ex);
        }
    }
        
        public void Actualizar(JTable tabla) {
        Connection conexion = Conexion.getConexion();
        try {
            PreparedStatement addProducto = conexion.prepareStatement("UPDATE Menus_PTC(id_menu, categoria, imagen_categoria) VALUES (?, ?, ?)");

            addProducto.setInt(1, getid_menu());
            addProducto.setString(2, getcategoria());
            addProducto.setString(3, getimagen_categoria());
 
            addProducto.executeUpdate();
 
        } catch (SQLException ex) {
            System.out.println("este es el error en el modelo:metodo guardar " + ex);
        }
    }
        
        public void Eliminar(JTable tabla) {
        Connection conexion = Conexion.getConexion();
        try {
            PreparedStatement addProducto = conexion.prepareStatement("DELETE FROM Menus_PTC(id_menu, categoria, imagen_categoria) VALUES (?, ?, ?)");

            addProducto.setInt(1, getid_menu());
            addProducto.setString(2, getcategoria());
            addProducto.setString(3, getimagen_categoria());
 
            addProducto.executeUpdate();
 
        } catch (SQLException ex) {
            System.out.println("este es el error en el modelo:metodo guardar " + ex);
        }
    }
        
        public void Mostrar(JTable tabla) {
        Connection conexion = Conexion.getConexion();
        DefaultTableModel modeloPinulito = new DefaultTableModel();
        modeloPinulito.setColumnIdentifiers(new Object[]{"id_menu", "categoria", "imagen_categoria"});
        try {
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Menus_Ptc");
            while (rs.next()) {
                modeloPinulito.addRow(new Object[]{rs.getString("UUID_producto"), 
                    rs.getString("nombre"), 
                    rs.getInt("precio"), 
                    rs.getString("categoria")});
            }
            tabla.setModel(modeloPinulito);
        } catch (Exception e) {
            System.out.println("Este es el error en el modelo, metodo mostrar " + e);
        }
    }
            
        public void cargarDatosTabla(frmMenus_PTC vista) {
        int filaSeleccionada = vista.jtbMenu.getSelectedRow();
        if (filaSeleccionada != -1) {
            String id_menu = vista.jtbMenu.getValueAt(filaSeleccionada, 0).toString();
            String categoria = vista.jtbMenu.getValueAt(filaSeleccionada, 1).toString();
            String imagen_categoria = vista.jtbMenu.getValueAt(filaSeleccionada, 2).toString();

            vista.txtCategoria.setText(id_menu);
            vista.txtImagen.setText(categoria);
            vista.txtCategoria.setText(imagen_categoria);
        }                
    }
    
}
