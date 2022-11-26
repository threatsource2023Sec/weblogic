package org.apache.openjpa.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.MetaDataDefaults;
import org.apache.openjpa.util.InvalidStateException;

public class LifecycleEventManager implements CallbackModes, Serializable {
   private static final Exception[] EMPTY_EXCEPTIONS = new Exception[0];
   private static final Localizer _loc = Localizer.forPackage(LifecycleEventManager.class);
   private Map _classListeners = null;
   private ListenerList _listeners = null;
   private List _addListeners = new LinkedList();
   private List _remListeners = new LinkedList();
   private List _exceps = new LinkedList();
   private boolean _firing = false;
   private boolean _fail = false;
   private boolean _failFast = false;

   public boolean isFailFast() {
      return this._failFast;
   }

   public void setFailFast(boolean failFast) {
      this._failFast = failFast;
   }

   public synchronized void addListener(Object listener, Class[] classes) {
      if (listener != null) {
         if (classes == null || classes.length != 0) {
            if (this._firing) {
               this._addListeners.add(listener);
               this._addListeners.add(classes);
            } else if (classes == null) {
               if (this._listeners == null) {
                  this._listeners = new ListenerList(5);
               }

               this._listeners.add(listener);
            } else {
               if (this._classListeners == null) {
                  this._classListeners = new HashMap();
               }

               for(int i = 0; i < classes.length; ++i) {
                  ListenerList listeners = (ListenerList)this._classListeners.get(classes[i]);
                  if (listeners == null) {
                     listeners = new ListenerList(3);
                     this._classListeners.put(classes[i], listeners);
                  }

                  listeners.add(listener);
               }

            }
         }
      }
   }

   public synchronized void removeListener(Object listener) {
      if (this._firing) {
         this._remListeners.add(listener);
      } else if (this._listeners == null || !this._listeners.remove(listener)) {
         if (this._classListeners != null) {
            Iterator itr = this._classListeners.values().iterator();

            while(itr.hasNext()) {
               ListenerList listeners = (ListenerList)itr.next();
               listeners.remove(listener);
            }
         }

      }
   }

   public boolean hasPersistListeners(Object source, ClassMetaData meta) {
      return this.hasHandlers(source, meta, 0) || this.hasHandlers(source, meta, 1) || this.hasHandlers(source, meta, 18);
   }

   public boolean hasDeleteListeners(Object source, ClassMetaData meta) {
      return this.hasHandlers(source, meta, 7) || this.hasHandlers(source, meta, 8) || this.hasHandlers(source, meta, 19);
   }

   public boolean hasClearListeners(Object source, ClassMetaData meta) {
      return this.hasHandlers(source, meta, 5) || this.hasHandlers(source, meta, 6);
   }

   public boolean hasLoadListeners(Object source, ClassMetaData meta) {
      return this.hasHandlers(source, meta, 2);
   }

   public boolean hasStoreListeners(Object source, ClassMetaData meta) {
      return this.hasHandlers(source, meta, 3) || this.hasHandlers(source, meta, 4);
   }

   public boolean hasUpdateListeners(Object source, ClassMetaData meta) {
      return this.hasHandlers(source, meta, 20) || this.hasHandlers(source, meta, 21);
   }

   public boolean hasDirtyListeners(Object source, ClassMetaData meta) {
      return this.hasHandlers(source, meta, 9) || this.hasHandlers(source, meta, 10);
   }

   public boolean hasDetachListeners(Object source, ClassMetaData meta) {
      return this.hasHandlers(source, meta, 13) || this.hasHandlers(source, meta, 14);
   }

   public boolean hasAttachListeners(Object source, ClassMetaData meta) {
      return this.hasHandlers(source, meta, 15) || this.hasHandlers(source, meta, 16);
   }

   private boolean hasHandlers(Object source, ClassMetaData meta, int type) {
      return this.hasCallbacks(source, meta, type) || this.hasListeners(source, meta, type);
   }

