package javax.jdo.listener;

public interface DirtyLifecycleListener extends InstanceLifecycleListener {
   void preDirty(InstanceLifecycleEvent var1);

   void postDirty(InstanceLifecycleEvent var1);
}
