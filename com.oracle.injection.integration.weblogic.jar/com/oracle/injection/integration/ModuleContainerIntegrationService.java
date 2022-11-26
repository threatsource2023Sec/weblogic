package com.oracle.injection.integration;

import com.oracle.injection.InjectionArchive;
import com.oracle.pitchfork.inject.DeploymentUnitMetadata;
import com.oracle.pitchfork.interfaces.inject.InjectionI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import com.oracle.pitchfork.interfaces.intercept.InterceptionMetadataI;
import com.oracle.pitchfork.interfaces.intercept.InterceptorMetadataI;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.InjectionException;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.DefinitionException;
import javax.interceptor.Interceptor;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.security.auth.Subject;
import javax.servlet.ServletContext;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.UserTransaction;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.contexts.WeldCreationalContext;
import org.jboss.weld.interceptor.builder.InterceptionModelBuilder;
import org.jboss.weld.interceptor.proxy.InterceptionContext;
import org.jboss.weld.interceptor.reader.InterceptorMetadataImpl;
import org.jboss.weld.interceptor.reader.InterceptorMetadataUtils;
import org.jboss.weld.interceptor.reader.PlainInterceptorFactory;
import org.jboss.weld.interceptor.reader.TargetClassInterceptorMetadata;
import org.jboss.weld.interceptor.spi.metadata.InterceptorClassMetadata;
import org.jboss.weld.interceptor.spi.metadata.InterceptorFactory;
import org.jboss.weld.interceptor.spi.model.InterceptionModel;
import org.jboss.weld.interceptor.spi.model.InterceptionType;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.ClassTransformer;
import weblogic.application.ApplicationContextInternal;
import weblogic.j2ee.descriptor.wl.PojoEnvironmentBean;
import weblogic.j2ee.injection.PitchforkContext;
import weblogic.j2ee.injection.PojoComponentContributor;
import weblogic.j2ee.injection.PojoEnricher;
import weblogic.jndi.factories.java.javaURLContextFactory;
import weblogic.jndi.internal.JNDIHelper;
import weblogic.kernel.ThreadLocalStack;
import weblogic.security.Security;
import weblogic.security.WLSPrincipals;
import weblogic.security.spi.WLSUser;
import weblogic.transaction.TransactionHelper;

public class ModuleContainerIntegrationService implements WeblogicContainerIntegrationService {
   private static Logger m_logger = Logger.getLogger(ModuleContainerIntegrationService.class.getName());
   private static ThreadLocalStack currentEjbName = new ThreadLocalStack(true);
   private static final InterceptionModel EMPTY_INTERCEPTION_MODEL = new EmptyInterceptionModel();
   private Set m_setOfInjectionEnabledWebApplications;
   private final TransactionHelper m_transactionHelper;
   private Map producerFields;
   private boolean producerFieldsCleared;
   private final int[] producerFieldsSyncObj;
   private HashMap componentMetaData;
   private final Object componentMetaDataLockObj;
   private Map ejbInterceptorNameMappings;
   private Map aroundConstructCallbackModels;

   public ModuleContainerIntegrationService(TransactionHelper transactionHelper) {
      this(transactionHelper, (ApplicationContextInternal)null);
   }

   public ModuleContainerIntegrationService(TransactionHelper transactionHelper, ApplicationContextInternal applicationContextInternal) {
      this.m_setOfInjectionEnabledWebApplications = new HashSet();
      this.producerFields = new HashMap();
      this.producerFieldsCleared = false;
      this.producerFieldsSyncObj = new int[0];
      this.componentMetaData = new HashMap();
      this.componentMetaDataLockObj = new Object();
      this.ejbInterceptorNameMappings = new HashMap();
      this.aroundConstructCallbackModels = new HashMap();
      this.m_transactionHelper = transactionHelper;
   }

   private String getCurrentEjbName() {
      return (String)currentEjbName.peek();
   }

   public void setCurrentEjbName(String ejbName) {
      if (ejbName == null) {
         currentEjbName.pop();
      } else {
         currentEjbName.push(ejbName);
      }

   }

   public Principal getPrincipal() {
      Subject subject = Security.getCurrentSubject();
      Set setOfPrincipals = subject.getPrincipals(WLSUser.class);
      return setOfPrincipals.size() == 1 ? (Principal)setOfPrincipals.iterator().next() : WLSPrincipals.getAnonymousUserPrincipal();
   }

