package weblogic.application.naming;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import javax.enterprise.deploy.shared.ModuleType;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleContext;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.spi.BeanInfo;
import weblogic.ejb.spi.ClientDrivenBeanInfo;
import weblogic.ejb.spi.DeploymentInfo;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.InjectionTargetBean;
import weblogic.j2ee.descriptor.MessageDestinationBean;
import weblogic.j2ee.descriptor.PersistenceUnitRefBean;
import weblogic.j2ee.descriptor.ResourceRefBean;
import weblogic.j2ee.descriptor.wl.MessageDestinationDescriptorBean;
import weblogic.j2ee.descriptor.wl.ResourceEnvDescriptionBean;
import weblogic.logging.Loggable;
import weblogic.utils.StringUtils;
import weblogic.utils.reflect.ReflectUtils;
import weblogic.validation.Bindable;
import weblogic.validation.injection.ValidationManager;

public final class EnvUtils {
   public static final String LINE_SEPARATOR = System.getProperty("line.separator");
   private static final Set VALID_ENV_ENTRY_TYPES = new HashSet(Arrays.asList(String.class.getName(), Boolean.class.getName(), Integer.class.getName(), Double.class.getName(), Float.class.getName(), Short.class.getName(), Long.class.getName(), Byte.class.getName(), Character.class.getName(), Class.class.getName()));

   public static String transformJNDIName(String jndiName, String appName) {
      if (jndiName == null) {
         return null;
      } else if (appName == null) {
         return jndiName;
      } else {
         return jndiName.indexOf("${APPNAME}") != -1 ? StringUtils.replaceGlobal(jndiName, "${APPNAME}", appName) : jndiName;
      }
   }

   public static Object findObject(String jndiName, Context ctx) {
      Object binder = null;

      try {
         if (jndiName.indexOf(":") > 0 && jndiName.indexOf("java:") == -1) {
            return null;
         } else {
            binder = ctx.lookup(jndiName);
            return binder;
         }
      } catch (NamingException var4) {
         return null;
      }
   }

   public static boolean isResourceShareable(ResourceRefBean resRef) {
      return resRef.getResSharingScope() == null || !resRef.getResSharingScope().equalsIgnoreCase("Unshareable");
   }

   public static boolean isContainerAuthEnabled(ResourceRefBean rr) {
      return rr.getResAuth() != null && rr.getResAuth().equalsIgnoreCase("Container");
   }

   public static void createIntermediateContexts(Context ctx, String name) throws NamingException {
      Context currentContext = ctx;
      StringTokenizer tokenizer = new StringTokenizer(name, "/");

      while(tokenizer.hasMoreTokens()) {
         String token = tokenizer.nextToken();
         if (!token.equals("") && tokenizer.hasMoreTokens()) {
            try {
               currentContext = currentContext.createSubcontext(token);
            } catch (NamingException var6) {
               currentContext = (Context)currentContext.lookup(token);
            }
         }
      }

   }

