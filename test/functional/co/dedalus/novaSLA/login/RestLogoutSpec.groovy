package co.dedalus.novaSLA.login

import grails.plugins.rest.client.RestResponse
import AbstractRestSpec

import co.dedalus.novaSLA.*
import co.dedalus.novaSLA.auth.*	

class RestLogoutSpec extends AbstractRestSpec {
   def setup() {
       // Initialize DB
   }

   def cleanup() {
   }

   void "logout filter can remove a token"() {
      given:
      	RestResponse authResponse = sendCorrectCredentials()
      	String token = authResponse.json.access_token
	      when:
      	def response = restBuilder.post("${baseUrl}/auth/logout") {
      	    header 'X-Auth-Token', token
      }

      then:
      	response.status == 200

      when:
      	response = restBuilder.get("${baseUrl}/auth/validate") {
      	header 'X-Auth-Token', token
      }

      then:
      		response.status == 401
   }

   void "logout filter returns 404 if token is not found"() {
      when:
      	def response = restBuilder.post("${baseUrl}/auth/logout") {
      	    header 'X-Auth-Token', 'whatever'
      }

      then:
      	response.status == 404
   }

   void "calling /auth/logout without token returns 400"() {
      when:
      	def response = restBuilder.post("${baseUrl}/auth/logout")
      then:
      	response.status == 400
   }
}