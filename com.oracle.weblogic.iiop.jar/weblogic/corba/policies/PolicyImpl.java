package weblogic.corba.policies;

import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyHelper;
import org.omg.CORBA_2_3.portable.ObjectImpl;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public class PolicyImpl extends ObjectImpl implements Policy {
   protected int type;
   protected int value;
   private byte[] policy_data;

   public PolicyImpl(int type, int value) {
      this.type = type;
      this.value = value;
   }

   public PolicyImpl(PolicyImpl policy) {
      this.type = policy.type;
      this.value = policy.value;
   }

   public PolicyImpl(int type, CorbaInputStream in) {
      this.type = type;
      this.policy_data = in.read_octet_sequence();
   }

   public String[] _ids() {
      return new String[]{PolicyHelper.id()};
   }

   public int policy_type() {
      return this.type;
   }

   public int policy_value() {
      return this.value;
   }

   public void destroy() {
   }

   public Policy copy() {
      return new PolicyImpl(this);
   }

   public static PolicyImpl readPolicy(CorbaInputStream in) {
      int type = in.read_ulong();
      switch (type) {
         case 28:
            return new RequestEndTimePolicyImpl(in);
         case 30:
            return new ReplyEndTimePolicyImpl(in);
         default:
            return new PolicyImpl(type, in);
      }
   }

   protected void readEncapsulatedPolicy(CorbaInputStream in) {
      throw new AssertionError();
   }

   protected void writeEncapsulatedPolicy(CorbaOutputStream out) {
      throw new AssertionError();
   }

   protected final void read(CorbaInputStream in) {
      long handle = in.startEncapsulation();
      this.readEncapsulatedPolicy(in);
      in.endEncapsulation(handle);
   }

   public final void write(CorbaOutputStream out) {
      out.write_ulong(this.type);
      if (this.policy_data != null) {
         out.write_octet_sequence(this.policy_data);
      } else {
         long handle = out.startEncapsulation();
         this.writeEncapsulatedPolicy(out);
         out.endEncapsulation(handle);
      }

   }
}
