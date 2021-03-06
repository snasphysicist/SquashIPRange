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
     * Note: when adding new buttons
     * check whether they need to be added
     * to the lists in: 
     *      toggleUIRunningState
     *      normaliseButtonSizes
     */
    private javax.swing.JFrame mainFrame ;
    private javax.swing.JPanel inputPanel ;
    private javax.swing.JPanel outputPanel ;
    private javax.swing.JPanel miscPanel ;
    private javax.swing.JButton reformatButton ;
    private javax.swing.JButton squashButton ;
    private javax.swing.JButton overlapButton ;
    private javax.swing.JButton listButton ;
    private javax.swing.JButton clearInputButton ;
    private javax.swing.JButton clipboardButton ;
    private javax.swing.JButton aboutButton ;
    private javax.swing.JButton closeButton ;
    private javax.swing.JButton cancelButton ;
    private javax.swing.JTextArea inputTextArea ;
    private javax.swing.JTextArea outputTextArea ;
    private javax.swing.JScrollPane inputScrollPane ;
    private javax.swing.JScrollPane outputScrollPane ;
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
    private static final int GBNONE = java.awt.GridBagConstraints.NONE ;
    private static final int GBLEFT = java.awt.GridBagConstraints.LINE_START ;
    private static final int GBBOTTOM = java.awt.GridBagConstraints.PAGE_END ;
    //textArea sizes (in arbitrary units)
    private static final int NARROWTEXTAREAWIDTH = 20 ;
    private static final int WIDETEXTAREAWIDTH = 40 ;
    private static final int TEXTAREAHEIGHT = 30 ;
    //Button sizes
    private static final int BUTTONWIDTH = 130 ;
    private static final int BUTTONHEIGHT = 30 ;
    
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
    private javax.swing.SwingWorker workerThread ;
    
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

                IPv4range[] inputRanges = SquashIPRange.parseStringRanges( 
                    SquashIPRange.splitStringRanges( inputTextArea.getText() ) 
                    ) ;

                inputRanges = SquashIPRange.sortRangeArray( inputRanges ) ;

                setInputNumbers( inputRanges.length , SquashIPRange.countAddresses( inputRanges ) ) ;

                if( quickRadioButton.isSelected() ) {
                    returnedRanges = SquashIPRange.quickSquash( inputRanges ) ;
                } else {
                    returnedRanges = SquashIPRange.fullSquash( inputRanges ) ;
                }

                return null ;

            } ;
                
            //After squashing code has run
            protected void done() {
                if( !this.isCancelled() ) {
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
                }
                
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
    
    //List addresses button handler
    private void listAddressesOnClick() {
        
        /*
         * Reset outputs to blank at the start
         * to ensure that incorrect information
         * isn't reported, even if there is a crash
         */
        setOutputNumbers( 0 , 0 ) ;
        outputTextArea.setText( "" ) ;
        
        //Toggle cancel button on & other buttons off
        toggleUIRunningState() ;

        workerThread = new javax.swing.SwingWorker<String, Object>() {
            
            /*
             * Code to run in background
             * Squash input and generate list
             */
            public String doInBackground() {
                
                IPv4range[] inputRanges = SquashIPRange.parseStringRanges( 
                    SquashIPRange.splitStringRanges( inputTextArea.getText() ) 
                    ) ;

                inputRanges = SquashIPRange.sortRangeArray( inputRanges ) ;

                setInputNumbers( inputRanges.length , SquashIPRange.countAddresses( inputRanges ) ) ;

                if( quickRadioButton.isSelected() ) {
                    returnedRanges = SquashIPRange.quickSquash( inputRanges ) ;
                } else {
                    returnedRanges = SquashIPRange.fullSquash( inputRanges ) ;
                }

                return SquashIPRange.getAllAddressesAllRangesAsString( 
                        returnedRanges , 
                        "," 
                        ) ;
            }
            
            //Update UI when squashing & listing done
            protected void done() {
                
                if( !this.isCancelled() ) {

                    //Write this to the output area
                    try {
                        outputTextArea.setText( this.get() ) ;
                    }
                    catch( java.util.concurrent.ExecutionException | InterruptedException e ) {
                        outputTextArea.setText( "Operation cancelled" ) ;
                    }

                    //Reset the result, so it can't be used again by mistake
                    returnedRanges = null ;
                }
                
                //Finally, call method to toggle cancel off/other buttons back on
                toggleUIRunningState() ;
                
            }
            
        } ;
        
        workerThread.execute() ;
        
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
    
    //Cancel button handler
    private void cancelOnClick() {
        workerThread.cancel( true ) ;
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
     * Toggles buttons active/inactive depending upon whether
     * the swing worker is doing something in the background
     */
    private void toggleUIRunningState() {
        javax.swing.JButton[] buttons = { reformatButton ,
                                          squashButton ,
                                          overlapButton ,
                                          listButton ,
                                          clearInputButton ,
                                          clipboardButton ,
                                          cancelButton } ;
        for( javax.swing.JButton button : buttons ) {
            button.setEnabled( !button.isEnabled() ) ;
        }
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
        return setUpConstraints( column , row , fill , columns , rows , GBLEFT ) ;
    }
    
    /*
     * Second least general setUpConstraints method
     * Defaults:
     *      gridwith = 1 -> takes up one column
     *      gridheight = 1 -> takes up one row
     */
    private static java.awt.GridBagConstraints setUpConstraints( int column , int row , int fill ) {
        return setUpConstraints( column , row , fill , 1 , 1 , GBLEFT ) ;
    }
    /*
     * Least general setUpConstraints method
     * Defaults:
     *      fill = NONE -> won't expand
     *      gridwidth = 1 -> takes up one column
     *      gridheight = 1 -> takes up one row
     */
    private static java.awt.GridBagConstraints setUpConstraints( int column , int row ) {
        return setUpConstraints( column , row , GBNONE , 1 , 1 , GBLEFT ) ;
    }
    
    /*
     * Used to make all buttons equal in size
     */
    private void normaliseButtonSizes() {
        javax.swing.JButton[] 
                buttons = {
                            reformatButton ,
                            squashButton ,
                            overlapButton ,
                            listButton ,
                            clearInputButton ,
                            clipboardButton ,
                            cancelButton ,
                            aboutButton ,
                            closeButton
                } ;
        
        for( javax.swing.JButton button : buttons ) {
            button.setPreferredSize( new java.awt.Dimension( BUTTONWIDTH , BUTTONHEIGHT ) ) ;
            button.revalidate() ;
        }
                
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
        
        miscPanel = new javax.swing.JPanel( new java.awt.GridBagLayout() ) ;
        
        mainFrame.add( inputPanel , setUpConstraints( 0 , 0 , GBBOTH ) ) ;
        mainFrame.add( outputPanel , setUpConstraints( 1 , 0 , GBBOTH , 1 , 1 ) ) ;
        mainFrame.add( miscPanel , setUpConstraints( 2 , 0 , GBNONE , 2 , 1 , GBBOTTOM ) ) ;
        
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
        inputPanel.add( overlapButton , setUpConstraints( 1 , 4 , GBBOTH ) ) ;
        
        //List Addresses, in input panel
        listButton = new javax.swing.JButton( "List Addresses" ) ;
        inputPanel.add( listButton , setUpConstraints( 1 , 5 , GBBOTH ) ) ;
        
        //Clear Input, in input panel
        clearInputButton = new javax.swing.JButton( "Clear Input" ) ;
        inputPanel.add( clearInputButton , setUpConstraints( 1 , 6 , GBBOTH ) ) ;
        
        //To Clipboard, in output panel
        clipboardButton = new javax.swing.JButton( "To Clipboard" ) ;
        outputPanel.add( clipboardButton , setUpConstraints( 1 , 2 , GBBOTH ) ) ;
        
        //Cancel, in misc panel
        cancelButton = new javax.swing.JButton( "Cancel" ) ;
        miscPanel.add( cancelButton , setUpConstraints( 0 , 0 , GBBOTH ) ) ;
        //Disabled by default
        cancelButton.setEnabled( false ) ;
        
        //About, in misc panel
        aboutButton = new javax.swing.JButton( "About" ) ;
        miscPanel.add( aboutButton , setUpConstraints( 0 , 1 , GBBOTH ) ) ;
        
        //Close, in misc panel
        closeButton = new javax.swing.JButton( "Close" ) ;
        miscPanel.add( closeButton , setUpConstraints( 0 , 2 , GBBOTH ) ) ;
        
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
        
        listButton.addActionListener( new java.awt.event.ActionListener() {
            public void actionPerformed( java.awt.event.ActionEvent ae ) {
                listAddressesOnClick() ;
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
        
        //Cancel
        cancelButton.addActionListener( new java.awt.event.ActionListener() {
            public void actionPerformed( java.awt.event.ActionEvent ae ) {
                cancelOnClick() ;
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
        inputPanel.add( quickRadioButton , setUpConstraints( 1 , 7 ) ) ;
        groupRadioButtons.add( quickRadioButton ) ;
        
        //Full, in input panel
        fullRadioButton = new javax.swing.JRadioButton( "Full" ) ;
        inputPanel.add( fullRadioButton , setUpConstraints( 1 , 8 ) ) ;
        groupRadioButtons.add( fullRadioButton ) ;
        
        //Quick is selected by default
        quickRadioButton.setSelected( true ) ;
        
        /*
         * Input and output text areas
         * Wrapped in scroll panes
         */
        
        //Input, in input panel
        inputTextArea = new javax.swing.JTextArea( TEXTAREAHEIGHT, 
                                                   NARROWTEXTAREAWIDTH ) ;
        inputTextArea.setLineWrap( true ) ;
        inputScrollPane = new javax.swing.JScrollPane() ;
        inputScrollPane.setViewportView( inputTextArea ) ;
        inputPanel.add( inputScrollPane , setUpConstraints( 0 , 2 , GBBOTH , 1 , 8 ) ) ;
        
        //Output, in output panel
        outputTextArea = new javax.swing.JTextArea( TEXTAREAHEIGHT, 
                                                    WIDETEXTAREAWIDTH ) ;
        outputTextArea.setLineWrap( true ) ;
        outputTextArea.setEditable( false ) ;
        outputScrollPane = new javax.swing.JScrollPane() ;
        outputScrollPane.setViewportView( outputTextArea ) ;
        outputPanel.add( outputScrollPane , setUpConstraints( 0 , 2 , GBBOTH , 1 , 7 ) ) ;
        
        /*
         * Final, general setup steps
         */
        
        //Exit on close
        mainFrame.setDefaultCloseOperation( javax.swing.JFrame.EXIT_ON_CLOSE ) ;
        
        //Normalise button sizes
        normaliseButtonSizes() ;
        
        //Pack the GUI around the contents
        mainFrame.pack() ;
        
        //Set minimum size for textAreas
        inputScrollPane.setPreferredSize( inputTextArea.getSize() ) ;
        outputScrollPane.setPreferredSize( outputTextArea.getSize() ) ;
        
        //Prevent the window from being resized
        mainFrame.setResizable( false ) ;
        
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