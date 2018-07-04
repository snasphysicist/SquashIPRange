/**
 * ****************************************************************
 *Squash IP Range is offered under the lesser GPL licence version 3
 *Please refer to the LICENSE file included at the top level
 *of the git repo for full information on this licence
 *Written by snasphysicist (Scott N A Smith)
 ******************************************************************
 */
package squashiprange;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author scott
 */
public class IPv4rangeTest {
    
    public IPv4rangeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of sortRange method, of class IPv4range.
     */
    @Test
    public void testSortRange() {
        System.out.println("Basic Test --- IPv4range --- sortRange") ;
        IPv4range instance = new IPv4range() ;
        instance.addAddressToRange( new IPv4address( "178.233.123.8" ) , false ) ;
        instance.addAddressToRange( new IPv4address( "247.101.116.108" ) , false ) ;
        instance.addAddressToRange( new IPv4address( "25.177.145.19" ) , false ) ;
        instance.addAddressToRange( new IPv4address( "199.223.209.135" ) , false ) ;
        instance.addAddressToRange( new IPv4address( "215.9.248.183" ) , false ) ;
        instance.sortRange() ;
        assertEquals( instance.getAddressFromRange( 0 ).getIPAsString() , "25.177.145.19"  ) ;
        assertEquals( instance.getAddressFromRange( 1 ).getIPAsString() , "178.233.123.8"  ) ;
        assertEquals( instance.getAddressFromRange( 2 ).getIPAsString() , "199.223.209.135"  ) ;
        assertEquals( instance.getAddressFromRange( 3 ).getIPAsString() , "215.9.248.183"  ) ;
        assertEquals( instance.getAddressFromRange( 4 ).getIPAsString() , "247.101.116.108"  ) ;
    }

    /**
     * Test of appendToIPv4addressArrayEnd method, of class IPv4range.
     */
    @Test
    public void testAppendToIPv4addressArrayEnd() {
        System.out.println("Basic Test --- IPv4range --- appendToIPv4addressArrayEnd");
        IPv4address ipAddress0 = new IPv4address( "237.245.0.148" ) ;
        IPv4address ipAddress1 = new IPv4address( "177.205.28.208" ) ;
        IPv4address ipAddress2 = new IPv4address( "254.249.21.26" ) ;
        IPv4address ipAddress3 = new IPv4address( "121.111.252.167" ) ;
        IPv4address ipAddress4 = new IPv4address( "118.82.56.176" ) ;
        IPv4address[] inarray = new IPv4address[]{ ipAddress0 , ipAddress1 , ipAddress2 , ipAddress3 } ;
        IPv4range instance = new IPv4range() ;
        IPv4address[] result = instance.appendToIPv4addressArrayEnd( inarray , ipAddress4 ) ;
        //We're checking not only that the new address
        //has been added in the correct place, but also
        //that the rest of the array has maintained its integrity
        assertEquals( true , result[0].equals( ipAddress0 ) ) ;
        assertEquals( true , result[1].equals( ipAddress1 ) ) ;
        assertEquals( true , result[2].equals( ipAddress2 ) ) ;
        assertEquals( true , result[3].equals( ipAddress3 ) ) ;
        assertEquals( true , result[4].equals( ipAddress4 ) ) ;
    }

    /**
     * Test of appendToIPv4addressArrayStart method, of class IPv4range.
     */
    @Test
    public void testAppendToIPv4addressArrayStart() {
        System.out.println("Basic Test --- IPv4range --- appendToIPv4addressArrayStart");
        IPv4address ipAddress0 = new IPv4address( "237.245.0.148" ) ;
        IPv4address ipAddress1 = new IPv4address( "177.205.28.208" ) ;
        IPv4address ipAddress2 = new IPv4address( "254.249.21.26" ) ;
        IPv4address ipAddress3 = new IPv4address( "121.111.252.167" ) ;
        IPv4address ipAddress4 = new IPv4address( "118.82.56.176" ) ;
        IPv4address[] inarray = new IPv4address[]{ ipAddress0 , ipAddress1 , ipAddress2 , ipAddress3 } ;
        IPv4range instance = new IPv4range() ;
        IPv4address[] result = instance.appendToIPv4addressArrayStart(inarray, ipAddress4) ;
        //We're checking not only that the new address
        //has been added in the correct place, but also
        //that the rest of the array has maintained its integrity
        assertEquals( true , result[0].equals( ipAddress4 ) ) ;
        assertEquals( true , result[1].equals( ipAddress0 ) ) ;
        assertEquals( true , result[2].equals( ipAddress1 ) ) ;
        assertEquals( true , result[3].equals( ipAddress2 ) ) ;
        assertEquals( true , result[4].equals( ipAddress3 ) ) ;
    }

