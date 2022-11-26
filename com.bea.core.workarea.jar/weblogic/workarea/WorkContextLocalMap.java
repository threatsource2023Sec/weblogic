package weblogic.workarea;

import java.io.IOException;
import java.security.AccessControlException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.workarea.spi.AfterCopyContextsListener;
import weblogic.workarea.spi.AfterReceiveRequestListener;
import weblogic.workarea.spi.AfterSendRequestListener;
import weblogic.workarea.spi.BeforeSendRequestListener;
import weblogic.workarea.spi.WorkContextAccessController;
import weblogic.workarea.spi.WorkContextEntry;
import weblogic.workarea.spi.WorkContextEntryImpl;
import weblogic.workarea.spi.WorkContextMapInterceptor;

final class WorkContextLocalMap implements WorkContextMap, WorkContextMapInterceptor {
   private final ConcurrentHashMap map = new ConcurrentHashMap();
   private int version = this.hashCode();
   private static final DebugLogger debugWorkContext = DebugLogger.getDebugLogger("DebugWorkContext");

   public WorkContext put(String key, WorkContext workContext, int propagationMode) throws PropertyReadOnlyException {
      if (debugWorkContext.isDebugEnabled()) {
         debugWorkContext.debug("put(" + key + ", " + workContext + ")");
      }

      if (key != null && !key.equals("")) {
         if (workContext == null) {
            throw new NullPointerException("Cannot use null WorkContext");
         } else {
            WorkContextEntry wce = (WorkContextEntry)this.map.get(key);
            if (wce != null) {
               if (!WorkContextAccessController.isAccessAllowed(key, 2)) {
                  throw new PropertyReadOnlyException(key);
               }
            } else if (!WorkContextAccessController.isAccessAllowed(key, 0)) {
               throw new AccessControlException("No CREATE permission for key: \"" + key + "\"");
            }

            this.map.put(key, new WorkContextEntryImpl(key, workContext, propagationMode));
            ++this.version;
            return wce == null ? null : wce.getWorkContext();
         }
      } else {
         throw new NullPointerException("Cannot use null key");
      }
   }

   public WorkContext put(String key, WorkContext workContext) throws PropertyReadOnlyException {
      return this.put(key, workContext, 212);
   }

   public WorkContext get(String key) {
      if (debugWorkContext.isDebugEnabled()) {
         debugWorkContext.debug("get(" + key + ")");
      }

      if (!WorkContextAccessController.isAccessAllowed(key, 1)) {
         throw new AccessControlException("No READ permission for key: \"" + key + "\"");
      } else {
         WorkContextEntry wce = this.getEntry(key);
         return wce == WorkContextEntry.NULL_CONTEXT ? null : wce.getWorkContext();
      }
   }

   public WorkContext remove(String key) throws NoWorkContextException, PropertyReadOnlyException {
      if (debugWorkContext.isDebugEnabled()) {
         debugWorkContext.debug("remove(" + key + ")");
      }

      WorkContextEntry wce = this.getEntry(key);
      if (wce == WorkContextEntry.NULL_CONTEXT) {
         throw new NoWorkContextException(key);
      } else if (!wce.isOriginator() && !WorkContextAccessController.isAccessAllowed(key, 3)) {
         throw new PropertyReadOnlyException("No DELETE permission for key: \"" + key + "\"");
      } else {
         this.map.remove(key);
         ++this.version;
         return wce.getWorkContext();
      }
   }

   public int getPropagationMode(String key) {
      if (!WorkContextAccessController.isAccessAllowed(key, 1)) {
         throw new AccessControlException("No READ permission for key: \"" + key + "\"");
      } else {
         return this.getEntry(key).getPropagationMode();
      }
   }

   public boolean isPropagationModePresent(int propMode) {
      boolean status = false;
      Iterator i = this.map.values().iterator();

      while(i.hasNext()) {
         WorkContextEntry wce = (WorkContextEntry)i.next();
         if (wce != null && (wce.getPropagationMode() & propMode) != 0) {
            status = true;
            break;
         }
      }

      return status;
   }

   public boolean isEmpty() {
      return this.map.isEmpty();
   }

   public Iterator iterator() {
      return new WorkContextIterator(this);
   }

   public Iterator keys() {
      return new WorkContextKeys(this);
   }

   private final WorkContextEntry getEntry(String key) {
      WorkContextEntry wce;
      return !this.map.isEmpty() && (wce = (WorkContextEntry)this.map.get(key)) != null ? wce : WorkContextEntry.NULL_CONTEXT;
   }

