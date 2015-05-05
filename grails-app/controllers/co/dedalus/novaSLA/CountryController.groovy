package co.dedalus.novaSLA

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.rest.RestfulController

@Transactional(readOnly = true)
class CountryController extends RestfulController  {

   static responseFormats = ['json', 'xml']
   static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

   CountryController() {
     super(Country)
   }

   def index(Integer max) {
      params.max = Math.min(max ?: 10, 100)
      def offset = params.offset?: 0

      respond Country.list(params), 
         [
            metadata:[
               totalCount: Country.count(),
               currentMax: params.max,
               currentOffset:offset],
            status: OK
         ]
   }

   def show(Country countryInstance) {
      respond countryInstance, [status: OK]
   } 

   @Transactional
   def save(Country countryInstance) {
      if (countryInstance == null) {
         render status: NOT_FOUND
         return
      }

      countryInstance.validate()
      if (countryInstance.hasErrors()) {
         render status: NOT_ACCEPTABLE
         return
      }

      countryInstance.save flush:true
      respond countryInstance, [status: CREATED]
   }

   @Transactional
   def update(Country countryInstance) {
      if (countryInstance == null) {
         render status: NOT_FOUND
         return
      }

      countryInstance.validate()
      if (countryInstance.hasErrors()) {
         render status: NOT_ACCEPTABLE
         return
      }

      countryInstance.save flush:true
      respond countryInstance, [status: OK]
   }

   @Transactional
   def delete(Country countryInstance) {

      if (countryInstance == null) {
         render status: NOT_FOUND
         return
      }

      countryInstance.delete flush:true
      render status: NO_CONTENT
   }

   private getIncludeFields() {
       params.fields?.tokenize(',')
   } 
}
