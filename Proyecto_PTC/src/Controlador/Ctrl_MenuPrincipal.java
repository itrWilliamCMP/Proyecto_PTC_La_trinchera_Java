package Controlador;

import Modelo.Clientes_PTC;
import Modelo.Empleados_PTC;
import Modelo.MenuPrincipal;
import Modelo.Menus_PTC;
import Modelo.VistaPedidos;
import Modelo.DetallePedido;
import Modelo.ProductoMenu; // Importar el modelo de productos
import Vista.FrmClientes_PTC;
import Vista.FrmMenuPrincipal;
import Vista.FrmEmpleados_PTC;
import Vista.FrmPedidos;
import Vista.FrmDetallePedido;
import Vista.frmMenus_PTC;
import Vista.FrmProductosMenu; // Importar la vista de productos
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
        this.formMenuPrincipal.btnVistaPedidos.addActionListener(this);
        this.formMenuPrincipal.btnClientes.addActionListener(this);
        this.formMenuPrincipal.btnCrearPedido.addActionListener(this);
        this.formMenuPrincipal.btnProductosMenus.addActionListener(this); // Agregar listener para el botón de productos
    }    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == formMenuPrincipal.btnCerrar) {
            cerrar();
        }

        if (e.getSource() == formMenuPrincipal.btnMenu) {
            abrirMenuRest();
        }

        if (e.getSource() == formMenuPrincipal.btnUsuarios) {
            abrirEmpleados();
        }

        if (e.getSource() == formMenuPrincipal.btnVistaPedidos) {
            abrirPedidos();
        }

        if (e.getSource() == formMenuPrincipal.btnClientes) {
            abrirClientes();
        }

        if (e.getSource() == formMenuPrincipal.btnCrearPedido) {
            abrirDetallePedido();
        }

        if (e.getSource() == formMenuPrincipal.btnProductosMenus) {
            abrirProductosMenu();
        }
    }

    private void cerrar() {    
        formMenuPrincipal.dispose();
    }

    private void abrirEmpleados() {
        formMenuPrincipal.setVisible(false);
        FrmEmpleados_PTC vista = new FrmEmpleados_PTC();
        Empleados_PTC modelo = new Empleados_PTC();
        Ctrl_Empleados_PTC controlador = new Ctrl_Empleados_PTC(vista, modelo);
        vista.setVisible(true);

        vista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                formMenuPrincipal.setVisible(true);
            }
        });
    }

    private void abrirMenuRest() {
        formMenuPrincipal.setVisible(false);
        frmMenus_PTC vista = new frmMenus_PTC();
        Menus_PTC modelo = new Menus_PTC();
        Ctrl_Menus_PTC controlador = new Ctrl_Menus_PTC(vista, modelo);
        vista.setVisible(true);

        vista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                formMenuPrincipal.setVisible(true);
            }
        });
    }

    private void abrirPedidos() {
        formMenuPrincipal.setVisible(false);
        FrmPedidos vista2 = new FrmPedidos();
        VistaPedidos modelo2 = new VistaPedidos();
        Ctrl_VistaPedidos controlador = new Ctrl_VistaPedidos(vista2, modelo2);
        vista2.setVisible(true);

        vista2.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                formMenuPrincipal.setVisible(true);
            }
        });
    }

    private void abrirClientes() {
        formMenuPrincipal.setVisible(false);
        FrmClientes_PTC vista = new FrmClientes_PTC();
        Clientes_PTC modelo = new Clientes_PTC();
        Ctrl_Clientes_PTC controlador = new Ctrl_Clientes_PTC(vista, modelo);
        vista.setVisible(true);

        vista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                formMenuPrincipal.setVisible(true);
            }
        });
    }

    private void abrirDetallePedido() {
        formMenuPrincipal.setVisible(false);
        FrmDetallePedido vista = new FrmDetallePedido();
        DetallePedido modelo = new DetallePedido();
        Ctrl_DetallePedido controlador = new Ctrl_DetallePedido(vista, modelo);
        vista.setVisible(true);

        vista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                formMenuPrincipal.setVisible(true);
            }
        });
    }

    // Método para abrir el formulario de productos
    private void abrirProductosMenu() {
        formMenuPrincipal.setVisible(false);
        FrmProductosMenu vista = new FrmProductosMenu(); // Crear la vista de productos
        ProductoMenu modelo = new ProductoMenu(); // Crear el modelo de productos
        Ctrl_ProductosMenu controlador = new Ctrl_ProductosMenu(vista, modelo); // Crear el controlador de productos
        vista.setVisible(true);

        vista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                formMenuPrincipal.setVisible(true);
            }
        });
    }
}
