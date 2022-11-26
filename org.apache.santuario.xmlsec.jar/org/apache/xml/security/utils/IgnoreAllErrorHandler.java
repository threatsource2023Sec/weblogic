package org.apache.xml.security.utils;

import java.security.AccessController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class IgnoreAllErrorHandler implements ErrorHandler {
   private static final Logger LOG = LoggerFactory.getLogger(IgnoreAllErrorHandler.class);
   private static final boolean warnOnExceptions = getProperty("org.apache.xml.security.test.warn.on.exceptions");
   private static final boolean throwExceptions = getProperty("org.apache.xml.security.test.throw.exceptions");

   private static boolean getProperty(String name) {
      return (Boolean)AccessController.doPrivileged(() -> {
         return Boolean.getBoolean(name);
      });
   }

   public void warning(SAXParseException ex) throws SAXException {
      if (warnOnExceptions) {
         LOG.warn("", ex);
      }

      if (throwExceptions) {
         throw ex;
      }
   }

   public void error(SAXParseException ex) throws SAXException {
      if (warnOnExceptions) {
         LOG.error("", ex);
      }

      if (throwExceptions) {
         throw ex;
      }
   }

   public void fatalError(SAXParseException ex) throws SAXException {
      if (warnOnExceptions) {
         LOG.warn("", ex);
      }

      if (throwExceptions) {
         throw ex;
      }
   }
}
