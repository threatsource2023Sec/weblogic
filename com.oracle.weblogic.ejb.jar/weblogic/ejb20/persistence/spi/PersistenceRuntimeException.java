package weblogic.ejb20.persistence.spi;

import java.io.PrintStream;
import weblogic.ejb.container.InternalException;
import weblogic.utils.AssertionError;

public final class PersistenceRuntimeException extends RuntimeException {
   private static final long serialVersionUID = 8026063946735631325L;
   private Throwable causeException;

   public Throwable getCausedByException() {
      return this.causeException;
   }

   public void printStackTrace(PrintStream printstream) {
      this.causeException.printStackTrace(printstream);
   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }

   public String toString() {
      return this.causeException.toString();
   }

   public PersistenceRuntimeException(Throwable e) {
      if (e == null) {
         throw new AssertionError("Attempted to create a PersistenceRuntimeException with a null causeException.");
      } else {
         if (e instanceof InternalException) {
            InternalException ie = (InternalException)e;
            if (ie.detail != null) {
               e = ie.detail;
            }
         }

         this.causeException = e;
      }
   }

   public PersistenceRuntimeException(String s, Throwable e) {
      super(s);
      if (e == null) {
         throw new AssertionError("Attempted to create a PersistenceRuntimeException with a null nested Exception.");
      } else {
         if (e instanceof InternalException) {
            InternalException ie = (InternalException)e;
            if (ie.detail != null) {
               e = ie.detail;
            }
         }

         this.causeException = e;
      }
   }
}
