package com.ibms.model;

import java.math.BigDecimal;
import java.util.Date;

public class Policy {
	
    private String policyName;
    private String policyType;
    private double premium;
    
    public Policy() {
    }
    

    // Constructor
    public Policy(String policyName, String policyType, double premium) {
        this.policyName = policyName;
        this.policyType = policyType;
        this.premium = premium;
    }

    // Getters
    public String getPolicyName() {
        return policyName;
    }

    public String getPolicyType() {
        return policyType;
    }

    public double getPremium() {
        return premium;
    }
}
