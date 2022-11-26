package weblogic.j2ee.dd.xml;

import commonj.timers.TimerManager;
import commonj.work.WorkManager;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.annotation.Resource.AuthenticationType;
import javax.annotation.sql.DataSourceDefinition;
import javax.annotation.sql.DataSourceDefinitions;
import javax.ejb.EJB;
import javax.ejb.EJBHome;
import javax.ejb.EJBs;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.mail.MailSessionDefinition;
import javax.mail.MailSessionDefinitions;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;
import javax.persistence.PersistenceUnit;
import javax.persistence.PersistenceUnits;
import javax.resource.AdministeredObjectDefinition;
import javax.resource.AdministeredObjectDefinitions;
import javax.resource.ConnectionFactoryDefinition;
import javax.resource.ConnectionFactoryDefinitions;
import javax.sql.DataSource;
import javax.xml.ws.WebServiceRef;
import org.omg.CORBA_2_3.ORB;
import weblogic.application.utils.ManagementUtils;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.j2ee.dd.xml.validator.AnnotationValidatorVisitor;
import weblogic.j2ee.descriptor.AdministeredObjectBean;
import weblogic.j2ee.descriptor.ConnectionFactoryResourceBean;
import weblogic.j2ee.descriptor.DataSourceBean;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.InjectionTargetBean;
import weblogic.j2ee.descriptor.J2eeClientEnvironmentBean;
import weblogic.j2ee.descriptor.JavaEEPropertyBean;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.j2ee.descriptor.MailSessionBean;
import weblogic.j2ee.descriptor.MessageDestinationRefBean;
import weblogic.j2ee.descriptor.PersistenceUnitRefBean;
import weblogic.j2ee.descriptor.PropertyBean;
import weblogic.j2ee.descriptor.ResourceEnvRefBean;
import weblogic.j2ee.descriptor.ResourceRefBean;
import weblogic.j2ee.descriptor.ServiceRefBean;
import weblogic.j2ee.extensions.ExtensionManager;
import weblogic.j2ee.extensions.InjectionExtension;
import weblogic.javaee.EJBReference;
import weblogic.kernel.KernelStatus;
import weblogic.managedbean.ManagedBeanUtils;
import weblogic.management.configuration.DataSourceMBean;
import weblogic.management.configuration.DataSourcePartitionMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.ErrorCollectionException;

public class BaseJ2eeAnnotationProcessor extends RecordingAnnotationProcessor {
   private static final boolean PRODUCTION_MODE;
   private final Map methodsCache;
   private WseeAnnotationProcessor wseeAnnotationProcessor;
   private ErrorCollectionException errors;
   private List services;
   private Map envs;
   protected static final boolean VERIFY_IF_IN_DESCRIPTOR = false;
   protected static final boolean IS_NOT_IN_DESCRIPTOR = true;
   private static final Map toPrimitive;
   private static final Map toEvolved;

   public BaseJ2eeAnnotationProcessor() {
      this((Set)null);
   }

   public BaseJ2eeAnnotationProcessor(Set supportedAnnotations) {
      super(supportedAnnotations);
      this.methodsCache = new HashMap();
      this.services = GlobalServiceLocator.getServiceLocator().getAllServices(AnnotationProcessorService.class, new Annotation[0]);
      this.envs = new HashMap();
      this.initializeWseeAnnotationProcessor();
   }

   public void processJ2eeAnnotations(Class beanClass, J2eeClientEnvironmentBean eg, boolean throwErrors) throws ErrorCollectionException {
      this.processJ2eeAnnotations(beanClass, eg);
      if (throwErrors) {
         this.throwProcessingErrors();
      }

   }

   private void initializeWseeAnnotationProcessor() {
      this.wseeAnnotationProcessor = (WseeAnnotationProcessor)GlobalServiceLocator.getServiceLocator().getService(WseeAnnotationProcessor.class, new Annotation[0]);
   }

   protected WseeAnnotationProcessor getWseeAnnotationProcessor() {
      return this.wseeAnnotationProcessor;
   }

   protected void processJ2eeAnnotations(Class beanClass, J2eeClientEnvironmentBean eg) {
      this.addDescriptorDefaults(beanClass, eg);
      List allFields = this.getFields(beanClass);
      List allMethods = this.getMethods(beanClass);
      Iterator var5 = allFields.iterator();

      while(var5.hasNext()) {
         Field f = (Field)var5.next();
         this.processField(f, eg);
      }

      this.processMethods(allMethods, eg);
      Collection resources = this.getClassResources(beanClass).values();
      Iterator var11 = resources.iterator();

      while(var11.hasNext()) {
         Resource resource = (Resource)var11.next();
         this.addEnvironmentEntry(resource, eg);
      }

      Collection ejbs = this.getClassEJBRefs(beanClass).values();
      Iterator var13 = ejbs.iterator();

      while(var13.hasNext()) {
         EJB ejb = (EJB)var13.next();
         this.addEjbRef(ejb, eg);
      }

      Collection pus = this.getClassPersistenceUnitRefs(beanClass).values();
      Iterator var15 = pus.iterator();

      while(var15.hasNext()) {
         PersistenceUnit pu = (PersistenceUnit)var15.next();
         this.addPersistenceUnitRef(pu, eg);
      }

      if (this.wseeAnnotationProcessor != null) {
         this.wseeAnnotationProcessor.addWebServiceRefs(beanClass, eg, this);
      }

      var15 = this.getDataSourceDefinitions(beanClass).iterator();

      while(var15.hasNext()) {
         DataSourceDefinition dataSourceDef = (DataSourceDefinition)var15.next();
         this.addDataSource(dataSourceDef, eg);
      }

      var15 = this.getMailSessionDefinitions(beanClass).iterator();

      while(var15.hasNext()) {
         MailSessionDefinition mailSessionDef = (MailSessionDefinition)var15.next();
         this.addMailSessionDefinition(mailSessionDef, eg);
      }

      var15 = this.getConnectionFactoryDefinitions(beanClass).iterator();

      while(var15.hasNext()) {
         ConnectionFactoryDefinition connectionFactoryDef = (ConnectionFactoryDefinition)var15.next();
         this.addConnectionFactoryDefinition(connectionFactoryDef, eg);
      }

      var15 = this.getAdministeredObjectDefinitions(beanClass).iterator();

      while(var15.hasNext()) {
         AdministeredObjectDefinition administeredObjectDef = (AdministeredObjectDefinition)var15.next();
         this.addAdministeredObjectDefinition(administeredObjectDef, eg);
      }

      var15 = this.services.iterator();

      while(var15.hasNext()) {
         AnnotationProcessorService service = (AnnotationProcessorService)var15.next();
         service.processAnnotations(beanClass, eg, this);
      }

   }

   public void validate(ClassLoader cl, DescriptorBean rootBean, boolean throwErrors) throws ErrorCollectionException {
      this.validate(cl, rootBean);
      if (throwErrors) {
         this.throwProcessingErrors();
      }

   }

   private void validate(ClassLoader cl, DescriptorBean rootBean) {
      if (!PRODUCTION_MODE) {
         AnnotationValidatorVisitor visitor = new AnnotationValidatorVisitor(cl);
         ((AbstractDescriptorBean)rootBean).accept(visitor);
         ErrorCollectionException errs = visitor.getErrors();
         if (errs != null && errs.size() != 0) {
            if (this.errors == null) {
               this.errors = new ErrorCollectionException();
            }

            this.errors.add(errs);
         }

      }
   }

   protected void processField(Field f, J2eeClientEnvironmentBean eg) {
      if (this.isAnnotationPresent(f, Resource.class)) {
         this.addEnvironmentEntry(f, eg);
      } else if (this.isAnnotationPresent(f, EJB.class)) {
         this.addEjbRef(f, eg);
      } else if (this.isAnnotationPresent(f, EJBReference.class)) {
         this.addEjbRef(f, eg);
      } else if (this.isAnnotationPresent(f, PersistenceUnit.class)) {
         this.addPersistenceUnitRef(f, eg);
      } else if (this.wseeAnnotationProcessor != null && this.isAnnotationPresent(f, WebServiceRef.class)) {
         this.wseeAnnotationProcessor.addWebServiceRef(f, eg, this);
      }

   }

   private void processMethods(List methods, J2eeClientEnvironmentBean eg) {
      this.processLifcycleMethods(methods, eg);
      Iterator var3 = methods.iterator();

      while(var3.hasNext()) {
         Method m = (Method)var3.next();
         this.processMethod(m, eg);
      }

   }

