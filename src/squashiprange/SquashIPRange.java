/******************************************************************
*Squash IP Range is offered under the lesser GPL licence version 3
*Please refer to the LICENSE file included at the top level
*of the git repo for full information on this licence
*Written by snasphysicist (Scott N A Smith)
*******************************************************************/
package squashiprange;

/**
 *
 * A tool to take a set of ip addresses and ip ranges 
 * and squash them down into a smaller set of ranges
 * if possible
 * 
 * @author snasphysicist
 */
public class SquashIPRange {
    
    //Need to find a better way to share this between this class & IPv4range class...
    //Takes an array of IPv4address objects and another IPv4address 
    //and appends the latter at the end of the former and returns the resulting array
    public static IPv4range[] appendToIPv4rangeArray( IPv4range[] inarray , IPv4range inobject ) {
        int i ;
        IPv4range[] intmarray = new IPv4range[inarray.length+1] ;
        for(i=0;i<inarray.length;i++) {
            intmarray[i] = inarray[i] ;
        }
        intmarray[inarray.length] = inobject ;
        return intmarray ;
    }
    
    public static IPv4range[] swapRanges( IPv4range[] toSwap , int index1 , int index2 ) {
        
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
    
    public static IPv4range[] sortRangeArray( IPv4range[] toSort ) {
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
        
        int i , j ;  
        boolean isAllowed ;
        
        //Accepted delimiters between IP address ranges
        //which will be replaced by commas
        String[] delimiters = new String[]{"\\|",";","\n","\r"} ;
        
        //Possible characters that look like dashes (in unicode)
        //These will be replaced by dashes
        String[] allDashes = new String[]{"~","\u002D","\u005F","\u007E","\u00AD",
                                            "\u00AF","\u02C9","\u02CD","\u02D7","\u02DC",
                                            "\u2010","\u2011","\u2012","\u203E","\u2043",
                                            "\u207B","\u208B","\u2212","\u223C","\u23AF",
                                            "\u23E4","\u2500","\u2796","\u2E3A","\u2E3B" } ;
        
        //After the above transformations, we'll only allow
        //numbers and full stops (characters for IP addresses)
        //commas (chosen delimiters)
        //dashes, stars, forward slashes (part of IP range formats)
        String [] allowedChars = new String[]{"1","2","3","4","5",
                                              "6","7","8","9","0",
                                              ",",".","/","*","-" } ;
        
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
        
        //Finally, remove any character left
        //that is not explicitly allowed
        i = 0 ;
        while ( i < ranges.length() ) {
            isAllowed = false ;
            for( j=0 ; j<allowedChars.length ; j++ ) {
                //Here we extract the current ranges character and
                //compare it to the current allowed character
                //as single characters, not as Strings
                //Hence the .charAt(0) on the allowed Chars
                //it's a lazy type conversion ;)
                if( ranges.charAt(i) == allowedChars[j].charAt(0) ) {
                    isAllowed = true ;
                }
            }
            //If it hasn't been found in the list of allowed
            //characters, then we get rid of it
            //We also need to decrement i by one,
            //because the string has been shortened by
            //one character
            if( !isAllowed ) {
                ranges = removeCharFromString( ranges , i ) ;
                i-- ;
            }
            i++ ;
        }
        
        //Then split by comma (representing all delimiters)
        separatedRanges = ranges.split( "," ) ;
        
        return separatedRanges ;
    }
    
    //Removes the character from a string at the index
    //specified in the input
    public static String removeCharFromString( String inString , int charIndex ) {
        return inString.substring(0, charIndex) + inString.substring(charIndex+1) ;
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
    
    //Copy to clipboard method to put some input text
    //on to the system clipboard
    public static void copyToClipboard( String textToClipboard ) {
        java.awt.datatransfer.StringSelection stringSelection = new java.awt.datatransfer.StringSelection( textToClipboard );
        java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
    
    //Combine all ranges from an input array of ranges into one range
    public static IPv4range concatenateManyRanges( IPv4range[] inRanges ) {
        int i ;
        IPv4range intmRange = new IPv4range() ;
                
        intmRange.concatenateWithRange( inRanges[0] , false ) ;
        for( i=1 ; i<inRanges.length ; i++ ) {
            if( intmRange.getAddressFromRange( 0 ).getIPAsNumber() > inRanges[i].getAddressFromRange( inRanges[i].getSizeOfRange()-1 ).getIPAsNumber() ) {
                intmRange.concatenateWithRange( inRanges[i] , true ) ;
            } else {
                intmRange.concatenateWithRange( inRanges[i] , false ) ;
            }
        }
        return intmRange ;
    }
    
    //This method takes an array of ranges as input
    //In that set of ranges it finds all pairs of ranges 
    //which are adjacent and for each pair it merges
    //the ranges
    public static void mergeAdjacentRanges( IPv4range[] inRanges ) {
        int i , j ;
        for( i=0 ; i<inRanges.length ; i++ ) {
            j = i + 1 ;
            while( j < inRanges.length ) {
                //Return value of 1 implies that the range indexed
                //by j has higher numbers than that indexed by i,
                //so the latter's addresses are inserted at
                //the end of the former.
                if( inRanges[i].isAdjacentRange( inRanges[j] ) == 1 ) {
                    inRanges[i].concatenateWithRange( inRanges[j] , false );
                    inRanges = inRanges[0].popFromIPv4rangeArray( inRanges , j ) ;
                    j-- ;
                //Alternatively a return value of -1 means the opposite
                //Range j's numbers lower than i's
                //Range j's addresses inserted before of range i's
                } else if ( inRanges[i].isAdjacentRange( inRanges[j] ) == -1 ) {
                    inRanges[i].concatenateWithRange( inRanges[j] , true );
                    inRanges = inRanges[0].popFromIPv4rangeArray( inRanges , j ) ;
                    j-- ;
                }
                j++ ;
            }
        }
    }
    
    /**
     * @param args none
     */
    public static void main(String[] args) {
        
        //An IPv4address object so we can use the
        //splitBySector method
        IPv4address splitTool = new IPv4address(0L) ;
        
        //This is where we'll put the sectors as strings
        String[] sectors ;
        
        //Output minimal set of address ranges
        //in human readable format
        String[] stringipRangesOut ;
        
        //Used as an intermediate range
        //when initialising ranges
        IPv4range intmRange ;
        
        //A single range to hold all addresses added
        IPv4range allAddresses = new IPv4range() ;
        
        //Used for parsing a.b.c-d.e-f notation
        Integer sector3min ;
        Integer sector3max ;
        
        //Loop counters
        Integer i, j, k ;
        
        //IPv4range[] overlappingAddresses = new IPv4range[ (numberOfRanges*(numberOfRanges-1))/2 ] ;
        
        //Input human readable IP ranges
        String[] stringipRanges = new String[]{"10.13.17.100-105","10.13.17.98-101"} ;
        
        //Number of input address ranges
        Integer numberOfRanges = stringipRanges.length ;
        
        //All input ranges converted to IPv4range objects
        IPv4range[] allRanges = new IPv4range[0] ;
        
        //Track if an IPv4range is created successfully
        boolean parsedRange ;

        //Taking the string ranges
        //and converting them to IPv4ranges
        //In a robust, error handled way
        for( j=0 ; j<stringipRanges.length ; j++ ) {
            //System.out.println( new Integer(j).toString() );
            try {
                parsedRange = false ;
                intmRange = new IPv4range() ;
                stringipRanges[j] = stringipRanges[j].replaceAll("\\*", "0-255") ;
                sectors = splitTool.splitBySector( stringipRanges[j] ) ;
                if ( sectors[3].contains( "/" ) ) {
                    parsedRange = intmRange.parseAddSlashNotation( stringipRanges[j] ) ;
                } else if ( sectors[2].contains( "-" ) ) {
                    sector3min = new Integer( sectors[2].split( "-" )[0] ) ;
                    sector3max = new Integer( sectors[2].split( "-" )[1] ) ;
                    parsedRange = true ;
                    for(i=sector3min;i<=sector3max;i++) {
                        parsedRange = parsedRange && intmRange.parseAddDashNotation( sectors[0] + "." + sectors[1] + "." + i.toString() + "." + sectors[3] ) ;
                    }
                } else {
                    parsedRange = intmRange.parseAddDashNotation( stringipRanges[j] ) ;
                }
                if( parsedRange ) {
                    allRanges = appendToIPv4rangeArray( allRanges , intmRange ) ;
                }
            } 
            catch ( Exception e ) {
                System.out.println( "Failed to parse range" ) ;
                e.printStackTrace() ;
            }
        }
        
        /*
        k = 0 ;
        for( i=0 ; i<numberOfRanges ; i++ ) {
            for( j=i+1 ; j<numberOfRanges;j++ ) {
                overlappingAddresses[k] = allRanges[i].findOverlap(allRanges[j]) ;
                k++ ;
            }
        }
        */
        
        //Remove overlapping parts of ranges
        for( i=0 ; i<numberOfRanges ; i++ ) {
            for( j=i+1 ; j<numberOfRanges;j++ ) {
                if( allRanges[i].getSizeOfRange() < allRanges[j].getSizeOfRange() ) {
                    allRanges[i].subtractRange( allRanges[j] );
                } else {
                    allRanges[j].subtractRange( allRanges[i] );
                }
            }
        }
        
        for( i=0 ; i<numberOfRanges ; i++ ) {
            allAddresses.concatenateWithRange( allRanges[i] ,  false );
        }
        /*
        for( i=0 ; i<allAddresses.getSizeOfRange() ; i++ ) {
            System.out.println( allAddresses.getAddressFromRange(i).getIPAsString() ) ;
        }
        
        stringipRangesOut = allAddresses.getWholeRangeHumanReadable() ;
        
        for( i=0 ; i<stringipRangesOut.length ; i++ ) {
            System.out.println( stringipRangesOut[i] ) ;
        }
        */
        
    } // Closing main
    
} // Closing class