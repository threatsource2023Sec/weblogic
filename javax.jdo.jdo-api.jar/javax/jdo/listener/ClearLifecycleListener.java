package javax.jdo.listener;

public interface ClearLifecycleListener extends InstanceLifecycleListener {
   void preClear(InstanceLifecycleEvent var1);

   void postClear(InstanceLifecycleEvent var1);
}
