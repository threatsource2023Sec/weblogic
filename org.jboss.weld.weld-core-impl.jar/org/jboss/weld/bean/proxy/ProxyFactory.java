package org.jboss.weld.bean.proxy;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.enterprise.inject.spi.Bean;
import org.jboss.classfilewriter.AccessFlag;
import org.jboss.classfilewriter.ClassFile;
import org.jboss.classfilewriter.ClassMethod;
import org.jboss.classfilewriter.DuplicateMemberException;
import org.jboss.classfilewriter.code.BranchEnd;
import org.jboss.classfilewriter.code.CodeAttribute;
import org.jboss.classfilewriter.util.Boxing;
import org.jboss.classfilewriter.util.DescriptorUtils;
import org.jboss.weld.Container;
import org.jboss.weld.bean.builtin.AbstractBuiltInBean;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.interceptor.proxy.LifecycleMixin;
import org.jboss.weld.interceptor.util.proxy.TargetInstanceProxy;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.proxy.WeldConstruct;
import org.jboss.weld.security.GetDeclaredConstructorsAction;
import org.jboss.weld.security.GetDeclaredMethodsAction;
import org.jboss.weld.security.GetProtectionDomainAction;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.serialization.spi.ContextualStore;
import org.jboss.weld.serialization.spi.ProxyServices;
import org.jboss.weld.util.Proxies;
import org.jboss.weld.util.bytecode.BytecodeUtils;
import org.jboss.weld.util.bytecode.ClassFileUtils;
import org.jboss.weld.util.bytecode.ConstructorUtils;
import org.jboss.weld.util.bytecode.MethodInformation;
import org.jboss.weld.util.bytecode.RuntimeMethodInformation;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.collections.Sets;
import org.jboss.weld.util.reflection.Reflections;

public class ProxyFactory implements PrivilegedAction {
   public static final String PROXY_SUFFIX = "$Proxy$";
   public static final String DEFAULT_PROXY_PACKAGE = "org.jboss.weld.proxies";
   private final Class beanType;
   private final Set additionalInterfaces;
   private final ClassLoader classLoader;
   private final String baseProxyName;
   private final Bean bean;
   private final Class proxiedBeanType;
   private final String contextId;
   private final ProxyServices proxyServices;
   private final WeldConfiguration configuration;
   public static final String CONSTRUCTED_FLAG_NAME = "constructed";
   private final ProxyInstantiator proxyInstantiator;
   protected static final BytecodeMethodResolver DEFAULT_METHOD_RESOLVER = new DefaultBytecodeMethodResolver();
   protected static final String LJAVA_LANG_REFLECT_METHOD = "Ljava/lang/reflect/Method;";
   protected static final String LJAVA_LANG_BYTE = "Ljava/lang/Byte;";
   protected static final String LJAVA_LANG_CLASS = "Ljava/lang/Class;";
   protected static final String LJAVA_LANG_OBJECT = "Ljava/lang/Object;";
   protected static final String LBEAN_IDENTIFIER = "Lorg/jboss/weld/serialization/spi/BeanIdentifier;";
   protected static final String LJAVA_LANG_STRING = "Ljava/lang/String;";
   protected static final String LJAVA_LANG_THREAD_LOCAL = "Ljava/lang/ThreadLocal;";
   protected static final String INIT_METHOD_NAME = "<init>";
   protected static final String INVOKE_METHOD_NAME = "invoke";
   protected static final String METHOD_HANDLER_FIELD_NAME = "methodHandler";
   static final String JAVA = "java";
   static final String NULL = "the class package is null";
   static final String SIGNED = "the class is signed";
   private static final Set METHOD_FILTERS;

   public ProxyFactory(String contextId, Class proxiedBeanType, Set typeClosure, Bean bean) {
      this(contextId, proxiedBeanType, typeClosure, bean, false);
   }

