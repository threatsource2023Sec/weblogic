package weblogic.store.io.jdbc;

public abstract class RjvmInfo {
   private static RjvmInfo singleton;

   public static RjvmInfo getRjvmInfo() {
      if (singleton == null) {
         try {
            singleton = (RjvmInfo)Class.forName("weblogic.store.io.jdbc.WLSRjvmInfo").newInstance();
         } catch (Exception var1) {
            return null;
         }
      }

      return singleton;
   }

   public abstract String getServerName();

   public abstract String getAddress();

   public abstract String getDomainName();
}