   protected void processMethod(Method m, J2eeClientEnvironmentBean eg) {
      if (this.isAnnotationPresent(m, Resource.class)) {
         this.addEnvironmentEntry(m, eg);
      } else if (this.isAnnotationPresent(m, EJB.class)) {
         this.addEjbRef(m, eg);
      } else if (this.isAnnotationPresent(m, PersistenceUnit.class)) {
         this.addPersistenceUnitRef(m, eg);
      } else if (this.wseeAnnotationProcessor != null && this.isAnnotationPresent(m, WebServiceRef.class)) {
         this.wseeAnnotationProcessor.addWebServiceRef(m, eg, this);
      } else if (this.isAnnotationPresent(m, EJBReference.class)) {
         this.addEjbRef(m, eg);
      }

   }

   private void processLifcycleMethods(List methods, J2eeClientEnvironmentBean eg) {
      List postConstructClasses = new ArrayList();
      List preDestroyClasses = new ArrayList();
      LifecycleCallbackBean[] var5 = eg.getPostConstructs();
      int var6 = var5.length;

      int var7;
      LifecycleCallbackBean dcb;
      for(var7 = 0; var7 < var6; ++var7) {
         dcb = var5[var7];
         postConstructClasses.add(dcb.getLifecycleCallbackClass());
      }

      var5 = eg.getPreDestroys();
      var6 = var5.length;

      for(var7 = 0; var7 < var6; ++var7) {
         dcb = var5[var7];
         preDestroyClasses.add(dcb.getLifecycleCallbackClass());
      }

      Iterator var9 = methods.iterator();

      while(var9.hasNext()) {
         Method method = (Method)var9.next();
         String clazz = method.getDeclaringClass().getName();
         if (this.isAnnotationPresent(method, PostConstruct.class) && !postConstructClasses.contains(clazz)) {
            this.populateLifecyleCallbackBean(eg.createPostConstruct(), method);
         }

         if (this.isAnnotationPresent(method, PreDestroy.class) && !preDestroyClasses.contains(clazz)) {
            this.populateLifecyleCallbackBean(eg.createPreDestroy(), method);
         }
      }

   }

   protected void populateLifecyleCallbackBean(LifecycleCallbackBean lc, Method m) {
      lc.setBeanSource(1);
      lc.setLifecycleCallbackClass(m.getDeclaringClass().getName());
      lc.setLifecycleCallbackMethod(m.getName());
   }

   private void addPersistenceUnitRef(PersistenceUnit pu, J2eeClientEnvironmentBean eg) {
      this.addPersistenceUnitRef(pu.name(), pu, eg, false);
   }

   protected void addPersistenceUnitRef(Field f, J2eeClientEnvironmentBean eg) {
      PersistenceUnit pu = (PersistenceUnit)this.getAnnotation(f, PersistenceUnit.class);
      String name = this.getCompEnvJndiName(pu.name(), f);
      if (this.findInjectionTargetFromPersistenceUnitRef(f, name, eg) == null) {
         this.addInjectionTarget(f, this.addPersistenceUnitRef(name, pu, eg, true));
      }

   }

   protected void addPersistenceUnitRef(Method m, J2eeClientEnvironmentBean eg) {
      PersistenceUnit pu = (PersistenceUnit)this.getAnnotation(m, PersistenceUnit.class);
      String name = this.getCompEnvJndiName(pu.name(), m);
      if (this.findInjectionTargetFromPersistenceUnitRef(m, name, eg) == null) {
         this.addInjectionTarget(m, this.addPersistenceUnitRef(name, pu, eg, true));
      }

   }

   protected InjectionTargetBean addPersistenceUnitRef(String name, PersistenceUnit pu, J2eeClientEnvironmentBean eg, boolean injectable) {
      PersistenceUnitRefBean puRef = null;
      PersistenceUnitRefBean[] var6 = eg.getPersistenceUnitRefs();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         PersistenceUnitRefBean ref = var6[var8];
         if (ref.getPersistenceUnitRefName().equals(name)) {
            puRef = ref;
            break;
         }
      }

      if (puRef == null) {
         puRef = eg.createPersistenceUnitRef();
         puRef.setPersistenceUnitRefName(name);
      }

      if (!this.isSet("PersistenceUnitName", puRef) && pu.unitName().length() > 0) {
         puRef.setPersistenceUnitName(pu.unitName());
      }

