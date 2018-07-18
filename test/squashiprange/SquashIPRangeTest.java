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
}
