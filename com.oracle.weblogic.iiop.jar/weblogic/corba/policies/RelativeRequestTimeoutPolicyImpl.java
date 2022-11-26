package weblogic.corba.policies;

import org.omg.Messaging.RelativeRequestTimeoutPolicy;

public class RelativeRequestTimeoutPolicyImpl extends PolicyImpl implements RelativeRequestTimeoutPolicy {
   private long expiry;

   public RelativeRequestTimeoutPolicyImpl(long value) {
      super(31, 0);
      this.expiry = value;
   }

   public long relative_expiry() {
      return this.expiry;
   }

   public long relativeExpiryMillis() {
      return this.expiry / 10000L;
   }
}
