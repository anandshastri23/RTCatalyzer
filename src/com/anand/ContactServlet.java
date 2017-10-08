package com.anand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

@WebServlet("/ContactServlet")
public class ContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connector connector;
	public int outputID = 0;
	public List<Integer> inputIDs = new ArrayList<Integer>();
	public List<String> inputRefIDs = new ArrayList<String>();
	public List<Fields> fieldsGlobal = new ArrayList<Fields>();
	public List<Integer> ruleIDsGlobal = new ArrayList<Integer>();
	
	@Resource(name="jdbc/RTCatalyzer")
	private DataSource dataSource;

	
	@Override
	public void init() throws ServletException {
		super.init();

		try{
			connector = new Connector(dataSource);
		}
		catch(Exception exc){
			throw new ServletException(exc);
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getParameter("hidden");
		if ( command == null){
			try {
				listfields(request,response);
			}catch (Exception exe) {
				throw new ServletException(exe);
			}
		}
		else {
		try {
			switch(command){
			case "select":
				getOutputFieldDetails(request,response);
				break;
			case "calculate":
				collectFieldValues(request,response);
				break;
			default:
				listfields(request, response);
			}
		}
		catch (Exception exe) {
			throw new ServletException(exe);
		}
		}
	}

	public void listfields(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			
			List<Fields> fields = connector.CollectOutputFields();
	
			request.setAttribute("fields", fields);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/list-fields.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void getOutputFieldDetails(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			String id = request.getParameter("fieldId");	
			outputID = Integer.parseInt(id);
			
			List<Fields> fields = connector.getInputfields1(id, dataSource);
			fieldsGlobal = fields;
			
			List<Integer> ruleIDs = connector.getRuleIDs(id, dataSource);
			ruleIDsGlobal = ruleIDs;
			
			for (int i = 0; i < fields.size(); i++) {
				System.out.println("Field name:" + fields.get(i).fieldDesc);
				inputIDs.add(fields.get(i).fieldId);
				inputRefIDs.add(fields.get(i).refID);
			}
			
	
			
			request.setAttribute("fields", fields);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/list-input-fields.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void collectFieldValues(HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("Output field ID" +outputID);
		List<String> compRule = connector.getCompRule(outputID, dataSource);
		List<Integer> results = new ArrayList<Integer>();
		List<FieldValue> fieldvalue = new ArrayList<FieldValue>();
		try {
			String[] values = request.getParameterValues("fieldvalue");	
			List<Integer> intValues = new ArrayList<Integer>();
			
			for (int i = 0; i < values.length; i++) {
				// System.out.println("Values:" + values[i]);
				
				intValues.add(Integer.parseInt(values[i]));
				
				FieldValue temp = new FieldValue(inputRefIDs.get(i),intValues.get(i)); 
				fieldvalue.add(temp);
				
				System.out.println("input field IDs:" +inputIDs.get(i));
				System.out.println("Input field Values:" +intValues.get(i));
			}
			
			for (int i = 0; i < compRule.size(); i++) {
				System.out.println("Computational rule " +i+":"+compRule.get(i));	
			}
			//Parsing begins here
			String updatedRule;
	
			
			for (int i = 0 ; i <compRule.size();i++){
				updatedRule = parseRule(compRule.get(i), fieldvalue);
				int result = calculateOutput(updatedRule);
				results.add(result);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> businessRules = connector.getRules(ruleIDsGlobal, dataSource);
		
		List<Results> fullResults = new ArrayList<Results>();
		
		for (int i =0; i<fieldvalue.size(); i++){
			Results result = new Results(fieldsGlobal.get(i).getFieldId(),fieldsGlobal.get(i).getFieldDesc(),fieldvalue.get(i).getValue(),results.get(i),businessRules.get(i));
			fullResults.add(result);
			System.out.println("result:"+fieldsGlobal.get(i).getFieldId()+fieldsGlobal.get(i).getFieldDesc()+fieldvalue.get(i).getValue()+results.get(i));
		}
		
		for (int i = fieldvalue.size(); i<(compRule.size()); i++){
			Results result = new Results(0," ",null,results.get(i),businessRules.get(i));
			fullResults.add(result);
			System.out.println("result:"+results.get(i));
		}	
		request.setAttribute("fullResults", fullResults);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/results.jsp");
		dispatcher.forward(request, response);		 

	}
	
	public String parseRule(String rule, List<FieldValue> fieldvalue){
		
		for (int i=0; i<fieldvalue.size(); i++){
			//System.out.println("Field:" +fieldvalue.get(i).getField().toString());
			//System.out.println("Value:" +fieldvalue.get(i).getValue().toString());
			rule = rule.replace(fieldvalue.get(i).getField().toString(), fieldvalue.get(i).getValue().toString());
		}
		
		return rule;
	}
	
	public int calculateOutput(String rule)throws ScriptException{
		System.out.println("UpdatedRule: " +rule );
		ScriptEngineManager mgr = new ScriptEngineManager();
	    ScriptEngine engine = mgr.getEngineByName("JavaScript");
	    System.out.println(("result new: " +engine.eval(rule)));
	    int result = (int) engine.eval(rule);
		return result;
	}
}