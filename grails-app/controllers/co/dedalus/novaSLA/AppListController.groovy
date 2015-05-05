package co.dedalus.novaSLA

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.rest.RestfulController

@Transactional(readOnly = true)
class AppListController extends RestfulController {

   static responseFormats = ['json', 'xml']
   static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

   AppListController() {
      super(AppList)
   }

   def index(Integer max) {
      params.max = Math.min(max ?: 10, 100)
      def offset = params.offset?: 0

      respond AppList.list(params), 
         [
           metadata:[
              totalCount: AppList.count(),
              currentMax: params.max,
              currentOffset:offset],
           status: OK
         ]
   }

   def show(AppList appListInstance) {
      respond appListInstance, [status: OK]
   } 

   @Transactional
   def save(AppList appListInstance) {
      if (appListInstance == null) {
         render status: NOT_FOUND
         return
      }

      appListInstance.validate()
      if (appListInstance.hasErrors()) {
         render status: NOT_ACCEPTABLE
         return
      }

      appListInstance.save flush:true
      respond appListInstance, [status: CREATED]
   }

   @Transactional
   def update(AppList appListInstance) {
      if (appListInstance == null) {
         render status: NOT_FOUND
         return
      }

      appListInstance.validate()
      if (appListInstance.hasErrors()) {
         render status: NOT_ACCEPTABLE
         return
      }

      appListInstance.save flush:true
      respond appListInstance, [status: OK]
   }

   @Transactional
   def delete(AppList appListInstance) {

      if (appListInstance == null) {
         render status: NOT_FOUND
         return
      }

      appListInstance.delete flush:true
      render status: NO_CONTENT
   }
}
