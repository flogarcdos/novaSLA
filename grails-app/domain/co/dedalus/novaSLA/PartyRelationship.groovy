package co.dedalus.novaSLA

class PartyRelationship {
	Date fromDate
	Date thruDate
	String comments

	PartyRelationshipType partyRelationshipType
	PartyRelationship partyFrom
	PartyRelationship partyTo

   static constraints = {
   	comments maxSize: 200, nullable: true
   	partyFrom unique: ['partyTo', 'partyRelationshipType', 'partyTo', 'partyFrom']
   }	

   static mapping = {
   	table 'TOU_PARTY_RELATIONSHIP'
   }    
}
