package oracle.dms.context.internal.wls;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import weblogic.diagnostics.context.Correlation;
import weblogic.diagnostics.context.CorrelationDebugContributor;
import weblogic.diagnostics.context.CorrelationFactory;
import weblogic.diagnostics.context.WrapUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.flightrecorder.JFRDebug;

public class WLSContextFamily {
   private static boolean initialized = false;
   static CorrelationFactory.Factory correlationFactory = null;

   public static synchronized void initialize(CorrelationFactory.Factory correlationFactory) {
      if (!initialized) {
         WLSContextFamily.correlationFactory = correlationFactory;
         initialized = true;
      }
   }

   public static class SerializableImpl implements Serializable {
      private static final long serialVersionUID = 0L;
      private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDiagnosticContext");
      private Correlation correlation = null;
      boolean propagatedToHere = false;
      boolean propagationSuccess = false;

      public SerializableImpl() {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            JFRDebug.generateDebugEvent("Correlation", "SerializableImpl()", (Throwable)null, CorrelationDebugContributor.getInstance((Correlation)null));
         }

      }

      public SerializableImpl(Correlation correlation) {
         this.correlation = correlation;
         if (DEBUG_LOGGER.isDebugEnabled()) {
            JFRDebug.generateDebugEvent("Correlation", "SerializableImpl(Correlation)", (Throwable)null, CorrelationDebugContributor.getInstance(correlation));
         }

      }

      public Correlation getCorrelation() {
         return this.correlation;
      }

      public void setCorrelation(Correlation correlation) {
         this.correlation = correlation;
         if (DEBUG_LOGGER.isDebugEnabled()) {
            JFRDebug.generateDebugEvent("Correlation", "SerializableImpl.setCorrelation(Correlation)", (Throwable)null, CorrelationDebugContributor.getInstance(correlation));
         }

      }

      boolean isPropagationSuccess() {
         return this.propagationSuccess;
      }

      boolean isPropagatedToHere() {
         return this.propagatedToHere;
      }

      private void writeObject(ObjectOutputStream out) throws IOException {
         String wrapString = WrapUtils.wrap(this.correlation);
         out.writeUTF(wrapString);
         if (DEBUG_LOGGER.isDebugEnabled()) {
            JFRDebug.generateDebugEvent("CorrelationWrap", "SerializableImpl.writeObject(): " + wrapString, (Throwable)null, CorrelationDebugContributor.getInstance(this.correlation));
         }

      }

      private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
         String wrappedString = null;

         try {
            this.propagatedToHere = true;
            this.propagationSuccess = false;
            wrappedString = in.readUTF();
            if (wrappedString != null && !wrappedString.isEmpty()) {
               Correlation correlation = WrapUtils.unwrap(wrappedString);
               if (correlation != null) {
                  if (WLSContextFamily.correlationFactory != null) {
                     WLSContextFamily.correlationFactory.correlationPropagatedIn(correlation);
                  }

                  this.correlation = correlation;
                  this.propagatedToHere = true;
                  this.propagationSuccess = true;
                  if (DEBUG_LOGGER.isDebugEnabled()) {
                     JFRDebug.generateDebugEvent("CorrelationUnwrap", "readObject Correlation unwrapped", (Throwable)null, CorrelationDebugContributor.getInstance(correlation));
                  }
               } else {
                  if (DEBUG_LOGGER.isDebugEnabled()) {
                     JFRDebug.generateDebugEvent("CorrelationUnwrap", "readObject didn't understand wrap: " + wrappedString, (Throwable)null, CorrelationDebugContributor.getInstance(this.correlation));
                     DEBUG_LOGGER.debug("readObject didn't understand wrapped String: " + wrappedString);
                  }

                  this.propagatedToHere = false;
                  this.propagationSuccess = false;
               }
            } else {
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  JFRDebug.generateDebugEvent("CorrelationUnwrap", "readObject found null or empty wrap", (Throwable)null, CorrelationDebugContributor.getInstance(this.correlation));
               }

               this.propagatedToHere = false;
               this.propagationSuccess = false;
            }
         } catch (Throwable var4) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               JFRDebug.generateDebugEvent("CorrelationUnwrap", "readObject unexpected failure: " + wrappedString, var4, CorrelationDebugContributor.getInstance(this.correlation));
               DEBUG_LOGGER.debug("readObject unexpected failure: " + wrappedString, var4);
            }

            this.propagatedToHere = false;
            this.propagationSuccess = false;
         }

      }
   }
}
