package weblogic.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.servlet.http.FutureResponseModel;

public abstract class FutureResponseServlet extends HttpServlet implements FutureResponseModel {
   public void service(HttpServletRequest req, HttpServletResponse rsp) throws IOException, ServletException {
      this.service(req, (FutureServletResponse)rsp);
   }

   public abstract void service(HttpServletRequest var1, FutureServletResponse var2) throws IOException, ServletException;
}
