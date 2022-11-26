package org.jboss.weld.bean.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.enterprise.inject.spi.Bean;
import org.jboss.classfilewriter.ClassFile;
import org.jboss.classfilewriter.ClassMethod;
import org.jboss.classfilewriter.DuplicateMemberException;
import org.jboss.classfilewriter.code.CodeAttribute;
import org.jboss.classfilewriter.util.Boxing;
import org.jboss.classfilewriter.util.DescriptorUtils;
import org.jboss.weld.annotated.enhanced.MethodSignature;
import org.jboss.weld.annotated.enhanced.jlr.MethodSignatureImpl;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.security.GetDeclaredMethodsAction;
import org.jboss.weld.util.bytecode.BytecodeUtils;
import org.jboss.weld.util.bytecode.MethodInformation;
import org.jboss.weld.util.bytecode.RuntimeMethodInformation;
import org.jboss.weld.util.reflection.Reflections;

public class InterceptedProxyFactory extends ProxyFactory {
   public static final String PROXY_SUFFIX = "InterceptedProxy";
   private static final String JAVA_LANG_OBJECT = "java.lang.Object";
   private final Set enhancedMethodSignatures;
   private final Set interceptedMethodSignatures;
   private final String suffix;
   private final boolean builtFromInterface;
   private Set interfacesToInspect;

   public InterceptedProxyFactory(String contextId, Class proxiedBeanType, Set typeClosure, Set enhancedMethodSignatures, Set interceptedMethodSignatures, String suffix) {
      super(contextId, proxiedBeanType, typeClosure, (Bean)null);
      this.enhancedMethodSignatures = enhancedMethodSignatures;
      this.interceptedMethodSignatures = interceptedMethodSignatures;
      this.suffix = suffix;
      this.builtFromInterface = proxiedBeanType.isInterface();
   }

   protected String getProxyNameSuffix() {
      return "InterceptedProxy" + this.suffix;
   }

