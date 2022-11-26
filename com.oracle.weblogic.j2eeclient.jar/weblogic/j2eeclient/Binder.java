package weblogic.j2eeclient;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import javax.annotation.ManagedBean;
import javax.mail.Session;
import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.LinkRef;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.xml.bind.ValidationException;
import org.omg.CORBA.ORB;
import weblogic.application.naming.DataSourceBinder;
import weblogic.application.naming.DataSourceOpaqueReference;
import weblogic.application.naming.MailSessionBinder;
import weblogic.deployment.ServiceRefProcessor;
import weblogic.deployment.ServiceRefProcessorException;
import weblogic.deployment.ServiceRefProcessorFactory;
import weblogic.ejb20.internal.HandleDelegateImpl;
import weblogic.j2ee.descriptor.ApplicationClientBean;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.InjectionTargetBean;
import weblogic.j2ee.descriptor.JmsConnectionFactoryBean;
import weblogic.j2ee.descriptor.JmsDestinationBean;
import weblogic.j2ee.descriptor.MessageDestinationBean;
import weblogic.j2ee.descriptor.MessageDestinationRefBean;
import weblogic.j2ee.descriptor.PersistenceUnitRefBean;
import weblogic.j2ee.descriptor.PortComponentRefBean;
import weblogic.j2ee.descriptor.ResourceEnvRefBean;
import weblogic.j2ee.descriptor.ResourceRefBean;
import weblogic.j2ee.descriptor.ServiceRefBean;
import weblogic.j2ee.descriptor.wl.EjbReferenceDescriptionBean;
import weblogic.j2ee.descriptor.wl.MessageDestinationDescriptorBean;
import weblogic.j2ee.descriptor.wl.ResourceDescriptionBean;
import weblogic.j2ee.descriptor.wl.ResourceEnvDescriptionBean;
import weblogic.j2ee.descriptor.wl.ServiceReferenceDescriptionBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationClientBean;
import weblogic.jndi.SimpleContext;
import weblogic.persistence.BasePersistenceUnitInfo;
import weblogic.rmi.extensions.DisconnectMonitorListImpl;
import weblogic.utils.Debug;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.validation.Bindable;
import weblogic.validation.injection.ValidationManager;
import weblogic.workarea.WorkContextHelper;

public final class Binder {
   private static final AppClientTextTextFormatter TEXT_FORMATTER = AppClientTextTextFormatter.getInstance();
   private static final String JAVA_COMP_ENV = "java:comp/env";
   private static final String PLATFORM_DEFAULT_JMS_CONNECTION_FACTORY = "DefaultJMSConnectionFactory";
   private static final String JMS_DEFAULT_JMS_CONNECTION_FACTORY = "weblogic.jms.DefaultConnectionFactory";
   private String moduleName = null;
   private static final boolean DEBUG = Boolean.getBoolean("weblogic.debug.DebugJ2EEClient");
   private final Context remoteRootCtx;
   private final Context localRootCtx;
   private ApplicationClientBean stdDD;
   private WeblogicApplicationClientBean wlDD;
   private DataSourceBinder dataSourceBinder;
   private MailSessionBinder mailSessionBinder;
   private ManagedBeanProcessor mgedBeanProcessor;
   private String applicationName;
   private String clientJarName;
   private static Collection lookupTypes = Arrays.asList("ejb-ref", "javax.jms.QueueConnectionFactory", "javax.jms.TopicConnectionFactory", "javax.jms.ConnectionFactory", "javax.jms.Queue", "javax.jms.Topic", "javax.sql.DataSource");

   public DataSourceBinder getDataSourceBinder() {
      return this.dataSourceBinder;
   }

   public ManagedBeanProcessor getMgedBeanProcessor() {
      return this.mgedBeanProcessor;
   }

   public Binder(String clientJarName, String url, String applicationName, String moduleName, ApplicationClientBean stdDD, WeblogicApplicationClientBean wlDD, Context localRootCtx, Context remoteRootCtx) throws Exception {
      this.moduleName = moduleName;
      this.applicationName = applicationName;
      this.clientJarName = clientJarName;
      this.localRootCtx = localRootCtx;
      this.remoteRootCtx = remoteRootCtx;
      this.stdDD = stdDD;
      this.wlDD = wlDD;
   }

