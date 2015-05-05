class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/api/app-list" (resources: 'AppList')
        "/api/countries" (resources: 'Country')

        "/api/listas/$codigo" (controller: 'AppListSearch', action: 'index') 

        "/api/guest/initialize-application" (controller: 'MainApi', action: 'initializeApplication') 
        "/api/main/authenicate-user" (controller: 'MainApi', action: 'authenicateUser') 

        // Menu routes
        "/api/menu/read-states" (controller: 'ViewState', action: 'inquireViewState') 
        "/api/menu/view-group" (resources: 'ViewGroup')

        "/api/accounts/login" (controller: 'AccountsApi', action: 'login', method: 'POST') 
        "/user" (controller: 'AccountsApi', action: 'user', method: 'POST') 
        
        "/"(view:"/index")
        "500"(view:'/error')
	}
}
