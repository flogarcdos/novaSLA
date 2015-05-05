package co.dedalus.novaSLA.appMenu

class Views {
	String name
	String controllerName
	String controllerUrl
	String controllerAs
	String templateUrl
	String data
	ViewState viewState
	Boolean	requiresAuthentication

   static mapping = {
		cache true
		table 'TOU_APP_VIEW_DETAIL'
	}

	static constraints = {
		name maxSize: 30
		controllerName maxSize: 250
		controllerUrl maxSize: 250
		controllerAs maxSize: 30
		templateUrl maxSize: 250
		data maxSize: 1500, nullable: true

		name unique: ['viewState']
	}
}
