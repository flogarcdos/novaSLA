package co.dedalus.novaSLA

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.SpringSecurityService
import grails.transaction.Transactional
import static org.springframework.http.HttpStatus.*

// import co.dedalus.novaSLA.appMenu.ApplicationMenu
import co.dedalus.novaSLA.model.TransactionalInformation
import co.dedalus.novaSLA.model.ApplicationApiModel

@Transactional(readOnly = true)
class MainApiController {

   def springSecurityService
   static responseFormats = ['json', 'xml']

   @Secured(['ROLE_ANONYMOUS','ROLE_ADMIN'])
   def initializeApplication() {

      ApplicationApiModel applicationApiModel = new ApplicationApiModel()
      TransactionalInformation transactionInf = new TransactionalInformation()
      def menuItems =[]

      try {

         def user = springSecurityService.isLoggedIn() ? springSecurityService.loadCurrentUser() : null
         //def user = springSecurityService.currentUser

// println '******** '
// println ('*+*+*+*+*+*+*+* ', user);
         //menuItems = ApplicationMenu.findAllByRequiresAuthentication(user? true: false)
         transactionInf.returnStatus = true;
         transactionInf.returnMessage.add("Se ha inicializado correctamente la aplicación.");
      }
      catch (Exception e) {
         transactionInf.returnStatus = false;
         transactionInf.returnMessage.add(e); 
      }

      if (!menuItems.size() || transactionInf.returnStatus == false) {
         applicationApiModel.returnStatus = transactionInf.returnStatus
         applicationApiModel.returnMessage.add ('Error inicializando la aplicación.')

         respond applicationApiModel as JSON, [status: BAD_REQUEST]
      }

      def max = params.max ?: 10
      params.max = Math.min(max ?: 10, 100)
      def offset = params.offset ?: 0

      applicationApiModel.applicationMenu = menuItems;
      applicationApiModel.returnMessage = transactionInf.returnMessage
      applicationApiModel.returnStatus = transactionInf.returnStatus;
      // applicationApiModel.isAuthenicated = springSecurityService.authentication.authenticated
      applicationApiModel.isAuthenicated = springSecurityService.isLoggedIn()
      applicationApiModel.totalRows = menuItems.size()
      applicationApiModel.currentOffset = offset
      applicationApiModel.currentMax = max

      respond applicationApiModel as JSON, [
         status: OK,
         excludes: ['class', 'version']
      ]
   }

   def authenicateUser() {

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
