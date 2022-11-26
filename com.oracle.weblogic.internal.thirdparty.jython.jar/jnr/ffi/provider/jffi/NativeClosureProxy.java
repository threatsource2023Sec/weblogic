package jnr.ffi.provider.jffi;

import java.io.PrintWriter;
import java.lang.ref.Reference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;
import jnr.ffi.NativeType;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.provider.FromNativeType;
import jnr.ffi.provider.ToNativeType;
import org.python.objectweb.asm.ClassReader;
import org.python.objectweb.asm.ClassVisitor;
import org.python.objectweb.asm.ClassWriter;

public abstract class NativeClosureProxy {
   protected final Runtime runtime;
   volatile Reference closureReference;
   public static final boolean DEBUG = Boolean.getBoolean("jnr.ffi.compile.dump");
   private static final AtomicLong nextClassID = new AtomicLong(0L);

   protected NativeClosureProxy(NativeRuntime runtime) {
      this.runtime = runtime;
   }

   protected Object getCallable() {
      Object callable = this.closureReference != null ? this.closureReference.get() : null;
      if (callable != null) {
         return callable;
      } else {
         throw new NullPointerException("callable is null");
      }
   }

   static Factory newProxyFactory(Runtime runtime, Method callMethod, ToNativeType resultType, FromNativeType[] parameterTypes, AsmClassLoader classLoader) {
      String closureProxyClassName = CodegenUtils.p(NativeClosureProxy.class) + "$$impl$$" + nextClassID.getAndIncrement();
      ClassWriter closureClassWriter = new ClassWriter(2);
      ClassVisitor closureClassVisitor = DEBUG ? AsmUtil.newCheckClassAdapter(closureClassWriter) : closureClassWriter;
      AsmBuilder builder = new AsmBuilder(runtime, closureProxyClassName, (ClassVisitor)closureClassVisitor, classLoader);
      ((ClassVisitor)closureClassVisitor).visit(50, 17, closureProxyClassName, (String)null, CodegenUtils.p(NativeClosureProxy.class), new String[0]);
      Class[] nativeParameterClasses = new Class[parameterTypes.length];

      for(int i = 0; i < parameterTypes.length; ++i) {
         nativeParameterClasses[i] = getNativeClass(parameterTypes[i].getNativeType());
      }

      Class nativeResultClass = getNativeClass(resultType.getNativeType());
      SkinnyMethodAdapter mv = new SkinnyMethodAdapter((ClassVisitor)closureClassVisitor, 17, "invoke", CodegenUtils.sig(nativeResultClass, nativeParameterClasses), (String)null, (String[])null);
      mv.start();
      mv.aload(0);
      mv.invokevirtual(NativeClosureProxy.class, "getCallable", Object.class);
      mv.checkcast(CodegenUtils.p(callMethod.getDeclaringClass()));
      LocalVariable[] parameterVariables = AsmUtil.getParameterVariables(nativeParameterClasses);
      LocalVariableAllocator localVariableAllocator = new LocalVariableAllocator(nativeParameterClasses);

      for(int i = 0; i < parameterTypes.length; ++i) {
         FromNativeType parameterType = parameterTypes[i];
         Class parameterClass = parameterType.effectiveJavaType();
         if (!isParameterTypeSupported(parameterClass)) {
            throw new IllegalArgumentException("unsupported closure parameter type " + parameterTypes[i].getDeclaredType());
         }

         AsmUtil.load(mv, nativeParameterClasses[i], parameterVariables[i]);
         if (!parameterClass.isPrimitive()) {
            AsmUtil.emitFromNativeConversion(builder, mv, parameterTypes[i], nativeParameterClasses[i]);
         } else {
            NumberUtil.convertPrimitive(mv, nativeParameterClasses[i], parameterClass, parameterType.getNativeType());
         }
      }

      if (callMethod.getDeclaringClass().isInterface()) {
         mv.invokeinterface(CodegenUtils.p(callMethod.getDeclaringClass()), callMethod.getName(), CodegenUtils.sig(callMethod.getReturnType(), callMethod.getParameterTypes()));
      } else {
         mv.invokevirtual(CodegenUtils.p(callMethod.getDeclaringClass()), callMethod.getName(), CodegenUtils.sig(callMethod.getReturnType(), callMethod.getParameterTypes()));
      }

      if (!isReturnTypeSupported(resultType.effectiveJavaType())) {
         throw new IllegalArgumentException("unsupported closure return type " + resultType.getDeclaredType());
      } else {
         AsmUtil.emitToNativeConversion(builder, mv, resultType);
         if (!resultType.effectiveJavaType().isPrimitive()) {
            if (Number.class.isAssignableFrom(resultType.effectiveJavaType())) {
               AsmUtil.unboxNumber(mv, resultType.effectiveJavaType(), nativeResultClass, resultType.getNativeType());
            } else if (Boolean.class.isAssignableFrom(resultType.effectiveJavaType())) {
               AsmUtil.unboxBoolean(mv, nativeResultClass);
            } else if (Pointer.class.isAssignableFrom(resultType.effectiveJavaType())) {
               AsmUtil.unboxPointer(mv, nativeResultClass);
            }
         }

         AsmUtil.emitReturnOp(mv, nativeResultClass);
         mv.visitMaxs(10, 10 + localVariableAllocator.getSpaceUsed());
         mv.visitEnd();
         SkinnyMethodAdapter closureInit = new SkinnyMethodAdapter((ClassVisitor)closureClassVisitor, 1, "<init>", CodegenUtils.sig(Void.TYPE, NativeRuntime.class, Object[].class), (String)null, (String[])null);
         closureInit.start();
         closureInit.aload(0);
         closureInit.aload(1);
         closureInit.invokespecial(CodegenUtils.p(NativeClosureProxy.class), "<init>", CodegenUtils.sig(Void.TYPE, NativeRuntime.class));
         AsmBuilder.ObjectField[] fields = builder.getObjectFieldArray();
         Object[] fieldObjects = new Object[fields.length];

         Class klass;
         for(int i = 0; i < fieldObjects.length; ++i) {
            fieldObjects[i] = fields[i].value;
            String fieldName = fields[i].name;
            builder.getClassVisitor().visitField(18, fieldName, CodegenUtils.ci(fields[i].klass), (String)null, (Object)null);
            closureInit.aload(0);
            closureInit.aload(2);
            closureInit.pushInt(i);
            closureInit.aaload();
            if (fields[i].klass.isPrimitive()) {
               klass = AsmUtil.unboxedType(fields[i].klass);
               closureInit.checkcast(klass);
               AsmUtil.unboxNumber(closureInit, klass, fields[i].klass);
            } else {
               closureInit.checkcast(fields[i].klass);
            }

            closureInit.putfield(builder.getClassNamePath(), fieldName, CodegenUtils.ci(fields[i].klass));
         }

         closureInit.voidreturn();
         closureInit.visitMaxs(10, 10);
         closureInit.visitEnd();
         ((ClassVisitor)closureClassVisitor).visitEnd();

         try {
            byte[] closureImpBytes = closureClassWriter.toByteArray();
            if (DEBUG) {
               ClassVisitor trace = AsmUtil.newTraceClassVisitor(new PrintWriter(System.err));
               (new ClassReader(closureImpBytes)).accept(trace, 0);
            }

            ClassLoader cl = NativeClosureFactory.class.getClassLoader();
            if (cl == null) {
               cl = Thread.currentThread().getContextClassLoader();
            }

            if (cl == null) {
               cl = ClassLoader.getSystemClassLoader();
            }

            klass = builder.getClassLoader().defineClass(CodegenUtils.c(closureProxyClassName), closureImpBytes);
            Constructor constructor = null;

            try {
               constructor = klass.getConstructor(NativeRuntime.class, Object[].class);
            } catch (NoSuchMethodException var22) {
               constructor = klass.getConstructors()[0];
            }

            return new Factory(runtime, constructor, klass.getMethod("invoke", nativeParameterClasses), fieldObjects);
         } catch (Throwable var23) {
            throw new RuntimeException(var23);
         }
      }
   }

