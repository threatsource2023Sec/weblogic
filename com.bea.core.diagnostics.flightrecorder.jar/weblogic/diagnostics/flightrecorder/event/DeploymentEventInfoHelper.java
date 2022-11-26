package weblogic.diagnostics.flightrecorder.event;

public final class DeploymentEventInfoHelper {
   public static void populateExtensions(Object returnValue, Object[] args, DeploymentEventInfo target) {
      if (target != null && !target.isPopulated() && args != null && args.length != 0) {
         Object[] var3 = args;
         int var4 = args.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Object arg = var3[var5];
            if (arg != null && arg instanceof DeploymentEventInfo) {
               setFromValue((DeploymentEventInfo)arg, target);
               if (target.isPopulated()) {
                  return;
               }
            }
         }

      }
   }

   private static void setFromValue(DeploymentEventInfo input, DeploymentEventInfo target) {
      if (target.getTaskId() == null && input.getTaskId() != null) {
         target.setTaskId(input.getTaskId());
      }

      if (target.getAppName() == null && input.getAppName() != null) {
         target.setAppName(input.getAppName());
      }

      if (!target.hasRequestId() && input.hasRequestId()) {
         target.setRequestId(input.getRequestId());
      }

   }
}
