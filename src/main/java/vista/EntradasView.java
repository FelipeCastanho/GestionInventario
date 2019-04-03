/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.vista;

import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import main.java.logica.ProductoLogica;
import main.java.logica.ReporteLogica;
import main.java.modelo.Producto;

/**
 * 	
 * @author danri
 */
public class EntradasView extends javax.swing.JFrame {

    /**
     * Creates new form EntradasView
     */
    public EntradasView() throws Exception {
        initComponents();
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        cargarReporte();
    }
    
    public void cargarReporte() throws Exception{
        ReporteLogica rpL = new ReporteLogica();
        int[][] entradas = rpL.reporteBasicoTransacciones("ENTRADA");
        if(entradas.length > 0){
            cincoMasEntradas(entradas);
            mayorMenorEntrada(entradas);
            totalEntradas(entradas);
            grafica(entradas);
        }else{
            JOptionPane.showMessageDialog(null, "Datos insuficientes para generar reporte");
        }
    }
    
    public void cincoMasEntradas(int[][] entradas) throws Exception{
        ciclo: {
            for (int i = 0; i < entradas.length; i++) {
                if(entradas[i][1] > 0){
                    DefaultTableModel modelo = (DefaultTableModel) jTableMayorEntrada.getModel();
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
    
    public void mayorMenorEntrada(int[][] entradas) throws Exception{
        Producto mayorEntrada = null;
        Producto menorEntrada = null;
        String entradaMayor = "";
        String costoMayor = "";
        String entradaMenor = "";
        String costoMenor = "";
        
        ciclo: {
            for (int i = 0; i < entradas.length; i++) {
                if(i == 0){
                    if(entradas[i][1] > 0){
                        mayorEntrada = buscarProducto(entradas[0][0]);
                        entradaMayor = mayorEntrada.getNombre();
                        costoMayor = entradas[0][3] + "";   
                    }
                }else if(entradas[i][1] == 0){
                    menorEntrada = buscarProducto(entradas[i - 1][0]);
                    entradaMenor = menorEntrada.getNombre();
                    costoMenor = entradas[i-1][3] + ""; 
                    break ciclo;
                }else if(i == (entradas.length - 1)){
                    menorEntrada = buscarProducto(entradas[i][0]);
                    entradaMenor = menorEntrada.getNombre();
                    costoMenor = entradas[i][3] + ""; 
                    break ciclo;
                }
            }
        }
        DefaultTableModel modelo = (DefaultTableModel) jTableMayorMenorEntrada.getModel();
        Object filaNueva[] = {entradaMayor, costoMayor, entradaMenor, costoMenor};
        modelo.addRow(filaNueva); 
    }
    
    public void totalEntradas(int[][] entradas){
        int totalProductos = 0;
        int costoTotal = 0;
        
        ciclo :{
            for (int i = 0; i < entradas.length; i++) {
                if(entradas[i][1] != 0){
                    totalProductos++;
                    costoTotal += entradas[i][3];
                }
            }   
        }
        
        DefaultTableModel modelo = (DefaultTableModel) jTableTotalEntradaCostos.getModel();
        Object filaNueva[] = {totalProductos, costoTotal};
        modelo.addRow(filaNueva);
    }
    
    public void grafica(int[][] entradas) throws Exception{
        
        jPanelGrafica.setLayout(new java.awt.BorderLayout());
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();

        ciclo: {
            for (int i = 0; i < entradas.length; i++) {
                if(entradas[i][1] != 0){
                    Producto producto = buscarProducto(entradas[i][0]);
                    line_chart_dataset.addValue(entradas[i][1], "cantidad", producto.getNombre());
                }else{
                    break ciclo;
                }
            }    
        }
        JFreeChart chart = ChartFactory.createLineChart("Relación precio/cantidad",
                "Cantidad","Precio",line_chart_dataset,PlotOrientation.VERTICAL,
                true,true,false);
        
       ChartPanel panel = new ChartPanel(chart);
       jPanelGrafica.add(panel,BorderLayout.CENTER);
       jPanelGrafica.validate();
    }
    
    public Producto buscarProducto(int id) throws Exception{
        ProductoLogica pL = new ProductoLogica();
        Producto producto = pL.buscarProductoID(id);
        return producto;
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
        jTableMayorEntrada = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableTotalEntradaCostos = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableMayorMenorEntrada = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jPanelGrafica = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableMayorEntrada.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableMayorEntrada);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Productos con mayor entrada");

        jTableTotalEntradaCostos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Total", "Costo total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableTotalEntradaCostos);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setText("Mayor entrada - Menor entrada");

        jTableMayorMenorEntrada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mayor salida", "Total ventas", "Menor salida", "Total ventas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTableMayorMenorEntrada);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setText("Total entradas");

        javax.swing.GroupLayout jPanelEntradasLayout = new javax.swing.GroupLayout(jPanelEntradas);
        jPanelEntradas.setLayout(jPanelEntradasLayout);
        jPanelEntradasLayout.setHorizontalGroup(
            jPanelEntradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEntradasLayout.createSequentialGroup()
                .addGroup(jPanelEntradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEntradasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelEntradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                            .addComponent(jScrollPane3)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanelEntradasLayout.createSequentialGroup()
                        .addGroup(jPanelEntradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelEntradasLayout.createSequentialGroup()
                                .addGap(124, 124, 124)
                                .addComponent(jLabel1))
                            .addGroup(jPanelEntradasLayout.createSequentialGroup()
                                .addGap(116, 116, 116)
                                .addComponent(jLabel2))
                            .addGroup(jPanelEntradasLayout.createSequentialGroup()
                                .addGap(179, 179, 179)
                                .addComponent(jLabel3)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelEntradasLayout.setVerticalGroup(
            jPanelEntradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEntradasLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelGraficaLayout = new javax.swing.GroupLayout(jPanelGrafica);
        jPanelGrafica.setLayout(jPanelGraficaLayout);
        jPanelGraficaLayout.setHorizontalGroup(
            jPanelGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelGraficaLayout.setVerticalGroup(
            jPanelGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 253, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelEntradas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelGrafica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelEntradas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelGrafica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
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
            java.util.logging.Logger.getLogger(EntradasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EntradasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EntradasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EntradasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new EntradasView().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(EntradasView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanelEntradas;
    private javax.swing.JPanel jPanelGrafica;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableMayorEntrada;
    private javax.swing.JTable jTableMayorMenorEntrada;
    private javax.swing.JTable jTableTotalEntradaCostos;
    // End of variables declaration//GEN-END:variables
}
