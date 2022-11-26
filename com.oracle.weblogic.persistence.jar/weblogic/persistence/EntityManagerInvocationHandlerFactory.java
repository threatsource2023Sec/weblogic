package weblogic.persistence;

import java.lang.reflect.InvocationHandler;
import javax.persistence.SynchronizationType;
import weblogic.application.naming.PersistenceUnitRegistry;

public final class EntityManagerInvocationHandlerFactory {
   public static InvocationHandler createTransactionalEntityManagerInvocationHandler(String applicationName, String moduleName, String puName, PersistenceUnitRegistry puReg, SynchronizationType syncType) {
      InterceptingInvocationHandler ih = new TransactionalEntityManagerProxyImpl(applicationName, moduleName, puName, puReg, syncType);
      perhapsSetJPA1Interceptor(puName, puReg, ih);
      return ih;
   }

   public static InvocationHandler createExtendedEntityManagerInvocationHandler(String applicationName, String moduleName, String puName, PersistenceUnitRegistry puReg) {
      InterceptingInvocationHandler ih = new ExtendedEntityManagerProxyImpl(applicationName, moduleName, puName, puReg);
      perhapsSetJPA1Interceptor(puName, puReg, ih);
      return ih;
   }

   private static void perhapsSetJPA1Interceptor(String puName, PersistenceUnitRegistry puReg, InterceptingInvocationHandler ih) {
      BasePersistenceUnitInfo pui = (BasePersistenceUnitInfo)puReg.getPersistenceUnit(puName);
      if (pui.isJPA1FilteringEnabled()) {
         JPA1EntityManagerInterceptor iceptor = new JPA1EntityManagerInterceptor();
         ih.setInterceptor(iceptor);
      }

   }
}
