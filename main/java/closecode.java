

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
 * Servlet implementation class closecode
 */
@WebServlet("/closecode")
public class closecode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public closecode() {
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
			long ano=Long.parseLong(request.getParameter("acno"));
			String name=request.getParameter("name");
			String password=request.getParameter("password");
			
			String s1="deactivate";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","webdb","webdb");
			 
			PreparedStatement pss=con.prepareStatement("update bank set status=? where ano=? and name=? and password=?");
			
			pss.setString(1, s1);
			pss.setLong(2, ano);
			pss.setString(3, name);
			pss.setString(4, password);
			
			int i=pss.executeUpdate();
			
			out.println(i+"<font color=blue size=5>Your Account is Closed Successfully</font>");
			con.close();
	}
		
		catch(Exception e)
		{
			out.println(e);
		}
	}

}
