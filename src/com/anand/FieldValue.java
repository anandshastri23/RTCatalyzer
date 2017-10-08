package com.anand;

public class FieldValue {
	
	public String field;
	public Integer value;
	
	public FieldValue(String field, Integer value) {
		super();
		this.field = field;
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}


	
}
