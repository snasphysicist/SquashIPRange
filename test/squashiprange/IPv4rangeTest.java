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
    
    
}
