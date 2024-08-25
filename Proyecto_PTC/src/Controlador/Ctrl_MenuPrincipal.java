package Controlador;

import Modelo.MenuPrincipal;
import Vista.FrmMenuPrincipal;
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
    }    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == formMenuPrincipal.btnCerrar) {
            cerrar();
        }
    } 
    
    private void cerrar() {    
        formMenuPrincipal.dispose();
    }    
}
