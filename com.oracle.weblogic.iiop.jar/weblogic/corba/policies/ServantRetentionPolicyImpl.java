package weblogic.corba.policies;

import org.omg.PortableServer.ServantRetentionPolicy;
import org.omg.PortableServer.ServantRetentionPolicyValue;

public class ServantRetentionPolicyImpl extends PolicyImpl implements ServantRetentionPolicy {
   public ServantRetentionPolicyImpl(int value) {
      super(21, value);
   }

   public ServantRetentionPolicyValue value() {
      return ServantRetentionPolicyValue.from_int(this.value);
   }
}
