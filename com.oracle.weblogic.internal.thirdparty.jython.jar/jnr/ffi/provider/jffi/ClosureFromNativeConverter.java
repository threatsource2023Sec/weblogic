package jnr.ffi.provider.jffi;

import com.kenai.jffi.CallContext;
import com.kenai.jffi.Invoker;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;
import jnr.ffi.CallingConvention;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.annotations.StdCall;
import jnr.ffi.mapper.DefaultSignatureType;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;
import jnr.ffi.mapper.FromNativeType;
import jnr.ffi.mapper.MethodResultContext;
import jnr.ffi.mapper.SignatureType;
import jnr.ffi.mapper.SignatureTypeMapper;
import jnr.ffi.provider.InAccessibleMemoryIO;
import jnr.ffi.provider.ParameterType;
import jnr.ffi.provider.ResultType;
import org.python.objectweb.asm.ClassReader;
import org.python.objectweb.asm.ClassVisitor;
import org.python.objectweb.asm.ClassWriter;

public abstract class ClosureFromNativeConverter implements FromNativeConverter {
   private static final AtomicLong nextClassID = new AtomicLong(0L);

   public Class nativeType() {
      return Pointer.class;
   }

   public static FromNativeConverter getInstance(Runtime runtime, SignatureType type, AsmClassLoader classLoader, SignatureTypeMapper typeMapper) {
      return newClosureConverter(runtime, classLoader, type.getDeclaredType(), typeMapper);
   }

   private static FromNativeConverter newClosureConverter(Runtime runtime, AsmClassLoader classLoader, Class closureClass, SignatureTypeMapper typeMapper) {
      ClassWriter cw = new ClassWriter(2);
      ClassVisitor cv = AsmLibraryLoader.DEBUG ? AsmUtil.newCheckClassAdapter(cw) : cw;
      String className = CodegenUtils.p(closureClass) + "$jnr$fromNativeConverter$" + nextClassID.getAndIncrement();
      AsmBuilder builder = new AsmBuilder(runtime, className, (ClassVisitor)cv, classLoader);
      ((ClassVisitor)cv).visit(50, 17, className, (String)null, CodegenUtils.p(AbstractClosurePointer.class), new String[]{CodegenUtils.p(closureClass)});
      ((ClassVisitor)cv).visitAnnotation(CodegenUtils.ci(FromNativeConverter.NoContext.class), true);
      generateInvocation(runtime, builder, closureClass, typeMapper);
      SkinnyMethodAdapter init = new SkinnyMethodAdapter((ClassVisitor)cv, 1, "<init>", CodegenUtils.sig(Void.TYPE, Runtime.class, Long.TYPE, Object[].class), (String)null, (String[])null);
      init.start();
      init.aload(0);
      init.aload(1);
      init.lload(2);
      init.invokespecial(CodegenUtils.p(AbstractClosurePointer.class), "<init>", CodegenUtils.sig(Void.TYPE, Runtime.class, Long.TYPE));
      builder.emitFieldInitialization(init, 4);
      init.voidreturn();
      init.visitMaxs(10, 10);
      init.visitEnd();
      Class implClass = loadClass(classLoader, className, cw);

      try {
         return new ProxyConverter(runtime, implClass.getConstructor(Runtime.class, Long.TYPE, Object[].class), builder.getObjectFieldValues());
      } catch (Throwable var11) {
         throw new RuntimeException(var11);
      }
   }

   private static Class loadClass(AsmClassLoader classLoader, String className, ClassWriter cw) {
      try {
         byte[] bytes = cw.toByteArray();
         if (AsmLibraryLoader.DEBUG) {
            ClassVisitor trace = AsmUtil.newTraceClassVisitor(new PrintWriter(System.err));
            (new ClassReader(bytes)).accept(trace, 0);
         }

         return classLoader.defineClass(className.replace("/", "."), bytes);
      } catch (Throwable var5) {
         throw new RuntimeException(var5);
      }
   }

