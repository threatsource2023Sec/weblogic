package weblogic.servlet.jsp;

import java.io.IOException;
import weblogic.utils.PlatformConstants;

public class CompilationException extends IOException {
   static final long serialVersionUID = -1069773868125433531L;
   private String errors;
   private String html;
   private String jspURI;

   public CompilationException(String msg, String uri, String errors, String html, Throwable cause) {
      super(msg + PlatformConstants.EOL + errors);
      this.initCause(cause);
      this.errors = errors;
      this.html = html;
      this.jspURI = uri;
   }

   public String getErrorText() {
      return this.errors;
   }

   public String getJavaFileName() {
      return this.jspURI;
   }

   public String toHtml() {
      return this.html;
   }
}
