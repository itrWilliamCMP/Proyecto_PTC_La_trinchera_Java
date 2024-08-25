package Principal;

import Vista.FrmIngresoUsuario;
import Controlador.Ctrl_IngresoUsuario;
import Modelo.IngresoUsuario;

public class Main {
    public static void main(String[] args) {
        // Configura el look and feel
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crea el formulario y el controlador
        FrmIngresoUsuario vista = new FrmIngresoUsuario();
        IngresoUsuario modelo = new IngresoUsuario();
        Ctrl_IngresoUsuario controlador = new Ctrl_IngresoUsuario(vista,modelo);

        vista.setVisible(true);
    }
}