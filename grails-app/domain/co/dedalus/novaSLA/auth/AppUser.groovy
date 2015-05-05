package co.dedalus.novaSLA.auth

import co.dedalus.novaSLA.Person

class AppUser {

	transient springSecurityService

	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	Person person

	static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true
		password blank: false
	}

	static mapping = {
		password column: 'password'
		table 'TOU_APP_USER'
	}

	Set<AppRole> getAuthorities() {
		AppUserRole.findAllByAppUser(this).collect { it.appRole }
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
}