   void bindToEnvironment(GenericClassLoader gcl, AppClientPersistenceUnitRegistry appclpu) throws Exception {
      Context globalCtx = this.localRootCtx.createSubcontext("java:global");
      Context localAppCtx = this.localRootCtx.createSubcontext("java:app");
      Context moduleCtx = this.localRootCtx.createSubcontext("java:module");
      if (this.moduleName != null) {
         if (this.moduleName.endsWith(".jar")) {
            moduleCtx.bind("ModuleName", this.moduleName.substring(0, this.moduleName.lastIndexOf(46)));
         } else {
            moduleCtx.bind("ModuleName", this.moduleName);
         }
      }

      Context compCtx = this.localRootCtx.createSubcontext("java:comp");
      compCtx.bind("InAppClientContainer", true);
      this.fillEnvironmentWithValidatorEntries(this.getValidationDescURL(gcl), new Bindable() {
         public void bind(String name, Object value) throws NamingException {
            Binder.this.localRootCtx.bind(name, value);
         }
      });

      try {
         compCtx.bind("UserTransaction", this.remoteRootCtx.lookup("javax.transaction.UserTransaction"));
      } catch (NameNotFoundException var11) {
         AppClientLogger.userTransactionEntryNotFoundLoggable(var11).getMessageText();
      }

      compCtx.bind("HandleDelegate", new HandleDelegateImpl());
      WorkContextHelper.bind(compCtx);
      DisconnectMonitorListImpl.bindToJNDI(compCtx);
      compCtx.bind("ORB", new SimpleContext.SimpleReference() {
         public Object get() throws NamingException {
            return ORB.init(new String[0], (Properties)null);
         }
      });
      this.dataSourceBinder = new DataSourceBinder(globalCtx, localAppCtx, moduleCtx, compCtx, (String)null, this.moduleName, (String)null);
      this.mailSessionBinder = new MailSessionBinder(globalCtx, localAppCtx, moduleCtx, compCtx, (String)null, this.moduleName, (String)null);
      DataSourceOpaqueReference.bootStrapOnClient(this.dataSourceBinder);

      try {
         compCtx.bind("DefaultJMSConnectionFactory", this.remoteRootCtx.lookup("weblogic.jms.DefaultConnectionFactory"));
      } catch (NamingException var10) {
         AppClientLogger.defaultJMSConnectionFactoryBindFailure(var10.getMessage());
      }

      this.processJMSResourceDefinitions(compCtx, moduleCtx);
      Context compEnvContext = null;

      try {
         compEnvContext = (Context)compCtx.lookup("env");
      } catch (NamingException var9) {
      }

      if (compEnvContext == null) {
         compEnvContext = compCtx.createSubcontext("env");
      }

      this.fillEnvContext(compEnvContext, appclpu);
      this.processManagedBean(gcl, localAppCtx, moduleCtx);
   }

   private void processManagedBean(GenericClassLoader gcl, Context appCtx, Context moduleCtx) throws Exception {
      this.mgedBeanProcessor = new ManagedBeanProcessor(this.applicationName, this.moduleName, appCtx, moduleCtx, gcl);
      this.mgedBeanProcessor.process();
   }

   private URL getValidationDescURL(GenericClassLoader gcl) {
      return gcl.getResource("META-INF/validation.xml");
   }

   private void fillEnvironmentWithValidatorEntries(URL validationDescriptorUrl, Bindable binder) throws ValidationException, NamingException, IOException {
      ValidationManager.defaultInstance().getDefaultValidationBean(binder, validationDescriptorUrl);
   }

   private void fillEnvContext(Context envCtx, AppClientPersistenceUnitRegistry appclpu) throws NamingException, IOException, Exception {
      this.envEntries(this.stdDD.getEnvEntries());
      this.ejbRefs(this.stdDD.getEjbRefs(), this.wlDD.getEjbReferenceDescriptions());
      this.serviceRefs(this.stdDD.getServiceRefs(), this.wlDD.getServiceReferenceDescriptions());
      this.resourceRefs(this.stdDD.getResourceRefs(), this.wlDD.getResourceDescriptions());
      this.resourceEnvRefs(this.stdDD.getResourceEnvRefs(), this.wlDD.getResourceEnvDescriptions());
      this.persistenceUnitRefs(envCtx, this.stdDD.getPersistenceUnitRefs(), appclpu);
      this.dataSourceBinder.bindDataSources(this.stdDD.getDataSources());
      this.mailSessionBinder.bindMailSessions(this.stdDD.getMailSessions());
      this.processMessageDestinationRefs(this.stdDD, this.wlDD);
   }

