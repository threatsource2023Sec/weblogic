package org.apache.openjpa.lib.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ValidatingErrorHandler implements ErrorHandler {
   public void warning(SAXParseException e) throws SAXException {
      throw e;
   }

   public void error(SAXParseException e) throws SAXException {
      throw e;
   }

   public void fatalError(SAXParseException e) throws SAXException {
      throw e;
   }
}
