package net.shibboleth.utilities.java.support.xml;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.slf4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public final class LoggingErrorHandler implements ErrorHandler {
   @Nonnull
   private Logger log;
   private boolean logException;

   public LoggingErrorHandler(@Nonnull Logger logger) {
      this.log = (Logger)Constraint.isNotNull(logger, "Logger cannot be null");
      this.logException = false;
   }

   public void setLogException(boolean flag) {
      this.logException = flag;
   }

   public void error(SAXParseException exception) throws SAXException {
      if (this.logException) {
         this.log.error("XML Parsing Error", exception);
      } else {
         this.log.error("XML Parsing Error");
      }

      throw exception;
   }

   public void fatalError(SAXParseException exception) throws SAXException {
      if (this.logException) {
         this.log.error("XML Parsing Error", exception);
      } else {
         this.log.error("XML Parsing Error");
      }

      throw exception;
   }

   public void warning(SAXParseException exception) throws SAXException {
      if (this.logException) {
         this.log.warn("XML Parsing Error", exception);
      } else {
         this.log.warn("XML Parsing Error");
      }

      throw exception;
   }
}
