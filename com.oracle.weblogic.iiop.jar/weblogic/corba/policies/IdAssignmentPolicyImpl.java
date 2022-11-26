package weblogic.corba.policies;

import org.omg.PortableServer.IdAssignmentPolicy;
import org.omg.PortableServer.IdAssignmentPolicyValue;

public class IdAssignmentPolicyImpl extends PolicyImpl implements IdAssignmentPolicy {
   public IdAssignmentPolicyImpl(int value) {
      super(19, value);
   }

   public IdAssignmentPolicyValue value() {
      return IdAssignmentPolicyValue.from_int(this.value);
   }
}
