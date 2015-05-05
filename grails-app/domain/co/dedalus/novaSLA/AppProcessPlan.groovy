package co.dedalus.novaSLA

class AppProcessPlan {
	String code
	String name
	String isEnabled
	AppListElement processType // ['Payroll', 'Social Security', 'Accruals']
	//AppListElement frequency //(Quincenal, Mensual, Semestral, Anual) TODO: number of periodos per year
	String associatedBPM //Activiti BPM Process
	
	static constraints = {
		isEnabled maxSize:1, blank: false, inList: ['Y','N']
		code maxSize: 30, blank: false, unique: true
		name maxSize: 100, blank: false
	}
	static mapping = {
		table 'TOU_APP_PROCESS_PLAN'
	}
}