   private boolean hasCallbacks(Object source, ClassMetaData meta, int type) {
      LifecycleCallbacks[] callbacks = meta.getLifecycleMetaData().getCallbacks(type);
      if (callbacks.length == 0) {
         return false;
      } else {
         for(int i = 0; i < callbacks.length; ++i) {
            if (callbacks[i].hasCallback(source, type)) {
               return true;
            }
         }

         return false;
      }
   }

   private synchronized boolean hasListeners(Object source, ClassMetaData meta, int type) {
      if (meta.getLifecycleMetaData().getIgnoreSystemListeners()) {
         return false;
      } else if (this.fireEvent((LifecycleEvent)null, source, (Object)null, type, this._listeners, true, (List)null) == Boolean.TRUE) {
         return true;
      } else {
         ListenerList system = meta.getRepository().getSystemListeners();
         if (!system.isEmpty() && this.fireEvent((LifecycleEvent)null, source, (Object)null, type, system, true, (List)null) == Boolean.TRUE) {
            return true;
         } else {
            if (this._classListeners != null) {
               Class c = source == null ? meta.getDescribedType() : source.getClass();

               do {
                  if (this.fireEvent((LifecycleEvent)null, source, (Object)null, type, (ListenerList)this._classListeners.get(c), true, (List)null) == Boolean.TRUE) {
                     return true;
                  }

                  c = c.getSuperclass();
               } while(c != null && c != Object.class);
            }

            return false;
         }
      }
   }

   public Exception[] fireEvent(Object source, ClassMetaData meta, int type) {
      return this.fireEvent(source, (Object)null, meta, type);
   }

   public synchronized Exception[] fireEvent(Object source, Object related, ClassMetaData meta, int type) {
      boolean reentrant = this._firing;
      this._firing = true;
      List exceptions = reentrant ? new LinkedList() : this._exceps;
      MetaDataDefaults def = meta.getRepository().getMetaDataFactory().getDefaults();
      boolean callbacks = def.getCallbacksBeforeListeners(type);
      if (callbacks) {
         this.makeCallbacks(source, related, meta, type, (Collection)exceptions);
      }

      LifecycleEvent ev = (LifecycleEvent)this.fireEvent((LifecycleEvent)null, source, related, type, this._listeners, false, (List)exceptions);
      if (this._classListeners != null) {
         Class c = source == null ? meta.getDescribedType() : source.getClass();

         do {
            ev = (LifecycleEvent)this.fireEvent(ev, source, related, type, (ListenerList)this._classListeners.get(c), false, (List)exceptions);
            c = c.getSuperclass();
         } while(c != null && c != Object.class);
      }

      if (!meta.getLifecycleMetaData().getIgnoreSystemListeners()) {
         ListenerList system = meta.getRepository().getSystemListeners();
         this.fireEvent(ev, source, related, type, system, false, (List)exceptions);
      }

      if (!callbacks) {
         this.makeCallbacks(source, related, meta, type, (Collection)exceptions);
      }

      Exception[] ret;
      if (((List)exceptions).isEmpty()) {
         ret = EMPTY_EXCEPTIONS;
      } else {
         ret = (Exception[])((Exception[])((List)exceptions).toArray(new Exception[((List)exceptions).size()]));
      }

      if (!reentrant) {
         this._firing = false;
         this._fail = false;
         Iterator itr;
         if (!this._addListeners.isEmpty()) {
            itr = this._addListeners.iterator();

            while(itr.hasNext()) {
               this.addListener(itr.next(), (Class[])((Class[])itr.next()));
            }
         }

         if (!this._remListeners.isEmpty()) {
            itr = this._remListeners.iterator();

            while(itr.hasNext()) {
               this.removeListener(itr.next());
            }
         }

         this._addListeners.clear();
         this._remListeners.clear();
         this._exceps.clear();
      }

      return ret;
   }

   private void makeCallbacks(Object source, Object related, ClassMetaData meta, int type, Collection exceptions) {
      LifecycleCallbacks[] callbacks = meta.getLifecycleMetaData().getCallbacks(type);

      for(int i = 0; !this._fail && i < callbacks.length; ++i) {
         try {
            callbacks[i].makeCallback(source, related, type);
         } catch (Exception var9) {
            exceptions.add(var9);
            if (this._failFast) {
               this._fail = true;
            }
         }
      }

   }

