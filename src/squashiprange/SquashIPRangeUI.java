/******************************************************************
*Squash IP Range is offered under the lesser GPL licence version 3
*Please refer to the LICENSE file included at the top level
*of the git repo for full information on this licence
*Written by snasphysicist (Scott N A Smith)
*******************************************************************/
package squashiprange;

import static squashiprange.SquashIPRange.appendToIPv4rangeArray;

/**
 *
 * @author snasphysicist
 */
public class SquashIPRangeUI extends javax.swing.JFrame {
    
    private static IPv4range[] swapRanges( IPv4range[] toSwap , int index1 , int index2 ) {
        
        int i ;
        int lindex ;
        int uindex ;
        IPv4range[] intmRangeArray = new IPv4range[ toSwap.length ] ;
        
        if( index1 < index2 ) {
            lindex = index1 ;
            uindex = index2 ;
        } else {
            lindex = index2 ;
            uindex = index1 ;
        }
        
        for( i=0 ; i<lindex ; i++ ) {
            intmRangeArray[i] = toSwap[i] ;
        }
        
        intmRangeArray[lindex] = toSwap[uindex] ;
        
        for( i=lindex+1 ; i<uindex ; i++ ) {
            intmRangeArray[i] = toSwap[i] ;
        }
        
        intmRangeArray[uindex] = toSwap[lindex] ;
        
        for( i=uindex+1 ; i<intmRangeArray.length ; i++ ) {
            intmRangeArray[i] = toSwap[i] ;
        }
        
        return intmRangeArray ;
        
    }
    
    private static IPv4range[] sortRangeArray( IPv4range[] toSort ) {
        int i ;
        boolean didSwap = true ;
        while( didSwap ) {
            didSwap = false ;
            for( i=0 ; i<toSort.length-1 ; i++ ) {
                //System.out.println( this.addressArray[i].getIPAsString() + " " + this.addressArray[i+1].getIPAsString() ) ;
                if( toSort[i].getAddressFromRange(0).getIPAsNumber() > toSort[i+1].getAddressFromRange(0).getIPAsNumber() ) {
                    toSort = swapRanges( toSort , i , i+1 );
                    didSwap = true ;
                }
            }
        }
        return toSort ;
    }
    
    public static int substringOccurrences( String searchin , String searchfor ) {
        int i ;
        int numberOfOccurrences = 0 ;
        for( i=0 ; i<=(searchin.length()-searchfor.length()) ; i++ ) {
            if( searchfor.equals( searchin.substring( i , i+searchfor.length() ) ) ) {
                numberOfOccurrences++ ;
            }
        }
        return numberOfOccurrences ;
    }
    
    public static String[] splitStringRanges( String ranges ) {
        
        int i ;
        
        //Accepted delimiters between IP address ranges
        //which will be replaced by commas
        String[] delimiters = new String[]{"\\|",";"," ","\t","\n","\r"} ;
        
        //Possible characters that look like dashes (in unicode)
        //These will be replaced by dashes
        String[] allDashes = new String[]{"~","\u002D","\u005F","\u007E","\u00AD",
                                            "\u00AF","\u02C9","\u02CD","\u02D7","\u02DC",
                                            "\u2010","\u2011","\u2012","\u203E","\u2043",
                                            "\u207B","\u208B","\u2212","\u223C","\u23AF",
                                            "\u23E4","\u2500","\u2796","\u2E3A","\u2E3B" } ;
        
        //This will contain the split set of
        //ip address ranges as strings
        String[] separatedRanges ;
        
        //Replacing dash like characters with the standard dash character
        for( i=0 ; i<allDashes.length ; i++ ) {
            ranges = ranges.replace( allDashes[i] , "-" ) ;
        }
        
        //Replacing accepted delimiters with commas
        for( i=0 ; i<delimiters.length ; i++ ) {
            ranges = ranges.replace( delimiters[i] , "," ) ;
        }
        
        //Removing repeated dash characters
        while( ranges.contains( "--" ) ) {
            ranges = ranges.replace( "--" , "-" ) ;
        }
        
        //Removing repeated comma characters
        while( ranges.contains( ",," ) ) {
            ranges = ranges.replace( ",," , "," ) ;
        }
        
        //Then split by comma (representing all delimiters)
        separatedRanges = ranges.split( "," ) ;
        
        return separatedRanges ;
    }
    
