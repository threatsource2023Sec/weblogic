package weblogic.servlet.jsp.dd;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import weblogic.logging.Loggable;
import weblogic.servlet.HTTPLogger;

class JspcLogErrHandler implements ErrorHandler {
   private String location;
   private String ctx;

   public JspcLogErrHandler(String ctx, String location) {
      this.ctx = ctx;
      this.location = location;
   }

   public void warning(SAXParseException e) throws SAXParseException {
   }

   public void error(SAXParseException e) throws SAXParseException {
      Loggable l = HTTPLogger.logMalformedDescriptorCtxLoggable(this.ctx, this.location, "" + e.getLineNumber(), "" + e.getColumnNumber(), e.getMessage());
      l.log();
   }

   public void fatalError(SAXParseException e) throws SAXException {
      Loggable l = HTTPLogger.logMalformedDescriptorCtxLoggable(this.ctx, this.location, "" + e.getLineNumber(), "" + e.getColumnNumber(), e.getMessage());
      l.log();
      throw new SAXException(l.getMessage());
   }
}
