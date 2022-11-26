package weblogic.servlet.jsp;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.HttpJspPage;

public abstract class JspBase implements Servlet, HttpJspPage {
   protected ServletConfig _servletConfig;

   public final void init(ServletConfig cfg) throws ServletException {
      this._servletConfig = cfg;
      this.jspInit();
      this._jspInit();
   }

   public ServletConfig getServletConfig() {
      return this._servletConfig;
   }

   public final void service(ServletRequest rq, ServletResponse rp) throws ServletException, IOException {
      this._jspService((HttpServletRequest)rq, (HttpServletResponse)rp);
   }

   public String getServletInfo() {
      return "Generated JSP Servlet: class=" + this.getClass().getName();
   }

   public final void destroy() {
      this.jspDestroy();
      this._jspDestroy();
   }

   public void jspInit() {
   }

   protected void _jspInit() {
   }

   public void jspDestroy() {
   }

   protected void _jspDestroy() {
   }

   public abstract void _jspService(HttpServletRequest var1, HttpServletResponse var2) throws ServletException, IOException;
}