   private Object fireEvent(LifecycleEvent ev, Object source, Object rel, int type, ListenerList listeners, boolean mock, List exceptions) {
      if (listeners != null && listeners.hasListeners(type)) {
         int i = 0;

         for(int size = listeners.size(); !this._fail && i < size; ++i) {
            Object listener = listeners.get(i);
            boolean responds;
            if (size == 1) {
               responds = true;
            } else if (listener instanceof ListenerAdapter) {
               responds = ((ListenerAdapter)listener).respondsTo(type);
               if (!responds) {
                  continue;
               }
            } else {
               responds = false;
            }

            try {
               switch (type) {
                  case 0:
                  case 1:
                     if (responds || listener instanceof PersistListener) {
                        if (mock) {
                           return Boolean.TRUE;
                        }

                        if (ev == null) {
                           ev = new LifecycleEvent(source, type);
                        }

                        if (type == 0) {
                           ((PersistListener)listener).beforePersist(ev);
                        } else {
                           ((PersistListener)listener).afterPersist(ev);
                        }
                     }
                     break;
                  case 2:
                  case 17:
                     if (responds || listener instanceof LoadListener) {
                        if (mock) {
                           return Boolean.TRUE;
                        }

                        if (ev == null) {
                           ev = new LifecycleEvent(source, type);
                        }

                        if (type == 2) {
                           ((LoadListener)listener).afterLoad(ev);
                        } else {
                           ((LoadListener)listener).afterRefresh(ev);
                        }
                     }
                     break;
                  case 3:
                  case 4:
                     if (responds || listener instanceof StoreListener) {
                        if (mock) {
                           return Boolean.TRUE;
                        }

                        if (ev == null) {
                           ev = new LifecycleEvent(source, type);
                        }

                        if (type == 3) {
                           ((StoreListener)listener).beforeStore(ev);
                        } else {
                           ((StoreListener)listener).afterStore(ev);
                        }
                     }
                     break;
                  case 5:
                  case 6:
                     if (responds || listener instanceof ClearListener) {
                        if (mock) {
                           return Boolean.TRUE;
                        }

                        if (ev == null) {
                           ev = new LifecycleEvent(source, type);
                        }

                        if (type == 5) {
                           ((ClearListener)listener).beforeClear(ev);
                        } else {
                           ((ClearListener)listener).afterClear(ev);
                        }
                     }
                     break;
                  case 7:
                  case 8:
                     if (responds || listener instanceof DeleteListener) {
                        if (mock) {
                           return Boolean.TRUE;
                        }

                        if (ev == null) {
                           ev = new LifecycleEvent(source, type);
                        }

                        if (type == 7) {
                           ((DeleteListener)listener).beforeDelete(ev);
                        } else {
                           ((DeleteListener)listener).afterDelete(ev);
                        }
                     }
                     break;
                  case 9:
                  case 10:
                  case 11:
                  case 12:
                     if (responds || listener instanceof DirtyListener) {
                        if (mock) {
                           return Boolean.TRUE;
                        }

                        if (ev == null) {
                           ev = new LifecycleEvent(source, type);
                        }

                        switch (type) {
                           case 9:
                              ((DirtyListener)listener).beforeDirty(ev);
                              break;
                           case 10:
                              ((DirtyListener)listener).afterDirty(ev);
                              break;
                           case 11:
                              ((DirtyListener)listener).beforeDirtyFlushed(ev);
                              break;
                           case 12:
                              ((DirtyListener)listener).afterDirtyFlushed(ev);
                        }
                     }
                     break;
                  case 13:
                  case 14:
                     if (responds || listener instanceof DetachListener) {
                        if (mock) {
                           return Boolean.TRUE;
                        }

                        if (ev == null) {
                           ev = new LifecycleEvent(source, rel, type);
                        }

                        if (type == 13) {
                           ((DetachListener)listener).beforeDetach(ev);
                        } else {
                           ((DetachListener)listener).afterDetach(ev);
                        }
                     }
                     break;
                  case 15:
                  case 16:
                     if (responds || listener instanceof AttachListener) {
                        if (mock) {
                           return Boolean.TRUE;
                        }

                        if (ev == null) {
                           ev = new LifecycleEvent(source, rel, type);
                        }

                        if (type == 15) {
                           ((AttachListener)listener).beforeAttach(ev);
                        } else {
                           ((AttachListener)listener).afterAttach(ev);
                        }
                     }
                     break;
                  case 18:
                     if (responds || listener instanceof PostPersistListener) {
                        if (mock) {
                           return Boolean.TRUE;
                        }

                        if (ev == null) {
                           ev = new LifecycleEvent(source, rel, type);
                        }

                        ((PostPersistListener)listener).afterPersistPerformed(ev);
                     }
                     break;
                  case 19:
                     if (responds || listener instanceof PostDeleteListener) {
                        if (mock) {
                           return Boolean.TRUE;
                        }

                        if (ev == null) {
                           ev = new LifecycleEvent(source, rel, type);
                        }

                        ((PostDeleteListener)listener).afterDeletePerformed(ev);
                     }
                     break;
                  case 20:
                  case 21:
                     if (responds || listener instanceof UpdateListener) {
                        if (mock) {
                           return Boolean.TRUE;
                        }

                        if (ev == null) {
                           ev = new LifecycleEvent(source, rel, type);
                        }

                        if (type == 20) {
                           ((UpdateListener)listener).beforeUpdate(ev);
                        } else {
                           ((UpdateListener)listener).afterUpdatePerformed(ev);
                        }
                     }
                     break;
                  default:
                     throw new InvalidStateException(_loc.get("unknown-lifecycle-event", (Object)Integer.toString(type)));
               }
            } catch (Exception var13) {
               exceptions.add(var13);
               if (this._failFast) {
                  this._fail = true;
               }
            }
         }

         return ev;
      } else {
         return null;
      }
   }

