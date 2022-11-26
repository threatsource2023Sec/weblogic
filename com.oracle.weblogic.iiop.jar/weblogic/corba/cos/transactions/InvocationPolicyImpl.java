package weblogic.corba.cos.transactions;

import org.omg.CosTransactions.InvocationPolicy;
import weblogic.corba.policies.PolicyImpl;

public class InvocationPolicyImpl extends PolicyImpl implements InvocationPolicy {
   public InvocationPolicyImpl(int value) {
      super(55, value);
   }

   public short ipv() {
      return (short)this.policy_value();
   }
}
