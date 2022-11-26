package weblogic.corba.policies;

import org.omg.Messaging.ReplyEndTimePolicy;
import org.omg.TimeBase.UtcT;
import org.omg.TimeBase.UtcTHelper;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public class ReplyEndTimePolicyImpl extends PolicyImpl implements ReplyEndTimePolicy {
   private UtcT endTime;

   public ReplyEndTimePolicyImpl(long time) {
      super(30, 0);
      this.endTime = new UtcT(RequestEndTimePolicyImpl.java2Utc(time), 0, (short)0, (short)0);
   }

   public ReplyEndTimePolicyImpl(UtcT time) {
      super(30, 0);
      this.endTime = new UtcT(time.time, time.inacclo, time.inacchi, time.tdf);
   }

   public ReplyEndTimePolicyImpl(CorbaInputStream in) {
      super(30, 0);
      this.read(in);
   }

   public UtcT end_time() {
      return this.endTime;
   }

   public long endTime() {
      return RequestEndTimePolicyImpl.utc2Java(this.endTime.time);
   }

   public long relativeTimeoutMillis() {
      long rt = RequestEndTimePolicyImpl.utc2Java(this.endTime.time) - System.currentTimeMillis();
      return rt < 0L ? 0L : rt;
   }

   protected void readEncapsulatedPolicy(CorbaInputStream in) {
      this.endTime = UtcTHelper.read(in);
   }

   protected void writeEncapsulatedPolicy(CorbaOutputStream out) {
      UtcTHelper.write(out, this.endTime);
   }
}