   public static void destroyContextBindings(Context ctx, DebugLogger debugLogger) throws NamingException {
      if (debugLogger.isDebugEnabled()) {
         String ns;
         try {
            ns = ctx.getNameInNamespace();
         } catch (OperationNotSupportedException var4) {
            ns = "Context of class " + ctx.getClass().getName() + ", but of unknown name";
         }

         debugLogger.debug("Destroying context bindings for " + ns);
      }

      String name;
      for(NamingEnumeration bindings = ctx.listBindings(""); bindings.hasMoreElements(); ctx.unbind(name)) {
         name = ((Binding)bindings.next()).getName();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Unbinding " + name);
         }
      }

   }

   public static String dumpContext(Context ctx) {
      StringBuilder sb = new StringBuilder(LINE_SEPARATOR);
      Set dumpedSet = new HashSet();
      dumpContext(ctx, sb, dumpedSet);
      return sb.toString();
   }

   private static void dumpContext(Context ctx, StringBuilder sb, Set dumpedSet) {
      try {
         String ns = ctx.getNameInNamespace();
         NamingEnumeration bindings = ctx.listBindings("");

         while(true) {
            while(bindings.hasMoreElements()) {
               Binding b = (Binding)bindings.next();
               String name = b.getName();
               Object obj = b.getObject();
               if (obj instanceof Context) {
                  Context c = (Context)obj;
                  if (c.getNameInNamespace().startsWith(ns) && !dumpedSet.contains(c)) {
                     dumpedSet.add(c);
                     dumpContext(c, sb, dumpedSet);
                     continue;
                  }
               }

               sb.append(ns).append("/").append(name).append(" -> ").append(b.getClassName()).append(LINE_SEPARATOR);
            }

            return;
         }
      } catch (NamingException var9) {
         sb.append("<Error dumping context - " + var9.getMessage() + ">");
      }
   }

   public static Object getValue(EnvEntryBean v) throws EnvironmentException {
      return getValue(v, Thread.currentThread().getContextClassLoader());
   }

   public static Object getValue(EnvEntryBean v, ClassLoader cl) throws EnvironmentException {
      String type = v.getEnvEntryType();
      String value = v.getEnvEntryValue();
      if (type == null) {
         InjectionTargetBean[] injectionTargets = v.getInjectionTargets();
         if (injectionTargets == null || injectionTargets.length <= 0) {
            throw new EnvironmentException("env-entry-type is required if there are no injection targets");
         }

         String targetClassName = injectionTargets[0].getInjectionTargetClass();
         String injectionTargetName = injectionTargets[0].getInjectionTargetName();

         Class injectedType;
         try {
            Class targetClass = cl.loadClass(targetClassName);
            injectedType = targetClass.getDeclaredField(injectionTargetName).getType();
         } catch (Exception var12) {
            throw new AssertionError(var12);
         }

         type = ReflectUtils.getJavaLangType(injectedType.getName());
      }

      Class typeClass;
      try {
         typeClass = cl.loadClass(type);
      } catch (Exception var11) {
         throw new weblogic.utils.AssertionError(var11);
      }

      if (!VALID_ENV_ENTRY_TYPES.contains(type) && !Enum.class.isAssignableFrom(typeClass)) {
         throw new EnvironmentException("Unsupported environment entry type " + type + " declared for environment entry " + v.getEnvEntryName());
      } else if (String.class.getName().equals(type)) {
         return value == null ? "" : value;
      } else if (Character.class.getName().equals(type)) {
         if (value != null && value.length() > 0) {
            return new Character(value.charAt(0));
         } else {
            Loggable l = J2EELogger.logCharEnvEntryHasLengthZeroLoggable();
            throw new EnvironmentException(l.getMessage());
         }
      } else if (Enum.class.isAssignableFrom(typeClass)) {
         return Enum.valueOf(typeClass, value);
      } else {
         try {
            if (Class.class.getName().equals(type)) {
               return cl.loadClass(value);
            } else {
               Constructor ctor = typeClass.getConstructor(String.class);
               return ctor.newInstance(value);
            }
         } catch (Exception var10) {
            throw new weblogic.utils.AssertionError(var10);
         }
      }
   }

   public static List computeInterfaces(InjectionTargetBean[] targets, ClassLoader cl) throws EnvironmentException {
      InterfacesList ifaces = new InterfacesList();
      if (targets.length == 0) {
         ifaces.addWithSpecialHandling(EntityManager.class);
      } else {
         for(int i = 0; i < targets.length; ++i) {
            try {
               Class parent = cl.loadClass(targets[i].getInjectionTargetClass());
               Class target = getInjectionType(parent, targets[i].getInjectionTargetName());
               if (target.isInterface()) {
                  ifaces.addWithSpecialHandling(target);
               }

               Enumeration ai = ReflectUtils.allInterfaces(target);

               while(ai.hasMoreElements()) {
                  ifaces.addWithSpecialHandling((Class)ai.nextElement());
               }
            } catch (ClassNotFoundException var7) {
               throw new EnvironmentException("Error loading class: ", var7);
            }
         }
      }

      return ifaces;
   }

   public static String getPersistenceUnitName(String name, PersistenceUnitRegistry puReg, InjectionTargetBean[] targets) throws EnvironmentException {
      if (name != null && !"".equals(name)) {
         return name;
      } else {
         Collection namesInScope = puReg.getPersistenceUnitNames();
         if (namesInScope.size() == 1) {
            return (String)namesInScope.iterator().next();
         } else if (targets != null && targets.length == 1) {
            return targets[0].getInjectionTargetName();
         } else if (targets != null && targets.length != 0) {
            throw new EnvironmentException("PersistenceContext refs defined with multiple injection targets must explicitly name a persistence unit.");
         } else {
            throw new EnvironmentException("PersistenceContext refs defined without any injection targets must explicitly name a persistence unit.");
         }
      }
   }

   public static boolean isJDOPersistenceContext(String pcRefName, InjectionTargetBean[] targets, Collection ifaces, ClassLoader cl) throws EnvironmentException {
      if (ifaces == null) {
         ifaces = computeInterfaces(targets, cl);
      }

      boolean hasEM = ((Collection)ifaces).contains(EntityManager.class);
      boolean hasPM = ((Collection)ifaces).contains(PersistenceManager.class);
      if (hasEM && hasPM) {
         throw new EnvironmentException("Persistence context " + pcRefName + " has injection target(s) that implement both " + EntityManager.class.getName() + " and " + PersistenceManager.class.getName() + ". This is illegal.");
      } else if (!hasEM && !hasPM) {
         throw new EnvironmentException("Persistence context " + pcRefName + " has injection target(s) that implement neither  " + EntityManager.class.getName() + " nor " + PersistenceManager.class.getName() + ". This is illegal.");
      } else {
         return hasPM;
      }
   }

   public static boolean isJDOPersistenceContextFactory(PersistenceUnitRefBean prb, InjectionTargetBean[] targets, Collection ifaces, ClassLoader cl) throws EnvironmentException {
      if (ifaces == null) {
         if (targets.length == 0) {
            return false;
         }

         ifaces = computeInterfaces(targets, cl);
      }

      boolean hasEMF = ((Collection)ifaces).contains(EntityManagerFactory.class);
      boolean hasPMF = ((Collection)ifaces).contains(PersistenceManagerFactory.class);
      if (hasEMF && hasPMF) {
         throw new EnvironmentException("Persistence unit " + prb + " has injection target(s) that implement both " + EntityManagerFactory.class.getName() + " and " + PersistenceManagerFactory.class.getName() + ". This is illegal.");
      } else if (!hasEMF && !hasPMF) {
         throw new EnvironmentException("Persistence unit " + prb + " has injection target(s) that implement neither  " + EntityManagerFactory.class.getName() + " nor " + PersistenceManagerFactory.class.getName() + ". This is illegal.");
      } else {
         return hasPMF;
      }
   }

   public static Class getInjectionTypeForMethod(Class cls, String propName) {
      char start = propName.charAt(0);
      String methodName = ("" + start).toUpperCase() + propName.substring(1);
      methodName = "set" + methodName;
      Method[] methods = cls.getDeclaredMethods();

      for(int i = 0; i < methods.length; ++i) {
         if (methods[i].getName().equals(methodName)) {
            Class[] params = methods[i].getParameterTypes();
            if (params.length == 1) {
               return params[0];
            }
         }
      }

      return null;
   }

   public static Class getInjectionTypeForField(Class cls, String propName) {
      try {
         return cls.getDeclaredField(propName).getType();
      } catch (NoSuchFieldException var3) {
         return null;
      }
   }

   public static Class getInjectionType(Class parent, String targetName) throws EnvironmentException {
      Class type = getInjectionTypeForField(parent, targetName);
      if (type == null) {
         type = getInjectionTypeForMethod(parent, targetName);
      }

      if (type == null) {
         throw new EnvironmentException("Unable to find injection target named: " + targetName + " on class: " + parent);
      } else {
         return type;
      }
   }

   public static String getAppScopedLinkPath(String link, String jarName, Context baseCtx) throws NamingException {
      if (link.indexOf("#") > 0) {
         if (link.startsWith("../")) {
            link = makePathAbsolute(link, jarName);
         }

         return link;
      } else {
         String prefixPath = null;
         int lastSlashIndex = jarName.lastIndexOf(47);
         if (lastSlashIndex != -1) {
            prefixPath = jarName.substring(0, lastSlashIndex);
            jarName = jarName.substring(lastSlashIndex + 1);
            baseCtx = (Context)baseCtx.lookup(prefixPath);
         }

         NamingEnumeration bindings = baseCtx.listBindings("");

         String name;
         do {
            if (!bindings.hasMoreElements()) {
               name = findByName(baseCtx, link);
               if (prefixPath != null) {
                  name = prefixPath + '/' + name;
               }

               return name;
            }

            name = ((Binding)bindings.nextElement()).getName();
         } while(!name.equals(jarName + "#" + link));

         if (prefixPath != null) {
            name = prefixPath + '/' + name;
         }

         return name;
      }
   }

   public static String makePathAbsolute(String relativePath, String jarName) {
      jarName = normalizeJarName(jarName);
      int i = 0;
      int j = jarName.length();

      while(relativePath.regionMatches(i, "../", 0, 3)) {
         i += 3;
         if (j >= 0) {
            j = jarName.lastIndexOf(47, j - 1);
         }
      }

      return jarName.substring(0, j + 1) + relativePath.substring(i);
   }

   protected static String findByName(Context ctx, String ejbLink) throws NamingException {
      NamingEnumeration bindings = ctx.listBindings("");

      String name;
      do {
         if (!bindings.hasMoreElements()) {
            return null;
         }

         name = ((Binding)bindings.nextElement()).getName();
      } while(!name.endsWith("#" + ejbLink));

      return name;
   }

   public static String normalizeJarName(String n) {
      return escapeChars(n, new char[]{'\'', '"', '.'});
   }

   public static String normalizeJNDIName(String n) {
      return escapeChars(n, new char[]{'/', '.'});
   }

   public static String escapeChars(String n, char[] chars) {
      if (n == null) {
         return null;
      } else {
         StringBuilder s = new StringBuilder();

         for(int i = 0; i < n.length(); ++i) {
            char c = n.charAt(i);
            if (c == '\\' && i < n.length() - 1) {
               StringBuilder var10000 = s.append(c);
               ++i;
               var10000.append(n.charAt(i));
            } else {
               for(int j = 0; j < chars.length; ++j) {
                  if (c == chars[j]) {
                     s.append('\\');
                     break;
                  }
               }

               s.append(c);
            }
         }

         return s.toString();
      }
   }

   public static ClientDrivenBeanInfo findInfoByEjbLink(Collection moduleContexts, String applicationId, String moduleId, String moduleURI, String ejbRefName, String ejbLink) throws ReferenceResolutionException {
      if (ejbLink.contains("#")) {
         return findInfoByPath(moduleContexts, moduleURI, ejbRefName, ejbLink);
      } else if (ejbLink.contains("/")) {
         try {
            return findInfoByModuleName(moduleContexts, moduleURI, ejbRefName, ejbLink);
         } catch (ReferenceResolutionException var9) {
            try {
               return findInfo(moduleContexts, applicationId, moduleURI, ejbRefName, ejbLink, (String)null);
            } catch (ReferenceResolutionException var8) {
               throw var9;
            }
         }
      } else {
         ClientDrivenBeanInfo cdbi = findInfoByCurrentModuleId(moduleContexts, applicationId, moduleId, moduleURI, ejbRefName, ejbLink);
         return cdbi != null ? cdbi : findInfo(moduleContexts, applicationId, moduleURI, ejbRefName, ejbLink, (String)null);
      }
   }

   private static ClientDrivenBeanInfo findInfoByPath(Collection mcs, String moduleURI, String ejbRefName, String ejbLink) throws ReferenceResolutionException {
      String absoluteLink = ejbLink;
      if (ejbLink.startsWith("../")) {
         absoluteLink = makePathAbsolute(ejbLink, moduleURI);
      }

      String targetModuleURI = absoluteLink.substring(0, absoluteLink.lastIndexOf(35));
      String targetEjbName = ejbLink.substring(ejbLink.lastIndexOf(35) + 1);
      ModuleContext moduleContext = findModuleContext((String)null, targetModuleURI, mcs);
      if (moduleContext == null) {
         Loggable l = J2EELogger.logUnableToResolveEJBLinkLoggable(ejbLink, ejbRefName, moduleURI);
         throw new ReferenceResolutionException(l.getMessage());
      } else {
         DeploymentInfo di = getDeploymentInfo(moduleContext);
         if (di == null) {
            Loggable l = J2EELogger.logInvalidQualifiedEJBLinkLoggable(ejbLink, ejbRefName, moduleURI, targetModuleURI, targetEjbName);
            throw new ReferenceResolutionException(l.getMessage());
         } else {
            BeanInfo bi = di.getBeanInfo(targetEjbName);
            if (bi != null && bi instanceof ClientDrivenBeanInfo) {
               return (ClientDrivenBeanInfo)bi;
            } else {
               Loggable l = J2EELogger.logInvalidQualifiedEJBLinkLoggable(ejbLink, ejbRefName, moduleURI, targetModuleURI, targetEjbName);
               throw new ReferenceResolutionException(l.getMessage());
            }
         }
      }
   }

   private static ClientDrivenBeanInfo findInfoByModuleName(Collection mcs, String moduleURI, String ejbRefName, String ejbLink) throws ReferenceResolutionException {
      String targetModuleName = ejbLink.substring(0, ejbLink.indexOf(47));
      String targetEjbName = ejbLink.substring(ejbLink.indexOf(47) + 1);
      ModuleContext moduleContext = findModuleContext(targetModuleName, (String)null, mcs);
      if (moduleContext == null) {
         Loggable l = J2EELogger.logUnableToResolveEJBLinkLoggable(ejbLink, ejbRefName, moduleURI);
         throw new ReferenceResolutionException(l.getMessage());
      } else {
         DeploymentInfo di = getDeploymentInfo(moduleContext);
         if (di == null) {
            Loggable l = J2EELogger.logInvalidQualifiedEJBLinkLoggable(ejbLink, ejbRefName, moduleURI, targetModuleName, targetEjbName);
            throw new ReferenceResolutionException(l.getMessage());
         } else {
            BeanInfo bi = di.getBeanInfo(targetEjbName);
            if (bi != null && bi instanceof ClientDrivenBeanInfo) {
               return (ClientDrivenBeanInfo)bi;
            } else {
               Loggable l = J2EELogger.logInvalidQualifiedEJBLinkLoggable(ejbLink, ejbRefName, moduleURI, targetModuleName, targetEjbName);
               throw new ReferenceResolutionException(l.getMessage());
            }
         }
      }
   }

   private static ModuleContext findModuleContext(String moduleName, String moduleURI, Collection moduleContexts) {
      Iterator var3 = moduleContexts.iterator();

      ModuleContext moduleContext;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         moduleContext = (ModuleContext)var3.next();
         if (moduleName != null && moduleName.equals(moduleContext.getName())) {
            return moduleContext;
         }
      } while(moduleURI == null || !moduleURI.equals(moduleContext.getURI()));

      return moduleContext;
   }

   public static ClientDrivenBeanInfo findInfoByReferenceClass(Collection moduleContexts, String applicationId, String moduleURI, String ejbRefName, String ifaceName) throws ReferenceResolutionException {
      return findInfo(moduleContexts, applicationId, moduleURI, ejbRefName, (String)null, ifaceName);
   }

   private static ClientDrivenBeanInfo findInfo(Collection moduleContexts, String applicationId, String moduleURI, String ejbRefName, String ejbName, String clientViewName) throws ReferenceResolutionException {
      Map resolved = new HashMap();
      Iterator var7 = moduleContexts.iterator();

      while(true) {
         ModuleContext moduleContext;
         DeploymentInfo di;
         do {
            if (!var7.hasNext()) {
               if (resolved.isEmpty()) {
                  Loggable l;
                  if (ejbName == null) {
                     l = J2EELogger.logFailedToAutoLinkEjbRefNoMatchesLoggable(ejbRefName, moduleURI, ApplicationVersionUtils.getApplicationName(applicationId), clientViewName);
                  } else {
                     l = J2EELogger.logInvalidUnqualifiedEJBLinkLoggable(ejbName, ejbRefName, moduleURI);
                  }

                  throw new ReferenceResolutionException(l.getMessage());
               }

               if (resolved.size() > 1) {
                  StringBuilder sb = new StringBuilder();
                  Iterator var15 = resolved.keySet().iterator();

                  while(var15.hasNext()) {
                     ClientDrivenBeanInfo cdbi = (ClientDrivenBeanInfo)var15.next();
                     if (sb.length() > 0) {
                        sb.append(", ");
                     }

                     ModuleContext mc = (ModuleContext)resolved.get(cdbi);
                     sb.append(mc.getURI() + "/" + cdbi.getEJBName());
                  }

                  Loggable l;
                  if (ejbName == null) {
                     l = J2EELogger.logFailedToAutoLinkEjbRefMultipleInterfacesLoggable(ejbRefName, moduleURI, ApplicationVersionUtils.getApplicationName(applicationId), clientViewName, sb.toString());
                  } else {
                     l = J2EELogger.logAmbiguousEJBLinkLoggable(ejbName, ejbRefName, moduleURI, sb.toString());
                  }

                  throw new ReferenceResolutionException(l.getMessage());
               }

               return (ClientDrivenBeanInfo)resolved.keySet().iterator().next();
            }

            moduleContext = (ModuleContext)var7.next();
            di = getDeploymentInfo(moduleContext);
         } while(di == null);

         Iterator var10 = di.getBeanInfos().iterator();

         while(var10.hasNext()) {
            BeanInfo bi = (BeanInfo)var10.next();
            if (bi instanceof ClientDrivenBeanInfo) {
               ClientDrivenBeanInfo cdbi = (ClientDrivenBeanInfo)bi;
               if (ejbName != null && ejbName.equals(cdbi.getEJBName())) {
                  resolved.put(cdbi, moduleContext);
               }

               if (clientViewName != null && cdbi.hasClientViewFor(clientViewName)) {
                  resolved.put(cdbi, moduleContext);
               }
            }
         }
      }
   }

   public static DeploymentInfo getDeploymentInfo(ModuleContext modCtx) {
      return (DeploymentInfo)modCtx.getRegistry().get(DeploymentInfo.class.getName());
   }

   public static Collection getAppModuleContexts(ApplicationContextInternal appCtx, String... types) {
      Collection moduleContexts = new HashSet();
      Module[] var3 = appCtx.findModules(types);
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Module m = var3[var5];
         moduleContexts.add(appCtx.getModuleContext(m.getId()));
      }

      return moduleContexts;
   }

   public static Collection getAppModuleContexts(ToolsContext appCtx, ModuleType... types) {
      Collection moduleContexts = new HashSet();
      ToolsModule[] var3 = appCtx.getModules();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ToolsModule m = var3[var5];
         ModuleType[] var7 = types;
         int var8 = types.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            ModuleType type = var7[var9];
            if (type.equals(m.getModuleType())) {
               moduleContexts.add(appCtx.getModuleContext(m.getURI()));
            }
         }
      }

      return moduleContexts;
   }

   public static ValidationManager.ValidationBean getDefaultValidationBean(List validationDescriptors) {
      try {
         URL validationDescriptor = ValidationManager.getSingleValidationDescriptor(validationDescriptors);
         return ValidationManager.defaultInstance().getDefaultValidationBean(new Bindable() {
            public void bind(String fullyQualifiedName, Object value) throws NamingException {
            }
         }, validationDescriptor);
      } catch (Exception var2) {
         return null;
      }
   }

   public static String resolveResourceLink(String appName, String resourceLink) {
      return "weblogic." + appName + "." + resourceLink;
   }

   public static String decideJndiName(String resourceJndiName, String resourceLink, String appName) {
      String jndiName = resourceJndiName;
      if ((resourceJndiName == null || resourceJndiName.length() < 1) && resourceLink != null && resourceLink.length() > 0) {
         jndiName = resolveResourceLink(appName, resourceLink);
      }

      jndiName = transformJNDIName(jndiName, appName);
      return jndiName;
   }

   public static Map genResEnvDesJNDIMap(ResourceEnvDescriptionBean[] descs, String appName) {
      if (descs == null) {
         return Collections.emptyMap();
      } else {
         Map jndiMap = new HashMap();
         ResourceEnvDescriptionBean[] var3 = descs;
         int var4 = descs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ResourceEnvDescriptionBean desc = var3[var5];
            String resEnvJndiName = desc.getJNDIName();
            String resEnvLink = desc.getResourceLink();
            String targetJndiName = decideJndiName(resEnvJndiName, resEnvLink, appName);
            jndiMap.put(desc.getResourceEnvRefName(), targetJndiName);
         }

         return jndiMap;
      }
   }

   public static void registerMessageDestinations(MessageDestinationBean[] mds, MessageDestinationDescriptorBean[] descs, String appName, String moduleId) throws EnvironmentException {
      if (!isArrayNullOrEmpty(descs) || !isArrayNullOrEmpty(mds)) {
         MessageDestinationInfoRegistry mdir = new MessageDestinationInfoRegistryImpl();
         mdir.register(mds, descs);
         ApplicationContextInternal appCtx = ApplicationAccess.getApplicationAccess().getApplicationContext(appName);
         if (moduleId != null) {
            ModuleRegistry mr = appCtx.getModuleContext(moduleId).getRegistry();
            mr.put(MessageDestinationInfoRegistry.class.getName(), mdir);
         } else {
            Map registry = (Map)appCtx.getUserObject(ModuleRegistry.class.getName());
            registry.put(MessageDestinationInfoRegistry.class.getName(), mdir);
         }

      }
   }

   public static void unregisterMessageDestinations(MessageDestinationBean[] mds, MessageDestinationDescriptorBean[] descs, String appName, String moduleId) {
      if (!isArrayNullOrEmpty(descs) || !isArrayNullOrEmpty(mds)) {
         ApplicationContextInternal appCtx = ApplicationAccess.getApplicationAccess().getApplicationContext(appName);
         if (moduleId != null) {
            ModuleRegistry mr = appCtx.getModuleContext(moduleId).getRegistry();
            mr.remove(MessageDestinationInfoRegistry.class.getName());
         } else {
            Map registry = (Map)appCtx.getUserObject(ModuleRegistry.class.getName());
            registry.remove(MessageDestinationInfoRegistry.class.getName());
         }

      }
   }

   private static boolean isArrayNullOrEmpty(Object[] objs) {
      return objs == null || objs.length == 0;
   }

   public static String decideJNDIName(String jndiName, String lookupName, String mappedName) {
      String result = jndiName;
      if (jndiName == null || jndiName.length() == 0) {
         if (lookupName != null) {
            result = lookupName;
         } else if (mappedName != null) {
            result = mappedName;
         }
      }

      return result;
   }

   private static ClientDrivenBeanInfo findInfoByCurrentModuleId(Collection moduleContexts, String applicationId, String moduleId, String moduleURI, String ejbRefName, String ejbLink) throws ReferenceResolutionException {
      ApplicationContextInternal appCtx = ApplicationAccess.getApplicationAccess().getApplicationContext(applicationId);
      if (appCtx != null) {
         ModuleContext currentModuleCtx = appCtx.getModuleContext(moduleId);
         if (currentModuleCtx != null && currentModuleCtx.getURI().equals(moduleURI)) {
            DeploymentInfo di = getDeploymentInfo(currentModuleCtx);
            if (di != null) {
               BeanInfo bi = di.getBeanInfo(ejbLink);
               if (bi != null && bi instanceof ClientDrivenBeanInfo) {
                  return (ClientDrivenBeanInfo)bi;
               }
            }
         }
      }

      return null;
   }

   public static class InterfacesList extends LinkedList {
      private static final long serialVersionUID = -4285869794507861475L;

      public boolean addWithSpecialHandling(Class clazz) {
         if (clazz == null) {
            throw new NullPointerException();
         } else if (this.contains(clazz)) {
            return false;
         } else {
            if (!clazz.equals(EntityManager.class) && !clazz.equals(PersistenceManager.class)) {
               this.addLast(clazz);
            } else {
               this.addFirst(clazz);
            }

            return true;
         }
      }
   }
}
