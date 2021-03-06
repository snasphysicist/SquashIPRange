/******************************************************************
*Squash IP Range is offered under the lesser GPL licence version 3
*Please refer to the LICENSE file included at the top level
*of the git repo for full information on this licence
*Written by snasphysicist (Scott N A Smith)
*******************************************************************/
package squashiprange;

/**
 *
 * @author snasphysicist
 */
public class SquashIPRangeUI extends javax.swing.JFrame {
    
    //Writes text to the form which tells the user
    //how many ranges/addresses were parsed from the input
    private void setInputNumbers( Integer numberOfRanges , Integer numberOfAddresses ) {
        jLabel3.setText( "Ranges in: " + numberOfRanges.toString() );
        jLabel4.setText( "Addresses in: " + numberOfAddresses.toString() );
    }
    
    //Writes text to the form which tells the user
    //how many ranges/addresses appear in the output
    private void setOutputNumbers( Integer numberOfRanges , Integer numberOfAddresses ) {
        jLabel5.setText( "Ranges out: " + numberOfRanges.toString() );
        jLabel6.setText( "Addresses out: " + numberOfAddresses.toString() );
    }
    
    /**
     * Creates new form SquashIPRangeUI
     */
    public SquashIPRangeUI() {
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1285, 550));
        setPreferredSize(new java.awt.Dimension(1285, 550));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Squash IP Range"));
        jPanel1.setRequestFocusEnabled(false);

        jLabel1.setText("Input IP Ranges:");

        jButton1.setText("Find Overlap");
        jButton1.setMaximumSize(new java.awt.Dimension(130, 27));
        jButton1.setMinimumSize(new java.awt.Dimension(130, 27));
        jButton1.setPreferredSize(new java.awt.Dimension(130, 27));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Squash");
        jButton2.setMaximumSize(new java.awt.Dimension(130, 27));
        jButton2.setMinimumSize(new java.awt.Dimension(130, 27));
        jButton2.setPreferredSize(new java.awt.Dimension(130, 27));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Clear Input");
        jButton3.setMaximumSize(new java.awt.Dimension(130, 27));
        jButton3.setMinimumSize(new java.awt.Dimension(130, 27));
        jButton3.setPreferredSize(new java.awt.Dimension(130, 27));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setText("Output:");
        jLabel2.setToolTipText("");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);
        jTextArea2.getAccessibleContext().setAccessibleName("Squash IP Range");
        jTextArea2.getAccessibleContext().setAccessibleDescription("");

        jLabel3.setText("Ranges in:");
        jLabel3.setToolTipText("");

        jLabel4.setText("Addresses in:");

        jLabel5.setText("Ranges out:");

        jLabel6.setText("Addresses out:");

        jButton6.setText("Reformat");
        jButton6.setMaximumSize(new java.awt.Dimension(130, 27));
        jButton6.setMinimumSize(new java.awt.Dimension(130, 27));
        jButton6.setPreferredSize(new java.awt.Dimension(130, 27));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("To Clipboard");
        jButton7.setMaximumSize(new java.awt.Dimension(130, 27));
        jButton7.setMinimumSize(new java.awt.Dimension(130, 27));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton5.setText("About");
        jButton5.setMaximumSize(new java.awt.Dimension(130, 27));
        jButton5.setMinimumSize(new java.awt.Dimension(130, 27));
        jButton5.setPreferredSize(new java.awt.Dimension(130, 27));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton4.setText("Close");
        jButton4.setMaximumSize(new java.awt.Dimension(130, 27));
        jButton4.setMinimumSize(new java.awt.Dimension(130, 27));
        jButton4.setPreferredSize(new java.awt.Dimension(130, 27));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Quick");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Full");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButton2)
                                    .addComponent(jRadioButton1)))))
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(54, 54, 54)
                        .addComponent(jLabel4)))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(54, 54, 54)
                        .addComponent(jLabel6))
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(10, 10, 10))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jPanel1.getAccessibleContext().setAccessibleName("Squash IP Range v 1.0");
        jPanel1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Find Overlap button
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        //Reset outputs to blank at the start
        //to ensure that incorrect information
        //isn't reported, even if there is a crash
        setOutputNumbers( 0 , 0 ) ;
        jTextArea2.setText( "" ) ;

        //Converts the raw input into an array of IPv4ranges
        IPv4range[] inputRanges = SquashIPRange.parseStringRanges( SquashIPRange.splitStringRanges( jTextArea1.getText() ) ) ;
        
        //Converts this array of IPv4ranges into a string
        //detailing the overlap between those ranges
        String outputText = SquashIPRange.findRangeSetOverlap( inputRanges ) ;
                
        //If no overlap is found between any of the ranges
        //report this in the output text box
        if ( outputText.equals( "" ) ) {
            outputText = "No overlap found" ;
        }
        
        //Otherwise write the details of
        //the overlapping ranges to the output box
        jTextArea2.setText( outputText ) ;
        
        //Display the number of IP address ranges and
        //the number of IP addresses in the input box
        setInputNumbers( inputRanges.length , SquashIPRange.countAddresses( inputRanges ) ) ;
        
    }//GEN-LAST:event_jButton1ActionPerformed

    //Close button
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        System.exit(0) ;
    }//GEN-LAST:event_jButton4ActionPerformed
    
    //Clear Input button
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jTextArea1.setText( "" ) ;
    }//GEN-LAST:event_jButton3ActionPerformed

    //Squash button
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        //Reset outputs to blank at the start
        //to ensure that incorrect information
        //isn't reported, even if there is a crash
        setOutputNumbers( 0 , 0 ) ;
        jTextArea2.setText( "" ) ;
        
        int i ;
        String outputText = "" ;
        IPv4range[] inputRanges ;
        IPv4range[] ipRangesOut ;

        inputRanges = SquashIPRange.parseStringRanges( SquashIPRange.splitStringRanges( jTextArea1.getText() ) ) ;
        
        inputRanges = SquashIPRange.sortRangeArray( inputRanges ) ;
        
        setInputNumbers( inputRanges.length , SquashIPRange.countAddresses( inputRanges ) ) ;
        
        // jRadioButton1 is the button with label "Quick"
        if( jRadioButton1.isSelected() ) {
            ipRangesOut = SquashIPRange.quickSquash( inputRanges ) ;
        } else {
            ipRangesOut = SquashIPRange.fullSquash( inputRanges ) ;
        }
        
        //Get the resulting ranges in human readable format
        //and write them to a string to output
        for( i=0 ; i<ipRangesOut.length ; i++ ) {
            outputText += ipRangesOut[i].convertRangeHumanReadable( ipRangesOut[i] ) + "\n" ;
        }
        
        //Write this to the lower area
        jTextArea2.setText( outputText ) ;

        setOutputNumbers( ipRangesOut.length , SquashIPRange.countAddresses( ipRangesOut ) ) ;
        
    }//GEN-LAST:event_jButton2ActionPerformed
    
    //About button
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        new AboutSquashIPRangeUI( this , false ).setVisible(true) ;
    }//GEN-LAST:event_jButton5ActionPerformed
    
    //Reformat button
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        
        //Reset outputs to blank at the start
        //to ensure that incorrect information
        //isn't reported, even if there is a crash
        setOutputNumbers( 0 , 0 ) ;
        jTextArea2.setText( "" ) ;

        //Loop counter
        int i ;

        //We'll gather the output text here
        String outputText = "" ;
        
        //All we do is attempt to parse the ranges
        //written in the input box
        IPv4range[] inputRanges = SquashIPRange.parseStringRanges( SquashIPRange.splitStringRanges( jTextArea1.getText() ) ) ;
        
        //Then we convert these into strings and
        //write what we've found into the output box
        for( i=0 ; i<inputRanges.length ; i++ ) {
            outputText += inputRanges[i].convertRangeHumanReadable( inputRanges[i] ) + "\n\r" ;
        }
        
        setInputNumbers( inputRanges.length , SquashIPRange.countAddresses( inputRanges ) ) ;
        
        jTextArea2.setText( outputText ) ;
        
        setOutputNumbers( inputRanges.length , SquashIPRange.countAddresses( inputRanges ) ) ;
        
    }//GEN-LAST:event_jButton6ActionPerformed

    //To Clipboard button
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        //Simply send the text into the output box to the
        //system clipboard using the copyToClipboard method
        //from SquashIPRange
        SquashIPRange.copyToClipboard( jTextArea2.getText() ) ;
    }//GEN-LAST:event_jButton7ActionPerformed

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
            java.util.logging.Logger.getLogger(SquashIPRangeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SquashIPRangeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SquashIPRangeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SquashIPRangeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SquashIPRangeUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