   public UserTransaction getUserTransaction() {
      return this.m_transactionHelper.getUserTransaction();
   }

   public boolean isTransactionActive() {
      return this.getCurrentTransaction() != null;
   }

   public void registerSynchronization(Synchronization synchronization) {
      Transaction transaction = this.getCurrentTransaction();
      if (transaction != null) {
         try {
            transaction.registerSynchronization(synchronization);
         } catch (RollbackException | SystemException var4) {
            m_logger.log(Level.FINER, "Exception registering transaction synchronization object.  Exception: " + var4);
         }
      }

   }

   public void addInjectionEnabledWebApp(ServletContext servletContext) {
      this.m_setOfInjectionEnabledWebApplications.add(servletContext.getContextPath());
   }

   public boolean isWebAppInjectionEnabled(ServletContext servletContext) {
      return this.m_setOfInjectionEnabledWebApplications.contains(servletContext.getContextPath());
   }

   private Transaction getCurrentTransaction() {
      try {
         return this.m_transactionHelper.getTransactionManager().getTransaction();
      } catch (SystemException var2) {
         return null;
      }
   }

   public void addInjectionMetaData(Class componentClass, Object metaData) {
      synchronized(this.componentMetaDataLockObj) {
         InjectionMetaDataHolder injectionMetaDataHolder = (InjectionMetaDataHolder)this.componentMetaData.get(componentClass);
         if (injectionMetaDataHolder == null) {
            injectionMetaDataHolder = new InjectionMetaDataHolder((Jsr250MetadataI)metaData, componentClass.isAnnotationPresent(Interceptor.class));
            this.componentMetaData.put(componentClass, injectionMetaDataHolder);
         } else {
            injectionMetaDataHolder.setMetaData((Jsr250MetadataI)metaData);
         }

      }
   }

   public void addEjbInterceptorInjectionMetaData(String ejbName, Class interceptorClass, Object interceptorMeta) {
      synchronized(this.componentMetaDataLockObj) {
         InjectionMetaDataHolder injectionMetaDataHolder = (InjectionMetaDataHolder)this.componentMetaData.get(interceptorClass);
         if (injectionMetaDataHolder == null) {
            injectionMetaDataHolder = new InjectionMetaDataHolder((Jsr250MetadataI)interceptorMeta, ejbName);
            this.componentMetaData.put(interceptorClass, injectionMetaDataHolder);
         } else {
            injectionMetaDataHolder.addEjbInterceptorMetaData(ejbName, (Jsr250MetadataI)interceptorMeta);
         }

      }
   }

   private Jsr250MetadataI getComponentMetaData(InjectionMetaDataHolder metaDataHolder) {
      return metaDataHolder != null ? metaDataHolder.getMetaData(this.getCurrentEjbName()) : null;
   }

   protected InjectionMetaDataHolder getInjectionMetaDataHolder(Class targetClass, InjectionArchive injectionArchive) {
      InjectionMetaDataHolder injectionMetaDataHolder = (InjectionMetaDataHolder)this.componentMetaData.get(targetClass);
      Jsr250MetadataI metaData;
      if (injectionMetaDataHolder == null) {
         metaData = this.createPojoJsr250MetaData(targetClass, injectionArchive);
         if (metaData != null) {
            this.addInjectionMetaData(targetClass, metaData);
            injectionMetaDataHolder = (InjectionMetaDataHolder)this.componentMetaData.get(targetClass);
         }
      } else {
         metaData = this.getComponentMetaData(injectionMetaDataHolder);
         if (metaData == null) {
            metaData = this.createPojoJsr250MetaData(targetClass, injectionArchive);
            if (metaData != null) {
               this.addInjectionMetaData(targetClass, metaData);
            }
         }
      }

      return injectionMetaDataHolder;
   }

   protected Jsr250MetadataI getJsr250Metadata(Class targetClass, InjectionArchive injectionArchive) {
      InjectionMetaDataHolder injectionMetaDataHolder = this.getInjectionMetaDataHolder(targetClass, injectionArchive);
      return this.getComponentMetaData(injectionMetaDataHolder);
   }

   public void registerProducerField(InjectionArchive injectionArchive, AnnotatedField producerField) {
      synchronized(this.producerFieldsSyncObj) {
         if (!this.producerFieldsCleared) {
            List annotatedFields = (List)this.producerFields.get(injectionArchive);
            if (annotatedFields == null) {
               annotatedFields = new ArrayList();
               this.producerFields.put(injectionArchive, annotatedFields);
            }

            ((List)annotatedFields).add(producerField);
         }

      }
   }

