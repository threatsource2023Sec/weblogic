package kodo.jdo;

import javax.jdo.listener.AttachLifecycleListener;
import javax.jdo.listener.ClearLifecycleListener;
import javax.jdo.listener.CreateLifecycleListener;
import javax.jdo.listener.DeleteLifecycleListener;
import javax.jdo.listener.DetachLifecycleListener;
import javax.jdo.listener.DirtyLifecycleListener;
import javax.jdo.listener.InstanceLifecycleEvent;
import javax.jdo.listener.InstanceLifecycleListener;
import javax.jdo.listener.LoadLifecycleListener;
import javax.jdo.listener.StoreLifecycleListener;
import org.apache.openjpa.event.LifecycleEvent;
import org.apache.openjpa.event.LifecycleEventManager;
import org.apache.openjpa.event.LifecycleListener;

class LifecycleListenerAdapter implements LifecycleListener, LifecycleEventManager.ListenerAdapter {
   private final InstanceLifecycleListener _listener;
   private int _types;

   public LifecycleListenerAdapter(InstanceLifecycleListener listener) {
      this._listener = listener;
      if (listener instanceof CreateLifecycleListener) {
         this._types |= 4;
      }

      if (listener instanceof ClearLifecycleListener) {
         this._types |= 64;
         this._types |= 128;
      }

      if (listener instanceof DeleteLifecycleListener) {
         this._types |= 256;
         this._types |= 512;
      }

      if (listener instanceof DirtyLifecycleListener) {
         this._types |= 1024;
         this._types |= 2048;
      }

      if (listener instanceof DirtyFlushedLifecycleListener) {
         this._types |= 4096;
         this._types |= 8192;
      }

      if (listener instanceof LoadLifecycleListener) {
         this._types |= 8;
      }

      if (listener instanceof StoreLifecycleListener) {
         this._types |= 16;
         this._types |= 32;
      }

      if (listener instanceof AttachLifecycleListener) {
         this._types |= 65536;
         this._types |= 131072;
      }

      if (listener instanceof DetachLifecycleListener) {
         this._types |= 16384;
         this._types |= 32768;
      }

   }

   InstanceLifecycleListener getDelegate() {
      return this._listener;
   }

   public boolean respondsTo(int eventType) {
      return (this._types & 2 << eventType) != 0;
   }

   public void beforeClear(LifecycleEvent event) {
      ((ClearLifecycleListener)this._listener).preClear(toInstanceLifecycleEvent(event));
   }

   public void afterClear(LifecycleEvent event) {
      ((ClearLifecycleListener)this._listener).postClear(toInstanceLifecycleEvent(event));
   }

   public void beforeDelete(LifecycleEvent event) {
      ((DeleteLifecycleListener)this._listener).preDelete(toInstanceLifecycleEvent(event));
   }

   public void afterDelete(LifecycleEvent event) {
      ((DeleteLifecycleListener)this._listener).postDelete(toInstanceLifecycleEvent(event));
   }

   public void beforeDirty(LifecycleEvent event) {
      ((DirtyLifecycleListener)this._listener).preDirty(toInstanceLifecycleEvent(event));
   }

   public void afterDirty(LifecycleEvent event) {
      ((DirtyLifecycleListener)this._listener).postDirty(toInstanceLifecycleEvent(event));
   }

   public void beforeDirtyFlushed(LifecycleEvent event) {
      ((DirtyFlushedLifecycleListener)this._listener).preDirtyFlushed(toInstanceLifecycleEvent(event));
   }

   public void afterDirtyFlushed(LifecycleEvent event) {
      ((DirtyFlushedLifecycleListener)this._listener).postDirtyFlushed(toInstanceLifecycleEvent(event));
   }

   public void afterLoad(LifecycleEvent event) {
      ((LoadLifecycleListener)this._listener).postLoad(toInstanceLifecycleEvent(event));
   }

   public void afterRefresh(LifecycleEvent event) {
   }

   public void afterPersist(LifecycleEvent event) {
      ((CreateLifecycleListener)this._listener).postCreate(toInstanceLifecycleEvent(event));
   }

   public void beforePersist(LifecycleEvent event) {
   }

   public void beforeStore(LifecycleEvent event) {
      ((StoreLifecycleListener)this._listener).preStore(toInstanceLifecycleEvent(event));
   }

   public void afterStore(LifecycleEvent event) {
      ((StoreLifecycleListener)this._listener).postStore(toInstanceLifecycleEvent(event));
   }

   public void beforeDetach(LifecycleEvent event) {
      ((DetachLifecycleListener)this._listener).preDetach(toInstanceLifecycleEvent(event));
   }

   public void afterDetach(LifecycleEvent event) {
      ((DetachLifecycleListener)this._listener).postDetach(toInstanceLifecycleEvent(event));
   }

   public void beforeAttach(LifecycleEvent event) {
      ((AttachLifecycleListener)this._listener).preAttach(toInstanceLifecycleEvent(event));
   }

   public void afterAttach(LifecycleEvent event) {
      ((AttachLifecycleListener)this._listener).postAttach(toInstanceLifecycleEvent(event));
   }

   private static InstanceLifecycleEvent toInstanceLifecycleEvent(LifecycleEvent event) {
      int type = 0;
      switch (event.getType()) {
         case 1:
            type = 0;
            break;
         case 2:
            type = 1;
            break;
         case 3:
         case 4:
            type = 2;
            break;
         case 5:
         case 6:
            type = 3;
            break;
         case 7:
         case 8:
            type = 4;
            break;
         case 9:
         case 10:
         case 11:
         case 12:
            type = 5;
            break;
         case 13:
         case 14:
            type = 6;
            break;
         case 15:
         case 16:
            type = 7;
      }

      return new InstanceLifecycleEvent(event.getSource(), type, event.getRelated());
   }

   public int hashCode() {
      return this._listener.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof LifecycleListenerAdapter) ? false : this._listener.equals(((LifecycleListenerAdapter)other).getDelegate());
      }
   }
}
