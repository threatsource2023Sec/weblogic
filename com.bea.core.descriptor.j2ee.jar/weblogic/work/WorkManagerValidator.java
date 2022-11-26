package weblogic.work;

import weblogic.j2ee.descriptor.wl.WebLogicJavaEEDescriptorValidatorLogger;

public class WorkManagerValidator {
   public static void validateMinThreadsConstraintCount(int value) {
      validateLegalMin("MinThreadsConstraints.Count", value);
   }

   public static void validateMaxThreadsConstraintCount(int value) {
      validateLegalMin("MaxThreadsConstraints.Count", value);
   }

   public static void validateResponseTimeRequestClassGoalMs(int value) {
      validateLegalMin("ResponseTimeRequestClass.GoalMs", value);
   }

   private static void validateLegalMin(String paramName, int value) {
      if (value < -1) {
         WebLogicJavaEEDescriptorValidatorLogger.logIllegalNegativeValue(paramName, value);
      }

   }
}
