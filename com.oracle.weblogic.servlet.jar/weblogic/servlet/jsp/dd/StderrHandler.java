package weblogic.servlet.jsp.dd;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

class StderrHandler implements ErrorHandler {
   public void warning(SAXParseException e) throws SAXParseException {
      System.err.println("[JSP]: warning " + e);
   }

   public void error(SAXParseException e) throws SAXParseException {
      System.err.println("[JSP]: got error " + e + " at line " + e.getLineNumber() + ", column " + e.getColumnNumber());
   }

   public void fatalError(SAXParseException e) throws SAXParseException {
      System.err.println("[JSP]: FATAL ERROR " + e);
      throw e;
   }
}
