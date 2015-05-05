package co.dedalus.novaSLA.model

class TransactionalInformation {
	Boolean returnStatus
	String returnMessage
	List<String> validationErrors;
	int currentMax;  		//totalPages;
	int currentOffset;   //pageSize;
	int totalRows;  		// totalCount
	Boolean isAuthenicated;

	TransactionalInformation() {
		this.returnStatus = setReturnStatus(false);
		this.returnMessage = ""
		this.validationErrors = []
		this.isAuthenicated = false
	}
}
