package co.dedalus.novaSLA

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.rest.RestfulController

@Transactional(readOnly = true)
class AppProcessPlanController extends RestfulController{

   static responseFormats = ['json', 'xml']
   static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

   AppProcessPlanController() {       
      super(AppProcessPlan)
   }

   def index(Integer max) {
      params.max = Math.min(max ?: 10, 100)
      def offset = params.offset?: 0

      respond AppProcessPlan.list(params), 
         [
          metadata:[
            totalCount: AppProcessPlan.count(),
            currentMax: params.max,
            currentOffset:offset],
          status: OK
         ]
   }

   def show(AppProcessPlan appProcessPlanInstance) {
      respond appProcessPlanInstance, [status: OK]
   } 

   @Transactional
   def save(AppProcessPlan appProcessPlanInstance) {
      if (appProcessPlanInstance == null) {
         render status: NOT_FOUND
         return
      }

      appProcessPlanInstance.validate()
      if (appProcessPlanInstance.hasErrors()) {
         render status: NOT_ACCEPTABLE
         return
      }

      appProcessPlanInstance.save flush:true
      respond appProcessPlanInstance, [status: CREATED]
   }

   @Transactional
   def update(AppProcessPlan appProcessPlanInstance) {
      if (appProcessPlanInstance == null) {
         render status: NOT_FOUND
         return
      }
   
      appProcessPlanInstance.validate()
      if (appProcessPlanInstance.hasErrors()) {
         render status: NOT_ACCEPTABLE
         return
      }
   
      appProcessPlanInstance.save flush:true
      respond appProcessPlanInstance, [status: OK]
   }

   @Transactional
   def delete(AppProcessPlan appProcessPlanInstance) {

      if (appProcessPlanInstance == null) {
         render status: NOT_FOUND
         return
      }
   
      appProcessPlanInstance.delete flush:true
      render status: NO_CONTENT
   }
}
