package jnr.ffi.provider.jffi;

import com.kenai.jffi.CallContext;
import com.kenai.jffi.Function;
import com.kenai.jffi.Invoker;
import com.kenai.jffi.ObjectParameterInfo;
import com.kenai.jffi.Platform;
import java.util.concurrent.atomic.AtomicLong;
import jnr.ffi.CallingConvention;
import jnr.ffi.NativeType;
import jnr.ffi.Pointer;
import jnr.ffi.provider.ParameterType;
import jnr.ffi.provider.ResultType;
import jnr.ffi.provider.SigType;
import org.python.objectweb.asm.Label;

class X86MethodGenerator implements MethodGenerator {
   private static final boolean ENABLED = Util.getBooleanProperty("jnr.ffi.x86asm.enabled", true);
   private final AtomicLong nextMethodID = new AtomicLong(0L);
   private final StubCompiler compiler;

   X86MethodGenerator(StubCompiler compiler) {
      this.compiler = compiler;
   }

   public boolean isSupported(ResultType resultType, ParameterType[] parameterTypes, CallingConvention callingConvention) {
      if (!ENABLED) {
         return false;
      } else {
         Platform platform = Platform.getPlatform();
         if (platform.getOS().equals(Platform.OS.WINDOWS)) {
            return false;
         } else if (!platform.getCPU().equals(Platform.CPU.I386) && !platform.getCPU().equals(Platform.CPU.X86_64)) {
            return false;
         } else if (!callingConvention.equals(CallingConvention.DEFAULT)) {
            return false;
         } else {
            int objectCount = 0;

            for(int i = 0; i < parameterTypes.length; ++i) {
               if (!isSupportedParameter(parameterTypes[i])) {
                  return false;
               }

               if (isSupportedObjectParameterType(parameterTypes[i])) {
                  ++objectCount;
               }
            }

            if (objectCount <= 0 || parameterTypes.length <= 4 && objectCount <= 3) {
               return isSupportedResult(resultType) && this.compiler.canCompile(resultType, parameterTypes, callingConvention);
            } else {
               return false;
            }
         }
      }
   }

   public void generate(AsmBuilder builder, String functionName, Function function, ResultType resultType, ParameterType[] parameterTypes, boolean ignoreError) {
      Class[] nativeParameterTypes = new Class[parameterTypes.length];
      boolean wrapperNeeded = false;

      for(int i = 0; i < parameterTypes.length; ++i) {
         wrapperNeeded |= parameterTypes[i].getToNativeConverter() != null || !parameterTypes[i].effectiveJavaType().isPrimitive();
         if (!parameterTypes[i].effectiveJavaType().isPrimitive()) {
            nativeParameterTypes[i] = getNativeClass(parameterTypes[i].getNativeType());
         } else {
            nativeParameterTypes[i] = parameterTypes[i].effectiveJavaType();
         }
      }

      wrapperNeeded |= resultType.getFromNativeConverter() != null || !resultType.effectiveJavaType().isPrimitive();
      Class nativeReturnType;
      if (resultType.effectiveJavaType().isPrimitive()) {
         nativeReturnType = resultType.effectiveJavaType();
      } else {
         nativeReturnType = getNativeClass(resultType.getNativeType());
      }

      String stubName = functionName + (wrapperNeeded ? "$jni$" + this.nextMethodID.incrementAndGet() : "");
      builder.getClassVisitor().visitMethod(273 | (wrapperNeeded ? 8 : 0), stubName, CodegenUtils.sig(nativeReturnType, nativeParameterTypes), (String)null, (String[])null);
      this.compiler.compile(function, stubName, resultType, parameterTypes, nativeReturnType, nativeParameterTypes, CallingConvention.DEFAULT, !ignoreError);
      if (wrapperNeeded) {
         generateWrapper(builder, functionName, function, resultType, parameterTypes, stubName, nativeReturnType, nativeParameterTypes);
      }

   }

