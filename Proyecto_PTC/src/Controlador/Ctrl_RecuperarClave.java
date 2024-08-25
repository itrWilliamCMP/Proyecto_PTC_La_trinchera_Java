package Controlador;
import Vista.FrmRecuperarClave;
import Modelo.RecuperarClave;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Ctrl_RecuperarClave implements ActionListener {

    private FrmRecuperarClave frmRecuperarClave;
    private RecuperarClave recuperarClave;

    
    public Ctrl_RecuperarClave(FrmRecuperarClave vista, RecuperarClave modelo) {
        frmRecuperarClave = vista;
        recuperarClave = modelo;
        
        // Asocia los botones con los manejadores de eventos
        frmRecuperarClave.btnEnviarCodigo.addActionListener(this);
        frmRecuperarClave.btnCambiarClave.addActionListener(this);
        frmRecuperarClave.btnCerrar.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frmRecuperarClave.btnEnviarCodigo) {
            enviarCodigo();
        } else if (e.getSource() == frmRecuperarClave.btnCambiarClave) {
            cambiarClave();
        } else if (e.getSource() == frmRecuperarClave.btnCerrar) {
            cerrar();
        }
    }  
    
    private void enviarCodigo() {
        String usuario = frmRecuperarClave.txtUsuario.getText();
        String correo = frmRecuperarClave.txtEmail.getText();
        recuperarClave.enviarCodigo(usuario,correo);
    }

    private void cerrar() {    
        frmRecuperarClave.dispose();
    }

    private void cambiarClave() {
        String usuario_empleado = frmRecuperarClave.txtUsuario.getText();
        String codigoVerificacion = frmRecuperarClave.txtCodigoVerificacion.getText();
        String contrasena_empleado = frmRecuperarClave.txtClave.getText();

        var result = recuperarClave.actualizarClave(usuario_empleado, codigoVerificacion, contrasena_empleado);
        
        if (result){
            this.cerrar();
        }
    } 
}