    /**
     * Test of popFromIPv4addressArray method, of class IPv4range.
     */
    @Test
    public void testPopFromIPv4addressArray() {
        System.out.println("Basic Test --- IPv4range --- popFromIPv4addressArray") ;
        int i ;
        IPv4address ipAddress0 = new IPv4address( "199.145.162.88" ) ;
        IPv4address ipAddress1 = new IPv4address( "167.201.110.202" ) ;
        IPv4address ipAddress2 = new IPv4address( "56.184.111.116" ) ;
        IPv4address ipAddress3 = new IPv4address( "196.214.131.36" ) ;
        IPv4address ipAddress4 = new IPv4address( "217.130.174.29" ) ;
        IPv4range instance = new IPv4range() ;
        IPv4address[] inArray = new IPv4address[]{ ipAddress0 , ipAddress1 , ipAddress2 , ipAddress3 , ipAddress4 } ;
        IPv4address[] result = instance.popFromIPv4addressArray( inArray , 4 ) ;
        for( i=0 ; i<result.length ; i++ ) {
            assertEquals( true , inArray[i].equals( result[i] ) ) ;
        }
        result = instance.popFromIPv4addressArray( result , 0 ) ;
        for( i=0 ; i<result.length ; i++ ) {
            assertEquals( true , inArray[i+1].equals( result[i] ) ) ;
        }
        result = instance.popFromIPv4addressArray( result , 1 ) ;
        assertEquals( true , inArray[1].equals( result[0] ) ) ;
        assertEquals( true , inArray[3].equals( result[1] ) ) ;
    }
    
    /**
     * Test of isInRange method, from class IPv4range.
     */
    @Test
    public void testIsInRange() {
        System.out.println( "Basic Test --- IPv4range --- isInRange" ) ;
        int i ;
        IPv4address ipAddress0 = new IPv4address( "180.129.104.80" ) ;
        IPv4address ipAddress1 = new IPv4address( "248.145.252.88" ) ;
        IPv4address ipAddress2 = new IPv4address( "39.44.11.73" ) ;
        IPv4address ipAddress3 = new IPv4address( "83.184.243.85" ) ;
        IPv4address ipAddress4 = new IPv4address( "58.176.153.35" ) ;
        IPv4address ipAddress5 = new IPv4address( "245.176.108.148" ) ;
        IPv4address ipAddress6 = new IPv4address( "98.88.52.247" ) ;
        IPv4address ipAddress7 = new IPv4address( "212.42.87.94" ) ;
        IPv4address ipAddress8 = new IPv4address( "234.126.171.34" ) ;
        IPv4address ipAddress9 = new IPv4address( "36.189.215.28" ) ;
        IPv4address[] addresses = new IPv4address[]{ ipAddress0 , ipAddress1 , 
                                                     ipAddress2 , ipAddress3 , 
                                                     ipAddress4 , ipAddress5 ,
                                                     ipAddress6 , ipAddress7 ,
                                                     ipAddress8 , ipAddress9 } ;
        IPv4range instance = new IPv4range() ;
        //Add first five addresses to range
        for( i=0 ; i<5 ; i++ ) {
            instance.addAddressToRange( addresses[i] , false ) ;
        }
        //Check that the first five addresses show
        //as in the range
        for( i=0 ; i<5 ; i++ ) {
            assertEquals( true , instance.isInRange( addresses[i] ) ) ;
        }
        //Check that the latter five addresses
        //do not show as in the range
        for( i=5 ; i<addresses.length ; i++ ) {
            assertEquals( false , instance.isInRange( addresses[i] ) ) ;
        }
        
    }
    
