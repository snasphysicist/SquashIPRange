/**
 * ****************************************************************
 *Squash IP Range is offered under the lesser GPL licence version 3
 *Please refer to the LICENSE file included at the top level
 *of the git repo for full information on this licence
 *Written by snasphysicist (Scott N A Smith)
 ******************************************************************
 */
package squashiprange ;

/**
 *
 * @author scott
 */
public class SquashIPRangeUINew {
    
    /*
     * Declare all UI components
     */
    private javax.swing.JFrame mainFrame ;
    private javax.swing.JPanel inputPanel ;
    private javax.swing.JPanel outputPanel ;
    private java.awt.Container miscPanel ;
    private javax.swing.JButton reformatButton ;
    private javax.swing.JButton squashButton ;
    private javax.swing.JButton overlapButton ;
    private javax.swing.JButton clearInputButton ;
    private javax.swing.JButton clipboardButton ;
    private javax.swing.JButton aboutButton ;
    private javax.swing.JButton closeButton ;
    private javax.swing.JButton cancelButton ;
    private javax.swing.JTextArea inputTextArea ;
    private javax.swing.JTextArea outputTextArea ;
    private javax.swing.JRadioButton quickRadioButton ;
    private javax.swing.JRadioButton fullRadioButton ;
    private javax.swing.ButtonGroup groupRadioButtons = new javax.swing.ButtonGroup() ;
    private javax.swing.JLabel rangesInLabel ;
    private javax.swing.JLabel rangesOutLabel ;
    private javax.swing.JLabel addressesInLabel ;
    private javax.swing.JLabel addressesOutLabel ;
    
    /*
     * Set up constants
     */
    
    //Grid Bag Constrains constants
    private static final int GBBOTH = java.awt.GridBagConstraints.BOTH ;
    private static final int GBNONE = java.awt.GridBagConstraints.BOTH ;
    private static final int GBCENTER = java.awt.GridBagConstraints.CENTER ;
    private static final int GBRIGHT = java.awt.GridBagConstraints.EAST ;
    private static final int GBTOP = java.awt.GridBagConstraints.NORTH ;
    //textArea sizes (in arbitrary units)
    private static final int NARROWTEXTAREAWIDTH = 20 ;
    private static final int WIDETEXTAREAWIDTH = 40 ;
    private static final int TEXTAREAHEIGHT = 30 ;
    
    /*
     * Variables that are used for threaded running
     * Most need to be defined here since they
     * are accessed from inner classes
     */
    private IPv4range[] returnedRanges ;
    
    /*
     * This holds a reference to the currently
     * running thread, needs to be at class
     * level so cancel button method can
     * access the thread to kill it
     */
    private javax.swing.SwingWorker<Object,Object> workerThread ;
    
    /*
     * Methods fired when buttons are clicked
     */
    
    //Reformat button handler
    private void reformatOnClick() {
        
        /*
         * Reset outputs to blank at the start
         * to ensure that incorrect information
         * isn't reported, even if there is a crash
         */
        setOutputNumbers( 0 , 0 ) ;
        outputTextArea.setText( "" ) ;
        
        //Loop counter
        int i ;

        //We'll gather the output text here
        String outputText = "" ;
        
        //All we do is attempt to parse the ranges
        //written in the input box
        IPv4range[] inputRanges = SquashIPRange.parseStringRanges( 
                                    SquashIPRange.splitStringRanges( inputTextArea.getText() ) 
                                    ) ;
        
        //Then we convert these into strings and
        //write what we've found into the output box
        for( i=0 ; i<inputRanges.length ; i++ ) {
            outputText += inputRanges[i].convertRangeHumanReadable( inputRanges[i] ) + "\n\r" ;
        }
        
        setInputNumbers( inputRanges.length , SquashIPRange.countAddresses( inputRanges ) ) ;
        
        outputTextArea.setText( outputText ) ;
        
        setOutputNumbers( inputRanges.length , SquashIPRange.countAddresses( inputRanges ) ) ;
        
    }
    
