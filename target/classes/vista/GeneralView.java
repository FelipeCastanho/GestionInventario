/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import logica.ProductoLogica;
import logica.ReporteLogica;
import modelo.Producto;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author danri
 */
public class GeneralView extends javax.swing.JFrame {

    /**
     * Creates new form GeneralView
     */
    public GeneralView() throws Exception {
        initComponents();
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        cargarReporte();
    }
    
    public void cargarReporte() throws Exception{
        ReporteLogica rpL = new ReporteLogica();
        int[][] salidas = rpL.reporteBasicoTransacciones("SALIDA");
        int[][] entradas = rpL.reporteBasicoTransacciones("ENTRADA");
        int[][] productos = rpL.reporteBasicoProductos();
        if(salidas.length > 0){
           cincoMasSalidas(salidas);
           cincoMasEntradas(entradas);
           cincoMenosSalidas(salidas);
           cincoMenosEntradas(entradas);
           grafica(productos);   
           jLabelTotalDevoluciones.setText(rpL.cantidadDevoluciones() + "");
        }else{
            JOptionPane.showMessageDialog(null, "Datos insuficientes para generar reporte");
        }
    }
    
    public void cincoMasSalidas(int[][] salidas) throws Exception{
        ciclo: {
            for (int i = 0; i < salidas.length; i++) {
                if(salidas[i][1] > 0){
                    DefaultTableModel modelo = (DefaultTableModel) jTableMayorSalidaGeneral.getModel();
                    Producto producto = buscarProducto(salidas[i][0]);
                    Object filaNueva[] = {producto.getNombre(), salidas[i][1], salidas[i][2], salidas[i][3]};
                    modelo.addRow(filaNueva); 
                }
                if(i > 5){
                    break ciclo;
                }
            }
        }
    }
    
    public void cincoMenosSalidas(int[][] salidas) throws Exception{
        int contador = 0;
        ciclo: {
            for (int i = salidas.length - 1; i >= 0; i--) {
                if(salidas[i][1] > 0){
                    DefaultTableModel modelo = (DefaultTableModel) jTableMenorSalidaGeneral.getModel();
                    Producto producto = buscarProducto(salidas[i][0]);
                    Object filaNueva[] = {producto.getNombre(), salidas[i][1], salidas[i][2], salidas[i][3]};
                    modelo.addRow(filaNueva); 
                    contador++;
                }
                if(contador > 5){
                    break ciclo;
                }
            }
        }
    }
    
    public void cincoMenosEntradas(int[][] entradas) throws Exception{
        int contador = 0;
        ciclo: {
            for (int i = entradas.length - 1; i >= 0; i--) {
                if(entradas[i][1] > 0){
                    DefaultTableModel modelo = (DefaultTableModel) jTableMenorEntradaGeneral.getModel();
                    Producto producto = buscarProducto(entradas[i][0]);
                    Object filaNueva[] = {producto.getNombre(), entradas[i][1], entradas[i][2], entradas[i][3]};
                    modelo.addRow(filaNueva); 
                    contador++;
                }
                if(contador > 5){
                    break ciclo;
                }
            }
        }
    }
    
    public void cincoMasEntradas(int[][] entradas) throws Exception{
        ciclo: {
            for (int i = 0; i < entradas.length; i++) {
                if(entradas[i][1] > 0){
                    DefaultTableModel modelo = (DefaultTableModel) jTableMayorEntradaGeneral.getModel();
                    Producto producto = buscarProducto(entradas[i][0]);
                    Object filaNueva[] = {producto.getNombre(), entradas[i][1], entradas[i][2], entradas[i][3]};
                    modelo.addRow(filaNueva); 
                }
                if(i > 5){
                    break ciclo;
                }
            }
        }
    }
    
    public Producto buscarProducto(int id) throws Exception{
        ProductoLogica pL = new ProductoLogica();
        Producto producto = pL.buscarProductoID(id);
        return producto;
    }
    
    public void grafica(int[][] productos) throws Exception{
        
        jPanel1.setLayout(new java.awt.BorderLayout());

        Producto caro = buscarProducto(productos[0][0]);
        Producto barato = buscarProducto(productos[1][0]);
        
        if(caro != null && barato != null){
            
            DefaultPieDataset data = new DefaultPieDataset();
            data.setValue(caro.getNombre(), productos[0][1]);
            data.setValue(barato.getNombre(), productos[1][1]);

            // Creando el Grafico
            JFreeChart chart = ChartFactory.createPieChart(
             "Gráfica precio productos", 
             data, 
             true, 
             true, 
             false);


           ChartPanel panel = new ChartPanel(chart);;

           jPanel1.add(panel,BorderLayout.CENTER);


           jPanel1.validate();
        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        // given some values of w & h
        return new Dimension(900, 800);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelEntradas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMayorEntradaGeneral = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabelTotalDevoluciones = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableMayorSalidaGeneral = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableMenorSalidaGeneral = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableMenorEntradaGeneral = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableMayorEntradaGeneral.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Entradas", "Costo actual", "Total ventas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableMayorEntradaGeneral);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Total de devoluciones: ");

        jLabelTotalDevoluciones.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabelTotalDevoluciones.setText("#");

        jTableMayorSalidaGeneral.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Entradas", "Costo actual", "Total ventas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTableMayorSalidaGeneral);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel4.setText("Productos con mayor salida");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setText("Productos con mayor entrada");

        jTableMenorSalidaGeneral.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Entradas", "Costo actual", "Total ventas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jTableMenorSalidaGeneral);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setText("Productos con menor salida");

        jTableMenorEntradaGeneral.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Entradas", "Costo actual", "Total ventas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(jTableMenorEntradaGeneral);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setText("Productos con menor entrada");

        javax.swing.GroupLayout jPanelEntradasLayout = new javax.swing.GroupLayout(jPanelEntradas);
        jPanelEntradas.setLayout(jPanelEntradasLayout);
        jPanelEntradasLayout.setHorizontalGroup(
            jPanelEntradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEntradasLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabelTotalDevoluciones, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(214, 214, 214))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEntradasLayout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(83, 83, 83))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEntradasLayout.createSequentialGroup()
                .addGroup(jPanelEntradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEntradasLayout.createSequentialGroup()
                        .addContainerGap(104, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(60, 60, 60))
                    .addGroup(jPanelEntradasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGroup(jPanelEntradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEntradasLayout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jLabel6))
                    .addGroup(jPanelEntradasLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEntradasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelEntradasLayout.setVerticalGroup(
            jPanelEntradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEntradasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEntradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelTotalDevoluciones))
                .addGap(18, 18, 18)
                .addGroup(jPanelEntradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEntradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEntradasLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7))
                    .addGroup(jPanelEntradasLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(11, 11, 11)))
                .addGroup(jPanelEntradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanelEntradasLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 249, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelEntradas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelEntradas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GeneralView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GeneralView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GeneralView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GeneralView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GeneralView().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(GeneralView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelTotalDevoluciones;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelEntradas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTableMayorEntradaGeneral;
    private javax.swing.JTable jTableMayorSalidaGeneral;
    private javax.swing.JTable jTableMenorEntradaGeneral;
    private javax.swing.JTable jTableMenorSalidaGeneral;
    // End of variables declaration//GEN-END:variables
}