    /**
     * Test of method concatenateWithRange, from class IPv4range.
     */
    @Test
    public void testConcatenateWithRange() {
        System.out.println( "Basic Test --- IPv4range --- concatenateWithRange" ) ;
        int i ;
        IPv4address ipAddress0 = new IPv4address( "3.15.24.189" ) ;
        IPv4address ipAddress1 = new IPv4address( "112.32.14.134" ) ;
        IPv4address ipAddress2 = new IPv4address( "32.76.192.10" ) ;
        IPv4address ipAddress3 = new IPv4address( "125.245.132.125" ) ;
        IPv4address ipAddress4 = new IPv4address( "127.111.60.35" ) ;
        IPv4address ipAddress5 = new IPv4address( "208.88.137.214" ) ;
        IPv4address ipAddress6 = new IPv4address( "164.240.169.115" ) ;
        IPv4address ipAddress7 = new IPv4address( "235.172.250.53" ) ;
        IPv4address ipAddress8 = new IPv4address( "188.43.16.110" ) ;
        IPv4address ipAddress9 = new IPv4address( "179.16.245.101" ) ;
        IPv4address[] addresses = new IPv4address[]{ ipAddress0 , ipAddress1 , 
                                                     ipAddress2 , ipAddress3 , 
                                                     ipAddress4 , ipAddress5 ,
                                                     ipAddress6 , ipAddress7 ,
                                                     ipAddress8 , ipAddress9 } ;
        IPv4range range1 = new IPv4range() ;
        IPv4range range2 = new IPv4range() ;
        for( i=0 ; i<5 ; i++ ) {
            range1.addAddressToRange( addresses[i] , false ) ;
        }
        for( i=5 ; i<10 ; i++ ) {
            range2.addAddressToRange( addresses[i] , false ) ;
        }
        range1.concatenateWithRange( range2 , false ) ;
        for( i=0 ; i<10 ; i++ ) {
            assertEquals( true , addresses[i].equals( range1.getAddressFromRange( i ) ) ) ;
        }
    }
    
    /**
     * Test of method addAddressToRange, in class IPv4range.
     */
    @Test
    public void testAddAddressToRange() {
        System.out.println( "Basic Test --- IPv4range --- addAddressToRange" ) ;
        IPv4address ipAddress0 = new IPv4address( "248.53.164.25" ) ;
        IPv4address ipAddress1 = new IPv4address( "50.40.40.250" ) ;
        IPv4address ipAddress2 = new IPv4address( "138.85.41.85" ) ;  
        IPv4range range = new IPv4range() ;
        //Test that we can add to an empty range
        range.addAddressToRange( ipAddress0 , false ) ;
        assertEquals( true , range.getAddressFromRange(0).equals( ipAddress0 ) ) ;
        //Test that we can add to the start of a range
        range.addAddressToRange( ipAddress1 ,  true ) ;
        assertEquals( true , range.getAddressFromRange(0).equals( ipAddress1 ) ) ;
        assertEquals( true , range.getAddressFromRange(1).equals( ipAddress0 ) ) ;
        //Test that we can add to the end of a range
        range.addAddressToRange( ipAddress2 ,  false ) ;
        assertEquals( true , range.getAddressFromRange(0).equals( ipAddress1 ) ) ;
        assertEquals( true , range.getAddressFromRange(1).equals( ipAddress0 ) ) ;
        assertEquals( true , range.getAddressFromRange(2).equals( ipAddress2 ) ) ;
    }
    
    /**
     * Test of method isAdjacentAddress, in class IPv4range.
     */
    @Test
    public void testIsAdjacentAddress() {
        System.out.println( "Basic Test --- IPv4range --- isAdjacentAddress" ) ;
        IPv4address ipAddress0 = new IPv4address( "4.60.112.109" ) ;
        IPv4address ipAddress1 = new IPv4address( "4.60.112.110" ) ;
        IPv4address ipAddress2 = new IPv4address( "4.60.112.111" ) ;
        IPv4address ipAddress3 = new IPv4address( "4.60.112.112" ) ;
        IPv4address ipAddress4 = new IPv4address( "4.60.112.113" ) ;
        IPv4address ipAddress5 = new IPv4address( "91.139.16.11" ) ;
        IPv4address ipAddress6 = new IPv4address( "1.236.77.61" ) ;
        IPv4range range0 = new IPv4range( ipAddress1 , ipAddress3 ) ;
        //Only for those IP addresses which are next to the ends of the
        //range but not in the range should the method return true
        //These are ipAddress0 and ipAddress4
        assertEquals( true , range0.isAdjacentAddress( ipAddress0 ) ) ;
        assertEquals( false , range0.isAdjacentAddress( ipAddress1 ) ) ;
        assertEquals( false , range0.isAdjacentAddress( ipAddress2 ) ) ;
        assertEquals( false , range0.isAdjacentAddress( ipAddress3 ) ) ;
        assertEquals( true , range0.isAdjacentAddress( ipAddress4 ) ) ;
        assertEquals( false , range0.isAdjacentAddress( ipAddress5 ) ) ;
        assertEquals( false , range0.isAdjacentAddress( ipAddress6 ) ) ;
    }
    
