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
    //private String humanreadableRange ;
    
    //Constructor starting from a single IPv4address
    public IPv4range( IPv4address inipAddress ) {
        this.addAddressToRange( inipAddress , false ) ;
    }
    
    //Constructor starting from an array of IPv4addresses
    public IPv4range( IPv4address[] inipAddresses ) {
        int i ;
        for(i=0;i<inipAddresses.length;i++){
            this.addAddressToRange( inipAddresses[i] , false ) ;
        }
    }
    
    //Trivial constructor with no inputs
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
    
    //Creates a new copy of this IPv4range
    //with a different pointer
    public IPv4range createCopy() {
        int i ;
        IPv4range intmRange = new IPv4range() ;
        for( i=0 ; i<this.addressArray.length ; i++ ) {
            intmRange.addAddressToRange( addressArray[i].createCopy() , false ) ;
        }
        return intmRange ;
    }
    
    //Ensures that a range's addresses are in the correct numerical order
    //Uses an bubble sort approach
    public void sortRange() {
        int i ;
        boolean didSwap = true ;
        while( didSwap ) {
            didSwap = false ;
            for( i=0 ; i<this.addressArray.length-1 ; i++ ) {
                if( this.addressArray[i].getIPAsNumber() > this.addressArray[i+1].getIPAsNumber() ) {
                    this.swapAddresses( i , i+1 );
                    didSwap = true ;
                }
            }
        }
    }
    
    //Helper method for sort method, above
    //Swaps the two addresses at indices 
    //index1 and index2 in addressArray
    private void swapAddresses( int index1 , int index2 ) {
        
        int i ;
        int lindex ;
        int uindex ;
        IPv4address[] intmAddressArray = new IPv4address[ this.addressArray.length ] ;
        
        if( index1 < index2 ) {
            lindex = index1 ;
            uindex = index2 ;
        } else {
            lindex = index2 ;
            uindex = index1 ;
        }
        
        for( i=0 ; i<lindex ; i++ ) {
            intmAddressArray[i] = this.addressArray[i] ;
        }
        
        intmAddressArray[lindex] = this.addressArray[uindex] ;
        
        for( i=lindex+1 ; i<uindex ; i++ ) {
            intmAddressArray[i] = this.addressArray[i] ;
        }
        
        intmAddressArray[uindex] = this.addressArray[lindex] ;
        
        for( i=uindex+1 ; i<intmAddressArray.length ; i++ ) {
            intmAddressArray[i] = this.addressArray[i] ;
        }
        
        this.addressArray = intmAddressArray ;
        
    }
    
    //Takes an array of IPv4address objects and another IPv4address 
    //and appends the latter at the end of the former and returns the resulting array
    public IPv4address[] appendToIPv4addressArrayEnd( IPv4address[] inarray , IPv4address inobject ) {
        int i ;
        IPv4address[] intmarray = new IPv4address[inarray.length+1] ;
        for(i=0;i<inarray.length;i++) {
            intmarray[i] = inarray[i] ;
        }
        intmarray[inarray.length] = inobject ;
        return intmarray ;
    }
    
    //Takes an array of IPv4address objects and another IPv4address 
    //and inserts the latter at the start of the former and returns the resulting array
    public IPv4address[] appendToIPv4addressArrayStart( IPv4address[] inarray , IPv4address inobject ) {
        int i ;
        IPv4address[] intmarray = new IPv4address[inarray.length+1] ;
        intmarray[0] = inobject ;
        for(i=0;i<inarray.length;i++) {
            intmarray[i+1] = inarray[i] ;
        }
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
    
    public IPv4range[] appendToIPv4rangeArray( IPv4range[] inarray , IPv4range inobject ) {
        int i ;
        IPv4range[] intmarray = new IPv4range[inarray.length+1] ;
        for(i=0;i<inarray.length;i++) {
            intmarray[i] = inarray[i] ;
        }
        intmarray[inarray.length] = inobject ;
        return intmarray ;
    }
    
    public IPv4range[] popFromIPv4rangeArray( IPv4range[] inArray , int toPop ) {
        int i ;
        IPv4range[] intmArray = new IPv4range[inArray.length-1] ;
        for( i=0 ; i<toPop ; i++ ) {
            intmArray[i] = inArray[i] ;
        }
        for( i=toPop ; i<intmArray.length ; i++ ) {
            intmArray[i] = inArray[i+1] ;
        }
        return intmArray ;
    } 
    
    //Concatenates the current range with an input range
    //If atStart is true, result ~ [ inipRange , this ]
    //If atStart is false, result ~ [ this , inipRange ] 
    public void concatenateWithRange( IPv4range inipRange , boolean atStart ) {
        int i ;
        IPv4range intmRange = this.createCopy() ;
        addressArray = new IPv4address[ intmRange.getSizeOfRange() + inipRange.getSizeOfRange() ] ;
        if( atStart ) {
            for( i=0 ; i<inipRange.getSizeOfRange() ; i++ ) {
                addressArray[i] = inipRange.getAddressFromRange(i) ;
            }
            for( i=0 ; i<intmRange.getSizeOfRange() ; i++ ) {
                addressArray[ i + inipRange.getSizeOfRange() ] = intmRange.getAddressFromRange(i) ;
            }
        } else {
            for( i=0 ; i<intmRange.getSizeOfRange() ; i++ ) {
                addressArray[i] = intmRange.getAddressFromRange(i) ;
            }
            for( i=0 ; i<inipRange.getSizeOfRange() ; i++ ) {
                addressArray[ i + intmRange.getSizeOfRange() ] = inipRange.getAddressFromRange(i) ;
            }
        }
    }
    
    //Returns all addresses in the range as an array of IPv4addresses
    public IPv4address[] getRangeAsAddresses() {
        return this.addressArray ;
    }
    
    //Returns a single IPv4address from the range based on the index input
    public IPv4address getAddressFromRange( int i ) {
        return this.addressArray[i] ;
    }
    
    //Adds an IPv4address to the range
    //Second argument atStart = True -> adds address at the start of the range
    //Second argument atStart = False -> adds address at the end of the range
    //If the address is already in the range, it takes no action
    public void addAddressToRange( IPv4address inipAddress , boolean atStart ) {
        if( !this.isInRange( inipAddress ) ) {
            if( !atStart ) {
                addressArray = this.appendToIPv4addressArrayEnd( addressArray , inipAddress ) ;
            } else {
                addressArray = this.appendToIPv4addressArrayStart( addressArray , inipAddress ) ;
            }
        }
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
            this.addAddressToRange( intmipAddress , false );
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
                    this.addAddressToRange( new IPv4address( sectors[0] + "." + sectors[1] + "." + i.toString() + "." + j.toString() ) , false );
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
                    this.addAddressToRange( intmipAddress , false );
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
    public boolean isAdjacentAddress( IPv4address inipAddress ) {
        
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
    
    //Checks if the input range is "adjacent" to this one
    //We count a range as adjacent if:
    //  The set of fourth octets in the two ranges matches
    //  And either:
    //      The smallest third octet of the input range is one larger
    //      than the largest third octet in this range
    //  Or:
    //      The largest third octet of the input range is one smaller
    //      than the smallest third octet in this range
    //This method will assume that the ranges are ordered from
    //smallest numerical ip address to largest and that both 
    //ranges are of the form a.b.c-d.e-f
    //THESE CONDITIONS ARE NOT CURRENTLY CHECKED BY THE METHOD
    //USE WITH CARE
    //Returns 0 if the ranges are not adjacent
    //Returns 1 if the input range is "above" this range
    //Returns -1 if the input range is "below" this range
    public int isAdjacentRange( IPv4range inipRange ) {
        
        int isAdjacent = 0 ;
        
        Integer[] thisBoundingThirdOctet = new Integer[2] ;
        Integer[] inBoundingThirdOctet = new Integer[2] ;
        
        thisBoundingThirdOctet[0] = new Integer( this.addressArray[0].getSectorAsString(3) ) ;
        thisBoundingThirdOctet[1] = new Integer( this.addressArray[this.addressArray.length-1].getSectorAsString(3) ) ;
        
        inBoundingThirdOctet[0] = new Integer( inipRange.getAddressFromRange(0).getSectorAsString(3) ) ;
        inBoundingThirdOctet[1] = new Integer( inipRange.getAddressFromRange(inipRange.getSizeOfRange()-1).getSectorAsString(3) ) ;
                
        if( this.addressArray[0].equalsFourthOctet(inipRange.getAddressFromRange(0))
                && this.addressArray[this.addressArray.length-1].equals( inipRange.getAddressFromRange(inipRange.getSizeOfRange()-1) ) ) {
            if( thisBoundingThirdOctet[0].equals( inBoundingThirdOctet[1] + 1L ) ) {
                //Input range third octets < this range third octets
                isAdjacent = -1 ;
            }
            if( inBoundingThirdOctet[0].equals( thisBoundingThirdOctet[1] + 1L ) ) {
                //Input range third octets > this range third octets
                isAdjacent = 1 ;
            }
        }
        
        return isAdjacent ;
        
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
                overlappingAddresses.addAddressToRange( addressArray[i] , false ) ;
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
    
    //This method assumes that the input range contains a set
    //of ip addresses that satisfy:
    //For all i in {m,n}, a.b.c.i is in the range
    //For all i in {p,q}, a.b.i.d is in the range
    //For all a.b.c.i in the range, i is in {m,n}
    //For all a.b.i.d in the range, i in is {p,q}
    //It then prints out this range as a string
    //In the format a.b.p-q.m-n
    //It currently DOES NOT check if the range is in this form!
    //Use with care!
    //It also does not assume that the range is in any way ordered
    //and searches through each address to find the lowest and
    //highest in the range
    public String convertRangeHumanReadable( IPv4range inipRange ) {
        
        int i ;
        IPv4address[] boundingipAddresses = new IPv4address[]{inipRange.getAddressFromRange(0),inipRange.getAddressFromRange(0)} ;
        String rangeString ;
        
        for( i=0 ; i<inipRange.getSizeOfRange() ; i++ ) {
            
            if( boundingipAddresses[0].getIPAsNumber() > inipRange.getAddressFromRange(i).getIPAsNumber() ) {
                boundingipAddresses[0] = inipRange.getAddressFromRange(i) ;
            }
            
            if( boundingipAddresses[1].getIPAsNumber() < inipRange.getAddressFromRange(i).getIPAsNumber() ) {
                boundingipAddresses[1] = inipRange.getAddressFromRange(i) ;
            }
            
        }
        
        rangeString = inipRange.getAddressFromRange(0).getSectorAsString(1) + "."
                        + inipRange.getAddressFromRange(0).getSectorAsString(2) + "."
                        + boundingipAddresses[0].getSectorAsString(3) ;
        
        if( !boundingipAddresses[0].getSectorAsString(3).equals( boundingipAddresses[1].getSectorAsString(3) ) ) {
            rangeString += "-" + boundingipAddresses[1].getSectorAsString(3) ;
        }
        
        rangeString += "." + boundingipAddresses[0].getSectorAsString(4) ;
        
        if( !boundingipAddresses[0].getSectorAsString(4).equals( boundingipAddresses[1].getSectorAsString(4) ) ) {
            rangeString += "-" + boundingipAddresses[1].getSectorAsString(4) ;
        }
        
        return rangeString ;
        
    }
    
    //Method to split the current IPv4range into a subset of IPv4ranges
    //which are "contiguous" ranges of ip addresses
    //By contiguous we mean that each member in the range has the same
    //first, second and third octets, and that the fourth octets are in
    //[a,b] where all c in [a,b] are in the range.
    public IPv4range[] getContiguousSubranges() {
        int i ;
        IPv4range[] intmRanges ;
        this.sortRange();
        intmRanges = new IPv4range[]{new IPv4range( this.addressArray[0] ) } ;
        for( i=1 ; i<this.addressArray.length ; i++ ) {
            if( this.addressArray[i].isAdjacentAddress( intmRanges[ intmRanges.length-1 ].getAddressFromRange( intmRanges[ intmRanges.length-1 ].getSizeOfRange()-1 ) ) ) {
                intmRanges[ intmRanges.length-1 ].addAddressToRange( this.addressArray[i] , false );
            } else {
                intmRanges = this.appendToIPv4rangeArray( intmRanges , new IPv4range( this.addressArray[i] ) ) ;
            }
            
        }
        return intmRanges ;
    }
    
    //Going to approach this in a different way by splitting it
    //into subtasks, each of which will have their own method
    //in this class.
    //Basic overview:
    //Ensure that the array is sorted (toSort, done)
    //Split the range into contiguous sections
    //of equal first, second and third octets (needs new method)
    //Recombine resulting ranges if:
    //  first & second octets equal (can use equals***Octet in IPv4address)
    //  third octets adjacent       (new method or modification of isAdjacentRange above)
    //  ranges in fourth octets equal (new method or modification of isAdjacentRange above)
    //Some of the recombination may enter in logic in SquashIPRange
    //instead of contained within this class
    /*
    public String[] getWholeRangeHumanReadable() {
        
        IPv4range[] minimalSetOfRanges = new IPv4range[0] ;
        int i, j, k ;
        boolean wasAddedToRange = true ;
        IPv4address[] addressArrayCopy = new IPv4address[ addressArray.length ] ;
        
        for( i=0 ; i<addressArray.length ; i++ ) {
            addressArrayCopy[i] = this.addressArray[i] ;
        }
        
        k = 0 ;
        
        while( !( k == addressArrayCopy.length ) ) {
                    
            while( wasAddedToRange ) {
                
                wasAddedToRange = false ;
                
                //Search through list of IP addresses
                i = 0 ;
                while( i<addressArrayCopy.length ) {
                    //If an ip address has first, second, third octets
                    //that match those of a range and is adjacent to that range
                    //then add it to that range
                    //System.out.println( i + " " + addressArrayCopy[i].getIPAsString() ) ;
                    for( j=0 ; j<minimalSetOfRanges.length ; j++ ) {
                        //System.out.println( minimalSetOfRanges[j].isAdjacentAddress(addressArrayCopy[i]) ) ;
                        if( addressArrayCopy[i].equalsFirstOctet( minimalSetOfRanges[j].getAddressFromRange(0) )
                                && addressArrayCopy[i].equalsSecondOctet( minimalSetOfRanges[j].getAddressFromRange(0) )
                                && addressArrayCopy[i].equalsThirdOctet( minimalSetOfRanges[j].getAddressFromRange(0) )
                                && minimalSetOfRanges[j].isAdjacentAddress(addressArrayCopy[i]) ) {

                            if( addressArrayCopy[i].getIPAsNumber() < minimalSetOfRanges[j].getAddressFromRange(0).getIPAsNumber() ) {
                                minimalSetOfRanges[j].addAddressToRange( addressArrayCopy[i] , true);
                            } else {
                                minimalSetOfRanges[j].addAddressToRange( addressArrayCopy[i] , false);
                                addressArrayCopy = this.popFromIPv4addressArray( addressArrayCopy , i ) ;
                            }
                            addressArrayCopy = this.popFromIPv4addressArray( addressArrayCopy , i ) ;
                            k++ ;
                            wasAddedToRange = true ;

                        } //Close outer if
                    } //Close for(j)
                    i++ ;
                } //Close while(i)
            }//Close while(addedToRange)
            
            //Otherwise, start a new range in the set with this ip address
            //as the first member
            if( !wasAddedToRange ) {
                 minimalSetOfRanges = this.appendToIPv4rangeArray( minimalSetOfRanges , new IPv4range( addressArrayCopy[0] ) ) ;
                 addressArrayCopy = this.popFromIPv4addressArray( addressArrayCopy , 0 ) ;
                 wasAddedToRange = true ;
                 k++ ;
            }
            
        }
        
        //For each range
        for( i=0 ; i<minimalSetOfRanges.length ; i++ ) {
            //Check ranges after it
            for( j=i+1 ; j<minimalSetOfRanges.length ; j++ ) {
                //If they are adjacent
                switch( minimalSetOfRanges[i].isAdjacentRange( minimalSetOfRanges[j] ) ) {
                    //Concatenate later member in array into earlier member in array
                    case 1  : minimalSetOfRanges[i].concatenateWithRange( minimalSetOfRanges[j] , false );
                    minimalSetOfRanges = this.popFromIPv4rangeArray( minimalSetOfRanges , j ) ;
                    break ;
                    case -1 : minimalSetOfRanges[i].concatenateWithRange( minimalSetOfRanges[j] , true );
                    minimalSetOfRanges = this.popFromIPv4rangeArray( minimalSetOfRanges , j ) ;
                    break ;
                    //Only other option should be zero
                    //In which case the ranges are not adjacent
                    //Hence no action is required
                    default : break ;
                }
            }
        }
        
        //Finally, convert each of the minimal set to a human readable string
        String[] minimalSetOfRangesStrings = new String[ minimalSetOfRanges.length ] ;

        for( i=0 ; i<minimalSetOfRanges.length ; i++ ) {
            minimalSetOfRangesStrings[i] = this.convertRangeHumanReadable( minimalSetOfRanges[i] ) ;
        }
        
        return minimalSetOfRangesStrings ;
    }
    */
    
}