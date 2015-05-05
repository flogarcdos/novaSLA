package co.dedalus.novaSLA

class AppList {
	String code;
	String name;
	String type
	String isEnabled;
	String alpha1Title
	String alpha2Title
	Country country;
	Customer customer;
   static constraints = {
		code maxSize:50, blank:false
		name maxSize:200, blank:false
		type maxSize:50, blank:false, inList: ['Character', 'Date', 'Number']
		isEnabled maxSize:1, inList: ['Y','N'], blank: false
		alpha1Title maxSize: 30, nullable: true
		alpha2Title maxSize: 30, nullable: true
		country nullable: true
		customer nullable: true
		code unique:['country']
		
   }
   static mapping = {
	   table 'TOU_APP_LIST'
   }
}
