package co.dedalus.novaSLA.model

class TransactionalInformation {
	Boolean returnStatus
	List<String> returnMessage
	Hashtable<String, String> validationErrors;
	int currentMax;  		//totalPages;
	int currentOffset;   //pageSize;
	int totalRows;  		// totalCount
	Boolean isAuthenicated;

	TransactionalInformation() {
		this.returnStatus = setReturnStatus(false);
		this.returnMessage = []
		this.validationErrors = new Hashtable();
		this.isAuthenicated = false
	}
}
