package com.anand;

public class Results {
	public int fieldID;
	public String fieldDesc;
	public Integer fieldValue;
	public Integer result;
	public String businessRule;
	
	public String getFieldDesc() {
		return fieldDesc;
	}
	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}
	public Integer getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(int fieldValue) {
		this.fieldValue = fieldValue;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public Results(int fieldID, String fieldDesc, Integer fieldValue, Integer result, String businessRule) {
		super();
		this.fieldDesc = fieldDesc;
		this.fieldValue = fieldValue;
		this.result = result;
		this.businessRule = businessRule;
	}
	
	
}
