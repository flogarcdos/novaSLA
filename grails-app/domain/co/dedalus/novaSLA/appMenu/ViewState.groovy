package co.dedalus.novaSLA.appMenu

class ViewState {
	Boolean isStartUp
	String  name
	String url
	String parentName
	Boolean isAbstract
	ViewGroup viewGroup
	Integer stateOrder

   static mapping = {
		cache true
		table 'TOU_APP_VIEW_STATE'
	}

	static constraints = {
		name maxSize: 30
		url maxSize: 250
		parentName maxSize: 30 , nullable: true
		stateOrder range: 1..99

		name unique: ['viewGroup']
	}
}
