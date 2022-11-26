package weblogic.corba.policies;

import org.omg.PortableServer.RequestProcessingPolicy;
import org.omg.PortableServer.RequestProcessingPolicyValue;

public class RequestProcessingPolicyImpl extends PolicyImpl implements RequestProcessingPolicy {
   public RequestProcessingPolicyImpl(int value) {
      super(22, value);
   }

   public RequestProcessingPolicyValue value() {
      return RequestProcessingPolicyValue.from_int(this.value);
   }
}
