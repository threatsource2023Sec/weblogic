package org.jboss.weld.bean.proxy;

import java.io.ObjectStreamException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;
import javax.enterprise.inject.spi.Bean;
import org.jboss.classfilewriter.ClassFile;
import org.jboss.classfilewriter.ClassMethod;
import org.jboss.classfilewriter.code.BranchEnd;
import org.jboss.classfilewriter.code.CodeAttribute;
import org.jboss.weld.Container;
import org.jboss.weld.bean.proxy.util.SerializableClientProxy;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.proxy.WeldClientProxy;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.serialization.spi.ContextualStore;
import org.jboss.weld.util.bytecode.MethodInformation;

public class ClientProxyFactory extends ProxyFactory {
   private static final String CLIENT_PROXY_SUFFIX = "ClientProxy";
   private static final String HASH_CODE_METHOD = "hashCode";
   private static final String EMPTY_PARENTHESES = "()";
   private static final String BEAN_ID_FIELD = "BEAN_ID_FIELD";
   private static final String CONTEXT_ID_FIELD = "CONTEXT_ID_FIELD";
   private final BeanIdentifier beanId;
   private volatile Field beanIdField;
   private volatile Field contextIdField;

   public ClientProxyFactory(String contextId, Class proxiedBeanType, Set typeClosure, Bean bean) {
      super(contextId, proxiedBeanType, typeClosure, bean);
      this.beanId = ((ContextualStore)Container.instance(contextId).services().get(ContextualStore.class)).putIfAbsent(bean);
   }

   public Object create(BeanInstance beanInstance) {
      try {
         Object instance = super.create(beanInstance);
         Field f;
         if (this.beanIdField == null) {
            f = SecurityActions.getDeclaredField(instance.getClass(), "BEAN_ID_FIELD");
            SecurityActions.ensureAccessible(f);
            this.beanIdField = f;
         }

         if (this.contextIdField == null) {
            f = SecurityActions.getDeclaredField(instance.getClass(), "CONTEXT_ID_FIELD");
            SecurityActions.ensureAccessible(f);
            this.contextIdField = f;
         }

         this.beanIdField.set(instance, this.beanId);
         this.contextIdField.set(instance, this.getContextId());
         return instance;
      } catch (IllegalAccessException var4) {
         throw new RuntimeException(var4);
      } catch (NoSuchFieldException var5) {
         throw new RuntimeException(var5.getCause());
      }
   }

   protected void addAdditionalInterfaces(Set interfaces) {
      interfaces.add(WeldClientProxy.class);
   }

   protected void addMethods(ClassFile proxyClassType, ClassMethod staticConstructor) {
      super.addMethods(proxyClassType, staticConstructor);
      this.generateWeldClientProxyMethod(proxyClassType);
   }

   private void generateWeldClientProxyMethod(ClassFile proxyClassType) {
      try {
         Method getContextualMetadata = WeldClientProxy.class.getMethod("getMetadata");
         this.generateBodyForWeldClientProxyMethod(proxyClassType.addMethod(getContextualMetadata));
      } catch (Exception var3) {
         throw new WeldException(var3);
      }
   }

   private void generateBodyForWeldClientProxyMethod(ClassMethod method) throws Exception {
      CodeAttribute b = method.getCodeAttribute();
      b.aload(0);
      this.getMethodHandlerField(method.getClassFile(), b);
      b.returnInstruction();
   }

   protected void addFields(ClassFile proxyClassType, List initialValueBytecode) {
      super.addFields(proxyClassType, initialValueBytecode);
      proxyClassType.addField(66, "BEAN_ID_FIELD", BeanIdentifier.class);
      proxyClassType.addField(66, "CONTEXT_ID_FIELD", String.class);
   }

   protected Class getMethodHandlerType() {
      return ProxyMethodHandler.class;
   }

