package co.dedalus.novaSLA.login

import grails.plugins.rest.client.RestResponse
import AbstractRestSpec

import co.dedalus.novaSLA.*
import co.dedalus.novaSLA.auth.*


class RestLoginSpec extends AbstractRestSpec {
   def setup() {
       // Initialize DB
   }

   def cleanup() {
   }


	void 'login works well with valid credentials'() {
		when:

       	RestResponse response = sendCorrectCredentials()
      then:
        	response.status == 200
        	response.headers.getFirst('Content-Type') == 'application/json;charset=UTF-8'
        	response.headers.getFirst('Cache-Control') == 'no-store'
        	response.headers.getFirst('Pragma') == 'no-cache'
        	response.json.access_token
        	response.json.token_type == 'Bearer'       	
	}

	void 'login fails with invalid credentials'() {
		when:

       	RestResponse response = sendWrongCredentials()
      then:
        	response.status == 401
      	
	}	
}