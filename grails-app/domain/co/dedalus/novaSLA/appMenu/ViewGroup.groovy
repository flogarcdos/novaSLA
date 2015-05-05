package co.dedalus.novaSLA.appMenu

class ViewGroup {
	String groupName
	String module

   static mapping = {
		cache true
		table 'TOU_APP_VIEW_GROUP'
	}

	static constraints = {
		groupName maxSize: 15
		module maxSize: 15

		groupName unique: ['module']
	}
}
