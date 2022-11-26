package weblogic.management.runtime;

/** @deprecated */
@Deprecated
public final class ConnectionLeakProfile {
   private final String stackTrace;
   private final String poolName;

   public ConnectionLeakProfile(String aPoolName, String aStackTrace) {
      this.poolName = aPoolName;
      this.stackTrace = aStackTrace;
   }

   public String getPoolName() {
      return this.poolName;
   }

   public String getStackTrace() {
      return this.stackTrace;
   }
}