    /**
     * Test of method getAddressFromRange, in class IPv4range.
     */
    @Test
    public void testGetAddressFromRange() {
        System.out.println( "Basic Test --- IPv4range --- getAddressFromRange" ) ;
        int i ;
        IPv4address ipAddress0 = new IPv4address( "83.135.162.246" ) ;
        IPv4address ipAddress1 = new IPv4address( "73.235.176.82" ) ;
        IPv4address ipAddress2 = new IPv4address( "189.74.43.181" ) ;
        IPv4address ipAddress3 = new IPv4address( "64.195.94.139" ) ;
        IPv4address ipAddress4 = new IPv4address( "16.130.238.75" ) ;
        IPv4address[] addresses = new IPv4address[]{ ipAddress0 , ipAddress1 ,
                                                     ipAddress2 , ipAddress3 ,
                                                     ipAddress4 } ;
        IPv4range range = new IPv4range() ;
        for( i=0 ; i<addresses.length ; i++ ) {
            range.addAddressToRange( addresses[i] , false ) ;
        }
        for( i=0 ; i<addresses.length ; i++ ) {
            assertEquals( true , addresses[i].equals( range.getAddressFromRange( i ) ) ) ;
        }
    }
    
    /**
     * Test of method getRangeAsAddresses, in class IPv4range.
     */
    @Test
    public void testGetRangeAsAddresses() {
        System.out.println( "Basic Test --- IPv4range --- getRangeAsAddresses" ) ;
        int i ;
        IPv4address ipAddress0 = new IPv4address( "151.53.247.138" ) ;
        IPv4address ipAddress1 = new IPv4address( "107.223.207.247" ) ;
        IPv4address ipAddress2 = new IPv4address( "204.240.2.186" ) ;
        IPv4address ipAddress3 = new IPv4address( "248.125.138.145" ) ;
        IPv4address ipAddress4 = new IPv4address( "66.11.20.200" ) ;
        IPv4address[] addresses = new IPv4address[]{ ipAddress0 , ipAddress1 , 
                                                     ipAddress2 , ipAddress3 , 
                                                     ipAddress4 } ;
        IPv4range range = new IPv4range() ;
        IPv4address[] addressesFromRange ;
        for( i=0 ; i<addresses.length ; i++ ) {
            range.addAddressToRange( addresses[i] , false ) ;
        }
        addressesFromRange = range.getRangeAsAddresses() ;
        for( i=0 ; i<addresses.length ; i++ ) {
            assertEquals( true , addresses[i].equals( addressesFromRange[i] ) ) ;
        }
    }
    
    /**
     * Test of method methodName, in class IPv4range.
     */
    @Test
    public void testGetSizeOfRange() {
        System.out.println( "Basic Test --- IPv4range --- getSizeOfRange" ) ;
        int i ;
        IPv4address ipAddress0 = new IPv4address( "67.47.218.141" ) ;
        IPv4address ipAddress1 = new IPv4address( "134.95.155.93" ) ;
        IPv4address ipAddress2 = new IPv4address( "233.21.7.167" ) ;
        IPv4address ipAddress3 = new IPv4address( "209.191.83.237" ) ;
        IPv4address ipAddress4 = new IPv4address( "64.162.43.69" ) ;
        IPv4address[] addresses = new IPv4address[]{ ipAddress0 , ipAddress1 ,
                                                     ipAddress2 , ipAddress3 ,
                                                     ipAddress4 } ;
        IPv4range range = new IPv4range() ;
        for( i=0 ; i<addresses.length ; i++ ) {
            range.addAddressToRange( addresses[i] , false ) ;
            assertEquals( true, i+1 == range.getSizeOfRange() ) ;
        }
    }
    
