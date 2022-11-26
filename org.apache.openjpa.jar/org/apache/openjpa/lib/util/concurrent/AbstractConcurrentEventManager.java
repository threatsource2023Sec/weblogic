package org.apache.openjpa.lib.util.concurrent;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.openjpa.lib.util.EventManager;

public abstract class AbstractConcurrentEventManager implements EventManager, Serializable {
   private static final Exception[] EMPTY_EXCEPTIONS = new Exception[0];
   protected final Collection _listeners = this.newListenerCollection();
   private boolean _failFast = false;

   public boolean isFailFast() {
      return this._failFast;
   }

   public void setFailFast(boolean failFast) {
      this._failFast = failFast;
   }

   public void addListener(Object listener) {
      if (listener != null) {
         this._listeners.add(listener);
      }

   }

   public boolean removeListener(Object listener) {
      return this._listeners.remove(listener);
   }

   public boolean hasListener(Object listener) {
      return this._listeners.contains(listener);
   }

   public boolean hasListeners() {
      return !this._listeners.isEmpty();
   }

   public Collection getListeners() {
      return Collections.unmodifiableCollection(this._listeners);
   }

   public Exception[] fireEvent(Object event) {
      if (this._listeners.isEmpty()) {
         return EMPTY_EXCEPTIONS;
      } else {
         List exceptions = null;
         Iterator itr = this._listeners.iterator();

         while(itr.hasNext()) {
            try {
               this.fireEvent(event, itr.next());
            } catch (Exception var5) {
               if (this._failFast) {
                  return new Exception[]{var5};
               }

               if (exceptions == null) {
                  exceptions = new LinkedList();
               }

               exceptions.add(var5);
            }
         }

         return exceptions == null ? EMPTY_EXCEPTIONS : (Exception[])((Exception[])exceptions.toArray(new Exception[exceptions.size()]));
      }
   }

   protected abstract void fireEvent(Object var1, Object var2) throws Exception;

   protected Collection newListenerCollection() {
      return new CopyOnWriteArrayList();
   }
}
