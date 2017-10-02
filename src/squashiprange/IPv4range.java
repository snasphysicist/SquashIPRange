/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package squashiprange;

/**
 * 
 * This class represents a range of ipv4 address
 * The addresses are stored in an array as numbers
 * The range will be stored as a human readable string
 * 
 * @author snasphysicist
 */
public class IPv4range {
    
    private IPv4address[] addressArray = new IPv4address[0];
    private String humanreadableRange ;
    
    //Takes an array of IPv4address objects and another IPv4address 
    //and appends the latter at the end of the former and returns the resulting array
    public IPv4address[] appendToIPv4addressArray( IPv4address[] inarray , IPv4address inobject ) {
        int i ;
        IPv4address[] intmarray = new IPv4address[inarray.length+1] ;
        for(i=0;i<inarray.length;i++) {
            intmarray[i] = inarray[i] ;
        }
        intmarray[inarray.length] = inobject ;
        return intmarray ;
    }
    
    //Constructor starting from a single IPv4address
    public IPv4range( IPv4address inipAddress ) {
        this.addAddressToRange( inipAddress ) ;
    }
    
    //Constructor starting from an array of IPv4addresses
    public IPv4range( IPv4address[] inipAddresses ) {
        int i ;
        for(i=0;i<inipAddresses.length;i++){
            this.addAddressToRange( inipAddresses[i] ) ;
        }
    }
    
    public IPv4range( String rangeAsString ) {
        if( rangeAsString.contains( "*" ) ) {
            this.parseAddStarNotation( rangeAsString );
        } else {
            if( rangeAsString.contains( "-" ) ) {
                this.parseAddDashNotation( rangeAsString ) ;
            } else {
                if ( rangeAsString.contains( "/" ) ) {
                    this.parseAddSlashNotation( rangeAsString );
                } else {
                    this.addAddressToRange( new IPv4address( rangeAsString ) ) ;
                }
            }
        }
    }
    
    //Returns all addresses in the range as an array of IPv4addresses
    public IPv4address[] getRangeAsAddresses() {
        return addressArray ;
    }
    
    //Returns a single IPv4address from the range based on the index input
    public IPv4address getAddressFromRange( int i ) {
        return addressArray[i] ;
    }
    
    //Adds an IPv4address to the range
    public void addAddressToRange( IPv4address inipAddress ) {
        addressArray = this.appendToIPv4addressArray( addressArray , inipAddress ) ;
    }
    
    private void parseAddStarNotation( String staripRange ) {
        int i ;
        IPv4address intmipAddress = new IPv4address( staripRange.substring(0,staripRange.length()-1) + "0" ) ;
        for(i=0;i<256;i++) {
            addressArray = this.appendToIPv4addressArray( addressArray , intmipAddress ) ;
            intmipAddress = intmipAddress.createCopy() ;
            intmipAddress.incrementAddress() ;
        }
    }
    
    private void parseAddDashNotation( String dashipRange ) {
        int i ;
        Integer firstip , lastip ;
        IPv4address intmipAddress ;
        //Double slash like \\. \\- needed because split uses regex
        firstip = new Integer( dashipRange.split("\\.")[3].split("\\-")[0] ) ;
        lastip = new Integer( dashipRange.split("\\.")[3].split("\\-")[1] ) ;
        System.out.println( firstip.toString() + " " + lastip.toString() );
        intmipAddress = new IPv4address( dashipRange.split("\\.")[0] + "." + dashipRange.split("\\.")[1] + "." + dashipRange.split("\\.")[2] + "." + firstip.toString()) ;
        
        for(i=0;i<=(lastip-firstip);i++) {
            System.out.println( "#" + intmipAddress.getIPAsString() ) ;
            addressArray = this.appendToIPv4addressArray( addressArray , intmipAddress ) ;
            intmipAddress = intmipAddress.createCopy() ;
            intmipAddress.incrementAddress() ;
        }
        
        for(i=0;i<addressArray.length;i++) {
            System.out.println( "##" + addressArray[i].getIPAsString() ) ;
        }
    }
    
    private void parseAddSlashNotation( String slashipRange ) {
        Integer subnetmaskbits = new Integer( slashipRange.split("/")[1] ) ;
        subnetmaskbits = 32 - subnetmaskbits ;
        Integer numberips = new Integer( (int) Math.pow(2,subnetmaskbits) ) ;
        IPv4address intmipAddress = new IPv4address( slashipRange.split("/")[0] ) ;
        int i ;
        for(i=0;i<numberips;i++) {
            addressArray = this.appendToIPv4addressArray( addressArray , intmipAddress ) ;
            intmipAddress = intmipAddress.createCopy() ;
            intmipAddress.incrementAddress() ;
        }
    }
    
}
