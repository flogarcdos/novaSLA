package co.dedalus.novaSLA

class AppProcessPlanTask {
	String code
	String name
	String isEnabled
	AppProcessPlan processPlan
	Integer execOrder
	// TODO: taskType (GenerateReports, CheckParameters, Approval)
	
   static constraints = {
		isEnabled maxSize:1, blank: false, inList: ['Y','N']
		code maxSize: 30, blank: false
		name maxSize: 100
		execOrder max: 99, min: 1
		
		code unique: ['processPlan']
   }
	static mapping = {
		table 'TOU_APP_PROCESS_PLAN_TASK'
	}
}
