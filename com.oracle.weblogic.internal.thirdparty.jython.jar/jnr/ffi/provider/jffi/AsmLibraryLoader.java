package jnr.ffi.provider.jffi;

import com.kenai.jffi.Function;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import jnr.ffi.CallingConvention;
import jnr.ffi.LibraryOption;
import jnr.ffi.Runtime;
import jnr.ffi.annotations.Synchronized;
import jnr.ffi.mapper.CachingTypeMapper;
import jnr.ffi.mapper.CompositeTypeMapper;
import jnr.ffi.mapper.DefaultSignatureType;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeType;
import jnr.ffi.mapper.FunctionMapper;
import jnr.ffi.mapper.MethodResultContext;
import jnr.ffi.mapper.SignatureType;
import jnr.ffi.mapper.SignatureTypeMapper;
import jnr.ffi.mapper.SignatureTypeMapperAdapter;
import jnr.ffi.mapper.TypeMapper;
import jnr.ffi.provider.IdentityFunctionMapper;
import jnr.ffi.provider.InterfaceScanner;
import jnr.ffi.provider.Invoker;
import jnr.ffi.provider.NativeFunction;
import jnr.ffi.provider.NativeVariable;
import jnr.ffi.provider.NullTypeMapper;
import jnr.ffi.provider.ParameterType;
import jnr.ffi.provider.ResultType;
import jnr.ffi.util.Annotations;
import org.python.objectweb.asm.ClassReader;
import org.python.objectweb.asm.ClassVisitor;
import org.python.objectweb.asm.ClassWriter;

public class AsmLibraryLoader extends LibraryLoader {
   public static final boolean DEBUG = Boolean.getBoolean("jnr.ffi.compile.dump");
   private static final AtomicLong nextClassID = new AtomicLong(0L);
   private static final AtomicLong uniqueId = new AtomicLong(0L);
   private static final ThreadLocal classLoader = new ThreadLocal();
   private final NativeRuntime runtime = NativeRuntime.getInstance();

   Object loadLibrary(NativeLibrary library, Class interfaceClass, Map libraryOptions) {
      AsmClassLoader oldClassLoader = (AsmClassLoader)classLoader.get();
      if (oldClassLoader == null) {
         classLoader.set(new AsmClassLoader(interfaceClass.getClassLoader()));
      }

      Object var5;
      try {
         var5 = this.generateInterfaceImpl(library, interfaceClass, libraryOptions, (AsmClassLoader)classLoader.get());
      } finally {
         if (oldClassLoader == null) {
            classLoader.remove();
         }

      }

      return var5;
   }

