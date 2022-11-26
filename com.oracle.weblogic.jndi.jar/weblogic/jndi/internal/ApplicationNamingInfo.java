package weblogic.jndi.internal;

public class ApplicationNamingInfo {
   private boolean forceCallByReference = false;

   public void setForceCallByReference(boolean b) {
      this.forceCallByReference = b;
   }

   public boolean isForceCallByReferenceEnabled() {
      return this.forceCallByReference;
   }
}
