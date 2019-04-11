/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.vista;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import main.java.logica.ProductoLogica;
import main.java.logica.TransaccionLogica;
import main.java.logica.TransaccionProductoLogica;
import main.java.modelo.Producto;
import main.java.modelo.Transaccion;


/**
 * Esta es la clase principal encargada de gestionar lo relacioanda a la transacciones.
 * de los productos que se encuentran en inventario.
 * @author jhon.
 */
public class TransaccionView extends javax.swing.JPanel {

    /**
     * Constructor de la clase donde se inicializan todos los componentes.
     * 
     */
    public TransaccionView() {
        initComponents();
        jComboBox_TipoTransaccion.removeAllItems();
        jComboBox_TipoTransaccion.addItem("ENTRADA");
        jComboBox_TipoTransaccion.addItem("SALIDA");
         
        setVisible(true);
    }
    

/**
* metodo encargado de limpiar la informacion de la tabla.
* 
*/
public void limpiarTabla() {
    DefaultTableModel tb = (DefaultTableModel) jTableTablaProductos.getModel();
    int a = jTableTablaProductos.getRowCount() - 1;
    for (int i = a; i >= 0; i--) {           
        tb.removeRow(tb.getRowCount() - 1);
    } 
    }

/**
 * Metodo encargado de agregar la información a una tabla.
 * 
 */
public void cargarTabla(Producto producto) {
        
    DefaultTableModel modelo = (DefaultTableModel) jTableTablaProductos.getModel();
    Object[] filaNueva = { producto.getId(),producto.getNombre(),producto.getCantidad(),producto.getCosto() };
    modelo.addRow(filaNueva);
    
}
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField_NombreCliente = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBox_TipoTransaccion = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser_FechaTransaccion = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableTablaProductos = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jButton_RegistrarTransaccion = new javax.swing.JButton();
        jTextField_NombreProducto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton_buscar = new javax.swing.JButton();
        jButton_borrarTabla = new javax.swing.JButton();

        jButton1.setText("jButton1");

        jLabel1.setText("Nombre Cliente");

        jLabel2.setText("Tipo Transaccion");

        jComboBox_TipoTransaccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Fecha Transaccion");

        jTableTablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Producto", "Nombre", "Cantidad", "Costo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableTablaProductos);
        if (jTableTablaProductos.getColumnModel().getColumnCount() > 0) {
            jTableTablaProductos.getColumnModel().getColumn(0).setMinWidth(0);
            jTableTablaProductos.getColumnModel().getColumn(0).setPreferredWidth(0);
            jTableTablaProductos.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jLabel4.setText("Productos agredados a esta transaccion:");

        jButton_RegistrarTransaccion.setText("Registrar Transaccion");
        jButton_RegistrarTransaccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RegistrarTransaccionActionPerformed(evt);
            }
        });

        jLabel6.setText("Nombre producto a buscar");

        jButton_buscar.setText("Buscar");
        jButton_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_buscarActionPerformed(evt);
            }
        });

        jButton_borrarTabla.setText("Borrar Tabla");
        jButton_borrarTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_borrarTablaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(jTextField_NombreCliente)
                            .addComponent(jLabel2)
                            .addComponent(jComboBox_TipoTransaccion, 0, 138, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jDateChooser_FechaTransaccion, javax.swing.GroupLayout.PREFERRED_SIZE, 113, 
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 
                                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField_NombreProducto))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_buscar))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jButton_RegistrarTransaccion)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 
                         javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_borrarTabla))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_NombreCliente, 
                     javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDateChooser_FechaTransaccion, 
                     javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_TipoTransaccion, 
                   javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_NombreProducto, 
                   javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_buscar))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_RegistrarTransaccion)
                    .addComponent(jButton_borrarTabla))
                .addContainerGap(99, Short.MAX_VALUE))
        );
    } // </editor-fold>//GEN-END:initComponents

    private void jButton_buscarActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton_buscarActionPerformed
        ProductoLogica pLogica = new ProductoLogica();
        Producto producto =  new Producto();
        List<Producto> listaproductos = pLogica.listarProductos();
        List<Producto> listaproductosFilrada = new ArrayList<>();
        String nombre = jTextField_NombreProducto.getText();
        
        for (int i = 0;i < listaproductos.size();i++) {
            if (listaproductos.get(i).getNombre().equals(nombre) && listaproductos.get(i).getEstado().equals("DISPONIBLE")) {
                listaproductosFilrada.add(listaproductos.get(i));
            }
        }
        Object[] options = comboBoxPane(listaproductosFilrada);
        JComboBox optionList = new JComboBox(options);
        if (sizeofListaProductos(listaproductosFilrada) > 0) {
            optionList.setSelectedIndex(listaproductosFilrada.size() - 1);
            JOptionPane.showMessageDialog(null, optionList, "Selecciona un producto",
            JOptionPane.QUESTION_MESSAGE);   
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron productos");
        }
        
        try {
            producto = pLogica.buscarProductoID(listaproductosFilrada.get(optionList.getSelectedIndex()).getId());
            cargarTabla(producto);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se encontraron productos");
        }                
    } //GEN-LAST:event_jButton_buscarActionPerformed

    private void jButton_borrarTablaActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton_borrarTablaActionPerformed
        // TODO add your handling code here:
        limpiarTabla();
    } //GEN-LAST:event_jButton_borrarTablaActionPerformed

    private void jButton_RegistrarTransaccionActionPerformed(java.awt.event.ActionEvent evt) { 
        Transaccion transaccion = new Transaccion();
        ProductoLogica pLogica =  new ProductoLogica();
        TransaccionLogica tLogica =  new TransaccionLogica();
        TransaccionProductoLogica tpLogica = new TransaccionProductoLogica();
        
        
        String nombreCliente = jTextField_NombreCliente.getText();
        String tipo = jComboBox_TipoTransaccion.getSelectedItem().toString();
        Date date = new Date();
        date = jDateChooser_FechaTransaccion.getDate();
        transaccion.setNombreCliente(nombreCliente);
        transaccion.setTipo(tipo);
        transaccion.setFecha(date);
        
        List<Producto> listaproductos = new ArrayList<>();
        DefaultTableModel modelo = (DefaultTableModel) jTableTablaProductos.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            try {
                Producto p = pLogica.buscarProductoID((int) jTableTablaProductos.getValueAt(i,0));
                p.setCantidad((int) jTableTablaProductos.getValueAt(i,2));
                listaproductos.add(p);
            } catch (Exception ex) {
                Logger.getLogger(TransaccionView.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        
        try {
            tLogica.registrarTransaccion(transaccion);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo registrar la transaccion ");
        }
        
        
        try {
            tpLogica.registrarTransaccionProductos(listaproductos, transaccion);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo registrar los productos asociados a esa transaccion ");
        }
    } //GEN-LAST:event_jButton_RegistrarTransaccionActionPerformed

/**
* metodo encargado de desplegar la información de la lista desplegable.
* 
*/
public Object[] comboBoxPane(List<Producto> listaProductos) {
    Object[] options = new Object[listaProductos.size()];
    
    for (int i = 0; i < listaProductos.size(); i++) {
        options[i] = listaProductos.get(i).getNombre() + ", " + listaProductos.get(i).getCantidad() + ", " + listaProductos.get(i).getCosto();
    }
    
    return options;
}  

public int sizeofListaProductos(List<Producto> listaProductos) {
    return listaProductos.size();
}
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_RegistrarTransaccion;
    private javax.swing.JButton jButton_borrarTabla;
    private javax.swing.JButton jButton_buscar;
    private javax.swing.JComboBox<String> jComboBox_TipoTransaccion;
    private com.toedter.calendar.JDateChooser jDateChooser_FechaTransaccion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableTablaProductos;
    private javax.swing.JTextField jTextField_NombreCliente;
    private javax.swing.JTextField jTextField_NombreProducto;
    // End of variables declaration//GEN-END:variables
}