   private Object generateInterfaceImpl(NativeLibrary library, Class interfaceClass, Map libraryOptions, AsmClassLoader classLoader) {
      boolean debug = DEBUG && !interfaceClass.isAnnotationPresent(NoTrace.class);
      ClassWriter cw = new ClassWriter(2);
      ClassVisitor cv = debug ? AsmUtil.newCheckClassAdapter(cw) : cw;
      AsmBuilder builder = new AsmBuilder(this.runtime, CodegenUtils.p(interfaceClass) + "$jnr$ffi$" + nextClassID.getAndIncrement(), (ClassVisitor)cv, classLoader);
      ((ClassVisitor)cv).visit(50, 17, builder.getClassNamePath(), (String)null, CodegenUtils.p(AbstractAsmLibraryInterface.class), new String[]{CodegenUtils.p(interfaceClass)});
      FunctionMapper functionMapper = libraryOptions.containsKey(LibraryOption.FunctionMapper) ? (FunctionMapper)libraryOptions.get(LibraryOption.FunctionMapper) : IdentityFunctionMapper.getInstance();
      Object typeMapper;
      if (libraryOptions.containsKey(LibraryOption.TypeMapper)) {
         Object tm = libraryOptions.get(LibraryOption.TypeMapper);
         if (tm instanceof SignatureTypeMapper) {
            typeMapper = (SignatureTypeMapper)tm;
         } else {
            if (!(tm instanceof TypeMapper)) {
               throw new IllegalArgumentException("TypeMapper option is not a valid TypeMapper instance");
            }

            typeMapper = new SignatureTypeMapperAdapter((TypeMapper)tm);
         }
      } else {
         typeMapper = new NullTypeMapper();
      }

      CompositeTypeMapper closureTypeMapper = new CompositeTypeMapper(new SignatureTypeMapper[]{(SignatureTypeMapper)typeMapper, new CachingTypeMapper(new InvokerTypeMapper((NativeClosureManager)null, classLoader, NativeLibraryLoader.ASM_ENABLED)), new CachingTypeMapper(new AnnotationTypeMapper())});
      SignatureTypeMapper typeMapper = new CompositeTypeMapper(new SignatureTypeMapper[]{(SignatureTypeMapper)typeMapper, new CachingTypeMapper(new InvokerTypeMapper(new NativeClosureManager(this.runtime, closureTypeMapper, classLoader), classLoader, NativeLibraryLoader.ASM_ENABLED)), new CachingTypeMapper(new AnnotationTypeMapper())});
      CallingConvention libraryCallingConvention = InvokerUtil.getCallingConvention(interfaceClass, libraryOptions);
      StubCompiler compiler = StubCompiler.newCompiler(this.runtime);
      MethodGenerator[] generators = new MethodGenerator[]{(MethodGenerator)(!interfaceClass.isAnnotationPresent(NoX86.class) ? new X86MethodGenerator(compiler) : new NotImplMethodGenerator()), new FastIntMethodGenerator(), new FastLongMethodGenerator(), new FastNumericMethodGenerator(), new BufferMethodGenerator()};
      DefaultInvokerFactory invokerFactory = new DefaultInvokerFactory(this.runtime, library, typeMapper, functionMapper, libraryCallingConvention, libraryOptions, interfaceClass.isAnnotationPresent(Synchronized.class));
      InterfaceScanner scanner = new InterfaceScanner(interfaceClass, typeMapper, libraryCallingConvention);
      Iterator var17 = scanner.functions().iterator();

      while(true) {
         while(var17.hasNext()) {
            NativeFunction function = (NativeFunction)var17.next();
            if (function.getMethod().isVarArgs()) {
               AsmBuilder.ObjectField field = builder.getObjectField(invokerFactory.createInvoker(function.getMethod()), Invoker.class);
               this.generateVarargsInvocation(builder, function.getMethod(), field);
            } else {
               String functionName = functionMapper.mapFunctionName(function.name(), new NativeFunctionMapperContext(library, function.annotations()));

               try {
                  long functionAddress = library.findSymbolAddress(functionName);
                  FromNativeContext resultContext = new MethodResultContext(this.runtime, function.getMethod());
                  SignatureType signatureType = DefaultSignatureType.create(function.getMethod().getReturnType(), (FromNativeContext)resultContext);
                  ResultType resultType = InvokerUtil.getResultType(this.runtime, function.getMethod().getReturnType(), resultContext.getAnnotations(), (FromNativeType)typeMapper.getFromNativeType(signatureType, resultContext), resultContext);
                  ParameterType[] parameterTypes = InvokerUtil.getParameterTypes(this.runtime, typeMapper, function.getMethod());
                  boolean saveError = jnr.ffi.LibraryLoader.saveError(libraryOptions, function.hasSaveError(), function.hasIgnoreError());
                  Function jffiFunction = new Function(functionAddress, InvokerUtil.getCallContext(resultType, parameterTypes, function.convention(), saveError));
                  MethodGenerator[] var28 = generators;
                  int var29 = generators.length;

                  for(int var30 = 0; var30 < var29; ++var30) {
                     MethodGenerator g = var28[var30];
                     if (g.isSupported(resultType, parameterTypes, function.convention())) {
                        g.generate(builder, function.getMethod().getName(), jffiFunction, resultType, parameterTypes, !saveError);
                        break;
                     }
                  }
               } catch (SymbolNotFoundError var36) {
                  String errorFieldName = "error_" + uniqueId.incrementAndGet();
                  ((ClassVisitor)cv).visitField(26, errorFieldName, CodegenUtils.ci(String.class), (String)null, var36.getMessage());
                  this.generateFunctionNotFound((ClassVisitor)cv, builder.getClassNamePath(), errorFieldName, functionName, function.getMethod().getReturnType(), function.getMethod().getParameterTypes());
               }
            }
         }

         VariableAccessorGenerator variableAccessorGenerator = new VariableAccessorGenerator(this.runtime);
         Iterator var40 = scanner.variables().iterator();

         while(var40.hasNext()) {
            NativeVariable v = (NativeVariable)var40.next();
            Method m = v.getMethod();
            Type variableType = ((ParameterizedType)m.getGenericReturnType()).getActualTypeArguments()[0];
            if (!(variableType instanceof Class)) {
               throw new IllegalArgumentException("unsupported variable class: " + variableType);
            }

            String functionName = functionMapper.mapFunctionName(m.getName(), (FunctionMapper.Context)null);

            try {
               variableAccessorGenerator.generate(builder, interfaceClass, m.getName(), library.findSymbolAddress(functionName), (Class)variableType, Annotations.sortedAnnotationCollection(m.getAnnotations()), typeMapper, classLoader);
            } catch (SymbolNotFoundError var35) {
               String errorFieldName = "error_" + uniqueId.incrementAndGet();
               ((ClassVisitor)cv).visitField(26, errorFieldName, CodegenUtils.ci(String.class), (String)null, var35.getMessage());
               this.generateFunctionNotFound((ClassVisitor)cv, builder.getClassNamePath(), errorFieldName, functionName, m.getReturnType(), m.getParameterTypes());
            }
         }

         SkinnyMethodAdapter init = new SkinnyMethodAdapter((ClassVisitor)cv, 1, "<init>", CodegenUtils.sig(Void.TYPE, Runtime.class, NativeLibrary.class, Object[].class), (String)null, (String[])null);
         init.start();
         init.aload(0);
         init.aload(1);
         init.aload(2);
         init.invokespecial(CodegenUtils.p(AbstractAsmLibraryInterface.class), "<init>", CodegenUtils.sig(Void.TYPE, Runtime.class, NativeLibrary.class));
         builder.emitFieldInitialization(init, 3);
         init.voidreturn();
         init.visitMaxs(10, 10);
         init.visitEnd();
         ((ClassVisitor)cv).visitEnd();

         try {
            byte[] bytes = cw.toByteArray();
            if (debug) {
               ClassVisitor trace = AsmUtil.newTraceClassVisitor(new PrintWriter(System.err));
               (new ClassReader(bytes)).accept(trace, 0);
            }

            Class implClass = classLoader.defineClass(builder.getClassNamePath().replace("/", "."), bytes);
            Constructor cons = implClass.getDeclaredConstructor(Runtime.class, NativeLibrary.class, Object[].class);
            Object result = cons.newInstance(this.runtime, library, builder.getObjectFieldValues());
            System.err.flush();
            System.out.flush();
            compiler.attach(implClass);
            return result;
         } catch (Throwable var34) {
            throw new RuntimeException(var34);
         }
      }
   }

