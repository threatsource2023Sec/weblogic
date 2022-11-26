package com.oracle.wls.shaded.org.apache.xml.utils;

import com.oracle.wls.shaded.org.apache.xml.res.XMLMessages;
import java.io.PrintStream;
import java.io.PrintWriter;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DefaultErrorHandler implements ErrorHandler, ErrorListener {
   PrintWriter m_pw;
   boolean m_throwExceptionOnError;

   public DefaultErrorHandler(PrintWriter pw) {
      this.m_throwExceptionOnError = true;
      this.m_pw = pw;
   }

   public DefaultErrorHandler(PrintStream pw) {
      this.m_throwExceptionOnError = true;
      this.m_pw = new PrintWriter(pw, true);
   }

   public DefaultErrorHandler() {
      this(true);
   }

   public DefaultErrorHandler(boolean throwExceptionOnError) {
      this.m_throwExceptionOnError = true;
      this.m_throwExceptionOnError = throwExceptionOnError;
   }

   public PrintWriter getErrorWriter() {
      if (this.m_pw == null) {
         this.m_pw = new PrintWriter(System.err, true);
      }

      return this.m_pw;
   }

   public void warning(SAXParseException exception) throws SAXException {
      PrintWriter pw = this.getErrorWriter();
      printLocation((PrintWriter)pw, (Throwable)exception);
      pw.println("Parser warning: " + exception.getMessage());
   }

   public void error(SAXParseException exception) throws SAXException {
      throw exception;
   }

   public void fatalError(SAXParseException exception) throws SAXException {
      throw exception;
   }

   public void warning(TransformerException exception) throws TransformerException {
      PrintWriter pw = this.getErrorWriter();
      printLocation((PrintWriter)pw, (Throwable)exception);
      pw.println(exception.getMessage());
   }

   public void error(TransformerException exception) throws TransformerException {
      if (this.m_throwExceptionOnError) {
         throw exception;
      } else {
         PrintWriter pw = this.getErrorWriter();
         printLocation((PrintWriter)pw, (Throwable)exception);
         pw.println(exception.getMessage());
      }
   }

   public void fatalError(TransformerException exception) throws TransformerException {
      if (this.m_throwExceptionOnError) {
         throw exception;
      } else {
         PrintWriter pw = this.getErrorWriter();
         printLocation((PrintWriter)pw, (Throwable)exception);
         pw.println(exception.getMessage());
      }
   }

   public static void ensureLocationSet(TransformerException exception) {
      SourceLocator locator = null;
      Throwable cause = exception;

      do {
         if (cause instanceof SAXParseException) {
            locator = new SAXSourceLocator((SAXParseException)cause);
         } else if (cause instanceof TransformerException) {
            SourceLocator causeLocator = ((TransformerException)cause).getLocator();
            if (null != causeLocator) {
               locator = causeLocator;
            }
         }

         if (cause instanceof TransformerException) {
            cause = ((TransformerException)cause).getCause();
         } else if (cause instanceof SAXException) {
            cause = ((SAXException)cause).getException();
         } else {
            cause = null;
         }
      } while(null != cause);

      exception.setLocator((SourceLocator)locator);
   }

   public static void printLocation(PrintStream pw, TransformerException exception) {
      printLocation((PrintWriter)(new PrintWriter(pw)), (Throwable)exception);
   }

   public static void printLocation(PrintStream pw, SAXParseException exception) {
      printLocation((PrintWriter)(new PrintWriter(pw)), (Throwable)exception);
   }

   public static void printLocation(PrintWriter pw, Throwable exception) {
      SourceLocator locator = null;
      Throwable cause = exception;

      do {
         if (cause instanceof SAXParseException) {
            locator = new SAXSourceLocator((SAXParseException)cause);
         } else if (cause instanceof TransformerException) {
            SourceLocator causeLocator = ((TransformerException)cause).getLocator();
            if (null != causeLocator) {
               locator = causeLocator;
            }
         }

         if (cause instanceof TransformerException) {
            cause = ((TransformerException)cause).getCause();
         } else if (cause instanceof WrappedRuntimeException) {
            cause = ((WrappedRuntimeException)cause).getException();
         } else if (cause instanceof SAXException) {
            cause = ((SAXException)cause).getException();
         } else {
            cause = null;
         }
      } while(null != cause);

      if (null != locator) {
         String id = null != ((SourceLocator)locator).getPublicId() ? ((SourceLocator)locator).getPublicId() : (null != ((SourceLocator)locator).getSystemId() ? ((SourceLocator)locator).getSystemId() : XMLMessages.createXMLMessage("ER_SYSTEMID_UNKNOWN", (Object[])null));
         pw.print(id + "; " + XMLMessages.createXMLMessage("line", (Object[])null) + ((SourceLocator)locator).getLineNumber() + "; " + XMLMessages.createXMLMessage("column", (Object[])null) + ((SourceLocator)locator).getColumnNumber() + "; ");
      } else {
         pw.print("(" + XMLMessages.createXMLMessage("ER_LOCATION_UNKNOWN", (Object[])null) + ")");
      }

   }
}
