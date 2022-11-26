package weblogic.corba.policies;

import org.omg.Messaging.RequestEndTimePolicy;
import org.omg.TimeBase.UtcT;
import org.omg.TimeBase.UtcTHelper;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public class RequestEndTimePolicyImpl extends PolicyImpl implements RequestEndTimePolicy {
   private UtcT endTime;
   public static final long UNITS_PER_MILLI = 10000L;
   private static final long UTCT_DELTA_MILLIS = 12219292800000L;

   public static final long java2Utc(long time) {
      return (time + 12219292800000L) * 10000L;
   }

   public static final long utc2Java(long utct) {
      return utct / 10000L - 12219292800000L;
   }

   public RequestEndTimePolicyImpl(long time) {
      super(28, 0);
      this.endTime = new UtcT(java2Utc(time), 0, (short)0, (short)0);
   }

   public RequestEndTimePolicyImpl(UtcT time) {
      super(28, 0);
      this.endTime = new UtcT(time.time, time.inacclo, time.inacchi, time.tdf);
   }

   public RequestEndTimePolicyImpl(CorbaInputStream in) {
      super(28, 0);
      this.read(in);
   }

   public UtcT end_time() {
      return this.endTime;
   }

   public long endTime() {
      return utc2Java(this.endTime.time);
   }

   public long relativeTimeoutMillis() {
      long rt = utc2Java(this.endTime.time) - System.currentTimeMillis();
      return rt < 0L ? 0L : rt;
   }

   protected void readEncapsulatedPolicy(CorbaInputStream in) {
      this.endTime = UtcTHelper.read(in);
   }

   protected void writeEncapsulatedPolicy(CorbaOutputStream out) {
      UtcTHelper.write(out, this.endTime);
   }
}
