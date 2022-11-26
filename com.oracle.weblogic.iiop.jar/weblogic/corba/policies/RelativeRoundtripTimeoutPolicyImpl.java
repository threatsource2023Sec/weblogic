package weblogic.corba.policies;

import org.omg.Messaging.RelativeRoundtripTimeoutPolicy;

public class RelativeRoundtripTimeoutPolicyImpl extends PolicyImpl implements RelativeRoundtripTimeoutPolicy {
   private long expiry;

   public RelativeRoundtripTimeoutPolicyImpl(long value) {
      super(32, 0);
      this.expiry = value;
   }

   public long relative_expiry() {
      return this.expiry;
   }

   public long relativeExpiryMillis() {
      return this.expiry / 10000L;
   }
}