   private void generateFunctionNotFound(ClassVisitor cv, String className, String errorFieldName, String functionName, Class returnType, Class[] parameterTypes) {
      SkinnyMethodAdapter mv = new SkinnyMethodAdapter(cv, 17, functionName, CodegenUtils.sig(returnType, parameterTypes), (String)null, (String[])null);
      mv.start();
      mv.getstatic(className, errorFieldName, CodegenUtils.ci(String.class));
      mv.invokestatic(AsmRuntime.class, "newUnsatisifiedLinkError", UnsatisfiedLinkError.class, String.class);
      mv.athrow();
      mv.visitMaxs(10, 10);
      mv.visitEnd();
   }

   private void generateVarargsInvocation(AsmBuilder builder, Method m, AsmBuilder.ObjectField field) {
      Class[] parameterTypes = m.getParameterTypes();
      SkinnyMethodAdapter mv = new SkinnyMethodAdapter(builder.getClassVisitor(), 17, m.getName(), CodegenUtils.sig(m.getReturnType(), parameterTypes), (String)null, (String[])null);
      mv.start();
      mv.aload(0);
      mv.getfield(builder.getClassNamePath(), field.name, CodegenUtils.ci(Invoker.class));
      mv.aload(0);
      mv.pushInt(parameterTypes.length);
      mv.anewarray(CodegenUtils.p(Object.class));
      int slot = 1;

      for(int i = 0; i < parameterTypes.length; ++i) {
         mv.dup();
         mv.pushInt(i);
         if (parameterTypes[i].equals(Long.TYPE)) {
            mv.lload(slot);
            mv.invokestatic(Long.class, "valueOf", Long.class, Long.TYPE);
            ++slot;
         } else if (parameterTypes[i].equals(Double.TYPE)) {
            mv.dload(slot);
            mv.invokestatic(Double.class, "valueOf", Double.class, Double.TYPE);
            ++slot;
         } else if (parameterTypes[i].equals(Integer.TYPE)) {
            mv.iload(slot);
            mv.invokestatic(Integer.class, "valueOf", Integer.class, Integer.TYPE);
         } else if (parameterTypes[i].equals(Float.TYPE)) {
            mv.fload(slot);
            mv.invokestatic(Float.class, "valueOf", Float.class, Float.TYPE);
         } else if (parameterTypes[i].equals(Short.TYPE)) {
            mv.iload(slot);
            mv.i2s();
            mv.invokestatic(Short.class, "valueOf", Short.class, Short.TYPE);
         } else if (parameterTypes[i].equals(Character.TYPE)) {
            mv.iload(slot);
            mv.i2c();
            mv.invokestatic(Character.class, "valueOf", Character.class, Character.TYPE);
         } else if (parameterTypes[i].equals(Byte.TYPE)) {
            mv.iload(slot);
            mv.i2b();
            mv.invokestatic(Byte.class, "valueOf", Byte.class, Byte.TYPE);
         } else if (parameterTypes[i].equals(Character.TYPE)) {
            mv.iload(slot);
            mv.i2b();
            mv.invokestatic(Boolean.class, "valueOf", Boolean.class, Boolean.TYPE);
         } else {
            mv.aload(slot);
         }

         mv.aastore();
         ++slot;
      }

      mv.invokeinterface(Invoker.class, "invoke", Object.class, Object.class, Object[].class);
      Class returnType = m.getReturnType();
      if (returnType.equals(Long.TYPE)) {
         mv.checkcast(Long.class);
         mv.invokevirtual(Long.class, "longValue", Long.TYPE);
         mv.lreturn();
      } else if (returnType.equals(Double.TYPE)) {
         mv.checkcast(Double.class);
         mv.invokevirtual(Double.class, "doubleValue", Double.TYPE);
         mv.dreturn();
      } else if (returnType.equals(Integer.TYPE)) {
         mv.checkcast(Integer.class);
         mv.invokevirtual(Integer.class, "intValue", Integer.TYPE);
         mv.ireturn();
      } else if (returnType.equals(Float.TYPE)) {
         mv.checkcast(Float.class);
         mv.invokevirtual(Float.class, "floatValue", Float.TYPE);
         mv.freturn();
      } else if (returnType.equals(Short.TYPE)) {
         mv.checkcast(Short.class);
         mv.invokevirtual(Short.class, "shortValue", Short.TYPE);
         mv.ireturn();
      } else if (returnType.equals(Character.TYPE)) {
         mv.checkcast(Character.class);
         mv.invokevirtual(Character.class, "charValue", Character.TYPE);
         mv.ireturn();
      } else if (returnType.equals(Byte.TYPE)) {
         mv.checkcast(Byte.class);
         mv.invokevirtual(Byte.class, "byteValue", Byte.TYPE);
         mv.ireturn();
      } else if (returnType.equals(Boolean.TYPE)) {
         mv.checkcast(Boolean.class);
         mv.invokevirtual(Boolean.class, "booleanValue", Boolean.TYPE);
         mv.ireturn();
      } else if (Void.TYPE.isAssignableFrom(m.getReturnType())) {
         mv.voidreturn();
      } else {
         mv.checkcast(m.getReturnType());
         mv.areturn();
      }

      mv.visitMaxs(100, AsmUtil.calculateLocalVariableSpace(parameterTypes) + 1);
      mv.visitEnd();
   }
}