   public ProxyFactory(String contextId, Class proxiedBeanType, Set typeClosure, Bean bean, boolean forceSuperClass) {
      this(contextId, proxiedBeanType, typeClosure, getProxyName(contextId, proxiedBeanType, typeClosure, bean), bean, forceSuperClass);
   }

   public ProxyFactory(String contextId, Class proxiedBeanType, Set typeClosure, String proxyName, Bean bean) {
      this(contextId, proxiedBeanType, typeClosure, proxyName, bean, false);
   }

   public ProxyFactory(String contextId, Class proxiedBeanType, Set typeClosure, String proxyName, Bean bean, boolean forceSuperClass) {
      this.additionalInterfaces = new LinkedHashSet();
      this.bean = bean;
      this.contextId = contextId;
      this.proxiedBeanType = proxiedBeanType;
      this.configuration = (WeldConfiguration)Container.instance(contextId).deploymentManager().getServices().get(WeldConfiguration.class);
      this.addInterfacesFromTypeClosure(typeClosure, proxiedBeanType);
      Proxies.TypeInfo typeInfo = Proxies.TypeInfo.of(typeClosure);
      Class superClass = typeInfo.getSuperClass();
      superClass = superClass == null ? Object.class : superClass;
      if (forceSuperClass || superClass.equals(Object.class) && this.additionalInterfaces.isEmpty()) {
         superClass = proxiedBeanType;
      }

      this.beanType = superClass;
      this.addDefaultAdditionalInterfaces();
      this.baseProxyName = proxyName;
      this.proxyServices = (ProxyServices)Container.instance(contextId).services().get(ProxyServices.class);
      if (!this.proxyServices.supportsClassDefining()) {
         if (bean != null) {
            this.classLoader = resolveClassLoaderForBeanProxy(contextId, bean.getBeanClass(), typeInfo, this.proxyServices);
         } else {
            this.classLoader = resolveClassLoaderForBeanProxy(contextId, proxiedBeanType, typeInfo, this.proxyServices);
         }
      } else {
         this.classLoader = null;
      }

      if (this.additionalInterfaces.size() > 1) {
         LinkedHashSet sorted = Proxies.sortInterfacesHierarchy(this.additionalInterfaces);
         this.additionalInterfaces.clear();
         this.additionalInterfaces.addAll(sorted);
      }

      this.proxyInstantiator = (ProxyInstantiator)Container.instance(contextId).services().get(ProxyInstantiator.class);
   }

   static String getProxyName(String contextId, Class proxiedBeanType, Set typeClosure, Bean bean) {
      Proxies.TypeInfo typeInfo = Proxies.TypeInfo.of(typeClosure);
      String proxyPackage;
      String className;
      if (proxiedBeanType.equals(Object.class)) {
         Class superInterface = typeInfo.getSuperInterface();
         if (superInterface == null) {
            throw new IllegalArgumentException("Proxied bean type cannot be java.lang.Object without an interface");
         }

         String reason = getDefaultPackageReason(superInterface);
         if (reason != null) {
            proxyPackage = "org.jboss.weld.proxies";
            BeanLogger.LOG.generatingProxyToDefaultPackage(superInterface, "org.jboss.weld.proxies", reason);
         } else {
            proxyPackage = superInterface.getPackage().getName();
         }
      } else {
         className = getDefaultPackageReason(proxiedBeanType);
         if (className != null && className.equals("the class package is null")) {
            proxyPackage = "org.jboss.weld.proxies";
            BeanLogger.LOG.generatingProxyToDefaultPackage(proxiedBeanType, "org.jboss.weld.proxies", className);
         } else {
            proxyPackage = proxiedBeanType.getPackage().getName();
         }
      }

      if (typeInfo.getSuperClass() == Object.class) {
         StringBuilder name = new StringBuilder();
         className = createCompoundProxyName(contextId, bean, typeInfo, name) + "$Proxy$";
      } else {
         boolean typeModified = false;
         Iterator var8 = typeInfo.getInterfaces().iterator();

         while(var8.hasNext()) {
            Class iface = (Class)var8.next();
            if (!iface.isAssignableFrom(typeInfo.getSuperClass())) {
               typeModified = true;
               break;
            }
         }

         if (typeModified) {
            StringBuilder name = new StringBuilder(typeInfo.getSuperClass().getSimpleName() + "$");
            className = createCompoundProxyName(contextId, bean, typeInfo, name) + "$Proxy$";
         } else {
            className = typeInfo.getSuperClass().getSimpleName() + "$Proxy$";
         }
      }

      return proxyPackage + '.' + getEnclosingPrefix(proxiedBeanType) + className;
   }

