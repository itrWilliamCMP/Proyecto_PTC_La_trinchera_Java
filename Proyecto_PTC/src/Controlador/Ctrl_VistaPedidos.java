package Controlador;

import Modelo.VistaPedidos;
import Vista.FrmDetallePedido;
import Vista.FrmPedidos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ctrl_VistaPedidos implements ActionListener {

    private FrmPedidos vistaFrmPedidos;
    private VistaPedidos modelo;
    private FrmDetallePedido detallePedido; // Instancia única de FrmDetallePedido

    public Ctrl_VistaPedidos(FrmPedidos Vista, VistaPedidos Modelo) {
        this.vistaFrmPedidos = Vista;
        this.modelo = Modelo;

        // Agregar listeners a los botones
        vistaFrmPedidos.btnEliminar.addActionListener(this);
        vistaFrmPedidos.btnNuevo.addActionListener(this);
        vistaFrmPedidos.btnCerrar.addActionListener(this);
        vistaFrmPedidos.btnNuevo.addActionListener(this);
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
        System.out.println("Se detecto el click de: " + e.getSource());
        String comando = e.getActionCommand();
        System.out.println("Botón presionado: " + comando);
        
        // Abrir FrmDetallePedido
        if (e.getSource() == vistaFrmPedidos.btnNuevo) {
            System.out.println("Se detecto el click en btnNuevo ");
            if (detallePedido == null || !detallePedido.isVisible()) {
                detallePedido = new FrmDetallePedido();
                detallePedido.setVisible(true);
                vistaFrmPedidos.dispose(); // Cerrar solo el formulario FrmPedidos
            }
        
        } 

        // Actualizar pedido
        if (e.getSource() == vistaFrmPedidos.btnActualizar) {
            if (vistaFrmPedidos.jtblPedidos.getSelectedRow() != -1) {
                System.out.println("Se detecto el click ");
                modelo.setEstado(vistaFrmPedidos.jtblPedidos.getValueAt(vistaFrmPedidos.jtblPedidos.getSelectedRow(), 3).toString());
                modelo.Actualizar(vistaFrmPedidos.jtblPedidos);
                modelo.Mostrar(vistaFrmPedidos.jtblPedidos);
            } else {
                System.out.println("No hay ninguna fila seleccionada.");
            }
        }

        // Eliminar pedido
        if (e.getSource() == vistaFrmPedidos.btnEliminar) {
            System.out.println("Se detecto el click ");
            modelo.Eliminar(vistaFrmPedidos.jtblPedidos);
            modelo.Mostrar(vistaFrmPedidos.jtblPedidos);
        }
        
        // Regresar al menú principal
        if (e.getSource() == vistaFrmPedidos.btnCerrar) {
            System.out.println("Se detecto el click ");
            vistaFrmPedidos.dispose(); // Cerrar solo el formulario FrmPedidos
        }
    }
}