    public static IPv4range[] parseStringRanges( String[] ranges ) {
        
        Integer i,j ;
        boolean parsedRange ;
        IPv4range intmRange ;
        String[] sectors ;
        IPv4address splitTool = new IPv4address(0L) ;
        int sector3min , sector3max ;
        IPv4range[] allRanges = new IPv4range[0] ;
        
        //Taking the string ranges
        //and converting them to IPv4ranges
        //In a robust, error handled way
        for( i=0 ; i<ranges.length ; i++ ) {
            try {
                intmRange = new IPv4range() ;
                ranges[i] = ranges[i].replaceAll("\\*", "0-255") ;
                if( substringOccurrences( ranges[i] , "." ) > 3 ) {
                    //Constuctor call split over three lines to
                    //restrict width of line
                    //range( address( string ) , address( string ) )
                    intmRange = new IPv4range( 
                                new IPv4address( ranges[i].split("-")[0] ) ,
                                new IPv4address( ranges[i].split("-")[1] ) ) ;
                    parsedRange = true ;
                } else {
                    sectors = splitTool.splitBySector( ranges[i] ) ;
                    if ( sectors[3].contains( "/" ) ) {
                        parsedRange = intmRange.parseAddSlashNotation( ranges[i] ) ;
                    } else if ( sectors[2].contains( "-" ) ) {
                        sector3min = new Integer( sectors[2].split( "-" )[0] ) ;
                        sector3max = new Integer( sectors[2].split( "-" )[1] ) ;
                        parsedRange = true ;
                        for(j=sector3min;j<=sector3max;j++) {
                            parsedRange = parsedRange && intmRange.parseAddDashNotation( sectors[0] + "." + sectors[1] + "." + j.toString() + "." + sectors[3] ) ;
                        }
                    } else {
                        parsedRange = intmRange.parseAddDashNotation( ranges[i] ) ;
                    }
                }
                if( parsedRange ) {
                    allRanges = appendToIPv4rangeArray( allRanges , intmRange ) ;
                }
            } 
            catch ( Exception e ) {
                System.out.println( "Failed to parse range" ) ;
                //e.printStackTrace() ;
            }
        }
        
        return allRanges ;
        
    }
    
