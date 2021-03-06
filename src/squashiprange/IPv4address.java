/******************************************************************
*Squash IP Range is offered under the lesser GPL licence version 3
*Please refer to the LICENSE file included at the top level
*of the git repo for full information on this licence
*Written by snasphysicist (Scott N A Smith)
*******************************************************************/
package squashiprange;

/**
 *
 * This class represents a single ipv4 address
 * It is stored and can be returned in both human readable and binary forms
 * 
 * @author snasphysicist
 */
public class IPv4address {
    
    //We're assuming human readable ip v4 addresses
    //in the form a.b.c.d where 0 <= a,b,c,d <=255
    private Long ipNumerical ;
    
    //Takes a human readable ip address as a string
    //and converts this into the numerical form
    public Long ipToNumerical( String ipAsString ) {
        int i ;
        String[] stringsectors ;
        //We'll split the a.b.c.d into the four sectors a, b, c, d
        Long[] longsectors = new Long[4] ;
        //Split ip address into an array of sectors, as strings
        stringsectors = splitBySector( ipAsString ) ;
        //Convert these strings to longs
        for(i=0;i<longsectors.length;i++) {
            longsectors[i] = new Long( stringsectors[i] ) ;
        }
        //Bitshifting the three leftmost sectors into their correct positions
        longsectors[0] = longsectors[0] << 24 ;
        longsectors[1] = longsectors[1] << 16 ;
        longsectors[2] = longsectors[2] << 8 ;
        return longsectors[0] + longsectors[1] + longsectors[2] + longsectors[3] ;
    }
    
    //Takes a numerical ip address and converts this
    //into human readable form
    public String ipToString( Long ipAsNumerical ) {
        Long sector1 ;
        Long sector2 ;
        Long sector3 ;
        Long sector4 ;
        //We use these four binary masks to extract the bits
        //that correspond to each of the four sectors
        //in the human readable ip address
        Long mask1 = 0b11111111000000000000000000000000L ;
        Long mask2 = 0b00000000111111110000000000000000L ;
        Long mask3 = 0b00000000000000001111111100000000L ;
        Long mask4 = 0b00000000000000000000000011111111L ;
        //We need to bitshift the results of the binary AND
        //in order to bring the numbers into a range from 0 to 255
        sector1 = (mask1 & ipAsNumerical) >> 24 ;
        sector2 = (mask2 & ipAsNumerical) >> 16 ;
        sector3 = (mask3 & ipAsNumerical) >> 8 ;
        sector4 = mask4 & ipAsNumerical ;
        //Add in the full stops here
        return sector1.toString() + "." + sector2.toString() + "." + sector3.toString() + "." + sector4.toString() ;
    }
    
    //Constructor taking a human readable ip address
    public IPv4address( String inipString ) {
        setIPAsString( inipString ) ;
    }
    
    //Constructor taking a numerical ip address
    public IPv4address( Long inipNumerical ) {
        setIPAsNumber(inipNumerical) ;
    }
    
    //Returns the ip in human readable format
    public String getIPAsString() {
        return ipToString( ipNumerical ) ;
    }
    
    //Returns the ip in numerical format
    public Long getIPAsNumber() {
        return ipNumerical ;
    }
    
    //Takes a human readable ip address and sets both the string
    //and numerical forms based on this
    public void setIPAsString( String inipString ) {
        ipNumerical = ipToNumerical( inipString ) ;
    }
    
    //Takes a numerical ip address and sets both the string
    //and numerical forms based on this
    public void setIPAsNumber( Long inipNumerical ) {
        ipNumerical = inipNumerical ;
    }
    
    //Increases the ip address by one
    public IPv4address incrementAddress() {
        ipNumerical++ ;
        return this ;
    }
    
    //Decreases the ip address by one
    public IPv4address decrementAddress() {
        ipNumerical-- ;
        return this ;
    }
    
    //Splits a human readable ip address into its four sectors
    //Returns the sectors as an array of strings
    public String[] splitBySector( String fullip ) {
        String[] sectors = new String[]{"","","",""} ;
        int i ;
        int j = 0 ;
        //Until we have extracted the third sector (c)
        //Remember that a,b,c,d can't be less than 0
        //So this cannot be true if the third sector has been found
        while ( sectors[2].equals("") ) {
            i = 0 ;
            //Walk through the string until we find a full stop
            while ( !( fullip.substring(i,i+1).equals(".") ) ) {
                i++ ;
            }
            //Convert the substring up to this full stop into a Long
            //j keeps track of the number of sectors found
            sectors[j] = fullip.substring(0,i) ;
            //Chop off the sector we just extracted, and the full stop at its end
            fullip = fullip.substring( i+1 ) ;
            j++ ;
        }
        //Once we've done this four times, what remains must be the fourth sector only
        sectors[j] = fullip ;
        return sectors ;
    }
    
    //Returns a single sector of the ip address as a string
    //We'll assume people will refer to a.b.c.d as sectors
    //1.2.3.4, so we need to subtract 1
    public String getSectorAsString( int sectorNumber ) {
        return this.splitBySector( getIPAsString() )[sectorNumber-1] ;
    }
    
    //Returns a single sector of the ip address as a number
    //We'll assume people will refer to a.b.c.d as sectors
    //1.2.3.4, so we need to subtract 1
    public Integer getSectorAsNumber( int sectorNumber ) {
        return new Integer( this.splitBySector( getIPAsString() )[sectorNumber-1] ) ;
    }
    
    //Method to decide if the ip address as the argument
    //is "adjacent" to this ip address
    //Adjacency means in this context that the numerical
    //values for the address differ by +-1
    //and the third sector has the same value
    public Boolean isAdjacentAddress( IPv4address inipAddress ) {
        Boolean adjacent = false ;
        if( Math.abs(inipAddress.getIPAsNumber()-this.ipNumerical) == 1 ) {
            adjacent = true ;
        }
        //Check they have the same third sector here
        if( !this.getSectorAsString( 3 ).equals( inipAddress.getSectorAsString(3) ) ) {
            adjacent = false ;
        }
        return adjacent ;
    }
    
    //Returns a new copy of the current IPv4address object
    public IPv4address createCopy() {
        IPv4address intmipAddress = new IPv4address( this.ipNumerical ) ;
        return intmipAddress ;
    }
    
    //== operator for IPv4addresses
    //If the numerical value of the ip address of the input IPv4address
    //equals that of this IPv4address, this returns true
    //otherwise it returns false
    public boolean equals( IPv4address inipAddress ) {
        return ( inipAddress.getIPAsNumber().equals( this.ipNumerical ) ) ;
    }
    
    //Additional "equality" operators that only
    //apply to each octet
    public boolean equalsFirstOctet( IPv4address inipAddress ) {
        return ( this.getSectorAsString(1).equals( inipAddress.getSectorAsString(1) ) ) ;
    }
    
    public boolean equalsSecondOctet( IPv4address inipAddress ) {
        return ( this.getSectorAsString(2).equals( inipAddress.getSectorAsString(2) ) ) ;
    }
    
    public boolean equalsThirdOctet( IPv4address inipAddress ) {
        return ( this.getSectorAsString(3).equals( inipAddress.getSectorAsString(3) ) ) ;
    }
    
    public boolean equalsFourthOctet( IPv4address inipAddress ) {
        return ( this.getSectorAsString(4).equals( inipAddress.getSectorAsString(4) ) ) ;
    }
    
    //Masks this IPv4address with the input bitmask 
    //and returns the result as an IPv4address
    public IPv4address maskAddress( Long bitmask ) {
        return new IPv4address( ipNumerical & bitmask  ) ;
    }
    
}
