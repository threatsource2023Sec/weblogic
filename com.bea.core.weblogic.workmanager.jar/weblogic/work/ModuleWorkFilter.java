package weblogic.work;

import weblogic.invocation.ComponentInvocationContext;

public class ModuleWorkFilter implements WorkFilter {
   private final String applicationId;
   private final String moduleName;

   public ModuleWorkFilter(String applicationId, String moduleName) {
      this.applicationId = applicationId;
      this.moduleName = moduleName;
   }

   public boolean matches(WorkAdapter work) {
      if (!(work instanceof CICCapturingWork)) {
         return false;
      } else {
         ComponentInvocationContext submittingCIC = ((CICCapturingWork)work).getSubmittingThreadCIC();
         if (submittingCIC == null) {
            return false;
         } else {
            return this.compare(this.applicationId, submittingCIC.getApplicationId()) && this.compare(this.moduleName, submittingCIC.getModuleName());
         }
      }
   }

   private boolean compare(String string1, String string2) {
      return string1 == null ? string2 == null : string1.equals(string2);
   }
}
