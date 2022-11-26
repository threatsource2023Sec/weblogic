package weblogic.diagnostics.flightrecorder.event;

public final class JDBCEventInfoHelper {
   public static void populateExtensions(Object returnValue, Object[] args, JDBCEventInfo target) {
      if (target != null) {
         if (returnValue != null && returnValue instanceof JDBCEventInfo) {
            setFromValue((JDBCEventInfo)returnValue, target);
         }

         if (target.getSql() == null || target.getPool() == null || !target.getInfectedSet()) {
            if (args != null && args.length != 0) {
               Object[] var3 = args;
               int var4 = args.length;

               for(int var5 = 0; var5 < var4; ++var5) {
                  Object arg = var3[var5];
                  if (arg != null && arg instanceof JDBCEventInfo) {
                     setFromValue((JDBCEventInfo)arg, target);
                     if (target.getSql() != null && target.getPool() != null && target.getInfectedSet()) {
                        return;
                     }
                  }
               }

            }
         }
      }
   }

   private static void setFromValue(JDBCEventInfo input, JDBCEventInfo target) {
      if (target.getSql() == null) {
         target.setSql(input.getSql());
      }

      if (target.getPool() == null) {
         target.setPool(input.getPool());
      }

      if (!target.getInfectedSet() && input.getInfectedSet()) {
         target.setInfected(input.getInfected());
      }

   }
}
