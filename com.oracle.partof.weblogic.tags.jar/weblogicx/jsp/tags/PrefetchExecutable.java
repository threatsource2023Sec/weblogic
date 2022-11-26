package weblogicx.jsp.tags;

import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.jsp.PageContext;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.work.WorkAdapter;

class PrefetchExecutable extends WorkAdapter {
   private ServletResponse rs;
   private ServletRequest rq;
   private RequestDispatcher rd;

   public PrefetchExecutable(String uri, PageContext pageContext) {
      ServletRequestImpl request = (ServletRequestImpl)pageContext.getRequest();
      ServletResponseImpl response = (ServletResponseImpl)pageContext.getResponse();
      final NullOutputStream nos = new NullOutputStream();
      this.rd = pageContext.getServletContext().getRequestDispatcher(uri);
      this.rq = new HttpServletRequestWrapper(request);
      this.rs = new HttpServletResponseWrapper(response) {
         public ServletOutputStream getOutputStream() {
            return nos;
         }

         public PrintWriter getWriter() {
            return new PrintWriter(nos);
         }
      };
   }

   public void run() {
      try {
         this.rd.include(this.rq, this.rs);
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   public static class NullOutputStream extends ServletOutputStream {
      public void write(int i) {
      }

      public void write(byte[] b) {
      }

      public void write(byte[] b, int i, int j) {
      }

      public void flush() {
      }

      public void close() {
      }

      public boolean isReady() {
         return false;
      }

      public void setWriteListener(WriteListener writeListener) {
         throw new IllegalStateException("Not Supported");
      }
   }
}
