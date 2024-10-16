package Controlador;

import Vista.FrmIngresoUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Modelo.IngresoUsuario;
import Modelo.MenuPrincipal;
import Modelo.RecuperarClave;
import Vista.FrmMenuPrincipal;
import Vista.FrmRecuperarClave;
import javax.swing.JOptionPane;


public class Ctrl_IngresoUsuario  implements ActionListener {
    
    private FrmIngresoUsuario frmIngresoUsuario;
    private IngresoUsuario ingresoUsuario;

    
    public Ctrl_IngresoUsuario(FrmIngresoUsuario vista, IngresoUsuario modelo) {
        frmIngresoUsuario = vista;
        ingresoUsuario = modelo;
        
        // Asocia los botones con los manejadores de eventos
        frmIngresoUsuario.btnIngresar.addActionListener(this);
        frmIngresoUsuario.btnCancelar.addActionListener(this);
        frmIngresoUsuario.btnRecuperarClave.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frmIngresoUsuario.btnIngresar) {
            ingresar();
            
        } else if (e.getSource() == frmIngresoUsuario.btnCancelar) {
            cancelar();
            
        } else if (e.getSource() == frmIngresoUsuario.btnRecuperarClave) {
            recuperarClave();
        }
    }

    private void ingresar() {
        String usuario_empleado = frmIngresoUsuario.txtUsuario.getText();
        
       
        // Obtener el arreglo de caracteres de JPasswordField
        char[] passwordChars = frmIngresoUsuario.txtClave.getPassword();
        // Convertir el arreglo de caracteres en una cadena
        String contrasena_empleado = new String(passwordChars);

        var result = ingresoUsuario.ValidarUsuario(usuario_empleado, contrasena_empleado);
        
        if (result){
            frmIngresoUsuario.dispose(); // Cierra la ventana actual
             
            // Crea el formulario y el controlador
            FrmMenuPrincipal vista = new FrmMenuPrincipal();
            MenuPrincipal modelo = new MenuPrincipal();
            
            Ctrl_MenuPrincipal controlador = new Ctrl_MenuPrincipal(vista,modelo);
            vista.setDefaultCloseOperation(vista.DO_NOTHING_ON_CLOSE);
            vista.setVisible(true);              
        }
    }

    private void cancelar() {
        int confirm = JOptionPane.showConfirmDialog(frmIngresoUsuario,"¿Estás seguro de que deseas salir?",
                    "Confirmar salida",JOptionPane.OK_CANCEL_OPTION);
    
        if (confirm == JOptionPane.OK_OPTION) {
            frmIngresoUsuario.dispose(); // Cierra la ventana actual
            System.exit(0); // Termina el proceso si no hay más ventanas abiertas
        }
    }

    private void recuperarClave() {
       
        // Ocultar el formulario de ingreso de usuario
        frmIngresoUsuario.setVisible(false);
        
        // Crea el formulario y el controlador
        FrmRecuperarClave vista = new FrmRecuperarClave();
        RecuperarClave modelo = new RecuperarClave();
        vista.setDefaultCloseOperation(vista.DO_NOTHING_ON_CLOSE);
        Ctrl_RecuperarClave controlador = new Ctrl_RecuperarClave(vista,modelo);
      
        vista.setVisible(true);  
        
        // Agregar un listener para mostrar el formulario de ingreso cuando se cierre el de recuperación
        vista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                // Mostrar nuevamente el formulario de ingreso de usuario
                frmIngresoUsuario.setVisible(true);
            }
        });        
    }
}

