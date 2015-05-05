import grails.rest.render.json.JsonCollectionRenderer
import grails.rest.render.json.JsonRenderer
import grails.util.Environment

import co.dedalus.novaSLA.*
import co.dedalus.novaSLA.service.SumoJsonCollectionRenderer

// Place your Spring DSL code here
beans = {
	for (domainClass in grailsApplication.domainClasses) {
	   "json${domainClass.shortName}CollectionRenderer"(SumoJsonCollectionRenderer, domainClass.clazz) {excludes = ['class']}
	   "json${domainClass.shortName}Renderer"(JsonRenderer, domainClass.clazz) {excludes = ['class']}
	   // "hal${domainClass.shortName}CollectionRenderer"(HalJsonCollectionRenderer, domainClass.clazz)
	   // "hal${domainClass.shortName}Renderer"(HalJsonRenderer, domainClass.clazz)
	}
}
