package weblogic.corba.policies;

import org.omg.PortableServer.IdUniquenessPolicy;
import org.omg.PortableServer.IdUniquenessPolicyValue;

public class IdUniquenessPolicyImpl extends PolicyImpl implements IdUniquenessPolicy {
   public IdUniquenessPolicyImpl(int value) {
      super(18, value);
   }

   public IdUniquenessPolicyValue value() {
      return IdUniquenessPolicyValue.from_int(this.value);
   }
}
