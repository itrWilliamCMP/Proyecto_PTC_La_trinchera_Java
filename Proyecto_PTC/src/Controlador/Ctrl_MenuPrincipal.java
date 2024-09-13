
package Controlador;

import Modelo.Empleados_PTC;
import Modelo.MenuPrincipal;
import Modelo.Menus_PTC;
import Vista.FrmMenuPrincipal;
import Vista.FrmEmpleados_PTC;
import Vista.frmMenus_PTC;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ctrl_MenuPrincipal implements ActionListener {
    
    private FrmMenuPrincipal formMenuPrincipal;
    private MenuPrincipal menuPrincipal;

    public Ctrl_MenuPrincipal(FrmMenuPrincipal formMenuPrincipal, MenuPrincipal menuPrincipal) {
        this.formMenuPrincipal = formMenuPrincipal;
        this.menuPrincipal = menuPrincipal;
        
        // Asocia los botones con los manejadores de eventos
        this.formMenuPrincipal.btnCerrar.addActionListener(this);
        this.formMenuPrincipal.btnUsuarios.addActionListener(this); 
    }    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == formMenuPrincipal.btnCerrar) {
            cerrar();
        }

        if (e.getSource() == formMenuPrincipal.btnMenu) {
            abrirMenuPTC();
        }
        
        if (e.getSource() == formMenuPrincipal.btnUsuarios) {
            abrirEmpleados();
        }   
    } 
    
    private void cerrar() {    
        formMenuPrincipal.dispose();
    }
    
    private void abrirEmpleados() {
 // Ocultar el formulario de ingreso de usuario
        formMenuPrincipal.setVisible(false);
        
        // Crea el formulario y el controlador
        FrmEmpleados_PTC vista = new FrmEmpleados_PTC();
        Empleados_PTC modelo = new Empleados_PTC();
        
        Ctrl_Empleados_PTC controlador = new Ctrl_Empleados_PTC(vista,modelo);
      
        vista.setVisible(true);  
        
        // Agregar un listener para mostrar el formulario de ingreso cuando se cierre el de recuperación
        vista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                // Mostrar nuevamente el formulario de ingreso de usuario
                formMenuPrincipal.setVisible(true);
            }
        });             
    }
    
        private void abrirMenuPTC() {
        formMenuPrincipal.setVisible(false);
        
        frmMenus_PTC vista = new frmMenus_PTC();
        Menus_PTC modelo = new Menus_PTC();
        
        Ctrl_Menus_PTC controlador = new Ctrl_Menus_PTC(vista,modelo);
      
        vista.setVisible(true);  
        
        vista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                formMenuPrincipal.setVisible(true);
            }
        });             
    }
    
}