   private static boolean isReturnTypeSupported(Class type) {
      return type.isPrimitive() || Boolean.TYPE == type || Boolean.class == type || Byte.class == type || Short.class == type || Integer.class == type || Long.class == type || Float.class == type || Double.class == type || Pointer.class == type;
   }

   private static boolean isParameterTypeSupported(Class type) {
      return type.isPrimitive() || Boolean.TYPE == type || Boolean.class == type || Byte.class == type || Short.class == type || Integer.class == type || Long.class == type || Float.class == type || Double.class == type || Pointer.class == type;
   }

   static Class getNativeClass(NativeType nativeType) {
      switch (nativeType) {
         case SCHAR:
         case UCHAR:
            return Byte.TYPE;
         case SSHORT:
         case USHORT:
            return Short.TYPE;
         case SINT:
         case UINT:
            return Integer.TYPE;
         case SLONG:
         case ULONG:
         case ADDRESS:
            return NumberUtil.sizeof(nativeType) <= 4 ? Integer.TYPE : Long.TYPE;
         case SLONGLONG:
         case ULONGLONG:
            return Long.TYPE;
         case FLOAT:
            return Float.TYPE;
         case DOUBLE:
            return Double.TYPE;
         case VOID:
            return Void.TYPE;
         default:
            throw new IllegalArgumentException("unsupported native type: " + nativeType);
      }
   }

   static class Factory {
      private final Runtime runtime;
      private final Constructor constructor;
      private final Object[] objectFields;
      private final Method invokeMethod;

      Factory(Runtime runtime, Constructor constructor, Method invokeMethod, Object[] objectFields) {
         this.runtime = runtime;
         this.constructor = constructor;
         this.invokeMethod = invokeMethod;
         this.objectFields = objectFields;
      }

      NativeClosureProxy newClosureProxy() {
         try {
            return (NativeClosureProxy)this.constructor.newInstance(this.runtime, this.objectFields);
         } catch (Throwable var2) {
            throw new RuntimeException(var2);
         }
      }

      Method getInvokeMethod() {
         return this.invokeMethod;
      }
   }
}
