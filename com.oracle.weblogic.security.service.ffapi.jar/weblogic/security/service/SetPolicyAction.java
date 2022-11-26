package weblogic.security.service;

import java.security.Policy;
import java.security.PrivilegedAction;

class SetPolicyAction implements PrivilegedAction {
   private Policy newPolicy;

   public SetPolicyAction(Policy newPolicy) {
      this.newPolicy = newPolicy;
   }

   public Object run() {
      Policy oldPolicy = Policy.getPolicy();
      Policy.setPolicy(this.newPolicy);
      return oldPolicy;
   }
}
