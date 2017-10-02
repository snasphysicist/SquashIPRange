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
        
        String sipadd1 = "10.12.13.14/28" ;
        IPv4range iprange1 = new IPv4range( sipadd1 ) ;
        System.out.println( iprange1.getAddressFromRange(0).getIPAsString() ) ;
        System.out.println( iprange1.getAddressFromRange(1).getIPAsString() ) ;
        System.out.println( iprange1.getAddressFromRange(2).getIPAsString() ) ;
        System.out.println( iprange1.getAddressFromRange(3).getIPAsString() ) ;
        System.out.println( iprange1.getAddressFromRange(4).getIPAsString() ) ;
        System.out.println( iprange1.getAddressFromRange(5).getIPAsString() ) ;
        
    }
}