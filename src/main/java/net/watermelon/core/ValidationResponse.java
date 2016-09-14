package net.watermelon.core;

import java.util.List;

public class ValidationResponse {
	private String status;
	 private List<ValidationMessage> errorMessageList;

	 public String getStatus() {
	   return status;
	 }
	 public void setStatus(String status) {
	   this.status = status;
	 }
	 public List <ValidationMessage>getErrorMessageList() {
	   return this.errorMessageList;
	 }
	 public void setErrorMessageList(List<ValidationMessage> errorMessageList) {
	   this.errorMessageList = errorMessageList;
	 }
	 
	

}
