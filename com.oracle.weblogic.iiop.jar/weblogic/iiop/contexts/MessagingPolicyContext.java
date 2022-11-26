package weblogic.iiop.contexts;

import weblogic.corba.policies.PolicyImpl;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public final class MessagingPolicyContext extends ServiceContext {
   private PolicyImpl[] policies;

   public MessagingPolicyContext(PolicyImpl[] policies) {
      super(7);
      this.policies = policies;
   }

   public MessagingPolicyContext(CorbaInputStream in) {
      super(7);
      this.readEncapsulatedContext(in);
   }

   protected final void readEncapsulation(CorbaInputStream in) {
      int len = in.read_ulong();
      this.policies = new PolicyImpl[len];

      for(int i = 0; i < len; ++i) {
         this.policies[i] = PolicyImpl.readPolicy(in);
      }

   }

   public final void write(CorbaOutputStream out) {
      for(int i = 0; i < this.policies.length; ++i) {
         this.policies[i].write(out);
      }

   }

   public String toString() {
      return "MessagingPolicy";
   }
}
