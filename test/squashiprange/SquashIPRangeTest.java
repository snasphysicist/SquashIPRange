/**
 * ****************************************************************
 *Squash IP Range is offered under the lesser GPL licence version 3
 *Please refer to the LICENSE file included at the top level
 *of the git repo for full information on this licence
 *Written by snasphysicist (Scott N A Smith)
 ******************************************************************
 */
package squashiprange;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author scott
 */
public class SquashIPRangeTest {
    
    public SquashIPRangeTest() {
    }

    /**
     * Test of matchesCondensedStar method, of class SquashIPRange
     */
    @Test
    public void testMatchesCondensedStar() {
        System.out.println( "Basic Test --- SquashIPRange --- matchesCondensedStar" ) ;
        String [] ranges = { "130.177.*" , "68.19.*.*" , "167.200.167.219" ,
                             "187.161.0.24-42" , "41.33.12-21.0-255" , "253.250.22.0/28" } ;
        boolean [] expected = { true , false , false , false , false , false } ;
        int i ;
        for( i=0 ; i<ranges.length ; i++ ) {
            assertEquals( SquashIPRange.matchesCompressedStar(ranges[i]) , expected[i] ) ;
        }
    }
    
    /**
     * Test of getAllAddressesAllRangesAsString method, of class SquashIPRange
     */
    @Test
    public void testGetAllAddressesAllRangesAsString() {
        
        System.out.println( "Basic Test --- SquashIPRange --- getAllAddressesAllRangesAsString" ) ;
        Integer i ;
        
        //Case 1, input ranges
        IPv4range range1_1 = new IPv4range() ;
        range1_1.parseAddDashNotation( "215.58.14.42-174" ) ;
        IPv4range range1_2 = new IPv4range() ;
        range1_2.parseAddDashNotation( "123.132.198.0-255" ) ;
        IPv4range[] ranges1 = { range1_1 , range1_2 } ;
        //Case 1, expected output
        String expected1 = "" ;
        for( i=42 ; i<=174 ; i++ ) {
            expected1 += "215.58.14." + i.toString() + " " ;
        }
        for( i=0 ; i<=255 ; i++ ) {
            expected1 += "123.132.198." + i.toString() + " " ;
        }
        
        //Case 2, input ranges
        IPv4range range2_1 = new IPv4range() ;
        range2_1.parseAddDashNotation( "215.32.6.185-227" ) ;
        IPv4range range2_2 = new IPv4range() ;
        range2_2.parseAddDashNotation( "27.74.239.8-16" ) ;
        IPv4range[] ranges2 = { range2_1 , range2_2 } ;
        String delimiter2 = "," ;
        //Case 1, expected output
        String expected2 = "" ;
        for( i=185 ; i<=227 ; i++ ) {
            expected2 += "215.32.6." + i.toString() + delimiter2 ;
        }
        for( i=8 ; i<=16 ; i++ ) {
            expected2 += "27.74.239." + i.toString() + delimiter2 ;
        }
        
        //Checking equality
        assertEquals( //1
                true , 
                expected1.equals( //2
                        SquashIPRange.getAllAddressesAllRangesAsString( ranges1 )
                ) //2
        ) ; //1
        assertEquals( //1
                true , 
                expected2.equals(  //2
                        SquashIPRange.getAllAddressesAllRangesAsString( //3
                                ranges2 ,
                                delimiter2 ) //3
                ) //2
        ) ; //1
    }
}
