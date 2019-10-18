package org.awsaf.javabrains.messenger.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {
	
	private String documentation;
	private String errorMessage;
	private int errorCode;
	
	
	public ErrorMessage() {
		
	}
	
	public ErrorMessage(String documentation, String errorMessage, int errorCode) {
		this.documentation = documentation;
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	
	
	
}
