package Controlador;

import Modelo.DetallePedido;
import Vista.FrmDetallePedido;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ctrl_DetallePedido implements ActionListener {
    
    private FrmDetallePedido vista;
    private DetallePedido modelo;
    

    public Ctrl_DetallePedido(FrmDetallePedido vista, DetallePedido modelo) {
        this.vista = vista;
        this.modelo = modelo;
        

        // Agregar listeners a los botones
        vista.btnRegresar.addActionListener(this);
        
        // Intentar cargar productos al iniciar el formulario
        try {
            modelo.cargarProductos(vista);
            System.out.println("Productos cargados correctamente.");
        } catch (Exception ex) {
            System.out.println("Error al cargar productos en el controlador: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Redirigir al menú principal al presionar btnRegresar
        if (e.getSource() == vista.btnRegresar) {
            try {
                vista.dispose();  // Cerrar el formulario FrmDetallePedido
                System.out.println("Formulario cerrado correctamente.");
            } catch (Exception ex) {
                System.out.println("Error al cerrar el formulario: " + ex.getMessage());
            }
        }
    }
}
