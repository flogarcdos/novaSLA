package co.dedalus.novaSLA.appMenu

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.SpringSecurityService
import grails.rest.RestfulController
import grails.transaction.Transactional
import static org.springframework.http.HttpStatus.*

import co.dedalus.novaSLA.appMenu.*
import co.dedalus.novaSLA.model.*

@Secured(['ROLE_ADMIN'])
@Transactional(readOnly = true)
class ViewGroupController extends RestfulController{

    def springSecurityService

    ApplicationApiModel applicationApiModel = new ApplicationApiModel()
    TransactionalInformation transactionalInfo = new TransactionalInformation()

    // def  applicationApiModel
    // def  transactionalInfo

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['isFullyAuthenticated()'])
    @Override
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        max = params.max ?: 10
        def offset = params.offset ?: 0

        def dataItems = []
        
        try {
            ViewGroup.list().each { vg -> 
                dataItems.add ([
                    'id': vg.id,
                    'Module': vg.module,
                    'Group': vg.groupName
                ])
            }
            transactionalInfo.returnStatus = true;
            transactionalInfo.returnMessage.add("OK")
        }
        catch(Exception e) {
            transactionalInfo.returnStatus = false;
            transactionalInfo.returnMessage.add(e.getMessage() );                    
        }

        applicationApiModel.returnMessage = transactionalInfo.returnMessage
        applicationApiModel.returnStatus = transactionalInfo.returnStatus;
        applicationApiModel.isAuthenicated = springSecurityService.isLoggedIn()

        if (transactionalInfo.returnStatus == false) {
            applicationApiModel.returnMessage.add (
                'Error leyendo el menú de la aplicación. ')

           respond applicationApiModel as JSON, [status: BAD_REQUEST]
        }

        applicationApiModel.dataItems = dataItems;
        applicationApiModel.totalRows = dataItems.size()
        applicationApiModel.currentOffset = offset
        applicationApiModel.currentMax = max

        respond (['data': applicationApiModel] as JSON, [status: OK])              
    }

    @Transactional
    def save(ViewGroup viewGroupInstance) {
        if (viewGroupInstance == null) {
            render status: NOT_FOUND
            return
        }

        viewGroupInstance.validate()
        if (viewGroupInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        viewGroupInstance.save flush:true
        respond viewGroupInstance, [status: CREATED]
    }

    @Transactional
    def update(ViewGroup viewGroupInstance) {
        if (viewGroupInstance == null) {
            render status: NOT_FOUND
            return
        }

        viewGroupInstance.validate()
        if (viewGroupInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        viewGroupInstance.save flush:true
        respond viewGroupInstance, [status: OK]
    }

    @Transactional
    def delete(ViewGroup viewGroupInstance) {

        if (viewGroupInstance == null) {
            render status: NOT_FOUND
            return
        }

        viewGroupInstance.delete flush:true
        render status: NO_CONTENT
    }
}
