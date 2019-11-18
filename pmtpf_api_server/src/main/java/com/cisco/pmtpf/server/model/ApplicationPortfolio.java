package com.cisco.pmtpf.server.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationPortfolio implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("sys_id")
	private String appPortfolioId;
	@JsonProperty("name")
	private String appPortfolioName;

	public String getAppPortfolioId() {
		return appPortfolioId;
	}

	public void setAppPortfolioId(String appPortfolioId) {
		this.appPortfolioId = appPortfolioId;
	}

	public String getAppPortfolioName() {
		return appPortfolioName;
	}

	public void setAppPortfolioName(String appPortfolioName) {
		this.appPortfolioName = appPortfolioName;
	}

	public static ApplicationPortfolio getEmptyObject() {
		return new ApplicationPortfolio();
	}

}
