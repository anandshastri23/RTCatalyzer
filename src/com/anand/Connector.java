package com.anand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

public class Connector {

	private DataSource dataSource;

	public Connector(DataSource theDataSource){
		this.dataSource = theDataSource;
	}
	
	public List<Fields>  CollectOutputFields() throws Exception{
		List<Fields> fields = new ArrayList<Fields>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRS = null;
		try {
			myConn = dataSource.getConnection();

			String sql = "select * from fields where Field_Type = 'O' ";

			myStmt = myConn.createStatement();

			myRS = myStmt.executeQuery(sql);

			while(myRS.next()){
				Fields field = new Fields(myRS.getInt("Field_ID"),myRS.getString("Field_Desc"),myRS.getString("Field_Type"),myRS.getString("Ref_ID"));
				fields.add(field);
			}
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
		finally{
			close(myConn,myStmt,myRS);
		}
		return fields;
	}
	

	
	public List<Fields> getInputfields1(String id, DataSource dataSource){
		List<Integer> ruleIDs = new ArrayList<Integer>();
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRS = null;
		
		System.out.println("id:"+id);
		
		int intfieldId = Integer.parseInt(id);
		
		try{
			myConn = dataSource.getConnection();

			String sql = " select Rule_ID from Business_rules where Output_field_ID=? ";

			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1,intfieldId);

			myRS = myStmt.executeQuery();

			while(myRS.next()){
			
				int ID = (myRS.getInt("Rule_ID"));
				ruleIDs.add(ID);
				System.out.println("ID"+ID);
			}
		
		}
		catch(Exception exe){
			exe.printStackTrace();	
		}
		finally{
			close(myConn,myStmt, null);
		}
		List<Fields> fields = getInputFields2(ruleIDs);
		return fields;
	}

	public List<Fields> getInputFields2(List<Integer> ruleIDs){
		List<Integer> fieldIDs = new ArrayList<Integer>();
		List<Fields> fields = new ArrayList<Fields>();
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRS = null;

		int temp=0;
		
		for (int i = 0; i < ruleIDs.size(); i++) {

			try{
				myConn = dataSource.getConnection();

				String sql = " select Field_ID from Rule_Field_ID where Business_rule_ID=? ";

				myStmt = myConn.prepareStatement(sql);
				temp = ruleIDs.get(i);
				myStmt.setInt(1,temp);
				myRS = myStmt.executeQuery();

				while(myRS.next()){
				
					int ID = (myRS.getInt("Field_ID"));
					fieldIDs.add(ID);
				}
			
				Set<Integer> hs = new HashSet<>();
				hs.addAll(fieldIDs);
				fieldIDs.clear();
				fieldIDs.addAll(hs);
				
			}
			catch(Exception exe){
				exe.printStackTrace();	
			}
			finally{
				close(myConn,myStmt, null);
			}
			
		}
		
	for (int i = 0; i < fieldIDs.size(); i++){
		
		System.out.println("final list "+i+":"+fieldIDs.get(i));
			try{
				myConn = dataSource.getConnection();

				String sql = " select * from Fields where Field_ID=? ";

				myStmt = myConn.prepareStatement(sql);
				temp = fieldIDs.get(i);
				myStmt.setInt(1,temp);
				myRS = myStmt.executeQuery();

				while(myRS.next()){
				
					Fields field = new Fields(myRS.getInt("Field_ID"),myRS.getString("Field_Desc"),myRS.getString("Field_Type"),myRS.getString("Ref_ID"));
					fields.add(field);
				}
						
			}
			catch(Exception exe){
				exe.printStackTrace();	
			}
			finally{
				close(myConn,myStmt, null);
			}
			
		}
		
	return fields;
	}
	
	public List<String> getCompRule(int id, DataSource dataSource){
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRS = null;

		List<String> compRule = new ArrayList<String>();
		
		try{
			myConn = dataSource.getConnection();

			String sql = " select Comp_Rule from Business_rules where Output_field_ID=? ";

			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1,id);

			myRS = myStmt.executeQuery();
	
			while(myRS.next()){
			
				String comprule = (myRS.getString("Comp_Rule"));
				compRule.add(comprule);
			}
		
		}
		catch(Exception exe){
			exe.printStackTrace();	
		}
		finally{
			close(myConn,myStmt, null);
		}
		return compRule;
	}


	public List<Integer> getRuleIDs(String id, DataSource dataSource){
		List<Integer> ruleIDs = new ArrayList<Integer>();
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRS = null;
		
		System.out.println("id:"+id);
		
		int intfieldId = Integer.parseInt(id);
		
		try{
			myConn = dataSource.getConnection();

			String sql = " select Rule_ID from Business_rules where Output_field_ID=? ";

			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1,intfieldId);

			myRS = myStmt.executeQuery();

			while(myRS.next()){
			
				int ID = (myRS.getInt("Rule_ID"));
				ruleIDs.add(ID);
				System.out.println("ID"+ID);
			}
		
		}
		catch(Exception exe){
			exe.printStackTrace();	
		}
		finally{
			close(myConn,myStmt, null);
		}
		
		return ruleIDs;
	}

	public List<String> getRules(List<Integer> ruleIDs, DataSource dataSource){
		List<String> businessRules = new ArrayList<String>();
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRS = null;
		for (int i =0; i <ruleIDs.size();i++){
		try{
			myConn = dataSource.getConnection();

			String sql = " select Decisions from Business_rules where Rule_ID=? ";

			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1,ruleIDs.get(i));

			myRS = myStmt.executeQuery();

			while(myRS.next()){
			
				String rule = (myRS.getString("Decisions"));
				businessRules.add(rule);

			}
		
		}
		catch(Exception exe){
			exe.printStackTrace();	
		}
		finally{
			close(myConn,myStmt, null);
		}
		}
		return businessRules;
	}
	private void close(Connection myConn, Statement myStmt, ResultSet myRS) {
		try{
			if (myRS != null){
				myRS.close();
			}
			if (myStmt != null){
				myStmt.close();
			}
			if (myConn != null){
				myConn.close(); //doesn't really close the connection, it will be made available for others
			}

		}
		catch(Exception exc){
			exc.printStackTrace();
		}

	}
	
}

	