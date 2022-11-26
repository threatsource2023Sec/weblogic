package weblogic.iiop.ior;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public final class TransactionPolicyComponent extends TaggedComponent {
   public static final int OTS_POLICY_RESERVED = 0;
   public static final int OTS_POLICY_REQUIRES = 1;
   public static final int OTS_POLICY_FORBIDS = 2;
   public static final int OTS_POLICY_ADAPTS = 3;
   public static final TransactionPolicyComponent EJB_OTS_POLICY = new TransactionPolicyComponent(31, 3);
   public static final TransactionPolicyComponent NON_TX_POLICY = new TransactionPolicyComponent(31, 2);
   private static final TransactionPolicyComponent[] OTS_POLICIES;
   public static final int INVOCATION_POLICY_EITHER = 0;
   public static final int INVOCATION_POLICY_SHARED = 1;
   public static final int INVOCATION_POLICY_UNSHARED = 2;
   public static final TransactionPolicyComponent EJB_INV_POLICY;
   private static final TransactionPolicyComponent[] INV_POLICIES;
   private int policy;

   public TransactionPolicyComponent(int tag, int policy) {
      super(tag);
      this.policy = policy;
   }

   public static TaggedComponent getInvocationPolicy(int kind) {
      assert kind >= 0 && kind <= 2;

      return INV_POLICIES[kind];
   }

   public static TaggedComponent getOTSPolicy(int kind) {
      assert kind > 0 && kind <= 3;

      return OTS_POLICIES[kind];
   }

   public final int getPolicy() {
      return this.policy;
   }

   public TransactionPolicyComponent(CorbaInputStream in, int tag) {
      super(tag);
      this.read(in);
   }

   public final void read(CorbaInputStream in) {
      long handle = in.startEncapsulation();
      this.policy = in.read_unsigned_short();
      in.endEncapsulation(handle);
   }

   public final void write(CorbaOutputStream out) {
      out.write_long(this.tag);
      long handle = out.startEncapsulation();
      out.write_unsigned_short(this.policy);
      out.endEncapsulation(handle);
   }

   static {
      OTS_POLICIES = new TransactionPolicyComponent[]{null, new TransactionPolicyComponent(31, 1), new TransactionPolicyComponent(31, 2), EJB_OTS_POLICY};
      EJB_INV_POLICY = new TransactionPolicyComponent(32, 1);
      INV_POLICIES = new TransactionPolicyComponent[]{new TransactionPolicyComponent(32, 0), EJB_INV_POLICY, new TransactionPolicyComponent(32, 2)};
   }
}
