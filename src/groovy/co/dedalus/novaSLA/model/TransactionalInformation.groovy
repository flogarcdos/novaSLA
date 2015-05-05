package co.dedalus.novaSLA.model

class TransactionalInformation {
	Boolean returnStatus
	List<String> returnMessage
	Hashtable validationErrors;
	int currentMax;  		//totalPages;
	int currentOffset;   //pageSize;
	int totalRows;  		// totalCount
	Boolean isAuthenicated;

	TransactionalInformation() {
		this.returnStatus = setReturnStatus(false);
		this.returnMessage = []
		this.isAuthenicated = false
	}
}
