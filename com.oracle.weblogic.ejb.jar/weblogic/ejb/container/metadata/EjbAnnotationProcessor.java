package weblogic.ejb.container.metadata;

import java.io.Externalizable;
import java.io.File;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.AccessTimeout;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.AfterBegin;
import javax.ejb.AfterCompletion;
import javax.ejb.ApplicationException;
import javax.ejb.Asynchronous;
import javax.ejb.BeforeCompletion;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.DependsOn;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.Init;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.LocalHome;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.MessageDriven;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remote;
import javax.ejb.RemoteHome;
import javax.ejb.Remove;
import javax.ejb.Schedule;
import javax.ejb.ScheduleExpression;
import javax.ejb.Schedules;
import javax.ejb.SessionSynchronization;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.Stateless;
import javax.ejb.TimedObject;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.ExcludeDefaultInterceptors;
import javax.interceptor.Interceptor;
import javax.jms.MessageListener;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.compliance.ComplianceException;
import weblogic.ejb.container.compliance.EJBComplianceChecker;
import weblogic.ejb.container.compliance.EJBComplianceTextFormatter;
import weblogic.ejb.container.compliance.InterceptorHelper;
import weblogic.ejb.container.compliance.TimeoutCheckHelper;
import weblogic.ejb.container.utils.ClassUtils;
import weblogic.ejb.container.utils.MethodKey;
import weblogic.ejb.container.utils.MethodUtils;
import weblogic.ejb.container.utils.ToStringUtils;
import weblogic.ejb.spi.EJBJarUtils;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.i18n.logging.Loggable;
import weblogic.j2ee.dd.xml.WseeAnnotationProcessor;
import weblogic.j2ee.descriptor.AccessTimeoutBean;
import weblogic.j2ee.descriptor.ActivationConfigBean;
import weblogic.j2ee.descriptor.ActivationConfigPropertyBean;
import weblogic.j2ee.descriptor.ApplicationExceptionBean;
import weblogic.j2ee.descriptor.AssemblyDescriptorBean;
import weblogic.j2ee.descriptor.AsyncMethodBean;
import weblogic.j2ee.descriptor.ConcurrentMethodBean;
import weblogic.j2ee.descriptor.ContainerTransactionBean;
import weblogic.j2ee.descriptor.DependsOnBean;
import weblogic.j2ee.descriptor.EjbCallbackBean;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.EnterpriseBeanBean;
import weblogic.j2ee.descriptor.EnterpriseBeansBean;
import weblogic.j2ee.descriptor.ExcludeListBean;
import weblogic.j2ee.descriptor.InitMethodBean;
import weblogic.j2ee.descriptor.InterceptorBean;
import weblogic.j2ee.descriptor.InterceptorBindingBean;
import weblogic.j2ee.descriptor.InterceptorsBean;
import weblogic.j2ee.descriptor.J2eeClientEnvironmentBean;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;
import weblogic.j2ee.descriptor.MethodBean;
import weblogic.j2ee.descriptor.MethodParamsBean;
import weblogic.j2ee.descriptor.MethodPermissionBean;
import weblogic.j2ee.descriptor.NamedMethodBean;
import weblogic.j2ee.descriptor.RemoveMethodBean;
import weblogic.j2ee.descriptor.SecurityIdentityBean;
import weblogic.j2ee.descriptor.SecurityRoleBean;
import weblogic.j2ee.descriptor.SecurityRoleRefBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.j2ee.descriptor.StatefulTimeoutBean;
import weblogic.j2ee.descriptor.TimerBean;
import weblogic.j2ee.descriptor.TimerScheduleBean;
import weblogic.j2ee.descriptor.wl.IdempotentMethodsBean;
import weblogic.j2ee.descriptor.wl.JndiBindingBean;
import weblogic.j2ee.descriptor.wl.MessageDrivenDescriptorBean;
import weblogic.j2ee.descriptor.wl.RetryMethodsOnRollbackBean;
import weblogic.j2ee.descriptor.wl.SkipStateReplicationMethodsBean;
import weblogic.j2ee.descriptor.wl.StatefulSessionDescriptorBean;
import weblogic.j2ee.descriptor.wl.TransactionDescriptorBean;
import weblogic.j2ee.descriptor.wl.TransactionIsolationBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.javaee.AllowRemoveDuringTransaction;
import weblogic.javaee.CallByReference;
import weblogic.javaee.DisableWarnings;
import weblogic.javaee.Idempotent;
import weblogic.javaee.JMSClientID;
import weblogic.javaee.JNDIName;
import weblogic.javaee.JNDINames;
import weblogic.javaee.MessageDestinationConfiguration;
import weblogic.javaee.SkipStateReplication;
import weblogic.javaee.TransactionIsolation;
import weblogic.javaee.TransactionTimeoutSeconds;
import weblogic.javaee.WarningCode;
import weblogic.managedbean.ManagedBeanAnnotationProcessor;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.utils.reflect.ReflectUtils;

public class EjbAnnotationProcessor extends ManagedBeanAnnotationProcessor {
   private static final DebugLogger debugLogger;
   private final ClassLoader classLoader;
   private final EjbDescriptorBean desc;
   private Boolean disableWarningsIsInXML;

   public EjbAnnotationProcessor(ClassLoader cl, EjbDescriptorBean desc) {
      this.classLoader = cl;
      this.desc = desc;
   }

   public void processAnnotations(Set classes, Set appIceptorClasses) throws ClassNotFoundException, ErrorCollectionException {
      try {
         this.validateComponentDefiningAnnotation(classes);
      } catch (ComplianceException var17) {
         this.addProcessingError(var17.getMessage());
         this.throwProcessingErrors();
      }

      Set beanClassNames = new HashSet();
      Map ejbNameToBean = new HashMap();
      Map ejbNames = new HashMap();
      Set interceptorClassNames = new HashSet();
      Map bindingToInterceptor = new TreeMap(new ManagedBeanAnnotationProcessor.ClassNameComparator());
      EjbJarBean ejbJar = this.desc.getEjbJarBean();
      if (ejbJar == null) {
         ejbJar = this.desc.createEjbJarBean();
      }

      EnterpriseBeansBean ebs = ejbJar.getEnterpriseBeans();
      if (ebs == null) {
         ebs = ejbJar.createEnterpriseBeans();
      }

      SessionBeanBean[] var10 = ebs.getSessions();
      int var11 = var10.length;

      int var12;
      for(var12 = 0; var12 < var11; ++var12) {
         SessionBeanBean session = var10[var12];
         beanClassNames.add(session.getEjbClass());
         ejbNameToBean.put(session.getEjbName(), session);
      }

      MessageDrivenBeanBean[] var22 = ebs.getMessageDrivens();
      var11 = var22.length;

      for(var12 = 0; var12 < var11; ++var12) {
         MessageDrivenBeanBean mdb = var22[var12];
         beanClassNames.add(mdb.getEjbClass());
         ejbNameToBean.put(mdb.getEjbName(), mdb);
      }

      InterceptorsBean interceptorsBean = ejbJar.getInterceptors();
      int var27;
      if (interceptorsBean != null) {
         InterceptorBean[] var24 = interceptorsBean.getInterceptors();
         var12 = var24.length;

         for(var27 = 0; var27 < var12; ++var27) {
            InterceptorBean ib = var24[var27];
            interceptorClassNames.add(ib.getInterceptorClass());
         }
      }

      Set appExceptions = new HashSet();
      Iterator var28 = this.sortEjbClasses(classes).iterator();

      Class clazz;
      while(var28.hasNext()) {
         clazz = (Class)var28.next();
         String ejbName;
         if (clazz.isAnnotationPresent(Stateless.class)) {
            Stateless stateless = (Stateless)clazz.getAnnotation(Stateless.class);
            ejbName = this.getEjbName(stateless.name(), clazz);

            try {
               this.validateNameAnnotation(ejbName, ejbNames, new String[]{"Stateless", clazz.getName()});
            } catch (ComplianceException var18) {
               this.addProcessingError(var18.getMessage());
               break;
            }

            if (!this.ensureBeanClassSet((EnterpriseBeanBean)ejbNameToBean.get(ejbName), clazz) && !beanClassNames.contains(clazz.getName())) {
               this.addSessionBean(ejbName, clazz, ebs, "Stateless");
            }
         } else if (clazz.isAnnotationPresent(Stateful.class)) {
            Stateful stateful = (Stateful)clazz.getAnnotation(Stateful.class);
            ejbName = this.getEjbName(stateful.name(), clazz);

            try {
               this.validateNameAnnotation(ejbName, ejbNames, new String[]{"Stateful", clazz.getName()});
            } catch (ComplianceException var19) {
               this.addProcessingError(var19.getMessage());
               break;
            }

            if (!this.ensureBeanClassSet((EnterpriseBeanBean)ejbNameToBean.get(ejbName), clazz) && !beanClassNames.contains(clazz.getName())) {
               this.addSessionBean(ejbName, clazz, ebs, "Stateful");
            }
         } else if (clazz.isAnnotationPresent(Singleton.class)) {
            Singleton singleton = (Singleton)clazz.getAnnotation(Singleton.class);
            ejbName = this.getEjbName(singleton.name(), clazz);

            try {
               this.validateNameAnnotation(ejbName, ejbNames, new String[]{"Singleton", clazz.getName()});
            } catch (ComplianceException var20) {
               this.addProcessingError(var20.getMessage());
               break;
            }

            if (!this.ensureBeanClassSet((EnterpriseBeanBean)ejbNameToBean.get(ejbName), clazz) && !beanClassNames.contains(clazz.getName())) {
               this.addSessionBean(ejbName, clazz, ebs, "Singleton");
            }
         } else if (clazz.isAnnotationPresent(MessageDriven.class)) {
            MessageDriven md = (MessageDriven)clazz.getAnnotation(MessageDriven.class);
            ejbName = this.getEjbName(md.name(), clazz);

            try {
               this.validateNameAnnotation(ejbName, ejbNames, new String[]{"MessageDriven", clazz.getName()});
            } catch (ComplianceException var21) {
               this.addProcessingError(var21.getMessage());
               break;
            }

            if (!this.ensureBeanClassSet((EnterpriseBeanBean)ejbNameToBean.get(ejbName), clazz) && !beanClassNames.contains(clazz.getName())) {
               this.addMessageDrivenBean(ejbName, clazz, ebs);
            }
         } else if (clazz.isAnnotationPresent(ApplicationException.class)) {
            appExceptions.add(clazz);
         } else if (clazz.isAnnotationPresent(Interceptor.class)) {
            if (!interceptorClassNames.contains(clazz.getName())) {
               if (interceptorsBean == null) {
                  interceptorsBean = ejbJar.createInterceptors();
               }

               this.findOrCreateInterceptorBean(interceptorsBean, clazz);
            }

            this.processInterceptorAnnotation(bindingToInterceptor, clazz);
         }
      }

      if (appIceptorClasses != null) {
         var28 = appIceptorClasses.iterator();

         while(var28.hasNext()) {
            clazz = (Class)var28.next();
            if (clazz.isAnnotationPresent(Interceptor.class)) {
               if (!interceptorClassNames.contains(clazz.getName())) {
                  if (interceptorsBean == null) {
                     interceptorsBean = ejbJar.createInterceptors();
                  }

                  this.findOrCreateInterceptorBean(interceptorsBean, clazz);
               }

               this.processInterceptorAnnotation(bindingToInterceptor, clazz);
            }
         }
      }

      this.processApplicationExceptions(appExceptions, (EjbJarBean)ejbJar);
      SessionBeanBean[] var30 = ebs.getSessions();
      var27 = var30.length;

      int var36;
      for(var36 = 0; var36 < var27; ++var36) {
         SessionBeanBean session = var30[var36];
         this.processSessionAnnotations(session, bindingToInterceptor);
      }

      MessageDrivenBeanBean[] var32 = ebs.getMessageDrivens();
      var27 = var32.length;

      for(var36 = 0; var36 < var27; ++var36) {
         MessageDrivenBeanBean mdb = var32[var36];
         this.processMessageDrivenAnnotations(mdb, bindingToInterceptor);
      }

      this.processInterceptorClasses();
      this.processScheduleAnnotations(ebs);
      if (!EJBComplianceChecker.isNeedCheck) {
         this.validate(this.classLoader, (DescriptorBean)this.desc.getEjbJarBean(), false);
         this.throwProcessingErrors();
      }
   }

   private Set sortEjbClasses(Set classes) {
      Set result = new SortedClassSet();
      result.addAll(classes);
      return result;
   }

   private boolean ensureBeanClassSet(EnterpriseBeanBean eb, Class clazz) {
      if (eb != null) {
         if (!this.isSet("EjbClass", eb)) {
            eb.setEjbClass(clazz.getName());
         }

         return true;
      } else {
         return false;
      }
   }

   private String getEjbName(String ejbName, Class beanClass) {
      return ejbName != null && ejbName.length() != 0 ? ejbName : beanClass.getSimpleName();
   }

   private void addSessionBean(String ejbName, Class beanClass, EnterpriseBeansBean eb, String sessionType) {
      SessionBeanBean sb = eb.createSession();
      sb.setEjbName(ejbName);
      sb.setEjbClass(beanClass.getName());
      sb.setSessionType(sessionType);
   }

