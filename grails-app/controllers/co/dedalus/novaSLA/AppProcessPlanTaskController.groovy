package co.dedalus.novaSLA

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.rest.RestfulController

@Transactional(readOnly = true)
class AppProcessPlanTaskController extends RestfulController {

   static responseFormats = ['json', 'xml']
   static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

   AppProcessPlanTaskController() {
      super(AppProcessPlanTask)
   }

   def index(Integer max) {
      params.max = Math.min(max ?: 10, 100)
      def offset = params.offset?: 0

      respond AppProcessPlanTask.list(params), 
         [
            metadata:[
               totalCount: AppProcessPlanTask.count(),
               currentMax: params.max,
               currentOffset:offset],
            status: OK
         ]
   }

   def show(AppProcessPlanTask appProcessPlanTaskInstance) {
      respond appProcessPlanTaskInstance, [status: OK]
   } 

   @Transactional
   def save(AppProcessPlanTask appProcessPlanTaskInstance) {
      if (appProcessPlanTaskInstance == null) {
         render status: NOT_FOUND
         return
      }

      appProcessPlanTaskInstance.validate()
      if (appProcessPlanTaskInstance.hasErrors()) {
         render status: NOT_ACCEPTABLE
         return
      }

      appProcessPlanTaskInstance.save flush:true
      respond appProcessPlanTaskInstance, [status: CREATED]
   }

   @Transactional
   def update(AppProcessPlanTask appProcessPlanTaskInstance) {
      if (appProcessPlanTaskInstance == null) {
         render status: NOT_FOUND
         return
      }

      appProcessPlanTaskInstance.validate()
      if (appProcessPlanTaskInstance.hasErrors()) {
         render status: NOT_ACCEPTABLE
         return
      }

      appProcessPlanTaskInstance.save flush:true
      respond appProcessPlanTaskInstance, [status: OK]
   }

   @Transactional
   def delete(AppProcessPlanTask appProcessPlanTaskInstance) {

      if (appProcessPlanTaskInstance == null) {
         render status: NOT_FOUND
         return
      }

      appProcessPlanTaskInstance.delete flush:true
      render status: NO_CONTENT
   }
}
