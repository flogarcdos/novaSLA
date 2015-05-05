package co.dedalus.novaSLA

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.rest.RestfulController
import grails.converters.JSON

@Transactional(readOnly = true)
class AppListSearchController {  

   static responseFormats = ['json', 'xml']
   static allowedMethods = [show: 'GET']

   def index(Integer max) {

      params.max = Math.min(max ?: 10, 100)

      Integer offset = 0
      if (params.offset && params.offset.isInteger() ) {
      	offset = params.offset.toInteger()
      }

      log.debug(params)

      def items = []

      if ( params.codigo) {
         def appList = AppList.findByCode(params.codigo)

         def appListaElement = AppListElement.findAllByAppList(appList, [sort: 'description']).each {r ->
            items.add ( [ 'code': r.code, 'description': r.description ] )
         }
      }
      else {
         render status: NO_CONTENT
         return
      }

		Map tObject = ['results': items]

      // // def totalRecords = resultados.getTotalCount() 
      // tObject['metadata'] = [
      //    "totalCount": 0, //totalRecords,
      //    "currentMax": params.max,
      //    "currentOffset": offset]

      respond tObject as JSON, [
         status: OK,
         includes: includeFields, excludes: ['class', 'version']
      ]
   }

   private getIncludeFields() {
       params.fields?.tokenize(',')
   }        
}   