   public static class ListenerList extends ArrayList {
      private int _types = 0;

      public ListenerList(int size) {
         super(size);
      }

      public ListenerList(ListenerList copy) {
         super(copy);
         this._types = copy._types;
      }

      public boolean hasListeners(int type) {
         return (this._types & 2 << type) > 0;
      }

      public boolean add(Object listener) {
         if (this.contains(listener)) {
            return false;
         } else {
            super.add(listener);
            this._types |= getEventTypes(listener);
            return true;
         }
      }

      public boolean remove(Object listener) {
         if (!super.remove(listener)) {
            return false;
         } else {
            this._types = 0;

            for(int i = 0; i < this.size(); ++i) {
               this._types |= getEventTypes(this.get(i));
            }

            return true;
         }
      }

      private static int getEventTypes(Object listener) {
         int types = 0;
         if (listener instanceof ListenerAdapter) {
            ListenerAdapter adapter = (ListenerAdapter)listener;

            for(int i = 0; i < LifecycleEvent.ALL_EVENTS.length; ++i) {
               if (adapter.respondsTo(LifecycleEvent.ALL_EVENTS[i])) {
                  types |= 2 << LifecycleEvent.ALL_EVENTS[i];
               }
            }

            return types;
         } else {
            if (listener instanceof PersistListener) {
               types |= 2;
               types |= 4;
            }

            if (listener instanceof ClearListener) {
               types |= 64;
               types |= 128;
            }

            if (listener instanceof DeleteListener) {
               types |= 256;
               types |= 512;
            }

            if (listener instanceof DirtyListener) {
               types |= 1024;
               types |= 2048;
               types |= 4096;
               types |= 8192;
            }

            if (listener instanceof LoadListener) {
               types |= 8;
               types |= 262144;
            }

            if (listener instanceof StoreListener) {
               types |= 16;
               types |= 32;
            }

            if (listener instanceof DetachListener) {
               types |= 16384;
               types |= 32768;
            }

            if (listener instanceof AttachListener) {
               types |= 65536;
               types |= 131072;
            }

            return types;
         }
      }
   }

   public interface ListenerAdapter {
      boolean respondsTo(int var1);
   }
}
