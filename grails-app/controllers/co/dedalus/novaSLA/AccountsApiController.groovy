package co.dedalus.novaSLA

// import java.security.Principal;

import static org.springframework.http.HttpStatus.*
import grails.converters.JSON
import grails.transaction.Transactional
import co.dedalus.novaSLA.appMenu.Views
import co.dedalus.novaSLA.model.TransactionalInformation

@Transactional(readOnly = true)
class AccountsApiController {

   static responseFormats = ['json', 'xml']
   // static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

   def login() {
      def springSecurityService

      def jsonObj = request.JSON

      def menuItems = []
      Views.findAllByRequiresAuthentication(true).each{ r ->
         menuItems.add ( [
              'description': r.description
            , 'route': r.route
            , 'module': r.module
            , 'menuOrder': r.menuOrder
            , 'requiresAuthentication': r.requiresAuthentication
            , 'roleList': r.roleList

         ])
      }

      TransactionalInformation transaction = new TransactionalInformation()

      def encodedPassword = 
         springSecurityService?.passwordEncoder ? 
            springSecurityService.encodePassword(jsonObj.password) : jsonObj.password

      user = AppUserFindByUsernameAndPassword(jsonObj.username, encodedPassword)

      if (user) {
         transaction.returnStatus = true
      }
      else {
         transaction.returnStatus = false
         transaction.returnMessage.add('Login invalid')
      }

      transaction.isAuthenicated = false

      Map tObject = ['results': menuItems]
            tObject['metadata'] = [
               "totalCount": ApplicationMenu.count(),
               "currentMax": params.max,
               "currentOffset":offset]

      respond tObject as JSON, [
         status: OK,
         includes: includeFields, excludes: ['class', 'version']
      ]
   }

   def AuthenicateUser() {

      TransactionalInformation transaction = new TransactionalInformation()

      transaction.returnStatus = true
      transaction.isAuthenicated = false

      Map tObject = ['results': transaction]
            tObject['metadata'] = [
               "totalCount": 0,
               "currentMax": 0,
               "currentOffset": 0]

      respond tObject as JSON, [
         status: OK,
         includes: includeFields, excludes: ['class', 'version']
      ]
   }

   private getIncludeFields() {
       params.fields?.tokenize(',')
   }     
}