    //Squash button handler
    private void squashOnClick() {
        
        /*
         * Reset outputs to blank at the start
         * to ensure that incorrect information
         * isn't reported, even if there is a crash
         */
        setOutputNumbers( 0 , 0 ) ;
        outputTextArea.setText( "" ) ;
        
        //Toggle cancel button on & other buttons off
        toggleUIRunningState() ;

        workerThread = new javax.swing.SwingWorker<Object, Object>() {
            
            //Squashing code to run in background
            public Object doInBackground() {

                System.out.println( "Starting" ) ;

                IPv4range[] inputRanges = SquashIPRange.parseStringRanges( 
                    SquashIPRange.splitStringRanges( inputTextArea.getText() ) 
                    ) ;

                System.out.println( "Operation 1" ) ;

                inputRanges = SquashIPRange.sortRangeArray( inputRanges ) ;

                System.out.println( "Operation 2" ) ;

                setInputNumbers( inputRanges.length , SquashIPRange.countAddresses( inputRanges ) ) ;

                System.out.println( "Operation 3" ) ;

                if( quickRadioButton.isSelected() ) {
                    returnedRanges = SquashIPRange.quickSquash( inputRanges ) ;
                } else {
                    returnedRanges = SquashIPRange.fullSquash( inputRanges ) ;
                }

                System.out.println( "Operation 4" ) ;

                return null ;

            } ;
                
            //After squashing code has run
            protected void done() {
                
                int i ;
                String outputText = "" ;
                
                //Get the resulting ranges in human readable format
                //and write them to a string to output
                for( i=0 ; i<returnedRanges.length ; i++ ) {
                    outputText += returnedRanges[i].convertRangeHumanReadable( returnedRanges[i] ) + "\n" ;
                }

                //Write this to the output area
                outputTextArea.setText( outputText ) ;
                
                //Set the numbers of ranges and addresses returned
                setOutputNumbers( returnedRanges.length , SquashIPRange.countAddresses( returnedRanges ) ) ;                

                //Reset the result, so it can't be used again by mistake
                returnedRanges = null ;
                
                //Finally, call method to toggle cancel off/other buttons back on
                toggleUIRunningState() ;
            }
                
        } ;
        
        workerThread.execute() ;
        
    }
    
    //Overlap button handler
    private void overlapOnClick() {
        
        /*
         * Reset outputs to blank at the start
         * to ensure that incorrect information
         * isn't reported, even if there is a crash
         */
        setOutputNumbers( 0 , 0 ) ;
        outputTextArea.setText( "" ) ;

        //Converts the raw input into an array of IPv4ranges
        IPv4range[] inputRanges = SquashIPRange.parseStringRanges( 
                                    SquashIPRange.splitStringRanges( inputTextArea.getText() ) 
                                    ) ;
        
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
        outputTextArea.setText( outputText ) ;
        
        //Display the number of IP address ranges and
        //the number of IP addresses in the input box
        setInputNumbers( inputRanges.length , SquashIPRange.countAddresses( inputRanges ) ) ;
        
    }
    
    //Clear input button handler
    private void clearInputOnClick() {
        inputTextArea.setText( "" ) ;
    }
    
    //To Clipboard button handler
    private void clipboardOnClick() {
        /* 
         * Simply send the text into the output box to the
         * system clipboard using the copyToClipboard method
         * from the SquashIPRange class
         */
        SquashIPRange.copyToClipboard( outputTextArea.getText() ) ;
    }
    
    //About button handler
    private void aboutOnClick() {
        new AboutSquashIPRangeUI( mainFrame , false ).setVisible(true) ;
    }
   
    //Close button handler
    private void closeOnClick() {
        System.exit(0) ;
    }
    
    /*
     * Helper functions that modify the appearance of the UI
     */
    
    /*
     * Set the display of the number of ranges and addresses
     * detected in the input field
     */
    private void setInputNumbers( Integer numberOfRanges , Integer numberOfAddresses ) {
        rangesInLabel.setText( "Ranges in: " + numberOfRanges.toString() ) ;
        addressesInLabel.setText( "Addresses in: " + numberOfAddresses.toString() ) ;
    }
    
    /*
     * Set the display of the number of ranges and addresses
     * entered into the output field
     */
    private void setOutputNumbers( Integer numberOfRanges , Integer numberOfAddresses ) {
        rangesOutLabel.setText( "Ranges out: " + numberOfRanges.toString() ) ;
        addressesOutLabel.setText( "Addresses out: " + numberOfAddresses.toString() ) ;
    }
    
    /*
     * Toggles buttons active/inactive depending upon
     * the swing worker is doing something in the background
     */
    private void toggleUIRunningState() {
        reformatButton.setEnabled( !reformatButton.isEnabled() ) ;
        squashButton.setEnabled( !squashButton.isEnabled() ) ;
        overlapButton.setEnabled( !overlapButton.isEnabled() ) ;
        clearInputButton.setEnabled( !clearInputButton.isEnabled() ) ;
        clipboardButton.setEnabled( !clipboardButton.isEnabled() ) ;
    }
    
    /*
     * Functions used in UI construction
     */
    
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
    
    /*
     * Note: the reason we use
     * main calling constructor calling setUpComponents
     * then this.setVisible as a wrapper for mainFrame.setVisible
     * instead of just putting all the setup code in main
     * is to avoid having to make everything static
     * 
     * Although I can't imagine why someone would have two
     * instances of this class at once, this approach 
     * at least anticipates and accounts for this
     */
    
