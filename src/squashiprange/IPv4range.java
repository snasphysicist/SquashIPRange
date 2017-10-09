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
    
    //Takes an array of IPv4addresses and an integer which is an index
    //in the array and returns an array with the entry at that index removed
    public IPv4address[] popFromIPv4addressArray( IPv4address[] inArray , int toPop ) {
        int i ;
        IPv4address[] intmArray = new IPv4address[inArray.length-1] ;
        for( i=0 ; i<toPop ; i++ ) {
            intmArray[i] = inArray[i] ;
        }
        for( i=toPop ; i<intmArray.length ; i++ ) {
            intmArray[i] = inArray[i+1] ;
        }
        return intmArray ;
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
    
    //Takes an ip address range written in string format with star notation
    //as an input and parses this to add each address in the range
    //to this IPv4range object
    //No validation of the inputs currently
    //Also, currently handles only a star in the fourth octet
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
    public boolean parseAddDashNotation( String dashipRange ) {
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
        
        //Only need to do the work if the range is valid
        if ( rangeValidated ) {
            //Add addresses
            for(i=thirdSectorLimits[0];i<=thirdSectorLimits[1];i++) {
                for(j=fourthSectorLimits[0];j<=fourthSectorLimits[1];j++) {
                    addressArray = this.appendToIPv4addressArray( addressArray , new IPv4address( sectors[0] + "." + sectors[1] + "." + i.toString() + "." + j.toString() ) ) ;
                }
            }


        }
        
        return rangeValidated ;
    }
  
    //Takes an ip range in string format written with the CIDR slash notation
    //then parses the string and adds all addresses from the range
    //to this IPv4range object
    public boolean parseAddSlashNotation( String slashipRange ) {
        //Will turn to false if invalid data is entered
        boolean rangeValidated = true ;
        //Everything after the slash is the number of mask bits
        Integer subnetmaskbits = new Integer( slashipRange.split("/")[1] ) ;
        //Number of addresses in the range is 2^(32-number of mask bits)
        //This is because the number of mask bits cover the bits that
        //**aren't** part of the range
        subnetmaskbits = 32 - subnetmaskbits ;
        Integer numberips = new Integer( (int) Math.pow(2,subnetmaskbits) ) ;
        //We start at the first ip address in the range, which is everything
        //to the left of the slash
        IPv4address intmipAddress = new IPv4address( slashipRange.split("/")[0] ) ;
        
        //Loop counter
        int i ;
        
        //Validation
        //Suppose the range is in the format a.b.c.d/e
        //The range is invalid if a,b,c,d < 0 or a,b,c,d>255 or e>31 or e<16
        //e>32 doesn't make sense because an IPv4address is only 32 bits long
        //e=32 doesn't make sense because then there is no range, only 1 IP
        //e<16 is disallowed because we're only allowing ranges in the
        //third and fourth octets
        if( ( new Integer( intmipAddress.getSectorAsString(1) ) < 0 )
                || ( new Integer( intmipAddress.getSectorAsString(1) ) > 255 )
                || ( new Integer( intmipAddress.getSectorAsString(2) ) < 0 )
                || ( new Integer( intmipAddress.getSectorAsString(2) ) > 255 )
                || ( new Integer( intmipAddress.getSectorAsString(3) ) < 0 )
                || ( new Integer( intmipAddress.getSectorAsString(3) ) > 255 )
                || ( new Integer( intmipAddress.getSectorAsString(4) ) < 0 )
                || ( new Integer( intmipAddress.getSectorAsString(4) ) > 255 )
                || ( subnetmaskbits > 16 ) || ( subnetmaskbits < 1 ) ) {
            rangeValidated = false ;
        }
        
        //If range is valid, try to add to this object each address in that range
        if ( rangeValidated ) {
            try {
                for(i=0;i<numberips;i++) {
                    addressArray = this.appendToIPv4addressArray( addressArray , intmipAddress ) ;
                    intmipAddress = intmipAddress.createCopy() ;
                    intmipAddress.incrementAddress() ;
                }
            } catch ( Exception e ) {
                //If it fails, it must be because something was wrong with
                //the way the range was formatted
                //and certainly not because there are any bugs
                //in my beautiful and perfect code
                rangeValidated = false ;
            }
        }
        
        //Inform the caller whether the range was valid,
        //so whether all the addresses could be added to the range
        return rangeValidated ;
    }
    
    //Takes an IPv4address as an input then checks each IPv4address
    //in this range to see if any are equal
    //Returns true if so, otherwise returns false
    public boolean isInRange( IPv4address inipAddress ) {
        int i ;
        boolean isInRange = false ;
        for( i=0 ; i<addressArray.length ; i++ ) {
            if( addressArray[i].equals( inipAddress ) )
                isInRange = true ;
        }
        return isInRange ;
    }
    
    //Checks if an input IPv4address is adjacent to this range
    //Recall that adjacent in the context of this program means
    //that the numerical ip addresses differ by one, and that
    //they have the same number in the third octet
    //We are also only count the input ip addresses as adjacent
    //if it is not already in the range
    //This method assumes that the first address in this range
    //is numerically the smallest; similarly that the last address
    //in this range is numerically the largest
    public boolean isAdjacentToRange( IPv4address inipAddress ) {
        boolean isAdjacentToRange = false ;
        //Creating a copy of the input because we are 
        //going to increment/decrement the address and
        //we don't want to change the original
        IPv4address intmipAddress = inipAddress.createCopy() ;
        
        //First statement checks that the lowest ip in the range equals
        //the input ip plus one
        //Second statement checks that the third octets are the same for both
        if( addressArray[0].equals( intmipAddress.incrementAddress() )
                && addressArray[0].getSectorAsString(3).equals( intmipAddress.getSectorAsString(3) ) ) {
            isAdjacentToRange = true ;
        }
        
        //First statement checks that the highest ip in the range equals
        //the input ip minus one
        //Second statement checks that the third octets are the same for both
        if( addressArray[addressArray.length-1].equals( intmipAddress.decrementAddress().decrementAddress() )
                && addressArray[addressArray.length-1].getSectorAsString(3).equals( intmipAddress.getSectorAsString(3) ) ) {
            isAdjacentToRange = true ;
        }
        
        //If one of the above is true, the address is adjacent
        return isAdjacentToRange ;
    }
    
    //Takes another IPv4range as an input and returns an IPv4range
    //containing all of the ip addresses shared by the input range
    //and this range
    //Basically, an intersection operation
    public IPv4range findOverlap( IPv4range inRange ) {
        int i ;
        IPv4range overlappingAddresses = new IPv4range() ;
        for( i=0 ; i<addressArray.length ; i++ ) {
            if( inRange.isInRange( addressArray[i] ) ) {
                overlappingAddresses.addAddressToRange( addressArray[i] ) ;
            }
        }
        return overlappingAddresses ;
    }
    
    //Returns the number of addresses in this range
    public Integer getSizeOfRange() {
        return addressArray.length ;
    }
    
    //A difference operation that subtracts the input IPv4range
    //from this IPv4range
    //That is to say, each address in this range that also appears in 
    //the input range is removed from this range
    public void subtractRange( IPv4range rangeToSubtract ) {
        int i,j ;
        for( i=0 ; i<rangeToSubtract.getSizeOfRange() ; i++ ) {
            j = 0 ;
            while( j<addressArray.length ) {
                if( addressArray[j].equals( rangeToSubtract.getAddressFromRange(i) ) ) {
                    addressArray = this.popFromIPv4addressArray( addressArray , j ) ;
                    j-- ;
                }
                j++ ;
            }
        }
    }
    
    public String getAllAddressesAsString() {
        int i ;
        String intmString = "" ;
        for( i=0 ; i<addressArray.length ; i++ ) {
            intmString += addressArray[i].getIPAsString() + " " ;
        }
        return intmString ;
    }
    
}