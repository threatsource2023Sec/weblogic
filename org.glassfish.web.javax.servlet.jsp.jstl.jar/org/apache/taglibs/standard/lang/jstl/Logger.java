package org.apache.taglibs.standard.lang.jstl;

import java.io.PrintStream;
import java.text.MessageFormat;

public class Logger {
   PrintStream mOut;

   public Logger(PrintStream pOut) {
      this.mOut = pOut;
   }

   public boolean isLoggingWarning() {
      return false;
   }

   public void logWarning(String pMessage, Throwable pRootCause) throws ELException {
      if (this.isLoggingWarning()) {
         if (pMessage == null) {
            System.out.println(pRootCause);
         } else if (pRootCause == null) {
            System.out.println(pMessage);
         } else {
            System.out.println(pMessage + ": " + pRootCause);
         }
      }

   }

   public void logWarning(String pTemplate) throws ELException {
      if (this.isLoggingWarning()) {
         this.logWarning(pTemplate, (Throwable)null);
      }

   }

   public void logWarning(Throwable pRootCause) throws ELException {
      if (this.isLoggingWarning()) {
         this.logWarning((String)null, (Throwable)pRootCause);
      }

   }

   public void logWarning(String pTemplate, Object pArg0) throws ELException {
      if (this.isLoggingWarning()) {
         this.logWarning(MessageFormat.format(pTemplate, "" + pArg0));
      }

   }

   public void logWarning(String pTemplate, Throwable pRootCause, Object pArg0) throws ELException {
      if (this.isLoggingWarning()) {
         this.logWarning(MessageFormat.format(pTemplate, "" + pArg0), pRootCause);
      }

   }

