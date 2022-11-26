package javax.jdo.listener;

public interface DetachLifecycleListener extends InstanceLifecycleListener {
   void preDetach(InstanceLifecycleEvent var1);

   void postDetach(InstanceLifecycleEvent var1);
}