    /*
     * Called from constructor to set up UI components
     */
    private void setUpComponents() {
        
        //The main container for the GUI
        mainFrame = new javax.swing.JFrame( "Squash IP Range" ) ;
        
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
        inputPanel = new javax.swing.JPanel( new java.awt.GridBagLayout() ) ;
        inputPanel.setBorder( titledBorder( "Input" ) );
        
        outputPanel = new javax.swing.JPanel( new java.awt.GridBagLayout() ) ;
        outputPanel.setBorder( titledBorder( "Output" ) ) ;
        
        //What is 0?
        miscPanel = new javax.swing.Box( 1 ) ;
        
        mainFrame.add( inputPanel , setUpConstraints( 0 , 0 , GBBOTH ) ) ;
        mainFrame.add( outputPanel , setUpConstraints( 1 , 0 , GBBOTH , 1 , 1 ) ) ;
        mainFrame.add( miscPanel , setUpConstraints( 2 , 0 , GBNONE , 1 , 1 , GBTOP ) ) ;
        
        /*
         * First, the Labels
         * Layout
         *      Constructor with default text
         *      Location in grid
         */
        
        //Number of ranges in, in input panel
        rangesInLabel = new javax.swing.JLabel( "Ranges in:" ) ;
        inputPanel.add( rangesInLabel , setUpConstraints( 0 , 0 ) ) ;
        
        //Number of addresses in, in input panel
        addressesInLabel = new javax.swing.JLabel( "Addresses in:" ) ;
        inputPanel.add( addressesInLabel , setUpConstraints( 0 , 1 ) ) ;
        
        //Number of ranges out, in output panel
        rangesOutLabel = new javax.swing.JLabel( "Ranges out:" ) ;
        outputPanel.add( rangesOutLabel , setUpConstraints( 0 , 0 ) ) ;
        
        //Number of addresses out, in output panel
        addressesOutLabel = new javax.swing.JLabel( "Addresses out:" ) ;
        outputPanel.add( addressesOutLabel , setUpConstraints( 0 , 1 ) ) ;
        
        /*
         * Buttons
         * Here we'll set up the buttons for the application
         * The basic layout for each will be
         *      Constructor with name
         *      Location in grid
         * Event handlers are added later
         */
        
        //Reformat, in input panel
        reformatButton = new javax.swing.JButton( "Reformat" ) ;
        inputPanel.add( reformatButton , setUpConstraints( 1, 2, GBBOTH ) ) ;
        
        //Squash, in input panel
        squashButton = new javax.swing.JButton( "Squash" ) ;
        inputPanel.add( squashButton , setUpConstraints( 1, 3, GBBOTH ) ) ;
        
        //Find Overlap, in input panel
        overlapButton = new javax.swing.JButton( "Find Overlap" ) ;
        inputPanel.add( overlapButton , setUpConstraints( 1, 4, GBBOTH ) ) ;
        
        //Clear Input, in input panel
        clearInputButton = new javax.swing.JButton( "Clear Input" ) ;
        inputPanel.add( clearInputButton , setUpConstraints( 1, 5, GBBOTH ) ) ;
        
        //To Clipboard, in output panel
        clipboardButton = new javax.swing.JButton( "To Clipboard" ) ;
        outputPanel.add( clipboardButton , setUpConstraints( 1, 2, GBBOTH ) ) ;
        
        //Cancel, in misc panel
        cancelButton = new javax.swing.JButton( "Cancel" ) ;
        miscPanel.add( cancelButton ) ;
        
        //About, in misc panel
        aboutButton = new javax.swing.JButton( "About" ) ;
        miscPanel.add( aboutButton ) ;
        
        //Close, in misc panel
        closeButton = new javax.swing.JButton( "Close" ) ;
        miscPanel.add( closeButton ) ;
        
        /*
         * Tie actions to buttons
         */
        
        //Reformat
        reformatButton.addActionListener( new java.awt.event.ActionListener() {
            public void actionPerformed( java.awt.event.ActionEvent ae ) {
                reformatOnClick() ;
            }
        } ) ;
        
        //Squash
        squashButton.addActionListener( new java.awt.event.ActionListener() {
            public void actionPerformed( java.awt.event.ActionEvent ae ) {
                squashOnClick() ;
            }
        } ) ;
        
        //Overlap
        overlapButton.addActionListener( new java.awt.event.ActionListener() {
            public void actionPerformed( java.awt.event.ActionEvent ae ) {
                overlapOnClick() ;
            }
        } ) ;
        
        //Clear Input
        clearInputButton.addActionListener( new java.awt.event.ActionListener() {
            public void actionPerformed( java.awt.event.ActionEvent ae ) {
                clearInputOnClick() ;
            }
        } ) ;
        
        //Clipboard
        clipboardButton.addActionListener( new java.awt.event.ActionListener() {
            public void actionPerformed( java.awt.event.ActionEvent ae ) {
                clipboardOnClick() ;
            }
        } ) ;
        
        //About
        aboutButton.addActionListener( new java.awt.event.ActionListener() {
            public void actionPerformed( java.awt.event.ActionEvent ae ) {
                aboutOnClick() ;
            }
        } ) ;
        
        //Close
        closeButton.addActionListener( new java.awt.event.ActionListener() {
            public void actionPerformed( java.awt.event.ActionEvent ae ) {
                closeOnClick() ;
            }
        } ) ;
        
        /*
         * Radio buttons
         * Format:
         *      Constructor, with name
         *      Location in grid
         *      Add to button group
         */
        
        //Quick, in input panel
        quickRadioButton = new javax.swing.JRadioButton( "Quick" ) ;
        inputPanel.add( quickRadioButton , setUpConstraints( 1 , 6 ) ) ;
        groupRadioButtons.add( quickRadioButton ) ;
        
        //Full, in input panel
        fullRadioButton = new javax.swing.JRadioButton( "Full" ) ;
        inputPanel.add( fullRadioButton , setUpConstraints( 1 , 7 ) ) ;
        groupRadioButtons.add( fullRadioButton ) ;
        
        //Quick is selected by default
        quickRadioButton.setSelected( true ) ;
        
        /*
         * Input and output text areas
         */
        
        //Input, in input panel
        inputTextArea = new javax.swing.JTextArea( TEXTAREAHEIGHT, 
                                                   NARROWTEXTAREAWIDTH ) ;
        inputPanel.add( inputTextArea, setUpConstraints( 0 , 2 , GBBOTH , 1 , 7 ) ) ;
        inputTextArea.setLineWrap( true ) ;
        
        //Output, in output panel
        outputTextArea = new javax.swing.JTextArea( TEXTAREAHEIGHT, 
                                                    WIDETEXTAREAWIDTH ) ;
        outputPanel.add( outputTextArea, setUpConstraints( 0 , 2 , GBBOTH , 1 , 7 ) ) ;
        outputTextArea.setLineWrap( true ) ;
        
        //Exit on close
        mainFrame.setDefaultCloseOperation( javax.swing.JFrame.EXIT_ON_CLOSE ) ;
        
        //Pack the GUI around the contents
        mainFrame.pack() ;
        
    }
    
