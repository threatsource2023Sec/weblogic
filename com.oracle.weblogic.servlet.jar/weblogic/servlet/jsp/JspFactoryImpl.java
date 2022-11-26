package weblogic.servlet.jsp;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspEngineInfo;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;
import weblogic.servlet.internal.WebAppServletContext;

public final class JspFactoryImpl extends JspFactory {
   private static boolean inited = false;

   private JspFactoryImpl() {
   }

   public static synchronized void init() {
      if (!inited) {
         setDefaultFactory(new JspFactoryImpl());
         inited = true;
      }
   }

   public PageContext getPageContext(Servlet s, ServletRequest rq, ServletResponse rp, String errorPageUrl, boolean needsSession, int bufsize, boolean autoflush) {
      try {
         return new PageContextImpl(this, s, rq, rp, errorPageUrl, needsSession, bufsize, autoflush);
      } catch (IOException var9) {
         return null;
      }
   }

   public void releasePageContext(PageContext pc) {
   }

   public JspEngineInfo getEngineInfo() {
      return new JspEngineInfo() {
         public String getSpecificationVersion() {
            return "2.3";
         }
      };
   }

   public JspApplicationContext getJspApplicationContext(ServletContext context) {
      return ((WebAppServletContext)context).getJspApplicationContext();
   }
}