   private void processMessageDestinationRefs(ApplicationClientBean stdDD, WeblogicApplicationClientBean wlDD) throws IOException, NamingException {
      MessageDestinationRefBean[] mdrs = stdDD.getMessageDestinationRefs();
      if (mdrs != null) {
         for(int count = 0; count < mdrs.length; ++count) {
            MessageDestinationRefBean destRef = mdrs[count];
            String name = destRef.getMessageDestinationRefName();
            if (this.shouldProcess(name)) {
               String lookupName = destRef.getLookupName();
               if (lookupName != null) {
                  try {
                     this.bind(name, this.localRootCtx.lookup(lookupName));
                  } catch (final ConfigurationException var11) {
                     throw new ConfigurationException("Unable to lookup name: " + lookupName) {
                        {
                           this.rootException = var11;
                        }
                     };
                  }
               } else {
                  String messageDestinationName = null;
                  MessageDestinationDescriptorBean mdd = null;
                  if (destRef.getMessageDestinationLink() != null) {
                     MessageDestinationBean md = stdDD.lookupMessageDestination(destRef.getMessageDestinationLink());
                     if (md == null) {
                        messageDestinationName = destRef.getMessageDestinationRefName();
                        mdd = wlDD.lookupMessageDestinationDescriptor(messageDestinationName);
                        if (mdd == null) {
                           throw new IOException(TEXT_FORMATTER.messageDestinationNotFoundWithLink("<message-destination>", "<message-destination-ref>", "<message-destination-link>", destRef.getMessageDestinationLink().toString()));
                        }
                     } else {
                        messageDestinationName = md.getMessageDestinationName();
                        mdd = wlDD.lookupMessageDestinationDescriptor(messageDestinationName);
                        if (mdd == null) {
                           throw new IOException(TEXT_FORMATTER.messageDestinationDescriptorNotFoundWithName("<message-destination-descriptor>", "<message-destination-name>", "<message-destination>", messageDestinationName));
                        }
                     }
                  } else {
                     messageDestinationName = destRef.getMessageDestinationRefName();
                     mdd = wlDD.lookupMessageDestinationDescriptor(messageDestinationName);
                     if (mdd == null) {
                        throw new IOException(TEXT_FORMATTER.messageDestinationDescriptorNotFoundWithRef("<message-destination-descriptor>", "<message-destination-ref>", name, "<message-destination-name>"));
                     }
                  }

                  Object dest = this.lookupMessageDestination(mdd);
                  this.bind(messageDestinationName, dest);
               }
            }
         }
      }

   }

   private Object lookupMessageDestination(MessageDestinationDescriptorBean mdd) throws NamingException {
      Hashtable parameters = new Hashtable();
      if (mdd.getInitialContextFactory() != null) {
         parameters.put("java.naming.factory.initial", mdd.getInitialContextFactory());
      }

      if (mdd.getProviderUrl() != null) {
         parameters.put("java.naming.provider.url", mdd.getProviderUrl());
      }

      Context serverCtx = new InitialContext(parameters);
      return serverCtx.lookup(mdd.getDestinationJNDIName());
   }

   private void persistenceUnitRefs(Context envCtx, PersistenceUnitRefBean[] puRefs, AppClientPersistenceUnitRegistry registry) throws Exception {
      for(int i = 0; puRefs != null && i < puRefs.length; ++i) {
         String name = puRefs[i].getPersistenceUnitRefName();
         if (this.shouldProcess(name)) {
            EntityManagerFactory emf = this.getEntityManagerFactory(puRefs[i], registry);
            this.bind(puRefs[i].getPersistenceUnitRefName(), emf);
         }
      }

   }