   protected void addMethodsFromClass(ClassFile proxyClassType, ClassMethod staticConstructor) {
      try {
         Set finalMethods = new HashSet();
         Set processedBridgeMethods = new HashSet();
         Set classes = new LinkedHashSet();

         for(Class cls = this.getBeanType(); cls != null; cls = cls.getSuperclass()) {
            classes.add(cls);
         }

         classes.add(this.getProxiedBeanType());
         Iterator var19 = classes.iterator();

         Class c;
         int var10;
         while(var19.hasNext()) {
            c = (Class)var19.next();
            Set declaredBridgeMethods = new HashSet();
            Method[] var9 = (Method[])AccessController.doPrivileged(new GetDeclaredMethodsAction(c));
            var10 = var9.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               Method method = var9[var11];
               MethodSignatureImpl methodSignature = new MethodSignatureImpl(method);
               if (this.isMethodAccepted(method, this.getProxySuperclass()) && this.enhancedMethodSignatures.contains(methodSignature) && !finalMethods.contains(methodSignature) && !processedBridgeMethods.contains(methodSignature)) {
                  try {
                     MethodInformation methodInfo = new RuntimeMethodInformation(method);
                     ClassMethod classMethod = proxyClassType.addMethod(method);
                     if (this.interceptedMethodSignatures.contains(methodSignature)) {
                        this.createInterceptedMethod(classMethod, methodInfo, method, staticConstructor);
                        BeanLogger.LOG.addingMethodToProxy(method);
                     } else {
                        this.createNotInterceptedMethod(classMethod, methodInfo, method, staticConstructor);
                     }
                  } catch (DuplicateMemberException var17) {
                  }
               } else {
                  if (Modifier.isFinal(method.getModifiers())) {
                     finalMethods.add(methodSignature);
                  }

                  if (method.isBridge()) {
                     declaredBridgeMethods.add(methodSignature);
                  }
               }
            }

            processedBridgeMethods.addAll(declaredBridgeMethods);
         }

         if (this.builtFromInterface) {
            var19 = this.interfacesToInspect.iterator();

            while(var19.hasNext()) {
               c = (Class)var19.next();
               Method[] var20 = c.getMethods();
               int var21 = var20.length;

               for(var10 = 0; var10 < var21; ++var10) {
                  Method method = var20[var10];
                  MethodSignature signature = new MethodSignatureImpl(method);

                  try {
                     if (this.isMethodAccepted(method, this.getProxySuperclass()) && !processedBridgeMethods.contains(signature)) {
                        MethodInformation methodInfo = new RuntimeMethodInformation(method);
                        ClassMethod classMethod = proxyClassType.addMethod(method);
                        this.createNotInterceptedMethod(classMethod, methodInfo, method, staticConstructor);
                        BeanLogger.LOG.addingMethodToProxy(method);
                     }
                  } catch (DuplicateMemberException var16) {
                  }

                  if (method.isBridge()) {
                     processedBridgeMethods.add(signature);
                  }
               }
            }
         }

      } catch (Exception var18) {
         throw new WeldException(var18);
      }
   }

   public void addInterfacesFromTypeClosure(Set typeClosure, Class proxiedBeanType) {
      Iterator var3 = typeClosure.iterator();

      while(var3.hasNext()) {
         Type type = (Type)var3.next();
         Class c = Reflections.getRawType(type);
         if (c.isInterface()) {
            this.addInterfaceToInspect(c);
         }
      }

   }

   protected boolean isMethodAccepted(Method method, Class proxySuperclass) {
      return super.isMethodAccepted(method, proxySuperclass) && CommonProxiedMethodFilters.NON_PRIVATE.accept(method, proxySuperclass) && !method.isBridge();
   }

   private void createNotInterceptedMethod(ClassMethod classMethod, MethodInformation methodInfo, Method method, ClassMethod staticConstructor) {
      CodeAttribute b = classMethod.getCodeAttribute();
      b.aload(0);
      this.getMethodHandlerField(classMethod.getClassFile(), b);
      b.aload(0);
      DEFAULT_METHOD_RESOLVER.getDeclaredMethod(classMethod, methodInfo.getDeclaringClass(), method.getName(), methodInfo.getParameterTypes(), staticConstructor);
      b.aconstNull();
      b.iconst(method.getParameterTypes().length);
      b.anewarray("java.lang.Object");
      int localVariableCount = 1;

      for(int i = 0; i < method.getParameterTypes().length; ++i) {
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
      if (methodInfo.getReturnType().equals("V")) {
         b.returnInstruction();
      } else if (DescriptorUtils.isPrimitive(methodInfo.getReturnType())) {
         Boxing.unbox(b, classMethod.getReturnType());
         b.returnInstruction();
      } else {
         b.checkcast(BytecodeUtils.getName(methodInfo.getReturnType()));
         b.returnInstruction();
      }

   }

   private void createInterceptedMethod(ClassMethod classMethod, MethodInformation methodInfo, Method method, ClassMethod staticConstructor) {
      CodeAttribute b = classMethod.getCodeAttribute();
      b.aload(0);
      this.getMethodHandlerField(classMethod.getClassFile(), b);
      b.invokestatic(InterceptionDecorationContext.class.getName(), "getStack", "()" + DescriptorUtils.makeDescriptor(InterceptionDecorationContext.Stack.class));
      b.aload(0);
      DEFAULT_METHOD_RESOLVER.getDeclaredMethod(classMethod, methodInfo.getDeclaringClass(), method.getName(), methodInfo.getParameterTypes(), staticConstructor);
      b.dup();
      b.iconst(method.getParameterTypes().length);
      b.anewarray("java.lang.Object");
      int localVariableCount = 1;

      for(int i = 0; i < method.getParameterTypes().length; ++i) {
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

      b.invokeinterface(StackAwareMethodHandler.class.getName(), "invoke", "Ljava/lang/Object;", InterceptedSubclassFactory.INVOKE_METHOD_PARAMETERS);
      if (methodInfo.getReturnType().equals("V")) {
         b.returnInstruction();
      } else if (DescriptorUtils.isPrimitive(methodInfo.getReturnType())) {
         Boxing.unbox(b, classMethod.getReturnType());
         b.returnInstruction();
      } else {
         b.checkcast(BytecodeUtils.getName(methodInfo.getReturnType()));
         b.returnInstruction();
      }

   }

   private void addInterfaceToInspect(Class iface) {
      if (this.interfacesToInspect == null) {
         this.interfacesToInspect = new HashSet();
      }

      this.interfacesToInspect.add(iface);
   }
}
