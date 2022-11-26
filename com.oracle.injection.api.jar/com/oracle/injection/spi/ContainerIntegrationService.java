package com.oracle.injection.spi;

import com.oracle.injection.InjectionArchive;
import java.security.Principal;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.transaction.Synchronization;
import javax.transaction.UserTransaction;

public interface ContainerIntegrationService {
   Principal getPrincipal();

   UserTransaction getUserTransaction();

   boolean isTransactionActive();

   void registerSynchronization(Synchronization var1);

   void registerProducerField(InjectionArchive var1, AnnotatedField var2);

   void clearProducerFields();

   void performJavaEEInjection(Class var1, Object var2, InjectionArchive var3);

   void addInjectionMetaData(Class var1, Object var2);

   void addEjbInterceptorInjectionMetaData(String var1, Class var2, Object var3);

   void setCurrentEjbName(String var1);

   void registerEjbDescriptorAroundConstructInterceptors(BeanManager var1, CreationalContext var2, Class var3, AnnotatedType var4);
}