    /**
     * Test of method parseAddStarNotation, in class IPv4range.
     */
    @Test
    public void testParseAddStarNotation() {
        System.out.println( "Basic Test --- IPv4range --- parseAddStarNotation" ) ;
        int i ;
        String starRange = "101.1.252.*" ;
        IPv4range parseRange = new IPv4range() ;
        parseRange.parseAddStarNotation( starRange ) ;
        //Manually generate the same range by adding all 256 addresess
        IPv4range controlRange = new IPv4range( new IPv4address( "101.1.252.0" ) ) ;
        for( i=0 ; i<256 ; i++ ) {
            controlRange.addAddressToRange( new IPv4address( controlRange.getAddressFromRange(0).getIPAsNumber() + i ) , false ) ;
        }
        //For the two ranges to match, we want each address
        //in each range to be in the other range
        //First check all addresses in the 
        //control range are in the parsed range
        for( i=0 ; i<controlRange.getSizeOfRange() ; i++ ) {
            assertEquals( true , parseRange.isInRange( controlRange.getAddressFromRange(i) ) ) ;
        }
        //Secondly check that all addresses in the 
        //parsed range are in the control range
        for( i=0 ; i<parseRange.getSizeOfRange() ; i++ ) {
            assertEquals( true , controlRange.isInRange( parseRange.getAddressFromRange(i) ) ) ;
        }
    }
    
    /**
     * Test of method parseAddSlashNotation, in class IPv4range.
     */
    @Test
    public void testParseAddSlashNotation() {
        System.out.println( "Basic Test --- IPv4range --- parseAddSlashNotation" ) ;
        int i ;
        IPv4range parseRange = new IPv4range() ;
        parseRange.parseAddSlashNotation( "195.138.54.167/25" ) ;
        //Manually recreate the range by adding 
        //addresses one at a time
        IPv4range controlRange = new IPv4range( new IPv4address( "195.138.54.128" ) ) ;
        for( i=0 ; i<128 ; i++ ) {
            controlRange.addAddressToRange( new IPv4address( controlRange.getAddressFromRange(0).getIPAsNumber() + i ) , false ) ;
        }
        //The two ranges match if all addresses
        //from each range can be found in the other
        //First check that every address in the control range
        //is also in the parsed range
        for( i=0 ; i<controlRange.getSizeOfRange() ; i++ ) {
            assertEquals( true , parseRange.isInRange( controlRange.getAddressFromRange(i) ) ) ;
        }
        //Then check that every address in the parsed range
        //is also in the control range
        for( i=0 ; i<parseRange.getSizeOfRange() ; i++ ) {
            assertEquals( true , controlRange.isInRange( parseRange.getAddressFromRange(i) ) ) ;
        }
    }
    
    /**
     * Test of method parseAddDashNotation, in class IPv4range.
     */
    @Test
    public void testParseAddDashNotation() {
        System.out.println( "Basic Test --- IPv4range --- parseAddDashNotation" ) ;
        Integer i , j;
        IPv4range parseRange = new IPv4range() ;
        parseRange.parseAddDashNotation( "143.157.53-223.39-223" ) ;
        //Manually build t0he range represented by the notation above
        //By adding in the IP addresses one by one
        IPv4range controlRange = new IPv4range() ;
        for( i=53 ; i<224 ; i++ ) {
            for( j=39 ; j<224 ; j++ ) {
                controlRange.addAddressToRange( new IPv4address( "143.157." + i.toString() + "." + j.toString() ) , false ) ;
            }
        }
        //Ranges are the same if each address from both ranges
        //can be found in the other range
        //First check that all ranges from the control range
        //can be found in the parsed range
        for( i=0 ; i<controlRange.getSizeOfRange() ; i++ ) {
            assertEquals( true , parseRange.isInRange( controlRange.getAddressFromRange(i) ) ) ;
        }
        //Then check that each address in the parsed range
        //can be found in the control range
        for( i=0 ; i<parseRange.getSizeOfRange() ; i++ ) {
            assertEquals( true , controlRange.isInRange( parseRange.getAddressFromRange(i) ) ) ;
        }        
    }
    
    /**
     * Test of method subtractRange, in class IPv4range.
     */
    @Test
    public void testSubtractRange() {
        System.out.println( "Basic Test --- IPv4range --- subtractRange" ) ;
        int i ;
        //Subtract from this
        String textRange1 = "162.221.100.10-32" ;
        //Subtract this
        String textRange2 = "162.221.100.0-20" ;
        //This range should result
        String textRange3 = "162.221.100.21-32" ;
        //Convert these three to ranges
        IPv4range range1 = new IPv4range() ;
        range1.parseAddDashNotation( textRange1 ) ;
        IPv4range range2 = new IPv4range() ;
        range2.parseAddDashNotation( textRange2 ) ;
        IPv4range range3 = new IPv4range() ;
        range3.parseAddDashNotation( textRange3 ) ;
        //Subtract range2 from range1
        //to create the differenced range
        IPv4range diffrange = range1.createCopy() ;
        diffrange.subtractRange( range2 ) ;
        //Differenced range should be
        //equivalent to range3
        //This is true if for all addresses in diffrange
        //the addresses are also in range3
        for( i=0 ; i<diffrange.getSizeOfRange() ; i++ ) {
            assertEquals( true , range3.isInRange( diffrange.getAddressFromRange(i) ) ) ;
        }
        //and vice versa
        for( i=0 ; i<range3.getSizeOfRange() ; i++ ) {
            assertEquals( true , diffrange.isInRange( range3.getAddressFromRange(i) ) ) ;
        }
    }

