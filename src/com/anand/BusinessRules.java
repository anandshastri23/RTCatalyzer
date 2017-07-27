package com.anand;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.sql.DataSource;

public class BusinessRules {

	public int ruleId;
	public String CompRule;
	public String NarrRule;
	public int outputId;
	public String decisions;
	
	public BusinessRules(int ruleId, String compRule, String narrRule,
			int outputId, String decisions) {
		super();
		this.ruleId = ruleId;
		CompRule = compRule;
		NarrRule = narrRule;
		this.outputId = outputId;
		this.decisions = decisions;
	}

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public String getCompRule() {
		return CompRule;
	}

	public void setCompRule(String compRule) {
		CompRule = compRule;
	}

	public String getNarrRule() {
		return NarrRule;
	}

	public void setNarrRule(String narrRule) {
		NarrRule = narrRule;
	}

	public int getOutputId() {
		return outputId;
	}

	public void setOutputId(int outputId) {
		this.outputId = outputId;
	}

	public String getDecisions() {
		return decisions;
	}

	public void setDecisions(String decisions) {
		this.decisions = decisions;
	}
	
	public static void display(BusinessRules businessrule){
	
		System.out.println(businessrule.ruleId);
		System.out.println(businessrule.CompRule);
		System.out.println(businessrule.NarrRule);
		System.out.println(businessrule.outputId);
		System.out.println(businessrule.decisions);
		
	}
	
}