    /*
     * Check available looks and feels
     * Select preferred if possible
     * Otherwise, fall back to a default
     */
    private void setLookAndFeel() {
        
        int i , j ;
        //Get list of looks and feels
        javax.swing.UIManager.LookAndFeelInfo[] allLooksAndFeels =
                javax.swing.UIManager.getInstalledLookAndFeels() ;
        //Preferred look and feel names, in order of preference
        String[] preferred = { "Nimbus" , "Metal" } ;
        //Location of these looks and feels in looks and feels array
        int[] indices = { -1 , -1 } ;
        
        /*
         * Go through each look and feel available
         * and check if the name matches each of
         * the names in the preferred list
         * Record the index when a match is found
         */
        for( i=0 ; i<allLooksAndFeels.length ; i++ ) {
            for( j=0 ; j<preferred.length ; j++ ) {
                if( preferred[j].equals( allLooksAndFeels[i].getName() ) ) {
                    indices[j] = i ;
                }
            }
        }
        
        /*
         * For each preferred look and feel
         * check whether an index has been found
         * If so, apply the index then exit the loop
         */
        for( i=0 ; i<preferred.length ; i++ ) { //1
            if( indices[i] > -1 ) { //2
                try {
                    javax.swing.UIManager.setLookAndFeel( 
                            allLooksAndFeels[ indices[i] ].getClassName() 
                            ) ;
                    break ;
                } catch ( ClassNotFoundException | 
                            InstantiationException | 
                            IllegalAccessException | 
                            javax.swing.UnsupportedLookAndFeelException e ) { //3
                   //Just print the exception and move on to the next class
                   e.printStackTrace() ; 
                } //3
            } //2
        } //1
        
    }
    
    /*
     * Set visible method
     * Wrapper for JFrame's set visible method
     */
    public void setVisible( boolean visible ) {
        mainFrame.setVisible( visible ) ;
    }
    
    /*
     * Class constructor
     * Just calls the component setup method above
     */
    public SquashIPRangeUINew() {
        setLookAndFeel() ;
        setUpComponents() ;
    }
    
}
