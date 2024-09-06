package Controlador;

import Modelo.Menus_PTC;
import Vista.frmMenus_PTC;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Ctrl_Menus_PTC implements ActionListener{

    private frmMenus_PTC vista;
    private Menus_PTC modelo;
    
        public Ctrl_Menus_PTC(frmMenus_PTC Vista, Menus_PTC Modelo){
        this.vista = Vista;
        this.modelo = Modelo;
        
        vista.btnGuardar.addActionListener(this); 
        
        modelo.Mostrar(vista.jtbMenu);
        vista.btnEliminar.addActionListener(this);
        vista.btnActualizar.addActionListener(this);
        vista.btnLimpiar.addActionListener(this);
        vista.jtbMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                modelo.cargarDatosTabla(vista);
            }
        });

        modelo.Mostrar(vista.jtbMenu);
    }
    
       private void limpiarCampos() {
        vista.txtCategoria.setText("");
        vista.txtImagen.setText("");
    }
       
    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource() == vista.btnGuardar){
          modelo.setcategoria(vista.txtCategoria.getText());
          modelo.setimagen_categoria(vista.txtImagen.getText());
          
          modelo.Guardar();
          modelo.Mostrar(vista.jtbMenu);
          limpiarCampos();

      }    
      
      if(e.getSource() == vista.btnEliminar){
      modelo.Eliminar(vista.jtbMenu);
      modelo.Mostrar(vista.jtbMenu);
      }
      
      if(e.getSource() == vista.btnActualizar){
          modelo.setcategoria(vista.txtCategoria.getText());
          modelo.setimagen_categoria (vista.txtImagen.getText());
          
          modelo.Actualizar(vista.jtbMenu);
          modelo.Mostrar(vista.jtbMenu);
      }
    
        if (e.getSource() == vista.btnLimpiar) {
            limpiarCampos(); 
        }

        if (e.getSource() == vista.btnRegresarMenu) {
            vista.dispose(); 
        }
    }
}