   public void clearProducerFields() {
      synchronized(this.producerFieldsSyncObj) {
         this.producerFields = new HashMap();
         this.producerFieldsCleared = true;
      }
   }

   public void validateProducerFields() throws DefinitionException {
      synchronized(this.producerFieldsSyncObj) {
         if (this.producerFields != null) {
            Iterator var2 = this.producerFields.keySet().iterator();

            while(var2.hasNext()) {
               InjectionArchive injectionArchive = (InjectionArchive)var2.next();
               ClassLoader currentContextClassLoader = Thread.currentThread().getContextClassLoader();

               try {
                  Thread.currentThread().setContextClassLoader(injectionArchive.getClassLoader());
                  Iterator var5 = ((List)this.producerFields.get(injectionArchive)).iterator();

                  while(var5.hasNext()) {
                     AnnotatedField annotatedField = (AnnotatedField)var5.next();
                     this.validateProducerField(annotatedField, injectionArchive);
                  }
               } finally {
                  Thread.currentThread().setContextClassLoader(currentContextClassLoader);
               }
            }
         }

      }
   }

   private boolean isProducerFieldForTheInjection(AnnotatedField producerField, InjectionI injection) {
      return producerField.getJavaMember().equals(injection.getMember());
   }

   private String composeCompenvLookupLocation(String name) {
      return name.startsWith("java:") ? name : "java:comp/env/" + name;
   }

   protected void validateProducerField(AnnotatedField producerField, InjectionArchive injectionArchive) {
      Class producerFieldClass = producerField.getJavaMember().getDeclaringClass();
      Jsr250MetadataI metaData = this.getJsr250Metadata(producerFieldClass, injectionArchive);
      if (metaData != null) {
         Context rootContext = injectionArchive.getRootContext(metaData.getComponentName());
         if (rootContext != null) {
            javaURLContextFactory.pushContext(rootContext);

            try {
               List injections = metaData.getInjections();
               if (injections != null) {
                  Iterator var7 = injections.iterator();

                  while(var7.hasNext()) {
                     InjectionI oneInjection = (InjectionI)var7.next();
                     if (this.isProducerFieldForTheInjection(producerField, oneInjection)) {
                        Class classToValidate = null;

                        try {
                           if (producerField.isAnnotationPresent(EJB.class)) {
                              String name = this.composeCompenvLookupLocation(oneInjection.getName());
                              classToValidate = JNDIHelper.lookupClassType(name, new InitialContext());
                           } else {
                              Object producedObject = metaData.resolve(oneInjection);
                              if (producedObject != null) {
                                 classToValidate = producedObject.getClass();
                              }
                           }
                        } catch (Exception var14) {
                        }

                        if (classToValidate != null && !producerField.getJavaMember().getType().isAssignableFrom(classToValidate)) {
                           throw new DefinitionException("The type of the injection point " + producerField.getJavaMember().getName() + " is " + producerField.getJavaMember().getType() + ".  The type of the physical resource is " + classToValidate.getClass() + ".  They are incompatible. ");
                        }
                     }
                  }
               }
            } finally {
               javaURLContextFactory.popContext();
            }
         }
      }

   }

   public void performJavaEEInjection(Class targetClass, Object target, InjectionArchive injectionArchive) {
      InjectionMetaDataHolder injectionMetaDataHolder = this.getInjectionMetaDataHolder(targetClass, injectionArchive);
      if (injectionMetaDataHolder != null) {
         Jsr250MetadataI metaData = this.getComponentMetaData(injectionMetaDataHolder);
         if (metaData != null) {
            boolean popContext = false;
            if (!injectionMetaDataHolder.isInterceptor()) {
               Context rootContext = injectionArchive.getRootContext(metaData.getComponentName());
               if (rootContext != null) {
                  javaURLContextFactory.pushContext(rootContext);
                  popContext = true;
               }
            }

            try {
               metaData.inject(target);
            } catch (Exception var12) {
               String msg = "Exception trying to inject java EE injection point";
               if (targetClass != null) {
                  msg = msg + " into class: " + targetClass.getName();
               }

               msg = msg + ".";
               throw new InjectionException(msg, var12);
            } finally {
               if (popContext) {
                  javaURLContextFactory.popContext();
               }

            }
         }
      }

   }

