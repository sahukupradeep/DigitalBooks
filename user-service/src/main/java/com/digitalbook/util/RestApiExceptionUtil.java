package com.digitalbook.util;

import com.digitalbook.exception.InvalidRequestException;
import com.digitalbook.exception.RequestNotFounException;
import com.digitalbook.payload.response.MessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestApiExceptionUtil {

	public static Exception throwClientException(Exception ex) {

		ObjectMapper mapper = new ObjectMapper();
		String message = ex.getMessage();
		String[] arrStr = message.split(" : ");
		String msg = "";
		try {
			MessageResponse messageResponse = mapper.readValue(message.trim().substring(7, message.length()),
					MessageResponse.class);
			msg = messageResponse.getMessage();
		} catch (Exception e) {

			msg = message;
		}

		switch (arrStr[0].trim()) {
		case "400":
			return new InvalidRequestException(msg);
		case "404":
			return new RequestNotFounException(msg);
		case "500":
			return new Exception(msg);

		default:
			return ex;
		}
	}

}
