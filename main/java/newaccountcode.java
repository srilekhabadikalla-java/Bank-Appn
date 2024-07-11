

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class newaccountcode
 */
@WebServlet("/newaccountcode")
public class newaccountcode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public newaccountcode() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		try {
			long acno=Long.parseLong(request.getParameter("acno"));
			String name=request.getParameter("name");
			String password=request.getParameter("password");
			String cpassword=request.getParameter("cpassword");
			double amount=Double.parseDouble(request.getParameter("amount"));
			String address=request.getParameter("address");
			long phno=Long.parseLong(request.getParameter("phno"));
			String status="activated";
			
	     	Class.forName("oracle.jdbc.driver.OracleDriver");
		    Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","webdb","webdb");
		    PreparedStatement ps=con.prepareStatement("insert into bank values(?,?,?,?,?,?,?,?)");
		    ps.setLong(1, acno);
		    ps.setString(2, name);
		    ps.setString(3,password);
		    ps.setString(4,cpassword);
		    ps.setDouble(5, amount);
		    ps.setString(6,address);
		    ps.setLong(7, phno); 
		    ps.setString(8, status);
		    ps.executeUpdate();
		    out.println("insert successfully");
		    con.close();
		}
		catch(Exception e)
		{
			out.println(e);
		}
	}

}
