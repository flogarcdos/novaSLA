package co.dedalus.novaSLA

class AppListElement {
   String code;
   String description;
   String alpha1;
   String alpha2;
   String isEnabled;
   AppList appList;
   Date validDate
   static constraints = {
		code maxSize:30, blank: false
		description maxSize:200, blank: false
		alpha1 maxSize: 30, nullable: true
		alpha2 maxSize: 30, nullable: true
		isEnabled maxSize:1, blank: false, inList: ['Y', 'N']
		validDate nullable: true
		code unique: ['appList', 'validDate']
   }
   static mapping = {
	   table 'TOU_APP_LIST_ELEMENT'
   }
}
