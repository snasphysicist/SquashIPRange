/**
 * ****************************************************************
 *Squash IP Range is offered under the lesser GPL licence version 3
 *Please refer to the LICENSE file included at the top level
 *of the git repo for full information on this licence
 *Written by snasphysicist (Scott N A Smith)
 ******************************************************************
 */
package squashiprange;

/**
 *
 * @author scott
 */
public class SquashIPRangeUINew {
    
    private static java.awt.GridBagConstraints setupConstraints( int row , int column ) {
        java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints() ;
        constraints.gridx = row ;
        constraints.gridy = column ;
        return constraints ;
    }
    
    public static void main( String[] args ) {
        
        //The main container for the GUI
        javax.swing.JFrame mainFrame = new javax.swing.JFrame( "Squash IP Range" ) ;
        
        //Set the layout method for the frame
        mainFrame.setLayout( new java.awt.GridBagLayout() ) ;
        
        //Buttons
        
        
        //Exit on close
        mainFrame.setDefaultCloseOperation( javax.swing.JFrame.EXIT_ON_CLOSE ) ;
        
        //Display the GUI
        mainFrame.setVisible( true ) ;
        
    }
    
}
