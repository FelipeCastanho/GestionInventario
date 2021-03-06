
package main.java.vista;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import main.java.modelo.Producto;
import main.java.logica.ProductoLogica;

/**
 * Esta es la clase relacionada con la consulta de los productos.
 * como tambien agregar los mismos, modificar y eliminar los mismos.
 * @author jhon.
 */

public class ProductoView extends javax.swing.JPanel {


    Producto producto;
    ProductoLogica productoLogica;

    /**
     * Constructor de la clase donde se inicializan los componentes.
     */        
    public ProductoView() { 
        producto = new Producto();
        productoLogica = new ProductoLogica();
        initComponents();
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jTextField_NombreProducto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldCantidad = new javax.swing.JTextField();
        jButton_Registrar = new javax.swing.JButton();
        jButton_Consultar = new javax.swing.JButton();
        jButton_Modificar = new javax.swing.JButton();
        jButton_Eliminar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField_Costo = new javax.swing.JTextField();
        jButton_BorrarCampos = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(450, 400));

        jLabel2.setText("Nombre Producto:");

        jLabel3.setText("Cantidad: ");

        jButton_Registrar.setText("Registrar");
        jButton_Registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RegistrarActionPerformed(evt);
            }
        });

        jButton_Consultar.setText("Consultar");
        jButton_Consultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ConsultarActionPerformed(evt);
            }
        });

        jButton_Modificar.setText("Modificar");
        jButton_Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ModificarActionPerformed(evt);
            }
        });

        jButton_Eliminar.setText("Eliminar");
        jButton_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_EliminarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("PRODUCTOS");

        jLabel6.setText("Costo: ");

        jButton_BorrarCampos.setText("Limpiar");
        jButton_BorrarCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_BorrarCamposActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton_Consultar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton_Registrar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton_Modificar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton_Eliminar))
                            .addComponent(jLabel6)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_NombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, 
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField_Costo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton_BorrarCampos)))))
                .addContainerGap(178, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel5)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField_NombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 
                 javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 
                 javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField_Costo, javax.swing.GroupLayout.PREFERRED_SIZE, 
                   javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_BorrarCampos))
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_Consultar)
                    .addComponent(jButton_Registrar)
                    .addComponent(jButton_Modificar)
                    .addComponent(jButton_Eliminar))
                .addContainerGap(132, Short.MAX_VALUE))
        );
    } // </editor-fold>//GEN-END:initComponents

    private void jButton_RegistrarActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton_RegistrarActionPerformed
     
        String nombre = jTextField_NombreProducto.getText();
        String cantidad = jTextFieldCantidad.getText();
        String costo = jTextField_Costo.getText();
        
        try {
            productoLogica = new ProductoLogica();
            producto = new Producto();
            producto.setNombre(nombre);
            producto.setCantidad(Integer.parseInt(cantidad));
            producto.setCosto(Integer.parseInt(costo));
            
            productoLogica.registrarProducto(producto);
            JOptionPane.showMessageDialog(this, "Se registro con exito");
        } catch (Exception ex) {
             JOptionPane.showMessageDialog(this, "Error no se pudo crear el producto");
        }
    } //GEN-LAST:event_jButton_RegistrarActionPerformed

    private void jButton_ConsultarActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton_ConsultarActionPerformed
        String nombreEquipo = jTextField_NombreProducto.getText();
        ProductoLogica productoLogica = new ProductoLogica();
        Producto producto = new Producto();
        
        if (jTextField_NombreProducto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo Nombre Producto no debe estar vacio"); 
        } else {
            try {
                
                producto = productoLogica.buscarProducto(nombreEquipo);
                jTextField_NombreProducto.setText(producto.getNombre());
                jTextFieldCantidad.setText(String.valueOf(producto.getCantidad()));
                jTextField_Costo.setText(String.valueOf(producto.getCosto()));
                
            
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "No se pudo encontrar el producto");
            }
        }
        
    } //GEN-LAST:event_jButton_ConsultarActionPerformed

    
    private void jButton_BorrarCamposActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton_BorrarCamposActionPerformed
        
        jTextField_NombreProducto.setText(" ");
        jTextFieldCantidad.setText(" ");
        jTextField_Costo.setText(" ");
    } //GEN-LAST:event_jButton_BorrarCamposActionPerformed

    private void jButton_ModificarActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton_ModificarActionPerformed
        String nombreProducto = jTextField_NombreProducto.getText();
        ProductoLogica productoLogica = new ProductoLogica();
        Producto producto = new Producto();
        int nuevaCantidad;
        int nuevoCosto;
        if (jTextField_NombreProducto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo Nombre Producto no debe estar vacio");
        } else {
            try {    
                producto = productoLogica.buscarProducto(nombreProducto);
                nuevaCantidad = Integer.parseInt(jTextFieldCantidad.getText());
                nuevoCosto = Integer.parseInt(jTextField_Costo.getText());
                productoLogica.actualizarProductos(producto, nuevaCantidad, nuevoCosto, true);
                JOptionPane.showMessageDialog(this, "Producto Modificado con exito");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "No se pudo modificar el producto");
            }            
        }
    } //GEN-LAST:event_jButton_ModificarActionPerformed

    private void jButton_EliminarActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton_EliminarActionPerformed
        String nombreProducto = jTextField_NombreProducto.getText();
        ProductoLogica productoLogica = new ProductoLogica();
        Producto producto = new Producto();
        if (jTextField_NombreProducto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo Nombre Producto no debe estar vacio");
        } else {
            try {    
                producto = productoLogica.buscarProducto(nombreProducto);
                productoLogica.eliminarProducto(producto);
                jTextField_NombreProducto.setText(" ");
                jTextFieldCantidad.setText(" ");
                jTextField_Costo.setText(" ");
                JOptionPane.showMessageDialog(this, "Producto Eliminado con exito");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el producto");
            }            
        }
    
    } //GEN-LAST:event_jButton_EliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_BorrarCampos;
    private javax.swing.JButton jButton_Consultar;
    private javax.swing.JButton jButton_Eliminar;
    private javax.swing.JButton jButton_Modificar;
    private javax.swing.JButton jButton_Registrar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jTextFieldCantidad;
    private javax.swing.JTextField jTextField_Costo;
    private javax.swing.JTextField jTextField_NombreProducto;
    // End of variables declaration//GEN-END:variables
}
