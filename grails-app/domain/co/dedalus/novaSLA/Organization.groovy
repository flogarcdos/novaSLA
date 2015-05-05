package co.dedalus.novaSLA

class Organization extends Party {
	String name
	String taxIdNum

   static constraints = {
   	name maxSize:100
   	taxIdNum maxSize:30, unique: true
   }

	static mapping = {
		table 'TOU_ORGANIZATION'
	}
	
	String toString() {
		return name
	}
}
