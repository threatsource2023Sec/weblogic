package weblogic.server;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.PartitionMBean;
import weblogic.t3.srvr.PartitionLifecycleLogger;

public class PartitionInterceptorBeanUpdateListener implements BeanUpdateListener {
   private static PartitionInterceptorService service;
   private static MethodHelper mHelper;
   private static Object serviceLock = new Object();
   private static Object methodHelperLock = new Object();
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");

   private static MethodHelper getMethodHelper() throws NoSuchMethodException {
      synchronized(methodHelperLock) {
         if (mHelper == null) {
            mHelper = new MethodHelper();
         }
      }

      return mHelper;
   }

   private static PartitionInterceptorService getPartitionCRUDService() {
      synchronized(serviceLock) {
         if (service == null) {
            service = (PartitionInterceptorService)GlobalServiceLocator.getServiceLocator().getService(PartitionInterceptorService.class, new Annotation[0]);
         }
      }

      return service;
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      try {
         this.invokePartitionCRUDInterceptor(event, getMethodHelper().PRE_CREATE_METHOD, getMethodHelper().PRE_DELETE_METHOD);
      } catch (Exception var3) {
         throw new BeanUpdateRejectedException("Prepare update failed :" + var3);
      }
   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      try {
         this.invokePartitionCRUDInterceptor(event, getMethodHelper().POST_CREATE_METHOD, getMethodHelper().POST_DELETE_METHOD);
      } catch (Exception var3) {
         throw new BeanUpdateFailedException("Prepare activate failed :" + var3);
      }
   }

   public void rollbackUpdate(BeanUpdateEvent event) {
      try {
         this.invokePartitionCRUDInterceptor(event, getMethodHelper().ROLLBACK_CREATE_METHOD, getMethodHelper().ROLLBACK_DELETE_METHOD);
      } catch (Exception var3) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("<PartitionInterceptorBeanUpdateListener> Rollback of partition create/delete event failed" + var3);
         }
      }

   }

   private void invokePartitionCRUDInterceptor(BeanUpdateEvent event, Method createMethod, Method deleteMethod) throws InvocationTargetException, IllegalAccessException {
      String partitionName = null;
      String operation = null;

      try {
         BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();

         for(int i = 0; i < updated.length; ++i) {
            BeanUpdateEvent.PropertyUpdate propertyUpdate = updated[i];
            if (propertyUpdate.getPropertyName().equals("Partitions")) {
               switch (propertyUpdate.getUpdateType()) {
                  case 2:
                     if (propertyUpdate.getAddedObject() instanceof PartitionMBean) {
                        partitionName = ((PartitionMBean)propertyUpdate.getAddedObject()).getName();
                     }

                     operation = createMethod.getName();
                     createMethod.invoke(getPartitionCRUDService(), propertyUpdate.getAddedObject());
                     break;
                  case 3:
                     if (propertyUpdate.getRemovedObject() instanceof PartitionMBean) {
                        partitionName = ((PartitionMBean)propertyUpdate.getRemovedObject()).getName();
                     }

                     operation = deleteMethod.getName();
                     deleteMethod.invoke(getPartitionCRUDService(), propertyUpdate.getRemovedObject());
               }
            }
         }

      } catch (IllegalAccessException | InvocationTargetException var9) {
         if (partitionName != null && operation != null) {
            PartitionLifecycleLogger.logPartitionInterceptorException(partitionName, operation, var9);
         }

         throw var9;
      }
   }

   private static class MethodHelper {
      Method PRE_CREATE_METHOD = null;
      Method POST_CREATE_METHOD = null;
      Method PRE_DELETE_METHOD = null;
      Method POST_DELETE_METHOD = null;
      Method ROLLBACK_DELETE_METHOD = null;
      Method ROLLBACK_CREATE_METHOD = null;

      MethodHelper() throws NoSuchMethodException {
         this.PRE_CREATE_METHOD = PartitionInterceptorService.class.getMethod("onPrePartitionCreate", PartitionMBean.class);
         this.POST_CREATE_METHOD = PartitionInterceptorService.class.getMethod("onPostPartitionCreate", PartitionMBean.class);
         this.PRE_DELETE_METHOD = PartitionInterceptorService.class.getMethod("onPreDeletePartition", PartitionMBean.class);
         this.POST_DELETE_METHOD = PartitionInterceptorService.class.getMethod("onPostDeletePartition", PartitionMBean.class);
         this.ROLLBACK_DELETE_METHOD = PartitionInterceptorService.class.getMethod("onRollbackDeletePartition", PartitionMBean.class);
         this.ROLLBACK_CREATE_METHOD = PartitionInterceptorService.class.getMethod("onRollbackCreatePartition", PartitionMBean.class);
      }
   }
}
