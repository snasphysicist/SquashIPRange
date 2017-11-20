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
public class IPv4addressTest {
    
    public IPv4addressTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of ipToNumerical method, of class IPv4address.
     */
    @Test
    public void testIpToNumerical() {
        System.out.println("Basic Test --- IPv4address --- ipToNumerical");
        String ipAsString = "10.13.16.18";
        IPv4address instance = new IPv4address( ipAsString ) ;
        Long expResult = 168628242L ;
        Long result = instance.ipToNumerical( ipAsString );
        assertEquals(expResult, result);
    }

    /**
     * Test of ipToString method, of class IPv4address.
     */
    @Test
    public void testIpToString() {
        System.out.println("Basic Test --- IPv4address --- ipToString");
        Long ipAsNumerical = 3564897623L ;
        IPv4address instance = new IPv4address( ipAsNumerical ) ;
        String expResult = "212.124.5.87";
        String result = instance.ipToString(ipAsNumerical);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIPAsString method, of class IPv4address.
     */
    @Test
    public void testGetIPAsString() {
        System.out.println("Basic Test --- IPv4address --- getIPAsString") ;
        String expResult = "57.185.99.109" ;
        IPv4address instance = new IPv4address( expResult ) ;
        String result = instance.getIPAsString() ;
        assertEquals(expResult, result) ;
    }

    /**
     * Test of getIPAsNumber method, of class IPv4address.
     */
    @Test
    public void testGetIPAsNumber() {
        System.out.println("Basic Test --- IPv4address --- getIPAsNumber") ;
        Long expResult = 1766710849L ;
        IPv4address instance = new IPv4address( expResult ) ;
        Long result = instance.getIPAsNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of incrementAddress method, of class IPv4address.
     */
    @Test
    public void testIncrementAddress() {
        System.out.println("Basic Test --- IPv4address --- incrementAddress");
        IPv4address instance = new IPv4address( "185.44.39.10" ) ;
        IPv4address result = instance.incrementAddress() ;
        assertEquals( true , result.equals( new IPv4address( "185.44.39.11" ) ) ) ;
    }

    /**
     * Test of decrementAddress method, of class IPv4address.
     */
    @Test
    public void testDecrementAddress() {
        System.out.println("Basic Test --- IPv4address --- decrementAddress");
        IPv4address instance = new IPv4address( "92.187.232.98" ) ;
        IPv4address result = instance.decrementAddress() ;
        assertEquals( true , result.equals( new IPv4address( "92.187.232.97" ) ) ) ;
    }

    /**
     * Test of splitBySector method, of class IPv4address.
     */
    @Test
    public void testSplitBySector() {
        System.out.println("Basic Test --- IPv4address --- splitBySector") ;
        String fullip = "147.5.20.238" ;
        IPv4address instance = new IPv4address( fullip ) ;
        String[] expResult = new String[]{ "147" , "5" , "20" , "238" } ;
        String[] result = instance.splitBySector(fullip) ;
        assertArrayEquals(expResult, result) ;
    }

    /**
     * Test of getSectorAsString method, of class IPv4address.
     */
    @Test
    public void testGetSectorAsString() {
        System.out.println("Basic Test --- IPv4address --- getSectorAsString");
        int i ;
        IPv4address instance = new IPv4address( "223.62.11.158" ) ;
        String[] expResult = new String[]{ "223" , "62" , "11" , "158" } ;
        for( i=1 ; i<5 ; i++ ) {
            assertEquals( instance.getSectorAsString( i ) , expResult[i-1] ) ;
        }
    }

    /**
     * Test of isAdjacentAddress method, of class IPv4address.
     */
    @Test
    public void testIsAdjacentAddress() {
        System.out.println("Basic Test --- IPv4address --- isAdjacentAddress");
        IPv4address ipAddress1 = new IPv4address( "94.47.126.252" ) ;
        IPv4address ipAddress2 = new IPv4address( "94.47.126.253" ) ;
        IPv4address ipAddress3 = new IPv4address( "94.47.126.254" ) ;
        assertEquals( true , ipAddress1.isAdjacentAddress( ipAddress2 ) ) ;
        assertEquals( true , ipAddress2.isAdjacentAddress( ipAddress3 ) ) ;
        assertEquals( false , ipAddress1.isAdjacentAddress( ipAddress3 ) ) ;
    }

    /**
     * Test of createCopy method, of class IPv4address.
     */
    @Test
    public void testCreateCopy() {
        System.out.println("Basic Test --- IPv4address --- createCopy") ;
        IPv4address expResult = new IPv4address( "124.120.32.70" ) ;
        IPv4address result = expResult.createCopy() ;
        //Since the only variables stored by IPv4address are
        //the IP address as a string and as a number, if these
        //two match then we assume the copy was created correctly
        assertEquals( expResult.getIPAsNumber() , result.getIPAsNumber() ) ;
        assertEquals( expResult.getIPAsString() , result.getIPAsString() ) ; 
    }

    /**
     * Test of equals method, of class IPv4address.
     */
    @Test
    public void testEquals() {
        System.out.println("Basic Test --- IPv4address --- equals");
        //An IP address should equal itself
        //The rest is self explanatory
        IPv4address ipAddress1 = new IPv4address( "103.17.63.181" ) ;
        IPv4address ipAddress2 = new IPv4address( "103.17.63.181" ) ;
        IPv4address ipAddress3 = new IPv4address( "105.107.151.42" ) ;
        assertEquals( true , ipAddress1.equals( ipAddress1 ) ) ;
        assertEquals( true , ipAddress1.equals( ipAddress2 ) ) ;
        assertEquals( false , ipAddress1.equals( ipAddress3 ) ) ;
        assertEquals( true , ipAddress2.equals( ipAddress1 ) ) ;
        assertEquals( true , ipAddress2.equals( ipAddress2 ) ) ;
        assertEquals( false , ipAddress2.equals( ipAddress3 ) ) ;
        assertEquals( false , ipAddress3.equals( ipAddress1 ) ) ;
        assertEquals( false , ipAddress3.equals( ipAddress2 ) ) ;
        assertEquals( true , ipAddress3.equals(ipAddress3 ) ) ;
    }

    /**
     * Test of equalsFirstOctet method, of class IPv4address.
     */
    @Test
    public void testEqualsFirstOctet() {
        System.out.println("Basic Test --- IPv4address --- equalsFirstOctet");
        //The outcome of this method should be true
        //only when the first octets match, false
        //only when they do not match and should
        //be independent of the remaining three octets
        IPv4address ipAddress1 = new IPv4address( "242.220.41.241" ) ;
        IPv4address ipAddress2 = new IPv4address( "242.220.41.241" ) ;
        IPv4address ipAddress3 = new IPv4address( "242.233.41.241" ) ;
        IPv4address ipAddress4 = new IPv4address( "242.220.56.241" ) ;
        IPv4address ipAddress5 = new IPv4address( "242.220.41.48" ) ;
        IPv4address ipAddress6 = new IPv4address( "242.233.56.241" ) ;
        IPv4address ipAddress7 = new IPv4address( "242.233.41.48" ) ;
        IPv4address ipAddress8 = new IPv4address( "242.220.56.48" ) ;
        IPv4address ipAddress9 = new IPv4address( "242.233.56.48" ) ;
        IPv4address ipAddress10 = new IPv4address( "240.220.41.241" ) ;
        IPv4address ipAddress11 = new IPv4address( "240.233.41.241" ) ;
        IPv4address ipAddress12 = new IPv4address( "240.220.56.241" ) ;
        IPv4address ipAddress13 = new IPv4address( "240.220.41.48" ) ;
        IPv4address ipAddress14 = new IPv4address( "240.233.56.241" ) ;
        IPv4address ipAddress15 = new IPv4address( "240.233.41.48" ) ;
        IPv4address ipAddress16 = new IPv4address( "240.220.56.48" ) ;
        IPv4address ipAddress17 = new IPv4address( "240.233.56.48" ) ;
        assertEquals( true , ipAddress1.equalsFirstOctet( ipAddress1 ) ) ;
        assertEquals( true , ipAddress1.equalsFirstOctet( ipAddress2 ) ) ;
        assertEquals( true , ipAddress1.equalsFirstOctet( ipAddress3 ) ) ;
        assertEquals( true , ipAddress1.equalsFirstOctet( ipAddress4 ) ) ;
        assertEquals( true , ipAddress1.equalsFirstOctet( ipAddress5 ) ) ;
        assertEquals( true , ipAddress1.equalsFirstOctet( ipAddress6 ) ) ;
        assertEquals( true , ipAddress1.equalsFirstOctet( ipAddress7 ) ) ;
        assertEquals( true , ipAddress1.equalsFirstOctet( ipAddress8 ) ) ;
        assertEquals( true , ipAddress1.equalsFirstOctet( ipAddress9 ) ) ;
        assertEquals( false , ipAddress1.equalsFirstOctet( ipAddress10 ) ) ;
        assertEquals( false , ipAddress1.equalsFirstOctet( ipAddress11 ) ) ;
        assertEquals( false , ipAddress1.equalsFirstOctet( ipAddress12 ) ) ;
        assertEquals( false , ipAddress1.equalsFirstOctet( ipAddress13 ) ) ;
        assertEquals( false , ipAddress1.equalsFirstOctet( ipAddress14 ) ) ;
        assertEquals( false , ipAddress1.equalsFirstOctet( ipAddress15 ) ) ;
        assertEquals( false , ipAddress1.equalsFirstOctet( ipAddress16 ) ) ;
        assertEquals( false , ipAddress1.equalsFirstOctet( ipAddress17 ) ) ;
    }

    /**
     * Test of equalsSecondOctet method, of class IPv4address.
     */
    @Test
    public void testEqualsSecondOctet() {
        System.out.println("Basic Test --- IPv4address --- equalsSecondOctet");
        //The outcome of this method should be true
        //only when the second octets match, false
        //only when they do not match and should
        //be independent of the remaining three octets
        IPv4address ipAddress1 = new IPv4address( "242.220.41.241" ) ;
        IPv4address ipAddress2 = new IPv4address( "242.220.41.241" ) ;
        IPv4address ipAddress3 = new IPv4address( "242.233.41.241" ) ;
        IPv4address ipAddress4 = new IPv4address( "242.220.56.241" ) ;
        IPv4address ipAddress5 = new IPv4address( "242.220.41.48" ) ;
        IPv4address ipAddress6 = new IPv4address( "242.233.56.241" ) ;
        IPv4address ipAddress7 = new IPv4address( "242.233.41.48" ) ;
        IPv4address ipAddress8 = new IPv4address( "242.220.56.48" ) ;
        IPv4address ipAddress9 = new IPv4address( "242.233.56.48" ) ;
        IPv4address ipAddress10 = new IPv4address( "240.220.41.241" ) ;
        IPv4address ipAddress11 = new IPv4address( "240.233.41.241" ) ;
        IPv4address ipAddress12 = new IPv4address( "240.220.56.241" ) ;
        IPv4address ipAddress13 = new IPv4address( "240.220.41.48" ) ;
        IPv4address ipAddress14 = new IPv4address( "240.233.56.241" ) ;
        IPv4address ipAddress15 = new IPv4address( "240.233.41.48" ) ;
        IPv4address ipAddress16 = new IPv4address( "240.220.56.48" ) ;
        IPv4address ipAddress17 = new IPv4address( "240.233.56.48" ) ;
        assertEquals( true , ipAddress1.equalsSecondOctet( ipAddress1 ) ) ;
        assertEquals( true , ipAddress1.equalsSecondOctet( ipAddress2 ) ) ;
        assertEquals( false , ipAddress1.equalsSecondOctet( ipAddress3 ) ) ;
        assertEquals( true , ipAddress1.equalsSecondOctet( ipAddress4 ) ) ;
        assertEquals( true , ipAddress1.equalsSecondOctet( ipAddress5 ) ) ;
        assertEquals( false , ipAddress1.equalsSecondOctet( ipAddress6 ) ) ;
        assertEquals( false , ipAddress1.equalsSecondOctet( ipAddress7 ) ) ;
        assertEquals( true , ipAddress1.equalsSecondOctet( ipAddress8 ) ) ;
        assertEquals( false , ipAddress1.equalsSecondOctet( ipAddress9 ) ) ;
        assertEquals( true , ipAddress1.equalsSecondOctet( ipAddress10 ) ) ;
        assertEquals( false , ipAddress1.equalsSecondOctet( ipAddress11 ) ) ;
        assertEquals( true , ipAddress1.equalsSecondOctet( ipAddress12 ) ) ;
        assertEquals( true , ipAddress1.equalsSecondOctet( ipAddress13 ) ) ;
        assertEquals( false , ipAddress1.equalsSecondOctet( ipAddress14 ) ) ;
        assertEquals( false , ipAddress1.equalsSecondOctet( ipAddress15 ) ) ;
        assertEquals( true , ipAddress1.equalsSecondOctet( ipAddress16 ) ) ;
        assertEquals( false , ipAddress1.equalsSecondOctet( ipAddress17 ) ) ;
    }

    /**
     * Test of equalsThirdOctet method, of class IPv4address.
     */
    @Test
    public void testEqualsThirdOctet() {
        System.out.println("Basic Test --- IPv4address --- equalsThirdOctet");
        //The outcome of this method should be true
        //only when the third octets match, false
        //only when they do not match and should
        //be independent of the remaining three octets
        IPv4address ipAddress1 = new IPv4address( "242.220.41.241" ) ;
        IPv4address ipAddress2 = new IPv4address( "242.220.41.241" ) ;
        IPv4address ipAddress3 = new IPv4address( "242.233.41.241" ) ;
        IPv4address ipAddress4 = new IPv4address( "242.220.56.241" ) ;
        IPv4address ipAddress5 = new IPv4address( "242.220.41.48" ) ;
        IPv4address ipAddress6 = new IPv4address( "242.233.56.241" ) ;
        IPv4address ipAddress7 = new IPv4address( "242.233.41.48" ) ;
        IPv4address ipAddress8 = new IPv4address( "242.220.56.48" ) ;
        IPv4address ipAddress9 = new IPv4address( "242.233.56.48" ) ;
        IPv4address ipAddress10 = new IPv4address( "240.220.41.241" ) ;
        IPv4address ipAddress11 = new IPv4address( "240.233.41.241" ) ;
        IPv4address ipAddress12 = new IPv4address( "240.220.56.241" ) ;
        IPv4address ipAddress13 = new IPv4address( "240.220.41.48" ) ;
        IPv4address ipAddress14 = new IPv4address( "240.233.56.241" ) ;
        IPv4address ipAddress15 = new IPv4address( "240.233.41.48" ) ;
        IPv4address ipAddress16 = new IPv4address( "240.220.56.48" ) ;
        IPv4address ipAddress17 = new IPv4address( "240.233.56.48" ) ;
        assertEquals( true , ipAddress1.equalsThirdOctet( ipAddress1 ) ) ;
        assertEquals( true , ipAddress1.equalsThirdOctet( ipAddress2 ) ) ;
        assertEquals( true , ipAddress1.equalsThirdOctet( ipAddress3 ) ) ;
        assertEquals( false , ipAddress1.equalsThirdOctet( ipAddress4 ) ) ;
        assertEquals( true , ipAddress1.equalsThirdOctet( ipAddress5 ) ) ;
        assertEquals( false , ipAddress1.equalsThirdOctet( ipAddress6 ) ) ;
        assertEquals( true , ipAddress1.equalsThirdOctet( ipAddress7 ) ) ;
        assertEquals( false , ipAddress1.equalsThirdOctet( ipAddress8 ) ) ;
        assertEquals( false , ipAddress1.equalsThirdOctet( ipAddress9 ) ) ;
        assertEquals( true , ipAddress1.equalsThirdOctet( ipAddress10 ) ) ;
        assertEquals( true , ipAddress1.equalsThirdOctet( ipAddress11 ) ) ;
        assertEquals( false , ipAddress1.equalsThirdOctet( ipAddress12 ) ) ;
        assertEquals( true , ipAddress1.equalsThirdOctet( ipAddress13 ) ) ;
        assertEquals( false , ipAddress1.equalsThirdOctet( ipAddress14 ) ) ;
        assertEquals( true , ipAddress1.equalsThirdOctet( ipAddress15 ) ) ;
        assertEquals( false , ipAddress1.equalsThirdOctet( ipAddress16 ) ) ;
        assertEquals( false , ipAddress1.equalsThirdOctet( ipAddress17 ) ) ;
    }

    /**
     * Test of equalsFourthOctet method, of class IPv4address.
     */
    @Test
    public void testEqualsFourthOctet() {
        //The outcome of this method should be true
        //only when the fourth octets match, false
        //only when they do not match and should
        //be independent of the remaining three octets
        System.out.println("Basic Test --- IPv4address --- equalsFourthOctet");
        IPv4address ipAddress1 = new IPv4address( "242.220.41.241" ) ;
        IPv4address ipAddress2 = new IPv4address( "242.220.41.241" ) ;
        IPv4address ipAddress3 = new IPv4address( "242.233.41.241" ) ;
        IPv4address ipAddress4 = new IPv4address( "242.220.56.241" ) ;
        IPv4address ipAddress5 = new IPv4address( "242.220.41.48" ) ;
        IPv4address ipAddress6 = new IPv4address( "242.233.56.241" ) ;
        IPv4address ipAddress7 = new IPv4address( "242.233.41.48" ) ;
        IPv4address ipAddress8 = new IPv4address( "242.220.56.48" ) ;
        IPv4address ipAddress9 = new IPv4address( "242.233.56.48" ) ;
        IPv4address ipAddress10 = new IPv4address( "240.220.41.241" ) ;
        IPv4address ipAddress11 = new IPv4address( "240.233.41.241" ) ;
        IPv4address ipAddress12 = new IPv4address( "240.220.56.241" ) ;
        IPv4address ipAddress13 = new IPv4address( "240.220.41.48" ) ;
        IPv4address ipAddress14 = new IPv4address( "240.233.56.241" ) ;
        IPv4address ipAddress15 = new IPv4address( "240.233.41.48" ) ;
        IPv4address ipAddress16 = new IPv4address( "240.220.56.48" ) ;
        IPv4address ipAddress17 = new IPv4address( "240.233.56.48" ) ;
        assertEquals( true , ipAddress1.equalsFourthOctet( ipAddress1 ) ) ;
        assertEquals( true , ipAddress1.equalsFourthOctet( ipAddress2 ) ) ;
        assertEquals( true , ipAddress1.equalsFourthOctet( ipAddress3 ) ) ;
        assertEquals( true , ipAddress1.equalsFourthOctet( ipAddress4 ) ) ;
        assertEquals( false , ipAddress1.equalsFourthOctet( ipAddress5 ) ) ;
        assertEquals( true , ipAddress1.equalsFourthOctet( ipAddress6 ) ) ;
        assertEquals( false , ipAddress1.equalsFourthOctet( ipAddress7 ) ) ;
        assertEquals( false , ipAddress1.equalsFourthOctet( ipAddress8 ) ) ;
        assertEquals( false , ipAddress1.equalsFourthOctet( ipAddress9 ) ) ;
        assertEquals( true , ipAddress1.equalsFourthOctet( ipAddress10 ) ) ;
        assertEquals( true , ipAddress1.equalsFourthOctet( ipAddress11 ) ) ;
        assertEquals( true , ipAddress1.equalsFourthOctet( ipAddress12 ) ) ;
        assertEquals( false , ipAddress1.equalsFourthOctet( ipAddress13 ) ) ;
        assertEquals( true , ipAddress1.equalsFourthOctet( ipAddress14 ) ) ;
        assertEquals( false , ipAddress1.equalsFourthOctet( ipAddress15 ) ) ;
        assertEquals( false , ipAddress1.equalsFourthOctet( ipAddress16 ) ) ;
        assertEquals( false , ipAddress1.equalsFourthOctet( ipAddress17 ) ) ;
    }
        
}
