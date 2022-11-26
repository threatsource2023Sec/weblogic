package weblogic.corba.policies;

import org.omg.PortableServer.LifespanPolicy;
import org.omg.PortableServer.LifespanPolicyValue;

public class LifespanPolicyImpl extends PolicyImpl implements LifespanPolicy {
   public LifespanPolicyImpl(int value) {
      super(17, value);
   }

   public LifespanPolicyValue value() {
      return LifespanPolicyValue.from_int(this.value);
   }
}
