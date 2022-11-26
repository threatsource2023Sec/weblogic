package weblogic.jdbc.common.internal;

public class AffinityContextHelperFactory {
   public static AffinityContextHelper createXAAffinityContextHelper() {
      return new XAAffinityContextHelper();
   }

   public static AffinityContextHelper createSessionAffinityContextHelper() {
      return new SessionAffinityContextHelper();
   }
}
