package weblogic.workarea;

import java.util.concurrent.CopyOnWriteArrayList;
import javax.naming.Context;
import javax.naming.NamingException;
import org.jvnet.hk2.annotations.Service;
import weblogic.core.base.api.FastThreadLocalMarker;
import weblogic.workarea.spi.AfterCopyContextsListener;
import weblogic.workarea.spi.AfterReceiveRequestListener;
import weblogic.workarea.spi.AfterSendRequestListener;
import weblogic.workarea.spi.BeforeSendRequestListener;
import weblogic.workarea.spi.WorkContextAccessController;
import weblogic.workarea.spi.WorkContextMapInterceptor;

@Service
public class WorkContextHelper implements FastThreadLocalMarker {
   private static final String WORK_CONTEXT_BINDING = "WorkContextMap";
   private static final WorkContextMapImpl map = new WorkContextMapImpl();
   private static WorkContextHelper singleton = new WorkContextHelper();
   static CopyOnWriteArrayList afterReceiveRequestListeners = new CopyOnWriteArrayList();
   static CopyOnWriteArrayList afterSendRequestListeners = new CopyOnWriteArrayList();
   static CopyOnWriteArrayList beforeSendRequestListeners = new CopyOnWriteArrayList();
   static CopyOnWriteArrayList afterCopyContextsListeners = new CopyOnWriteArrayList();

   protected WorkContextHelper() {
   }

   public static WorkContextHelper getWorkContextHelper() {
      return singleton;
   }

   public static void setWorkContextHelper(WorkContextHelper wam) {
      throw new IllegalArgumentException("WorkContextHelper does not currently support replacement");
   }

   public WorkContextMap getWorkContextMap() {
      return map;
   }

   public WorkContextMap getPriviledgedWorkContextMap() {
      return WorkContextAccessController.getPriviledgedWorkContextMap(map);
   }

   public WorkContextMapInterceptor getInterceptor() {
      return map;
   }

   public WorkContextMapInterceptor getLocalInterceptor() {
      return map.getInterceptor();
   }

   public WorkContextMapInterceptor createInterceptor() {
      return new WorkContextLocalMap();
   }

   public void setLocalInterceptor(WorkContextMapInterceptor interceptor) {
      map.setInterceptor(interceptor);
   }

   public void registerAfterReceiveRequestListener(AfterReceiveRequestListener listener) {
      if (WorkContextAccessController.isAccessAllowed((String)null, 4)) {
         afterReceiveRequestListeners.add(listener);
      }

   }

   public void registerAfterSendRequestListener(AfterSendRequestListener listener) {
      if (WorkContextAccessController.isAccessAllowed((String)null, 4)) {
         afterSendRequestListeners.add(listener);
      }

   }

   public void registerBeforeSendRequestListener(BeforeSendRequestListener listener) {
      if (WorkContextAccessController.isAccessAllowed((String)null, 4)) {
         beforeSendRequestListeners.add(listener);
      }

   }

   public void registerAfterCopyContextsListener(AfterCopyContextsListener listener) {
      if (WorkContextAccessController.isAccessAllowed((String)null, 4)) {
         afterCopyContextsListeners.add(listener);
      }

   }

   public void unRegisterAfterReceiveRequestListener(AfterReceiveRequestListener listener) {
      if (WorkContextAccessController.isAccessAllowed((String)null, 4)) {
         afterReceiveRequestListeners.remove(listener);
      }

   }

   public void unRegisterAfterSendRequestListener(AfterSendRequestListener listener) {
      if (WorkContextAccessController.isAccessAllowed((String)null, 4)) {
         afterSendRequestListeners.remove(listener);
      }

   }

   public void unRegisterBeforeSendRequestListener(BeforeSendRequestListener listener) {
      if (WorkContextAccessController.isAccessAllowed((String)null, 4)) {
         beforeSendRequestListeners.remove(listener);
      }

   }

   public void unRegisterAfterCopyContextsListener(AfterCopyContextsListener listener) {
      if (WorkContextAccessController.isAccessAllowed((String)null, 4)) {
         afterCopyContextsListeners.remove(listener);
      }

   }

   public static void bind(Context ctx) throws NamingException {
      ctx.bind("WorkContextMap", getWorkContextHelper().getWorkContextMap());
   }

   public static void unbind(Context ctx) throws NamingException {
      ctx.unbind("WorkContextMap");
   }

   public String getFastThreadLocalClassName() {
      return this.getClass().getCanonicalName();
   }
}
