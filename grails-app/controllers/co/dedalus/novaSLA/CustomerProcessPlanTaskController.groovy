package co.dedalus.novaSLA

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.rest.RestfulController

@Transactional(readOnly = true)
class CustomerProcessPlanTaskController extends RestfulController {

   static responseFormats = ['json', 'xml']
   static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

   CustomerProcessPlanTaskController() {
      super(CustomerProcessPlanTask)
   }

   def index(Integer max) {
      params.max = Math.min(max ?: 10, 100)
      def offset = params.offset?: 0

      respond CustomerProcessPlanTask.list(params), 
         [
            metadata:[
               totalCount: CustomerProcessPlanTask.count(),
               currentMax: params.max,
               currentOffset:offset],
            status: OK
         ]
   }

   def show(CustomerProcessPlanTask customerProcessPlanTaskInstance) {
      respond customerProcessPlanTaskInstance, [status: OK]
   } 

   @Transactional
   def save(CustomerProcessPlanTask customerProcessPlanTaskInstance) {
      if (customerProcessPlanTaskInstance == null) {
         render status: NOT_FOUND
         return
      }

      customerProcessPlanTaskInstance.validate()
      if (customerProcessPlanTaskInstance.hasErrors()) {
         render status: NOT_ACCEPTABLE
         return
      }

      customerProcessPlanTaskInstance.save flush:true
      respond customerProcessPlanTaskInstance, [status: CREATED]
   }

   @Transactional
   def update(CustomerProcessPlanTask customerProcessPlanTaskInstance) {
      if (customerProcessPlanTaskInstance == null) {
         render status: NOT_FOUND
         return
      }

      customerProcessPlanTaskInstance.validate()
      if (customerProcessPlanTaskInstance.hasErrors()) {
         render status: NOT_ACCEPTABLE
         return
      }

      customerProcessPlanTaskInstance.save flush:true
      respond customerProcessPlanTaskInstance, [status: OK]
   }

   @Transactional
   def delete(CustomerProcessPlanTask customerProcessPlanTaskInstance) {

      if (customerProcessPlanTaskInstance == null) {
         render status: NOT_FOUND
         return
      }

      customerProcessPlanTaskInstance.delete flush:true
      render status: NO_CONTENT
   }
}
