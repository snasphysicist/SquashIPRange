/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        String[] stringipRanges = new String[]{"10.13.17.100-102","10.13.17.99-101"} ;
        
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
        
        stringipRangesOut = allAddresses.getWholeRangeHumanReadable() ;
        
        for( i=0 ; i<stringipRangesOut.length ; i++ ) {
            System.out.println( stringipRangesOut[i] ) ;
        }
        
    } // Closing main
    
} // Closing class