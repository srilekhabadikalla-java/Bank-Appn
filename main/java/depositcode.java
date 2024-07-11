

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
 * Servlet implementation class depositcode
 */
@WebServlet("/depositcode")
public class depositcode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public depositcode() {
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
			double am=Double.parseDouble(request.getParameter("amount"));
			String s1="";
			double amount=0.0;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","webdb","webdb");
			
			PreparedStatement ps=con.prepareStatement("select amount,status from bank where name=? and ano=? and password=?");
			ps.setString(1, name);
			ps.setLong(2, ano);
			ps.setString(3, password);
			ResultSet rs=ps.executeQuery();
			
			 if (rs.next()) {
                 
                 double currentAmount = rs.getDouble("amount");
                 s1=rs.getString("status");
                 amount = currentAmount + am;
			 }
			 if (s1.equals("deactivate"))
			 {
				 out.println("Your Account is deactivated.Unable To Deposit.");
			 }
			 else
			 {
			PreparedStatement pss=con.prepareStatement("update bank set amount=? where ano=? and name=? and password=?");
			
			pss.setDouble(1, amount);
			pss.setLong(2, ano);
			pss.setString(3, name);
			pss.setString(4, password);
			
			
			int i=pss.executeUpdate();
			
			out.println(i+"<font color=blue size=5>Deposit Successfully</font>");
			out.println("<h1 style='color:green'>"+"your balance has increase"+amount+"</h1>");
			con.close();
	}
		}
		catch(Exception e)
		{
			out.println(e);
		}
	}

}
