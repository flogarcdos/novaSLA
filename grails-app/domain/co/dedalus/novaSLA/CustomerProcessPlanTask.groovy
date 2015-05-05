package co.dedalus.novaSLA

class CustomerProcessPlanTask {
	String name
	String duration // ISO 8601 expression 
	String alert
	String candidateUser  // TODO: One of CandidateUser, CandidateGroup
	String checkList	// TODO: appListElement
	String isMultiInstance // TODO:  MultiInstance or sub-task?
	 
	AppProcessPlanTask appProcessPlanTask
	CustomerProcessPlan customerProcessPlan
	
	static constraints = {
		name maxSize:30
		duration maxSize:15, blank: false // TODO: duration matches: "[a-zA-Z]+"
		alert maxSize: 4000
		candidateUser maxSize: 30
		checkList nullable:true, blank:true
		isMultiInstance maxSize:1, blank: false, inList: ['Y','N']
		
		appProcessPlanTask unique: ['customerProcessPlan']
	}
	static mapping = {
		table 'TOU_CUSTOMER_PROCESS_PLAN_TASK'
	}
}
