package weblogic.workarea;

import java.io.IOException;
import java.util.Iterator;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.kernel.ThreadLocalInitialValue;
import weblogic.workarea.spi.WorkContextMapInterceptor;

final class WorkContextMapImpl implements WorkContextMap, WorkContextMapInterceptor {
   private static final AuditableThreadLocal localContextMap = AuditableThreadLocalFactory.createThreadLocal(new ThreadLocalInitialValue() {
      protected Object childValue(Object parentValue) {
         return parentValue == null ? null : ((WorkContextMapInterceptor)parentValue).copyThreadContexts(2);
      }
   });

   public WorkContext put(String key, WorkContext workContext, int propagationMode) throws PropertyReadOnlyException {
      WorkContext var4;
      try {
         var4 = this.getMap().put(key, workContext, propagationMode);
      } finally {
         if (this.getMapMaybe().isEmpty()) {
            this.reset();
         }

      }

      return var4;
   }

   public WorkContext put(String key, WorkContext workContext) throws PropertyReadOnlyException {
      WorkContext var3;
      try {
         var3 = this.getMap().put(key, workContext);
      } finally {
         if (this.getMapMaybe().isEmpty()) {
            this.reset();
         }

      }

      return var3;
   }

   public WorkContext get(String key) {
      WorkContextMap map = this.getMapMaybe();
      return map == null ? null : map.get(key);
   }

   public WorkContext remove(String key) throws NoWorkContextException, PropertyReadOnlyException {
      WorkContextMap map = this.getMapMaybe();
      if (map == null) {
         throw new NoWorkContextException();
      } else {
         WorkContext prev = map.remove(key);
         if (map.isEmpty()) {
            this.reset();
         }

         return prev;
      }
   }

   public int getPropagationMode(String key) {
      return this.isEmpty() ? 1 : this.getMapMaybe().getPropagationMode(key);
   }

   public boolean isPropagationModePresent(int propMode) {
      return this.getMapMaybe().isPropagationModePresent(propMode);
   }

   public boolean isEmpty() {
      return this.getMapMaybe() == null;
   }

   private void reset() {
      localContextMap.set((Object)null);
   }

   public Iterator iterator() {
      WorkContextMap map = this.getMapMaybe();
      return map == null ? null : map.iterator();
   }

   public Iterator keys() {
      WorkContextMap map = this.getMapMaybe();
      return map == null ? null : map.keys();
   }

   public int version() {
      WorkContextMapInterceptor map = (WorkContextMapInterceptor)localContextMap.get();
      return map != null ? map.version() : 0;
   }

   private final WorkContextMap getMapMaybe() {
      return (WorkContextMap)localContextMap.get();
   }

   private final WorkContextMap getMap() {
      WorkContextMap map = (WorkContextMap)localContextMap.get();
      if (map == null) {
         map = new WorkContextLocalMap();
         localContextMap.set(map);
      }

      return (WorkContextMap)map;
   }

   public WorkContextMapInterceptor getInterceptor() {
      return (WorkContextMapInterceptor)this.getMapMaybe();
   }

   public void setInterceptor(WorkContextMapInterceptor interceptor) {
      localContextMap.set(interceptor);
   }

   public void sendRequest(WorkContextOutput out, int propagationMode) throws IOException {
      WorkContextMapInterceptor inter = this.getInterceptor();
      if (inter != null) {
         inter.sendRequest(out, propagationMode);
      }

   }

   public void sendResponse(WorkContextOutput out, int propagationMode) throws IOException {
      WorkContextMapInterceptor inter = this.getInterceptor();
      if (inter != null) {
         inter.sendResponse(out, 4);
      }

   }

   public void receiveRequest(WorkContextInput in) throws IOException {
      ((WorkContextMapInterceptor)this.getMap()).receiveRequest(in);
   }

   public void receiveResponse(WorkContextInput in) throws IOException {
      WorkContextMap map = this.getMapMaybe();
      if (in != null || map != null) {
         if (map == null) {
            map = new WorkContextLocalMap();
            localContextMap.set(map);
         }

         ((WorkContextMapInterceptor)map).receiveResponse(in);
         if (((WorkContextMap)map).isEmpty()) {
            this.reset();
         }

      }
   }

   public WorkContextMapInterceptor copyThreadContexts(int mode) {
      WorkContextMapInterceptor inter = this.getInterceptor();
      return inter != null ? inter.copyThreadContexts(mode) : null;
   }

   public void restoreThreadContexts(WorkContextMapInterceptor contexts) {
      if (contexts != null) {
         ((WorkContextMapInterceptor)this.getMap()).restoreThreadContexts(contexts);
      }

   }

   public WorkContextMapInterceptor suspendThreadContexts() {
      WorkContextMapInterceptor map = this.getInterceptor();
      if (map != null) {
         this.reset();
      }

      return map;
   }

   public void resumeThreadContexts(WorkContextMapInterceptor contexts) {
      localContextMap.set(contexts);
   }
}
