package co.dedalus.novaSLA

class Person extends Party{
	String firstName
	String lastName
	AppListElement gender
	Date birthDate
	String docIdNum

   static constraints = {
   	firstName maxSize: 50
   	lastName maxSize: 50
   	docIdNum maxSize: 30, unique: true
   	birthDate nullable:true
   }

	static mapping = {
		table 'TOU_PERSON'
	}
	
	String toString() {
		return lastName + " " + firstName
	}
}
