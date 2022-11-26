package weblogic.corba.idl.poa;

import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.PortableServer.ThreadPolicy;
import org.omg.PortableServer.ThreadPolicyValue;
import weblogic.corba.policies.PolicyImpl;

public class ThreadPolicyImpl extends PolicyImpl implements ThreadPolicy {
   public ThreadPolicyImpl(int value) {
      super(16, value);
      if (value != 0) {
         throw new NO_IMPLEMENT();
      }
   }

   public ThreadPolicyValue value() {
      return ThreadPolicyValue.from_int(this.value);
   }
}