   public void sendRequest(WorkContextOutput out, int propagationMode) throws IOException {
      Iterator i;
      if (!WorkContextHelper.beforeSendRequestListeners.isEmpty()) {
         i = WorkContextHelper.beforeSendRequestListeners.iterator();

         while(i.hasNext()) {
            BeforeSendRequestListener listener = (BeforeSendRequestListener)i.next();

            try {
               listener.sendRequest(propagationMode, this);
            } catch (Throwable var7) {
               if (debugWorkContext.isDebugEnabled()) {
                  debugWorkContext.debug("beforeSendRequestListeners(" + var7.toString() + ")");
               }
            }
         }
      }

      i = this.map.values().iterator();

      while(i.hasNext()) {
         WorkContextEntry wce = (WorkContextEntry)i.next();
         int filteredPropagationMode = WorkContextFilter.getFilteredPropagationMode(wce.getName(), propagationMode);
         if ((wce.getPropagationMode() & filteredPropagationMode) != 0) {
            if (debugWorkContext.isDebugEnabled()) {
               debugWorkContext.debug("sendRequest(" + wce.toString() + ")");
            }

            wce.write(out);
         }
      }

      WorkContextEntry.NULL_CONTEXT.write(out);
      if (!WorkContextHelper.afterSendRequestListeners.isEmpty()) {
         i = WorkContextHelper.afterSendRequestListeners.iterator();

         while(i.hasNext()) {
            AfterSendRequestListener listener = (AfterSendRequestListener)i.next();

            try {
               listener.requestSent(propagationMode, this);
            } catch (Throwable var6) {
               if (debugWorkContext.isDebugEnabled()) {
                  debugWorkContext.debug("afterSendRequestListeners(" + var6.toString() + ")");
               }
            }
         }
      }

   }

   public void sendResponse(WorkContextOutput out, int propagationMode) throws IOException {
      Iterator i = this.map.values().iterator();

      while(i.hasNext()) {
         WorkContextEntry wce = (WorkContextEntry)i.next();
         if ((wce.getPropagationMode() & 256) == 0 && (wce.getPropagationMode() & propagationMode) != 0) {
            if (debugWorkContext.isDebugEnabled()) {
               debugWorkContext.debug("sendResponse(" + wce.toString() + ")");
            }

            wce.write(out);
         }
      }

      WorkContextEntry.NULL_CONTEXT.write(out);
   }

   public void receiveRequest(WorkContextInput in) throws IOException {
      while(true) {
         while(true) {
            try {
               WorkContextEntry wce = WorkContextEntryImpl.readEntry(in);
               if (wce != WorkContextEntry.NULL_CONTEXT) {
                  String key = wce.getName();
                  this.map.put(key, wce);
                  if (debugWorkContext.isDebugEnabled()) {
                     debugWorkContext.debug("receiveRequest(" + wce.toString() + ")");
                  }
                  continue;
               }
            } catch (ClassNotFoundException var6) {
               if (debugWorkContext.isDebugEnabled()) {
                  debugWorkContext.debug("receiveRequest : ", var6);
               }
               continue;
            }

            if (!WorkContextHelper.afterReceiveRequestListeners.isEmpty()) {
               Iterator var7 = WorkContextHelper.afterReceiveRequestListeners.iterator();

               while(var7.hasNext()) {
                  AfterReceiveRequestListener listener = (AfterReceiveRequestListener)var7.next();

                  try {
                     listener.requestReceived(this);
                  } catch (Throwable var5) {
                     if (debugWorkContext.isDebugEnabled()) {
                        debugWorkContext.debug("afterReceiveRequestListeners(" + var5.toString() + ")");
                     }
                  }
               }
            }

            return;
         }
      }
   }

   public void receiveResponse(WorkContextInput in) throws IOException {
      Set propKeySet = new HashSet();
      Iterator i = this.map.values().iterator();

      while(i.hasNext()) {
         WorkContextEntry wce = (WorkContextEntry)i.next();
         int propMode = wce.getPropagationMode();
         if ((propMode & 256) == 0 && (propMode & 1) == 0) {
            propKeySet.add(wce.getName());
            i.remove();
            ++this.version;
         }
      }

      if (in != null) {
         while(true) {
            while(true) {
               try {
                  WorkContextEntry wce = WorkContextEntryImpl.readEntry(in);
                  ++this.version;
                  if (wce == WorkContextEntry.NULL_CONTEXT) {
                     return;
                  }

                  if (propKeySet.contains(wce.getName())) {
                     this.map.put(wce.getName(), new WorkContextEntryImpl(wce.getName(), wce.getWorkContext(), wce.getPropagationMode()));
                  } else {
                     this.map.put(wce.getName(), wce);
                  }
               } catch (ClassNotFoundException var6) {
                  if (debugWorkContext.isDebugEnabled()) {
                     debugWorkContext.debug("receiveResponse : ", var6);
                  }
               }
            }
         }
      }
   }

