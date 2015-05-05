package co.dedalus.novaSLA

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.rest.RestfulController

@Transactional(readOnly = true)
class AppListElementController extends RestfulController {

   static responseFormats = ['json', 'xml']
   static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

   AppListElementController() {
      super(AppListElement)
   }

   def index(Integer max) {
      params.max = Math.min(max ?: 10, 100)
      def offset = params.offset?: 0

      respond AppListElement.list(params), 
         [
            metadata:[
               totalCount: AppListElement.count(),
               currentMax: params.max,
               currentOffset:offset],
            status: OK
         ]
   }

   def show(AppListElement appListElementInstance) {
      respond appListElementInstance, [status: OK]
   } 
   
   @Transactional
   def save(AppListElement appListElementInstance) {
      if (appListElementInstance == null) {
         render status: NOT_FOUND
         return
      }

      appListElementInstance.validate()
      if (appListElementInstance.hasErrors()) {
         render status: NOT_ACCEPTABLE
         return
      }

      appListElementInstance.save flush:true
      respond appListElementInstance, [status: CREATED]
   }

   @Transactional
   def update(AppListElement appListElementInstance) {
      if (appListElementInstance == null) {
         render status: NOT_FOUND
         return
      }

      appListElementInstance.validate()
      if (appListElementInstance.hasErrors()) {
         render status: NOT_ACCEPTABLE
         return
      }

      appListElementInstance.save flush:true
      respond appListElementInstance, [status: OK]
   }

   @Transactional
   def delete(AppListElement appListElementInstance) {

      if (appListElementInstance == null) {
         render status: NOT_FOUND
         return
      }

      appListElementInstance.delete flush:true
      render status: NO_CONTENT
   }
}
