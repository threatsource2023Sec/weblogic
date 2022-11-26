package weblogic.j2ee.descriptor;

public class EnvEntryValidator {
   public static void validateEnvEntry(EnvEntryBean envEntryBean) {
      String envEntryType = envEntryBean.getEnvEntryType();
      if (envEntryType == null) {
         InjectionTargetBean[] injectionTargets = envEntryBean.getInjectionTargets();
         if (injectionTargets == null || injectionTargets.length <= 0) {
            throw new IllegalArgumentException("env-entry-type is required if there are no injection targets");
         }
      }

   }
}
