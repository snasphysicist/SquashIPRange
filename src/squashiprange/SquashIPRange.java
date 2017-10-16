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
        
        //Testing
        IPv4address splitTool = new IPv4address(0L) ;
        String[] sectors = new String[4] ;
        String[] stringipRanges = new String[]{"10.13.16.*","10.13.16.19","10.13.17.20"} ;
        IPv4range intmRange ;
        IPv4range[] allRanges = new IPv4range[0] ;
        Integer sector3min ;
        Integer sector3max ;
        Integer i,j,k ;
        Integer numberOfRanges = stringipRanges.length ;
        IPv4range[] overlappingAddresses = new IPv4range[ (numberOfRanges*(numberOfRanges-1))/2 ] ;
        
        //Taking the string ranges
        //and converting them to IPv4ranges
        //In a robust, error handled way
        for( j=0 ; j<stringipRanges.length ; j++ ) {
            System.out.println( new Integer(j).toString() );
            try {
                intmRange = new IPv4range() ;
                stringipRanges[j] = stringipRanges[j].replaceAll("\\*", "0-255") ;
                sectors = splitTool.splitBySector( stringipRanges[j] ) ;
                if ( sectors[3].contains( "/" ) ) {
                    System.out.println( intmRange.parseAddSlashNotation( stringipRanges[j] ) ) ;
                } else if ( sectors[2].contains( "-" ) ) {
                    sector3min = new Integer( sectors[2].split( "-" )[0] ) ;
                    sector3max = new Integer( sectors[2].split( "-" )[1] ) ;
                    for(i=sector3min;i<=sector3max;i++) {
                        System.out.println( intmRange.parseAddDashNotation( sectors[0] + "." + sectors[1] + "." + i.toString() + "." + sectors[3] ) ) ;
                    }
                } else {
                    System.out.println( intmRange.parseAddDashNotation( stringipRanges[j] ) ) ;
                }
                allRanges = appendToIPv4rangeArray( allRanges , intmRange ) ;
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
        
        k = 0 ;
        for( i=0 ; i<numberOfRanges ; i++ ) {
            for( j=i+1 ; j<numberOfRanges;j++ ) {
                if( allRanges[i].getSizeOfRange() < allRanges[j].getSizeOfRange() ) {
                    allRanges[i].subtractRange(allRanges[j]) ;
                }
                k++ ;
            }
        }
        
        for( i=0 ; i<allRanges.length ; i++ ) {
            System.out.println( allRanges[i].convertRangeHumanReadable(allRanges[i]) ) ;
        }
        
    } // Closing main
    
} // Closing class