   protected Jsr250MetadataI createPojoJsr250MetaData(Class pojoClass, PojoEnvironmentBean pojoEnvironmentBean, ClassLoader classLoader) {
      if (!pojoClass.isInterface() && !pojoClass.equals(Object.class)) {
         if (pojoEnvironmentBean == null) {
            m_logger.log(Level.FINER, "Cannot create Jsr250Metadata for pojo: " + pojoClass.getName() + " because PojoEnvironmentBean is null.");
            return null;
         } else {
            PitchforkContext pitchforkContext = new PitchforkContext((String)null);
            PojoComponentContributor pojoComponentContributor = new PojoComponentContributor(pojoClass.getName(), pojoEnvironmentBean, pitchforkContext, classLoader);
            DeploymentUnitMetadata deploymentUnitMetadata = new DeploymentUnitMetadata();
            PojoEnricher pojoEnricher = new PojoEnricher(deploymentUnitMetadata);
            pojoComponentContributor.contribute(pojoEnricher);
            return pojoComponentContributor.getMetadata(pojoClass.getName());
         }
      } else {
         return null;
      }
   }

   protected Jsr250MetadataI createPojoJsr250MetaData(Class pojoClass, InjectionArchive injectionArchive) {
      return this.createPojoJsr250MetaData(pojoClass, injectionArchive.getPojoEnvironmentBean(), injectionArchive.getClassLoader());
   }

   public void addEjbInterceptorGeneratedNameMapping(String ejbName, String generatedInterceptorClassName, String interceptorClassName) {
      Map interceptorNameMappings = (Map)this.ejbInterceptorNameMappings.get(ejbName);
      if (interceptorNameMappings == null) {
         interceptorNameMappings = new HashMap();
         this.ejbInterceptorNameMappings.put(ejbName, interceptorNameMappings);
      }

      ((Map)interceptorNameMappings).put(generatedInterceptorClassName, interceptorClassName);
   }

   public Map getEjbInterceptorGeneratedNameMappings(String ejbName) {
      return (Map)this.ejbInterceptorNameMappings.get(ejbName);
   }

   public String getEjbInterceptorGeneratedNameMapping(String ejbName, String generatedInterceptorName) {
      String interceptorClassName = null;
      Map interceptorNameMappings = this.getEjbInterceptorGeneratedNameMappings(ejbName);
      if (interceptorNameMappings != null) {
         interceptorClassName = (String)interceptorNameMappings.get(generatedInterceptorName);
      }

      return interceptorClassName;
   }

   public void registerEjbDescriptorAroundConstructInterceptors(BeanManager beanManager, CreationalContext cc, Class beanClass, AnnotatedType annotatedType) {
      if (cc instanceof WeldCreationalContext) {
         InterceptionModel callbackModel = (InterceptionModel)this.aroundConstructCallbackModels.get(beanClass);
         if (callbackModel == null) {
            callbackModel = this.createAroundConstructCallbackInterceptionModel(beanManager, beanClass, annotatedType);
         }

         if (callbackModel != null && !EMPTY_INTERCEPTION_MODEL.equals(callbackModel)) {
            InterceptionContext interceptionContext = InterceptionContext.forConstructorInterception(callbackModel, cc, (BeanManagerImpl)beanManager, (SlimAnnotatedType)annotatedType);
            ((WeldCreationalContext)cc).registerAroundConstructCallback(new AssemblyDescriptorAroundConstructCallback(interceptionContext));
         }
      }

   }