   private void addMessageDrivenBean(String ejbName, Class mdb, EnterpriseBeansBean eb) {
      MessageDrivenBeanBean mdbb = eb.createMessageDriven();
      mdbb.setEjbName(ejbName);
      mdbb.setEjbClass(mdb.getName());
   }

   private boolean isTimerMethodExists(TimerBean[] beans, Method method) {
      TimerBean[] var3 = beans;
      int var4 = beans.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         TimerBean bean = var3[var5];
         NamedMethodBean nmb = bean.getTimeoutMethod();
         if (nmb == null) {
            return false;
         }

         if (this.isMethodEqualsMethodBean(method, nmb)) {
            return true;
         }
      }

      return false;
   }

   private boolean isMethodEqualsMethodBean(Method method, NamedMethodBean methodBean) {
      if (!method.getName().equals(methodBean.getMethodName())) {
         return false;
      } else {
         Class[] paramsInMethod = method.getParameterTypes();
         if (methodBean.getMethodParams() == null) {
            return paramsInMethod.length == 0;
         } else {
            String[] paramsInBean = methodBean.getMethodParams().getMethodParams();
            if (paramsInBean.length != paramsInMethod.length) {
               return false;
            } else {
               for(int i = 0; i < paramsInBean.length; ++i) {
                  if (!paramsInBean[i].equals(paramsInMethod[i].getName())) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   private List getValidAnnotatedTimerMethod(TimerBean[] timerBeans, List methodList) {
      List result = new ArrayList();
      Iterator var4 = methodList.iterator();

      while(var4.hasNext()) {
         Method method = (Method)var4.next();
         if (!this.isTimerMethodExists(timerBeans, method)) {
            result.add(method);
         }
      }

      return result;
   }

   private void fillTimerBeanFromScheduleAnnotation(TimerBean timerBean, Schedule schedule, Method annotatedMethod) {
      timerBean.setTimezone(schedule.timezone().trim().length() == 0 ? null : schedule.timezone());
      timerBean.setPersistent(schedule.persistent());
      timerBean.setInfo(schedule.info().trim().length() == 0 ? null : schedule.info());
      TimerScheduleBean scheduleBean = timerBean.createSchedule();
      scheduleBean.setYear(schedule.year());
      scheduleBean.setMonth(schedule.month());
      scheduleBean.setHour(schedule.hour());
      scheduleBean.setMinute(schedule.minute());
      scheduleBean.setSecond(schedule.second());
      scheduleBean.setDayOfWeek(schedule.dayOfWeek());
      scheduleBean.setDayOfMonth(schedule.dayOfMonth());
      this.populateMethodBean(timerBean.createTimeoutMethod(), annotatedMethod);
   }

   private void fillTimerBeansFromSchedulesAnnotation(List timerBeans, Schedules schedulesAnno, Method method) {
      Schedule[] schedules = schedulesAnno.value();

      for(int i = 0; i < schedules.length; ++i) {
         this.fillTimerBeanFromScheduleAnnotation((TimerBean)timerBeans.get(i), schedules[i], method);
      }

   }

   private void vallidateScheduleExpressions(List scheduleList, List schedulesList) throws ErrorCollectionException {
      List scheduleClassList = new LinkedList();
      Iterator var4 = scheduleList.iterator();

      Method each;
      while(var4.hasNext()) {
         each = (Method)var4.next();
         scheduleClassList.add(each.getAnnotation(Schedule.class));
      }

      var4 = schedulesList.iterator();

      while(var4.hasNext()) {
         each = (Method)var4.next();
         Collections.addAll(scheduleClassList, ((Schedules)each.getAnnotation(Schedules.class)).value());
      }

      ErrorCollectionException exps = new ErrorCollectionException();
      Iterator var10 = scheduleClassList.iterator();

      while(var10.hasNext()) {
         Schedule each = (Schedule)var10.next();

         try {
            ScheduleExpression se = new ScheduleExpression();
            se.dayOfMonth(each.dayOfMonth());
            se.dayOfWeek(each.dayOfWeek());
            se.hour(each.hour());
            se.minute(each.minute());
            se.month(each.month());
            se.second(each.second());
            se.timezone(each.timezone().trim().length() == 0 ? null : each.timezone());
            se.year(each.year());
            if (!EJBComplianceChecker.isNeedCheck) {
               TimeoutCheckHelper.validateSingleScheduleExpressionSimply(se);
            }
         } catch (ComplianceException var8) {
            exps.add(var8);
         }
      }

      if (!exps.isEmpty()) {
         throw exps;
      }
   }

   private void readScheduleAnnotationToTimerBean(Class beanClass, EjbTimerBeanFunctions beanFunctions) throws ErrorCollectionException {
      List scheduleList = this.getValidAnnotatedTimerMethod(beanFunctions.getTimers(), this.findAnnotatedMethods(beanClass, Schedule.class, true));
      List schedulesList = this.getValidAnnotatedTimerMethod(beanFunctions.getTimers(), this.findAnnotatedMethods(beanClass, Schedules.class, true));
      this.vallidateScheduleExpressions(scheduleList, schedulesList);
      Iterator var5 = scheduleList.iterator();

      Method method;
      while(var5.hasNext()) {
         method = (Method)var5.next();
         this.fillTimerBeanFromScheduleAnnotation(beanFunctions.createTimer(), (Schedule)method.getAnnotation(Schedule.class), method);
      }

      var5 = schedulesList.iterator();

      while(var5.hasNext()) {
         method = (Method)var5.next();
         Schedules schedules = (Schedules)method.getAnnotation(Schedules.class);
         List timerBeans = new ArrayList();

         for(int i = 0; i < schedules.value().length; ++i) {
            timerBeans.add(beanFunctions.createTimer());
         }

         this.fillTimerBeansFromSchedulesAnnotation(timerBeans, schedules, method);
      }

   }

   private void processScheduleAnnotations(EnterpriseBeansBean ebs) throws ClassNotFoundException, ErrorCollectionException {
      SessionBeanBean[] var2 = ebs.getSessions();
      int var3 = var2.length;

      int var4;
      Class beanClass;
      for(var4 = 0; var4 < var3; ++var4) {
         final SessionBeanBean session = var2[var4];
         beanClass = this.loadBeanClass(session.getEjbClass(), session.getEjbName());
         this.readScheduleAnnotationToTimerBean(beanClass, new EjbTimerBeanFunctions() {
            public TimerBean[] getTimers() {
               return session.getTimers();
            }

            public TimerBean createTimer() {
               return session.createTimer();
            }
         });
      }

      MessageDrivenBeanBean[] var7 = ebs.getMessageDrivens();
      var3 = var7.length;

      for(var4 = 0; var4 < var3; ++var4) {
         final MessageDrivenBeanBean messageDriven = var7[var4];
         beanClass = this.loadBeanClass(messageDriven.getEjbClass(), messageDriven.getEjbName());
         this.readScheduleAnnotationToTimerBean(beanClass, new EjbTimerBeanFunctions() {
            public TimerBean[] getTimers() {
               return messageDriven.getTimers();
            }

            public TimerBean createTimer() {
               return messageDriven.createTimer();
            }
         });
      }

   }

   private void processSessionAnnotations(SessionBeanBean session, Map bindingToInterceptor) throws ClassNotFoundException, ErrorCollectionException {
      Class beanClass = this.loadBeanClass(session.getEjbClass(), session.getEjbName());
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Processing annotations on Session bean class " + beanClass.getName());
      }

      if (!this.isSet("SessionType", session)) {
         if (beanClass.isAnnotationPresent(Stateful.class)) {
            session.setSessionType("Stateful");
         } else if (beanClass.isAnnotationPresent(Stateless.class)) {
            session.setSessionType("Stateless");
         } else if (beanClass.isAnnotationPresent(Singleton.class)) {
            session.setSessionType("Singleton");
         } else {
            Loggable l = EJBLogger.logSessionBeanWithoutSetSessionTypeLoggable(session.getEjbName());
            this.addFatalProcessingError(l.getMessage());
         }
      }

      this.recordComponentClass(beanClass);
      boolean isStateless = session.getSessionType().equals("Stateless");
      boolean isStateful = session.getSessionType().equals("Stateful");
      boolean isSingleton = session.getSessionType().equals("Singleton");
      if (!this.isSet("Home", session)) {
         RemoteHome remoteHome = (RemoteHome)beanClass.getAnnotation(RemoteHome.class);
         if (remoteHome != null) {
            session.setHome(remoteHome.value().getName());
         }
      }

      if (!this.isSet("LocalHome", session)) {
         LocalHome localHome = (LocalHome)beanClass.getAnnotation(LocalHome.class);
         if (localHome != null) {
            session.setLocalHome(localHome.value().getName());
         }
      }

      Set localIfaces = new HashSet();
      Set remoteIfaces = new HashSet();
      Local localAnnotation = (Local)beanClass.getAnnotation(Local.class);
      int var12;
      Class home;
      if (localAnnotation != null) {
         Class[] var10 = localAnnotation.value();
         int var11 = var10.length;

         for(var12 = 0; var12 < var11; ++var12) {
            home = var10[var12];
            localIfaces.add(home);
         }
      }

      Remote remoteAnnotation = (Remote)beanClass.getAnnotation(Remote.class);
      Class remoteIface;
      if (remoteAnnotation != null) {
         Class[] var33 = remoteAnnotation.value();
         var12 = var33.length;

         for(int var36 = 0; var36 < var12; ++var36) {
            remoteIface = var33[var36];
            remoteIfaces.add(remoteIface);
         }
      }

      Set interfaces = this.getImplementedInterfaces(beanClass);
      Iterator var35 = interfaces.iterator();

      while(var35.hasNext()) {
         home = (Class)var35.next();
         if (home.isAnnotationPresent(Remote.class)) {
            remoteIfaces.add(home);
         } else if (home.isAnnotationPresent(Local.class)) {
            localIfaces.add(home);
         }
      }

      if (remoteIfaces.isEmpty() && localIfaces.isEmpty() && session.getBusinessRemotes().length == 0 && session.getBusinessLocals().length == 0 && session.getLocalBean() == null && beanClass.getAnnotation(LocalBean.class) == null) {
         if (beanClass.isAnnotationPresent(Remote.class)) {
            remoteIfaces.addAll(interfaces);
         } else {
            for(var35 = interfaces.iterator(); var35.hasNext(); localIfaces.add(home)) {
               home = (Class)var35.next();
               if (Remote.class.isAssignableFrom(home)) {
                  EJBLogger.logLocalBusinessInterfaceExtendsRemote(home.getName(), beanClass.getName());
               }
            }
         }
      }

      int var15;
      int var16;
      Method m;
      String local;
      Iterator var39;
      Method[] var40;
      if (session.getHome() != null) {
         local = session.getRemote();
         if (local == null) {
            var39 = remoteIfaces.iterator();

            while(var39.hasNext()) {
               remoteIface = (Class)var39.next();
               if (EJBObject.class.isAssignableFrom(remoteIface)) {
                  session.setRemote(remoteIface.getName());
                  remoteIfaces.remove(remoteIface);
                  break;
               }
            }

            if (session.getRemote() == null) {
               home = this.classLoader.loadClass(session.getHome());
               var40 = home.getMethods();
               var15 = var40.length;

               for(var16 = 0; var16 < var15; ++var16) {
                  m = var40[var16];
                  if (m.getName().startsWith("create") && EJBObject.class.isAssignableFrom(m.getReturnType())) {
                     session.setRemote(m.getReturnType().getName());
                     break;
                  }
               }
            }
         } else {
            remoteIfaces.remove(this.classLoader.loadClass(local));
         }
      }

      if (session.getLocalHome() != null) {
         local = session.getLocal();
         if (local == null) {
            var39 = localIfaces.iterator();

            while(var39.hasNext()) {
               remoteIface = (Class)var39.next();
               if (EJBLocalObject.class.isAssignableFrom(remoteIface)) {
                  session.setLocal(remoteIface.getName());
                  localIfaces.remove(remoteIface);
                  break;
               }
            }

            if (session.getLocal() == null) {
               home = this.classLoader.loadClass(session.getLocalHome());
               var40 = home.getMethods();
               var15 = var40.length;

               for(var16 = 0; var16 < var15; ++var16) {
                  m = var40[var16];
                  if (m.getName().startsWith("create") && EJBLocalObject.class.isAssignableFrom(m.getReturnType())) {
                     session.setLocal(m.getReturnType().getName());
                     break;
                  }
               }
            }
         } else {
            localIfaces.remove(this.classLoader.loadClass(local));
         }
      }

      HashSet descriptorLocals;
      int var43;
      String[] var45;
      String createMethodName;
      if (session.getBusinessLocals().length == 0) {
         descriptorLocals = new HashSet();
         var45 = session.getBusinessRemotes();
         var43 = var45.length;

         for(var15 = 0; var15 < var43; ++var15) {
            createMethodName = var45[var15];
            descriptorLocals.add(createMethodName);
         }

         var39 = localIfaces.iterator();

         while(var39.hasNext()) {
            remoteIface = (Class)var39.next();
            if (!descriptorLocals.contains(remoteIface.getName())) {
               session.addBusinessLocal(remoteIface.getName());
            }
         }
      }

      if (session.getBusinessRemotes().length == 0) {
         descriptorLocals = new HashSet();
         var45 = session.getBusinessLocals();
         var43 = var45.length;

         for(var15 = 0; var15 < var43; ++var15) {
            createMethodName = var45[var15];
            descriptorLocals.add(createMethodName);
         }

         var39 = remoteIfaces.iterator();

         while(var39.hasNext()) {
            remoteIface = (Class)var39.next();
            if (!descriptorLocals.contains(remoteIface.getName())) {
               session.addBusinessRemote(remoteIface.getName());
            }
         }
      }

      if (session.getLocalBean() == null && beanClass.getAnnotation(LocalBean.class) != null) {
         session.createLocalBean();
      }

      this.processAsyncMethods(beanClass, session);
      if (!this.isSet("TransactionType", session)) {
         session.setTransactionType(this.getTransactionType(beanClass));
      }

      Stateful sf;
      if (isStateless) {
         Stateless sl = (Stateless)beanClass.getAnnotation(Stateless.class);
         if (sl != null && !this.isSet("MappedName", session) && sl.mappedName().length() > 0) {
            session.setMappedName(sl.mappedName());
         }
      } else if (isStateful) {
         sf = (Stateful)beanClass.getAnnotation(Stateful.class);
         if (sf != null && !this.isSet("MappedName", session) && sf.mappedName().length() > 0) {
            session.setMappedName(sf.mappedName());
         }
      } else if (isSingleton) {
         Singleton sg = (Singleton)beanClass.getAnnotation(Singleton.class);
         if (sg != null && !this.isSet("MappedName", session) && sg.mappedName().length() > 0) {
            session.setMappedName(sg.mappedName());
         }
      }

      if (isSingleton || isStateful) {
         if (!this.isSet("ConcurrencyManagement", session)) {
            session.setConcurrencyManagement(this.getConcurrencyManagement(beanClass));
         }

         if (!"Bean".equals(session.getConcurrencyManagement())) {
            this.processConcurrentMethods(beanClass, session);
         }
      }

      String localCreateMethodName;
      if (isSingleton) {
         if (!this.isSet("InitOnStartup", session) && beanClass.getAnnotation(Startup.class) != null) {
            session.setInitOnStartup(true);
         }

         if (session.getDependsOn() == null) {
            DependsOn dependsOn = (DependsOn)beanClass.getAnnotation(DependsOn.class);
            if (dependsOn != null) {
               DependsOnBean dob = session.createDependsOn();
               String[] var49 = dependsOn.value();
               var15 = var49.length;

               for(var16 = 0; var16 < var15; ++var16) {
                  localCreateMethodName = var49[var16];
                  dob.addEjbName(localCreateMethodName);
               }
            }
         }
      } else if (isStateful && !this.isSet("PassivationCapable", session)) {
         sf = (Stateful)beanClass.getAnnotation(Stateful.class);
         if (sf != null) {
            session.setPassivationCapable(sf.passivationCapable());
         }
      }

      if (isStateless || isSingleton) {
         try {
            Method mInBC = this.findAnnotatedTimeoutMethod(beanClass);
            Method mInDD = getTimeoutMethodByDD(beanClass, session);
            TimeoutCheckHelper.validateTimeoutMethodsIdentical(mInDD, mInBC);
            if (mInDD == null && mInBC != null) {
               this.populateMethodBean(session.createTimeoutMethod(), mInBC);
            }
         } catch (ComplianceException var28) {
            throw new ErrorCollectionException(var28);
         }
      }

      List methods;
      if (isStateful && session.getInitMethods().length == 0) {
         methods = this.findAnnotatedMethods(beanClass, Init.class, false);
         if (!methods.isEmpty() && (session.getHome() != null || session.getLocalHome() != null)) {
            var39 = methods.iterator();

            while(var39.hasNext()) {
               Method m = (Method)var39.next();
               Init annotation = (Init)m.getAnnotation(Init.class);
               createMethodName = annotation.value();
               localCreateMethodName = annotation.value();
               Method createMethod = null;
               Method localCreateMethod = null;
               weblogic.logging.Loggable l;
               if (createMethodName == null || createMethodName.length() == 0) {
                  Class localHome;
                  List initMethods;
                  if (session.getHome() != null) {
                     localHome = this.classLoader.loadClass(session.getHome());
                     initMethods = ClassUtils.getMethodNamesForNameAndParams("create", m.getParameterTypes(), localHome.getMethods());
                     if (initMethods.isEmpty()) {
                        l = EJBLogger.logNoMatchCreateMethodForInitMethodLoggable(m.toString(), session.getEjbName());
                        this.addProcessingError(l.getMessage());
                     } else if (initMethods.size() > 1) {
                        this.addProcessingError("The value element of @Init annotation must be specified when the home interface:" + localHome + " of a stateful session bean:" + beanClass + " that has more than one create<METHOD> method.");
                     } else {
                        createMethodName = (String)initMethods.get(0);
                     }
                  }

                  if (session.getLocalHome() != null) {
                     localHome = this.classLoader.loadClass(session.getLocalHome());
                     initMethods = ClassUtils.getMethodNamesForNameAndParams("create", m.getParameterTypes(), localHome.getMethods());
                     if (initMethods.isEmpty()) {
                        l = EJBLogger.logNoMatchCreateMethodForInitMethodLoggable(m.toString(), session.getEjbName());
                        this.addProcessingError(l.getMessage());
                     } else if (initMethods.size() > 1) {
                        this.addProcessingError("The value element of @Init annotation must be specified when the home interface:" + localHome + " of a stateful session bean:" + beanClass + " that has more than one create<METHOD> method.");
                     } else {
                        localCreateMethodName = (String)initMethods.get(0);
                     }
                  }
               }

               boolean matchingCreateMethod = false;
               boolean matchingLocalCreateMethod = false;

               Class localHome;
               try {
                  if (session.getHome() != null) {
                     localHome = this.classLoader.loadClass(session.getHome());
                     createMethod = localHome.getMethod(createMethodName, m.getParameterTypes());
                     matchingCreateMethod = true;
                  }
               } catch (NoSuchMethodException var27) {
               }

               try {
                  if (session.getLocalHome() != null) {
                     localHome = this.classLoader.loadClass(session.getLocalHome());
                     localCreateMethod = localHome.getMethod(localCreateMethodName, m.getParameterTypes());
                     matchingLocalCreateMethod = true;
                  }
               } catch (NoSuchMethodException var26) {
               }

               if (!matchingCreateMethod && !matchingLocalCreateMethod) {
                  l = EJBLogger.logNoMatchCreateMethodForInitMethodLoggable(m.toString(), session.getEjbName());
                  this.addProcessingError(l.getMessage());
               }

               InitMethodBean init;
               if (matchingCreateMethod) {
                  init = session.createInitMethod();
                  this.populateMethodBean(init.createBeanMethod(), m);
                  this.populateMethodBean(init.createCreateMethod(), createMethod);
               }

               if (matchingLocalCreateMethod) {
                  init = session.createInitMethod();
                  this.populateMethodBean(init.createBeanMethod(), m);
                  this.populateMethodBean(init.createCreateMethod(), localCreateMethod);
               }
            }
         }
      }

      if (isStateful) {
         methods = this.findAnnotatedMethods(beanClass, Remove.class, false);
         List allDeclaredMethods = Arrays.asList(beanClass.getDeclaredMethods());
         HashSet specificMethods = new HashSet();
         LinkedList generalBeans = new LinkedList();
         RemoveMethodBean[] var62 = session.getRemoveMethods();
         int var59 = var62.length;

         RemoveMethodBean rmb;
         for(int var60 = 0; var60 < var59; ++var60) {
            rmb = var62[var60];
            NamedMethodBean methodBean = rmb.getBeanMethod();
            MethodParamsBean methodParamsBean = methodBean.getMethodParams();
            if (methodParamsBean == null) {
               generalBeans.add(rmb);
            } else {
               String methodName = methodBean.getMethodName();
               String[] methodParams = methodParamsBean.getMethodParams();
               Method foundMethod = ClassUtils.getMethodForNameAndParams(methodName, methodParams, allDeclaredMethods);
               if (foundMethod != null) {
                  specificMethods.add(foundMethod);
               }

               Method matchedMethod = ClassUtils.getMethodForNameAndParams(methodName, methodParams, methods);
               if (!this.isSet("RetainIfException", rmb) && matchedMethod != null) {
                  rmb.setRetainIfException(((Remove)matchedMethod.getAnnotation(Remove.class)).retainIfException());
               }

               if (matchedMethod != null) {
                  methods.remove(matchedMethod);
               }
            }
         }

         Iterator var64 = generalBeans.iterator();

         while(var64.hasNext()) {
            RemoveMethodBean generalBean = (RemoveMethodBean)var64.next();
            String methodName = generalBean.getBeanMethod().getMethodName();
            List affectedRemoveMethods = ReflectUtils.getDeclaredMethodsOfAGivenName(beanClass, methodName);
            Iterator var73 = affectedRemoveMethods.iterator();

            while(var73.hasNext()) {
               Method affectedRemoveMethod = (Method)var73.next();
               if (!specificMethods.contains(affectedRemoveMethod)) {
                  boolean retainIfException = false;
                  Remove annotation = (Remove)affectedRemoveMethod.getAnnotation(Remove.class);
                  if (annotation != null) {
                     methods.remove(affectedRemoveMethod);
                     retainIfException = annotation.retainIfException();
                  }

                  if (this.isSet("RetainIfException", generalBean)) {
                     retainIfException = generalBean.isRetainIfException();
                  }

                  RemoveMethodBean rm = session.createRemoveMethod();
                  this.populateMethodBean(rm.createBeanMethod(), affectedRemoveMethod);
                  rm.setRetainIfException(retainIfException);
               }
            }

            session.destroyRemoveMethod(generalBean);
         }

         var64 = methods.iterator();

         while(var64.hasNext()) {
            m = (Method)var64.next();
            Remove annotation = (Remove)m.getAnnotation(Remove.class);
            rmb = session.createRemoveMethod();
            this.populateMethodBean(rmb.createBeanMethod(), m);
            rmb.setRetainIfException(annotation.retainIfException());
         }

         if (!SessionSynchronization.class.isAssignableFrom(beanClass)) {
            this.processSessionSyncAnnotations(beanClass, session);
         }

         StatefulTimeout statefulTimeout = (StatefulTimeout)beanClass.getAnnotation(StatefulTimeout.class);
         if (statefulTimeout != null) {
            StatefulTimeoutBean stb = session.createStatefulTimeout();
            stb.setTimeout(statefulTimeout.value());
            stb.setUnit(statefulTimeout.unit().name());
         }
      }

      this.processEjbCallbackAnnotations(beanClass, session);
      this.processJ2eeAnnotations(beanClass, session);
      this.processDeclareRoles(beanClass, (DescriptorBean)session);
      this.processRunAs(beanClass, (DescriptorBean)session);
      this.processAssemblyDescriptor(session, beanClass, this.getInterfaceNames(session), bindingToInterceptor, isSingleton || isStateful);
   }

   private void processConcurrentMethods(Class c, SessionBeanBean sbb) {
      Set set = new HashSet();
      ConcurrentMethodBean[] cmbs = sbb.getConcurrentMethods();
      if (cmbs != null) {
         ConcurrentMethodBean[] var5 = cmbs;
         int var6 = cmbs.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            ConcurrentMethodBean cmb = var5[var7];
            MethodParamsBean mpb = cmb.getMethod().getMethodParams();
            set.add(new MethodKey(cmb.getMethod().getMethodName(), mpb == null ? new String[0] : mpb.getMethodParams()));
         }
      }

      while(c != null && c != Object.class) {
         Lock cl = (Lock)c.getAnnotation(Lock.class);
         AccessTimeout cat = (AccessTimeout)c.getAnnotation(AccessTimeout.class);
         Method[] var15 = c.getDeclaredMethods();
         int var16 = var15.length;

         for(int var17 = 0; var17 < var16; ++var17) {
            Method m = var15[var17];
            Lock ml = (Lock)m.getAnnotation(Lock.class);
            AccessTimeout mat = (AccessTimeout)m.getAnnotation(AccessTimeout.class);
            this.addConcurrentMethodBean(ml == null ? cl : ml, mat == null ? cat : mat, m, set, sbb);
         }

         c = c.getSuperclass();
      }

   }

   private void addConcurrentMethodBean(Lock l, AccessTimeout at, Method m, Set set, SessionBeanBean sbb) {
      MethodKey mk = new MethodKey(m);
      if (!set.contains(mk)) {
         if (l != null || at != null) {
            ConcurrentMethodBean cmb = sbb.createConcurrentMethod();
            NamedMethodBean nmb = cmb.createMethod();
            nmb.setMethodName(mk.getMethodName());
            nmb.createMethodParams().setMethodParams(mk.getMethodParams());
            if (l != null) {
               cmb.setConcurrentLockType(this.getLockTypeAsString(l));
            }

            if (at != null) {
               AccessTimeoutBean atb = cmb.createAccessTimeout();
               atb.setTimeout(at.value());
               atb.setUnit(ToStringUtils.timeUnitToString(at.unit()));
            }
         }

         set.add(mk);
      }
   }

   private String getLockTypeAsString(Lock l) {
      return l != null && l.value() == LockType.READ ? "Read" : "Write";
   }

   private Set getInterfaceNames(SessionBeanBean session) {
      Set interfaceNames = new HashSet();
      if (session.getRemote() != null) {
         interfaceNames.add(session.getRemote());
      }

      if (session.getLocal() != null) {
         interfaceNames.add(session.getLocal());
      }

      String[] var3 = session.getBusinessLocals();
      int var4 = var3.length;

      int var5;
      String iface;
      for(var5 = 0; var5 < var4; ++var5) {
         iface = var3[var5];
         interfaceNames.add(iface);
      }

      var3 = session.getBusinessRemotes();
      var4 = var3.length;

      for(var5 = 0; var5 < var4; ++var5) {
         iface = var3[var5];
         interfaceNames.add(iface);
      }

      return interfaceNames;
   }

   private Set getInterfaceNames(MessageDrivenBeanBean mdb) {
      Set interfaceNames = new HashSet();
      String mt = mdb.getMessagingType();
      if (mt == null) {
         mt = MessageListener.class.getName();
      }

      interfaceNames.add(mt);
      return interfaceNames;
   }

   private void processSessionSyncAnnotations(Class c, SessionBeanBean sbb) {
      boolean findAB = sbb.getAfterBeginMethod() == null;
      boolean findAC = sbb.getAfterCompletionMethod() == null;

      for(boolean findBC = sbb.getBeforeCompletionMethod() == null; c != null && c != Object.class && (findAB || findAC || findBC); c = c.getSuperclass()) {
         Method[] var6 = c.getDeclaredMethods();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Method m = var6[var8];
            if (findAB && m.isAnnotationPresent(AfterBegin.class)) {
               this.fillMethodBean(sbb.createAfterBeginMethod(), m);
               findAB = false;
            }

            if (findAC && m.isAnnotationPresent(AfterCompletion.class)) {
               this.fillMethodBean(sbb.createAfterCompletionMethod(), m);
               findAC = false;
            }

            if (findBC && m.isAnnotationPresent(BeforeCompletion.class)) {
               this.fillMethodBean(sbb.createBeforeCompletionMethod(), m);
               findBC = false;
            }
         }
      }

   }

   private void processAsyncMethods(Class c, SessionBeanBean sbb) {
      Set set = new HashSet();
      AsyncMethodBean[] ambs = sbb.getAsyncMethods();
      int var6;
      int var7;
      if (ambs != null) {
         AsyncMethodBean[] var5 = ambs;
         var6 = ambs.length;

         for(var7 = 0; var7 < var6; ++var7) {
            AsyncMethodBean amb = var5[var7];
            MethodParamsBean mpb = amb.getMethodParams();
            set.add(new MethodKey(amb.getMethodName(), mpb == null ? new String[0] : mpb.getMethodParams()));
         }
      }

      for(; c != null && c != Object.class; c = c.getSuperclass()) {
         Method[] var10;
         Method m;
         if (c.isAnnotationPresent(Asynchronous.class)) {
            var10 = c.getDeclaredMethods();
            var6 = var10.length;

            for(var7 = 0; var7 < var6; ++var7) {
               m = var10[var7];
               this.addAsyncBean(m, set, sbb);
            }
         } else {
            var10 = c.getDeclaredMethods();
            var6 = var10.length;

            for(var7 = 0; var7 < var6; ++var7) {
               m = var10[var7];
               if (m.isAnnotationPresent(Asynchronous.class)) {
                  this.addAsyncBean(m, set, sbb);
               }
            }
         }
      }

   }

   private void addAsyncBean(Method m, Set set, SessionBeanBean sbb) {
      MethodKey mk = new MethodKey(m);
      if (!set.contains(mk)) {
         AsyncMethodBean amb = sbb.createAsyncMethod();
         amb.setMethodName(mk.getMethodName());
         amb.createMethodParams().setMethodParams(mk.getMethodParams());
         set.add(mk);
      }

   }

   private void perhapsDeclareSecurityRoles(Collection roles, EjbJarBean ejbJar) {
      Set declaredRoles = new HashSet();
      AssemblyDescriptorBean ad = ejbJar.getAssemblyDescriptor();
      if (ad == null) {
         ad = ejbJar.createAssemblyDescriptor();
      }

      SecurityRoleBean[] var5 = ad.getSecurityRoles();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         SecurityRoleBean sr = var5[var7];
         declaredRoles.add(sr.getRoleName());
      }

      Iterator var9 = roles.iterator();

      while(var9.hasNext()) {
         String role = (String)var9.next();
         if (!declaredRoles.contains(role) && !"**".equals(role)) {
            ad.createSecurityRole().setRoleName(role);
            declaredRoles.add(role);
         }
      }

   }

   private void processEjbCallbackAnnotations(Class beanClass, EjbCallbackBean cb) {
      Iterator var3;
      Method m;
      if (cb.getAroundInvokes().length == 0) {
         var3 = this.findAnnotatedMethods(beanClass, AroundInvoke.class, true).iterator();

         while(var3.hasNext()) {
            m = (Method)var3.next();
            this.populateAroundInvokeBean(cb.createAroundInvoke(), m);
         }
      }

      if (cb.getAroundTimeouts().length == 0) {
         var3 = this.findAnnotatedMethods(beanClass, AroundTimeout.class, true).iterator();

         while(var3.hasNext()) {
            m = (Method)var3.next();
            this.populateAroundTimeoutBean(cb.createAroundTimeout(), m);
         }
      }

      if (cb.getPostActivates().length == 0) {
         var3 = this.findAnnotatedMethods(beanClass, PostActivate.class, true).iterator();

         while(var3.hasNext()) {
            m = (Method)var3.next();
            this.populateLifecyleCallbackBean(cb.createPostActivate(), m);
         }
      }

      if (cb.getPrePassivates().length == 0) {
         var3 = this.findAnnotatedMethods(beanClass, PrePassivate.class, true).iterator();

         while(var3.hasNext()) {
            m = (Method)var3.next();
            this.populateLifecyleCallbackBean(cb.createPrePassivate(), m);
         }
      }

   }

   private void processAssemblyDescriptor(EnterpriseBeanBean enterpriseBean, Class beanClass, Set interfaceNames, Map bindingToIterceptor, boolean processTxAnnotationsForLifeCycleCallbacks) throws ErrorCollectionException {
      String ejbName = enterpriseBean.getEjbName();
      EjbJarBean ejbJar = this.desc.getEjbJarBean();
      AssemblyDescriptorBean ad = ejbJar.getAssemblyDescriptor();
      if (ad == null) {
         ad = ejbJar.createAssemblyDescriptor();
      }

      Set ifaces = this.getBusinessInterfaces(interfaceNames);
      Set methods = this.getBusinessMethods(beanClass, ifaces, enterpriseBean);
      methods.addAll(this.getTimeoutMethods(beanClass, enterpriseBean));
      this.processMethodPermissions(ejbName, methods, ad);
      this.processTransactionAttributes(ejbName, methods, ad, false);
      if (processTxAnnotationsForLifeCycleCallbacks) {
         Set lifecycleMethods = this.getLifecycleMethods(beanClass, enterpriseBean);
         this.processTransactionAttributes(ejbName, lifecycleMethods, ad, true);
      }

      this.processInterceptorBindings(ejbName, beanClass, methods, ad, bindingToIterceptor);
      this.processApplicationExceptions(methods, ad);
   }

   private void processApplicationExceptions(Set appExceptions, EjbJarBean ejbJar) {
      AssemblyDescriptorBean ad = ejbJar.getAssemblyDescriptor();
      if (ad == null) {
         ad = ejbJar.createAssemblyDescriptor();
      }

      Set alreadyDeclared = new HashSet();
      ApplicationExceptionBean[] var5 = ad.getApplicationExceptions();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         ApplicationExceptionBean aeb = var5[var7];
         alreadyDeclared.add(aeb.getExceptionClass());
      }

      Iterator var9 = appExceptions.iterator();

      while(var9.hasNext()) {
         Class exception = (Class)var9.next();
         if (!alreadyDeclared.contains(exception.getName())) {
            ApplicationExceptionBean aeb = ad.createApplicationException();
            aeb.setExceptionClass(exception.getName());
            ApplicationException ae = (ApplicationException)exception.getAnnotation(ApplicationException.class);
            aeb.setRollback(ae.rollback());
            aeb.setInherited(ae.inherited());
         }
      }

   }

   private void processApplicationExceptions(Set methods, AssemblyDescriptorBean ad) {
      Set exceptions = new HashSet();
      Iterator var4 = methods.iterator();

      int var7;
      while(var4.hasNext()) {
         Method m = (Method)var4.next();
         Class[] var6 = m.getExceptionTypes();
         var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Class et = var6[var8];
            if (et.isAnnotationPresent(ApplicationException.class)) {
               exceptions.add(et);
            }
         }
      }

      Set alreadyDeclared = new HashSet();
      ApplicationExceptionBean[] var11 = ad.getApplicationExceptions();
      int var13 = var11.length;

      for(var7 = 0; var7 < var13; ++var7) {
         ApplicationExceptionBean aeb = var11[var7];
         alreadyDeclared.add(aeb.getExceptionClass());
      }

      Iterator var12 = exceptions.iterator();

      while(var12.hasNext()) {
         Class exception = (Class)var12.next();
         if (!alreadyDeclared.contains(exception.getName())) {
            ApplicationExceptionBean aeb = ad.createApplicationException();
            aeb.setExceptionClass(exception.getName());
            ApplicationException ae = (ApplicationException)exception.getAnnotation(ApplicationException.class);
            aeb.setRollback(ae.rollback());
            aeb.setInherited(ae.inherited());
         }
      }

   }

   private void processInterceptorBindings(String ejbName, Class beanClass, Set methods, AssemblyDescriptorBean ad, Map bindingToInterceptor) {
      Iterator var6 = methods.iterator();

      while(var6.hasNext()) {
         Method m = (Method)var6.next();
         this.processInterceptorBinding(m, ejbName, methods, ad, bindingToInterceptor);
      }

      Constructor[] var13 = beanClass.getDeclaredConstructors();
      int var15 = var13.length;

      for(int var8 = 0; var8 < var15; ++var8) {
         Constructor c = var13[var8];
         if (c.getParameterTypes().length == 0 || c.isAnnotationPresent(Inject.class)) {
            this.processInterceptorBinding(c, ejbName, ad, bindingToInterceptor);
         }
      }

      List interceptorClasses = this.getOrderedListOfInterceptors(beanClass, bindingToInterceptor);
      boolean excludeDefault = beanClass.isAnnotationPresent(ExcludeDefaultInterceptors.class);
      if (!interceptorClasses.isEmpty() || excludeDefault) {
         InterceptorBindingBean ibb = null;
         InterceptorBindingBean[] var18 = ad.getInterceptorBindings();
         int var10 = var18.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            InterceptorBindingBean ib = var18[var11];
            if (ib.getEjbName().equals(ejbName) && ib.getMethod() == null) {
               ibb = ib;
            }
         }

         if (ibb == null) {
            ibb = ad.createInterceptorBinding();
            ibb.setEjbName(ejbName);
         }

         if (!interceptorClasses.isEmpty()) {
            this.perhapsAddInterceptors(ibb, interceptorClasses);
         }

         if (excludeDefault && !this.isSet("ExcludeDefaultInterceptors", ibb)) {
            ibb.setExcludeDefaultInterceptors(true);
         }
      }

   }

   private void processInterceptorBinding(Method method, String ejbName, Set methods, AssemblyDescriptorBean ad, Map bindingToInterceptor) {
      List interceptorClasses = this.getOrderedListOfInterceptors(method, bindingToInterceptor);
      boolean excludeDefault = method.isAnnotationPresent(ExcludeDefaultInterceptors.class);
      boolean excludeClass = method.isAnnotationPresent(ExcludeClassInterceptors.class);
      if (!interceptorClasses.isEmpty() || excludeDefault || excludeClass) {
         InterceptorBindingBean ibb = this.getMethodInterceptorBinding(ejbName, method, ad.getInterceptorBindings(), methods);
         this.constructInterceptorBindingBean(ibb, ad, interceptorClasses, ejbName, method.getName(), method.getParameterTypes(), excludeDefault, excludeClass);
      }
   }

   private void processInterceptorBinding(Constructor constructor, String ejbName, AssemblyDescriptorBean ad, Map bindingToInterceptor) {
      List interceptorClasses = this.getOrderedListOfInterceptors(constructor, bindingToInterceptor);
      boolean excludeDefault = constructor.isAnnotationPresent(ExcludeDefaultInterceptors.class);
      boolean excludeClass = constructor.isAnnotationPresent(ExcludeClassInterceptors.class);
      if (!interceptorClasses.isEmpty() || excludeDefault || excludeClass) {
         InterceptorBindingBean ibb = this.getConstructorInterceptorBinding(ejbName, constructor, ad.getInterceptorBindings());
         this.constructInterceptorBindingBean(ibb, ad, interceptorClasses, ejbName, constructor.getDeclaringClass().getSimpleName(), constructor.getParameterTypes(), excludeDefault, excludeClass);
      }
   }

   private void constructInterceptorBindingBean(InterceptorBindingBean ibb, AssemblyDescriptorBean ad, List interceptorClasses, String ejbName, String methodName, Class[] parameterTypes, boolean excludeDefault, boolean excludeClass) {
      if (ibb == null) {
         ibb = ad.createInterceptorBinding();
         ibb.setEjbName(ejbName);
         this.populateMethodBean(ibb.createMethod(), methodName, parameterTypes);
      }

      if (!interceptorClasses.isEmpty()) {
         this.perhapsAddInterceptors(ibb, interceptorClasses);
      }

      if (excludeDefault && !this.isSet("ExcludeDefaultInterceptors", ibb)) {
         ibb.setExcludeDefaultInterceptors(true);
      }

      if (excludeClass && !this.isSet("ExcludeClassInterceptors", ibb)) {
         ibb.setExcludeClassInterceptors(true);
      }

   }

   private void processInterceptorClasses() throws ErrorCollectionException {
      Set iceptorClassNames = new HashSet();
      AssemblyDescriptorBean ad = this.desc.getEjbJarBean().getAssemblyDescriptor();
      int var5;
      if (ad != null) {
         InterceptorBindingBean[] var3 = ad.getInterceptorBindings();
         int var4 = var3.length;

         for(var5 = 0; var5 < var4; ++var5) {
            InterceptorBindingBean ibb = var3[var5];
            String[] iceptorClasses = null;
            if (ibb.getInterceptorOrder() != null) {
               iceptorClasses = ibb.getInterceptorOrder().getInterceptorClasses();
            } else {
               iceptorClasses = ibb.getInterceptorClasses();
            }

            String[] var8 = iceptorClasses;
            int var9 = iceptorClasses.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               String className = var8[var10];
               iceptorClassNames.add(className);
            }
         }
      }

      InterceptorsBean iceptors = this.desc.getEjbJarBean().getInterceptors();
      if (iceptors != null) {
         InterceptorBean[] var13 = iceptors.getInterceptors();
         var5 = var13.length;

         for(int var16 = 0; var16 < var5; ++var16) {
            InterceptorBean iceptor = var13[var16];
            this.processInterceptorClass(iceptor);
            iceptorClassNames.remove(iceptor.getInterceptorClass());
         }
      }

      if (!iceptorClassNames.isEmpty()) {
         if (iceptors == null) {
            iceptors = this.desc.getEjbJarBean().createInterceptors();
         }

         Iterator var14 = iceptorClassNames.iterator();

         while(var14.hasNext()) {
            String className = (String)var14.next();
            InterceptorBean iceptor = iceptors.createInterceptor();
            iceptor.setInterceptorClass(className);
            this.processInterceptorClass(iceptor);
         }
      }

   }

   private void processInterceptorClass(InterceptorBean iceptor) throws ErrorCollectionException {
      try {
         Class iceptorClass = this.classLoader.loadClass(iceptor.getInterceptorClass());
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Processing annotations intercepter class " + iceptorClass.getName());
         }

         this.processJ2eeAnnotations(iceptorClass, iceptor);
         this.processEjbCallbackAnnotations(iceptorClass, iceptor);
         this.processAroundConstructAnnotations(iceptorClass, iceptor);
      } catch (ClassNotFoundException var4) {
         Loggable log = EJBLogger.logCannotLoadInterceptorClassLoggable(iceptor.getInterceptorClass().toString(), var4.toString());
         this.addFatalProcessingError(log.getMessage());
      }

   }

   private void processTransactionAttributes(String ejbName, Set origMethods, AssemblyDescriptorBean ad, boolean isLifecycleCallback) {
      Set methods = new HashSet(origMethods);
      ContainerTransactionBean[] var6 = ad.getContainerTransactions();
      int var7 = var6.length;

      ContainerTransactionBean ct;
      for(int var8 = 0; var8 < var7; ++var8) {
         ct = var6[var8];
         MethodBean[] var10 = ct.getMethods();
         int var11 = var10.length;

         for(int var12 = 0; var12 < var11; ++var12) {
            MethodBean mb = var10[var12];
            if (mb.getEjbName().equals(ejbName)) {
               this.removeMethodsFromSet(mb, methods);
            }
         }
      }

      Iterator var14 = methods.iterator();

      while(var14.hasNext()) {
         Method m = (Method)var14.next();
         TransactionAttribute ta = null;
         if (m.isAnnotationPresent(TransactionAttribute.class)) {
            ta = (TransactionAttribute)m.getAnnotation(TransactionAttribute.class);
         }

         if (ta == null && !isLifecycleCallback) {
            Class dc = m.getDeclaringClass();
            if (dc.isAnnotationPresent(TransactionAttribute.class)) {
               ta = (TransactionAttribute)dc.getAnnotation(TransactionAttribute.class);
            }
         }

         if (ta != null) {
            ct = ad.createContainerTransaction();
            ct.setTransAttribute(this.getTransactionAttributeAsString(ta.value()));
            this.fillMethodBean(ct.createMethod(), ejbName, m);
         }
      }

   }

   private void processMethodPermissions(String ejbName, Set origMethods, AssemblyDescriptorBean ad) {
      Set methods = new HashSet(origMethods);
      MethodPermissionBean[] var5 = ad.getMethodPermissions();
      int var6 = var5.length;

      int var7;
      for(var7 = 0; var7 < var6; ++var7) {
         MethodPermissionBean mp = var5[var7];
         MethodBean[] var9 = mp.getMethods();
         int var10 = var9.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            MethodBean mb = var9[var11];
            if (mb.getEjbName().equals(ejbName)) {
               this.removeMethodsFromSet(mb, methods);
            }
         }
      }

      ExcludeListBean el = ad.getExcludeList();
      MethodBean mb;
      if (el != null) {
         MethodBean[] var14 = el.getMethods();
         var7 = var14.length;

         for(int var17 = 0; var17 < var7; ++var17) {
            mb = var14[var17];
            if (mb.getEjbName().equals(ejbName)) {
               this.removeMethodsFromSet(mb, methods);
            }
         }
      }

      Set securityRoles = new HashSet();
      Iterator var16 = methods.iterator();

      while(var16.hasNext()) {
         Method m = (Method)var16.next();
         mb = null;
         weblogic.logging.Loggable l;
         MethodPermissionBean mp;
         if (!m.isAnnotationPresent(DenyAll.class)) {
            if (m.isAnnotationPresent(PermitAll.class)) {
               if (m.isAnnotationPresent(RolesAllowed.class)) {
                  l = EJBLogger.logMutipleMehtodPermissionMethodForMethodLoggable(ejbName, m.toString());
                  this.addProcessingError(l.getMessage());
               }

               MethodPermissionBean mp = ad.createMethodPermission();
               mp.createUnchecked();
               mb = mp.createMethod();
            } else if (m.isAnnotationPresent(RolesAllowed.class)) {
               RolesAllowed allowed = (RolesAllowed)m.getAnnotation(RolesAllowed.class);
               mp = ad.createMethodPermission();
               mp.setRoleNames(allowed.value());
               mb = mp.createMethod();
               securityRoles.addAll(Arrays.asList(allowed.value()));
            }
         } else {
            if (m.isAnnotationPresent(PermitAll.class) || m.isAnnotationPresent(RolesAllowed.class)) {
               l = EJBLogger.logMutipleMehtodPermissionMethodForMethodLoggable(ejbName, m.toString());
               this.addProcessingError(l.getMessage());
            }

            if (el == null) {
               el = ad.createExcludeList();
            }

            mb = el.createMethod();
         }

         if (mb == null) {
            Class clazz = m.getDeclaringClass();
            weblogic.logging.Loggable l;
            if (!clazz.isAnnotationPresent(DenyAll.class)) {
               if (clazz.isAnnotationPresent(PermitAll.class)) {
                  if (clazz.isAnnotationPresent(RolesAllowed.class)) {
                     l = EJBLogger.logMutipleMehtodPermissionMethodForClassLoggable(clazz.toString());
                     this.addProcessingError(l.getMessage());
                  }

                  mp = ad.createMethodPermission();
                  mp.createUnchecked();
                  mb = mp.createMethod();
               } else if (clazz.isAnnotationPresent(RolesAllowed.class)) {
                  RolesAllowed allowed = (RolesAllowed)clazz.getAnnotation(RolesAllowed.class);
                  MethodPermissionBean mp = ad.createMethodPermission();
                  mp.setRoleNames(allowed.value());
                  mb = mp.createMethod();
                  securityRoles.addAll(Arrays.asList(allowed.value()));
               }
            } else {
               if (clazz.isAnnotationPresent(PermitAll.class) || clazz.isAnnotationPresent(RolesAllowed.class)) {
                  l = EJBLogger.logMutipleMehtodPermissionMethodForClassLoggable(clazz.toString());
                  this.addProcessingError(l.getMessage());
               }

               if (el == null) {
                  el = ad.createExcludeList();
               }

               mb = el.createMethod();
            }
         }

         if (mb != null) {
            this.fillMethodBean(mb, ejbName, m);
         }
      }

      if (!securityRoles.isEmpty()) {
         this.perhapsDeclareSecurityRoles(securityRoles, this.desc.getEjbJarBean());
      }

   }

   private void removeMethodsFromSet(MethodBean mb, Set methods) {
      MethodParamsBean mps = mb.getMethodParams();
      Collection removeMethods = new HashSet();
      if (mps == null) {
         if ("*".equals(mb.getMethodName())) {
            methods.clear();
         } else {
            Iterator var5 = methods.iterator();

            while(var5.hasNext()) {
               Method m = (Method)var5.next();
               if (m.getName().equals(mb.getMethodName())) {
                  removeMethods.add(m);
               }
            }
         }
      } else {
         String[] params = mps.getMethodParams();
         Iterator var15 = methods.iterator();

         label49:
         while(true) {
            Method m;
            Class[] paramTypes;
            do {
               do {
                  if (!var15.hasNext()) {
                     break label49;
                  }

                  m = (Method)var15.next();
               } while(!m.getName().equals(mb.getMethodName()));

               paramTypes = m.getParameterTypes();
            } while(params.length != paramTypes.length);

            int counter = 0;
            Class[] var10 = paramTypes;
            int var11 = paramTypes.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               Class param = var10[var12];
               if (!param.getName().equals(params[counter])) {
                  break;
               }

               ++counter;
            }

            if (counter == params.length) {
               removeMethods.add(m);
            }
         }
      }

      methods.removeAll(removeMethods);
   }

   private Set getBusinessInterfaces(Collection ifaceNames) throws ErrorCollectionException {
      Set ifaces = new HashSet();
      Iterator var3 = ifaceNames.iterator();

      while(var3.hasNext()) {
         String ifaceName = (String)var3.next();

         try {
            ifaces.add(this.classLoader.loadClass(ifaceName));
         } catch (ClassNotFoundException var7) {
            Loggable l = EJBLogger.logUnableLoadInterfaceClassLoggable(ifaceName, var7.toString());
            this.addFatalProcessingError(l.getMessage());
         }
      }

      return ifaces;
   }

   private Set getBusinessMethods(Class beanClass, Set ifaces, EnterpriseBeanBean enterpriseBean) throws ErrorCollectionException {
      Set bm = new HashSet();
      Iterator var5 = ifaces.iterator();

      while(var5.hasNext()) {
         Class iface = (Class)var5.next();
         Method[] var7 = iface.getMethods();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Method m = var7[var9];
            if (m.getDeclaringClass() != EJBObject.class && m.getDeclaringClass() != EJBLocalObject.class) {
               try {
                  bm.add(beanClass.getMethod(m.getName(), m.getParameterTypes()));
               } catch (NoSuchMethodException var15) {
                  boolean missingBridge = false;
                  Method[] beanMethods;
                  if (!EJBObject.class.isAssignableFrom(iface) && !EJBLocalObject.class.isAssignableFrom(iface)) {
                     beanMethods = beanClass.getMethods();

                     for(int i = 0; i < beanMethods.length && !missingBridge; ++i) {
                        missingBridge = MethodUtils.potentialBridgeCandidate(m, beanMethods[i]);
                     }
                  }

                  beanMethods = null;
                  weblogic.logging.Loggable log;
                  if (missingBridge) {
                     log = EJBLogger.logMayBeMissingBridgeMethodLoggable(m.toString(), iface.getName());
                  } else {
                     log = EJBLogger.logBeanClassNotImplementInterfaceMethodLoggable(beanClass.getName(), m.toString());
                  }

                  this.addFatalProcessingError(log.getMessage());
               }
            }
         }
      }

      if (this.isWebService(enterpriseBean, beanClass)) {
         bm.addAll(this.getWebServiceMethods(enterpriseBean, beanClass));
      }

      if (this.hasNoInterfaceView(enterpriseBean, beanClass)) {
         Method[] var16 = beanClass.getMethods();
         int var17 = var16.length;

         for(int var18 = 0; var18 < var17; ++var18) {
            Method m = var16[var18];
            if (!Modifier.isAbstract(m.getModifiers()) && !Modifier.isStatic(m.getModifiers()) && !Modifier.isFinal(m.getModifiers()) && m.getDeclaringClass() != Object.class) {
               bm.add(m);
            }
         }
      }

      return bm;
   }

   private boolean hasNoInterfaceView(EnterpriseBeanBean ebb, Class beanClass) throws ErrorCollectionException {
      if (ebb instanceof SessionBeanBean) {
         SessionBeanBean sbb = (SessionBeanBean)ebb;
         return sbb.getLocalBean() != null || sbb.getBusinessRemotes().length == 0 && sbb.getBusinessLocals().length == 0 && sbb.getHome() == null && sbb.getLocalHome() == null && !this.isWebService(sbb, beanClass);
      } else if (!(ebb instanceof MessageDrivenBeanBean)) {
         return false;
      } else {
         Set intfs = this.getBusinessInterfaces(this.getInterfaceNames((MessageDrivenBeanBean)ebb));
         return intfs.size() == 1 && ((Class)intfs.iterator().next()).getMethods().length == 0;
      }
   }

   private boolean isWebService(EnterpriseBeanBean ebb, Class beanClass) {
      return ebb instanceof SessionBeanBean && this.getWseeAnnotationProcessor() != null && this.getWseeAnnotationProcessor().hasWSAnnotation(beanClass);
   }

   private Collection getWebServiceMethods(EnterpriseBeanBean ebb, Class beanClass) throws ErrorCollectionException {
      Class sei = null;
      SessionBeanBean session = (SessionBeanBean)ebb;
      if (session.getServiceEndpoint() != null) {
         try {
            sei = this.classLoader.loadClass(session.getServiceEndpoint());
         } catch (ClassNotFoundException var7) {
            Loggable log = EJBLogger.logCannotFoundServiceEndPointClassLoggable(session.getServiceEndpoint());
            this.addFatalProcessingError(log.getMessage());
         }
      }

      WseeAnnotationProcessor wseeAnnotationProcessor = this.getWseeAnnotationProcessor();
      return (Collection)(wseeAnnotationProcessor == null ? new ArrayList() : wseeAnnotationProcessor.getWebServiceMethods(beanClass, sei));
   }

   private Method findAnnotatedTimeoutMethod(Class beanClass) throws ComplianceException {
      Collection ms = this.findAnnotatedMethods(beanClass, Timeout.class, true);
      TimeoutCheckHelper.validateOnlyOneTimeoutMethod(ms);
      Iterator var3 = ms.iterator();
      if (var3.hasNext()) {
         Method m = (Method)var3.next();
         TimeoutCheckHelper.validateTimeoutMethodIsejbTimeout(beanClass, m);
         return m;
      } else {
         return null;
      }
   }

   private Set getLifecycleMethods(Class beanClass, EnterpriseBeanBean bean) {
      Set retVal = new HashSet();
      retVal.addAll(this.getLifecycleMethods(beanClass, bean.getPostConstructs()));
      retVal.addAll(this.getLifecycleMethods(beanClass, bean.getPreDestroys()));
      if (bean instanceof SessionBeanBean) {
         SessionBeanBean sbb = (SessionBeanBean)bean;
         if ("Stateful".equals(sbb.getSessionType())) {
            retVal.addAll(this.getLifecycleMethods(beanClass, sbb.getPostActivates()));
            retVal.addAll(this.getLifecycleMethods(beanClass, sbb.getPrePassivates()));
         }
      }

      return retVal;
   }

   private Set getLifecycleMethods(Class beanClass, LifecycleCallbackBean[] lcbs) {
      if (lcbs != null && lcbs.length != 0) {
         Set retVal = new HashSet();
         LifecycleCallbackBean[] var4 = lcbs;
         int var5 = lcbs.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            LifecycleCallbackBean lcb = var4[var6];
            if (beanClass.getName().equals(lcb.getLifecycleCallbackClass())) {
               try {
                  retVal.add(beanClass.getDeclaredMethod(lcb.getLifecycleCallbackMethod()));
               } catch (NoSuchMethodException var9) {
               }
            }
         }

         return retVal;
      } else {
         return Collections.emptySet();
      }
   }

   private List getTimeoutMethods(Class beanClass, EnterpriseBeanBean bean) {
      List retVal = new LinkedList();

      try {
         Method userTimeout = this.findAnnotatedTimeoutMethod(beanClass);
         if (userTimeout != null) {
            retVal.add(userTimeout);
         } else {
            userTimeout = getTimeoutMethodByDD(beanClass, bean);
            if (userTimeout != null) {
               retVal.add(userTimeout);
            } else if (TimedObject.class.isAssignableFrom(beanClass)) {
               try {
                  userTimeout = beanClass.getMethod("ejbTimeout", Timer.class);
               } catch (NoSuchMethodException var6) {
                  return null;
               }

               retVal.add(userTimeout);
            }
         }

         retVal.addAll(getScheduledMethodsByDD(beanClass, bean));
         retVal.addAll(this.findAnnotatedMethods(beanClass, Schedule.class, true));
         return retVal;
      } catch (ComplianceException var7) {
         return null;
      }
   }

   private static Method getTimeoutMethodByDD(Class beanClass, EnterpriseBeanBean bean) throws ComplianceException {
      NamedMethodBean nmb = null;
      if (bean instanceof SessionBeanBean) {
         nmb = ((SessionBeanBean)bean).getTimeoutMethod();
      } else {
         if (!(bean instanceof MessageDrivenBeanBean)) {
            return null;
         }

         nmb = ((MessageDrivenBeanBean)bean).getTimeoutMethod();
      }

      if (nmb == null) {
         return null;
      } else {
         Method retVal = InterceptorHelper.getTimeoutMethodFromDD(nmb, beanClass);
         TimeoutCheckHelper.validateTimeoutMethodExistsInBC(retVal, beanClass, nmb.getMethodName());
         return retVal;
      }
   }

   private static List getScheduledMethodsByDD(Class beanClass, EnterpriseBeanBean bean) throws ComplianceException {
      List retVal = new LinkedList();
      TimerBean[] autoTimerBeans = null;
      if (bean instanceof SessionBeanBean) {
         autoTimerBeans = ((SessionBeanBean)bean).getTimers();
      } else {
         if (!(bean instanceof MessageDrivenBeanBean)) {
            return retVal;
         }

         autoTimerBeans = ((MessageDrivenBeanBean)bean).getTimers();
      }

      if (autoTimerBeans == null) {
         return retVal;
      } else {
         TimerBean[] var4 = autoTimerBeans;
         int var5 = autoTimerBeans.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            TimerBean autoTimerBean = var4[var6];
            NamedMethodBean nmb = autoTimerBean.getTimeoutMethod();
            if (nmb != null) {
               Method autoTimer = InterceptorHelper.getTimeoutMethodFromDD(nmb, beanClass);
               TimeoutCheckHelper.validateTimeoutMethodExistsInBC(autoTimer, beanClass, nmb.getMethodName());
               retVal.add(autoTimer);
            }
         }

         return retVal;
      }
   }

   private void processMessageDrivenAnnotations(MessageDrivenBeanBean mdb, Map bindingToIterceptor) throws ClassNotFoundException, ErrorCollectionException {
      Class beanClass = this.loadBeanClass(mdb.getEjbClass(), mdb.getEjbName());
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Processing annotations MessageDriven bean class " + beanClass.getName());
      }

      weblogic.logging.Loggable l;
      if (beanClass.isAnnotationPresent(MessageDriven.class)) {
         for(Class superClass = beanClass.getSuperclass(); !superClass.equals(Object.class); superClass = superClass.getSuperclass()) {
            if (superClass.isAnnotationPresent(MessageDriven.class)) {
               l = EJBLogger.logMDBWithMDBParentLoggable(superClass.getName(), mdb.getEjbClass().toString());
               this.addProcessingError(l.getMessage());
               break;
            }
         }
      }

      this.recordComponentClass(beanClass);
      MessageDriven md = (MessageDriven)beanClass.getAnnotation(MessageDriven.class);
      if (md != null) {
         if (!this.isSet("MessagingType", mdb) && md.messageListenerInterface() != Object.class) {
            mdb.setMessagingType(md.messageListenerInterface().getName());
         }

         this.processActivationConfigProperties(mdb, md.activationConfig());
         if (!this.isSet("MappedName", mdb) && md.mappedName().length() > 0) {
            mdb.setMappedName(md.mappedName());
         }
      }

      if (mdb.getMessagingType() == null) {
         if (MessageListener.class.isAssignableFrom(beanClass)) {
            mdb.setMessagingType(MessageListener.class.getName());
         } else {
            Set interfaces = this.getImplementedInterfaces(beanClass);
            if (interfaces.size() == 1) {
               mdb.setMessagingType(((Class)interfaces.iterator().next()).getName());
            }
         }
      }

      if (mdb.getMessagingType() == null) {
         l = EJBLogger.logNoMessageListenerSpecifiedForMDBLoggable(mdb.getEjbName());
         this.addFatalProcessingError(l.getMessage());
      }

      if (!this.isSet("TransactionType", mdb)) {
         mdb.setTransactionType(this.getTransactionType(beanClass));
      }

      this.processJ2eeAnnotations(beanClass, mdb);

      Method m;
      try {
         Method mInBC = this.findAnnotatedTimeoutMethod(beanClass);
         m = getTimeoutMethodByDD(beanClass, mdb);
         TimeoutCheckHelper.validateTimeoutMethodsIdentical(m, mInBC);
         if (m == null && mInBC != null) {
            this.populateMethodBean(mdb.createTimeoutMethod(), mInBC);
         }
      } catch (ComplianceException var7) {
         throw new ErrorCollectionException(var7);
      }

      Iterator var11;
      if (mdb.getAroundInvokes().length == 0) {
         var11 = this.findAnnotatedMethods(beanClass, AroundInvoke.class, true).iterator();

         while(var11.hasNext()) {
            m = (Method)var11.next();
            this.populateAroundInvokeBean(mdb.createAroundInvoke(), m);
         }
      }

      if (mdb.getAroundTimeouts().length == 0) {
         var11 = this.findAnnotatedMethods(beanClass, AroundTimeout.class, true).iterator();

         while(var11.hasNext()) {
            m = (Method)var11.next();
            this.populateAroundTimeoutBean(mdb.createAroundTimeout(), m);
         }
      }

      this.processRunAs(beanClass, (DescriptorBean)mdb);
      this.processAssemblyDescriptor(mdb, beanClass, this.getInterfaceNames(mdb), bindingToIterceptor, false);
   }

   private void processActivationConfigProperties(MessageDrivenBeanBean mdb, ActivationConfigProperty[] props) {
      if (props.length != 0) {
         ActivationConfigBean ac = mdb.getActivationConfig();
         if (ac == null) {
            ac = mdb.createActivationConfig();
         }

         ActivationConfigProperty[] var4 = props;
         int var5 = props.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ActivationConfigProperty prop = var4[var6];
            boolean needToAdd = true;
            String propertyName = prop.propertyName();
            ActivationConfigPropertyBean[] var10 = ac.getActivationConfigProperties();
            int var11 = var10.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               ActivationConfigPropertyBean acp = var10[var12];
               if (acp.getActivationConfigPropertyName().equalsIgnoreCase(propertyName)) {
                  needToAdd = false;
                  break;
               }
            }

            if (needToAdd) {
               ActivationConfigPropertyBean acp = ac.createActivationConfigProperty();
               acp.setActivationConfigPropertyName(prop.propertyName());
               acp.setActivationConfigPropertyValue(prop.propertyValue());
            }
         }

      }
   }

   private String getTransactionType(Class beanClass) {
      TransactionManagement tm = (TransactionManagement)beanClass.getAnnotation(TransactionManagement.class);
      return tm != null && tm.value() != TransactionManagementType.CONTAINER ? "Bean" : "Container";
   }

   private String getConcurrencyManagement(Class clazz) {
      ConcurrencyManagement cm = (ConcurrencyManagement)clazz.getAnnotation(ConcurrencyManagement.class);
      return cm != null && cm.value() == ConcurrencyManagementType.BEAN ? "Bean" : "Container";
   }

   private void fillMethodBean(NamedMethodBean nmb, Method m) {
      nmb.setMethodName(m.getName());
      MethodParamsBean paramsBean = nmb.createMethodParams();
      Class[] var4 = m.getParameterTypes();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Class param = var4[var6];
         paramsBean.addMethodParam(param.getCanonicalName());
      }

   }

   private void fillMethodBean(weblogic.j2ee.descriptor.wl.MethodBean mb, String ejbName, Method m) {
      mb.setEjbName(ejbName);
      mb.setMethodName(m.getName());
      weblogic.j2ee.descriptor.wl.MethodParamsBean paramsBean = mb.createMethodParams();
      Class[] var5 = m.getParameterTypes();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Class param = var5[var7];
         paramsBean.addMethodParam(param.getCanonicalName());
      }

   }

   private void fillMethodBean(MethodBean method, String ejbName, Method m) {
      method.setEjbName(ejbName);
      method.setMethodName(m.getName());
      MethodParamsBean methodParams = method.createMethodParams();
      Class[] var5 = m.getParameterTypes();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Class param = var5[var7];
         methodParams.addMethodParam(param.getCanonicalName());
      }

   }

   private String getTransactionAttributeAsString(TransactionAttributeType ta) {
      switch (ta) {
         case MANDATORY:
            return "Mandatory";
         case REQUIRED:
            return "Required";
         case REQUIRES_NEW:
            return "RequiresNew";
         case SUPPORTS:
            return "Supports";
         case NOT_SUPPORTED:
            return "NotSupported";
         case NEVER:
            return "Never";
         default:
            throw new IllegalArgumentException();
      }
   }

   private Set getImplementedInterfaces(Class bean) {
      Set ifaceSet = new HashSet();
      Class[] var3 = bean.getInterfaces();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class iface = var3[var5];
         if (iface != Serializable.class && iface != Externalizable.class && !iface.getName().startsWith("javax.")) {
            ifaceSet.add(iface);
         }
      }

      return ifaceSet;
   }

   private void validateNameAnnotation(String name, Map ejbNames, String[] info) throws ComplianceException {
      String[] bean = (String[])ejbNames.get(name);
      if (bean != null) {
         throw new ComplianceException(EJBComplianceTextFormatter.getInstance().EJB_ANNOTATION_VALUE_IS_DUPLICATE(name, bean[0], bean[1], info[0], info[1]));
      } else {
         ejbNames.put(name, info);
      }
   }

   private void validateComponentDefiningAnnotation(Set classes) throws ComplianceException {
      Iterator var2 = classes.iterator();

      Class beanClass;
      Set componentDefiningAnnotations;
      do {
         if (!var2.hasNext()) {
            return;
         }

         beanClass = (Class)var2.next();
         componentDefiningAnnotations = this.getComponentDefiningAnnotations(beanClass);
      } while(componentDefiningAnnotations.size() <= 1);

      throw new ComplianceException(EJBComplianceTextFormatter.getInstance().EJB_COMPONENT_DEFINING_ANNOTATION_INVALID(componentDefiningAnnotations.toString(), beanClass.getSimpleName()));
   }

   private Set getComponentDefiningAnnotations(Class beanClass) {
      Set componentDefiningAnnotations = new HashSet();
      Class[] annos = new Class[]{Stateless.class, Stateful.class, Singleton.class, MessageDriven.class};
      Class[] var4 = annos;
      int var5 = annos.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Class anno = var4[var6];
         if (beanClass.isAnnotationPresent(anno)) {
            componentDefiningAnnotations.add(anno.getSimpleName());
         }
      }

      return componentDefiningAnnotations;
   }

   public void processWLSAnnotations() throws ClassNotFoundException, ErrorCollectionException {
      EjbJarBean ejbJar = this.desc.getEjbJarBean();
      if (ejbJar != null) {
         SessionBeanBean[] var2 = ejbJar.getEnterpriseBeans().getSessions();
         int var3 = var2.length;

         int var4;
         for(var4 = 0; var4 < var3; ++var4) {
            SessionBeanBean session = var2[var4];
            EnterpriseBeanType type = null;
            if ("Stateless".equals(session.getSessionType())) {
               type = EjbAnnotationProcessor.EnterpriseBeanType.STATELESS;
            } else if ("Stateful".equals(session.getSessionType())) {
               type = EjbAnnotationProcessor.EnterpriseBeanType.STATEFUL;
            } else {
               if (!"Singleton".equals(session.getSessionType())) {
                  throw new AssertionError("Unknown session bean type : " + type);
               }

               type = EjbAnnotationProcessor.EnterpriseBeanType.SINGLETON;
            }

            Class cls = Class.forName(session.getEjbClass(), false, this.classLoader);
            this.processWLSAnnotations(cls, this.desc.getWeblogicEjbJarBean(), session, type);
         }

         MessageDrivenBeanBean[] var8 = ejbJar.getEnterpriseBeans().getMessageDrivens();
         var3 = var8.length;

         for(var4 = 0; var4 < var3; ++var4) {
            MessageDrivenBeanBean mdb = var8[var4];
            Class cls = Class.forName(mdb.getEjbClass(), false, this.classLoader);
            this.processWLSAnnotations(cls, this.desc.getWeblogicEjbJarBean(), mdb, EjbAnnotationProcessor.EnterpriseBeanType.MESSAGE_DRIVEN);
         }

      }
   }

   private void processWLSAnnotations(Class cls, WeblogicEjbJarBean wlEjbJar, EnterpriseBeanBean bean, EnterpriseBeanType type) throws ErrorCollectionException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Processing WebLogic annotations on bean class " + cls.getName());
      }

      this.processCallByReference(cls, bean.getEjbName(), wlEjbJar, type);
      this.processJNDIName(cls, bean, wlEjbJar, type);
      this.processTransactionTimeoutSeconds(cls, bean.getEjbName(), wlEjbJar, type);
      this.processAllowRemoveDuringTransaction(cls, bean.getEjbName(), wlEjbJar, type);
      this.processMessageDestinationConfiguration(cls, bean.getEjbName(), wlEjbJar, type);
      this.processJMSClientID(cls, bean.getEjbName(), wlEjbJar, type);
      this.processDisableWarnings(cls, wlEjbJar, type);
      Set ifaces;
      if (type == EjbAnnotationProcessor.EnterpriseBeanType.MESSAGE_DRIVEN) {
         ifaces = this.getBusinessInterfaces(this.getInterfaceNames((MessageDrivenBeanBean)bean));
      } else {
         ifaces = this.getBusinessInterfaces(this.getInterfaceNames((SessionBeanBean)bean));
      }

      Iterator var6 = this.getBusinessMethods(cls, ifaces, bean).iterator();

      while(var6.hasNext()) {
         Method meth = (Method)var6.next();
         this.processIdempotent(meth, bean.getEjbName(), wlEjbJar, type);
         this.processTransactionIsolation(meth, bean.getEjbName(), wlEjbJar, type);
         this.processSkipStateReplication(meth, bean.getEjbName(), wlEjbJar, type);
      }

      if (!EJBComplianceChecker.isNeedCheck) {
         this.throwProcessingErrors();
      }
   }

   private void processCallByReference(Class cls, String name, WeblogicEjbJarBean wlEjbJar, EnterpriseBeanType type) {
      CallByReference cbr = (CallByReference)this.assertContext(cls, CallByReference.class, type);
      if (cbr != null && !this.isSet("EnableCallByReference", this.getWLBean(name, wlEjbJar))) {
         this.getWLBean(name, wlEjbJar).setEnableCallByReference(true);
      }

   }

   private void processJNDIName(Class cls, EnterpriseBeanBean bean, WeblogicEjbJarBean wlEjbJar, EnterpriseBeanType type) throws ErrorCollectionException {
      if (!(bean instanceof SessionBeanBean)) {
         this.assertContext(cls, JNDIName.class, type);
         this.assertContext(cls, JNDINames.class, type);
      } else {
         Set businessRemotes = this.getBusinessInterfaces(Arrays.asList(((SessionBeanBean)bean).getBusinessRemotes()));
         Set businessLocals = this.getBusinessInterfaces(Arrays.asList(((SessionBeanBean)bean).getBusinessLocals()));
         Set businesses = new HashSet();
         businesses.addAll(businessRemotes);
         businesses.addAll(businessLocals);
         Map jndiNames = new HashMap();
         JNDIName toBeAdded = (JNDIName)this.assertContext(cls, JNDIName.class, type);
         this.handleJNDINameOnBeanClass(jndiNames, businesses, toBeAdded, cls, this.hasNoInterfaceView(bean, cls));
         JNDINames names = (JNDINames)this.assertContext(cls, JNDINames.class, type);
         if (names != null) {
            JNDIName[] var11 = names.value();
            int var12 = var11.length;

            for(int var13 = 0; var13 < var12; ++var13) {
               JNDIName jndiName = var11[var13];
               this.handleJNDINameOnBeanClass(jndiNames, businesses, jndiName, cls, this.hasNoInterfaceView(bean, cls));
            }
         }

         this.handleJNDINameOnBI(jndiNames, businesses, cls);
         this.addJNDINames(jndiNames, bean.getEjbName(), wlEjbJar);
      }
   }

   private void checkDupJNDINameOnInterface(Class intfTobeChecked, String nameTobeChecked, String beanClassName) {
      JNDIName iface = (JNDIName)intfTobeChecked.getAnnotation(JNDIName.class);
      if (iface != null && !iface.value().equals(nameTobeChecked)) {
         Loggable l = EJBLogger.logDuplicateJNDINameAnnotationEJB31Loggable(intfTobeChecked.getName(), intfTobeChecked.getName(), beanClassName);
         this.addProcessingError(l.getMessage());
      }

   }

   private void handleJNDINameOnBeanClass(Map names, Set businessIntfs, JNDIName toBeAdded, Class beanCls, boolean hasNoInterfaceView) {
      if (names != null && toBeAdded != null) {
         Class businessIntf;
         if (toBeAdded.className() != null) {
            Iterator var6 = businessIntfs.iterator();

            while(var6.hasNext()) {
               businessIntf = (Class)var6.next();
               if (businessIntf.equals(toBeAdded.className())) {
                  this.checkDupJNDINameOnInterface(businessIntf, toBeAdded.value(), beanCls.getName());
               }
            }
         }

         Class key = null;
         if (Object.class.equals(toBeAdded.className())) {
            if (businessIntfs.size() > 0 && hasNoInterfaceView) {
               Loggable l = EJBLogger.logNoJNDINameOnMultiInterfaceImplEJB31Loggable(beanCls.getName());
               this.addProcessingError(l.getMessage());
            } else if (businessIntfs.size() == 1) {
               businessIntf = (Class)businessIntfs.iterator().next();
               this.checkDupJNDINameOnInterface(businessIntf, toBeAdded.value(), beanCls.getName());
               key = businessIntf;
            } else if (hasNoInterfaceView) {
               key = beanCls;
            }
         } else {
            key = toBeAdded.className();
         }

         if (key != null) {
            this.addToJNDIMap(names, key, toBeAdded, beanCls, beanCls);
         }
      }
   }

   private void handleJNDINameOnBI(Map names, Set businessIntfs, Class beanCls) {
      if (names != null && businessIntfs != null) {
         Iterator var4 = businessIntfs.iterator();

         while(var4.hasNext()) {
            Class bizIntf = (Class)var4.next();
            JNDIName toBeAdded = (JNDIName)bizIntf.getAnnotation(JNDIName.class);
            if (toBeAdded != null) {
               this.addToJNDIMap(names, bizIntf, toBeAdded, bizIntf, beanCls);
            }
         }

      }
   }

   private void addToJNDIMap(Map names, Class key, JNDIName toBeAdded, Class curCls, Class beanCls) {
      if (names != null && key != null) {
         if (names.containsKey(key) && !((String)names.get(key)).equals(toBeAdded.value())) {
            Loggable l = EJBLogger.logDuplicateJNDINameAnnotationEJB31Loggable(key.getName(), curCls.getName(), beanCls.getName());
            this.addProcessingError(l.getMessage());
         }

         names.put(key, toBeAdded.value());
      }
   }

   private void addJNDINames(Map jndiMap, String ejbname, WeblogicEjbJarBean wlEjbJar) {
      if (jndiMap != null) {
         Set businessIntfs = new HashSet();
         businessIntfs.addAll(jndiMap.keySet());
         WeblogicEnterpriseBeanBean wlbean = this.getWLBean(ejbname, wlEjbJar);
         JndiBindingBean[] var6 = wlbean.getJndiBinding();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            JndiBindingBean jndi = var6[var8];
            String className = jndi.getClassName();
            Iterator var11 = jndiMap.entrySet().iterator();

            while(var11.hasNext()) {
               Map.Entry entry = (Map.Entry)var11.next();
               Class c = (Class)entry.getKey();
               if (c.getName().equals(className)) {
                  if (!this.isSet("JndiName", jndi)) {
                     jndi.setJndiName((String)entry.getValue());
                  }

                  businessIntfs.remove(c);
               }
            }
         }

         Iterator var14 = businessIntfs.iterator();

         while(var14.hasNext()) {
            Class c = (Class)var14.next();
            JndiBindingBean bindingBean = wlbean.createJndiBinding();
            bindingBean.setClassName(c.getName());
            bindingBean.setJndiName((String)jndiMap.get(c));
         }

      }
   }

   private void processTransactionTimeoutSeconds(Class cls, String name, WeblogicEjbJarBean wlEjbJar, EnterpriseBeanType type) {
      TransactionTimeoutSeconds tts = (TransactionTimeoutSeconds)this.assertContext(cls, TransactionTimeoutSeconds.class, type);
      if (tts != null) {
         TransactionDescriptorBean td = this.getWLBean(name, wlEjbJar).getTransactionDescriptor();
         if (!this.isSet("TransTimeoutSeconds", td)) {
            td.setTransTimeoutSeconds(tts.value());
            tts.value();
         }

      }
   }

   private void processAllowRemoveDuringTransaction(Class cls, String name, WeblogicEjbJarBean wlEjbJar, EnterpriseBeanType type) {
      AllowRemoveDuringTransaction ards = (AllowRemoveDuringTransaction)this.assertContext(cls, AllowRemoveDuringTransaction.class, type);
      if (ards != null) {
         StatefulSessionDescriptorBean ssd = this.getWLBean(name, wlEjbJar).getStatefulSessionDescriptor();
         if (!this.isSet("AllowRemoveDuringTransaction", ssd)) {
            ssd.setAllowRemoveDuringTransaction(true);
         }

      }
   }

   private void processMessageDestinationConfiguration(Class cls, String name, WeblogicEjbJarBean wlEjbJar, EnterpriseBeanType type) {
      MessageDestinationConfiguration mdc = (MessageDestinationConfiguration)this.assertContext(cls, MessageDestinationConfiguration.class, type);
      if (mdc != null) {
         MessageDrivenDescriptorBean mdd = this.getWLBean(name, wlEjbJar).getMessageDrivenDescriptor();
         if (!this.isSet("ConnectionFactoryJNDIName", mdd) && !"".equals(mdc.connectionFactoryJNDIName())) {
            mdd.setConnectionFactoryJNDIName(mdc.connectionFactoryJNDIName());
         }

         if (!this.isSet("InitialContextFactory", mdd)) {
            mdd.setInitialContextFactory(mdc.initialContextFactory().getName());
         }

         if (!this.isSet("ProviderUrl", mdd) && !"".equals(mdc.providerURL())) {
            mdd.setProviderUrl(mdc.providerURL());
         }

      }
   }

   private void processJMSClientID(Class cls, String name, WeblogicEjbJarBean wlEjbJar, EnterpriseBeanType type) {
      JMSClientID jci = (JMSClientID)this.assertContext(cls, JMSClientID.class, type);
      if (jci != null) {
         MessageDrivenDescriptorBean mdd = this.getWLBean(name, wlEjbJar).getMessageDrivenDescriptor();
         if (!this.isSet("JmsClientId", mdd)) {
            mdd.setJmsClientId(jci.value());
         }

         if (!this.isSet("GenerateUniqueJmsClientId", mdd)) {
            mdd.setGenerateUniqueJmsClientId(jci.generateUniqueID());
         }

      }
   }

   private void processDisableWarnings(Class cls, WeblogicEjbJarBean wlEjbJar, EnterpriseBeanType type) {
      DisableWarnings dw = (DisableWarnings)this.assertContext(cls, DisableWarnings.class, type);
      if (dw != null) {
         if (this.disableWarningsIsInXML == null) {
            this.disableWarningsIsInXML = this.isSet("DisableWarnings", wlEjbJar);
         }

         if (!this.disableWarningsIsInXML) {
            WarningCode[] var5 = dw.value();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               WarningCode wc = var5[var7];
               wlEjbJar.addDisableWarning(wc.getWeblogicCode());
            }
         }
      }

   }

   private void processIdempotent(Method meth, String name, WeblogicEjbJarBean wlEjbJar, EnterpriseBeanType type) {
      Idempotent idempotent = (Idempotent)this.assertContext(meth, Idempotent.class, type);
      if (idempotent != null) {
         IdempotentMethodsBean imb = wlEjbJar.getIdempotentMethods();
         if (imb == null) {
            imb = wlEjbJar.createIdempotentMethods();
         }

         weblogic.j2ee.descriptor.wl.MethodBean mb = this.findMethod(meth, name, imb.getMethods());
         if (mb == null) {
            mb = imb.createMethod();
            this.fillMethodBean(mb, name, meth);
         }

         RetryMethodsOnRollbackBean[] rmrbs = wlEjbJar.getRetryMethodsOnRollbacks();
         mb = null;
         if (rmrbs != null) {
            RetryMethodsOnRollbackBean[] var9 = rmrbs;
            int var10 = rmrbs.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               RetryMethodsOnRollbackBean rmrb = var9[var11];
               mb = this.findMethod(meth, name, rmrb.getMethods());
               if (mb != null) {
                  break;
               }
            }
         }

         if (mb == null) {
            RetryMethodsOnRollbackBean rmrb = wlEjbJar.createRetryMethodsOnRollback();
            rmrb.setRetryCount(idempotent.retryOnRollbackCount());
            mb = rmrb.createMethod();
            this.fillMethodBean(mb, name, meth);
         }

      }
   }

   private void processTransactionIsolation(Method meth, String name, WeblogicEjbJarBean wlEjbJar, EnterpriseBeanType type) {
      TransactionIsolation isolation = (TransactionIsolation)this.assertContext(meth, TransactionIsolation.class, type);
      if (isolation != null) {
         TransactionIsolationBean[] tibs = wlEjbJar.getTransactionIsolations();
         weblogic.j2ee.descriptor.wl.MethodBean mb = null;
         if (tibs != null) {
            TransactionIsolationBean[] var8 = tibs;
            int var9 = tibs.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               TransactionIsolationBean tib = var8[var10];
               mb = this.findMethod(meth, name, tib.getMethods());
               if (mb != null) {
                  break;
               }
            }
         }

         if (mb == null) {
            TransactionIsolationBean tib = wlEjbJar.createTransactionIsolation();
            tib.setIsolationLevel(isolation.value().getWeblogicIsolationString());
            this.fillMethodBean(tib.createMethod(), name, meth);
         }

      }
   }

   private void processSkipStateReplication(Method meth, String name, WeblogicEjbJarBean wlEjbJar, EnterpriseBeanType type) {
      SkipStateReplication ssr = (SkipStateReplication)this.assertContext(meth, SkipStateReplication.class, type);
      if (ssr != null) {
         SkipStateReplicationMethodsBean ssrmb = wlEjbJar.getSkipStateReplicationMethods();
         if (ssrmb == null) {
            ssrmb = wlEjbJar.createSkipStateReplicationMethods();
         }

         weblogic.j2ee.descriptor.wl.MethodBean mb = this.findMethod(meth, name, ssrmb.getMethods());
         if (mb == null) {
            mb = ssrmb.createMethod();
            this.fillMethodBean(mb, name, meth);
         }

      }
   }

   private Annotation assertContext(Class beanClass, Class annoType, EnterpriseBeanType type) {
      Annotation anno = beanClass.getAnnotation(annoType);
      if (anno != null && !type.validAnnotationTypes.contains(annoType)) {
         Loggable l = EJBLogger.logAnnotationOnInvalidClassLoggable(annoType.getName(), beanClass.getName());
         throw new IllegalStateException(l.getMessage());
      } else {
         return anno;
      }
   }

   private Annotation assertContext(Method meth, Class annoType, EnterpriseBeanType type) {
      Annotation anno = meth.getAnnotation(annoType);
      if (anno != null && !type.validAnnotationTypes.contains(annoType)) {
         Loggable l = EJBLogger.logAnnotationOnInvalidMethodLoggable(annoType.getName(), meth.getDeclaringClass().getName(), meth.getName());
         throw new IllegalStateException(l.getMessage());
      } else {
         return anno;
      }
   }

   private WeblogicEnterpriseBeanBean getWLBean(String name, WeblogicEjbJarBean wlEjbJar) {
      WeblogicEnterpriseBeanBean bean = wlEjbJar.lookupWeblogicEnterpriseBean(name);
      if (bean == null) {
         bean = wlEjbJar.createWeblogicEnterpriseBean();
         bean.setEjbName(name);
      }

      return bean;
   }

   private weblogic.j2ee.descriptor.wl.MethodBean findMethod(Method meth, String ejbName, weblogic.j2ee.descriptor.wl.MethodBean[] methods) {
      weblogic.j2ee.descriptor.wl.MethodBean[] var4 = methods;
      int var5 = methods.length;

      label43:
      for(int var6 = 0; var6 < var5; ++var6) {
         weblogic.j2ee.descriptor.wl.MethodBean mb = var4[var6];
         if (ejbName.equals(mb.getEjbName()) && meth.getName().equals(mb.getMethodName()) && mb.getMethodParams() != null) {
            String[] params = mb.getMethodParams().getMethodParams();
            if (params != null) {
               Class[] paramTypes = meth.getParameterTypes();
               if (params.length == paramTypes.length) {
                  for(int i = 0; i < params.length; ++i) {
                     if (!params[i].equals(paramTypes[i].getCanonicalName())) {
                        continue label43;
                     }
                  }

                  return mb;
               }
            }
         }
      }

      return null;
   }

   private Class loadBeanClass(String className, String ejbName) throws ErrorCollectionException, ClassNotFoundException {
      if (className == null || className.trim().length() == 0) {
         this.addProcessingError("In the ejb-jar.xml, the EJB " + ejbName + " does not specify an ejb-class value and no annotated EJB class was found in the ejb-jar file with a matching ejb-name. The ejb-class value must be specified.");
         this.throwProcessingErrors();
      }

      return this.classLoader.loadClass(className);
   }

   protected void addBeanInterfaceNotSetError(J2eeClientEnvironmentBean eg) {
      String name;
      weblogic.logging.Loggable log;
      if (eg instanceof EnterpriseBeanBean) {
         name = ((EnterpriseBeanBean)eg).getEjbClass();
         log = EJBLogger.logNoSetBeanInterfaceForBeanLoggable(name);
         this.addProcessingError(log.getMessage());
      } else if (eg instanceof InterceptorBean) {
         name = ((InterceptorBean)eg).getInterceptorClass();
         log = EJBLogger.logNoSetBeanInterfaceForInterceptorLoggable(name);
         this.addProcessingError(log.getMessage());
      }

   }

   protected void perhapsDeclareRunAs(DescriptorBean identityBean, String roleName) {
      EnterpriseBeanBean eb = (EnterpriseBeanBean)identityBean;
      SecurityIdentityBean si = eb.getSecurityIdentity();
      if (si == null) {
         si = eb.createSecurityIdentity();
         si.createRunAs().setRoleName(roleName);
      }

      this.perhapsDeclareSecurityRoles(Arrays.asList(roleName), (EjbJarBean)identityBean.getDescriptor().getRootBean());
   }

   protected void perhapsDeclareRoles(DescriptorBean identityBean, String[] roles) {
      SessionBeanBean session = (SessionBeanBean)identityBean;
      Set roleRefs = new HashSet();
      SecurityRoleRefBean[] var5 = session.getSecurityRoleRefs();
      int var6 = var5.length;

      int var7;
      for(var7 = 0; var7 < var6; ++var7) {
         SecurityRoleRefBean srr = var5[var7];
         roleRefs.add(srr.getRoleName());
      }

      EjbJarBean ejbJarBean = (EjbJarBean)identityBean.getDescriptor().getRootBean();
      this.perhapsDeclareSecurityRoles(Arrays.asList(roles), ejbJarBean);
      String[] var12 = roles;
      var7 = roles.length;

      for(int var13 = 0; var13 < var7; ++var13) {
         String role = var12[var13];
         if (!roleRefs.contains(role)) {
            SecurityRoleRefBean srr = session.createSecurityRoleRef();
            srr.setRoleName(role);
            roleRefs.add(role);
         }
      }

   }

   public static void main(String[] argv) throws Exception {
      EjbDescriptorBean result = new EjbDescriptorBean(true);
      VirtualJarFile vJar = VirtualJarFactory.createVirtualJar(new File(argv[0]));
      ClasspathClassFinder2 cf = new ClasspathClassFinder2(argv[0]);
      GenericClassLoader cl = new GenericClassLoader(cf);
      EjbAnnotationProcessor ap = new EjbAnnotationProcessor(cl, result);
      ap.processAnnotations(EJBJarUtils.getIdentityAnnotatedClasses(vJar, cl), (Set)null);
      System.out.println();
      System.out.println();
      DescriptorBean bean = (DescriptorBean)result.getEjbJarBean();
      bean.getDescriptor().toXML(System.out);
   }

   static {
      debugLogger = EJBDebugService.metadataLogger;
   }

   static enum EnterpriseBeanType {
      STATELESS(new Class[]{TransactionTimeoutSeconds.class, DisableWarnings.class, CallByReference.class, JNDIName.class, Idempotent.class, TransactionIsolation.class}),
      STATEFUL(new Class[]{TransactionTimeoutSeconds.class, DisableWarnings.class, CallByReference.class, JNDIName.class, AllowRemoveDuringTransaction.class, Idempotent.class, TransactionIsolation.class, SkipStateReplication.class}),
      SINGLETON(new Class[]{TransactionTimeoutSeconds.class, DisableWarnings.class, CallByReference.class, JNDIName.class, Idempotent.class, TransactionIsolation.class}),
      MESSAGE_DRIVEN(new Class[]{TransactionTimeoutSeconds.class, DisableWarnings.class, MessageDestinationConfiguration.class, JMSClientID.class, Idempotent.class, TransactionIsolation.class});

      final Collection validAnnotationTypes;

      private EnterpriseBeanType(Class... validAnnotationTypes) {
         this.validAnnotationTypes = Collections.unmodifiableSet(new HashSet(Arrays.asList(validAnnotationTypes)));
      }
   }

   private interface EjbTimerBeanFunctions {
      TimerBean[] getTimers();

      TimerBean createTimer();
   }

   static final class SortedClassSet extends TreeSet {
      SortedClassSet() {
         super(new Comparator() {
            public int compare(Class c1, Class c2) {
               return c1.getCanonicalName().compareTo(c2.getCanonicalName());
            }
         });
      }
   }
}
