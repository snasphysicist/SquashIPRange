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
    
    //Demonstration of functionality 1
    //Reading in a single IP address
    private static void demo1() {
        String singleipAddress = "10.13.16.25" ;
        IPv4range ipRange = new IPv4range() ;
        System.out.println( " DEMONSTRATION 1 " ) ;
        ipRange.parseAddDashNotation( singleipAddress ) ;
        System.out.println( "Text in: " + singleipAddress ) ;
        System.out.println( "IPs in range " + ipRange.getAllAddressesAsString() + "\n" ) ;
    }
    
    //Demonstration of functionality 2
    //Reading in a range of IPs, dash notation in fourth octet
    private static void demo2() {
        String dashipRange = "10.13.16.24-26" ;
        IPv4range ipRange = new IPv4range() ;
        System.out.println( " DEMONSTRATION 2 " ) ;
        ipRange.parseAddDashNotation( dashipRange ) ;
        System.out.println( "Text in: " + dashipRange ) ;
        System.out.println( "IPs in range " + ipRange.getAllAddressesAsString() + "\n" ) ;
    }
    
    //Demonstration of functionality 3
    //Reading in a range of IPs, dash notation in third octet
    private static void demo3() {
        String dashipRange = "10.13.16-18.25" ;
        IPv4range ipRange = new IPv4range() ;
        System.out.println( " DEMONSTRATION 3 " ) ;
        ipRange.parseAddDashNotation( dashipRange ) ;
        System.out.println( "Text in: " + dashipRange ) ;
        System.out.println( "IPs in range " + ipRange.getAllAddressesAsString() + "\n" ) ;
    }
    
    //Demonstration of functionality 4
    //Reading in a range of IPs, dash notation in third and fourth octet
    private static void demo4() {
        String dashipRange = "10.13.16-17.25-26";
        IPv4range ipRange = new IPv4range() ;
        System.out.println( " DEMONSTRATION 4 " ) ;
        ipRange.parseAddDashNotation( dashipRange ) ;
        System.out.println( "Text in: " + dashipRange ) ;
        System.out.println( "IPs in range " + ipRange.getAllAddressesAsString() + "\n" ) ;
    }
    
    //Demonstration of functionality 5
    //Reading in a range of IPs, star notation in fourth octet
    private static void demo5() {
        String staripRange = "10.13.16.*" ;
        IPv4range ipRange = new IPv4range() ;
        System.out.println( " DEMONSTRATION 5 " ) ;
        ipRange.parseAddStarNotation( staripRange ) ;
        System.out.println( "Text in: " + staripRange ) ;
        System.out.println( "IPs in range " + ipRange.getAllAddressesAsString() + "\n" ) ;
    }
    
    //Demonstration of functionality 6
    //Reading in a range of IPs, CIDR slash notation
    private static void demo6() {
        String slashipRange = "10.13.16.30/29" ;
        IPv4range ipRange = new IPv4range() ;
        System.out.println( " DEMONSTRATION 6 " ) ;
        ipRange.parseAddSlashNotation( slashipRange ) ;
        System.out.println( "Text in: " + slashipRange ) ;
        System.out.println( "IPs in range " + ipRange.getAllAddressesAsString() + "\n" ) ;
    }
    
    //Demonstration of functionality 7
    //Finding overlap between ranges
    private static void demo7() {
        int i, j ;
        String[] dashipRanges = new String[]{"10.13.16.17-18","10.13.16.18-21","10.13.16.20-26"} ;
        IPv4range[] ipRanges = new IPv4range[3] ;
        IPv4range overlappingAddresses ;
        System.out.println( " DEMONSTRATION 7 " ) ;
        for( i=0 ; i<dashipRanges.length ; i++ ) {
            ipRanges[i] = new IPv4range() ;
            ipRanges[i].parseAddDashNotation( dashipRanges[i] ) ;
            System.out.println( "Text in range " + i + ": " + dashipRanges[i] ) ;
            System.out.println( "IPs in range " + ipRanges[i].getAllAddressesAsString() ) ;
        }
        for( i=0 ; i<ipRanges.length ; i++ ) {
            for( j=i+1 ; j<ipRanges.length ; j++ ) {
                overlappingAddresses = ipRanges[i].findOverlap( ipRanges[j] ) ;
                if( overlappingAddresses.getSizeOfRange() > 0 ) {
                    System.out.println( "Ranges " + i + " and " + j + " overlap" ) ;
                    System.out.println( "Matching addresses: " + overlappingAddresses.getAllAddressesAsString() ) ;
                } else {
                    System.out.println( "Ranges " + i + " and " + j + " do not overlap" ) ;
                }
            }
        }
        System.out.println( "" );
    }
    
    //Demonstration of functionality 8
    //Removing overlapping parts of ranges
    private static void demo8() {
        int i ;
        String[] dashipRanges = new String[]{"10.13.16-17.25-26","10.13.17.25-30"} ;
        IPv4range[] ipRanges = new IPv4range[2] ;
        System.out.println( " DEMONSTRATION 8 " ) ;
        for( i=0 ; i<dashipRanges.length ; i++ ) {
            ipRanges[i] = new IPv4range() ;
            ipRanges[i].parseAddDashNotation( dashipRanges[i] ) ;
            System.out.println( "Text in range " + i + ": " + dashipRanges[i] ) ;
            System.out.println( "IPs in range " + ipRanges[i].getAllAddressesAsString() ) ;
        }
        if( ipRanges[0].getSizeOfRange() <= ipRanges[1].getSizeOfRange() ) {
            ipRanges[0].subtractRange( ipRanges[1] ) ;
        } else {
            ipRanges[1].subtractRange( ipRanges[0] ) ;
        }
        System.out.println( "AFTER ELIMINATING OVERLAP" ) ;
        System.out.println( "IPs in range " + ipRanges[0].getAllAddressesAsString() ) ;
        System.out.println( "IPs in range " + ipRanges[1].getAllAddressesAsString() + "\n" ) ;
    }
    
    //Demonstration of functionality 9
    //Conversion of ranges back to human readable notation
    private static void demo9() {
        int i, j ;
        String[] dashipRanges = new String[]{"10.13.16.24-26","10.13.16-18.23-25","10.13.17.21-23"} ;
        IPv4range[] ipRanges = new IPv4range[3] ;
        
        System.out.println( " DEMONSTRATION 9 " ) ;
        
        for( i=0 ; i<dashipRanges.length ; i++ ) {
            ipRanges[i] = new IPv4range() ;
            ipRanges[i].parseAddDashNotation( dashipRanges[i] ) ;
            System.out.println( "Text in range " + i + ": " + dashipRanges[i] ) ;
            System.out.println( "IPs in range " + ipRanges[i].getAllAddressesAsString() ) ;
        }
        
        for( i=0 ; i<ipRanges.length ; i++ ) {
            for( j=i+1 ; j<ipRanges.length ; j++ ) {
                if( ipRanges[i].getSizeOfRange() <= ipRanges[j].getSizeOfRange() ) {
                    ipRanges[i].subtractRange( ipRanges[j] ) ;
                } else {
                    ipRanges[j].subtractRange( ipRanges[i] ) ;
                }
            }
        }
        
        System.out.println( "AFTER ELIMINATING OVERLAP" ) ;
        for( i=0 ; i<ipRanges.length ; i++ ) {
            System.out.println( "IPs in range " + i + " " + ipRanges[i].getAllAddressesAsString() ) ;
        }
        
        System.out.println( "CONVERTED TO HUMAN READABLE FORMAT" ) ;
        for( i=0 ; i<ipRanges.length ; i++ ) {
            System.out.println( "Text for range " + i + " :" + ipRanges[i].convertRangeHumanReadable( ipRanges[i] ) ) ;
        }
        System.out.println( "" ) ;
    }
    
    //Demonstration of functionality 10
    //Concatenation & ordering of ranges
    private static void demo10() {
        int i ;
        String[] dashipRanges = new String[]{"10.13.16.24-26","10.13.16.20-23"} ;
        IPv4range[] ipRanges = new IPv4range[2] ;
        System.out.println( " DEMONSTRATION 10 " ) ;
        for( i=0 ; i<dashipRanges.length ; i++ ) {
            ipRanges[i] = new IPv4range() ;
            ipRanges[i].parseAddDashNotation( dashipRanges[i] ) ;
            System.out.println( "Text in range " + i + ": " + dashipRanges[i] ) ;
            System.out.println( "IPs in range " + ipRanges[i].getAllAddressesAsString() ) ;
        }
        ipRanges[0].concatenateWithRange(ipRanges[1], false) ;
        ipRanges[0].sortRange();
        System.out.println( "AFTER CONCATENATION" ) ;
        System.out.println( "IPs in range " + ipRanges[0].getAllAddressesAsString() ) ;
        System.out.println( "Range 0 as text: " + ipRanges[0].convertRangeHumanReadable( ipRanges[0] ) + "\n" ) ;
    }
    
    //Demonstration of functionality 11
    //Taking multiple overlapping and non overlapping ranges
    //Adding those all into a range without duplicates
    //Splitting this non-contiguous range into multiple
    //contiguous subranges
    private static void demo11() {
        int i ;
        String[] dashipRanges = new String[]{"10.13.16.20-26","10.13.16.25-30","10.13.16.50-52","10.13.17.20-22"} ;
        IPv4range[] ipRanges = new IPv4range[4] ;
        IPv4range concatenatedRange = new IPv4range() ;
        IPv4range[] ipRangesOut ;
        System.out.println( " DEMONSTRATION 11 " ) ;
        for( i=0 ; i<dashipRanges.length ; i++ ) {
            ipRanges[i] = new IPv4range() ;
            ipRanges[i].parseAddDashNotation( dashipRanges[i] ) ;
            System.out.println( "Text in range " + i + ": " + dashipRanges[i] ) ;
            System.out.println( "IPs in range " + ipRanges[i].getAllAddressesAsString() ) ;
            concatenatedRange.concatenateWithRange( ipRanges[i] , true );
        }
        ipRangesOut = concatenatedRange.getContiguousSubranges() ;
        System.out.println( "AFTER CONCATENATION AND SPLITTING" ) ;
        for( i=0 ; i<ipRangesOut.length ; i++ ) {
            System.out.println( "IPs in range " + ipRangesOut[i].getAllAddressesAsString() ) ;
            System.out.println( "Range " + i + " as text : " + ipRangesOut[i].convertRangeHumanReadable( ipRangesOut[i] ) ) ;
        }
        System.out.println() ;
    }
    
    //Demonstration of functionality 12
    //Combining two "adjacent" ranges of addresses into a single range
    private static void demo12() {
        int i,j ;
        String[] dashipRanges = new String[]{"10.13.16.24-26","10.13.17.24-26","10.13.18.67-69","10.13.19.67-70"} ;
        IPv4range[] ipRanges = new IPv4range[4] ;
        System.out.println( " DEMONSTRATION 12 " ) ;
        for( i=0 ; i<dashipRanges.length ; i++ ) {
            ipRanges[i] = new IPv4range() ;
            ipRanges[i].parseAddDashNotation( dashipRanges[i] ) ;
            System.out.println( "Text in range " + i + ": " + dashipRanges[i] ) ;
            System.out.println( "IPs in range " + ipRanges[i].getAllAddressesAsString() ) ;
        }
        //First we detect adjacent ranges and concatenate them
        for( i=0 ; i<ipRanges.length ; i++ ) {
            for( j=i+1 ; j<ipRanges.length ; j++ ) {
                System.out.println( "Ranges " + i + " and " + j + " \"adjacent\"? " + ipRanges[i].isAdjacentRange( ipRanges[j] ) );
                if( ipRanges[i].isAdjacentRange( ipRanges[j] ) == 1 ) {
                    ipRanges[i].concatenateWithRange( ipRanges[j] , false );
                    System.out.println( "Added range " + j + " to end of range " + i ) ;
                } else if ( ipRanges[i].isAdjacentRange( ipRanges[j] ) == -1 ) {
                    ipRanges[i].concatenateWithRange( ipRanges[j] , true );
                    System.out.println( "Added range " + j + " to start of range " + i ) ;
                }
            }
        }
        //Then we remove overlapping IP addresses from ranges
        for( i=0 ; i<ipRanges.length ; i++ ) {
            for( j=i+1 ; j<ipRanges.length ; j++ ) {
                if( ipRanges[i].getSizeOfRange() >= ipRanges[j].getSizeOfRange() ) {
                    ipRanges[j].subtractRange( ipRanges[i] ) ;
                } else {
                    ipRanges[i].subtractRange( ipRanges[j] ) ;                
                }
            }
        }
        //Remove any empty ranges
        i = 0 ;
        while( i<ipRanges.length ) {
            if( ipRanges[i].getSizeOfRange() == 0 ) {
                ipRanges = ipRanges[0].popFromIPv4rangeArray( ipRanges , i ) ;
                i-- ;
            }
            i++ ;
        }
        System.out.println( "AFTER CONCATENATION" ) ;
        for( i=0 ; i<ipRanges.length ; i++ ) {
            System.out.println( "IPs in range " + ipRanges[i].getAllAddressesAsString() ) ;
            System.out.println( "Range " + i + " as text : " + ipRanges[i].convertRangeHumanReadable( ipRanges[i] ) ) ;
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

        demo1() ;
        demo2() ;
        demo3() ;
        demo4() ;
        demo5() ;
        demo6() ;
        demo7() ;
        demo8() ;
        demo9() ;
        demo10() ;
        demo11() ;
        demo12() ;
        
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