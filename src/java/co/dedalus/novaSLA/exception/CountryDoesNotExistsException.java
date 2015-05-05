package co.dedalus.novaSLA.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public final class CountryDoesNotExistsException extends Exception {
	private static final long serialVersionUID = 1L;

	CountryDoesNotExistsException(Long countryId) {
        super(String.format("Country '%d' does not exist", countryId));
    }
}