    //Takes an array of ranges as an input
    //and returns the total number of addresses
    //in all of the ranges
    public static Integer countAddresses( IPv4range[] inRanges ) {
        int i ;
        Integer j = 0 ;
        for( i=0 ; i<inRanges.length ; i++ ) {
            j = j + inRanges[i].getSizeOfRange() ;
        }
        return j ;
    }
    
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
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(700, 600));
        setPreferredSize(new java.awt.Dimension(600, 550));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Squash IP Ranges v 1.0"));

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

        jLabel3.setText("Ranges in:");
        jLabel3.setToolTipText("");

        jLabel4.setText("Addresses in:");

        jLabel5.setText("Ranges out:");

        jLabel6.setText("Addresses out:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(60, 60, 60)
                                .addComponent(jLabel5)
                                .addGap(60, 60, 60)
                                .addComponent(jLabel6))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(60, 60, 60)
                                .addComponent(jLabel3)
                                .addGap(60, 60, 60)
                                .addComponent(jLabel4)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 154, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton4.setText("Close");
        jButton4.setMaximumSize(new java.awt.Dimension(130, 27));
        jButton4.setMinimumSize(new java.awt.Dimension(130, 27));
        jButton4.setPreferredSize(new java.awt.Dimension(130, 27));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Find Overlap button
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        //Reset outputs to blank at the start
        //to ensure that incorrect information
        //isn't reported, even if there is a crash
        setOutputNumbers( 0 , 0 ) ;
        jTextArea2.setText( "" ) ;
        
        int i , j ;
        String outputText = "" ;
        IPv4range[] inputRanges = parseStringRanges( splitStringRanges( jTextArea1.getText() ) ) ;
        IPv4range overlappingAddresses ;
        
        for( i=0 ; i<inputRanges.length ; i++ ) {
            for( j=i+1 ; j<inputRanges.length ; j++ ) {
                overlappingAddresses = inputRanges[i].findOverlap( inputRanges[j] ) ;
                if( overlappingAddresses.getSizeOfRange() > 0 ) {
                    outputText += "Overlap between " 
                                + inputRanges[i].convertRangeHumanReadable( inputRanges[i] )
                                + " and " 
                                + inputRanges[j].convertRangeHumanReadable( inputRanges[j] )
                                + " : "
                                + overlappingAddresses.convertRangeHumanReadable( overlappingAddresses )
                                + "\n" ;
                }
            }
        }
        
        //If no overlap is found between any of the ranges
        //report this in the output text box
        if ( outputText.equals( "" ) ) {
            outputText = "No overlap found" ;
        }
        
        jTextArea2.setText( outputText ) ;
        
        setInputNumbers( inputRanges.length , countAddresses( inputRanges ) ) ;
        
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
        
        //Debug
        java.time.Instant time1 = java.time.Instant.now() ;
        //
        
        //Reset outputs to blank at the start
        //to ensure that incorrect information
        //isn't reported, even if there is a crash
        setOutputNumbers( 0 , 0 ) ;
        jTextArea2.setText( "" ) ;
        
        int i , j ;
        String outputText = "" ;
        IPv4range[] inputRanges = parseStringRanges( splitStringRanges( jTextArea1.getText() ) ) ;
        IPv4range concatenatedRange = new IPv4range() ;
        IPv4range[] ipRangesOut ;
       
        inputRanges = sortRangeArray( inputRanges ) ;
        
        //Debug
        java.time.Instant time2 = java.time.Instant.now() ;
        //
        
        System.out.println( "Initial parsing " + (time2.toEpochMilli() - time1.toEpochMilli()) );
        
        setInputNumbers( inputRanges.length , countAddresses( inputRanges ) ) ;
        
        //Combine all input ranges into one range
        concatenatedRange.concatenateWithRange( inputRanges[0] , false ) ;
        for( i=1 ; i<inputRanges.length ; i++ ) {
            if( concatenatedRange.getAddressFromRange( 0 ).getIPAsNumber() > inputRanges[i].getAddressFromRange( inputRanges[i].getSizeOfRange()-1 ).getIPAsNumber() ) {
                concatenatedRange.concatenateWithRange( inputRanges[i] , true ) ;
            } else {
                concatenatedRange.concatenateWithRange( inputRanges[i] , false ) ;
            }
        }
        
        java.time.Instant time3 = java.time.Instant.now() ;
        System.out.println( "Create whole range " + (time3.toEpochMilli() - time2.toEpochMilli()) );
        
        //Sort this range
        concatenatedRange.sortRange() ;
        
        java.time.Instant time4 = java.time.Instant.now() ;
        System.out.println( "Sort range " + (time4.toEpochMilli() - time3.toEpochMilli()) );
        
        //Split into contiguous subranges
        ipRangesOut = concatenatedRange.getContiguousSubranges() ;
        
        java.time.Instant time5 = java.time.Instant.now() ;
        System.out.println( "Split ranges " + (time5.toEpochMilli() - time4.toEpochMilli()) );
        
        //Concat and remove in one step
        //Check for & concatenate adjacent ranges
        for( i=0 ; i<ipRangesOut.length ; i++ ) {
            j = i + 1 ;
            while( j < ipRangesOut.length ) {
                if( ipRangesOut[i].isAdjacentRange( ipRangesOut[j] ) == 1 ) {
                    ipRangesOut[i].concatenateWithRange( ipRangesOut[j] , false );
                    ipRangesOut = ipRangesOut[0].popFromIPv4rangeArray( ipRangesOut , j ) ;
                    j-- ;
                } else if ( ipRangesOut[i].isAdjacentRange( ipRangesOut[j] ) == -1 ) {
                    ipRangesOut[i].concatenateWithRange( ipRangesOut[j] , true );
                    ipRangesOut = ipRangesOut[0].popFromIPv4rangeArray( ipRangesOut , j ) ;
                    j-- ;
                }
                j++ ;
            }
        }
        
        java.time.Instant time6 = java.time.Instant.now() ;
        System.out.println( "Concat adjacent ranges " + (time6.toEpochMilli() - time5.toEpochMilli()) );
        
        //Remove overlapping IP addresses from ranges
        for( i=0 ; i<ipRangesOut.length ; i++ ) {
            for( j=i+1 ; j<ipRangesOut.length ; j++ ) {
                if( ipRangesOut[i].getSizeOfRange() >= ipRangesOut[j].getSizeOfRange() ) {
                    ipRangesOut[j].subtractRange( ipRangesOut[i] ) ;
                } else {
                    ipRangesOut[i].subtractRange( ipRangesOut[j] ) ;                
                }
            }
        }
        
        java.time.Instant time7 = java.time.Instant.now() ;
        System.out.println( "Remove overlap " + (time7.toEpochMilli() - time6.toEpochMilli()) );
        
        //Remove any ranges which have been emptied by the above operation
        i = 0 ;
        while( i<ipRangesOut.length ) {
            if( ipRangesOut[i].getSizeOfRange() == 0 ) {
                ipRangesOut = ipRangesOut[0].popFromIPv4rangeArray( ipRangesOut , i ) ;
                i-- ;
            }
            i++ ;
        }
        
        java.time.Instant time8 = java.time.Instant.now() ;
        System.out.println( "Remove empty ranges " + (time8.toEpochMilli() - time7.toEpochMilli()) );
        
        //Get the resulting ranges in human readable format
        //and write them to a string to output
        for( i=0 ; i<ipRangesOut.length ; i++ ) {
            outputText += ipRangesOut[i].convertRangeHumanReadable( ipRangesOut[i] ) + "\n" ;
        }
        
        //Write this to the lower area
        jTextArea2.setText( outputText ) ;

        setOutputNumbers( ipRangesOut.length , countAddresses( ipRangesOut ) ) ;
        
    }//GEN-LAST:event_jButton2ActionPerformed
    
    //About button
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        new AboutSquashIPRange( this , false ).setVisible(true) ;
    }//GEN-LAST:event_jButton5ActionPerformed

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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
