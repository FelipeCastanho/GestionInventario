/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.vista;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta es la clase que encargada del manejo de los reportes del programa.
 * @author jhon.
 */
public class ReportesView extends javax.swing.JPanel {

    /**
     * Constructor de la clase principal donde se inicializan los objetos del programa.
     * 
     */
    public ReportesView() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonSalidas = new javax.swing.JButton();
        jButtonEntradas = new javax.swing.JButton();
        jButtonGeneral = new javax.swing.JButton();

        jButtonSalidas.setText("Reporte de salidas");
        jButtonSalidas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalidasActionPerformed(evt);
            }
        });

        jButtonEntradas.setText("Reporte de entradas");
        jButtonEntradas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEntradasActionPerformed(evt);
            }
        });

        jButtonGeneral.setText("Reporte general");
        jButtonGeneral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGeneralActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonSalidas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonEntradas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(150, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(jButtonSalidas)
                .addGap(18, 18, 18)
                .addComponent(jButtonEntradas)
                .addGap(18, 18, 18)
                .addComponent(jButtonGeneral)
                .addContainerGap(160, Short.MAX_VALUE))
        );
    } // </editor-fold>//GEN-END:initComponents

    private void jButtonSalidasActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButtonSalidasActionPerformed
        try {
            SalidasView salidas = new SalidasView();
            salidas.setVisible(true);
            salidas.setLocationRelativeTo(null);
        } catch (Exception ex) {
            Logger.getLogger(ReportesView.class.getName()).log(Level.SEVERE, null, ex);
        }
    } //GEN-LAST:event_jButtonSalidasActionPerformed

    private void jButtonEntradasActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButtonEntradasActionPerformed
        try {
            EntradasView entradas = new EntradasView();
            entradas.setVisible(true);
            entradas.setLocationRelativeTo(null);
        } catch (Exception ex) {
            Logger.getLogger(ReportesView.class.getName()).log(Level.SEVERE, null, ex);
        }
    } //GEN-LAST:event_jButtonEntradasActionPerformed

    private void jButtonGeneralActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButtonGeneralActionPerformed
        try {
            GeneralView general = new GeneralView();
            general.setVisible(true);
            general.setLocationRelativeTo(this);
        } catch (Exception ex) {
            Logger.getLogger(ReportesView.class.getName()).log(Level.SEVERE, null, ex);
        }
    } //GEN-LAST:event_jButtonGeneralActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonEntradas;
    private javax.swing.JButton jButtonGeneral;
    private javax.swing.JButton jButtonSalidas;
    // End of variables declaration//GEN-END:variables
}
