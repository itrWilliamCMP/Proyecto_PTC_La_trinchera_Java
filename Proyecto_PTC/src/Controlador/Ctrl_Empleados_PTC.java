package Controlador;

import Modelo.Empleados_PTC;
import Vista.FrmEmpleados_PTC;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ctrl_Empleados_PTC implements ActionListener {

    private FrmEmpleados_PTC vista;
    private Empleados_PTC modelo;

    public Ctrl_Empleados_PTC(FrmEmpleados_PTC Vista, Empleados_PTC Modelo) {
        this.vista = Vista;
        this.modelo = Modelo;

        // Agregar listeners a los botones
        vista.btnGuardar.addActionListener(this); 
        vista.btnEliminar.addActionListener(this);
        vista.btnActualizar.addActionListener(this);
        vista.btnRegresar.addActionListener(this);
        vista.btnLimpiar.addActionListener(this); 
        vista.jTblEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                modelo.cargarDatosTabla(vista);
            }
        });

        modelo.Mostrar(vista.jTblEmpleados);
    }

    // Método para limpiar los campos del formulario
    private void limpiarCampos() {
        vista.txtUsuarios.setText("");
        vista.txtCorreo.setText("");
        vista.txtContrasena.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Guardar empleado
        if (e.getSource() == vista.btnGuardar) {
            modelo.setUsuario_empleado(vista.txtUsuarios.getText());
            modelo.setCorreoElectronico(vista.txtCorreo.getText());
            modelo.setContrasena_empleado(vista.txtContrasena.getText());

            modelo.Guardar();
            modelo.Mostrar(vista.jTblEmpleados);
            limpiarCampos(); // Limpiar campos después de guardar
        }

        // Eliminar empleado
        if (e.getSource() == vista.btnEliminar) {
            modelo.Eliminar(vista.jTblEmpleados);
            modelo.Mostrar(vista.jTblEmpleados);
            
            limpiarCampos();
        }

        // Actualizar empleado
        if (e.getSource() == vista.btnActualizar) {
            modelo.setUsuario_empleado(vista.txtUsuarios.getText());
            modelo.setCorreoElectronico(vista.txtCorreo.getText());
            modelo.setContrasena_empleado(vista.txtContrasena.getText());

            modelo.Actualizar(vista.jTblEmpleados);
            modelo.Mostrar(vista.jTblEmpleados);
            limpiarCampos(); // Limpiar campos después de actualizar
        }

        // Limpiar campos manualmente
        if (e.getSource() == vista.btnLimpiar) {
            limpiarCampos(); // Llamar al método de limpiar campos
        }

        // Redirigir al menú principal al presionar btnRegresar
        if (e.getSource() == vista.btnRegresar) {
            vista.dispose();  // Cerrar el formulario FrmEmpleados_PTC
        }
    }
}
