package weblogic.corba.policies;

import org.omg.PortableServer.ImplicitActivationPolicy;
import org.omg.PortableServer.ImplicitActivationPolicyValue;

public class ImplicitActivationPolicyImpl extends PolicyImpl implements ImplicitActivationPolicy {
   public ImplicitActivationPolicyImpl(int value) {
      super(20, value);
   }

   public ImplicitActivationPolicyValue value() {
      return ImplicitActivationPolicyValue.from_int(this.value);
   }
}
