package co.dedalus.novaSLA

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.rest.RestfulController

@Transactional(readOnly = true)
class CustomerController extends RestfulController {

   static responseFormats = ['json', 'xml']
   static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

   CustomerController() {
      super(Customer)
   }

   def index(Integer max) {
      params.max = Math.min(max ?: 10, 100)
      def offset = params.offset?: 0

      respond Customer.list(params), 
         [
            metadata:[
               totalCount: Customer.count(),
               currentMax: params.max,
               currentOffset:offset],
            status: OK
         ]
   }

   def show(Customer customerInstance) {
      respond customerInstance, [status: OK]
   } 

   @Transactional
   def save(Customer customerInstance) {
      if (customerInstance == null) {
         render status: NOT_FOUND
         return
      }

      customerInstance.validate()
      if (customerInstance.hasErrors()) {
         render status: NOT_ACCEPTABLE
         return
      }

      customerInstance.save flush:true
      respond customerInstance, [status: CREATED]
   }

   @Transactional
   def update(Customer customerInstance) {
      if (customerInstance == null) {
         render status: NOT_FOUND
         return
      }

      customerInstance.validate()
      if (customerInstance.hasErrors()) {
         render status: NOT_ACCEPTABLE
         return
      }

      customerInstance.save flush:true
      respond customerInstance, [status: OK]
   }

   @Transactional
   def delete(Customer customerInstance) {

      if (customerInstance == null) {
         render status: NOT_FOUND
         return
      }

      customerInstance.delete flush:true
      render status: NO_CONTENT
   }
}