   private EntityManagerFactory getEntityManagerFactory(PersistenceUnitRefBean prb, AppClientPersistenceUnitRegistry registry) throws Exception {
      String name = this.getPersistenceUnitName(prb.getPersistenceUnitName(), prb.getInjectionTargets());
      BasePersistenceUnitInfo puInfo = (BasePersistenceUnitInfo)registry.getPersistenceUnit(name);
      if (puInfo == null) {
         throw new IllegalArgumentException(AppClientLogger.persistentInitAvailableInScopeLoggable(name).getMessageText());
      } else {
         return puInfo.getEntityManagerFactory();
      }
   }

   private String getPersistenceUnitName(String name, InjectionTargetBean[] targets) throws Exception {
      if (name != null && !"".equals(name)) {
         return name;
      } else if (targets != null && targets.length == 1) {
         return targets[0].getInjectionTargetName();
      } else if (targets != null && targets.length != 0) {
         throw new Exception(TEXT_FORMATTER.persistentCtxWithManyInjectionTgts());
      } else {
         throw new Exception(TEXT_FORMATTER.persistentCtxWithoutInjectionTgt());
      }
   }

   private Object resolveSimpleType(String typeName, String value, String lookupName) throws IOException, NamingException {
      if (value == null) {
         return this.localRootCtx.lookup(lookupName);
      } else if ("java.lang.String".equals(typeName)) {
         return value;
      } else {
         try {
            return Class.forName(typeName).getConstructor(String.class).newInstance(value);
         } catch (NoSuchMethodException var5) {
            throw new AssertionError(var5);
         } catch (IllegalAccessException var6) {
            throw new AssertionError(var6);
         } catch (InstantiationException var7) {
            throw new IOException(TEXT_FORMATTER.unableToCreateEnvEntry(typeName, value, var7));
         } catch (InvocationTargetException var8) {
            throw new IOException(TEXT_FORMATTER.unableToCreateEnvEntry(typeName, value, var8.getTargetException()));
         } catch (ClassNotFoundException var9) {
            throw new AssertionError(var9);
         }
      }
   }

   private void envEntries(EnvEntryBean[] stdEnvs) throws NamingException, IOException {
      if (stdEnvs != null) {
         for(int i = 0; i < stdEnvs.length; ++i) {
            EnvEntryBean e = stdEnvs[i];
            String entryName = e.getEnvEntryName();
            if (this.shouldProcess(entryName)) {
               if (e.getEnvEntryValue() == null && e.getLookupName() == null) {
                  if (e.getInjectionTargets().length == 0) {
                     throw new IOException(TEXT_FORMATTER.envEntryWithNoValueOrTgt(e.getEnvEntryName()));
                  }
               } else {
                  String lookupName = e.getLookupName();
                  this.bind(entryName, this.resolveSimpleType(e.getEnvEntryType(), e.getEnvEntryValue(), lookupName));
               }
            }
         }
      }

   }

   private void bind(String name, Object obj) throws NamingException {
      this.localRootCtx.bind(this.toFullyQualifiedName(name), obj);
   }

   private String findEJBJNDIName(EjbRefBean ref, EjbReferenceDescriptionBean[] d) throws IOException {
      if (ref.getLookupName() != null) {
         return ref.getLookupName();
      } else {
         String name = ref.getEjbRefName();
         if (d != null) {
            for(int i = 0; i < d.length; ++i) {
               if (name.equals(d[i].getEjbRefName())) {
                  return d[i].getJNDIName();
               }
            }
         }

         throw new IOException(TEXT_FORMATTER.missingJndiNameForTag("<ejb-ref>", name));
      }
   }

   private Object resolveResource(ResourceRefBean resRefBean, ResourceDescriptionBean[] resDescBean) throws NamingException, IOException {
      String name = resRefBean.getResRefName();
      String type = resRefBean.getResType();
      String jndiName = null;
      if (resDescBean != null) {
         for(int i = 0; i < resDescBean.length; ++i) {
            if (name.equals(resDescBean[i].getResRefName())) {
               jndiName = resDescBean[i].getJNDIName();
               break;
            }
         }
      }

      if (jndiName == null) {
         jndiName = resRefBean.getLookupName();
      }

      if (jndiName == null) {
         throw new IOException(TEXT_FORMATTER.missingJndiNameForTag("<resource-ref>", name));
      } else if (lookupTypes.contains(type)) {
         return this.localRootCtx.lookup(jndiName);
      } else if (type.equals("javax.mail.Session")) {
         try {
            return Session.getDefaultInstance(new Properties());
         } catch (Exception var7) {
            throw new IOException(StackTraceUtils.throwable2StackTrace(var7));
         }
      } else if (this.isManagedBean(type)) {
         return new LinkRef(jndiName);
      } else {
         try {
            return Class.forName(type).getConstructor(String.class).newInstance(jndiName);
         } catch (Exception var8) {
            var8.printStackTrace();
            throw new IOException(StackTraceUtils.throwable2StackTrace(var8));
         }
      }
   }

