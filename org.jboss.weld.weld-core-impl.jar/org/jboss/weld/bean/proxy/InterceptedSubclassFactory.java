package org.jboss.weld.bean.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.AccessController;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.inject.spi.Bean;
import org.jboss.classfilewriter.ClassFile;
import org.jboss.classfilewriter.ClassMethod;
import org.jboss.classfilewriter.DuplicateMemberException;
import org.jboss.classfilewriter.code.BranchEnd;
import org.jboss.classfilewriter.code.CodeAttribute;
import org.jboss.classfilewriter.util.Boxing;
import org.jboss.classfilewriter.util.DescriptorUtils;
import org.jboss.weld.annotated.enhanced.MethodSignature;
import org.jboss.weld.annotated.enhanced.jlr.MethodSignatureImpl;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.interceptor.proxy.LifecycleMixin;
import org.jboss.weld.interceptor.util.proxy.TargetInstanceProxy;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.security.GetDeclaredMethodsAction;
import org.jboss.weld.util.bytecode.BytecodeUtils;
import org.jboss.weld.util.bytecode.MethodInformation;
import org.jboss.weld.util.bytecode.RuntimeMethodInformation;
import org.jboss.weld.util.reflection.Reflections;

public class InterceptedSubclassFactory extends ProxyFactory {
   public static final String PROXY_SUFFIX = "Subclass";
   private static final String SUPER_DELEGATE_SUFFIX = "$$super";
   static final String COMBINED_INTERCEPTOR_AND_DECORATOR_STACK_METHOD_HANDLER_CLASS_NAME = CombinedInterceptorAndDecoratorStackMethodHandler.class.getName();
   static final String[] INVOKE_METHOD_PARAMETERS = new String[]{DescriptorUtils.makeDescriptor(InterceptionDecorationContext.Stack.class), "Ljava/lang/Object;", "Ljava/lang/reflect/Method;", "Ljava/lang/reflect/Method;", "[Ljava/lang/Object;"};
   protected static final String PRIVATE_METHOD_HANDLER_FIELD_NAME = "privateMethodHandler";
   private final Set enhancedMethodSignatures;
   private final Set interceptedMethodSignatures;
   private Set interfacesToInspect;
   private final Class proxiedBeanType;

   public InterceptedSubclassFactory(String contextId, Class proxiedBeanType, Set typeClosure, Bean bean, Set enhancedMethodSignatures, Set interceptedMethodSignatures) {
      this(contextId, proxiedBeanType, typeClosure, getProxyName(contextId, proxiedBeanType, typeClosure, bean), bean, enhancedMethodSignatures, interceptedMethodSignatures);
   }

   public InterceptedSubclassFactory(String contextId, Class proxiedBeanType, Set typeClosure, String proxyName, Bean bean, Set enhancedMethodSignatures, Set interceptedMethodSignatures) {
      super(contextId, proxiedBeanType, typeClosure, proxyName, bean, true);
      this.enhancedMethodSignatures = enhancedMethodSignatures;
      this.interceptedMethodSignatures = interceptedMethodSignatures;
      this.proxiedBeanType = proxiedBeanType;
   }

   public void addInterfacesFromTypeClosure(Set typeClosure, Class proxiedBeanType) {
      Class[] var3 = proxiedBeanType.getInterfaces();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class c = var3[var5];
         this.addInterface(c);
      }

      Iterator var7 = typeClosure.iterator();