    /**
     * Test of method methodName, in class IPv4range.
     */
    @Test
    public void testFindOverlap() {
        System.out.println( "Basic Test --- IPv4range --- findOverlap" ) ;
        int i ;
        IPv4range range1 = new IPv4range() ;
        range1.parseAddDashNotation( "238.122.193.13-50" ) ;
        IPv4range range2 = new IPv4range() ;
        range2.parseAddDashNotation( "238.122.193.0-12" ) ;
        IPv4range range3 = new IPv4range() ;
        range3.parseAddDashNotation( "238.122.193.40-90" ) ;
        // This is overlap of range 1 with range 3
        IPv4range knownOverlap = new IPv4range() ;
        knownOverlap.parseAddDashNotation( "238.122.193.40-50" ) ;
        //Overlap of range 1 with range 2
        IPv4range foundOverlap1 = range1.findOverlap( range2 ) ;
        //Overlap of range 1 with range 3
        IPv4range foundOverlap2 = range1.findOverlap( range3 ) ;        
        //We expect there to be no overlap between range 1 and range 2
        assertEquals( true , foundOverlap1.getSizeOfRange() == 0 ) ;
        //We expect all addresses in knownOverlap to be present
        //in the overlap between range 2 and range 3
        //and vice versa (i.e. knownOverlap == foundOverlap2)
        for( i=0 ; i<knownOverlap.getSizeOfRange() ; i++ ) {
            assertEquals( true , foundOverlap2.isInRange( knownOverlap.getAddressFromRange(i) ) ) ;
        }
        for( i=0 ; i<foundOverlap2.getSizeOfRange() ; i++ ) {
            assertEquals( true , knownOverlap.isInRange( foundOverlap2.getAddressFromRange(i) ) ) ;
        }
    }
    
    /**
     * Test of method createCopy, in class IPv4range.
     */
    @Test
    public void testCreateCopy() {
        int i , j ;
        boolean foundPointer ;
        System.out.println( "Basic Test --- IPv4range --- createCopy" ) ;
        //Create some range and fill it with addresses
        IPv4range range1 = new IPv4range() ;
        range1.parseAddStarNotation("57.14.143.*");
        //Create a copy in another range
        IPv4range range2 = range1.createCopy() ;
        //For this to be correct, the values of
        //each address in each range should be present
        //in the other range
        for( i=0 ; i<range1.getSizeOfRange() ; i++ ) {
            assertEquals( true , range2.isInRange( range1.getAddressFromRange(i) ) ) ;
        }
        for( i=0 ; i<range2.getSizeOfRange() ; i++ ) {
            assertEquals( true , range1.isInRange( range2.getAddressFromRange(i) ) ) ;
        }
        //However the pointer for an address
        //should not be able to be found
        //in the other range
        for( i=0 ; i<range1.getSizeOfRange() ; i++ ) {
            foundPointer = false ;
            for( j=0 ; j<range2.getSizeOfRange() ; j++ ) {
                if( range1.getAddressFromRange(i) == range2.getAddressFromRange(j) ) {
                    foundPointer = true ;
                }
            }
            assertEquals( false , foundPointer ) ; 
        }        
    }
    
