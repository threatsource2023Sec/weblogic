package weblogic.wtc.gwt;

public final class WTCServiceStatus {
   public static final int UNKNOWN = 0;
   public static final int SUSPENDED = 1;
   public static final int UNAVAILABLE = 2;
   public static final int AVAILABLE = 3;
   public static final int IMPORT = 1;
   public static final int EXPORT = 2;

   public static String statusToString(int status) {
      switch (status) {
         case 1:
            return new String("SUSPENDED");
         case 2:
            return new String("UNAVAILABLE");
         case 3:
            return new String("AVAILABLE");
         default:
            return new String("UNKNOWN");
      }
   }

   public static String svcTypeToString(int svcType) {
      switch (svcType) {
         case 1:
            return new String("IMPORT");
         case 2:
            return new String("EXPORT");
         default:
            return new String("UNKNOWN");
      }
   }
}
