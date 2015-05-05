package co.dedalus.novaSLA

class PartyRole {
	Party party
	RoleType	roleType
	Date fromDate
	Date thruDate

   static constraints = {
   	thruDate nullable: true

   	party unique: ['roleType', 'fromDate', 'thruDate']
   }

   static mapping = {
   	table 'TOU_PARTY_ROLE'
   }

   def beforeInsert() {
	   if (fromDate == NULL_DATE) {
	        fromDate = new Date()
	   }
	}
}
