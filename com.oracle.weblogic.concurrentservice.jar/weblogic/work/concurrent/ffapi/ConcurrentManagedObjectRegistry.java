package weblogic.work.concurrent.ffapi;

import javax.enterprise.concurrent.ManagedExecutorService;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.work.concurrent.runtime.ConcurrentManagedObjectCollection;
import weblogic.work.concurrent.utils.ConcurrentUtils;

public enum ConcurrentManagedObjectRegistry {
   INSTANCE;

   public ManagedExecutorService lookupManagedExecutorService(String mesName) {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      String appId = cic.getApplicationId();
      if (appId == null) {
         throw new IllegalStateException("can not lookup regular JSR236 MES from out of an application. mesName=" + mesName);
      } else {
         ApplicationContextInternal appContext = ApplicationAccess.getApplicationAccess().getApplicationContext(appId);
         if (appContext == null) {
            throw new IllegalStateException("can not lookup regular JSR236 MES from an non-exist application. mesName=" + mesName + " appId=" + appId);
         } else {
            ConcurrentManagedObjectCollection collection = appContext.getConcurrentManagedObjectCollection();
            if (mesName != null && !mesName.equals(ConcurrentUtils.getDefaultMESInfo().getName())) {
               ManagedExecutorService mes = collection.getManagedExecutorService(ApplicationAccess.getApplicationAccess().getCurrentModuleName(), mesName);
               if (mes == null) {
                  throw new IllegalArgumentException("can not find MES " + mesName + " from " + cic);
               } else {
                  return mes;
               }
            } else {
               return collection.getDefaultManagedExecutorService();
            }
         }
      }
   }
}
