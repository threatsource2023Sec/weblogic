package weblogic.cluster.singleton;

public interface LeaseObtainedListener {
   void onAcquire(String var1);

   void onException(Exception var1, String var2);
}