   public void addInterfacesFromTypeClosure(Set typeClosure, Class proxiedBeanType) {
      Iterator var3 = typeClosure.iterator();

      while(var3.hasNext()) {
         Type type = (Type)var3.next();
         Class c = Reflections.getRawType(type);
         if (c.isInterface()) {
            this.addInterface(c);
         }
      }

   }

   private static String createCompoundProxyName(String contextId, Bean bean, Proxies.TypeInfo typeInfo, StringBuilder name) {
      List interfaces = new ArrayList();
      Iterator var6 = typeInfo.getInterfaces().iterator();

      while(var6.hasNext()) {
         Class type = (Class)var6.next();
         interfaces.add(type.getSimpleName());
      }

      Collections.sort(interfaces);
      var6 = interfaces.iterator();

      while(var6.hasNext()) {
         String iface = (String)var6.next();
         name.append(iface);
         name.append('$');
      }

      if (bean != null && !(bean instanceof AbstractBuiltInBean)) {
         BeanIdentifier id = ((ContextualStore)Container.instance(contextId).services().get(ContextualStore.class)).putIfAbsent(bean);
         int idHash = id.hashCode();
         name.append(Math.abs(idHash == Integer.MIN_VALUE ? 0 : idHash));
      }

      String className = name.toString();
      return className;
   }

   private static String getEnclosingPrefix(Class clazz) {
      Class encl = clazz.getEnclosingClass();
      return encl == null ? "" : getEnclosingPrefix(encl) + encl.getSimpleName() + '$';
   }

   public void addInterface(Class newInterface) {
      if (!newInterface.isInterface()) {
         throw new IllegalArgumentException(newInterface + " is not an interface");
      } else {
         this.additionalInterfaces.add(newInterface);
      }
   }

   public Object create(BeanInstance beanInstance) {
      Object proxy = System.getSecurityManager() == null ? this.run() : AccessController.doPrivileged(this);
      ((ProxyObject)proxy).weld_setHandler(new ProxyMethodHandler(this.contextId, beanInstance, this.bean));
      return proxy;
   }

   public Object run() {
      try {
         Class proxyClass = this.getProxyClass();
         boolean hasConstrutedField = SecurityActions.hasDeclaredField(proxyClass, "constructed");
         if (hasConstrutedField != this.useConstructedFlag()) {
            ProxyInstantiator newInstantiator = ProxyInstantiator.Factory.create(!hasConstrutedField);
            BeanLogger.LOG.creatingProxyInstanceUsingDifferentInstantiator(proxyClass, newInstantiator, this.proxyInstantiator);
            return newInstantiator.newInstance(proxyClass);
         } else {
            return this.proxyInstantiator.newInstance(proxyClass);
         }
      } catch (InstantiationException var4) {
         throw new DefinitionException(BeanLogger.LOG.proxyInstantiationFailed(this), var4.getCause());
      } catch (IllegalAccessException var5) {
         throw new DefinitionException(BeanLogger.LOG.proxyInstantiationBeanAccessFailed(this), var5.getCause());
      }
   }

