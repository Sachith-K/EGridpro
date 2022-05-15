package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Billing;

@WebServlet("/BillingService")
public class BillingService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Billing bill = new Billing();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BillingService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String output = bill.insertBilling(      
				request.getParameter("UserID"),
				request.getParameter("UserEmail"),
				request.getParameter("Date"),
				request.getParameter("UnitsConsumed"),
				request.getParameter("PricePerUnit")); 
				response.getWriter().write(output);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method

		Map paras = getParasMap(request); 
		 
		 String output = bill.updateBilling(paras.get("hidBillIDSave").toString(),     
		    		paras.get("UserID").toString(),     
		    		paras.get("UserEmail").toString(),
		    		paras.get("Date").toString(),
		    		paras.get("UnitsConsumed").toString(),	
		 			paras.get("PricePerUnit").toString());
		 			response.getWriter().write(output);
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 Map paras = getParasMap(request); 		 
		 String output = bill.deleteBilling(paras.get("BillID").toString());  		 
		 response.getWriter().write(output);
	}
	
	// Convert request parameters to a Map
		private static Map getParasMap(HttpServletRequest request)
		{
			Map<String, String> map = new HashMap<String, String>();
			try
			{ 
				Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
				String queryString = scanner.hasNext() ?
				scanner.useDelimiter("\\A").next() : "";
				scanner.close();
				String[] params = queryString.split("&");
				for (String param : params)
				{ 
			
					String[] p = param.split("=");
					map.put(p[0], p[1]);
				}
			}
			catch (Exception e)
			{
			}
			return map;
			}
	
}
