package Controlador;

import Modelo.Menus_PTC;
import Vista.frmMenus_PTC;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Ctrl_Menus_PTC implements MouseListener{

    private frmMenus_PTC vista;
    private Menus_PTC modelo;
    
        public Ctrl_Menus_PTC(frmMenus_PTC Vista, Menus_PTC Modelo){
        this.vista = Vista;
        this.modelo = Modelo;
        
        vista.btnGuardar.addMouseListener(this); 
        
        vista.jtbMenu.addMouseListener(this);
        modelo.Mostrar(vista.jtbMenu);
        vista.btnEliminar.addMouseListener(this);
        vista.btnActualizar.addMouseListener(this);
        vista.jtbMenu.addMouseListener(this);
    }
        
        
    @Override
    public void mouseClicked(MouseEvent e) {
      if(e.getSource() == vista.btnGuardar){
          modelo.setcategoria(vista.txtCategoria.getText());
          modelo.setimagen_categoria(vista.txtImagen.getText());
          
          modelo.Guardar();
          modelo.Mostrar(vista.jtbMenu);
      }    
      
      if(e.getSource() == vista.btnEliminar){
      modelo.Eliminar(vista.jtbMenu);
      modelo.Mostrar(vista.jtbMenu);
      }
      
      if(e.getSource() == vista.jtbMenu){
          modelo.cargarDatosTabla(vista);
      }
      
      if(e.getSource() == vista.btnActualizar){
          modelo.setcategoria(vista.txtCategoria.getText());
          modelo.setimagen_categoria (vista.txtImagen.getText());
          
          modelo.Actualizar(vista.jtbMenu);
          modelo.Mostrar(vista.jtbMenu);
      }
    }

    
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