    /**
     * Test of method isAdjacentRange, in class IPv4range.
     */
    @Test
    public void testIsAdjacentRange() {
        System.out.println( "Basic Test --- IPv4range --- isAdjacentRange" ) ;
        
        int i ;
        
        //This is a bit inelegant
        //but it will do for now
        String[] ranges1 = new String[]{"26.131.49.199",
                                        "26.131.49.199",
                                        "26.131.49.199",
                                        "26.131.49.199",
                                        "26.131.49.199",        //5
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",  //11
                                        "196.227.172.137",
                                        "196.227.173.137",
                                        "196.227.169.137",
                                        "196.227.168.137",
                                        "196.227.170.137",
                                        "196.227.171.137",      //17
                                        "63.253.150-151.249",
                                        "63.253.150-151.249",
                                        "63.253.150-151.249",
                                        "63.253.150-151.249",
                                        "63.253.150-151.249",
                                        "63.253.150-151.249",   //23
                                        "26.131.49.199",
                                        "26.131.49.199",
                                        "26.131.49.199",
                                        "26.131.49.199",
                                        "26.131.49.199",        //28
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",  //34
                                        "196.227.172.137",
                                        "196.227.173.137",
                                        "196.227.169.137",
                                        "196.227.168.137",
                                        "196.227.170.137",
                                        "196.227.171.137",      //40
                                        "63.253.150-151.249",
                                        "63.253.150-151.249",
                                        "63.253.150-151.249",
                                        "63.253.150-151.249",
                                        "63.253.150-151.249",
                                        "63.253.150-151.249",   //46
                                        "26.131.49.199",
                                        "26.131.49.199",
                                        "26.131.49.199",
                                        "26.131.49.199",
                                        "26.131.49.199",        //51
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",  //57
                                        "196.227.172.137",
                                        "196.227.173.137",
                                        "196.227.169.137",
                                        "196.227.168.137",
                                        "196.227.170.137",
                                        "196.227.171.137",      //63
                                        "63.253.150-151.249",
                                        "63.253.150-151.249",
                                        "63.253.150-151.249",
                                        "63.253.150-151.249",
                                        "63.253.150-151.249",
                                        "63.253.150-151.249",   //69
                                        "26.131.49.199" ,
                                        "26.131.49.199" ,
                                        "26.131.49.199" ,
                                        "26.131.49.199" ,
                                        "26.131.49.199" ,       //74
                                        "196.227.170-171.137" ,
                                        "196.227.170-171.137" ,
                                        "196.227.170-171.137" ,
                                        "196.227.170-171.137" ,
                                        "196.227.170-171.137" ,
                                        "196.227.170-171.137" , //80
                                        "196.227.172.137" ,
                                        "196.227.173.137" ,
                                        "196.227.169.137" ,
                                        "196.227.168.137" ,
                                        "196.227.170.137" ,
                                        "196.227.171.137" ,     //86
                                        "63.253.150-151.249" ,
                                        "63.253.150-151.249" ,
                                        "63.253.150-151.249" ,
                                        "63.253.150-151.249" ,
                                        "63.253.150-151.249" ,
                                        "63.253.150-151.249"    //92
                                    } ; 
                    
        String[] ranges2 = new String[]{"26.131.50.199",
                                        "26.131.51.199",
                                        "26.131.48.199",
                                        "26.131.47.199",
                                        "26.131.49.199",            //5
                                        "196.227.172.137",
                                        "196.227.173.137",
                                        "196.227.169.137",
                                        "196.227.168.137",
                                        "196.227.170.137",
                                        "196.227.171.137",          //11
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",
                                        "196.227.170-171.137",      //17
                                        "63.253.152-153.249",
                                        "63.253.153-154.249",
                                        "63.253.148-149.249",
                                        "63.253.147-148.249",
                                        "63.253.147-151.249",
                                        "63.253.147-150.249",       //23
                                        "26.131.50.22",
                                        "26.131.51.22",
                                        "26.131.48.22",
                                        "26.131.47.22",
                                        "26.131.49.22",             //28
                                        "196.227.172.189",
                                        "196.227.173.189",
                                        "196.227.169.189",
                                        "196.227.168.189",
                                        "196.227.170.189",
                                        "196.227.171.189",          //34
                                        "196.227.170-171.4",
                                        "196.227.170-171.4",
                                        "196.227.170-171.4",
                                        "196.227.170-171.4",
                                        "196.227.170-171.4",
                                        "196.227.170-171.4",        //40
                                        "63.253.152-153.122",
                                        "63.253.153-154.122",
                                        "63.253.148-149.122",
                                        "63.253.147-148.122",
                                        "63.253.147-151.122",
                                        "63.253.147-150.122",       //46
                                        "26.23.50.199",
                                        "26.23.51.199",
                                        "26.23.48.199",
                                        "26.23.47.199",
                                        "26.23.49.199",             //51
                                        "196.22.172.137",
                                        "196.22.173.137",
                                        "196.22.169.137",
                                        "196.22.168.137",
                                        "196.22.170.137",
                                        "196.22.171.137",            //57
                                        "196.22.170-171.137",
                                        "196.22.170-171.137",
                                        "196.22.170-171.137",
                                        "196.22.170-171.137",
                                        "196.22.170-171.137",
                                        "196.22.170-171.137",       //63
                                        "63.25.152-153.249",
                                        "63.25.153-154.249",
                                        "63.25.148-149.249",
                                        "63.25.147-148.249",
                                        "63.25.147-150.249",
                                        "63.25.147-151.249",        //69
                                        "2.131.50.199" ,
                                        "2.131.51.199" ,
                                        "2.131.48.199" ,
                                        "2.131.47.199" ,
                                        "2.131.49.199" ,            //74
                                        "19.227.172.137" ,
                                        "19.227.173.137" ,
                                        "19.227.169.137" ,
                                        "19.227.168.137" ,
                                        "19.227.170.137" ,
                                        "19.227.171.137" ,          //80
                                        "19.227.170-171.137" ,
                                        "19.227.170-171.137" ,
                                        "19.227.170-171.137" ,
                                        "19.227.170-171.137" ,
                                        "19.227.170-171.137" ,
                                        "19.227.170-171.137" ,      //86
                                        "6.253.152-153.249" ,
                                        "6.253.153-154.249" ,
                                        "6.253.148-149.249" ,
                                        "6.253.147-148.249" ,
                                        "6.253.147-150.249" ,
                                        "6.253.147-151.249"         //92
                                    } ;        
                                        
        int[] expected = new int[]{1,0,-1,0,0,          //5
                                   1,0,-1,0,0,0,        //11
                                   -1,0,1,0,0,0,        //17
                                   1,0,-1,0,0,0,        //23
                                   0,0,0,0,0,           //28
                                   0,0,0,0,0,0,         //34
                                   0,0,0,0,0,0,         //40
                                   0,0,0,0,0,0,         //46
                                   0,0,0,0,0,           //51
                                   0,0,0,0,0,0,         //57
                                   0,0,0,0,0,0,         //63
                                   0,0,0,0,0,0,         //69
                                   0,0,0,0,0,           //74
                                   0,0,0,0,0,0,         //80
                                   0,0,0,0,0,0,         //86
                                   0,0,0,0,0,0          //92
                                } ;        
        
        //Simply test is adjacent range returns
        //the expected value for each pair
        for( i=0 ; i<ranges1.length ; i++ ) {
            IPv4range range1 = new IPv4range() ;
            IPv4range range2 = new IPv4range() ;
            range1.parseAddDashNotation( ranges1[i] ) ;
            range2.parseAddDashNotation( ranges2[i] ) ;
            assertEquals( expected[i] , range1.isAdjacentRange( range2 ) ) ;
        }
        
    }   