   public Class getProxyClass() {
      String suffix = "_$$_Weld" + this.getProxyNameSuffix();
      String proxyClassName = this.getBaseProxyName();
      if (!proxyClassName.endsWith(suffix)) {
         proxyClassName = proxyClassName + suffix;
      }

      if (proxyClassName.startsWith("java")) {
         proxyClassName = proxyClassName.replaceFirst("java", "org.jboss.weld");
      }

      Class proxyClass = null;
      Class originalClass = this.bean != null ? this.bean.getBeanClass() : this.proxiedBeanType;
      BeanLogger.LOG.generatingProxyClass(proxyClassName);

      try {
         proxyClass = (Class)Reflections.cast(this.classLoader == null ? this.proxyServices.loadClass(originalClass, proxyClassName) : this.classLoader.loadClass(proxyClassName));
      } catch (ClassNotFoundException var10) {
         try {
            proxyClass = this.createProxyClass(originalClass, proxyClassName);
         } catch (Throwable var9) {
            try {
               proxyClass = (Class)Reflections.cast(this.classLoader == null ? this.proxyServices.loadClass(originalClass, proxyClassName) : this.classLoader.loadClass(proxyClassName));
            } catch (ClassNotFoundException var8) {
               BeanLogger.LOG.catchingDebug(var9);
               throw BeanLogger.LOG.unableToLoadProxyClass(this.bean, this.proxiedBeanType, var9);
            }
         }
      }

      return proxyClass;
   }

   protected String getBaseProxyName() {
      return this.baseProxyName;
   }

   public static void setBeanInstance(String contextId, Object proxy, BeanInstance beanInstance, Bean bean) {
      if (proxy instanceof ProxyObject) {
         ProxyObject proxyView = (ProxyObject)proxy;
         proxyView.weld_setHandler(new ProxyMethodHandler(contextId, beanInstance, bean));
      }

   }

   protected String getProxyNameSuffix() {
      return "$Proxy$";
   }

   private void addDefaultAdditionalInterfaces() {
      this.additionalInterfaces.add(Serializable.class);
      this.additionalInterfaces.add(WeldConstruct.class);
   }

   protected void addAdditionalInterfaces(Set interfaces) {
   }

   private Class createProxyClass(Class originalClass, String proxyClassName) throws Exception {
      Set specialInterfaces = Sets.newHashSet(LifecycleMixin.class, TargetInstanceProxy.class, ProxyObject.class);
      this.addAdditionalInterfaces(specialInterfaces);
      this.additionalInterfaces.removeAll(specialInterfaces);
      ClassFile proxyClassType = null;
      int accessFlags = AccessFlag.of(new int[]{1, 32, 4096});
      if (this.getBeanType().isInterface()) {
         proxyClassType = this.newClassFile(proxyClassName, accessFlags, Object.class.getName());
         proxyClassType.addInterface(this.getBeanType().getName());
      } else {
         proxyClassType = this.newClassFile(proxyClassName, accessFlags, this.getBeanType().getName());
      }

      Iterator var6 = this.additionalInterfaces.iterator();

      while(var6.hasNext()) {
         Class clazz = (Class)var6.next();
         proxyClassType.addInterface(clazz.getName());
      }

      List initialValueBytecode = new ArrayList();
      ClassMethod staticConstructor = proxyClassType.addMethod(AccessFlag.of(new int[]{1, 8}), "<clinit>", "V", new String[0]);
      this.addFields(proxyClassType, initialValueBytecode);
      this.addConstructors(proxyClassType, initialValueBytecode);
      this.addMethods(proxyClassType, staticConstructor);
      staticConstructor.getCodeAttribute().returnInstruction();
      Iterator var8 = specialInterfaces.iterator();

      Class proxyClass;
      while(var8.hasNext()) {
         proxyClass = (Class)var8.next();
         proxyClassType.addInterface(proxyClass.getName());
      }

      this.dumpToFile(proxyClassName, proxyClassType.toBytecode());
      ProtectionDomain domain = (ProtectionDomain)AccessController.doPrivileged(new GetProtectionDomainAction(this.proxiedBeanType));
      if (this.proxiedBeanType.getPackage() != null && !this.proxiedBeanType.equals(Object.class)) {
         if (System.getSecurityManager() != null) {
            ProtectionDomainCache cache = (ProtectionDomainCache)Container.instance(this.contextId).services().get(ProtectionDomainCache.class);
            domain = cache.getProtectionDomainForProxy(domain);
         }
      } else {
         domain = ProxyFactory.class.getProtectionDomain();
      }

      if (this.classLoader == null) {
         proxyClass = (Class)Reflections.cast(ClassFileUtils.toClass(proxyClassType, originalClass, this.proxyServices, domain));
      } else {
         proxyClass = (Class)Reflections.cast(ClassFileUtils.toClass(proxyClassType, this.classLoader, domain));
      }

      BeanLogger.LOG.createdProxyClass(proxyClass, Arrays.toString(proxyClass.getInterfaces()));
      return proxyClass;
   }

