package Controlador;

import Modelo.VistaPedidos;
import Vista.FrmDetallePedido;
import Vista.FrmPedidos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ctrl_VistaPedidos implements ActionListener {

    private FrmPedidos vistaFrmPedidos;
    private VistaPedidos modelo;

    public Ctrl_VistaPedidos(FrmPedidos Vista, VistaPedidos Modelo) {
        this.vistaFrmPedidos = Vista;
        this.modelo = Modelo;

        // Agregar listeners a los botones
        vistaFrmPedidos.btnActualizar.addActionListener(this);
        vistaFrmPedidos.btnCerrar.addActionListener(this);
        vistaFrmPedidos.jtblPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                modelo.cargarDatosTabla(vistaFrmPedidos);
            }
        });

        // Mostrar los datos en la tabla
        modelo.Mostrar(vistaFrmPedidos.jtblPedidos);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Se detectó el click de: " + e.getSource());
        
        // Actualizar pedido
        if (e.getSource() == vistaFrmPedidos.btnActualizar) {
            if (vistaFrmPedidos.jtblPedidos.getSelectedRow() != -1) {
                System.out.println("Se detectó el click para actualizar");
                modelo.setEstado(vistaFrmPedidos.txtEstado.getText()); // Obtener el estado del campo txtEstado
                String EstadoPedido = (String)vistaFrmPedidos.cmb_estado.getSelectedItem();
                
                modelo.Actualizar(EstadoPedido); // Llamar al método Actualizar
                modelo.Mostrar(vistaFrmPedidos.jtblPedidos); 
                vistaFrmPedidos.txtEstado.setText("");
            } else {
                System.out.println("No hay ninguna fila seleccionada.");
            }
        }

        
        // Regresar al menú principal
        if (e.getSource() == vistaFrmPedidos.btnCerrar) {
            System.out.println("Se detectó el click para cerrar");
            vistaFrmPedidos.dispose(); // Cerrar solo el formulario FrmPedidos
        }
    }
}
