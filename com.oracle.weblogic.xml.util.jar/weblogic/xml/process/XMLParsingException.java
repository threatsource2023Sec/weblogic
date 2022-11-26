package weblogic.xml.process;

import org.xml.sax.SAXParseException;
import weblogic.utils.NestedException;

public class XMLParsingException extends NestedException {
   private static final long serialVersionUID = -6330961353409169997L;
   protected String fileName;

   public XMLParsingException(String msg) {
      super(msg);
   }

   public XMLParsingException(Throwable th) {
      super(th);
   }

   public void setFileName(String name) {
      this.fileName = name;
   }

   public String getFileName() {
      return this.fileName;
   }

   public String getMessage() {
      return this.getMessagePrefix() + super.getMessage();
   }

   public String toString() {
      return this.getMessagePrefix() + super.toString();
   }

   private String getMessagePrefix() {
      StringBuffer sb = new StringBuffer();
      Throwable t = this.getNestedException();
      boolean isSaxParseException = t instanceof SAXParseException;
      if (this.fileName != null || isSaxParseException) {
         sb.append("Error parsing file ");
         if (this.fileName != null) {
            sb.append('\'');
            sb.append(this.fileName);
            sb.append('\'');
            if (isSaxParseException) {
               sb.append(" ");
            } else {
               sb.append(".  ");
            }
         }

         if (isSaxParseException) {
            SAXParseException s = (SAXParseException)t;
            sb.append("at line: ");
            sb.append(s.getLineNumber());
            sb.append(" column: ");
            sb.append(s.getColumnNumber());
            sb.append(".  ");
         }
      }

      return sb.toString();
   }
}