   protected void addSerializationSupport(ClassFile proxyClassType) {
      Class[] exceptions = new Class[]{ObjectStreamException.class};
      ClassMethod writeReplace = proxyClassType.addMethod(2, "writeReplace", "Ljava/lang/Object;", new String[0]);
      writeReplace.addCheckedExceptions(exceptions);
      CodeAttribute b = writeReplace.getCodeAttribute();
      b.newInstruction(SerializableClientProxy.class.getName());
      b.dup();
      b.aload(0);
      b.getfield(proxyClassType.getName(), "BEAN_ID_FIELD", BeanIdentifier.class);
      b.aload(0);
      b.getfield(proxyClassType.getName(), "CONTEXT_ID_FIELD", String.class);
      b.invokespecial(SerializableClientProxy.class.getName(), "<init>", "(Lorg/jboss/weld/serialization/spi/BeanIdentifier;Ljava/lang/String;)V");
      b.returnInstruction();
   }

   protected void createForwardingMethodBody(ClassMethod classMethod, final MethodInformation methodInfo, ClassMethod staticConstructor) {
      final Method method = methodInfo.getMethod();
      boolean bytecodeInvocationAllowed = Modifier.isPublic(method.getModifiers()) && Modifier.isPublic(method.getReturnType().getModifiers());
      Class[] var6 = method.getParameterTypes();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Class paramType = var6[var8];
         if (!Modifier.isPublic(paramType.getModifiers())) {
            bytecodeInvocationAllowed = false;
            break;
         }
      }

      if (!bytecodeInvocationAllowed) {
         this.createInterceptorBody(classMethod, methodInfo, staticConstructor);
      } else {
         (new RunWithinInterceptionDecorationContextGenerator(classMethod, this) {
            void doWork(CodeAttribute b, ClassMethod classMethod) {
               ClientProxyFactory.this.loadBeanInstance(classMethod.getClassFile(), methodInfo, b);
               b.dup();
               String methodDescriptor = methodInfo.getDescriptor();
               b.loadMethodParameters();
               if (method.getDeclaringClass().isInterface()) {
                  b.invokeinterface(methodInfo.getDeclaringClass(), methodInfo.getName(), methodDescriptor);
               } else {
                  b.invokevirtual(methodInfo.getDeclaringClass(), methodInfo.getName(), methodDescriptor);
               }

            }

            void doReturn(CodeAttribute b, ClassMethod classMethod) {
               if (method.getReturnType().isPrimitive()) {
                  b.returnInstruction();
               } else {
                  b.dupX1();
                  BranchEnd returnInstruction = b.ifAcmpeq();
                  b.returnInstruction();
                  b.branchEnd(returnInstruction);
                  b.aload(0);
                  b.checkcast(methodInfo.getMethod().getReturnType().getName());
                  b.returnInstruction();
               }

            }
         }).runStartIfNotEmpty();
      }
   }

   private void loadBeanInstance(ClassFile file, MethodInformation methodInfo, CodeAttribute b) {
      b.aload(0);
      this.getMethodHandlerField(file, b);
      b.invokevirtual(ProxyMethodHandler.class.getName(), "getInstance", "()Ljava/lang/Object;");
      b.checkcast(methodInfo.getDeclaringClass());
   }

   protected void generateHashCodeMethod(ClassFile proxyClassType) {
      ClassMethod method = proxyClassType.addMethod(1, "hashCode", "I", new String[0]);
      CodeAttribute b = method.getCodeAttribute();
      b.loadClass(proxyClassType.getName());
      b.invokevirtual("java.lang.Object", "hashCode", "()I");
      b.returnInstruction();
   }

   protected void generateEqualsMethod(ClassFile proxyClassType) {
      ClassMethod method = proxyClassType.addMethod(1, "equals", "Z", new String[]{"Ljava/lang/Object;"});
      CodeAttribute b = method.getCodeAttribute();
      b.aload(1);
      b.instanceofInstruction(proxyClassType.getName());
      b.returnInstruction();
   }

   protected String getProxyNameSuffix() {
      return "ClientProxy";
   }

   protected boolean isMethodAccepted(Method method, Class proxySuperclass) {
      return super.isMethodAccepted(method, proxySuperclass) && CommonProxiedMethodFilters.NON_PRIVATE.accept(method, proxySuperclass);
   }
}
