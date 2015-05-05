package co.dedalus.novaSLA

class CustomerProcessPlan {
	String code
	String name
	String isEnabled
	Customer customer
	AppProcessPlan appProcessPlan

	static constraints = {
		isEnabled maxSize:1, blank: false, inList: ['Y','N']
		code maxSize: 30, blank: false
		name maxSize: 200, blank: false
		
		code unique: ['customer']
	}
	static mapping = {
		table 'TOU_CUSTOMER_PROCESS_PLAN'
	}
}
