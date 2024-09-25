package com.ibms.model;

import java.math.BigDecimal;
import java.util.Date;

public class Policy {
	
	private long policyId;
	
	private String policyType;
	
	private BigDecimal premiumAmount;
	
	private Date startDate;
	
	private Date endDate;
	
	private BigDecimal coverageAmount;

	public Policy(long policyId, String policyType, BigDecimal premiumAmount, Date startDate, Date endDate,
			BigDecimal coverageAmount) {
		super();
		this.policyId = policyId;
		this.policyType = policyType;
		this.premiumAmount = premiumAmount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.coverageAmount = coverageAmount;
	}

	public long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(long policyId) {
		this.policyId = policyId;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public BigDecimal getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(BigDecimal premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getCoverageAmount() {
		return coverageAmount;
	}

	public void setCoverageAmount(BigDecimal coverageAmount) {
		this.coverageAmount = coverageAmount;
	}

}
