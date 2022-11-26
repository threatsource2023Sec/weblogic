package weblogic.servlet.jsp;

public final class JspException extends RuntimeException {
   private static final long serialVersionUID = 5685371183394534746L;
   public int lineNumber;
   public String uri;

   public JspException(String msg, int num, String uri) {
      this(msg, num);
      this.uri = uri;
   }

   public JspException(String msg, int num) {
      super(msg);
      this.lineNumber = num;
   }

   public JspException(String msg) {
      this(msg, -1);
   }

   public String getShortMessage() {
      return super.getMessage();
   }

   public String getMessage() {
      return "(line " + this.lineNumber + "): " + super.getMessage();
   }
}
