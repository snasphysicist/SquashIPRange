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
    
    //Grid Bag Constrains constants
    private static final int GBBOTH = java.awt.GridBagConstraints.BOTH ;
    private static final int GBNONE = java.awt.GridBagConstraints.BOTH ;
    private static final int GBCENTER = java.awt.GridBagConstraints.CENTER ;
    private static final int GBRIGHT = java.awt.GridBagConstraints.EAST ;
    //textArea sizes (in arbitrary units)
    private static final int TEXTAREAWIDTH = 20 ;
    private static final int TEXTAREAHEIGHT = 30 ;
    
    //Generate titled border objects
    private static javax.swing.border.Border titledBorder( String title ) {
        javax.swing.border.Border theBorder = javax.swing.BorderFactory.createEtchedBorder() ;
        return javax.swing.BorderFactory.createTitledBorder( theBorder , title ) ;
    }
    
    //Most general setUpConstraints method
    private static java.awt.GridBagConstraints setUpConstraints( int column , int row , 
                                                                 int fill , int columns ,
                                                                 int rows , int anchor ) {
        java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints() ;
        constraints.gridx = column ;
        constraints.gridy = row ;
        constraints.fill = fill ;
        constraints.gridwidth = columns ;
        constraints.gridheight = rows ;
        constraints.anchor = anchor ;
        return constraints ;
    }
    
    /*
     * Second most general setUpConstraints method
     * Defaults:
     *      anchor = CENTER -> centered in the space alloted
     */
    private static java.awt.GridBagConstraints setUpConstraints( int column , int row , 
                                                                 int fill , int columns ,
                                                                 int rows ) {
        return setUpConstraints( column , row , fill , columns , rows , GBCENTER ) ;
    }
    
    /*
     * Second least general setUpConstraints method
     * Defaults:
     *      gridwith = 1 -> takes up one column
     *      gridheight = 1 -> takes up one row
     */
    private static java.awt.GridBagConstraints setUpConstraints( int column , int row , int fill ) {
        return setUpConstraints( column , row , fill , 1 , 1 , GBCENTER ) ;
    }
    /*
     * Least general setUpConstraints method
     * Defaults:
     *      fill = NONE -> won't expand
     *      gridwidth = 1 -> takes up one column
     *      gridheight = 1 -> takes up one row
     */
    private static java.awt.GridBagConstraints setUpConstraints( int column , int row ) {
        return setUpConstraints( column , row , GBNONE , 1 , 1 , GBCENTER ) ;
    }
    
    public static void main( String[] args ) {
        
        //The main container for the GUI
        javax.swing.JFrame mainFrame = new javax.swing.JFrame( "Squash IP Range" ) ;
        
        //Set the layout method for the frame
        mainFrame.setLayout( new java.awt.GridBagLayout() ) ;
        
        /*
         * The application will be split
         * into three JPanels:
         *   Input
         *   Output
         *   About/Exit
         * Each will have a titled border
         */
        javax.swing.JPanel inputPanel = new javax.swing.JPanel( new java.awt.GridBagLayout() ) ;
        inputPanel.setBorder( titledBorder( "Input" ) );
        
        javax.swing.JPanel outputPanel = new javax.swing.JPanel( new java.awt.GridBagLayout() ) ;
        outputPanel.setBorder( titledBorder( "Output" ) ) ;
        
        javax.swing.JPanel miscPanel = new javax.swing.JPanel( new java.awt.GridBagLayout() ) ;
        
        mainFrame.add( inputPanel , setUpConstraints( 0 , 0 , GBBOTH ) ) ;
        mainFrame.add( outputPanel , setUpConstraints( 1 , 0 , GBBOTH , 1 , 1 ) ) ;
        mainFrame.add( miscPanel , setUpConstraints( 2 , 1 , GBBOTH , 1 , 1 , GBRIGHT ) ) ;
        
        /*
         * Buttons
         * Here we'll set up the buttons for the application
         * The basic layout for each will be
         *      Constructor with name
         *      Location in grid
         *      Binding function to perform on action
         */
        
        //Reformat, in input panel
        javax.swing.JButton reformatButton = new javax.swing.JButton( "Reformat" ) ;
        inputPanel.add( reformatButton , setUpConstraints( 1, 0, GBBOTH ) ) ;
        
        //Squash, in input panel
        javax.swing.JButton squashButton = new javax.swing.JButton( "Squash" ) ;
        inputPanel.add( squashButton , setUpConstraints( 1, 1, GBBOTH ) ) ;
        
        //Find Overlap, in input panel
        javax.swing.JButton overlapButton = new javax.swing.JButton( "Find Overlap" ) ;
        inputPanel.add( overlapButton , setUpConstraints( 1, 2, GBBOTH ) ) ;
        
        //Clear Input, in input panel
        javax.swing.JButton clearInputButton = new javax.swing.JButton( "Clear Input" ) ;
        inputPanel.add( clearInputButton , setUpConstraints( 1, 3, GBBOTH ) ) ;
        
        //To Clipboard, in output panel
        javax.swing.JButton clipboardButton = new javax.swing.JButton( "To Clipboard" ) ;
        outputPanel.add( clipboardButton , setUpConstraints( 1, 0, GBBOTH ) ) ;
        
        //Help, in misc panel
        javax.swing.JButton aboutButton = new javax.swing.JButton( "About" ) ;
        miscPanel.add( aboutButton , setUpConstraints( 0, 0, GBBOTH, 1, 1, GBRIGHT )  ) ;
         
       
        //Close, in misc panel
        javax.swing.JButton closeButton = new javax.swing.JButton( "Close" ) ;
        miscPanel.add( closeButton , setUpConstraints( 1, 0, GBBOTH, 1, 1, GBRIGHT ) ) ;
        
        /*
         * Input and output text areas
         */
        
        //Input, in input panel
        javax.swing.JTextArea inputTextArea = new javax.swing.JTextArea( TEXTAREAHEIGHT, 
                                                                         TEXTAREAWIDTH ) ;
        inputPanel.add( inputTextArea, setUpConstraints( 0 , 0 , GBBOTH , 1 , 6 ) ) ;
        inputTextArea.setLineWrap( true ) ;
        
        //Output, in output panel
        javax.swing.JTextArea outputTextArea = new javax.swing.JTextArea( TEXTAREAHEIGHT, 
                                                                          TEXTAREAWIDTH ) ;
        outputPanel.add( outputTextArea, setUpConstraints( 0 , 0 , GBBOTH , 1 , 6 ) ) ;
        outputTextArea.setLineWrap( true ) ;
        
        //Exit on close
        mainFrame.setDefaultCloseOperation( javax.swing.JFrame.EXIT_ON_CLOSE ) ;
        
        //Pack the GUI around the contents
        mainFrame.pack() ;
        
        //Display the GUI
        mainFrame.setVisible( true ) ;
        
    }
    
}
