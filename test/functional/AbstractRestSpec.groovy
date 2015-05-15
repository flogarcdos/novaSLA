
import grails.plugins.rest.client.RestBuilder
import spock.lang.Shared
import spock.lang.Specification

// import co.dedalus.novaSLA.*
// import co.dedalus.novaSLA.auth.*

abstract class AbstractRestSpec extends Specification {


    @Shared
    ConfigObject config = new ConfigSlurper().parse(new File('grails-app/conf/Config.groovy').toURL())

    @Shared
    RestBuilder restBuilder = new RestBuilder()

    @Shared
    String baseUrl = "http://localhost:8080/novaSLA"

    def sendWrongCredentials() {
        if (config.grails.plugin.springsecurity.rest.login.useRequestParamsCredentials == true) {
            restBuilder.post("${baseUrl}/auth/login?username=foo&password=bar")
        } else {
            restBuilder.post("${baseUrl}/auth/login") {
                json {
                    username = 'foo'
                    password = 'bar'
                }
            }
        }
    }

    def sendCorrectCredentials() {
        if (config.grails.plugin.springsecurity.rest.login.useRequestParamsCredentials == true) {
            restBuilder.post("${baseUrl}/auth/login?username=jimi&password=jimispassword")
        } else {
            restBuilder.post("${baseUrl}/auth/login") {
                json {
                    username = 'rfeynman'
                    password = 'password'
                }
            }
        }
    }

    // def createPerson() {
    //     def party = new Party().save() 
    //     def person = new Person(
    //           id: party
    //         , firstName: 'Brian Edward'
    //         , lastName: 'Cox'
    //         , gender: AppListElement.findByCode('M')
    //         , birhtDate: new Date().parse('yyyy-MM-dd', '1968-03-03')
    //         , docIdNum: '908098').save()
    // }

    // def createUser() {
    //     def adminRole = AppRole.findByAuthority('ROLE_ADMIN') ?: 
    //         new AppRole(authority: 'ROLE_ADMIN').save()

    //     def testUser = new AppUser(
    //            username: 'bcox'
    //           ,password: 'password'
    //           ,enabled: true
    //           ,accountExpired: false
    //           ,accountLocked: false
    //           ,passwordExpired: false
    //           ,person: Person.findByLastName('Cox') )
    //     .save(failOnError: true, flush: true)

    //     def appUserRole = new AppUserRole(appUser: testUser, appRole: adminRole).save()
    // }

}