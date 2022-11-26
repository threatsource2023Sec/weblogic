package weblogic.xml.dom;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import weblogic.utils.NestedException;

public class DOMParserException extends NestedException {
   private SAXException saxException;
   private SAXParseException saxParseException;
   static final long serialVersionUID = 3947439951925250986L;

   public DOMParserException(SAXException saxe) {
      super(saxe);
      this.saxException = saxe;
      if (this.saxException instanceof SAXParseException) {
         this.saxParseException = (SAXParseException)saxe;
      }

   }

   public int getErrorLine() {
      return this.saxParseException != null ? this.saxParseException.getLineNumber() : -1;
   }

   public int getErrorColumn() {
      return this.saxParseException != null ? this.saxParseException.getColumnNumber() : -1;
   }

   public String toString() {
      String retVal = new String();
      if (this.saxParseException != null) {
         retVal = retVal + "Received SAXParseException from Sun Parser at line " + this.getErrorLine() + ", column " + this.getErrorColumn() + ": " + this.saxParseException;
      } else {
         retVal = retVal + "Received SAXException from Sun Parser: " + this.saxException;
      }

      return retVal;
   }
}
