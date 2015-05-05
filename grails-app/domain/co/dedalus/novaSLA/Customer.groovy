package co.dedalus.novaSLA

class Customer {
	String name
	String docId
	String logoPath
	Date   startDate
	String agreementTerm
	String isEnabled
	String accessType
	String isPortalInd
	Country country

	static constraints = {
		name maxSize:100, blank: false
		docId maxSize:30, blank:false
		logoPath maxSize: 250, blank: false
		startDate nullable: true
		agreementTerm maxSize:1000, blank: false
		isEnabled maxSize: 1, blank: false, inList: ['Y', 'N']
		accessType maxSize:3, blank: false, inList: ['APP', 'CEN', 'LOC', 'REG', 'OTH']
			// (APP)lication, Cost (CEN)ter, (LOC)ation, (REG)ion, (OTH)er
		isPortalInd maxSize: 1, blank: false, inList: ['Y', 'N']
		
		docId unique: ['country']
		
    }
	static mapping = {
		table 'TOU_CUSTOMER'
	}
}
