// class BootStrap {

//     def init = { servletContext ->
//     }
//     def destroy = {
//     }
// }


import grails.util.Environment
import org.apache.commons.logging.LogFactory

import co.dedalus.novaSLA.*
import co.dedalus.novaSLA.appMenu.*
import co.dedalus.novaSLA.auth.*

class BootStrap {
	def grailsApplication
	
	private static final log = LogFactory.getLog(this)

   def init = { servletContext ->
		if (Environment.PRODUCTION != Environment.current) {
			
			countryBootStrap()
			customerBootStrap()
			appListBootStrap()
			appListElementBootStrap()
			appProcessPlanBS()
			appProcessPlanTask()
			applicationMenuBS()
			personBootstrap()
			userBootstrap()
		}

   }
   def destroy = {
   }

	def countryBootStrap() {
		if ( !Country.count() ) {
			def filePath = "country.csv"
			def myFile = grailsApplication.mainContext.getResource("classpath:resources/$filePath").file
			def i = 1
			myFile.splitEachLine(';') {
				fields ->
				 def country = new Country( code:	fields[0], name: fields[1] )
				 country.validate()
	
				if (country.hasErrors()){
					log.debug("could not import line ${i} due to ${country.errors}") }
				else {
					log.debug("importing line ${i}: ${country.toString()}")
					country.save(failOnError: true, flush: true)
				}
				++i
			}
		}
	}

	def customerBootStrap() {
		if ( !Customer.count() ) {
			log.debug("customer.count is zero")
			def filePath = "customer.csv"
			def myFile = grailsApplication.mainContext.getResource("classpath:resources/$filePath").file
			def i = 1
			myFile.splitEachLine(';') {
				fields ->
			 def customer = new Customer(
				   name: fields[0]
				 , docId: fields[1]
				 , logoPath: fields[2]
				 , startDate: new Date().parse('yyyy-MM-dd', fields[3])
				 , agreementTerm: fields[4]
				 , isEnabled: fields[5]
				 , accessType: fields[6]
				 , isPortalInd : fields[7]
				 , country: Country.findByName('Colombia') )
			 customer.validate()

				if (customer.hasErrors()){
					log.debug("Could not import line ${i} due to ${customer.errors}") }
				else {
					log.debug("Importing line ${i}: ${customer.toString()}")
					customer.save(failOnError: true, flush: true)
				}
				++i
			}
		}
   }

	def appListBootStrap() {
		if ( !AppList.count() ) {
			log.debug("appList.count is zero")
			def filePath = "AppList.csv"
			def myFile = grailsApplication.mainContext.getResource("classpath:resources/$filePath").file
			def i = 1
			myFile.splitEachLine(';') {
				fields ->
					 def appList = new AppList(
						   code: fields[0]
						 , name: fields[1]
						 , type: fields[2]
						 , isEnabled: fields[3]
						 , alpha1Title: fields[4]
						 , alpha2Title : fields[5]
						 , country: Country.findByName(fields[6])
						 , customer: null )
			 	appList.validate()

				if (appList.hasErrors()){
					log.debug("Could not import line ${i} due to ${appList.errors}") }
				else {
					log.debug("Importing line ${i}: ${appList.toString()}")
					appList.save(failOnError: true, flush: true)
				}
				++i
			}
		}
   }

	def appListElementBootStrap() {
		if ( !AppListElement.count() ) {
			log.debug("appListElement.count is zero")
			def filePath = "AppListElement.csv"
			def myFile = grailsApplication.mainContext.getResource("classpath:resources/$filePath").file
			def i = 1
			myFile.splitEachLine(';') {
				fields ->
					 def appListElement = new AppListElement(
						   code: fields[0]
						 , description: fields[1]
						 , alpha1: fields[2]
						 , alpha2 : fields[3]
						 , isEnabled: fields[4]
						 , appList: AppList.findByCode(fields[5])
						 , validDate: null )
				 appListElement.validate()

				if (appListElement.hasErrors()){
					log.debug("Could not import line ${i} due to ${appListElement.errors}") }
				else {
					log.debug("Importing line ${i}: ${appListElement.toString()}")
					appListElement.save(failOnError: true, flush: true)
				}
				++i
			}
		}
   }

	def appProcessPlanBS() {
		if ( !AppProcessPlan.count() ) {
			log.debug("appProcessPlan.count is zero")
			def filePath = "AppProcessPlan.csv"
			def myFile = grailsApplication.mainContext.getResource("classpath:resources/$filePath").file
			def i = 1
			myFile.splitEachLine(';') {
				fields ->
					def appProcessPlan = new AppProcessPlan(
						  code: fields[0]
						, name: fields[1]
						, isEnabled: fields[2]
						, processType: AppListElement.findByCode(fields[3])
						, associatedBPM : fields[4]
					)
				 appProcessPlan.validate()

				if (appProcessPlan.hasErrors()){
					log.debug("Could not import line ${i} due to ${appProcessPlan.errors}") }
				else {
					log.debug("Importing line ${i}: ${appProcessPlan.toString()}")
					appProcessPlan.save(failOnError: true, flush: true)
				}
				++i
			}
		}
   }
	
