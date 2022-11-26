package weblogic.corba.cos.transactions;

import org.omg.CosTransactions.OTSPolicy;
import weblogic.corba.policies.PolicyImpl;

public class OTSPolicyImpl extends PolicyImpl implements OTSPolicy {
   public OTSPolicyImpl(int value) {
      super(56, value);
   }

   public short tpv() {
      return (short)this.policy_value();
   }
}
