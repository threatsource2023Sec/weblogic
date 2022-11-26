package org.jboss.weld.bean.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.enterprise.inject.spi.Bean;
import org.jboss.classfilewriter.ClassFile;
import org.jboss.classfilewriter.ClassMethod;
import org.jboss.classfilewriter.code.CodeAttribute;
import org.jboss.classfilewriter.util.DescriptorUtils;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.injection.FieldInjectionPoint;
import org.jboss.weld.injection.ParameterInjectionPoint;
import org.jboss.weld.injection.attributes.WeldInjectionPointAttributes;
import org.jboss.weld.interceptor.util.proxy.TargetInstanceProxy;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.security.GetDeclaredMethodAction;
import org.jboss.weld.security.GetDeclaredMethodsAction;
import org.jboss.weld.util.bytecode.BytecodeUtils;
import org.jboss.weld.util.bytecode.MethodInformation;
import org.jboss.weld.util.bytecode.RuntimeMethodInformation;
import org.jboss.weld.util.bytecode.StaticMethodInformation;

public class DecoratorProxyFactory extends ProxyFactory {
   public static final String PROXY_SUFFIX = "DecoratorProxy";
   private static final String INIT_MH_METHOD_NAME = "_initMH";
   private final WeldInjectionPointAttributes delegateInjectionPoint;
   private final Field delegateField;
   private final TargetInstanceBytecodeMethodResolver targetInstanceBytecodeMethodResolver = new TargetInstanceBytecodeMethodResolver();

   public DecoratorProxyFactory(String contextId, Class proxyType, WeldInjectionPointAttributes delegateInjectionPoint, Bean bean) {
      super(contextId, proxyType, Collections.emptySet(), bean);
      this.delegateInjectionPoint = delegateInjectionPoint;
      if (delegateInjectionPoint instanceof FieldInjectionPoint) {
         this.delegateField = (Field)((FieldInjectionPoint)delegateInjectionPoint).getMember();
      } else {
         this.delegateField = null;
      }

   }

   private void addHandlerInitializerMethod(ClassFile proxyClassType, ClassMethod staticConstructor) throws Exception {
      ClassMethod classMethod = proxyClassType.addMethod(2, "_initMH", "V", new String[]{"Ljava/lang/Object;"});
      CodeAttribute b = classMethod.getCodeAttribute();
      b.aload(0);
      StaticMethodInformation methodInfo = new StaticMethodInformation("_initMH", new Class[]{Object.class}, Void.TYPE, classMethod.getClassFile().getName());
      this.invokeMethodHandler(classMethod, methodInfo, false, DEFAULT_METHOD_RESOLVER, staticConstructor);
      b.checkcast(MethodHandler.class);
      b.putfield(classMethod.getClassFile().getName(), "methodHandler", DescriptorUtils.makeDescriptor(MethodHandler.class));
      b.returnInstruction();
      BeanLogger.LOG.createdMethodHandlerInitializerForDecoratorProxy(this.getBeanType());
   }

   protected void addAdditionalInterfaces(Set interfaces) {
      interfaces.add(DecoratorProxy.class);
   }

   protected void addMethodsFromClass(ClassFile proxyClassType, ClassMethod staticConstructor) {
      Method initializerMethod = null;
      int delegateParameterPosition = -1;
      if (this.delegateInjectionPoint instanceof ParameterInjectionPoint) {
         ParameterInjectionPoint parameterIP = (ParameterInjectionPoint)this.delegateInjectionPoint;
         if (parameterIP.getMember() instanceof Method) {
            initializerMethod = (Method)parameterIP.getMember();
            delegateParameterPosition = parameterIP.getAnnotated().getPosition();
         }
      }

      try {
         if (delegateParameterPosition >= 0) {
            this.addHandlerInitializerMethod(proxyClassType, staticConstructor);
         }

         Class cls = this.getBeanType();
         Set methods = new LinkedHashSet();
         this.decoratorMethods(cls, methods);
         Iterator var7 = methods.iterator();

         while(true) {
            Method method;
            RuntimeMethodInformation methodInfo;
            do {
               if (!var7.hasNext()) {
                  return;
               }

               method = (Method)var7.next();
               methodInfo = new RuntimeMethodInformation(method);
            } while(method.getDeclaringClass().getName().equals("java.lang.Object") && !method.getName().equals("toString"));

            if (delegateParameterPosition >= 0 && initializerMethod.equals(method)) {
               this.createDelegateInitializerCode(proxyClassType.addMethod(method), methodInfo, delegateParameterPosition);
            }

            if (Modifier.isAbstract(method.getModifiers())) {
               this.createAbstractMethodCode(proxyClassType.addMethod(method), methodInfo, staticConstructor);
            }
         }
      } catch (Exception var10) {
         throw new WeldException(var10);
      }
   }

