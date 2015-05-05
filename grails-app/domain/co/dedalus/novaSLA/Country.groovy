package co.dedalus.novaSLA

class Country {

   String code;
	String name;
	
	static constraints = {
		code maxSize:30, blank: false, unique: true
		name maxSize:100,blank: false, unique: true
    }
	static mapping = {
		table 'TOU_COUNTRY'
	}
	
	String toString() {
		return name
	}
}