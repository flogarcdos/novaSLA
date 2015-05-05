package co.dedalus.novaSLA.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//import co.dedalus.novaSLA.Country;

@ResponseStatus(HttpStatus.NOT_FOUND)
public final class CountryHasErrorsException extends Exception {
	private static final long serialVersionUID = 1L;

	CountryHasErrorsException(Exception e) {
        //super(String.format("Country '%d' has errors", countryId));
		super("Country has errors " + e);
    }
}

