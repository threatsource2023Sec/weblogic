package weblogic.xml.babel.helpers;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SAXErrorHandlerImpl implements ErrorHandler {
   public void warning(SAXParseException exception) throws SAXException {
      throw exception;
   }

   public void error(SAXParseException exception) throws SAXException {
      throw exception;
   }

   public void fatalError(SAXParseException exception) throws SAXParseException {
      throw exception;
   }
}
