package weblogic.iiop.ior;

import weblogic.corba.policies.PolicyImpl;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public final class MessagingPolicyComponent extends TaggedComponent {
   private PolicyImpl[] policies;

   public MessagingPolicyComponent(PolicyImpl[] policies) {
      super(2);
      this.policies = policies;
   }

   MessagingPolicyComponent(CorbaInputStream in) {
      super(2);
      this.read(in);
   }

   public final void read(CorbaInputStream in) {
      long handle = in.startEncapsulation();
      int len = in.read_ulong();
      this.policies = new PolicyImpl[len];

      for(int i = 0; i < len; ++i) {
         this.policies[i] = PolicyImpl.readPolicy(in);
      }

      in.endEncapsulation(handle);
   }

   public final void write(CorbaOutputStream out) {
      out.write_long(this.tag);
      long handle = out.startEncapsulation();
      PolicyImpl[] var4 = this.policies;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         PolicyImpl policy = var4[var6];
         policy.write(out);
      }

      out.endEncapsulation(handle);
   }
}
