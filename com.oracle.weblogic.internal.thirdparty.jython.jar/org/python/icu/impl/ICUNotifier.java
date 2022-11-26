package org.python.icu.impl;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;

public abstract class ICUNotifier {
   private final Object notifyLock = new Object();
   private NotifyThread notifyThread;
   private List listeners;

   public void addListener(EventListener l) {
      if (l == null) {
         throw new NullPointerException();
      } else if (this.acceptsListener(l)) {
         synchronized(this.notifyLock) {
            if (this.listeners == null) {
               this.listeners = new ArrayList();
            } else {
               Iterator var3 = this.listeners.iterator();

               while(var3.hasNext()) {
                  EventListener ll = (EventListener)var3.next();
                  if (ll == l) {
                     return;
                  }
               }
            }

            this.listeners.add(l);
         }
      } else {
         throw new IllegalStateException("Listener invalid for this notifier.");
      }
   }

   public void removeListener(EventListener l) {
      if (l == null) {
         throw new NullPointerException();
      } else {
         synchronized(this.notifyLock) {
            if (this.listeners != null) {
               Iterator iter = this.listeners.iterator();

               while(iter.hasNext()) {
                  if (iter.next() == l) {
                     iter.remove();
                     if (this.listeners.size() == 0) {
                        this.listeners = null;
                     }

                     return;
                  }
               }
            }

         }
      }
   }

   public void notifyChanged() {
      synchronized(this.notifyLock) {
         if (this.listeners != null) {
            if (this.notifyThread == null) {
               this.notifyThread = new NotifyThread(this);
               this.notifyThread.setDaemon(true);
               this.notifyThread.start();
            }

            this.notifyThread.queue((EventListener[])this.listeners.toArray(new EventListener[this.listeners.size()]));
         }

      }
   }

   protected abstract boolean acceptsListener(EventListener var1);

   protected abstract void notifyListener(EventListener var1);

   private static class NotifyThread extends Thread {
      private final ICUNotifier notifier;
      private final List queue = new ArrayList();

      NotifyThread(ICUNotifier notifier) {
         this.notifier = notifier;
      }

      public void queue(EventListener[] list) {
         synchronized(this) {
            this.queue.add(list);
            this.notify();
         }
      }

      public void run() {
         while(true) {
            try {
               EventListener[] list;
               synchronized(this) {
                  while(true) {
                     if (!this.queue.isEmpty()) {
                        list = (EventListener[])this.queue.remove(0);
                        break;
                     }

                     this.wait();
                  }
               }

               for(int i = 0; i < list.length; ++i) {
                  this.notifier.notifyListener(list[i]);
               }
            } catch (InterruptedException var5) {
            }
         }
      }
   }
}
