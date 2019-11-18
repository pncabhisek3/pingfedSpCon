package com.cisco.pmtpf.server.commons;

import org.apache.commons.lang3.StringUtils;

public class ApiResponse<T>  {

	private boolean isSuccess = false;
	private String statusMsg;
	private T data;

	public ApiResponse(String statusMsg, T data) {
		this.statusMsg = statusMsg;
		this.data = data;
	}

	public ApiResponse(T data) {
		this(true, null);
		this.data = data;
	}

	public ApiResponse(boolean isSuccess, String statusMsg) {
		this.statusMsg = StringUtils.isNotBlank(statusMsg) ? statusMsg : ErrorCodeEnum.Invalid_Request.toString();
		if (true == isSuccess && StringUtils.isBlank(statusMsg))
			this.statusMsg = "Success";
	}

	public ApiResponse(boolean isSuccess, String statusMsg, T data) {
		this(isSuccess, statusMsg);
		this.data = data;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ApiResponse [isSuccess=" + isSuccess + ", statusMsg=" + statusMsg + ", data=" + data + "]";
	}

}