   private InterceptionModel createAroundConstructCallbackInterceptionModel(BeanManager beanManager, Class beanClass, AnnotatedType annotatedType) {
      InterceptionModel callbackModel = null;
      List aroundConstructMetadatas = null;
      Object jsr250Metadata = this.getComponentMetaData((InjectionMetaDataHolder)this.componentMetaData.get(beanClass));
      if (jsr250Metadata instanceof InterceptionMetadataI) {
         aroundConstructMetadatas = ((InterceptionMetadataI)jsr250Metadata).getAroundConstructMetadatas();
         if (aroundConstructMetadatas == null) {
            return null;
         }
      }

      BeanManagerImpl bm = (BeanManagerImpl)beanManager;
      List knownACInterceptorClasses = new ArrayList();
      InterceptionModel im;
      Set cdiInterceptors;
      Iterator var12;
      InterceptorClassMetadata i;
      if (annotatedType != null) {
         if (annotatedType != null) {
            Map modelRegistry = bm.getInterceptorModelRegistry();
            if (modelRegistry != null) {
               im = (InterceptionModel)modelRegistry.get(annotatedType);
               if (im != null) {
                  cdiInterceptors = im.getAllInterceptors();
                  var12 = cdiInterceptors.iterator();

                  while(var12.hasNext()) {
                     i = (InterceptorClassMetadata)var12.next();
                     if (i.isEligible(InterceptionType.AROUND_CONSTRUCT)) {
                        knownACInterceptorClasses.add(i.getJavaClass());
                     }
                  }
               }
            }
         }
      } else if (bm.getInterceptorModelRegistry() != null) {
         Iterator var18 = bm.getInterceptorModelRegistry().values().iterator();

         label79:
         while(true) {
            do {
               if (!var18.hasNext()) {
                  break label79;
               }

               im = (InterceptionModel)var18.next();
            } while(im == null);

            cdiInterceptors = im.getAllInterceptors();
            var12 = cdiInterceptors.iterator();

            while(var12.hasNext()) {
               i = (InterceptorClassMetadata)var12.next();
               if (i.isEligible(InterceptionType.AROUND_CONSTRUCT)) {
                  knownACInterceptorClasses.add(i.getJavaClass());
               }
            }
         }
      }

      List interceptors = new LinkedList();
      if (aroundConstructMetadatas != null) {
         Iterator var20 = aroundConstructMetadatas.iterator();

         while(var20.hasNext()) {
            InterceptorMetadataI im = (InterceptorMetadataI)var20.next();
            Class key = im.getComponentClass();
            boolean callBackRequired = true;
            Iterator var14 = knownACInterceptorClasses.iterator();

            while(var14.hasNext()) {
               Class cdiInterceptorClass = (Class)var14.next();
               String cdiInterceptorName = cdiInterceptorClass.getName();
               if (cdiInterceptorName.equals(key.getName())) {
                  callBackRequired = false;
                  break;
               }

               String interceptorName = this.getEjbInterceptorGeneratedNameMapping(this.getCurrentEjbName(), key.getName());
               if (cdiInterceptorName.equals(interceptorName)) {
                  callBackRequired = false;
                  break;
               }
            }

            if (callBackRequired) {
               EnhancedAnnotatedType type = ((ClassTransformer)bm.getServices().get(ClassTransformer.class)).getEnhancedAnnotatedType(key, bm.getId());
               InterceptorFactory factory = PlainInterceptorFactory.of(key, bm);
               interceptors.add(new InterceptorMetadataImpl(key, factory, InterceptorMetadataUtils.buildMethodMap(type, false, bm)));
            }
         }
      }

      if (!interceptors.isEmpty()) {
         InterceptionModelBuilder modelBuilder = new InterceptionModelBuilder();
         Set classInterceptorBindings = new HashSet();
         modelBuilder.setClassInterceptorBindings(classInterceptorBindings);
         modelBuilder.interceptGlobal(javax.enterprise.inject.spi.InterceptionType.AROUND_CONSTRUCT, (Constructor)null, interceptors, (Set)null);
         callbackModel = modelBuilder.build();
         this.aroundConstructCallbackModels.put(beanClass, callbackModel);
      } else {
         this.aroundConstructCallbackModels.put(beanClass, EMPTY_INTERCEPTION_MODEL);
      }

      return callbackModel;
   }

   private static final class EmptyInterceptionModel implements InterceptionModel {
      private EmptyInterceptionModel() {
      }

      public List getInterceptors(InterceptionType interceptionType, Method method) {
         return null;
      }

      public List getConstructorInvocationInterceptors() {
         return null;
      }

      public Set getAllInterceptors() {
         return null;
      }

      public boolean hasExternalConstructorInterceptors() {
         return false;
      }

      public boolean hasExternalNonConstructorInterceptors() {
         return false;
      }

      public boolean hasTargetClassInterceptors() {
         return false;
      }

      public TargetClassInterceptorMetadata getTargetClassInterceptorMetadata() {
         return null;
      }

      public Set getClassInterceptorBindings() {
         return null;
      }

      public Set getMemberInterceptorBindings(Member member) {
         return null;
      }

      // $FF: synthetic method
      EmptyInterceptionModel(Object x0) {
         this();
      }
   }
}
