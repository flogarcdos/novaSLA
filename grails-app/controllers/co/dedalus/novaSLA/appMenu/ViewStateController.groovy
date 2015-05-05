package co.dedalus.novaSLA.appMenu

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.SpringSecurityService
import grails.transaction.Transactional
import static org.springframework.http.HttpStatus.*

import co.dedalus.novaSLA.appMenu.*
import co.dedalus.novaSLA.model.*

@Transactional(readOnly = true)
class ViewStateController {

   def springSecurityService
   static responseFormats = ['json', 'xml']

   //@Secured(['ROLE_ADMIN'])
   @Secured(['ROLE_ADMIN', 'isFullyAuthenticated()'])
   def inquireViewState() {
      ApplicationApiModel applicationApiModel = new ApplicationApiModel()
      TransactionalInformation transactionInf = new TransactionalInformation()
      
      def max = params.max ?: 10
      params.max = Math.min(max ?: 10, 100)
      def offset = params.offset ?: 0

      def dataItems =[]
      try {
         def user = springSecurityService.isLoggedIn() ? springSecurityService.loadCurrentUser() : null
         ViewGroup.list().each { vg ->
            ViewState.findAllByViewGroup(vg).each {vs ->
               Views.findAllByViewState(vs).each{ v-> 
                  dataItems.add ([
                       'Module' : vg.module
                     , 'Group': vg.groupName
                     , 'State' : [
                          'isStartUp' : vs.isStartUp
                        , 'Name' : vs.name
                        , 'Url' : vs.url
                        , 'Parent' : vs.parentName
                        , 'isAbstract' : vs.isAbstract
                        , 'Order' : vs.stateOrder
                        , 'Views' : [
                                'name': v.name
                              , 'controllerName': v.controllerName
                              , 'controllerUrl': v.controllerUrl
                              , 'templateUrl': v.templateUrl
                           ]
                     ]
                  ])
               } // Views
            } // ViewState
         }  // ViewGroup

         transactionInf.returnStatus = true;
         transactionInf.returnMessage.add("OK");
      }
      catch (Exception e) {
         transactionInf.returnStatus = false;
         transactionInf.returnMessage.add(e.getMessage() ); 
      }

      applicationApiModel.returnStatus = transactionInf.returnStatus;
      applicationApiModel.returnMessage = transactionInf.returnMessage
      applicationApiModel.isAuthenicated = springSecurityService.isLoggedIn()

      if (transactionInf.returnStatus == false) {
         respond applicationApiModel as JSON, [status: BAD_REQUEST]
      }

      applicationApiModel.dataItems = dataItems;
      applicationApiModel.totalRows = dataItems.size()
      applicationApiModel.currentOffset = offset
      applicationApiModel.currentMax = max

      respond (['data': applicationApiModel] as JSON, [
         status: OK,
         excludes: ['class', 'version']
      ])
   }
  
   private getIncludeFields() {
       params.fields?.tokenize(',')
   }     
}
