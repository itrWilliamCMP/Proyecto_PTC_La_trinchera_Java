package Controlador;

import Modelo.Clientes_PTC;
import Vista.FrmClientes_PTC;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ctrl_Clientes_PTC implements ActionListener {

    private FrmClientes_PTC vista;
    private Clientes_PTC modelo;

    public Ctrl_Clientes_PTC(FrmClientes_PTC Vista, Clientes_PTC Modelo) {
        this.vista = Vista;
        this.modelo = Modelo;

        // Agregar listeners a los botones
        vista.btnEliminar.addActionListener(this);
        vista.btnActualizar.addActionListener(this);
        vista.btnRegresar.addActionListener(this);
        vista.btnLimpiar.addActionListener(this); 
        vista.jtblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                modelo.cargarDatosTabla(vista);
            }
        });

        modelo.Mostrar(vista.jtblClientes);
    }

    // Método para limpiar los campos del formulario
    private void limpiarCampos() {
        vista.txtNombreCliente.setText("");
        vista.txtCorreo.setText("");
        vista.txtDirrecion.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Eliminar cliente
        if (e.getSource() == vista.btnEliminar) {
            modelo.Eliminar(vista.jtblClientes);
            modelo.Mostrar(vista.jtblClientes);
            
            limpiarCampos();
        }

        // Actualizar cliente
        if (e.getSource() == vista.btnActualizar) {
            modelo.setNombre_clie(vista.txtNombreCliente.getText());
            modelo.setCorreoElectronico(vista.txtCorreo.getText());
            modelo.setDireccion_entrega(vista.txtDirrecion.getText());

            modelo.Actualizar(vista.jtblClientes);
            modelo.Mostrar(vista.jtblClientes);
            limpiarCampos(); // Limpiar campos después de actualizar
        }

        // Limpiar campos manualmente
        if (e.getSource() == vista.btnLimpiar) {
            limpiarCampos(); // Llamar al método de limpiar campos
        }

        // Redirigir al menú principal al presionar btnRegresar
        if (e.getSource() == vista.btnRegresar) {
            vista.dispose();  // Cerrar el formulario FrmClientes_PTC
        }
    }
}