   public void logWarning(String pTemplate, Object pArg0, Object pArg1) throws ELException {
      if (this.isLoggingWarning()) {
         this.logWarning(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1));
      }

   }

   public void logWarning(String pTemplate, Throwable pRootCause, Object pArg0, Object pArg1) throws ELException {
      if (this.isLoggingWarning()) {
         this.logWarning(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1), pRootCause);
      }

   }

   public void logWarning(String pTemplate, Object pArg0, Object pArg1, Object pArg2) throws ELException {
      if (this.isLoggingWarning()) {
         this.logWarning(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1, "" + pArg2));
      }

   }

   public void logWarning(String pTemplate, Throwable pRootCause, Object pArg0, Object pArg1, Object pArg2) throws ELException {
      if (this.isLoggingWarning()) {
         this.logWarning(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1, "" + pArg2), pRootCause);
      }

   }

   public void logWarning(String pTemplate, Object pArg0, Object pArg1, Object pArg2, Object pArg3) throws ELException {
      if (this.isLoggingWarning()) {
         this.logWarning(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1, "" + pArg2, "" + pArg3));
      }

   }

   public void logWarning(String pTemplate, Throwable pRootCause, Object pArg0, Object pArg1, Object pArg2, Object pArg3) throws ELException {
      if (this.isLoggingWarning()) {
         this.logWarning(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1, "" + pArg2, "" + pArg3), pRootCause);
      }

   }

   public void logWarning(String pTemplate, Object pArg0, Object pArg1, Object pArg2, Object pArg3, Object pArg4) throws ELException {
      if (this.isLoggingWarning()) {
         this.logWarning(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1, "" + pArg2, "" + pArg3, "" + pArg4));
      }

   }

   public void logWarning(String pTemplate, Throwable pRootCause, Object pArg0, Object pArg1, Object pArg2, Object pArg3, Object pArg4) throws ELException {
      if (this.isLoggingWarning()) {
         this.logWarning(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1, "" + pArg2, "" + pArg3, "" + pArg4), pRootCause);
      }

   }

   public void logWarning(String pTemplate, Object pArg0, Object pArg1, Object pArg2, Object pArg3, Object pArg4, Object pArg5) throws ELException {
      if (this.isLoggingWarning()) {
         this.logWarning(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1, "" + pArg2, "" + pArg3, "" + pArg4, "" + pArg5));
      }

   }

   public void logWarning(String pTemplate, Throwable pRootCause, Object pArg0, Object pArg1, Object pArg2, Object pArg3, Object pArg4, Object pArg5) throws ELException {
      if (this.isLoggingWarning()) {
         this.logWarning(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1, "" + pArg2, "" + pArg3, "" + pArg4, "" + pArg5), pRootCause);
      }

   }

   public boolean isLoggingError() {
      return true;
   }

   public void logError(String pMessage, Throwable pRootCause) throws ELException {
      if (this.isLoggingError()) {
         if (pMessage == null) {
            throw new ELException(pRootCause);
         } else if (pRootCause == null) {
            throw new ELException(pMessage);
         } else {
            throw new ELException(pMessage, pRootCause);
         }
      }
   }

   public void logError(String pTemplate) throws ELException {
      if (this.isLoggingError()) {
         this.logError(pTemplate, (Throwable)null);
      }

   }

   public void logError(Throwable pRootCause) throws ELException {
      if (this.isLoggingError()) {
         this.logError((String)null, (Throwable)pRootCause);
      }

   }

   public void logError(String pTemplate, Object pArg0) throws ELException {
      if (this.isLoggingError()) {
         this.logError(MessageFormat.format(pTemplate, "" + pArg0));
      }

   }

   public void logError(String pTemplate, Throwable pRootCause, Object pArg0) throws ELException {
      if (this.isLoggingError()) {
         this.logError(MessageFormat.format(pTemplate, "" + pArg0), pRootCause);
      }

   }

   public void logError(String pTemplate, Object pArg0, Object pArg1) throws ELException {
      if (this.isLoggingError()) {
         this.logError(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1));
      }

   }

   public void logError(String pTemplate, Throwable pRootCause, Object pArg0, Object pArg1) throws ELException {
      if (this.isLoggingError()) {
         this.logError(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1), pRootCause);
      }

   }

   public void logError(String pTemplate, Object pArg0, Object pArg1, Object pArg2) throws ELException {
      if (this.isLoggingError()) {
         this.logError(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1, "" + pArg2));
      }

   }

   public void logError(String pTemplate, Throwable pRootCause, Object pArg0, Object pArg1, Object pArg2) throws ELException {
      if (this.isLoggingError()) {
         this.logError(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1, "" + pArg2), pRootCause);
      }

   }

   public void logError(String pTemplate, Object pArg0, Object pArg1, Object pArg2, Object pArg3) throws ELException {
      if (this.isLoggingError()) {
         this.logError(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1, "" + pArg2, "" + pArg3));
      }

   }

   public void logError(String pTemplate, Throwable pRootCause, Object pArg0, Object pArg1, Object pArg2, Object pArg3) throws ELException {
      if (this.isLoggingError()) {
         this.logError(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1, "" + pArg2, "" + pArg3), pRootCause);
      }

   }

   public void logError(String pTemplate, Object pArg0, Object pArg1, Object pArg2, Object pArg3, Object pArg4) throws ELException {
      if (this.isLoggingError()) {
         this.logError(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1, "" + pArg2, "" + pArg3, "" + pArg4));
      }

   }

   public void logError(String pTemplate, Throwable pRootCause, Object pArg0, Object pArg1, Object pArg2, Object pArg3, Object pArg4) throws ELException {
      if (this.isLoggingError()) {
         this.logError(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1, "" + pArg2, "" + pArg3, "" + pArg4), pRootCause);
      }

   }

   public void logError(String pTemplate, Object pArg0, Object pArg1, Object pArg2, Object pArg3, Object pArg4, Object pArg5) throws ELException {
      if (this.isLoggingError()) {
         this.logError(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1, "" + pArg2, "" + pArg3, "" + pArg4, "" + pArg5));
      }

   }

   public void logError(String pTemplate, Throwable pRootCause, Object pArg0, Object pArg1, Object pArg2, Object pArg3, Object pArg4, Object pArg5) throws ELException {
      if (this.isLoggingError()) {
         this.logError(MessageFormat.format(pTemplate, "" + pArg0, "" + pArg1, "" + pArg2, "" + pArg3, "" + pArg4, "" + pArg5), pRootCause);
      }

   }
}
