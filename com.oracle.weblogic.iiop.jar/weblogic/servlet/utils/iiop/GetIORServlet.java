package weblogic.servlet.utils.iiop;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.corba.cos.naming.RootNamingContextImpl;
import weblogic.iiop.ior.IOR;

public final class GetIORServlet extends HttpServlet {
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      IOR ior = RootNamingContextImpl.getInitialReference().getRootContextForCurrentPartition().getIOR();
      PrintWriter out = response.getWriter();
      out.println("host IOR:");
      out.println(ior.stringify());
   }
}
