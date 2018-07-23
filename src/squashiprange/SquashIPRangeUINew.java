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
    
    private static java.awt.GridBagConstraints setUpConstraints( int column , int row , int fill ) {
        java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints() ;
        constraints.gridx = column ;
        constraints.gridy = row ;
        constraints.fill = fill ;
        return constraints ;
    }
    
    public static void main( String[] args ) {
        
        //The main container for the GUI
        javax.swing.JFrame mainFrame = new javax.swing.JFrame( "Squash IP Range" ) ;
        
        //Set the layout method for the frame
        mainFrame.setLayout( new java.awt.GridBagLayout() ) ;
        
        /*
         * Buttons
         * Here we'll set up the buttons for the application
         * The basic layout for each will be
         *      Constructor with name
         *      Location in grid
         *      Binding function to perform on action
         */
        
        //Reformat
        javax.swing.JButton reformatButton = new javax.swing.JButton( "Reformat" ) ;
        mainFrame.add( reformatButton , setUpConstraints( 1, 1, 
                                                          java.awt.GridBagConstraints.BOTH ) ) ;
        
        //Squash
        javax.swing.JButton squashButton = new javax.swing.JButton( "Squash" ) ;
        mainFrame.add( squashButton , setUpConstraints( 1, 2, 
                                                          java.awt.GridBagConstraints.BOTH ) ) ;
        
        //Find Overlap
        javax.swing.JButton overlapButton = new javax.swing.JButton( "Find Overlap" ) ;
        mainFrame.add( overlapButton , setUpConstraints( 1, 3, 
                                                          java.awt.GridBagConstraints.BOTH ) ) ;
        
        //Clear Input
        javax.swing.JButton clearInputButton = new javax.swing.JButton( "Clear Input" ) ;
        mainFrame.add( clearInputButton , setUpConstraints( 1, 4, 
                                                          java.awt.GridBagConstraints.BOTH ) ) ;
        
        //To Clipboard
        javax.swing.JButton clipboardButton = new javax.swing.JButton( "To Clipboard" ) ;
        mainFrame.add( clipboardButton , setUpConstraints( 2, 1, 
                                                          java.awt.GridBagConstraints.BOTH ) ) ;
        
        //Close
        javax.swing.JButton closeButton = new javax.swing.JButton( "Close" ) ;
        mainFrame.add( closeButton , setUpConstraints( 2, 4, 
                                                          java.awt.GridBagConstraints.BOTH ) ) ;
        
        //Help
        javax.swing.JButton aboutButton = new javax.swing.JButton( "About" ) ;
        mainFrame.add( aboutButton , setUpConstraints( 2, 5, 
                                                          java.awt.GridBagConstraints.BOTH ) ) ;
        
        //Exit on close
        mainFrame.setDefaultCloseOperation( javax.swing.JFrame.EXIT_ON_CLOSE ) ;
        
        //Pack the GUI around the contents
        mainFrame.pack() ;
        
        //Display the GUI
        mainFrame.setVisible( true ) ;
        
    }
    
}
