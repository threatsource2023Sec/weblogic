package javax.jdo.listener;

public interface StoreLifecycleListener extends InstanceLifecycleListener {
   void preStore(InstanceLifecycleEvent var1);

   void postStore(InstanceLifecycleEvent var1);
}
