package com.anand;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet("/ContactServlet")
public class ContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connector connector;

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
			List<Fields> fields = connector.getInputfields1(id, dataSource);
			
			for (int i = 0; i < fields.size(); i++) {
				System.out.println("Field name:" + fields.get(i).fieldDesc);
			}
			
			request.setAttribute("fields", fields);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/list-input-fields.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}