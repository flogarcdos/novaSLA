package co.dedalus.novaSLA

class PartyRelationshipType {
	String description
	String comments
	RoleType roleFrom
	RoleType roleTo
	
   static constraints = {
   	description maxSize: 30, unique: true
   	comments maxSize: 200, nullable: true
   	
   }

   static mapping = {
   	table 'TOU_PARTYREL_ROLE_TYPE'
   }   
}
