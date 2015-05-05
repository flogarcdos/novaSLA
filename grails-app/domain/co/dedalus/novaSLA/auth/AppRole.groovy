package co.dedalus.novaSLA.auth

class AppRole {

	String authority

	static mapping = {
		cache true
		table 'TOU_APP_ROLE'
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