   private static void generateInvocation(Runtime runtime, AsmBuilder builder, Class closureClass, SignatureTypeMapper typeMapper) {
      Method closureMethod = ClosureUtil.getDelegateMethod(closureClass);
      FromNativeContext resultContext = new MethodResultContext(runtime, closureMethod);
      SignatureType signatureType = DefaultSignatureType.create(closureMethod.getReturnType(), (FromNativeContext)resultContext);
      FromNativeType fromNativeType = typeMapper.getFromNativeType(signatureType, resultContext);
      FromNativeConverter fromNativeConverter = fromNativeType != null ? fromNativeType.getFromNativeConverter() : null;
      ResultType resultType = InvokerUtil.getResultType(runtime, closureMethod.getReturnType(), resultContext.getAnnotations(), (FromNativeConverter)fromNativeConverter, resultContext);
      ParameterType[] parameterTypes = InvokerUtil.getParameterTypes(runtime, typeMapper, closureMethod);
      CallingConvention callingConvention = closureClass.isAnnotationPresent(StdCall.class) ? CallingConvention.STDCALL : CallingConvention.DEFAULT;
      CallContext callContext = InvokerUtil.getCallContext(resultType, parameterTypes, callingConvention, true);
      LocalVariableAllocator localVariableAllocator = new LocalVariableAllocator(parameterTypes);
      Class[] javaParameterTypes = new Class[parameterTypes.length];

      for(int i = 0; i < parameterTypes.length; ++i) {
         javaParameterTypes[i] = parameterTypes[i].getDeclaredType();
      }

      SkinnyMethodAdapter mv = new SkinnyMethodAdapter(builder.getClassVisitor(), 17, closureMethod.getName(), CodegenUtils.sig(resultType.getDeclaredType(), javaParameterTypes), (String)null, (String[])null);
      mv.start();
      mv.getstatic(CodegenUtils.p(AbstractClosurePointer.class), "ffi", CodegenUtils.ci(Invoker.class));
      mv.aload(0);
      mv.getfield(builder.getClassNamePath(), builder.getCallContextFieldName(callContext), CodegenUtils.ci(CallContext.class));
      mv.aload(0);
      mv.getfield(CodegenUtils.p(AbstractClosurePointer.class), "functionAddress", CodegenUtils.ci(Long.TYPE));
      BaseMethodGenerator[] generators = new BaseMethodGenerator[]{new FastIntMethodGenerator(), new FastLongMethodGenerator(), new FastNumericMethodGenerator(), new BufferMethodGenerator()};
      BaseMethodGenerator[] var17 = generators;
      int var18 = generators.length;

      for(int var19 = 0; var19 < var18; ++var19) {
         BaseMethodGenerator generator = var17[var19];
         if (generator.isSupported(resultType, parameterTypes, callingConvention)) {
            generator.generate(builder, mv, localVariableAllocator, callContext, resultType, parameterTypes, false);
         }
      }

      mv.visitMaxs(100, 10 + localVariableAllocator.getSpaceUsed());
      mv.visitEnd();
   }

   public abstract static class AbstractClosurePointer extends InAccessibleMemoryIO {
      public static final Invoker ffi = Invoker.getInstance();
      protected final long functionAddress;

      protected AbstractClosurePointer(Runtime runtime, long functionAddress) {
         super(runtime, functionAddress, true);
         this.functionAddress = functionAddress;
      }

      public final long size() {
         return 0L;
      }
   }

   public static final class ProxyConverter extends ClosureFromNativeConverter {
      private final Runtime runtime;
      private final Constructor closureConstructor;
      private final Object[] initFields;

      public ProxyConverter(Runtime runtime, Constructor closureConstructor, Object[] initFields) {
         this.runtime = runtime;
         this.closureConstructor = closureConstructor;
         this.initFields = (Object[])initFields.clone();
      }

      public Object fromNative(Pointer nativeValue, FromNativeContext context) {
         try {
            return this.closureConstructor.newInstance(this.runtime, nativeValue.address(), this.initFields);
         } catch (Throwable var4) {
            throw new RuntimeException(var4);
         }
      }
   }
}
