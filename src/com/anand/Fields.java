package com.anand;

public class Fields {
	public int fieldId;
	public String fieldDesc;
	public String fieldType;
	public String refID;
	
	
	public Fields(int fieldId, String fieldDesc, String fieldType, String refID) {
		super();
		this.fieldId = fieldId;
		this.fieldDesc = fieldDesc;
		this.fieldType = fieldType;
		this.refID = refID;
	}
	public int getFieldId() {
		return fieldId;
	}
	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}
	public String getFieldDesc() {
		return fieldDesc;
	}
	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getRefID() {
		return refID;
	}
	public void setRefID(String refID) {
		this.refID = refID;
	}
	
	
}
