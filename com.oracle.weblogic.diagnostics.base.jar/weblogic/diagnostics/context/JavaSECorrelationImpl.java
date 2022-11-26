package weblogic.diagnostics.context;

import java.util.Map;
import java.util.logging.Level;

public final class JavaSECorrelationImpl implements Correlation {
   private static final long serialVersionUID = 1L;
   private DiagnosticContextImpl dcImpl = null;

   JavaSECorrelationImpl() {
   }

   JavaSECorrelationImpl(DiagnosticContextImpl dcImpl) {
      this.dcImpl = dcImpl;
   }

   JavaSECorrelationImpl(String dcid, String rid) {
      this.dcImpl = new DiagnosticContextImpl(true, dcid);
      this.dcImpl.setRID(rid);
   }

   public String getECID() {
      return this.dcImpl == null ? null : this.dcImpl.getContextId();
   }

   public String getRID() {
      return this.dcImpl == null ? null : this.dcImpl.getRID();
   }

   public long getDyeVector() {
      return this.dcImpl == null ? 0L : this.dcImpl.getDyeVector();
   }

   public Level getLogLevel() {
      if (this.dcImpl == null) {
         return null;
      } else {
         try {
            return Level.parse(Integer.toString(this.dcImpl.getLogLevel()));
         } catch (IllegalArgumentException var2) {
            return null;
         }
      }
   }

   public String getPayload() {
      return this.dcImpl == null ? null : this.dcImpl.getPayload();
   }

   public void setPayload(String payload) {
      if (this.dcImpl != null) {
         this.dcImpl.setPayload(payload);
      }

   }

   public void setDyeVector(long dye) {
      if (this.dcImpl != null) {
         this.dcImpl.setDyeVector(dye);
      }

   }

   public void setLogLevel(Level logLevel) {
      if (this.dcImpl != null) {
         if (logLevel == null) {
            this.dcImpl.setLogLevel(-1);
         } else {
            this.dcImpl.setLogLevel(logLevel.intValue());
         }
      }

   }

   public String getLegacyDCID() {
      return this.dcImpl == null ? null : this.dcImpl.getLegacyDCID();
   }

   public void setDye(int dye, boolean enable) throws InvalidDyeException {
      if (this.dcImpl != null) {
         this.dcImpl.setDye((byte)dye, enable);
      }

   }

   public boolean isDyedWith(int dye) throws InvalidDyeException {
      return this.dcImpl == null ? false : this.dcImpl.isDyedWith((byte)dye);
   }

   public Map values() {
      throw new UnsupportedOperationException("Operation not supported on client");
   }

   public int[] getRIDComponents() {
      throw new UnsupportedOperationException("Operation not supported on client");
   }

   public int[] newChildRIDComponents() {
      throw new UnsupportedOperationException("Operation not supported on client");
   }

   public int getRIDChildCount() {
      throw new UnsupportedOperationException("TOperation not supported on client");
   }

   public int incAndGetChildRIDCount() {
      throw new UnsupportedOperationException("Operation not supported on client");
   }

   public final Object setDMSObject(Object p) {
      throw new UnsupportedOperationException("Operation not supported on client");
   }

   public final Object getDMSObject() {
      throw new UnsupportedOperationException("Operation not supported on client");
   }

   public boolean getInheritable() {
      throw new UnsupportedOperationException("Operation not supported on client");
   }

   public void setInheritable(boolean inheritable) {
      throw new UnsupportedOperationException("Operation not supported on client");
   }

   DiagnosticContextImpl getDiagnosticContextImpl() {
      return this.dcImpl;
   }

   void setDiagnosticContextImpl(DiagnosticContextImpl dcImpl) {
      this.dcImpl = dcImpl;
   }
}
