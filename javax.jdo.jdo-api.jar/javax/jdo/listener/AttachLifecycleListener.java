package javax.jdo.listener;

public interface AttachLifecycleListener extends InstanceLifecycleListener {
   void preAttach(InstanceLifecycleEvent var1);

   void postAttach(InstanceLifecycleEvent var1);
}
