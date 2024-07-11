

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/balancecode")
public class balancecode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public balancecode() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
		    long ano = Long.parseLong(request.getParameter("acno"));
		    String name = request.getParameter("name");
		    String password = request.getParameter("password");

		    
		    Class.forName("oracle.jdbc.driver.OracleDriver");
		    
		    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "webdb", "webdb");

		    PreparedStatement ps = con.prepareStatement("SELECT ano, name, amount, adddress, phno, status FROM bank WHERE name = ? AND ano = ? AND password = ?");
		    ps.setString(1, name);
		    ps.setLong(2, ano);
		    ps.setString(3, password);

		    ResultSet rs = ps.executeQuery();
		    String s = "";
		    if (rs.next()) {
		        s = rs.getString("status");
		    }

		    if (s.equals("deactivate")) {
		        out.println("<h1 style='color:green'>Your account is deactivated. Unable to fetch the balance.</h1>");
		    } else {
		        out.println("<h1 style='color: blue;'>Welcome " + name + "</h1><br>");

		        ResultSetMetaData rss = rs.getMetaData();
		        int columnCount = rss.getColumnCount();
		        out.println("<table>");
		        out.println("<tr>");
		        for (int i = 1; i <= columnCount; i++) {
		            out.println("<h1 style='color:green'>"+"<th>" + rss.getColumnName(i) + "</th>"+"</h1");
		        }
		        out.println("</tr>");

		        do {
		            out.println("<tr>");
		            for (int j = 1; j <= columnCount; j++) {
		                out.println("<h1 style='color:green'>"+"<td>" + rs.getString(j) + "</td>"+"</h1>");
		            }
		            out.println("</tr>");
		        } while (rs.next());
		        
		        out.println("</table>");
		    }

		    rs.close();
		    ps.close();
		    con.close();

		} catch (Exception e) {

		    out.println(e);
		}

	}

}
