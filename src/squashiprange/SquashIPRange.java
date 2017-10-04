/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package squashiprange;

import java.util.Arrays;

/**
 *
 * A tool to take a set of ip addresses and ip ranges 
 * and squash them down into a smaller set of ranges
 * if possible
 * 
 * @author snasphysicist
 */
public class SquashIPRange {

    /**
     * @param args none
     */
    public static void main(String[] args) {
        
        //Testing
        IPv4address splitTool = new IPv4address(0L) ;
        String[] sectors = new String[4] ;
        String stringipRange = "212.1.58-59.5" ;
        IPv4range testRange = new IPv4range() ;
        Integer sector3min ;
        Integer sector3max ;
        Integer i ;
        
        try {
            stringipRange = stringipRange.replaceAll("\\*", "0-255") ;
            sectors = splitTool.splitBySector( stringipRange ) ;
            if ( sectors[3].contains( "/" ) ) {
                testRange.parseAddSlashNotation( stringipRange ) ;
            } else if ( sectors[2].contains( "-" ) ) {
                    sector3min = new Integer( sectors[2].split( "-" )[0] ) ;
                    sector3max = new Integer( sectors[2].split( "-" )[1] ) ;
                    for(i=sector3min;i<=sector3max;i++) {
                        testRange.parseAddDashNotation( sectors[0] + "." + sectors[1] + "." + i.toString() + "." + sectors[3] ) ;
                    }
                } else if( sectors[3].contains( "-" ) ) {
                    testRange.parseAddDashNotation( stringipRange ) ;
                }
            System.out.println( testRange.getAddressFromRange(0).getIPAsString() ) ;
        }
        catch ( Exception e ) {
            System.out.println( "Failed to parse range" ) ;
            System.out.println( e.toString() ) ;
            e.printStackTrace() ;
        }
        
    }
}