package co.dedalus.novaSLA.appMenu

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ViewsController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Views.list(params), [status: OK]
    }

    @Transactional
    def save(Views viewsInstance) {
        if (viewsInstance == null) {
            render status: NOT_FOUND
            return
        }

        viewsInstance.validate()
        if (viewsInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        viewsInstance.save flush:true
        respond viewsInstance, [status: CREATED]
    }

    @Transactional
    def update(Views viewsInstance) {
        if (viewsInstance == null) {
            render status: NOT_FOUND
            return
        }

        viewsInstance.validate()
        if (viewsInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        viewsInstance.save flush:true
        respond viewsInstance, [status: OK]
    }

    @Transactional
    def delete(Views viewsInstance) {

        if (viewsInstance == null) {
            render status: NOT_FOUND
            return
        }

        viewsInstance.delete flush:true
        render status: NO_CONTENT
    }
}
