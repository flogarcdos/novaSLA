package co.dedalus.novaSLA

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.rest.RestfulController

@Transactional(readOnly = true)
class CustomerProcessPlanController extends RestfulController {

   static responseFormats = ['json', 'xml']
   static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

   CustomerProcessPlanController() {
      super(CustomerProcessPlan)
   }

   def index(Integer max) {
      params.max = Math.min(max ?: 10, 100)
      def offset = params.offset?: 0

      respond CustomerProcessPlan.list(params), 
         [
            metadata:[
               totalCount: CustomerProcessPlan.count(),
               currentMax: params.max,
               currentOffset:offset],
            status: OK
         ]
   }

   def show(CustomerProcessPlan customerProcessPlanInstance) {
      respond customerProcessPlanInstance, [status: OK]
   } 

   @Transactional
   def save(CustomerProcessPlan customerProcessPlanInstance) {
      if (customerProcessPlanInstance == null) {
         render status: NOT_FOUND
         return
      }

      customerProcessPlanInstance.validate()
      if (customerProcessPlanInstance.hasErrors()) {
         render status: NOT_ACCEPTABLE
         return
      }

      customerProcessPlanInstance.save flush:true
      respond customerProcessPlanInstance, [status: CREATED]
   }

   @Transactional
   def update(CustomerProcessPlan customerProcessPlanInstance) {
      if (customerProcessPlanInstance == null) {
         render status: NOT_FOUND
         return
      }

      customerProcessPlanInstance.validate()
      if (customerProcessPlanInstance.hasErrors()) {
         render status: NOT_ACCEPTABLE
         return
      }

      customerProcessPlanInstance.save flush:true
      respond customerProcessPlanInstance, [status: OK]
   }

   @Transactional
   def delete(CustomerProcessPlan customerProcessPlanInstance) {

      if (customerProcessPlanInstance == null) {
         render status: NOT_FOUND
         return
      }

      customerProcessPlanInstance.delete flush:true
      render status: NO_CONTENT
   }
}