   private static void generateWrapper(AsmBuilder builder, String functionName, Function function, ResultType resultType, ParameterType[] parameterTypes, String nativeMethodName, Class nativeReturnType, Class[] nativeParameterTypes) {
      Class[] javaParameterTypes = new Class[parameterTypes.length];

      for(int i = 0; i < parameterTypes.length; ++i) {
         javaParameterTypes[i] = parameterTypes[i].getDeclaredType();
      }

      SkinnyMethodAdapter mv = new SkinnyMethodAdapter(builder.getClassVisitor(), 17, functionName, CodegenUtils.sig(resultType.getDeclaredType(), javaParameterTypes), (String)null, (String[])null);
      mv.setMethodVisitor(AsmUtil.newTraceMethodVisitor(mv.getMethodVisitor()));
      mv.start();
      LocalVariableAllocator localVariableAllocator = new LocalVariableAllocator(parameterTypes);
      LocalVariable objCount = localVariableAllocator.allocate(Integer.TYPE);
      LocalVariable[] parameters = AsmUtil.getParameterVariables(parameterTypes);
      LocalVariable[] converted = new LocalVariable[parameterTypes.length];
      int pointerCount = 0;

      Class unboxedResultType;
      for(int i = 0; i < parameterTypes.length; ++i) {
         Class javaParameterClass = parameterTypes[i].effectiveJavaType();
         unboxedResultType = nativeParameterTypes[i];
         converted[i] = BaseMethodGenerator.loadAndConvertParameter(builder, mv, localVariableAllocator, parameters[i], parameterTypes[i]);
         ToNativeOp toNativeOp = ToNativeOp.get(parameterTypes[i]);
         if (toNativeOp != null && toNativeOp.isPrimitive()) {
            toNativeOp.emitPrimitive(mv, unboxedResultType, parameterTypes[i].getNativeType());
         } else if (AbstractFastNumericMethodGenerator.hasPointerParameterStrategy(javaParameterClass)) {
            pointerCount = AbstractFastNumericMethodGenerator.emitDirectCheck(mv, javaParameterClass, unboxedResultType, converted[i], objCount, pointerCount);
         } else if (!javaParameterClass.isPrimitive()) {
            throw new IllegalArgumentException("unsupported type " + javaParameterClass);
         }
      }

      Label hasObjects = new Label();
      Label convertResult = new Label();
      if (pointerCount > 0) {
         mv.iload(objCount);
         mv.ifne(hasObjects);
      }

      mv.invokestatic(builder.getClassNamePath(), nativeMethodName, CodegenUtils.sig(nativeReturnType, nativeParameterTypes));
      unboxedResultType = AsmUtil.unboxedReturnType(resultType.effectiveJavaType());
      NumberUtil.convertPrimitive(mv, nativeReturnType, unboxedResultType);
      if (pointerCount > 0) {
         mv.label(convertResult);
      }

      BaseMethodGenerator.emitEpilogue(builder, mv, resultType, parameterTypes, parameters, converted, (Runnable)null);
      if (pointerCount > 0) {
         mv.label(hasObjects);
         LocalVariable[] tmp = new LocalVariable[parameterTypes.length];

         int i;
         for(i = parameterTypes.length - 1; i >= 0; --i) {
            tmp[i] = localVariableAllocator.allocate(Long.TYPE);
            if (Float.TYPE == nativeParameterTypes[i]) {
               mv.invokestatic(Float.class, "floatToRawIntBits", Integer.TYPE, Float.TYPE);
               mv.i2l();
            } else if (Double.TYPE == nativeParameterTypes[i]) {
               mv.invokestatic(Double.class, "doubleToRawLongBits", Long.TYPE, Double.TYPE);
            } else {
               NumberUtil.convertPrimitive(mv, nativeParameterTypes[i], Long.TYPE, parameterTypes[i].getNativeType());
            }

            mv.lstore(tmp[i]);
         }

         mv.getstatic(CodegenUtils.p(AbstractAsmLibraryInterface.class), "ffi", CodegenUtils.ci(Invoker.class));
         mv.aload(0);
         mv.getfield(builder.getClassNamePath(), builder.getCallContextFieldName(function), CodegenUtils.ci(CallContext.class));
         mv.aload(0);
         mv.getfield(builder.getClassNamePath(), builder.getFunctionAddressFieldName(function), CodegenUtils.ci(Long.TYPE));
         mv.lload(tmp);
         mv.iload(objCount);

         for(i = 0; i < parameterTypes.length; ++i) {
            LocalVariable[] strategies = new LocalVariable[parameterTypes.length];
            Class javaParameterType = parameterTypes[i].effectiveJavaType();
            if (AbstractFastNumericMethodGenerator.hasPointerParameterStrategy(javaParameterType)) {
               mv.aload(converted[i]);
               AbstractFastNumericMethodGenerator.emitParameterStrategyLookup(mv, javaParameterType);
               mv.astore(strategies[i] = localVariableAllocator.allocate(ParameterStrategy.class));
               mv.aload(converted[i]);
               mv.aload(strategies[i]);
               ObjectParameterInfo info = ObjectParameterInfo.create(i, AsmUtil.getNativeArrayFlags(parameterTypes[i].annotations()));
               mv.aload(0);
               mv.getfield(builder.getClassNamePath(), builder.getObjectParameterInfoName(info), CodegenUtils.ci(ObjectParameterInfo.class));
            }
         }

         mv.invokevirtual(CodegenUtils.p(Invoker.class), AbstractFastNumericMethodGenerator.getObjectParameterMethodName(parameterTypes.length), AbstractFastNumericMethodGenerator.getObjectParameterMethodSignature(parameterTypes.length, pointerCount));
         if (Float.TYPE == nativeReturnType) {
            NumberUtil.narrow(mv, Long.TYPE, Integer.TYPE);
            mv.invokestatic(Float.class, "intBitsToFloat", Float.TYPE, Integer.TYPE);
         } else if (Double.TYPE == nativeReturnType) {
            mv.invokestatic(Double.class, "longBitsToDouble", Double.TYPE, Long.TYPE);
         } else if (Void.TYPE == nativeReturnType) {
            mv.pop2();
         }

         NumberUtil.convertPrimitive(mv, Long.TYPE, unboxedResultType, resultType.getNativeType());
         mv.go_to(convertResult);
      }

      mv.visitMaxs(100, localVariableAllocator.getSpaceUsed());
      mv.visitEnd();
   }