    /**
     * Test of method methodName, in class IPv4range.
     */
    @Test
    public void testGetAllAddressesAsString() {
        System.out.println( "Basic Test --- IPv4range --- getAllAddressesAsString" ) ;
        //Case 1 - single address
        IPv4range range1 = new IPv4range( new IPv4address("58.35.212.146") ) ;
        String expected1 = "58.35.212.146 " ; 
        //Case 2 - range of addresses
        IPv4range range2 = new IPv4range() ;
        range2.parseAddDashNotation( "219.145.101.126-131" ) ;
        String expected2 = "219.145.101.126 219.145.101.127 219.145.101.128 "
                    + "219.145.101.129 219.145.101.130 219.145.101.131 " ;
        //Case 3 - non-contiguous addresses
        IPv4range range3 = new IPv4range( new IPv4address("36.164.200.182") ) ;
        range3.addAddressToRange( new IPv4address( "16.164.16.36" ) , false ) ;
        range3.addAddressToRange( new IPv4address( "226.1.228.172" ) , false ) ;
        String expected3 = "36.164.200.182 16.164.16.36 226.1.228.172 " ;
        //Check all three outputs
        //match the expected strings
        assertEquals( true , range1.getAllAddressesAsString().equals( expected1 ) ) ;
        assertEquals( true , range2.getAllAddressesAsString().equals( expected2 ) ) ;
        assertEquals( true , range3.getAllAddressesAsString().equals( expected3 ) ) ;
    }

    //Template
    /*
    /**
     * Test of method methodName, in class IPv4range.
     */
    /*
    @Test
    public void testMethodName() {
        System.out.println( "Basic Test --- IPv4range --- methodName" ) ;
    }
    */
}