   private ClassFile newClassFile(String name, int accessFlags, String superclass, String... interfaces) {
      try {
         return this.classLoader == null ? new ClassFile(name, accessFlags, superclass, interfaces) : new ClassFile(name, accessFlags, superclass, this.classLoader, interfaces);
      } catch (Exception var6) {
         throw BeanLogger.LOG.unableToCreateClassFile(name, var6.getCause());
      }
   }

   private void dumpToFile(String fileName, byte[] data) {
      File proxyDumpFilePath = this.configuration.getProxyDumpFilePath();
      if (proxyDumpFilePath != null) {
         File dumpFile = new File(proxyDumpFilePath, fileName + ".class");

         try {
            Files.write(dumpFile.toPath(), data, new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING});
         } catch (IOException var6) {
            BeanLogger.LOG.beanCannotBeDumped(fileName, var6);
         }

      }
   }

   protected void addConstructors(ClassFile proxyClassType, List initialValueBytecode) {
      try {
         if (this.getBeanType().isInterface()) {
            ConstructorUtils.addDefaultConstructor(proxyClassType, initialValueBytecode, !this.useConstructedFlag());
         } else {
            boolean constructorFound = false;
            Constructor[] var4 = (Constructor[])AccessController.doPrivileged(new GetDeclaredConstructorsAction(this.getBeanType()));
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Constructor constructor = var4[var6];
               if ((constructor.getModifiers() & 2) == 0) {
                  constructorFound = true;
                  String[] exceptions = new String[constructor.getExceptionTypes().length];

                  for(int i = 0; i < exceptions.length; ++i) {
                     exceptions[i] = constructor.getExceptionTypes()[i].getName();
                  }

                  ConstructorUtils.addConstructor("V", DescriptorUtils.parameterDescriptors(constructor.getParameterTypes()), exceptions, proxyClassType, initialValueBytecode, !this.useConstructedFlag());
               }
            }

            if (!constructorFound) {
               this.addConstructorsForBeanWithPrivateConstructors(proxyClassType);
            }
         }

      } catch (Exception var10) {
         throw new WeldException(var10);
      }
   }

   protected void addFields(ClassFile proxyClassType, List initialValueBytecode) {
      proxyClassType.addField(2, "methodHandler", this.getMethodHandlerType());
      if (this.useConstructedFlag()) {
         proxyClassType.addField(2, "constructed", "Z");
      }

   }

   protected Class getMethodHandlerType() {
      return MethodHandler.class;
   }

   protected void addMethods(ClassFile proxyClassType, ClassMethod staticConstructor) {
      this.addMethodsFromClass(proxyClassType, staticConstructor);
      this.addSpecialMethods(proxyClassType, staticConstructor);
      this.addSerializationSupport(proxyClassType);
   }

   protected void addSerializationSupport(ClassFile proxyClassType) {
   }

   protected void addMethodsFromClass(ClassFile proxyClassType, ClassMethod staticConstructor) {
      try {
         Class cls = this.getBeanType();
         this.generateEqualsMethod(proxyClassType);
         this.generateHashCodeMethod(proxyClassType);

         Iterator var5;
         Class implementedInterface;
         for(boolean isBeanClassAbstract = Modifier.isAbstract(cls.getModifiers()); cls != null; cls = cls.getSuperclass()) {
            this.addMethods(cls, proxyClassType, staticConstructor);
            if (isBeanClassAbstract && Modifier.isAbstract(cls.getModifiers())) {
               var5 = Reflections.getInterfaceClosure(cls).iterator();

               while(var5.hasNext()) {
                  implementedInterface = (Class)var5.next();
                  if (!this.additionalInterfaces.contains(implementedInterface)) {
                     this.addMethods(implementedInterface, proxyClassType, staticConstructor);
                  }
               }
            }
         }

         var5 = this.additionalInterfaces.iterator();

         while(var5.hasNext()) {
            implementedInterface = (Class)var5.next();
            Method[] var7 = implementedInterface.getMethods();
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               Method method = var7[var9];
               if (this.isMethodAccepted(method, this.getProxySuperclass())) {
                  try {
                     MethodInformation methodInfo = new RuntimeMethodInformation(method);
                     ClassMethod classMethod = proxyClassType.addMethod(method);
                     if (Reflections.isDefault(method)) {
                        this.addConstructedGuardToMethodBody(classMethod);
                        this.createForwardingMethodBody(classMethod, methodInfo, staticConstructor);
                     } else {
                        this.createSpecialMethodBody(classMethod, methodInfo, staticConstructor);
                     }

                     BeanLogger.LOG.addingMethodToProxy(method);
                  } catch (DuplicateMemberException var13) {
                  }
               }
            }
         }

      } catch (Exception var14) {
         throw new WeldException(var14);
      }
   }

   private void addMethods(Class cls, ClassFile proxyClassType, ClassMethod staticConstructor) {
      Method[] var4 = (Method[])AccessController.doPrivileged(new GetDeclaredMethodsAction(cls));
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Method method = var4[var6];
         if (this.isMethodAccepted(method, this.getProxySuperclass())) {
            try {
               MethodInformation methodInfo = new RuntimeMethodInformation(method);
               ClassMethod classMethod = proxyClassType.addMethod(method);
               this.addConstructedGuardToMethodBody(classMethod);
               this.createForwardingMethodBody(classMethod, methodInfo, staticConstructor);
               BeanLogger.LOG.addingMethodToProxy(method);
            } catch (DuplicateMemberException var10) {
            }
         }
      }

   }

   protected boolean isMethodAccepted(Method method, Class proxySuperclass) {
      Iterator var3 = METHOD_FILTERS.iterator();

      ProxiedMethodFilter filter;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         filter = (ProxiedMethodFilter)var3.next();
      } while(filter.accept(method, proxySuperclass));

      return false;
   }

   protected void generateHashCodeMethod(ClassFile proxyClassType) {
   }

   protected void generateEqualsMethod(ClassFile proxyClassType) {
   }

   protected void createSpecialMethodBody(ClassMethod proxyClassType, MethodInformation method, ClassMethod staticConstructor) {
      this.createInterceptorBody(proxyClassType, method, staticConstructor);
   }

   protected void addConstructedGuardToMethodBody(ClassMethod classMethod) {
      this.addConstructedGuardToMethodBody(classMethod, classMethod.getClassFile().getSuperclass());
   }

   protected void addConstructedGuardToMethodBody(ClassMethod classMethod, String className) {
      if (this.useConstructedFlag()) {
         CodeAttribute cond = classMethod.getCodeAttribute();
         cond.aload(0);
         cond.getfield(classMethod.getClassFile().getName(), "constructed", "Z");
         BranchEnd jumpMarker = cond.ifne();
         cond.aload(0);
         cond.loadMethodParameters();
         cond.invokespecial(className, classMethod.getName(), classMethod.getDescriptor());
         cond.returnInstruction();
         cond.branchEnd(jumpMarker);
      }
   }

   protected void createForwardingMethodBody(ClassMethod classMethod, MethodInformation method, ClassMethod staticConstructor) {
      this.createInterceptorBody(classMethod, method, staticConstructor);
   }

   protected void createInterceptorBody(ClassMethod classMethod, MethodInformation method, ClassMethod staticConstructor) {
      this.invokeMethodHandler(classMethod, method, true, DEFAULT_METHOD_RESOLVER, staticConstructor);
   }

   protected void invokeMethodHandler(ClassMethod classMethod, MethodInformation method, boolean addReturnInstruction, BytecodeMethodResolver bytecodeMethodResolver, ClassMethod staticConstructor) {
      CodeAttribute b = classMethod.getCodeAttribute();
      b.aload(0);
      this.getMethodHandlerField(classMethod.getClassFile(), b);
      b.aload(0);
      bytecodeMethodResolver.getDeclaredMethod(classMethod, method.getDeclaringClass(), method.getName(), method.getParameterTypes(), staticConstructor);
      b.aconstNull();
      b.iconst(method.getParameterTypes().length);
      b.anewarray("java.lang.Object");
      int localVariableCount = 1;

      for(int i = 0; i < method.getParameterTypes().length; ++i) {
         String typeString = method.getParameterTypes()[i];
         b.dup();
         b.iconst(i);
         BytecodeUtils.addLoadInstruction(b, typeString, localVariableCount);
         Boxing.boxIfNessesary(b, typeString);
         b.aastore();
         if (DescriptorUtils.isWide(typeString)) {
            localVariableCount += 2;
         } else {
            ++localVariableCount;
         }
      }

      b.invokeinterface(MethodHandler.class.getName(), "invoke", "Ljava/lang/Object;", new String[]{"Ljava/lang/Object;", "Ljava/lang/reflect/Method;", "Ljava/lang/reflect/Method;", "[Ljava/lang/Object;"});
      if (addReturnInstruction) {
         if (method.getReturnType().equals("V")) {
            b.returnInstruction();
         } else if (DescriptorUtils.isPrimitive(method.getReturnType())) {
            Boxing.unbox(b, method.getReturnType());
            b.returnInstruction();
         } else {
            b.checkcast(BytecodeUtils.getName(method.getReturnType()));
            b.returnInstruction();
         }
      }

   }

   protected void addSpecialMethods(ClassFile proxyClassType, ClassMethod staticConstructor) {
      try {
         Method[] var3 = LifecycleMixin.class.getMethods();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method method = var3[var5];
            BeanLogger.LOG.addingMethodToProxy(method);
            MethodInformation methodInfo = new RuntimeMethodInformation(method);
            ClassMethod classMethod = proxyClassType.addMethod(method);
            this.createInterceptorBody(classMethod, methodInfo, staticConstructor);
         }

         Method getInstanceMethod = TargetInstanceProxy.class.getMethod("weld_getTargetInstance");
         Method getInstanceClassMethod = TargetInstanceProxy.class.getMethod("weld_getTargetClass");
         MethodInformation getInstanceMethodInfo = new RuntimeMethodInformation(getInstanceMethod);
         this.createInterceptorBody(proxyClassType.addMethod(getInstanceMethod), getInstanceMethodInfo, staticConstructor);
         MethodInformation getInstanceClassMethodInfo = new RuntimeMethodInformation(getInstanceClassMethod);
         this.createInterceptorBody(proxyClassType.addMethod(getInstanceClassMethod), getInstanceClassMethodInfo, staticConstructor);
         Method setMethodHandlerMethod = ProxyObject.class.getMethod("weld_setHandler", MethodHandler.class);
         this.generateSetMethodHandlerBody(proxyClassType.addMethod(setMethodHandlerMethod));
         Method getMethodHandlerMethod = ProxyObject.class.getMethod("weld_getHandler");
         this.generateGetMethodHandlerBody(proxyClassType.addMethod(getMethodHandlerMethod));
      } catch (Exception var9) {
         throw new WeldException(var9);
      }
   }

   protected void generateSetMethodHandlerBody(ClassMethod method) {
      CodeAttribute b = method.getCodeAttribute();
      b.aload(0);
      b.aload(1);
      b.checkcast(this.getMethodHandlerType());
      b.putfield(method.getClassFile().getName(), "methodHandler", DescriptorUtils.makeDescriptor(this.getMethodHandlerType()));
      b.returnInstruction();
   }

   protected void generateGetMethodHandlerBody(ClassMethod method) {
      CodeAttribute b = method.getCodeAttribute();
      b.aload(0);
      this.getMethodHandlerField(method.getClassFile(), b);
      b.returnInstruction();
   }

   private void addConstructorsForBeanWithPrivateConstructors(ClassFile proxyClassType) {
      ClassMethod ctor = proxyClassType.addMethod(1, "<init>", "V", new String[]{"Ljava/lang/Byte;"});
      CodeAttribute b = ctor.getCodeAttribute();
      b.aload(0);
      b.aconstNull();
      b.aconstNull();
      b.invokespecial(proxyClassType.getName(), "<init>", "(Ljava/lang/Byte;Ljava/lang/Byte;)V");
      b.returnInstruction();
      ctor = proxyClassType.addMethod(1, "<init>", "V", new String[]{"Ljava/lang/Byte;", "Ljava/lang/Byte;"});
      b = ctor.getCodeAttribute();
      b.aload(0);
      b.aconstNull();
      b.invokespecial(proxyClassType.getName(), "<init>", "(Ljava/lang/Byte;)V");
      b.returnInstruction();
   }

   public Class getBeanType() {
      return this.beanType;
   }

   public Set getAdditionalInterfaces() {
      return this.additionalInterfaces;
   }

   public Bean getBean() {
      return this.bean;
   }

   public String getContextId() {
      return this.contextId;
   }

   protected Class getProxiedBeanType() {
      return this.proxiedBeanType;
   }

   public static ClassLoader resolveClassLoaderForBeanProxy(String contextId, Class proxiedType, Proxies.TypeInfo typeInfo, ProxyServices proxyServices) {
      Class superClass = typeInfo.getSuperClass();
      if (superClass.getName().startsWith("java")) {
         ClassLoader cl = proxyServices.getClassLoader(proxiedType);
         if (cl == null) {
            cl = Thread.currentThread().getContextClassLoader();
         }

         return cl;
      } else {
         return ((ProxyServices)Container.instance(contextId).services().get(ProxyServices.class)).getClassLoader(superClass);
      }
   }

   protected void getMethodHandlerField(ClassFile file, CodeAttribute b) {
      b.getfield(file.getName(), "methodHandler", DescriptorUtils.makeDescriptor(this.getMethodHandlerType()));
   }

   protected Class getProxySuperclass() {
      return this.getBeanType().isInterface() ? Object.class : this.getBeanType();
   }

   protected boolean isUsingProxyInstantiator() {
      return true;
   }

   private boolean useConstructedFlag() {
      return !this.isUsingProxyInstantiator() || this.proxyInstantiator.isUsingConstructor();
   }

   private static String getDefaultPackageReason(Class clazz) {
      if (clazz.getPackage() == null) {
         return "the class package is null";
      } else {
         return clazz.getSigners() != null ? "the class is signed" : null;
      }
   }

   static {
      Set filters = new HashSet();
      filters.add(CommonProxiedMethodFilters.NON_STATIC);
      filters.add(CommonProxiedMethodFilters.NON_FINAL);
      filters.add(CommonProxiedMethodFilters.OBJECT_TO_STRING);
      filters.add(CommonProxiedMethodFilters.NON_JDK_PACKAGE_PRIVATE);
      GroovyMethodFilter groovy = new GroovyMethodFilter();
      if (groovy.isEnabled()) {
         filters.add(groovy);
      }

      METHOD_FILTERS = ImmutableSet.copyOf(filters);
   }
}
