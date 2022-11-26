package javax.jdo.listener;

public interface DeleteLifecycleListener extends InstanceLifecycleListener {
   void preDelete(InstanceLifecycleEvent var1);

   void postDelete(InstanceLifecycleEvent var1);
}
