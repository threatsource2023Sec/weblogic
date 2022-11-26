package com.bea.security.xacml;

public class InvalidRoleAssignmentPolicyException extends EvaluationPlanException {
   public InvalidRoleAssignmentPolicyException(Throwable cause) {
      super(cause);
   }

   public InvalidRoleAssignmentPolicyException(String msg) {
      super(msg);
   }
}
