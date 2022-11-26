package weblogic.servlet.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import weblogic.servlet.internal.RemoveWrapperOnForward;

public class NestedBodyResponse extends HttpServletResponseWrapper implements RemoveWrapperOnForward {
   private PrintWriter pw;
   private BodyOutputStream bos;
   private BodyContentImpl bodyContent;

   public NestedBodyResponse(HttpServletResponse response, BodyContentImpl bci) {
      super(response);
      this.bodyContent = bci;
   }

   public ServletOutputStream getOutputStream() throws IOException {
      if (this.bos == null) {
         this.bos = new BodyOutputStream(this.bodyContent.getChunkOutputWrapper());
      }

      return this.bos;
   }

   public PrintWriter getWriter() throws IOException {
      if (this.pw == null) {
         this.pw = new PrintWriter(this.bodyContent);
      }

      return this.pw;
   }

   public BodyContentImpl getBodyContentImpl() {
      return this.bodyContent;
   }
}