   private boolean isManagedBean(String type) {
      try {
         Class beanClass = Class.forName(type, true, Thread.currentThread().getContextClassLoader());
         return beanClass.getAnnotation(ManagedBean.class) != null;
      } catch (ClassNotFoundException var3) {
         return false;
      }
   }

   private String findResourceEnvJNDIName(String name, String type, ResourceEnvDescriptionBean[] d) throws IOException, ClassNotFoundException {
      if (type != null && type.trim().length() > 0) {
         Class clz = Class.forName(type);
         if (Validator.class.isAssignableFrom(clz)) {
            return "java:comp/Validator";
         }

         if (ValidatorFactory.class.isAssignableFrom(clz)) {
            return "java:comp/ValidatorFactory";
         }
      }

      if (d != null) {
         for(int i = 0; i < d.length; ++i) {
            if (name.equals(d[i].getResourceEnvRefName())) {
               return d[i].getJNDIName();
            }
         }
      }

      return null;
   }

   private void ejbRefs(EjbRefBean[] ejbRefs, EjbReferenceDescriptionBean[] ejbDescs) throws NamingException, IOException {
      if (ejbRefs != null && ejbRefs.length != 0) {
         for(int i = 0; i < ejbRefs.length; ++i) {
            String name = ejbRefs[i].getEjbRefName();
            if (this.shouldProcess(name)) {
               String jndiName = this.findEJBJNDIName(ejbRefs[i], ejbDescs);
               if (DEBUG) {
                  Debug.say("Adding ejb link: " + name + "->" + jndiName);
               }

               this.bind(name, new LinkRef(jndiName));
            }
         }

      }
   }

   private void serviceRefs(ServiceRefBean[] serviceRefs, ServiceReferenceDescriptionBean[] wlserviceRefs) throws NamingException, ServiceRefProcessorException {
      if (wlserviceRefs == null) {
         wlserviceRefs = new ServiceReferenceDescriptionBean[0];
      }

      Map wlMap = new HashMap();

      int i;
      String name;
      for(i = 0; i < wlserviceRefs.length; ++i) {
         name = wlserviceRefs[i].getServiceRefName();
         if (this.shouldProcess(name)) {
            wlMap.put(name, wlserviceRefs[i]);
         }
      }

      for(i = 0; i < serviceRefs.length; ++i) {
         name = serviceRefs[i].getServiceRefName();
         if (this.shouldProcess(name)) {
            ServiceReferenceDescriptionBean wlbean = (ServiceReferenceDescriptionBean)wlMap.get(name);
            this.removePortComponentLink(serviceRefs[i]);
            ServiceRefProcessor helper = ServiceRefProcessorFactory.getInstance().getProcessor(serviceRefs[i], wlbean, (ServletContext)null);
            helper.bindServiceRef(this.localRootCtx, (Context)this.localRootCtx.lookup("java:comp/env"), this.clientJarName);
         }
      }

   }

   private void removePortComponentLink(ServiceRefBean serviceRef) {
      PortComponentRefBean[] refs = serviceRef.getPortComponentRefs();

      for(int i = 0; i < refs.length; ++i) {
         PortComponentRefBean ref = refs[i];
         ref.setPortComponentLink((String)null);
      }

   }

   private void resourceRefs(ResourceRefBean[] resRefs, ResourceDescriptionBean[] resDescs) throws NamingException, IOException {
      if (resRefs != null && resRefs.length != 0) {
         for(int i = 0; i < resRefs.length; ++i) {
            if (!"org.omg.CORBA.ORB".equals(resRefs[i].getResType())) {
               String name = resRefs[i].getResRefName();
               if (this.shouldProcess(name)) {
                  this.bind(name, this.resolveResource(resRefs[i], resDescs));
               }
            }
         }

      }
   }

