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
    
    /*
     * Determines if a string matches the format
     * for "compressed star" notation, a.b.*
     */
    public static boolean matchesCompressedStar( String range ) {
        /*
         * regex for octet optional 0-2 optional 0-9 required 0-9
         * -> [012]?\d?\d
         * regex for full range format
         * |a        |.|b         |.|*|
         * [012]?\d?\d\.[012]?\d?\d\.\*
         */
        return java.util.regex.Pattern.matches( "[012]?\\d?\\d\\.[012]?\\d?\\d\\.\\*" , range ) ;
    }
    
    public static IPv4range[] parseStringRanges( String[] ranges ) {
        
        Integer i,j ;
        boolean parsedRange ;
        IPv4range intmRange ;
        IPv4range[] intmRanges ;
        String[] sectors ;
        IPv4address splitTool = new IPv4address(0L) ;
        int sector3min , sector3max ;
        IPv4range[] allRanges = new IPv4range[0] ;

        /*
         * Taking the string ranges
         * and converting them to IPv4ranges
         * In a robust, error handled way
        */
        for( i=0 ; i<ranges.length ; i++ ) {
            try {
                intmRange = new IPv4range() ;
                /*
                 * If the range is in the format
                 * a.b.*, convert it to a.b.*.*

                */
                if( matchesCompressedStar( ranges[i] ) ) {
                    ranges[i] = ranges[i] + ".*" ; 
                }
                ranges[i] = ranges[i].replaceAll("\\*", "0-255") ;
                if( substringOccurrences( ranges[i] , "." ) > 3 ) {
                    intmRanges = longToShortDash( ranges[i] ) ;
                    for( j=0 ; j<intmRanges.length ; j++ ) {
                        allRanges = SquashIPRange.appendToIPv4rangeArray( allRanges , intmRanges[j] ) ;
                    }
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
                    if( parsedRange ) {
                        allRanges = appendToIPv4rangeArray( allRanges , intmRange ) ;
                    }
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
    
    //Old, slower, more "thorough" squash method
    public static IPv4range[] fullSquash( IPv4range[] ipRangesToSquash ) {
        
        IPv4range concatenatedRange ;
        IPv4range[] ipRangesOut ;
        
        //Take all the address from all the input ranges
        //and put them into a single large range
        concatenatedRange = concatenateManyRanges( ipRangesToSquash ) ;
        
        //Sort the IP addresses in the range
        concatenatedRange.sortRange() ;
        
        //Split this back into contiguous sub ranges
        ipRangesOut = concatenatedRange.getContiguousSubranges() ;
        
        //Merge any "adjacent" ranges into single ranges
        mergeAdjacentRanges( ipRangesOut ) ;
        
        //Remove any overlap between the ranges that remain
        removeRangeSetOverlap( ipRangesOut ) ;
        
        //Remove any ranges which are emptied by the previous operation
        ipRangesOut = SquashIPRange.removeEmptyRanges( ipRangesOut ) ;
        
        return ipRangesOut ;
    }
    
    //Newer, faster, less thorough squash method
    public static IPv4range[] quickSquash( IPv4range[] ipRangesToSquash ) {
        
        IPv4range[] ipRangesOut ;
        
        //Remove any overlap between the ranges in the input array
        removeRangeSetOverlap( ipRangesToSquash ) ;
        
        //Remove any ranges which are left empty by this operation
        ipRangesOut = SquashIPRange.removeEmptyRanges( ipRangesToSquash ) ;
        
        //Merge any "adjacent" ranges in this set into single ranges
        ipRangesOut = mergeAdjacentRanges( ipRangesOut ) ;
        
        return ipRangesOut ;
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
    public static IPv4range[] mergeAdjacentRanges( IPv4range[] inRanges ) {
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
        return inRanges ;
    }
    
    //Takes an array of IPv4range objects and removes
    //any overlapping self-addresses from those ranges
    public static void removeRangeSetOverlap( IPv4range[] inRanges ) {
        int i , j ;
        for( i=0 ; i<inRanges.length ; i++ ) {
            for( j=i+1 ; j<inRanges.length ; j++ ) {
                if( inRanges[i].getSizeOfRange() >= inRanges[j].getSizeOfRange() ) {
                    inRanges[j].subtractRange( inRanges[i] ) ;
                } else {
                    inRanges[i].subtractRange( inRanges[j] ) ;                
                }
            }
        }
    }
    
    //Removes any empty ranges from
    //an array of IPv4ranges
    public static IPv4range[] removeEmptyRanges( IPv4range[] inRanges ) {
        int i ;
        i = 0 ;
        while( i<inRanges.length ) {
            if( inRanges[i].getSizeOfRange() == 0 ) {
                inRanges = inRanges[0].popFromIPv4rangeArray( inRanges , i ) ;
                i-- ;
            }
            i++ ;
        }
        return inRanges ;
    }  
    
    //Takes one array of input ranges, finds the
    //overlap between each pair of ranges,
    //converts each overlap to a human readable string
    //then returns all strings concatenated as one string
    public static String findRangeSetOverlap( IPv4range[] inRanges ) {
        int i , j ;
        IPv4range overlappingAddresses ;
        String outputText = "" ;
        for( i=0 ; i<inRanges.length ; i++ ) {
            for( j=i+1 ; j<inRanges.length ; j++ ) {
                overlappingAddresses = inRanges[i].findOverlap( inRanges[j] ) ;
                if( overlappingAddresses.getSizeOfRange() > 0 ) {
                    outputText += "Overlap between " 
                                + inRanges[i].convertRangeHumanReadable( inRanges[i] )
                                + " and " 
                                + inRanges[j].convertRangeHumanReadable( inRanges[j] )
                                + " : "
                                + overlappingAddresses.convertRangeHumanReadable( overlappingAddresses )
                                + "\n" ;
                }
            }
        }
        return outputText ;
    }
    
    //Determines if a long dash notation representation of an
    //IPv4 address cannot be represented by short dash notation    
    public static boolean incompatibleWithShortDash( String rangeLongDash ) {
        //Convert the start and end addresses of the range
        //into IPv4address objects for ease of parsing
        IPv4address startAddress = new IPv4address( rangeLongDash.split("-")[0] ) ;
        IPv4address endAddress = new IPv4address( rangeLongDash.split("-")[1] ) ;
        //The indicated range is incompatible if the third octets of these two
        //are not equal and either the start addressdoes not end in zero 
        //or the end address does not end in 255
        return ( !startAddress.equalsThirdOctet( endAddress ) ) & ( (!startAddress.getSectorAsString(4).equals("0") ) ) | ( !endAddress.getSectorAsString(4).equals("255") ) ;
    }
    
    /*
     * Splits ranges created from long dash notation
     * input which are cannot be represented in short dash notation
     * into two or three ranges which can be represented in short dash notation
     */
    public static IPv4range[] longToShortDash( String rangeLongDash ) {
        
        /*
         * The newly created short dash ranges
         * will be stored in this array to be returned
         */
        IPv4range[] newRanges = new IPv4range[0] ;
        
        /*
         * Intermediate addresses used to 
         * make code more readable
         */
        IPv4address intmAddress1 ;
        IPv4address intmAddress2 ;
        
        /*
         * Convert the start and end addresses of the range
         * into IPv4address objects for ease of parsing
         */
        IPv4address startAddress = new IPv4address( rangeLongDash.split("-")[0] ) ;
        IPv4address endAddress = new IPv4address( rangeLongDash.split("-")[1] ) ;
        
        /*
         * If the third octets of the start and finish
         * addresses match, then a simpler procedure
         * can be followed
         */
        if( startAddress.equalsThirdOctet( endAddress ) ) {
            newRanges = appendToIPv4rangeArray( newRanges ,
                                                new IPv4range( startAddress , endAddress ) ) ;
        }
        else {
            /*
             * Otherwise, if the third octets of
             * the start and finish addresses are not
             * equal, then up to three separate
             * ranges will need to be created
             */

            /* 
             * Always create a.b.c.d-255 & a.b.e.0-f
             * Firstly, a.b.c.d-255
             * a.b.c.0 (a.b.c.255 -> a.b.c.0)
             */
            intmAddress1 = startAddress.maskAddress( ~255L ) ;
            intmAddress1 = new IPv4address( intmAddress1.getIPAsNumber() + 255L ) ;
            newRanges = appendToIPv4rangeArray( newRanges , 
                                                new IPv4range( startAddress , intmAddress1 ) ) ;

            /*
             * Secondly, a.b.e.0-f
             * a.b.e.0 (from a.b.e.f -> a.b.e.0)
             */
            intmAddress2 = endAddress.maskAddress( ~255L ) ;
            newRanges = appendToIPv4rangeArray( newRanges , 
                                                new IPv4range( intmAddress2 , endAddress ) ) ; 

            //In the case e-c=2 -> add the central bit, a.b.c+1.0-255
            if( (endAddress.getSectorAsNumber(3) - startAddress.getSectorAsNumber(3)) == 2 ) {
                //a.b.c+1.0 (from a.b.c.d -> a.b.c.0 -> number -> +256 -> IP address)  
                intmAddress1 = new IPv4address( startAddress.maskAddress( ~255L ).getIPAsNumber() + 256L ) ;
                //a.b.c+1.0 (from a.b.c+1.0 -> number -> +255 -> IP address)  
                intmAddress2 = new IPv4address( intmAddress1.getIPAsNumber() + 255L ) ; 
                newRanges = appendToIPv4rangeArray( newRanges , new IPv4range( intmAddress1 , intmAddress2 ) ) ;
            }

            //In the case e-c>2 -> add the central bit, a.b.c+1-e-1.0-255
            if( (endAddress.getSectorAsNumber(3) - startAddress.getSectorAsNumber(3)) > 2 ) {
                //a.b.c+1.0 (from a.b.c.d -> a.b.c.0 -> number -> +256 -> IP address) 
                intmAddress1 = new IPv4address( startAddress.maskAddress( ~255L ).getIPAsNumber() + 256L ) ;
                //a.b.e-1.255 (from a.b.e.f -> a.b.e.0 -> number -> -1 -> IP address)
                intmAddress2 = new IPv4address( endAddress.maskAddress( ~255L ).getIPAsNumber() - 1L ) ; 
                newRanges = appendToIPv4rangeArray( newRanges , new IPv4range( intmAddress1 , intmAddress2 ) ) ;
            }

            /*
             * We only need to think about concatenating some of the ranges
             * if e-c>1, so a "central" range was added
             */
            if( newRanges.length == 3 ) {

                //If d=0, concatenate a.b.c.d-255 with the central bit
                if( startAddress.getSectorAsNumber( 4 ) == 0 ) {
                    newRanges[ newRanges.length-1 ].concatenateWithRange( newRanges[0] , true ) ; 
                    newRanges = newRanges[0].popFromIPv4rangeArray( newRanges , 0 ) ;
                }

                //If f=255, concatenate a.b.e.0-f with the central bit
                if( endAddress.getSectorAsNumber(4) == 255 ) {
                    newRanges[ newRanges.length-1 ].concatenateWithRange( newRanges[newRanges.length-2] , false ) ;
                    newRanges = newRanges[0].popFromIPv4rangeArray( newRanges , newRanges.length-2 ) ;
                }

            }

        }
        
        return newRanges ;
        
    }
    
    /*
     * Returns full string list of IP addresses
     * for all IP ranges in an array
     * Accepts the array of ranges as the first argument
     * And the delimiter between the addresses
     * as the second argume
     */
    public static String getAllAddressesAllRangesAsString( IPv4range[] ranges , String delimiter ) {
        String fullList = "" ;
        int i ;
        for( i=0 ; i<ranges.length ; i++ ) {
            fullList += ranges[i].getAllAddressesAsString( delimiter ) ;
        }
        return fullList ;
    }
    
    //Overloaded version of above, for space as default delimiter
    public static String getAllAddressesAllRangesAsString( IPv4range[] ranges ) {
        String fullList = "" ;
        int i ;
        for( i=0 ; i<ranges.length ; i++ ) {
            fullList += ranges[i].getAllAddressesAsString( " " ) ;
        }
        return fullList ;
    }
    
    /*
     * Creates new class of the main gui
     * Then sets visible to open the gui
     */
    public static void main(String[] args) {
        SquashIPRangeUINew gui = new SquashIPRangeUINew() ;
        gui.setVisible( true ) ;
    }
    
}