   private void decoratorMethods(Class cls, Set all) {
      if (cls != null) {
         all.addAll(Arrays.asList((Object[])AccessController.doPrivileged(new GetDeclaredMethodsAction(cls))));
         this.decoratorMethods(cls.getSuperclass(), all);
         Class[] var3 = cls.getInterfaces();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Class ifc = var3[var5];
            Method[] methods = ifc.getMethods();
            Method[] var8 = methods;
            int var9 = methods.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               Method m = var8[var10];
               boolean isEqual = false;
               Iterator var13 = all.iterator();

               while(var13.hasNext()) {
                  Method a = (Method)var13.next();
                  if (isEqual(m, a)) {
                     isEqual = true;
                     break;
                  }
               }

               if (!isEqual) {
                  all.add(m);
               }
            }
         }

      }
   }

   private static boolean isEqual(Method m, Method a) {
      if (m.getName().equals(a.getName()) && m.getParameterTypes().length == a.getParameterTypes().length && m.getReturnType().isAssignableFrom(a.getReturnType())) {
         for(int i = 0; i < m.getParameterTypes().length; ++i) {
            if (!m.getParameterTypes()[i].isAssignableFrom(a.getParameterTypes()[i])) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   protected String getProxyNameSuffix() {
      return "DecoratorProxy";
   }

   protected boolean isUsingProxyInstantiator() {
      return false;
   }

   private void createAbstractMethodCode(ClassMethod classMethod, MethodInformation method, ClassMethod staticConstructor) {
      if (this.delegateField != null && !Modifier.isPrivate(this.delegateField.getModifiers())) {
         CodeAttribute b = classMethod.getCodeAttribute();
         b.aload(0);
         b.getfield(classMethod.getClassFile().getName(), this.delegateField.getName(), DescriptorUtils.makeDescriptor(this.delegateField.getType()));
         b.loadMethodParameters();
         b.invokeinterface(this.delegateField.getType().getName(), method.getName(), method.getDescriptor());
         b.returnInstruction();
      } else if (!Modifier.isPrivate(method.getMethod().getModifiers())) {
         this.invokeMethodHandler(classMethod, method, true, this.targetInstanceBytecodeMethodResolver, staticConstructor);
      } else {
         this.createInterceptorBody(classMethod, method, staticConstructor);
      }

   }

   private void createDelegateInitializerCode(ClassMethod classMethod, MethodInformation initializerMethodInfo, int delegateParameterPosition) {
      CodeAttribute b = classMethod.getCodeAttribute();
      b.aload(0);
      int localVariables = 1;
      int actualDelegateParameterPosition = 0;

      for(int i = 0; i < initializerMethodInfo.getMethod().getParameterTypes().length; ++i) {
         if (i == delegateParameterPosition) {
            actualDelegateParameterPosition = localVariables;
         }

         Class type = initializerMethodInfo.getMethod().getParameterTypes()[i];
         BytecodeUtils.addLoadInstruction(b, DescriptorUtils.makeDescriptor(type), localVariables);
         if (type != Long.TYPE && type != Double.TYPE) {
            ++localVariables;
         } else {
            localVariables += 2;
         }
      }

      b.invokespecial(classMethod.getClassFile().getSuperclass(), initializerMethodInfo.getName(), initializerMethodInfo.getDescriptor());
      b.aload(0);
      b.aload(actualDelegateParameterPosition);
      b.invokevirtual(classMethod.getClassFile().getName(), "_initMH", "(Ljava/lang/Object;)V");
      b.returnInstruction();
   }

   protected class TargetInstanceBytecodeMethodResolver implements BytecodeMethodResolver {
      private static final String JAVA_LANG_CLASS_CLASS_NAME = "java.lang.Class";

      public void getDeclaredMethod(ClassMethod classMethod, String declaringClass, String methodName, String[] parameterTypes, ClassMethod staticConstructor) {
         MethodInformation methodInfo = new StaticMethodInformation("weld_getTargetClass", new String[0], "Ljava/lang/Class;", TargetInstanceProxy.class.getName());
         DecoratorProxyFactory.this.invokeMethodHandler(classMethod, methodInfo, false, ProxyFactory.DEFAULT_METHOD_RESOLVER, staticConstructor);
         CodeAttribute code = classMethod.getCodeAttribute();
         code.checkcast("java/lang/Class");
         code.ldc(methodName);
         code.iconst(parameterTypes.length);
         code.anewarray("java.lang.Class");

         for(int i = 0; i < parameterTypes.length; ++i) {
            code.dup();
            code.iconst(i);
            String type = parameterTypes[i];
            BytecodeUtils.pushClassType(code, type);
            code.aastore();
         }

         code.invokestatic(GetDeclaredMethodAction.class.getName(), "wrapException", "(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;)Ljava/security/PrivilegedAction;");
         code.invokestatic(AccessController.class.getName(), "doPrivileged", "(Ljava/security/PrivilegedAction;)Ljava/lang/Object;");
         code.checkcast(Method.class);
      }
   }
}