      return injectable ? puRef.createInjectionTarget() : null;
   }

   protected InjectionTargetBean findInjectionTargetFromPersistenceUnitRef(Method m, String name, J2eeClientEnvironmentBean eg) {
      return this.findInjectionTargetFromPersistenceUnitRef(m.getDeclaringClass().getName(), this.getPropertyName(m), name, eg);
   }

   protected InjectionTargetBean findInjectionTargetFromPersistenceUnitRef(Field f, String name, J2eeClientEnvironmentBean eg) {
      return this.findInjectionTargetFromPersistenceUnitRef(f.getDeclaringClass().getName(), f.getName(), name, eg);
   }

   protected InjectionTargetBean findInjectionTargetFromPersistenceUnitRef(String targetClass, String targetName, String name, J2eeClientEnvironmentBean eg) {
      PersistenceUnitRefBean puRef = null;
      PersistenceUnitRefBean[] var6 = eg.getPersistenceUnitRefs();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         PersistenceUnitRefBean ref = var6[var8];
         if (ref.getPersistenceUnitRefName().equals(name)) {
            puRef = ref;
            break;
         }
      }

      return puRef != null ? this.findInjectionTargetInArray(targetClass, targetName, puRef.getInjectionTargets()) : null;
   }

   public InjectionTargetBean findInjectionTargetFromServiceRef(Method m, String name, J2eeClientEnvironmentBean eg) {
      return this.findInjectionTargetFromServiceRef(m.getDeclaringClass().getName(), this.getPropertyName(m), name, eg);
   }

   public InjectionTargetBean findInjectionTargetFromServiceRef(Field f, String name, J2eeClientEnvironmentBean eg) {
      return this.findInjectionTargetFromServiceRef(f.getDeclaringClass().getName(), f.getName(), name, eg);
   }

   protected InjectionTargetBean findInjectionTargetFromServiceRef(String targetClass, String targetName, String name, J2eeClientEnvironmentBean eg) {
      ServiceRefBean srb = this.findOrCreateServiceRef(name, eg);
      return srb != null ? this.findInjectionTargetInArray(targetClass, targetName, srb.getInjectionTargets()) : null;
   }

   protected void addEjbRef(EJB ejbRef, J2eeClientEnvironmentBean eg) {
      this.addEjbRef(ejbRef.name(), ejbRef.beanInterface(), ejbRef, eg, false);
   }

   protected void addEjbRef(Field f, J2eeClientEnvironmentBean eg) {
      EJB ejb = (EJB)this.getAnnotation(f, EJB.class);
      if (ejb == null) {
         EJBReference ejbReference = (EJBReference)this.getAnnotation(f, EJBReference.class);
         String name = this.getCompEnvJndiName(ejbReference.name(), f);
         Class type = f.getType();
         if (this.findInjectionTargetFromEjbRef(f, name, type, eg) == null) {
            this.addInjectionTarget(f, this.addEjbRef(name, type, ejbReference, eg, true));
         }
      } else {
         String name = this.getCompEnvJndiName(ejb.name(), f);
         Class type = this.getEnvironmentType(ejb.beanInterface(), f);
         if (this.findInjectionTargetFromEjbRef(f, name, type, eg) == null) {
            this.addInjectionTarget(f, this.addEjbRef(name, type, ejb, eg, true));
         }
      }

   }

   protected void addEjbRef(Method m, J2eeClientEnvironmentBean eg) {
      EJB ejb = (EJB)this.getAnnotation(m, EJB.class);
      if (ejb == null) {
         EJBReference ejbReference = (EJBReference)this.getAnnotation(m, EJBReference.class);
         String name = this.getCompEnvJndiName(ejbReference.name(), m);
         Class type = m.getParameterTypes()[0];
         if (this.findInjectionTargetFromEjbRef(m, name, type, eg) == null) {
            this.addInjectionTarget(m, this.addEjbRef(name, type, ejbReference, eg, true));
         }
      } else {
         String name = this.getCompEnvJndiName(ejb.name(), m);
         Class type = this.getEnvironmentType(ejb.beanInterface(), m);
         if (this.findInjectionTargetFromEjbRef(m, name, type, eg) == null) {
            this.addInjectionTarget(m, this.addEjbRef(name, type, ejb, eg, true));
         }
      }

   }

   protected InjectionTargetBean addEjbref(String name, Class iface, EJBReference ref, J2eeClientEnvironmentBean eg, boolean injectable) {
      EjbRefBean eRef = this.findEjbRef(name, eg);
      if (eRef != null) {
         return this.addEJBRemoteRef(name, iface, ref, eg, eRef, injectable);
      } else if (iface == Object.class) {
         this.addBeanInterfaceNotSetError(eg);
         return null;
      } else {
         return this.addEJBRemoteRef(name, iface, (EJBReference)ref, eg, (EjbRefBean)null, injectable);
      }
   }

   protected InjectionTargetBean addEJBRemoteRef(String name, Class iface, EJBReference ejbRef, J2eeClientEnvironmentBean eg, EjbRefBean eRef, boolean injectable) {
      if (eRef == null) {
         eRef = eg.createEjbRef();
         eRef.setEjbRefName(name);
      }

      if (iface != Object.class) {
         if (EJBHome.class.isAssignableFrom(iface)) {
            if (!this.isSet("Home", eRef)) {
               eRef.setHome(iface.getName());
            }
         } else if (!this.isSet("Remote", eRef)) {
            eRef.setRemote(iface.getName());
         }
      }

      if (!this.isSet("MappedName", eRef) && ejbRef.jndiName().length() > 0) {
         eRef.setMappedName("weblogic-jndi:" + ejbRef.jndiName());
      }

      return injectable ? eRef.createInjectionTarget() : null;
   }

   protected InjectionTargetBean addEjbRef(String name, Class iface, EJBReference ref, J2eeClientEnvironmentBean eg, boolean injectable) {
      EjbRefBean eRef = this.findEjbRef(name, eg);
      if (eRef != null) {
         return this.addEJBRemoteRef(name, iface, ref, eg, eRef, injectable);
      } else if (iface == Object.class) {
         this.addBeanInterfaceNotSetError(eg);
         return null;
      } else {
         return this.addEJBRemoteRef(name, iface, (EJBReference)ref, eg, (EjbRefBean)null, injectable);
      }
   }

   protected InjectionTargetBean addEjbRef(String name, Class iface, EJB ejb, J2eeClientEnvironmentBean eg, boolean injectable) {
      EjbRefBean eRef = this.findEjbRef(name, eg);
      if (eRef != null) {
         return this.addEJBRemoteRef(name, iface, ejb, eg, eRef, injectable);
      } else {
         return EJBHome.class.isAssignableFrom(iface) ? this.addEJBRemoteRef(name, iface, (EJB)ejb, eg, (EjbRefBean)null, injectable) : this.addEJBRemoteRef(name, iface, (EJB)ejb, eg, (EjbRefBean)null, injectable);
      }
   }

   protected InjectionTargetBean findInjectionTargetFromEjbRef(Method m, String name, Class type, J2eeClientEnvironmentBean eg) {
      return this.findInjectionTargetFromEjbRef(m.getDeclaringClass().getName(), this.getPropertyName(m), name, type, eg);
   }

   protected InjectionTargetBean findInjectionTargetFromEjbRef(Field f, String name, Class type, J2eeClientEnvironmentBean eg) {
      return this.findInjectionTargetFromEjbRef(f.getDeclaringClass().getName(), f.getName(), name, type, eg);
   }

   protected InjectionTargetBean findInjectionTargetFromEjbRef(String targetClass, String targetName, String name, Class type, J2eeClientEnvironmentBean eg) {
      EjbRefBean eRef = this.findEjbRef(name, eg);
      return eRef != null ? this.findInjectionTargetInArray(targetClass, targetName, eRef.getInjectionTargets()) : null;
   }

   protected InjectionTargetBean addEJBRemoteRef(String name, Class iface, EJB ejbRef, J2eeClientEnvironmentBean eg, EjbRefBean eRef, boolean injectable) {
      if (eRef == null) {
         eRef = eg.createEjbRef();
         eRef.setEjbRefName(name);
      }

      if (iface != Object.class) {
         if (EJBHome.class.isAssignableFrom(iface)) {
            if (!this.isSet("Home", eRef)) {
               eRef.setHome(iface.getName());
            }
         } else if (!this.isSet("Remote", eRef)) {
            eRef.setRemote(iface.getName());
         }
      }

      if (!this.isSet("EjbLink", eRef) && ejbRef.beanName().length() > 0) {
         eRef.setEjbLink(ejbRef.beanName());
      }

      if (!this.isSet("MappedName", eRef) && ejbRef.mappedName().length() > 0) {
         eRef.setMappedName(ejbRef.mappedName());
      }

      this.setUnsetAttribute(eRef, false, "LookupName", ejbRef.lookup());
      return injectable ? eRef.createInjectionTarget() : null;
   }

   protected EjbRefBean findEjbRef(String name, J2eeClientEnvironmentBean eg) {
      EjbRefBean[] var3 = eg.getEjbRefs();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         EjbRefBean eRef = var3[var5];
         if (name.equals(eRef.getEjbRefName())) {
            return eRef;
         }
      }

      return null;
   }

   protected void addEnvironmentEntry(Resource resource, J2eeClientEnvironmentBean eg) {
      this.addEnvironmentEntry(resource.name(), resource.type(), resource, eg, false);
   }

   protected void addEnvironmentEntry(Field f, J2eeClientEnvironmentBean eg) {
      Resource resource = (Resource)this.getAnnotation(f, Resource.class);
      String name = this.getCompEnvJndiName(resource.name(), f);
      Class type = this.getEnvironmentType(resource.type(), f);
      if (this.findInjectionTargetFromEnvironmentEntry(f, name, type, resource, eg) == null) {
         this.addInjectionTarget(f, this.addEnvironmentEntry(name, type, resource, eg, true));
      }

   }

   protected void addEnvironmentEntry(Method m, J2eeClientEnvironmentBean eg) {
      Resource resource = (Resource)this.getAnnotation(m, Resource.class);
      String name = this.getCompEnvJndiName(resource.name(), m);
      Class type = this.getEnvironmentType(resource.type(), m);
      if (this.findInjectionTargetFromEnvironmentEntry(m, name, type, resource, eg) == null) {
         this.addInjectionTarget(m, this.addEnvironmentEntry(name, type, resource, eg, true));
      }

   }

   protected String getPropertyName(Method m) {
      String name = m.getName();
      char start = name.charAt(3);
      return ("" + start).toLowerCase() + name.substring(4);
   }

   protected EnvEntryBean findOrCreateEnvEntry(String name, J2eeClientEnvironmentBean eg) {
      EnvEntryBean[] var3 = eg.getEnvEntries();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         EnvEntryBean env = var3[var5];
         if (name.equals(env.getEnvEntryName())) {
            return env;
         }
      }

      EnvEntryBean env = eg.createEnvEntry();
      env.setEnvEntryName(name);
      return env;
   }

   public ServiceRefBean findOrCreateServiceRef(String name, J2eeClientEnvironmentBean eg) {
      ServiceRefBean[] var3 = eg.getServiceRefs();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ServiceRefBean env = var3[var5];
         if (name.equals(env.getServiceRefName())) {
            return env;
         }
      }

      ServiceRefBean env = eg.createServiceRef();
      env.setServiceRefName(name);
      return env;
   }

   protected ResourceRefBean findOrCreateResourceRef(String name, J2eeClientEnvironmentBean eg) {
      ResourceRefBean[] var3 = eg.getResourceRefs();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ResourceRefBean env = var3[var5];
         if (name.equals(env.getResRefName())) {
            return env;
         }
      }

      ResourceRefBean env = eg.createResourceRef();
      env.setResRefName(name);
      return env;
   }

   protected MessageDestinationRefBean findOrCreateMessageDestinationRef(String name, J2eeClientEnvironmentBean eg) {
      MessageDestinationRefBean[] var3 = eg.getMessageDestinationRefs();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         MessageDestinationRefBean env = var3[var5];
         if (name.equals(env.getMessageDestinationRefName())) {
            return env;
         }
      }

      MessageDestinationRefBean env = eg.createMessageDestinationRef();
      env.setMessageDestinationRefName(name);
      return env;
   }

   protected ResourceEnvRefBean findOrCreateResourceEnvRef(String name, J2eeClientEnvironmentBean eg) {
      ResourceEnvRefBean[] var3 = eg.getResourceEnvRefs();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ResourceEnvRefBean env = var3[var5];
         if (name.equals(env.getResourceEnvRefName())) {
            return env;
         }
      }

      ResourceEnvRefBean env = eg.createResourceEnvRef();
      env.setResourceEnvRefName(name);
      return env;
   }

   private String transformPrimitiveType(Class type) {
      if (type == Boolean.TYPE) {
         return Boolean.class.getName();
      } else if (type == Integer.TYPE) {
         return Integer.class.getName();
      } else if (type == Float.TYPE) {
         return Float.class.getName();
      } else if (type == Short.TYPE) {
         return Short.class.getName();
      } else if (type == Character.TYPE) {
         return Character.class.getName();
      } else if (type == Byte.TYPE) {
         return Byte.class.getName();
      } else if (type == Long.TYPE) {
         return Long.class.getName();
      } else {
         return type == Double.TYPE ? Double.class.getName() : type.getName();
      }
   }

   private String getOriginalOrInjectionExtensionName(String origName, Class type, Resource resource) {
      InjectionExtension extension = ExtensionManager.instance.getFirstMatchingExtension(type.getName(), resource.name());
      if (extension == null) {
         return origName;
      } else {
         return resource.name().length() == 0 ? extension.getName(type.getName()) : extension.getName(type.getName(), origName);
      }
   }

   private InjectionTargetBean processResourceEnvRef(ResourceEnvRefBean entry, String name, Class type, Resource resource, boolean injectable) {
      InjectionExtension extension = ExtensionManager.instance.getFirstMatchingExtension(type.getName(), resource.name());
      if (extension != null) {
         if (resource.name().length() == 0) {
            name = extension.getName(type.getName());
         } else {
            name = extension.getName(type.getName(), name);
         }

         entry.setResourceEnvRefName(name);
         if (!this.isSet("ResourceEnvRefType", entry)) {
            entry.setResourceEnvRefType(type.getName());
         }

         if (!this.isSet("MappedName", entry)) {
            entry.setMappedName(name);
         }
      } else {
         if (!this.isSet("ResourceEnvRefType", entry)) {
            entry.setResourceEnvRefType(type.getName());
         }

         if (resource.mappedName().length() > 0 && !this.isSet("MappedName", entry)) {
            entry.setMappedName(resource.mappedName());
         }

         this.setUnsetAttribute(entry, false, "LookupName", resource.lookup());
      }

      return injectable ? entry.createInjectionTarget() : null;
   }

   private InjectionTargetBean processEnvEntry(EnvEntryBean env, String name, Class type, Resource resource, boolean injectable) {
      if (!this.isSet("EnvEntryType", env)) {
         if (type.isPrimitive()) {
            env.setEnvEntryType(this.transformPrimitiveType(type));
         } else {
            env.setEnvEntryType(type.getName());
         }
      }

      if (resource.mappedName().length() > 0 && !this.isSet("MappedName", env)) {
         env.setMappedName(resource.mappedName());
      }

      this.setUnsetAttribute(env, false, "LookupName", resource.lookup());
      return injectable ? env.createInjectionTarget() : null;
   }

   private InjectionTargetBean processMessageDestinationRef(MessageDestinationRefBean mdr, String name, Class type, Resource resource, boolean injectable) {
      if (!this.isSet("MessageDestinationType", mdr)) {
         mdr.setMessageDestinationType(type.getName());
      }

      if (resource.mappedName().length() > 0 && !this.isSet("MappedName", mdr)) {
         mdr.setMappedName(resource.mappedName());
      }

      this.setUnsetAttribute(mdr, false, "LookupName", resource.lookup());
      return injectable ? mdr.createInjectionTarget() : null;
   }

   private InjectionTargetBean processServiceRef(ServiceRefBean srb, String name, Class type, Resource resource, boolean injectable) {
      if (!this.isSet("ServiceRefType", srb)) {
         srb.setServiceRefType(type.getName());
      }

      if (resource.mappedName().length() > 0 && !this.isSet("MappedName", srb)) {
         srb.setMappedName(resource.mappedName());
      }

      return injectable ? srb.createInjectionTarget() : null;
   }

   private InjectionTargetBean processResourceRef(ResourceRefBean rrb, String name, Class type, Resource resource, boolean injectable) {
      if (!this.isSet("ResType", rrb)) {
         rrb.setResType(type.getName());
      }

      if (!this.isSet("ResAuth", rrb)) {
         if (resource.authenticationType() != AuthenticationType.CONTAINER) {
            rrb.setResAuth("Application");
         } else {
            rrb.setResAuth("Container");
         }
      }

      if (!this.isSet("ResSharingScope", rrb) && !resource.shareable()) {
         rrb.setResSharingScope("Unshareable");
      }

      if (resource.mappedName().length() > 0 && !this.isSet("MappedName", rrb)) {
         rrb.setMappedName(resource.mappedName());
      }

      this.setUnsetAttribute(rrb, false, "LookupName", resource.lookup());
      if (ManagedBeanUtils.isManagedBean(type) && !this.isSet("MappedName", rrb) && !this.isSet("LookupName", rrb)) {
         rrb.setLookupName(ManagedBeanUtils.calculateJavaModuleLookupName(type));
      }

      return injectable ? rrb.createInjectionTarget() : null;
   }

   protected InjectionTargetBean addEnvironmentEntry(String name, Class type, Resource resource, J2eeClientEnvironmentBean eg, boolean injectable) {
      if (type == DataSource.class) {
         this.configureDefaultDatasource(name, resource, eg);
      }

      Map entries = (Map)this.envs.get(eg);
      if (entries == null) {
         entries = new HashMap();
         EnvEntryBean[] var7 = eg.getEnvEntries();
         int var8 = var7.length;

         int var9;
         for(var9 = 0; var9 < var8; ++var9) {
            EnvEntryBean envEntry = var7[var9];
            ((Map)entries).put(envEntry.getEnvEntryName(), envEntry);
         }

         ServiceRefBean[] var11 = eg.getServiceRefs();
         var8 = var11.length;

         for(var9 = 0; var9 < var8; ++var9) {
            ServiceRefBean serviceRef = var11[var9];
            ((Map)entries).put(serviceRef.getServiceRefName(), serviceRef);
         }

         ResourceRefBean[] var12 = eg.getResourceRefs();
         var8 = var12.length;

         for(var9 = 0; var9 < var8; ++var9) {
            ResourceRefBean resourceRef = var12[var9];
            ((Map)entries).put(resourceRef.getResRefName(), resourceRef);
         }

         MessageDestinationRefBean[] var13 = eg.getMessageDestinationRefs();
         var8 = var13.length;

         for(var9 = 0; var9 < var8; ++var9) {
            MessageDestinationRefBean messageDestRef = var13[var9];
            ((Map)entries).put(messageDestRef.getMessageDestinationRefName(), messageDestRef);
         }

         ResourceEnvRefBean[] var14 = eg.getResourceEnvRefs();
         var8 = var14.length;

         for(var9 = 0; var9 < var8; ++var9) {
            ResourceEnvRefBean resourceEnvRef = var14[var9];
            ((Map)entries).put(resourceEnvRef.getResourceEnvRefName(), resourceEnvRef);
         }

         this.envs.put(eg, entries);
      }

      Object existing = ((Map)entries).get(name);
      if (existing != null) {
         if (existing instanceof ResourceRefBean) {
            return this.processResourceRef((ResourceRefBean)existing, name, type, resource, injectable);
         } else if (existing instanceof ServiceRefBean) {
            return this.processServiceRef((ServiceRefBean)existing, name, type, resource, injectable);
         } else if (existing instanceof MessageDestinationRefBean) {
            return this.processMessageDestinationRef((MessageDestinationRefBean)existing, name, type, resource, injectable);
         } else {
            return existing instanceof EnvEntryBean ? this.processEnvEntry((EnvEntryBean)existing, name, type, resource, injectable) : this.processResourceEnvRef((ResourceEnvRefBean)existing, name, type, resource, injectable);
         }
      } else {
         if (resource.type() == Object.class && !injectable) {
            this.addProcessingError("A class level Resource annotation with the name " + name + " does not have the type attribute set");
         }

         if (!type.isPrimitive() && !type.isEnum() && type != String.class && type != Character.class && type != Integer.class && type != Boolean.class && type != Double.class && type != Byte.class && type != Short.class && type != Long.class && type != Float.class && type != Class.class) {
            if (this.wseeAnnotationProcessor != null && this.wseeAnnotationProcessor.isSupportedType(type)) {
               ServiceRefBean srb = this.findOrCreateServiceRef(name, eg);
               return this.processServiceRef(srb, name, type, resource, injectable);
            } else {
               String typeName = type.getName();
               if (type != DataSource.class && type != ConnectionFactory.class && type != QueueConnectionFactory.class && type != TopicConnectionFactory.class && !typeName.equals("javax.mail.Session") && type != URL.class && type != javax.resource.cci.ConnectionFactory.class && type != ORB.class && type != org.omg.CORBA.ORB.class && type != WorkManager.class && type != TimerManager.class && !JCAConnectionFactoryProvider.isAdapterConnectionFactoryClass(type.getName()) && !typeName.equals("com.tangosol.net.NamedCache") && !typeName.equals("com.tangosol.net.Service") && !ManagedBeanUtils.isManagedBean(type)) {
                  if (type != Queue.class && type != Topic.class) {
                     ResourceEnvRefBean rer = this.findOrCreateResourceEnvRef(this.getOriginalOrInjectionExtensionName(name, type, resource), eg);
                     return this.processResourceEnvRef(rer, name, type, resource, injectable);
                  } else {
                     MessageDestinationRefBean mdr = this.findOrCreateMessageDestinationRef(name, eg);
                     return this.processMessageDestinationRef(mdr, name, type, resource, injectable);
                  }
               } else {
                  ResourceRefBean rrb = this.findOrCreateResourceRef(name, eg);
                  return this.processResourceRef(rrb, name, type, resource, injectable);
               }
            }
         } else {
            EnvEntryBean env = this.findOrCreateEnvEntry(name, eg);
            return this.processEnvEntry(env, name, type, resource, injectable);
         }
      }
   }

   protected InjectionTargetBean findInjectionTargetFromEnvironmentEntry(Method m, String name, Class type, Resource resource, J2eeClientEnvironmentBean eg) {
      return this.findInjectionTargetFromEnvironmentEntry(m.getDeclaringClass().getName(), this.getPropertyName(m), name, type, resource, eg);
   }

   protected InjectionTargetBean findInjectionTargetFromEnvironmentEntry(Field f, String name, Class type, Resource resource, J2eeClientEnvironmentBean eg) {
      return this.findInjectionTargetFromEnvironmentEntry(f.getDeclaringClass().getName(), f.getName(), name, type, resource, eg);
   }

   protected InjectionTargetBean findInjectionTargetFromEnvironmentEntry(String targetClass, String targetName, String name, Class type, Resource resource, J2eeClientEnvironmentBean eg) {
      if (!type.isPrimitive() && !type.isEnum() && type != String.class && type != Character.class && type != Integer.class && type != Boolean.class && type != Double.class && type != Byte.class && type != Short.class && type != Long.class && type != Float.class && type != Class.class) {
         if (this.wseeAnnotationProcessor != null && this.wseeAnnotationProcessor.isSupportedType(type)) {
            this.findOrCreateServiceRef(name, eg);
         }

         String typeName = type.getName();
         if (type != DataSource.class && type != ConnectionFactory.class && type != QueueConnectionFactory.class && type != TopicConnectionFactory.class && !typeName.equals("javax.mail.Session") && type != URL.class && type != javax.resource.cci.ConnectionFactory.class && type != ORB.class && type != org.omg.CORBA.ORB.class && type != WorkManager.class && type != TimerManager.class && !JCAConnectionFactoryProvider.isAdapterConnectionFactoryClass(type.getName()) && !typeName.equals("com.tangosol.net.NamedCache") && !typeName.equals("com.tangosol.net.Service") && !ManagedBeanUtils.isManagedBean(type)) {
            if (type != Queue.class && type != Topic.class) {
               InjectionExtension extension = ExtensionManager.instance.getFirstMatchingExtension(type.getName(), resource.name());
               ResourceEnvRefBean rer;
               if (extension != null) {
                  if (resource.name().length() == 0) {
                     name = extension.getName(type.getName());
                  } else {
                     name = extension.getName(type.getName(), name);
                  }

                  rer = this.findOrCreateResourceEnvRef(name, eg);
                  return rer != null ? this.findInjectionTargetInArray(targetClass, targetName, rer.getInjectionTargets()) : null;
               } else {
                  rer = this.findOrCreateResourceEnvRef(name, eg);
                  return rer != null ? this.findInjectionTargetInArray(targetClass, targetName, rer.getInjectionTargets()) : null;
               }
            } else {
               MessageDestinationRefBean mdr = this.findOrCreateMessageDestinationRef(name, eg);
               return mdr != null ? this.findInjectionTargetInArray(targetClass, targetName, mdr.getInjectionTargets()) : null;
            }
         } else {
            ResourceRefBean rrb = this.findOrCreateResourceRef(name, eg);
            return rrb != null ? this.findInjectionTargetInArray(targetClass, targetName, rrb.getInjectionTargets()) : null;
         }
      } else {
         EnvEntryBean env = this.findOrCreateEnvEntry(name, eg);
         return env != null ? this.findInjectionTargetInArray(targetClass, targetName, env.getInjectionTargets()) : null;
      }
   }

   protected List getFields(Class bean) {
      return this.getFields(bean, new ArrayList());
   }

   protected List getFields(Class bean, Collection overrides) {
      List fieldSet = new ArrayList();
      Field[] var4 = bean.getDeclaredFields();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Field field = var4[var6];
         fieldSet.add(field);
      }

      Class superClass = bean.getSuperclass();
      if (!superClass.equals(Object.class)) {
         fieldSet.addAll(this.getFields(superClass, overrides));
      }

      return fieldSet;
   }

   protected List getMethods(Class bean) {
      List methodList = (List)this.methodsCache.get(bean);
      if (methodList == null) {
         methodList = this.getMethods(bean, new HashSet());
         Collections.reverse(methodList);
         this.methodsCache.put(bean, methodList);
      }

      return methodList;
   }

   protected List getMethods(Class bean, Set overrides) {
      List methodList = new ArrayList();
      Method[] var4 = bean.getDeclaredMethods();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Method method = var4[var6];
         int modifiers = method.getModifiers();
         if (Modifier.isPrivate(modifiers)) {
            methodList.add(method);
         } else {
            StringBuffer signature = (new StringBuffer(method.getName())).append("(");
            Class[] var10 = method.getParameterTypes();
            int var11 = var10.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               Class param = var10[var12];
               signature.append(param.getName()).append(", ");
            }

            if (!overrides.contains(signature.toString())) {
               methodList.add(method);
               overrides.add(signature.toString());
            }
         }
      }

      Class superClass = bean.getSuperclass();
      if (!superClass.equals(Object.class)) {
         methodList.addAll(this.getMethods(superClass, overrides));
      }

      return methodList;
   }

   protected Map getClassResources(Class bean) {
      Map resourceSet = null;
      Class superClass = bean.getSuperclass();
      if (superClass.equals(Object.class)) {
         resourceSet = new HashMap();
      } else {
         resourceSet = this.getClassResources(superClass);
      }

      if (this.isAnnotationPresent(bean, Resources.class)) {
         Resources resources = (Resources)this.getAnnotation(bean, Resources.class);
         Resource[] var5 = resources.value();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Resource resource = var5[var7];
            this.validateClassResource(resource, bean);
            ((Map)resourceSet).put(resource.name(), resource);
         }
      }

      if (this.isAnnotationPresent(bean, Resource.class)) {
         Resource resource = (Resource)this.getAnnotation(bean, Resource.class);
         this.validateClassResource(resource, bean);
         ((Map)resourceSet).put(resource.name(), resource);
      }

      return (Map)resourceSet;
   }

   protected Map getClassEJBRefs(Class bean) {
      Map resourceSet = null;
      Class superClass = bean.getSuperclass();
      if (superClass.equals(Object.class)) {
         resourceSet = new HashMap();
      } else {
         resourceSet = this.getClassEJBRefs(superClass);
      }

      if (this.isAnnotationPresent(bean, EJBs.class)) {
         EJBs ejbs = (EJBs)this.getAnnotation(bean, EJBs.class);
         EJB[] var5 = ejbs.value();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            EJB ejb = var5[var7];
            this.validateClassResource(ejb, bean);
            ((Map)resourceSet).put(ejb.name(), ejb);
         }
      }

      if (this.isAnnotationPresent(bean, EJB.class)) {
         EJB ejb = (EJB)this.getAnnotation(bean, EJB.class);
         this.validateClassResource(ejb, bean);
         ((Map)resourceSet).put(ejb.name(), ejb);
      }

      return (Map)resourceSet;
   }

   protected Map getClassPersistenceUnitRefs(Class bean) {
      Map resourceSet = null;
      Class superClass = bean.getSuperclass();
      if (superClass.equals(Object.class)) {
         resourceSet = new HashMap();
      } else {
         resourceSet = this.getClassPersistenceUnitRefs(superClass);
      }

      if (this.isAnnotationPresent(bean, PersistenceUnits.class)) {
         PersistenceUnits pus = (PersistenceUnits)this.getAnnotation(bean, PersistenceUnits.class);
         PersistenceUnit[] var5 = pus.value();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            PersistenceUnit pu = var5[var7];
            this.validateClassResource(pu, bean);
            ((Map)resourceSet).put(pu.name(), pu);
         }
      }

      if (this.isAnnotationPresent(bean, PersistenceUnit.class)) {
         PersistenceUnit pu = (PersistenceUnit)this.getAnnotation(bean, PersistenceUnit.class);
         this.validateClassResource(pu, bean);
         ((Map)resourceSet).put(pu.name(), pu);
      }

      return (Map)resourceSet;
   }

   protected Set getClassPersistenceContextRefs(Class bean) {
      Set resourceSet = null;
      Class superClass = bean.getSuperclass();
      if (superClass.equals(Object.class)) {
         resourceSet = new HashSet();
      } else {
         resourceSet = this.getClassPersistenceContextRefs(superClass);
      }

      if (this.isAnnotationPresent(bean, PersistenceContexts.class)) {
         PersistenceContexts pcs = (PersistenceContexts)this.getAnnotation(bean, PersistenceContexts.class);
         PersistenceContext[] var5 = pcs.value();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            PersistenceContext pc = var5[var7];
            this.validateClassResource(pc, bean);
            ((Set)resourceSet).add(pc);
         }
      }

      if (this.isAnnotationPresent(bean, PersistenceContext.class)) {
         PersistenceContext pc = (PersistenceContext)this.getAnnotation(bean, PersistenceContext.class);
         this.validateClassResource(pc, bean);
         ((Set)resourceSet).add(pc);
      }

      return (Set)resourceSet;
   }

   Set getDataSourceDefinitions(Class beanClass) {
      Set set = null;
      Class superClass = beanClass.getSuperclass();
      if (superClass.equals(Object.class)) {
         set = new HashSet();
      } else {
         set = this.getDataSourceDefinitions(superClass);
      }

      if (this.isAnnotationPresent(beanClass, DataSourceDefinitions.class)) {
         DataSourceDefinitions defs = (DataSourceDefinitions)this.getAnnotation(beanClass, DataSourceDefinitions.class);
         DataSourceDefinition[] var5 = defs.value();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            DataSourceDefinition def = var5[var7];
            this.validateAnnotation(def, beanClass);
            ((Set)set).add(def);
         }
      }

      if (this.isAnnotationPresent(beanClass, DataSourceDefinition.class)) {
         DataSourceDefinition def = (DataSourceDefinition)this.getAnnotation(beanClass, DataSourceDefinition.class);
         this.validateAnnotation(def, beanClass);
         ((Set)set).add(def);
      }

      return (Set)set;
   }

   Set getMailSessionDefinitions(Class beanClass) {
      Set set = null;
      Class superClass = beanClass.getSuperclass();
      if (superClass.equals(Object.class)) {
         set = new HashSet();
      } else {
         set = this.getMailSessionDefinitions(superClass);
      }

      if (this.isAnnotationPresent(beanClass, MailSessionDefinitions.class)) {
         MailSessionDefinitions defs = (MailSessionDefinitions)this.getAnnotation(beanClass, MailSessionDefinitions.class);
         MailSessionDefinition[] var5 = defs.value();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            MailSessionDefinition def = var5[var7];
            this.validateAnnotation(def, beanClass);
            ((Set)set).add(def);
         }
      }

      if (this.isAnnotationPresent(beanClass, MailSessionDefinition.class)) {
         MailSessionDefinition def = (MailSessionDefinition)this.getAnnotation(beanClass, MailSessionDefinition.class);
         this.validateAnnotation(def, beanClass);
         ((Set)set).add(def);
      }

      return (Set)set;
   }

   Set getConnectionFactoryDefinitions(Class beanClass) {
      Set set = null;
      Class superClass = beanClass.getSuperclass();
      if (superClass.equals(Object.class)) {
         set = new HashSet();
      } else {
         set = this.getConnectionFactoryDefinitions(superClass);
      }

      if (this.isAnnotationPresent(beanClass, ConnectionFactoryDefinitions.class)) {
         ConnectionFactoryDefinitions defs = (ConnectionFactoryDefinitions)this.getAnnotation(beanClass, ConnectionFactoryDefinitions.class);
         ConnectionFactoryDefinition[] var5 = defs.value();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            ConnectionFactoryDefinition def = var5[var7];
            this.validateAnnotation(def, beanClass);
            ((Set)set).add(def);
         }
      }

      if (this.isAnnotationPresent(beanClass, ConnectionFactoryDefinition.class)) {
         ConnectionFactoryDefinition def = (ConnectionFactoryDefinition)this.getAnnotation(beanClass, ConnectionFactoryDefinition.class);
         this.validateAnnotation(def, beanClass);
         ((Set)set).add(def);
      }

      return (Set)set;
   }

   Set getAdministeredObjectDefinitions(Class beanClass) {
      Set set = null;
      Class superClass = beanClass.getSuperclass();
      if (superClass.equals(Object.class)) {
         set = new HashSet();
      } else {
         set = this.getAdministeredObjectDefinitions(superClass);
      }

      if (this.isAnnotationPresent(beanClass, AdministeredObjectDefinitions.class)) {
         AdministeredObjectDefinitions defs = (AdministeredObjectDefinitions)this.getAnnotation(beanClass, AdministeredObjectDefinitions.class);
         AdministeredObjectDefinition[] var5 = defs.value();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            AdministeredObjectDefinition def = var5[var7];
            this.validateAnnotation(def, beanClass);
            ((Set)set).add(def);
         }
      }

      if (this.isAnnotationPresent(beanClass, AdministeredObjectDefinition.class)) {
         AdministeredObjectDefinition def = (AdministeredObjectDefinition)this.getAnnotation(beanClass, AdministeredObjectDefinition.class);
         this.validateAnnotation(def, beanClass);
         ((Set)set).add(def);
      }

      return (Set)set;
   }

   protected void validateAnnotation(DataSourceDefinition def, Class clz) {
      if (def.name().length() == 0) {
         this.addProcessingError("A DataSourceDefinition on class, " + clz.getName() + ", does not have the name attribute set.");
      }

   }

   protected void validateAnnotation(MailSessionDefinition def, Class clz) {
      if (def.name().length() == 0) {
         this.addProcessingError("A MailSessionDefinition on class, " + clz.getName() + ", does not have the name attribute set.");
      }

   }

   protected void validateAnnotation(ConnectionFactoryDefinition def, Class clz) {
      if (def.interfaceName().length() == 0) {
         this.addProcessingError("A ConnectionFactoryDefinition on class, " + clz.getName() + ", does not have the interfaceName attribute set.");
      }

      if (def.name().length() == 0) {
         this.addProcessingError("A ConnectionFactoryDefinition on class, " + clz.getName() + ", does not have the name attribute set.");
      }

      if (def.resourceAdapter().length() == 0) {
         this.addProcessingError("A ConnectionFactoryDefinition on class, " + clz.getName() + ", does not have the resourceAdapter attribute set.");
      }

   }

   protected void validateAnnotation(AdministeredObjectDefinition def, Class clz) {
      if (def.className().length() == 0) {
         this.addProcessingError("A AdministeredObjectDefinition on class, " + clz.getName() + ", does not have the className attribute set.");
      }

      if (def.name().length() == 0) {
         this.addProcessingError("A AdministeredObjectDefinition on class, " + clz.getName() + ", does not have the name attribute set.");
      }

      if (def.resourceAdapter().length() == 0) {
         this.addProcessingError("A AdministeredObjectDefinition on class, " + clz.getName() + ", does not have the resourceAdapter attribute set.");
      }

   }

   protected void validateClassResource(PersistenceContext resource, Class bean) {
      if (resource.name().length() == 0) {
         this.addProcessingError("A class level PersistenceContext annotation on class " + bean.getName() + " does not have the name attribute set.");
      }

   }

   protected void validateClassResource(PersistenceUnit resource, Class bean) {
      if (resource.name().length() == 0) {
         this.addProcessingError("A class level PersistenceUnit annotation on class " + bean.getName() + " does not have the name attribute set.");
      }

   }

   protected void validateClassResource(Resource resource, Class bean) {
      if (resource.name().length() == 0) {
         this.addProcessingError("A class level Resource annotation on class " + bean.getName() + " does not have the name attribute set.");
      }

   }

   protected void validateClassResource(EJB resource, Class bean) {
      if (resource.name().length() == 0) {
         this.addProcessingError("A class level EJB annotation on class " + bean.getName() + " does not have the name attribute set.");
      }

   }

   public String getCompEnvJndiName(String name, Field f) {
      if (name.length() == 0) {
         name = f.getDeclaringClass().getName() + "/" + f.getName();
      }

      return name;
   }

   public String getCompEnvJndiName(String name, Method m) {
      if (name.length() == 0) {
         name = m.getDeclaringClass().getName() + "/" + this.getPropertyName(m);
      }

      return name;
   }

   public Class getEnvironmentType(Class type, Field f) {
      return type != Object.class ? type : f.getType();
   }

   public Class getEnvironmentType(Class type, Method m) {
      return type != Object.class ? type : m.getParameterTypes()[0];
   }

   public void addInjectionTarget(Field f, InjectionTargetBean it) {
      if (f != null && it != null) {
         it.setInjectionTargetClass(f.getDeclaringClass().getName());
         it.setInjectionTargetName(f.getName());
      }
   }

   public void addInjectionTarget(Method m, InjectionTargetBean it) {
      if (m != null && it != null) {
         it.setInjectionTargetClass(m.getDeclaringClass().getName());
         it.setInjectionTargetName(this.getPropertyName(m));
      }
   }

   protected InjectionTargetBean findInjectionTargetInArray(String targetClass, String targetName, InjectionTargetBean[] its) {
      if (its == null) {
         return null;
      } else {
         InjectionTargetBean[] var4 = its;
         int var5 = its.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            InjectionTargetBean it = var4[var6];
            if (targetClass.equals(it.getInjectionTargetClass()) && targetName.equals(it.getInjectionTargetName())) {
               return it;
            }
         }

         return null;
      }
   }

   protected void log(String s) {
      System.out.println(s);
   }

   protected boolean isSet(String attribute, Object obj) {
      return ((DescriptorBean)obj).isSet(attribute);
   }

   protected void setUnsetAttribute(Object bean, boolean isNotInDescriptor, String name, Object value) {
      if (value != null) {
         if (value instanceof String && ((String)value).length() == 0) {
            return;
         }

         if (isNotInDescriptor || !((DescriptorBean)bean).isSet(name)) {
            this.setAttribute(bean, name, value, value.getClass());
         }
      }

   }

   public void setAttribute(Object bean, String name, Object value, Class clz) {
      try {
         Method m = bean.getClass().getMethod("set" + name, clz);
         m.invoke(bean, value);
      } catch (IllegalAccessException var13) {
         throw new AssertionError(var13);
      } catch (InvocationTargetException var14) {
         throw new AssertionError(var14.getTargetException());
      } catch (NoSuchMethodException var15) {
         Class primitiveClass = (Class)toPrimitive.get(clz);
         if (primitiveClass != null) {
            this.setAttribute(bean, name, value, primitiveClass);
         } else {
            Class evolvedClass = (Class)toEvolved.get(clz);
            if (evolvedClass == null) {
               StringBuilder sb = new StringBuilder();
               Method[] var9 = bean.getClass().getMethods();
               int var10 = var9.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  Method m = var9[var11];
                  sb.append(m).append("\n");
               }

               throw new IllegalArgumentException(name + " is a not a recognized property on " + bean + ".\nMethods: " + sb);
            }

            this.setAttribute(bean, name, value, evolvedClass);
         }
      }

   }

   public void addProcessingError(String message) {
      if (this.errors == null) {
         this.errors = new ErrorCollectionException();
      }

      AnnotationProcessException ape = new AnnotationProcessException(message);
      this.errors.add(ape);
   }

   protected void addFatalProcessingError(String message) throws ErrorCollectionException {
      this.addProcessingError(message);
      this.throwProcessingErrors();
   }

   protected void throwProcessingErrors() throws ErrorCollectionException {
      if (this.errors != null && !this.errors.isEmpty()) {
         throw this.errors;
      }
   }

   protected void addBeanInterfaceNotSetError(J2eeClientEnvironmentBean eg) {
      this.addProcessingError("@EJB annotation doesn't have beanInterface set");
   }

   protected void addDescriptorDefaults(Class beanClass, J2eeClientEnvironmentBean eg) {
      this.addLifecycleCallbackDefaults(eg.getPostConstructs(), beanClass.getName());
      this.addLifecycleCallbackDefaults(eg.getPreDestroys(), beanClass.getName());
   }

   protected void addLifecycleCallbackDefaults(LifecycleCallbackBean[] lcbs, String beanClassName) {
      LifecycleCallbackBean[] var3 = lcbs;
      int var4 = lcbs.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         LifecycleCallbackBean lcb = var3[var5];
         if (lcb.getLifecycleCallbackClass() == null) {
            lcb.setLifecycleCallbackClass(beanClassName);
         }
      }

   }

   protected void addDataSource(DataSourceDefinition ds, J2eeClientEnvironmentBean eg) {
      String name = ds.name();
      DataSourceBean dsBean = null;
      DataSourceBean[] var5 = eg.getDataSources();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         DataSourceBean bean = var5[var7];
         if (bean.getName().equals(name)) {
            dsBean = bean;
            break;
         }
      }

      boolean isNotInDescriptor = dsBean == null;
      if (isNotInDescriptor) {
         dsBean = eg.createDataSource();
         dsBean.setName(name);
      }

      this.setUnsetAttribute(dsBean, isNotInDescriptor, "ClassName", ds.className());
      this.setUnsetAttribute(dsBean, isNotInDescriptor, "DatabaseName", ds.databaseName());
      this.setUnsetAttribute(dsBean, isNotInDescriptor, "Description", ds.description());
      this.setUnsetAttribute(dsBean, isNotInDescriptor, "InitialPoolSize", ds.initialPoolSize());
      this.setUnsetAttribute(dsBean, isNotInDescriptor, "IsolationLevel", this.toIsolationLevelString(ds.isolationLevel()));
      this.setUnsetAttribute(dsBean, isNotInDescriptor, "LoginTimeout", ds.loginTimeout());
      this.setUnsetAttribute(dsBean, isNotInDescriptor, "MaxIdleTime", ds.maxIdleTime());
      this.setUnsetAttribute(dsBean, isNotInDescriptor, "MaxPoolSize", ds.maxPoolSize());
      this.setUnsetAttribute(dsBean, isNotInDescriptor, "MaxStatements", ds.maxStatements());
      this.setUnsetAttribute(dsBean, isNotInDescriptor, "MinPoolSize", ds.minPoolSize());
      this.setUnsetAttribute(dsBean, isNotInDescriptor, "Password", ds.password());
      this.setUnsetAttribute(dsBean, isNotInDescriptor, "PortNumber", ds.portNumber());
      this.setUnsetAttribute(dsBean, isNotInDescriptor, "ServerName", ds.serverName());
      this.setUnsetAttribute(dsBean, isNotInDescriptor, "Transactional", ds.transactional());
      this.setUnsetAttribute(dsBean, isNotInDescriptor, "Url", ds.url());
      this.setUnsetAttribute(dsBean, isNotInDescriptor, "User", ds.user());
      this.setUnsetProperties(dsBean, isNotInDescriptor, ds.properties());
   }

   protected void addMailSessionDefinition(MailSessionDefinition msd, J2eeClientEnvironmentBean eg) {
      String name = msd.name();
      MailSessionBean msBean = null;
      MailSessionBean[] var5 = eg.getMailSessions();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         MailSessionBean bean = var5[var7];
         if (bean.getName().equals(name)) {
            msBean = bean;
            break;
         }
      }

      boolean isNotInDescriptor = msBean == null;
      if (isNotInDescriptor) {
         msBean = eg.createMailSession();
         msBean.setName(name);
      }

      this.setUnsetAttribute(msBean, isNotInDescriptor, "Description", msd.description());
      this.setUnsetAttribute(msBean, isNotInDescriptor, "Name", msd.name());
      this.setUnsetAttribute(msBean, isNotInDescriptor, "StoreProtocol", msd.storeProtocol());
      this.setUnsetAttribute(msBean, isNotInDescriptor, "TransportProtocol", msd.transportProtocol());
      this.setUnsetAttribute(msBean, isNotInDescriptor, "Host", msd.host());
      this.setUnsetAttribute(msBean, isNotInDescriptor, "User", msd.user());
      this.setUnsetAttribute(msBean, isNotInDescriptor, "Password", msd.password());
      this.setUnsetAttribute(msBean, isNotInDescriptor, "From", msd.from());
      this.setUnsetProperties((PropertyBean)msBean, isNotInDescriptor, msd.properties());
   }

   protected void addConnectionFactoryDefinition(ConnectionFactoryDefinition cfd, J2eeClientEnvironmentBean eg) {
      String interfaceName = cfd.interfaceName();
      String name = cfd.name();
      String resourceAdapter = cfd.resourceAdapter();
      ConnectionFactoryResourceBean cfrBean = null;
      ConnectionFactoryResourceBean[] var7 = eg.getConnectionFactories();
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         ConnectionFactoryResourceBean bean = var7[var9];
         if (bean.getName().equals(name)) {
            cfrBean = bean;
            break;
         }
      }

      boolean isNotInDescriptor = cfrBean == null;
      if (isNotInDescriptor) {
         cfrBean = eg.createConnectionFactoryResourceBean();
         cfrBean.setInterfaceName(interfaceName);
         cfrBean.setName(name);
         cfrBean.setResourceAdapter(resourceAdapter);
      }

      this.setUnsetAttribute(cfrBean, isNotInDescriptor, "Description", cfd.description());
      this.setUnsetAttribute(cfrBean, isNotInDescriptor, "MaxPoolSize", cfd.maxPoolSize());
      this.setUnsetAttribute(cfrBean, isNotInDescriptor, "MinPoolSize", cfd.minPoolSize());
      String transactionSupport = "NoTransaction";
      switch (cfd.transactionSupport()) {
         case LocalTransaction:
            transactionSupport = "LocalTransaction";
            break;
         case NoTransaction:
            transactionSupport = "NoTransaction";
            break;
         case XATransaction:
            transactionSupport = "XATransaction";
      }

      this.setUnsetAttribute(cfrBean, isNotInDescriptor, "TransactionSupport", transactionSupport);
      this.setUnsetProperties((PropertyBean)cfrBean, isNotInDescriptor, cfd.properties());
   }

   protected void addAdministeredObjectDefinition(AdministeredObjectDefinition aod, J2eeClientEnvironmentBean eg) {
      String className = aod.className();
      String name = aod.name();
      String resourceAdapter = aod.resourceAdapter();
      AdministeredObjectBean aoBean = null;
      AdministeredObjectBean[] var7 = eg.getAdministeredObjects();
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         AdministeredObjectBean bean = var7[var9];
         if (bean.getName().equals(name)) {
            aoBean = bean;
            break;
         }
      }

      boolean isNotInDescriptor = aoBean == null;
      if (isNotInDescriptor) {
         aoBean = eg.createAdministeredObjectBean();
         aoBean.setInterfaceName(className);
         aoBean.setName(name);
         aoBean.setResourceAdapter(resourceAdapter);
      }

      this.setUnsetAttribute(aoBean, isNotInDescriptor, "Description", aod.description());
      this.setUnsetAttribute(aoBean, isNotInDescriptor, "InterfaceName", aod.interfaceName());
      this.setUnsetProperties((PropertyBean)aoBean, isNotInDescriptor, aod.properties());
   }

   protected void setUnsetProperties(PropertyBean bean, boolean isNotInDescriptor, String[] properties) {
      for(int i = 0; i < properties.length; ++i) {
         String property = properties[i];
         this.setUnsetProperty(bean, isNotInDescriptor, property);
      }

   }

   private void setUnsetProperties(DataSourceBean dsBean, boolean isNotInDescriptor, String[] properties) {
      for(int i = 0; i < properties.length; ++i) {
         String property = properties[i];
         this.setUnsetProperty(dsBean, isNotInDescriptor, property);
      }

   }

   private void setUnsetProperty(PropertyBean bean, boolean isNotInDescriptor, String property) {
      int separatorIndex = property.indexOf(61);
      String name = separatorIndex < 0 ? property : property.substring(0, separatorIndex);
      if (isNotInDescriptor || bean.lookupProperty(name) == null) {
         JavaEEPropertyBean propertyBean = bean.createProperty();
         propertyBean.setName(name);
         if (separatorIndex >= 0) {
            propertyBean.setValue(property.substring(separatorIndex + 1));
         }
      }

   }

   private String toIsolationLevelString(int isolationLevel) {
      String isolationLevelString = null;
      switch (isolationLevel) {
         case 1:
            isolationLevelString = "TRANSACTION_READ_UNCOMMITTED";
            break;
         case 2:
            isolationLevelString = "TRANSACTION_READ_COMMITTED";
         case 3:
         case 5:
         case 6:
         case 7:
         default:
            break;
         case 4:
            isolationLevelString = "TRANSACTION_REPEATABLE_READ";
            break;
         case 8:
            isolationLevelString = "TRANSACTION_SERIALIZABLE";
      }

      return isolationLevelString;
   }

   public static String getDefaultDatasource() {
      String defaultDatasource = null;
      ComponentInvocationContextManager manager = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext context = manager.getCurrentComponentInvocationContext();
      if (context != null) {
         String partitionName = context.getPartitionName();
         if (!context.isGlobalRuntime() && partitionName != null) {
            DomainMBean dmb = ManagementUtils.getDomainMBean();
            PartitionMBean pmb = dmb.lookupPartition(partitionName);
            DataSourcePartitionMBean dspmb = pmb.getDataSourcePartition();
            if (dspmb != null) {
               defaultDatasource = dspmb.getDefaultDatasource();
            }
         }
      }

      if (defaultDatasource == null && ManagementUtils.getServerMBean() != null) {
         DataSourceMBean dataSourceMBean = ManagementUtils.getServerMBean().getDataSource();
         defaultDatasource = dataSourceMBean.getDefaultDatasource();
      }

      return defaultDatasource != null ? defaultDatasource : "java:comp/DefaultDataSource";
   }

   private void configureDefaultDatasource(String name, Resource resource, J2eeClientEnvironmentBean eg) {
      String defaultDatasource = getDefaultDatasource();
      ResourceRefBean resourceRefBean;
      if (resource.lookup().equals("java:comp/DefaultDataSource") && defaultDatasource != null) {
         resourceRefBean = this.findOrCreateResourceRef(name, eg);
         resourceRefBean.setResType("javax.sql.DataSource");
         resourceRefBean.setLookupName(defaultDatasource);
      }

      if (resource.name().equals("") && resource.lookup().equals("") && resource.mappedName().equals("")) {
         resourceRefBean = this.findOrCreateResourceRef(name, eg);
         resourceRefBean.setResType("javax.sql.DataSource");
         resourceRefBean.setLookupName(defaultDatasource);
      }

   }

   static {
      if (KernelStatus.isServer()) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         PRODUCTION_MODE = ManagementService.getRuntimeAccess(kernelId).getDomain().isProductionModeEnabled();
      } else {
         PRODUCTION_MODE = false;
      }

      toPrimitive = new HashMap() {
         {
            this.put(Integer.class, Integer.TYPE);
            this.put(Long.class, Long.TYPE);
            this.put(Double.class, Double.TYPE);
            this.put(Boolean.class, Boolean.TYPE);
            this.put(Character.class, Character.TYPE);
            this.put(Float.class, Float.TYPE);
            this.put(Byte.class, Byte.TYPE);
            this.put(Short.class, Short.TYPE);
         }
      };
      toEvolved = new HashMap() {
         {
            Iterator var1 = BaseJ2eeAnnotationProcessor.toPrimitive.entrySet().iterator();

            while(var1.hasNext()) {
               Map.Entry entry = (Map.Entry)var1.next();
               this.put(entry.getValue(), entry.getKey());
            }

         }
      };
   }
}