   public WorkContextMapInterceptor copyThreadContexts(int mode) {
      if (this.map.isEmpty()) {
         return null;
      } else {
         WorkContextLocalMap copy = new WorkContextLocalMap();
         copy.map.putAll(this.map);
         copy.version = this.version;
         Iterator i = copy.map.values().iterator();

         while(i.hasNext()) {
            WorkContextEntry e = (WorkContextEntry)i.next();
            if ((e.getPropagationMode() & mode) == 0) {
               i.remove();
            }

            if (debugWorkContext.isDebugEnabled()) {
               debugWorkContext.debug("copyThreadContexts(" + e.toString() + ")");
            }
         }

         if (copy.map.isEmpty()) {
            return null;
         } else {
            if (!WorkContextHelper.afterCopyContextsListeners.isEmpty()) {
               i = WorkContextHelper.afterCopyContextsListeners.iterator();

               while(i.hasNext()) {
                  AfterCopyContextsListener listener = (AfterCopyContextsListener)i.next();

                  try {
                     listener.contextsCopied(mode, this, copy);
                  } catch (Throwable var6) {
                     if (debugWorkContext.isDebugEnabled()) {
                        debugWorkContext.debug("afterCopyContextsListeners(" + var6.toString() + ")");
                     }
                  }
               }
            }

            return copy;
         }
      }
   }

   public void restoreThreadContexts(WorkContextMapInterceptor contexts) {
      if (debugWorkContext.isDebugEnabled()) {
         debugWorkContext.debug("restoreThreadContexts(" + (contexts == null ? null : ((WorkContextLocalMap)contexts).map) + ")");
      }

      if (contexts == this) {
         throw new AssertionError("Cyclic attempt to restore thread contexts");
      } else if (contexts != null) {
         this.map.clear();
         this.map.putAll(((WorkContextLocalMap)contexts).map);
         ++this.version;
      }
   }

   public WorkContextMapInterceptor suspendThreadContexts() {
      throw new UnsupportedOperationException("suspendThreadContexts()");
   }

   public void resumeThreadContexts(WorkContextMapInterceptor contexts) {
      throw new UnsupportedOperationException("resumeThreadContexts()");
   }

   public int version() {
      return this.version;
   }

   private static void p(String msg) {
      System.out.println("<WorkContextLocalMap>: " + msg);
   }

   private final class WorkContextKeys implements Iterator {
      final Iterator iter;

      private WorkContextKeys(WorkContextLocalMap map) {
         this.iter = map.map.values().iterator();
      }

      public void remove() {
         WorkContextLocalMap.this.version++;
         this.iter.remove();
      }

      public boolean hasNext() {
         return this.iter.hasNext();
      }

      public Object next() {
         WorkContextEntry wce;
         for(wce = (WorkContextEntry)this.iter.next(); wce != null && !WorkContextAccessController.isAccessAllowed(wce.getName(), 1) && this.hasNext(); wce = (WorkContextEntry)this.iter.next()) {
         }

         return wce == null ? null : wce.getName();
      }

      // $FF: synthetic method
      WorkContextKeys(WorkContextLocalMap x1, Object x2) {
         this(x1);
      }
   }

   private final class WorkContextIterator implements Iterator {
      final Iterator iter;

      private WorkContextIterator(WorkContextLocalMap map) {
         this.iter = map.map.values().iterator();
      }

      public void remove() {
         WorkContextLocalMap.this.version++;
         this.iter.remove();
      }

      public boolean hasNext() {
         return this.iter.hasNext();
      }

      public Object next() {
         WorkContextEntry wce;
         for(wce = (WorkContextEntry)this.iter.next(); wce != null && !WorkContextAccessController.isAccessAllowed(wce.getName(), 1) && this.hasNext(); wce = (WorkContextEntry)this.iter.next()) {
         }

         return wce == null ? null : wce.getWorkContext();
      }

      // $FF: synthetic method
      WorkContextIterator(WorkContextLocalMap x1, Object x2) {
         this(x1);
      }
   }
}
