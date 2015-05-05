package co.dedalus.novaSLA.auth
 
class AuthenticationToken {
 
   String username
   String token

   static mapping = {
		cache true
		table 'TOU_APP_AUTH_TOKEN'
	}
}