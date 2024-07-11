

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class transfercode
 */
@WebServlet("/transfercode")
public class transfercode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public transfercode() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		try {
			long ano=Long.parseLong(request.getParameter("acno"));
			String name=request.getParameter("name");
			String password=request.getParameter("password");
			long acno=Long.parseLong(request.getParameter("accno"));
			double am=Double.parseDouble(request.getParameter("amount"));
			double currentAmount=0.0;
			double targetAmount=0.0;
			String s2="";
			String s1="";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","webdb","webdb");
			
			PreparedStatement ps=con.prepareStatement("select amount,status from bank where name=? and ano=? and password=?");
			ps.setString(1, name);
			ps.setLong(2, ano);
			ps.setString(3, password);
			ResultSet rs=ps.executeQuery();
			
			 if (rs.next()) {
                 
                 currentAmount = rs.getDouble("amount");
                 s2=rs.getString("status");
			 }
			 
			 if (s2.equals("deactivate"))
				{
					out.println("<h1 style='color:green'>"+"Your Account is Deactivated. Unable To Transfer The Money"+"<h1");
				}
				
				else
				{
			
			
			 PreparedStatement pcs=con.prepareStatement("select amount,status from bank where  ano=?");
				
				pcs.setLong(1, acno);
				
				ResultSet rss=pcs.executeQuery();
				
				 if (rss.next()) {
	                 
	                 targetAmount = rss.getDouble("amount");
	                 s1=rss.getString("status");
				 }
				
				 if (s2.equals("deactivate"))
					{
						out.println("<h1 style='color:green'>"+"Your Account is Deactivated. Unable To Transfer The Money"+"<h1");
					}
					
					else
					{
					
				 
			 if (am<currentAmount) {
				 PreparedStatement pss=con.prepareStatement("update bank set amount=? where ano=?");
				 
				 double b=currentAmount - am;
				    pss.setDouble(1, b);
					pss.setLong(2, ano);
					pss.executeUpdate();
		
					
					PreparedStatement psc=con.prepareStatement("update bank set amount=? where ano=?");
					double tb=targetAmount+am;
					psc.setDouble(1, tb);
					psc.setLong(2, acno);
					
					psc.executeUpdate();
					
					out.println("<font color=blue size=5>Transfer Successfully</font>");
					out.println("<h1 style='color:green'>"+"the target balance is "+tb+"your balance has decrease"+b+"</h1>");
			 }
					else {
						out.println("<h1 style='color:green'>"+"Transaction Failed due to insuficient funds"+"</h1>");
					}
			}
				}
					
			con.close();
	}
		catch(Exception e)
		{
			out.println(e);
		}
	}

}