   private void resourceEnvRefs(ResourceEnvRefBean[] resRefs, ResourceEnvDescriptionBean[] resDescs) throws NamingException, IOException, ClassNotFoundException {
      if (resRefs != null && resRefs.length != 0) {
         ResourceEnvRefBean[] var3 = resRefs;
         int var4 = resRefs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ResourceEnvRefBean resRef = var3[var5];
            String name = resRef.getResourceEnvRefName();
            if (this.shouldProcess(name)) {
               String jndiName = this.findResourceEnvJNDIName(name, resRef.getResourceEnvRefType(), resDescs);
               if (jndiName == null) {
                  jndiName = resRef.getLookupName();
               }

               if (jndiName == null) {
                  throw new IOException(TEXT_FORMATTER.missingJndiNameForTags("<resource-env-ref>", name, "@Resource", "<resource-env-description>"));
               }

               this.bind(name, this.localRootCtx.lookup(jndiName));
            }
         }

      }
   }

   private String toFullyQualifiedName(String name) {
      return name.startsWith("java:") ? name : "java:comp/env/" + name;
   }

   private boolean shouldProcess(String entryName) {
      return !entryName.startsWith("java:global") && !entryName.startsWith("java:app");
   }

   private static Context getJMSServerResourceContainerContext(Context localRootCtx, String applicationName, String clientJarName) throws NamingException {
      Context globalJavaAppContext = (Context)localRootCtx.lookup("__WL_GlobalJavaApp");
      Context applicationContext = (Context)globalJavaAppContext.lookup(applicationName);
      return (Context)applicationContext.lookup(clientJarName.replace('.', '_'));
   }

   private void processJMSResourceDefinitions(Context compCtx, Context moduleCtx) throws Exception {
      Context appClientContext = null;
      String jndiName = null;

      try {
         JmsConnectionFactoryBean[] var5 = this.stdDD.getJmsConnectionFactories();
         int var6 = var5.length;

         int var7;
         for(var7 = 0; var7 < var6; ++var7) {
            JmsConnectionFactoryBean jmsConnectionFactoryBean = var5[var7];
            if (jmsConnectionFactoryBean.getName().startsWith("java:comp")) {
               if (appClientContext == null) {
                  appClientContext = getJMSServerResourceContainerContext(this.localRootCtx, this.applicationName, this.clientJarName);
               }

               jndiName = jmsConnectionFactoryBean.getName().substring("java:comp".length() + 1);
               compCtx.bind(jndiName, appClientContext.lookup(jndiName));
            } else if (jmsConnectionFactoryBean.getName().startsWith("java:module")) {
               if (appClientContext == null) {
                  appClientContext = getJMSServerResourceContainerContext(this.localRootCtx, this.applicationName, this.clientJarName);
               }

               jndiName = jmsConnectionFactoryBean.getName().substring("java:module".length() + 1);
               moduleCtx.bind(jndiName, appClientContext.lookup(jndiName));
            }
         }

         JmsDestinationBean[] var12 = this.stdDD.getJmsDestinations();
         var6 = var12.length;

         for(var7 = 0; var7 < var6; ++var7) {
            JmsDestinationBean jmsDestinationBean = var12[var7];
            if (jmsDestinationBean.getName().startsWith("java:comp")) {
               if (appClientContext == null) {
                  appClientContext = getJMSServerResourceContainerContext(this.localRootCtx, this.applicationName, this.clientJarName);
               }

               jndiName = jmsDestinationBean.getName().substring("java:comp".length() + 1);
               compCtx.bind(jndiName, appClientContext.lookup(jndiName));
            } else if (jmsDestinationBean.getName().startsWith("java:module")) {
               if (appClientContext == null) {
                  appClientContext = getJMSServerResourceContainerContext(this.localRootCtx, this.applicationName, this.clientJarName);
               }

               jndiName = jmsDestinationBean.getName().substring("java:module".length() + 1);
               moduleCtx.bind(jndiName, appClientContext.lookup(jndiName));
            }
         }
      } finally {
         if (appClientContext != null) {
            appClientContext.close();
         }

      }

   }
}
