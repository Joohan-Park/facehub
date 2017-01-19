package com.pikitori.util;

public class JSONResult {

	private String result;  // "success" or "fail"
	private String message; // result�� "fail" �� ��  ����
	private Object data;    // result�� "succes" �� ��  �����ؾ� �� ������ ��ü

	public static JSONResult error( String message ) {
		return new JSONResult( false, null, message );
	}
	
	public static JSONResult success( Object data ) {
		
		return new JSONResult( true, data, null );
	}
	
	private JSONResult() {
	}

	private JSONResult( boolean isSuccess, Object data, String message ) {
		result = isSuccess ? "success" : "fail";
		this.data = data;
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}
}