   void attach(Class clazz) {
      this.compiler.attach(clazz);
   }

   private static boolean isSupportedObjectParameterType(ParameterType type) {
      return Pointer.class.isAssignableFrom(type.effectiveJavaType());
   }

   private static boolean isSupportedType(SigType type) {
      switch (type.getNativeType()) {
         case SCHAR:
         case UCHAR:
         case SSHORT:
         case USHORT:
         case SINT:
         case UINT:
         case SLONG:
         case ULONG:
         case SLONGLONG:
         case ULONGLONG:
         case FLOAT:
         case DOUBLE:
            return true;
         default:
            return false;
      }
   }

   static boolean isSupportedResult(ResultType resultType) {
      return isSupportedType(resultType) || Void.TYPE == resultType.effectiveJavaType() || resultType.getNativeType() == NativeType.ADDRESS;
   }

   static boolean isSupportedParameter(ParameterType parameterType) {
      return isSupportedType(parameterType) || isSupportedObjectParameterType(parameterType);
   }

   static Class getNativeClass(NativeType nativeType) {
      switch (nativeType) {
         case SCHAR:
         case UCHAR:
         case SSHORT:
         case USHORT:
         case SINT:
         case UINT:
         case SLONG:
         case ULONG:
         case SLONGLONG:
         case ULONGLONG:
         case ADDRESS:
            return NumberUtil.sizeof(nativeType) <= 4 ? Integer.TYPE : Long.TYPE;
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
}
