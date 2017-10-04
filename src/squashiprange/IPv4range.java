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
    
    public IPv4range() {
        addressArray = new IPv4address[0] ;
    }
    
    /*public IPv4range( String rangeAsString ) {
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
    */
    
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
    
    public void parseAddStarNotation( String staripRange ) {
        int i ;
        IPv4address intmipAddress = new IPv4address( staripRange.substring(0,staripRange.length()-1) + "0" ) ;
        for(i=0;i<256;i++) {
            addressArray = this.appendToIPv4addressArray( addressArray , intmipAddress ) ;
            intmipAddress = intmipAddress.createCopy() ;
            intmipAddress.incrementAddress() ;
        }
    }
    
    //Parses a range of ip addresses given in the form a.b.c[-d].e[-f]
    //Adds each address in this range to the array in the IPv4range object
    //Also validates the range (-1 < a,b,c,d,e < 256, d<e )
    public void parseAddDashNotation( String dashipRange ) {
        Integer i, j ;
        Integer[] thirdSectorLimits = new Integer[2] ;
        Integer[] fourthSectorLimits = new Integer[2] ;
        String[] sectors ;
        boolean rangeValidated = true ;
        //We'll only use this for the split by sector functionality
        IPv4address sectorsplitter = new IPv4address(0L) ;
        
        //Use the IPv4address split by sector tool to get the four sectors
        sectors = sectorsplitter.splitBySector( dashipRange ) ;
        //Whether the third sector is a range or not the
        //minimum value of this sector will always be this
        thirdSectorLimits[0] = new Integer( sectors[2].split("-")[0] ) ;
        //The maximum value can either come from the number after the dash
        //or, if it is not a range, then it is simply the same as the minimum
        if( sectors[2].contains("-") ) {
            thirdSectorLimits[1] = new Integer( sectors[2].split("-")[1] ) ;
        } else {
            thirdSectorLimits[1] = thirdSectorLimits[0] ;
        }
        //Repeat this for the fourth sector, with the same logic
        fourthSectorLimits[0] = new Integer( sectors[3].split("-")[0] ) ;
        if( sectors[3].contains("-") ) {
            fourthSectorLimits[1] = new Integer( sectors[3].split("-")[1] ) ;
        } else {
            fourthSectorLimits[1] = fourthSectorLimits[0] ;
        }
        
        //Validate input
        if( ( new Integer( sectors[0] ) < 0 ) || ( new Integer( sectors[0] ) > 255 )
                || ( new Integer( sectors[1] ) < 0 ) || ( new Integer( sectors[1] ) > 255 )
                || ( thirdSectorLimits[0] < 0 ) || ( thirdSectorLimits[1] > 255 )
                || ( fourthSectorLimits[0] < 0 ) || ( fourthSectorLimits[1] > 255 )
                || ( thirdSectorLimits[1] < thirdSectorLimits[0] )
                || ( fourthSectorLimits[1] < fourthSectorLimits[0] ) ) {
            rangeValidated = false ;
        }
        
        //Add addresses
        for(i=thirdSectorLimits[0];i<=thirdSectorLimits[1];i++) {
            for(j=fourthSectorLimits[0];j<=fourthSectorLimits[1];j++) {
                System.out.println( i.toString() + " " + j.toString() ) ;
                addressArray = this.appendToIPv4addressArray( addressArray , new IPv4address( sectors[0] + "." + sectors[1] + "." + i.toString() + "." + j.toString() ) ) ;
            }
        }
        
        for(i=0;i<addressArray.length;i++) {
            System.out.println( "##" + addressArray[i].getIPAsString() ) ;
        }
    }
  
    public void parseAddSlashNotation( String slashipRange ) {
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
