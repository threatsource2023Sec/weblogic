package monfox.jdom.input;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class BuilderErrorHandler implements ErrorHandler {
   public static boolean a;

   public void warning(SAXParseException var1) throws SAXException {
   }

   public void error(SAXParseException var1) throws SAXException {
      throw var1;
   }

   public void fatalError(SAXParseException var1) throws SAXException {
      throw var1;
   }
}