      while(var7.hasNext()) {
         Type type = (Type)var7.next();
         Class c = Reflections.getRawType(type);
         if (c.isInterface()) {
            this.addInterfaceToInspect(c);
         }
      }

   }

   private void addInterfaceToInspect(Class iface) {
      if (this.interfacesToInspect == null) {
         this.interfacesToInspect = new HashSet();
      }

      this.interfacesToInspect.add(iface);
   }

   protected String getProxyNameSuffix() {
      return "Subclass";
   }

   protected void addMethods(ClassFile proxyClassType, ClassMethod staticConstructor) {
      this.addMethodsFromClass(proxyClassType, staticConstructor);
      this.addSpecialMethods(proxyClassType, staticConstructor);
   }

   protected void addMethodsFromClass(ClassFile proxyClassType, final ClassMethod staticConstructor) {
      try {
         Set finalMethods = new HashSet();
         Set processedBridgeMethods = new HashSet();

         HashSet declaredBridgeMethods;
         for(Class cls = this.getBeanType(); cls != null; cls = cls.getSuperclass()) {
            declaredBridgeMethods = new HashSet();
            Method[] var7 = (Method[])AccessController.doPrivileged(new GetDeclaredMethodsAction(cls));
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               Method method = var7[var9];
               MethodSignatureImpl methodSignature = new MethodSignatureImpl(method);
               if (!Modifier.isFinal(method.getModifiers()) && !method.isBridge() && this.enhancedMethodSignatures.contains(methodSignature) && !finalMethods.contains(methodSignature) && !this.bridgeMethodsContainsMethod(processedBridgeMethods, methodSignature, method.getGenericReturnType(), Modifier.isAbstract(method.getModifiers()))) {
                  try {
                     final MethodInformation methodInfo = new RuntimeMethodInformation(method);
                     ClassMethod classMethod;
                     if (this.interceptedMethodSignatures.contains(methodSignature)) {
                        this.createDelegateMethod(proxyClassType, method, methodInfo);
                        classMethod = proxyClassType.addMethod(method);
                        this.addConstructedGuardToMethodBody(classMethod);
                        this.createForwardingMethodBody(classMethod, methodInfo, staticConstructor);
                        BeanLogger.LOG.addingMethodToProxy(method);
                     } else {
                        classMethod = proxyClassType.addMethod(method);
                        (new RunWithinInterceptionDecorationContextGenerator(classMethod, this) {
                           void doWork(CodeAttribute b, ClassMethod classMethod) {
                              if (Modifier.isPrivate(classMethod.getAccessFlags())) {
                                 InterceptedSubclassFactory.this.invokePrivateMethodHandler(b, classMethod, methodInfo, staticConstructor);
                              } else {
                                 b.aload(0);
                                 b.loadMethodParameters();
                                 b.invokespecial(methodInfo.getDeclaringClass(), methodInfo.getName(), methodInfo.getDescriptor());
                              }

                           }

                           void doReturn(CodeAttribute b, ClassMethod method) {
                              b.returnInstruction();
                           }
                        }).runStartIfNotOnTop();
                     }
                  } catch (DuplicateMemberException var16) {
                  }
               } else {
                  if (Modifier.isFinal(method.getModifiers())) {
                     finalMethods.add(methodSignature);
                  }

                  if (method.isBridge()) {
                     BridgeMethod bridgeMethod = new BridgeMethod(methodSignature, method.getGenericReturnType());
                     if (!this.hasAbstractPackagePrivateSuperClassWithImplementation(cls, bridgeMethod)) {
                        declaredBridgeMethods.add(bridgeMethod);
                     }
                  }
               }
            }

            processedBridgeMethods.addAll(declaredBridgeMethods);
         }

         declaredBridgeMethods = new HashSet(this.getAdditionalInterfaces());
         if (this.interfacesToInspect != null) {
            declaredBridgeMethods.addAll(this.interfacesToInspect);
         }

         Iterator var19 = declaredBridgeMethods.iterator();

         while(var19.hasNext()) {
            Class c = (Class)var19.next();
            Method[] var21 = c.getMethods();
            int var22 = var21.length;

            for(int var23 = 0; var23 < var22; ++var23) {
               Method method = var21[var23];
               MethodSignature signature = new MethodSignatureImpl(method);
               if (this.enhancedMethodSignatures.contains(signature) && !this.bridgeMethodsContainsMethod(processedBridgeMethods, signature, (Type)null, Modifier.isAbstract(method.getModifiers()))) {
                  try {
                     MethodInformation methodInfo = new RuntimeMethodInformation(method);
                     ClassMethod classMethod;
                     if (this.interceptedMethodSignatures.contains(signature) && Reflections.isDefault(method)) {
                        this.createDelegateMethod(proxyClassType, method, methodInfo);
                        classMethod = proxyClassType.addMethod(method);
                        this.addConstructedGuardToMethodBody(classMethod);
                        this.createForwardingMethodBody(classMethod, methodInfo, staticConstructor);
                        BeanLogger.LOG.addingMethodToProxy(method);
                     } else if (Reflections.isDefault(method)) {
                        this.createDelegateMethod(proxyClassType, method, methodInfo);
                     } else {
                        classMethod = proxyClassType.addMethod(method);
                        this.createSpecialMethodBody(classMethod, methodInfo, staticConstructor);
                        BeanLogger.LOG.addingMethodToProxy(method);
                     }
                  } catch (DuplicateMemberException var17) {
                  }
               }

               if (method.isBridge()) {
                  processedBridgeMethods.add(new BridgeMethod(signature, method.getGenericReturnType()));
               }
            }
         }

      } catch (Exception var18) {
         throw new WeldException(var18);
      }
   }

   private boolean hasAbstractPackagePrivateSuperClassWithImplementation(Class clazz, BridgeMethod bridgeMethod) {
      for(Class superClass = clazz.getSuperclass(); superClass != null; superClass = superClass.getSuperclass()) {
         if (Modifier.isAbstract(superClass.getModifiers()) && Reflections.isPackagePrivate(superClass.getModifiers())) {
            Method[] var4 = superClass.getDeclaredMethods();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Method method = var4[var6];
               if (bridgeMethod.signature.matches(method) && method.getGenericReturnType().equals(bridgeMethod.returnType) && !Reflections.isAbstract(method)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private boolean bridgeMethodsContainsMethod(Set processedBridgeMethods, MethodSignature signature, Type returnType, boolean isMethodAbstract) {
      Iterator var5 = processedBridgeMethods.iterator();

      BridgeMethod bridgeMethod;
      do {
         if (!var5.hasNext()) {
            return false;
         }

         bridgeMethod = (BridgeMethod)var5.next();
      } while(!bridgeMethod.signature.equals(signature));

      if (returnType != null) {
         if (!bridgeMethod.returnType.equals(Object.class) && !isMethodAbstract) {
            if (bridgeMethod.returnType instanceof Class && returnType instanceof TypeVariable) {
               return true;
            } else {
               return bridgeMethod.returnType.equals(returnType);
            }
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   protected void createForwardingMethodBody(ClassMethod classMethod, MethodInformation method, ClassMethod staticConstructor) {
      this.createInterceptorBody(classMethod, method, true, staticConstructor);
   }

   protected void createInterceptorBody(ClassMethod method, MethodInformation methodInfo, boolean delegateToSuper, ClassMethod staticConstructor) {
      this.invokeMethodHandler(method, methodInfo, true, DEFAULT_METHOD_RESOLVER, delegateToSuper, staticConstructor);
   }

   private void createDelegateToSuper(ClassMethod classMethod, MethodInformation method) {
      this.createDelegateToSuper(classMethod, method, classMethod.getClassFile().getSuperclass());
   }

   private void createDelegateToSuper(ClassMethod classMethod, MethodInformation method, String className) {
      CodeAttribute b = classMethod.getCodeAttribute();
      b.aload(0);
      b.loadMethodParameters();
      b.invokespecial(className, method.getName(), method.getDescriptor());
      b.returnInstruction();
   }

   protected void invokeMethodHandler(ClassMethod method, MethodInformation methodInfo, boolean addReturnInstruction, BytecodeMethodResolver bytecodeMethodResolver, boolean addProceed, ClassMethod staticConstructor) {
      CodeAttribute b = method.getCodeAttribute();
      b.aload(0);
      this.getMethodHandlerField(method.getClassFile(), b);
      if (addProceed) {
         b.dup();
         b.invokestatic(InterceptionDecorationContext.class.getName(), "getStack", "()" + DescriptorUtils.makeDescriptor(InterceptionDecorationContext.Stack.class));
         if (!Reflections.isDefault(methodInfo.getMethod()) && !Modifier.isPrivate(method.getAccessFlags())) {
            b.dupX1();
            b.invokevirtual(COMBINED_INTERCEPTOR_AND_DECORATOR_STACK_METHOD_HANDLER_CLASS_NAME, "isDisabledHandler", "(" + DescriptorUtils.makeDescriptor(InterceptionDecorationContext.Stack.class) + ")" + "Z");
            b.iconst(0);
            BranchEnd invokeSuperDirectly = b.ifIcmpeq();
            b.pop2();
            b.aload(0);
            b.loadMethodParameters();
            b.invokespecial(methodInfo.getDeclaringClass(), methodInfo.getName(), methodInfo.getDescriptor());
            b.returnInstruction();
            b.branchEnd(invokeSuperDirectly);
         }
      } else {
         b.aconstNull();
      }

      b.aload(0);
      bytecodeMethodResolver.getDeclaredMethod(method, methodInfo.getDeclaringClass(), methodInfo.getName(), methodInfo.getParameterTypes(), staticConstructor);
      if (addProceed) {
         if (Modifier.isPrivate(method.getAccessFlags())) {
            bytecodeMethodResolver.getDeclaredMethod(method, methodInfo.getDeclaringClass(), methodInfo.getName(), methodInfo.getParameterTypes(), staticConstructor);
         } else {
            bytecodeMethodResolver.getDeclaredMethod(method, method.getClassFile().getName(), methodInfo.getName() + "$$super", methodInfo.getParameterTypes(), staticConstructor);
         }
      } else {
         b.aconstNull();
      }

      b.iconst(methodInfo.getParameterTypes().length);
      b.anewarray(Object.class.getName());
      int localVariableCount = 1;

      for(int i = 0; i < methodInfo.getParameterTypes().length; ++i) {
         String typeString = methodInfo.getParameterTypes()[i];
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

      b.invokeinterface(StackAwareMethodHandler.class.getName(), "invoke", "Ljava/lang/Object;", INVOKE_METHOD_PARAMETERS);
      if (addReturnInstruction) {
         if (methodInfo.getReturnType().equals("V")) {
            b.returnInstruction();
         } else if (DescriptorUtils.isPrimitive(methodInfo.getReturnType())) {
            Boxing.unbox(b, method.getReturnType());
            b.returnInstruction();
         } else {
            b.checkcast(BytecodeUtils.getName(methodInfo.getReturnType()));
            b.returnInstruction();
         }
      }

   }

   protected void addSpecialMethods(ClassFile proxyClassType, ClassMethod staticConstructor) {
      try {
         Method[] var3 = LifecycleMixin.class.getMethods();
         int var4 = var3.length;

         Method getMethodHandlerMethod;
         for(int var5 = 0; var5 < var4; ++var5) {
            getMethodHandlerMethod = var3[var5];
            BeanLogger.LOG.addingMethodToProxy(getMethodHandlerMethod);
            MethodInformation methodInfo = new RuntimeMethodInformation(getMethodHandlerMethod);
            this.createInterceptorBody(proxyClassType.addMethod(getMethodHandlerMethod), methodInfo, false, staticConstructor);
         }

         Method getInstanceMethod = TargetInstanceProxy.class.getMethod("weld_getTargetInstance");
         Method getInstanceClassMethod = TargetInstanceProxy.class.getMethod("weld_getTargetClass");
         generateGetTargetInstanceBody(proxyClassType.addMethod(getInstanceMethod));
         generateGetTargetClassBody(proxyClassType.addMethod(getInstanceClassMethod));
         Method setMethodHandlerMethod = ProxyObject.class.getMethod("weld_setHandler", MethodHandler.class);
         this.generateSetMethodHandlerBody(proxyClassType.addMethod(setMethodHandlerMethod));
         getMethodHandlerMethod = ProxyObject.class.getMethod("weld_getHandler");
         this.generateGetMethodHandlerBody(proxyClassType.addMethod(getMethodHandlerMethod));
      } catch (Exception var8) {
         throw new WeldException(var8);
      }
   }

   private static void generateGetTargetInstanceBody(ClassMethod method) {
      CodeAttribute b = method.getCodeAttribute();
      b.aload(0);
      b.returnInstruction();
   }

   private static void generateGetTargetClassBody(ClassMethod method) {
      CodeAttribute b = method.getCodeAttribute();
      BytecodeUtils.pushClassType(b, method.getClassFile().getSuperclass());
      b.returnInstruction();
   }

   public Class getBeanType() {
      return this.proxiedBeanType;
   }

   protected Class getMethodHandlerType() {
      return CombinedInterceptorAndDecoratorStackMethodHandler.class;
   }

   protected boolean isUsingProxyInstantiator() {
      return false;
   }

   private void createDelegateMethod(ClassFile proxyClassType, Method method, MethodInformation methodInformation) {
      int modifiers = (method.getModifiers() | 4096 | 2) & -2 & -5;
      ClassMethod delegatingMethod = proxyClassType.addMethod(modifiers, method.getName() + "$$super", DescriptorUtils.makeDescriptor(method.getReturnType()), DescriptorUtils.parameterDescriptors(method.getParameterTypes()));
      delegatingMethod.addCheckedExceptions((Class[])method.getExceptionTypes());
      this.createDelegateToSuper(delegatingMethod, methodInformation);
   }

   private void invokePrivateMethodHandler(CodeAttribute b, ClassMethod classMethod, MethodInformation methodInfo, ClassMethod staticConstructor) {
      try {
         classMethod.getClassFile().addField(2, "privateMethodHandler", MethodHandler.class);
      } catch (DuplicateMemberException var8) {
      }

      b.aload(0);
      b.getfield(classMethod.getClassFile().getName(), "privateMethodHandler", DescriptorUtils.makeDescriptor(MethodHandler.class));
      b.aload(0);
      DEFAULT_METHOD_RESOLVER.getDeclaredMethod(classMethod, methodInfo.getDeclaringClass(), methodInfo.getName(), methodInfo.getParameterTypes(), staticConstructor);
      b.aconstNull();
      b.iconst(methodInfo.getParameterTypes().length);
      b.anewarray(Object.class.getName());
      int localVariableCount = 1;

      for(int i = 0; i < methodInfo.getParameterTypes().length; ++i) {
         String typeString = methodInfo.getParameterTypes()[i];
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
      if (!methodInfo.getReturnType().equals("V")) {
         if (DescriptorUtils.isPrimitive(methodInfo.getReturnType())) {
            Boxing.unbox(b, methodInfo.getReturnType());
         } else {
            b.checkcast(BytecodeUtils.getName(methodInfo.getReturnType()));
         }
      }

   }

   public static void setPrivateMethodHandler(Object instance) {
      if (instance instanceof ProxyObject && instance.getClass().isSynthetic() && instance.getClass().getName().endsWith("Subclass") && SecurityActions.hasDeclaredField(instance.getClass(), "privateMethodHandler")) {
         try {
            Field privateMethodHandlerField = SecurityActions.getDeclaredField(instance.getClass(), "privateMethodHandler");
            SecurityActions.ensureAccessible(privateMethodHandlerField);
            privateMethodHandlerField.set(instance, PrivateMethodHandler.INSTANCE);
         } catch (NoSuchFieldException var2) {
         } catch (IllegalAccessException | IllegalArgumentException var3) {
            throw new RuntimeException(var3);
         }
      }

   }

   private static class BridgeMethod {
      private final Type returnType;
      private final MethodSignature signature;

      public BridgeMethod(MethodSignature signature, Type returnType) {
         this.signature = signature;
         this.returnType = returnType;
      }

      public int hashCode() {
         int prime = true;
         int result = 1;
         result = 31 * result + (this.returnType == null ? 0 : this.returnType.hashCode());
         result = 31 * result + (this.signature == null ? 0 : this.signature.hashCode());
         return result;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (!(obj instanceof BridgeMethod)) {
            return false;
         } else {
            BridgeMethod other = (BridgeMethod)obj;
            if (this.returnType == null) {
               if (other.returnType != null) {
                  return false;
               }
            } else if (!this.returnType.equals(other.returnType)) {
               return false;
            }

            if (this.signature == null) {
               if (other.signature != null) {
                  return false;
               }
            } else if (!this.signature.equals(other.signature)) {
               return false;
            }

            return true;
         }
      }

      public String toString() {
         return "method " + this.returnType + " " + this.signature.getMethodName() + Arrays.toString(this.signature.getParameterTypes()).replace('[', '(').replace(']', ')');
      }
   }
}
