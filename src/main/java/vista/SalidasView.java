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
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import main.java.logica.ProductoLogica;
import main.java.logica.ReporteLogica;
import main.java.modelo.Producto;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Esta es la clase que encargada de las transacciones de salida.
 * @author jhon.
 */
public class SalidasView extends javax.swing.JFrame {

    /**
     * Constructor de la clase principal donde se inicializan los objetos del programa.
     * 
     */
    public SalidasView() throws Exception {
        initComponents();
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        cargarReporte();
    }
    
    /**
     * metodo que carga un reporte de salida.
     * 
     */
    public void cargarReporte() throws Exception {
        ReporteLogica rpL = new ReporteLogica();
        int[][] salidas = rpL.reporteBasicoTransacciones("SALIDA");
        if (salidas.length > 0) {
           cincoMasSalidas(salidas);
           mayorMenorSalida(salidas);
           totalSalidas(salidas);
           grafica(salidas);   
        } else {
            JOptionPane.showMessageDialog(null, "Datos insuficientes para generar reporte");
        }
    }
    
    /**
     * metodo que verifica cinco o mas transacciones de salida.
     * 
     */
    
    public void cincoMasSalidas(int[][] salidas) throws Exception {
        ciclo: {
            for (int i = 0; i < salidas.length; i++) {
                if (salidas[i][1] > 0) {
                    DefaultTableModel modelo = (DefaultTableModel) jTableMayorSalida.getModel();
                    Producto producto = buscarProducto(salidas[i][0]);
                    Object[] filaNueva = { producto.getNombre(), salidas[i][1], salidas[i][2], salidas[i][3] };
                    modelo.addRow(filaNueva); 
                }
                if (i > 5) {
                    break ciclo;
                }
            }
        }
    }
    
    /**
     * metodo que calcula la mayor o la menor transaccion de salida.
     * 
     */
    public void mayorMenorSalida(int[][] salidas) throws Exception {
        Producto mayorSalida = null;
        Producto menorSalida = null;
        String salidaMayor = "";
        String costoMayor = "";
        String salidaMenor = "";
        String costoMenor = "";
        
        ciclo: {
            for (int i = 0; i < salidas.length; i++) {
                if (i == 0) {
                    if (salidas[i][1] > 0) {
                        mayorSalida = buscarProducto(salidas[0][0]);
                        salidaMayor = mayorSalida.getNombre();
                        costoMayor = salidas[0][3] + "";   
                    }
                } else if (salidas[i][1] == 0) {
                        menorSalida = buscarProducto(salidas[i - 1][0]);
                        salidaMenor = menorSalida.getNombre();
                        costoMenor = salidas[i - 1][3] + ""; 
                        break ciclo;
                } else if (i == (salidas.length - 1)) {
                    menorSalida = buscarProducto(salidas[i][0]);
                    salidaMenor = menorSalida.getNombre();
                    costoMenor = salidas[i][3] + ""; 
                    break ciclo;
                }
            }
        }
        DefaultTableModel modelo = (DefaultTableModel) jTableMayorMenorSalida.getModel();
        Object[] filaNueva = {salidaMayor, costoMayor, salidaMenor, costoMenor};
        modelo.addRow(filaNueva); 
    }
    
    /**
     * metodo que calcula el total de las transacciones de salida.
     * 
     */
    public void totalSalidas(int[][] salidas) {
        int totalProductos = 0;
        int costoTotal = 0;
        
        ciclo : {
            for (int i = 0; i < salidas.length; i++) {
                if (salidas[i][1] != 0) {
                    totalProductos++;
                    costoTotal += salidas[i][3];
                }
            }   
        }
        
        DefaultTableModel modelo = (DefaultTableModel) jTableTotalSalidaCostos.getModel();
        Object[] filaNueva = {totalProductos, costoTotal};
        modelo.addRow(filaNueva);
    }
    
    /**
     * metodo para construir una grafica de reporte.
     * 
     */
    public void grafica(int[][] salidas) throws Exception {
        
        jPanelGrafica.setLayout(new java.awt.BorderLayout());
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();

        ciclo: {
            for (int i = 0; i < salidas.length; i++) {
                if (salidas[i][1] != 0) {
                    Producto producto = buscarProducto(salidas[i][0]);
                    line_chart_dataset.addValue(salidas[i][1], "cantidad", producto.getNombre());
                } else {
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
    
    /**
     * Metodo para buscar un producto pro su id desde la BD.
     * 
     */
    public Producto buscarProducto(int id) throws Exception {
        ProductoLogica pl = new ProductoLogica();
        Producto producto = pl.buscarProductoID(id);
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

        defaultCategoryDataset1 = new org.jfree.data.category.DefaultCategoryDataset();
        jPanelSalidas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMayorSalida = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableTotalSalidaCostos = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableMayorMenorSalida = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jPanelGrafica = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableMayorSalida.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Producto", "Salidas", "Costo actual", "Total ventas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableMayorSalida);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Productos con mayor salida");

        jTableTotalSalidaCostos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTableTotalSalidaCostos);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setText("Mayor salida - Menor salida");

        jTableMayorMenorSalida.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTableMayorMenorSalida);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setText("Total salidas");

        javax.swing.GroupLayout jPanelSalidasLayout = new javax.swing.GroupLayout(jPanelSalidas);
        jPanelSalidas.setLayout(jPanelSalidasLayout);
        jPanelSalidasLayout.setHorizontalGroup(
            jPanelSalidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSalidasLayout.createSequentialGroup()
                .addGroup(jPanelSalidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSalidasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelSalidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, 
                            		javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                            .addComponent(jScrollPane3)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanelSalidasLayout.createSequentialGroup()
                        .addGroup(jPanelSalidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelSalidasLayout.createSequentialGroup()
                                .addGap(124, 124, 124)
                                .addComponent(jLabel1))
                            .addGroup(jPanelSalidasLayout.createSequentialGroup()
                                .addGap(186, 186, 186)
                                .addComponent(jLabel3)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanelSalidasLayout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelSalidasLayout.setVerticalGroup(
            jPanelSalidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSalidasLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelGrafica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelSalidas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelSalidas, javax.swing.GroupLayout.PREFERRED_SIZE, 
               javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelGrafica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * metodo principal de la clase donde se cargan los componentes.
     * 
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
            java.util.logging.Logger.getLogger(SalidasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalidasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalidasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalidasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new SalidasView().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(SalidasView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jfree.data.category.DefaultCategoryDataset defaultCategoryDataset1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanelGrafica;
    private javax.swing.JPanel jPanelSalidas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableMayorMenorSalida;
    private javax.swing.JTable jTableMayorSalida;
    private javax.swing.JTable jTableTotalSalidaCostos;
    // End of variables declaration//GEN-END:variables
}
