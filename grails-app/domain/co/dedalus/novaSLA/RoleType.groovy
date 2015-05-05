package co.dedalus.novaSLA

class RoleType {
	String description
	String comments

   static constraints = {
   	description maxSize: 30, unique: true
   	comments maxSize: 200, nullable: true
   }

   static mapping = {
   	table 'TOU_ROLE_TYPE'
   }   
}
