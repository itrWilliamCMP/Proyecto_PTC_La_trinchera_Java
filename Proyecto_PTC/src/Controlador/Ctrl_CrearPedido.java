package Controlador;

import Modelo.Conexion;
import Modelo.CrearPedido;
import Vista.FrmCrearPedido;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

public class Ctrl_CrearPedido implements ActionListener {

    private FrmCrearPedido vista;
    private CrearPedido modelo;    
    private DefaultTableModel modeloTabla;
    
    public Ctrl_CrearPedido(FrmCrearPedido Vista, CrearPedido Modelo) {
        this.vista = Vista;
        this.modelo = Modelo;
        
        // Agregar el listener para el botón de regresar
        this.vista.btnRegresar.addActionListener(this);
        
        // Cargar productos al combo
        cargarDatosProductos();
        
        DefaultTableModel tablemodel = new DefaultTableModel();
       
        tablemodel.addColumn("id_producto");
        tablemodel.addColumn("producto");
        tablemodel.addColumn("cantidad");
        tablemodel.addColumn("precio");
        tablemodel.addColumn("total");
        
        modeloTabla = tablemodel;
        
        vista.detalle_pedido.setModel(tablemodel);
        
        this.vista.cantidad.getDocument().addDocumentListener(new DocumentListener() {
           
            @Override
            public void insertUpdate(DocumentEvent e) {
                calcularTotal();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calcularTotal();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calcularTotal();
            }
        });   
        
        vista.btn_agregar.addActionListener(e -> agregarFila());
    }
    
        public void agregarFila() {
        try {

            CrearPedido productoSeleccionado = (CrearPedido) vista.comboProductos.getSelectedItem();

          String producto = productoSeleccionado.getProducto();
           String id_producto = Integer.toString(productoSeleccionado.getIdProducto());

            // Obtener los valores de los JTextFields
            String cantidad = vista.cantidad.getText();
            String precioUnitario = vista.p_unit.getText();
            String total = vista.total.getText();

            // Agregar la fila al DefaultTableModel
            // Accediendo al TableModel de la JTable
          DefaultTableModel model = (DefaultTableModel) vista.detalle_pedido.getModel();

            // Agregar una nueva fila al modelo de la tabla
            model.addRow(new Object[]{id_producto,producto,cantidad, precioUnitario, total});

            // Limpiar los campos después de agregar la fila
            vista.cantidad.setText("");
            vista.total.setText("");
            
            
            actualizarTotal();
            
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, 
                "Error al agregar la fila: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }}
    
 private void actualizarTotal() {
    double totalSum = 0.00;

    try {
        DefaultTableModel model = (DefaultTableModel) vista.detalle_pedido.getModel();

        // Recorremos las filas del TableModel
        for (int i = 0; i < model.getRowCount(); i++) {
            Object totalObject = model.getValueAt(i, 4); // Asegúrate de que esta sea la columna de "Total"

            // Verificamos si el valor no es null
            if (totalObject != null) {
                double total = 0.0;

                // Verificar si el valor es un Double o un String que representa un número
                if (totalObject instanceof Double) {
                    total = (Double) totalObject;
                } else if (totalObject instanceof String) {
                    total = Double.parseDouble((String) totalObject);
                }

                totalSum += total; // Sumar el total
            }
        }

        // Mostrar el total en el JTextField
        vista.total_pedido.setText(String.valueOf(totalSum));

    } catch (NumberFormatException ex) {
        // Manejar el error si la conversión falla
        JOptionPane.showMessageDialog(vista, "Error de formato en la fila: " + ex.getMessage());
    } catch (Exception ex) {
        // Manejar otras excepciones
        JOptionPane.showMessageDialog(vista, "Error inesperado: " + ex.getMessage());
    }
}   
        
    public void cargarDatosProductos() {
        modelo.cargarProductos(vista.comboProductos,vista.p_unit);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Redirigir al menú principal al presionar btnRegresar
        if (e.getSource() == vista.btnRegresar) {
            vista.dispose();  // Cerrar el formulario FrmCrearPedido
        }
    }
    
    public void CtrlMultiplicacion() {
        // Añadir el evento de escucha para el campo de texto 'txtCantidad'

    }

    public void calcularTotal() {
        try {
            
            // Obtener los valores de cantidad y precio unitario
            double cantidad = Double.parseDouble(vista.cantidad.getText());
            double pUnitario = Double.parseDouble(vista.p_unit.getText());

            // Calcular el total
            double total = cantidad * pUnitario;
            System.out.println("Entre a calcular total: ");
            // Mostrar el total en el JTextField txtTotal
            vista.total.setText(String.valueOf(total));
        } catch (NumberFormatException ex) {
            // Si no es un número válido, limpiar el campo total
            vista.total.setText("");
           
        }
    }    
    
private void enviarPedido() {
        DefaultTableModel model = (DefaultTableModel) vista.detalle_pedido.getModel(); // Asegúrate de tener un JTable en la vista
        
        try {
            for (int i = 0; i < model.getRowCount(); i++) {
                // Obtener los valores de cada fila
                int idProducto = (int) model.getValueAt(i, 0); // Suponiendo que el id_producto está en la columna 0
                int cantidad = (int) model.getValueAt(i, 1); // Suponiendo que cantidad está en la columna 1
                double precio = (double) model.getValueAt(i, 2); // Suponiendo que precio está en la columna 2

                // Llamar al método del modelo para guardar en la base de datos
                boolean exito = modelo.guardarDetallePedido(idProducto, cantidad, precio);
                
                if (!exito) {
                    JOptionPane.showMessageDialog(vista, "Error al guardar el pedido para el producto ID: " + idProducto);
                }
            }
            JOptionPane.showMessageDialog(vista, "Todos los pedidos guardados exitosamente.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Error: Formato de número incorrecto.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage());
        }
    }    


}