	def appProcessPlanTask() {
		if ( !AppProcessPlanTask.count() ) {
			log.debug("appProcessPlanTask.count is zero")
			def filePath = "AppProcessPlanTask.csv"
			def myFile = grailsApplication.mainContext.getResource("classpath:resources/$filePath").file
			def i = 1
			myFile.splitEachLine(';') {
				fields ->
					def appProcessPlanTask = new AppProcessPlanTask(
						  code: fields[0]
						, name: fields[1]
						, isEnabled: fields[2]
						, processPlan: AppProcessPlan.findByCode(fields[3])
						, execOrder : fields[4]
					)
				 appProcessPlanTask.validate()

				if (appProcessPlanTask.hasErrors()){
					log.debug("Could not import line ${i} due to ${appProcessPlanTask.errors}") }
				else {
					log.debug("Importing line ${i}: ${appProcessPlanTask.toString()}")
					appProcessPlanTask.save(failOnError: true, flush: true)
				}
				++i
			}
		}
   }

   def applicationMenuBS() {
   	println ('**** applicationMenuBS')
   	if ( !ViewGroup.count() ) {
			log.debug("viewGroup.count is zero")
			println ("**** viewGroup.count is zero")
			def filePath = "AppMenu.csv"
			def myFile = grailsApplication.mainContext.getResource("classpath:resources/$filePath").file
			def i = 1
			myFile.splitEachLine(';') {
				fields ->
					def viewGroup = new ViewGroup(
						  groupName: fields[0]
						, module: fields[1]
					)
				 viewGroup.validate()

				if (viewGroup.hasErrors()){
					log.debug("Could not import line ${i} due to ${viewGroup.errors}") 
					println ("**** Could not import line ${i} due to ${viewGroup.errors}") }
				else {
					log.debug("Importing line ${i}: ${viewGroup.toString()}")
					viewGroup.save(failOnError: true, flush: true)
				}
				++i
			}

			log.debug("viewState.count is zero")
			filePath = "ViewState.csv"
			myFile = grailsApplication.mainContext.getResource("classpath:resources/$filePath").file
			i = 1
			myFile.splitEachLine(';') {
				fields ->
					def viewState = new ViewState(
						  isStartUp: fields[0]
						, name: fields[1]
						, url: fields[2]
						, parentName: fields[3]
						, isAbstract: fields[4]
						, stateOrder: fields[5]
						, viewGroup: ViewGroup.findByModule('home')
					)
				 viewState.validate()

				if (viewState.hasErrors()){
					log.debug("Could not import line ${i} due to ${viewState.errors}") 
					println(" **** Could not import line ${i} due to ${viewState.errors}") }
				else {
					log.debug("Importing line ${i}: ${viewState.toString()}")
					viewState.save(failOnError: true, flush: true)
				}
				++i
			}

			log.debug("views.count is zero")
			filePath = "Views.csv"
			myFile = grailsApplication.mainContext.getResource("classpath:resources/$filePath").file
			i = 1
			myFile.splitEachLine(';') {
				fields ->
					def views = new Views(
						  name: fields[0]
						, controllerName: fields[1]
						, controllerUrl: fields[2]
						, controllerAs: fields[3]
						, templateUrl: fields[4]
						, data: fields[5]
						, viewState: ViewState.findByName(fields[0])
						, requiresAuthentication: true
					)
				 views.validate()

				if (views.hasErrors()){
					log.debug("Could not import line ${i} due to ${views.errors}")
					println (" ****** Could not import line ${i} due to ${views.errors}") }
				else {
					log.debug("Importing line ${i}: ${views.toString()}")
					views.save(failOnError: true, flush: true)
				}
				++i
			}


   	}
   }

   def personBootstrap() {
   	if ( !Person.count() ) {
			log.debug("person.count is zero")
			def filePath = "Person.csv"
			def myFile = grailsApplication.mainContext.getResource("classpath:resources/$filePath").file
			def i = 1
			myFile.splitEachLine(';') {
				fields ->
					def party = new Party().save(failOnError: true, flush: true)
					def person = new Person(
						  id: party.id
						, firstName: fields[0]
						, lastName: fields[1]
						, gender: AppListElement.findByCode(fields[2])
						, birthDate: convertToDate( fields[3] )
						, docIdNum : fields[4]
					)
				 person.validate()

				if (person.hasErrors()){
					log.debug("Could not import line ${i} due to ${person.errors}") 
				}
				else {
					log.debug("Importing line ${i}: ${person.toString()}")
					person.save(failOnError: true, flush: true)
				}
				++i
			}
   	}
   }

   def userBootstrap() {
   	if (!AppUser.count()) {
   		// if (!AppRole.count()) {
		   	def adminRole = new AppRole(authority: 'ROLE_ADMIN').save(failOnError: true, flush: true)
		      def userRole = new AppRole(authority: 'ROLE_USER').save(failOnError: true, flush: true)
   		// }

	      def testUser = new AppUser(
				 username: 'rfeynman'
				,password: 'password'
				,enabled: true
				,accountExpired: false
				,accountLocked: false
				,passwordExpired: false
				,person: Person.list(max:1, offset: new Random().nextInt(Person.count() ) )
			)
	      .save(failOnError: true, flush: true)

	      def appUserRole = new AppUserRole(appUser: testUser, appRole: adminRole).save(failOnError: true, flush: true)

	      assert AppUser.count() == 1
	      assert AppRole.count() == 2
	      assert AppUserRole.count() == 1
   	}
   }

   public static Date convertToDate(String input) {
      Date date = null;
      if(null != input && input != '') {
         date = new Date().parse('yyyy-MM-dd', input)
      }
      return date;
   }
}
