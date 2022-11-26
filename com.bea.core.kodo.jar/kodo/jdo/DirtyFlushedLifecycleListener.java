package kodo.jdo;

import javax.jdo.listener.InstanceLifecycleEvent;
import javax.jdo.listener.InstanceLifecycleListener;

public interface DirtyFlushedLifecycleListener extends InstanceLifecycleListener {
   void preDirtyFlushed(InstanceLifecycleEvent var1);

   void postDirtyFlushed(InstanceLifecycleEvent var1);
}
