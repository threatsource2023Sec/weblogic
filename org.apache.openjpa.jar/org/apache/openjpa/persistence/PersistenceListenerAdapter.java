package org.apache.openjpa.persistence;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import org.apache.openjpa.event.DeleteListener;
import org.apache.openjpa.event.LifecycleCallbacks;
import org.apache.openjpa.event.LifecycleEvent;
import org.apache.openjpa.event.LifecycleEventManager;
import org.apache.openjpa.event.LoadListener;
import org.apache.openjpa.event.PersistListener;
import org.apache.openjpa.event.PostDeleteListener;
import org.apache.openjpa.event.PostPersistListener;
import org.apache.openjpa.event.UpdateListener;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.CallbackException;

class PersistenceListenerAdapter implements LifecycleEventManager.ListenerAdapter, PersistListener, PostPersistListener, LoadListener, UpdateListener, DeleteListener, PostDeleteListener {
   private static final Localizer _loc = Localizer.forPackage(PersistenceListenerAdapter.class);
   private final LifecycleCallbacks[][] _callbacks;

   public PersistenceListenerAdapter(LifecycleCallbacks[][] callbacks) {
      this._callbacks = callbacks;
   }

   public PersistenceListenerAdapter(Collection[] calls) {
      this._callbacks = new LifecycleCallbacks[LifecycleEvent.ALL_EVENTS.length][];

      for(int i = 0; i < LifecycleEvent.ALL_EVENTS.length; ++i) {
         if (calls[i] != null) {
            this._callbacks[i] = (LifecycleCallbacks[])calls[i].toArray(new LifecycleCallbacks[calls[i].size()]);
         }
      }

   }

   public boolean respondsTo(int eventType) {
      return this._callbacks[eventType] != null;
   }

   private void makeCallback(LifecycleEvent ev) {
      int eventType = ev.getType();
      if (this._callbacks[eventType] != null) {
         Object src = ev.getSource();
         LifecycleCallbacks[] arr$ = this._callbacks[eventType];
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            LifecycleCallbacks callback = arr$[i$];

            try {
               callback.makeCallback(src, ev.getRelated(), eventType);
            } catch (Throwable var9) {
               Throwable t = var9;
               if (var9 instanceof InvocationTargetException) {
                  t = var9.getCause();
               }

               if (t instanceof RuntimeException) {
                  throw (RuntimeException)t;
               }

               throw (new CallbackException(_loc.get("system-listener-err", src))).setCause(t).setFatal(true);
            }
         }

      }
   }

   public void beforePersist(LifecycleEvent event) {
      this.makeCallback(event);
   }

   public void afterPersist(LifecycleEvent event) {
      throw new UnsupportedOperationException();
   }

   public void afterPersistPerformed(LifecycleEvent event) {
      this.makeCallback(event);
   }

   public void afterLoad(LifecycleEvent event) {
      this.makeCallback(event);
   }

   public void afterRefresh(LifecycleEvent event) {
   }

   public void beforeUpdate(LifecycleEvent event) {
      this.makeCallback(event);
   }

   public void afterUpdatePerformed(LifecycleEvent event) {
      this.makeCallback(event);
   }

   public void beforeDelete(LifecycleEvent event) {
      this.makeCallback(event);
   }

   public void afterDelete(LifecycleEvent event) {
      throw new UnsupportedOperationException();
   }

   public void afterDeletePerformed(LifecycleEvent event) {
      this.makeCallback(event);
   }
}
