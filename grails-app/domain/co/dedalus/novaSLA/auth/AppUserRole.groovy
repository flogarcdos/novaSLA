package co.dedalus.novaSLA.auth

import org.apache.commons.lang.builder.HashCodeBuilder

class AppUserRole implements Serializable {

	private static final long serialVersionUID = 1

	AppUser appUser
	AppRole appRole

	boolean equals(other) {
		if (!(other instanceof AppUserRole)) {
			return false
		}

		other.appUser?.id == appUser?.id &&
		other.appRole?.id == appRole?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (appUser) builder.append(appUser.id)
		if (appRole) builder.append(appRole.id)
		builder.toHashCode()
	}

	static AppUserRole get(long appUserId, long appRoleId) {
		AppUserRole.where {
			appUser == AppUser.load(appUserId) &&
			appRole == AppRole.load(appRoleId)
		}.get()
	}

	static boolean exists(long appUserId, long appRoleId) {
		AppUserRole.where {
			appUser == AppUser.load(appUserId) &&
			appRole == AppRole.load(appRoleId)
		}.count() > 0
	}

	static AppUserRole create(AppUser appUser, AppRole appRole, boolean flush = false) {
		def instance = new AppUserRole(appUser: appUser, appRole: appRole)
		instance.save(flush: flush, insert: true)
		instance
	}

	static boolean remove(AppUser u, AppRole r, boolean flush = false) {
		if (u == null || r == null) return false

		int rowCount = AppUserRole.where {
			appUser == AppUser.load(u.id) &&
			appRole == AppRole.load(r.id)
		}.deleteAll()

		if (flush) { AppUserRole.withSession { it.flush() } }

		rowCount > 0
	}

	static void removeAll(AppUser u, boolean flush = false) {
		if (u == null) return

		AppUserRole.where {
			appUser == AppUser.load(u.id)
		}.deleteAll()

		if (flush) { AppUserRole.withSession { it.flush() } }
	}

	static void removeAll(AppRole r, boolean flush = false) {
		if (r == null) return

		AppUserRole.where {
			appRole == AppRole.load(r.id)
		}.deleteAll()

		if (flush) { AppUserRole.withSession { it.flush() } }
	}

	static constraints = {
		appRole validator: { AppRole r, AppUserRole ur ->
			if (ur.appUser == null) return
			boolean existing = false
			AppUserRole.withNewSession {
				existing = AppUserRole.exists(ur.appUser.id, r.id)
			}
			if (existing) {
				return 'userRole.exists'
			}
		}
	}

	static mapping = {
		id composite: ['appRole', 'appUser']
		table 'TOU_APP_USER_ROLE'
		version false
	}
}
