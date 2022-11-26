package jnr.ffi.provider.jffi;

import com.kenai.jffi.CallContext;
import com.kenai.jffi.HeapInvocationBuffer;
import com.kenai.jffi.Invoker;
import com.kenai.jffi.ObjectParameterStrategy;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import jnr.ffi.CallingConvention;
import jnr.ffi.NativeType;
import jnr.ffi.provider.InvocationSession;
import jnr.ffi.provider.ParameterType;
import jnr.ffi.provider.ResultType;

final class BufferMethodGenerator extends BaseMethodGenerator {
   static final Map marshalOps;
   static final Map invokeOps;

   void generate(AsmBuilder builder, SkinnyMethodAdapter mv, LocalVariableAllocator localVariableAllocator, CallContext callContext, ResultType resultType, ParameterType[] parameterTypes, boolean ignoreError) {
      this.generateBufferInvocation(builder, mv, localVariableAllocator, callContext, resultType, parameterTypes);
   }

   public boolean isSupported(ResultType resultType, ParameterType[] parameterTypes, CallingConvention callingConvention) {
      return true;
   }

   private static void emitPrimitiveOp(SkinnyMethodAdapter mv, ParameterType parameterType, ToNativeOp op) {
      MarshalOp marshalOp = (MarshalOp)marshalOps.get(parameterType.getNativeType());
      if (marshalOp == null) {
         throw new IllegalArgumentException("unsupported parameter type " + parameterType);
      } else {
         op.emitPrimitive(mv, marshalOp.primitiveClass, parameterType.getNativeType());
         mv.invokevirtual(HeapInvocationBuffer.class, marshalOp.methodName, Void.TYPE, marshalOp.primitiveClass);
      }
   }

   static boolean isSessionRequired(ParameterType parameterType) {
      return false;
   }

   static boolean isSessionRequired(ParameterType[] parameterTypes) {
      ParameterType[] var1 = parameterTypes;
      int var2 = parameterTypes.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ParameterType parameterType = var1[var3];
         if (isSessionRequired(parameterType)) {
            return true;
         }
      }

      return false;
   }

   void generateBufferInvocation(AsmBuilder builder, final SkinnyMethodAdapter mv, LocalVariableAllocator localVariableAllocator, CallContext callContext, ResultType resultType, ParameterType[] parameterTypes) {
      boolean sessionRequired = isSessionRequired(parameterTypes);
      final LocalVariable session = localVariableAllocator.allocate(InvocationSession.class);
      if (sessionRequired) {
         mv.newobj(CodegenUtils.p(InvocationSession.class));
         mv.dup();
         mv.invokespecial(InvocationSession.class, "<init>", Void.TYPE);
         mv.astore(session);
      }

      mv.aload(0);
      mv.getfield(builder.getClassNamePath(), builder.getCallContextFieldName(callContext), CodegenUtils.ci(CallContext.class));
      mv.invokestatic(AsmRuntime.class, "newHeapInvocationBuffer", HeapInvocationBuffer.class, CallContext.class);
      LocalVariable[] parameters = AsmUtil.getParameterVariables(parameterTypes);
      LocalVariable[] converted = new LocalVariable[parameterTypes.length];
      LocalVariable[] strategies = new LocalVariable[parameterTypes.length];

      for(int i = 0; i < parameterTypes.length; ++i) {
         mv.dup();
         if (isSessionRequired(parameterTypes[i])) {
            mv.aload(session);
         }

         converted[i] = loadAndConvertParameter(builder, mv, localVariableAllocator, parameters[i], parameterTypes[i]);
         Class javaParameterType = parameterTypes[i].effectiveJavaType();
         ToNativeOp op = ToNativeOp.get(parameterTypes[i]);
         if (op != null && op.isPrimitive()) {
            emitPrimitiveOp(mv, parameterTypes[i], op);
         } else {
            if (!AbstractFastNumericMethodGenerator.hasPointerParameterStrategy(javaParameterType)) {
               throw new IllegalArgumentException("unsupported parameter type " + parameterTypes[i]);
            }

            AbstractFastNumericMethodGenerator.emitParameterStrategyLookup(mv, javaParameterType);
            mv.astore(strategies[i] = localVariableAllocator.allocate(PointerParameterStrategy.class));
            mv.aload(converted[i]);
            mv.aload(strategies[i]);
            mv.pushInt(AsmUtil.getNativeArrayFlags(parameterTypes[i].annotations()));
            mv.invokevirtual(HeapInvocationBuffer.class, "putObject", Void.TYPE, Object.class, ObjectParameterStrategy.class, Integer.TYPE);
         }
      }

      InvokeOp iop = (InvokeOp)invokeOps.get(resultType.getNativeType());
      if (iop == null) {
         throw new IllegalArgumentException("unsupported return type " + resultType.getDeclaredType());
      } else {
         mv.invokevirtual(Invoker.class, iop.methodName, iop.primitiveClass, CallContext.class, Long.TYPE, HeapInvocationBuffer.class);
         NumberUtil.convertPrimitive(mv, iop.primitiveClass, AsmUtil.unboxedReturnType(resultType.effectiveJavaType()), resultType.getNativeType());
         emitEpilogue(builder, mv, resultType, parameterTypes, parameters, converted, sessionRequired ? new Runnable() {
            public void run() {
               mv.aload(session);
               mv.invokevirtual(CodegenUtils.p(InvocationSession.class), "finish", "()V");
            }
         } : null);
      }
   }

   static {
      Map mops = new EnumMap(NativeType.class);
      Map iops = new EnumMap(NativeType.class);
      mops.put(NativeType.SCHAR, new MarshalOp("Byte", Integer.TYPE));
      mops.put(NativeType.UCHAR, new MarshalOp("Byte", Integer.TYPE));
      mops.put(NativeType.SSHORT, new MarshalOp("Short", Integer.TYPE));
      mops.put(NativeType.USHORT, new MarshalOp("Short", Integer.TYPE));
      mops.put(NativeType.SINT, new MarshalOp("Int", Integer.TYPE));
      mops.put(NativeType.UINT, new MarshalOp("Int", Integer.TYPE));
      mops.put(NativeType.SLONGLONG, new MarshalOp("Long", Long.TYPE));
      mops.put(NativeType.ULONGLONG, new MarshalOp("Long", Long.TYPE));
      mops.put(NativeType.FLOAT, new MarshalOp("Float", Float.TYPE));
      mops.put(NativeType.DOUBLE, new MarshalOp("Double", Double.TYPE));
      mops.put(NativeType.ADDRESS, new MarshalOp("Address", Long.TYPE));
      if (NumberUtil.sizeof(NativeType.SLONG) == 4) {
         mops.put(NativeType.SLONG, new MarshalOp("Int", Integer.TYPE));
         mops.put(NativeType.ULONG, new MarshalOp("Int", Integer.TYPE));
      } else {
         mops.put(NativeType.SLONG, new MarshalOp("Long", Long.TYPE));
         mops.put(NativeType.ULONG, new MarshalOp("Long", Long.TYPE));
      }

      iops.put(NativeType.SCHAR, new InvokeOp("Int", Integer.TYPE));
      iops.put(NativeType.UCHAR, new InvokeOp("Int", Integer.TYPE));
      iops.put(NativeType.SSHORT, new InvokeOp("Int", Integer.TYPE));
      iops.put(NativeType.USHORT, new InvokeOp("Int", Integer.TYPE));
      iops.put(NativeType.SINT, new InvokeOp("Int", Integer.TYPE));
      iops.put(NativeType.UINT, new InvokeOp("Int", Integer.TYPE));
      iops.put(NativeType.VOID, new InvokeOp("Int", Integer.TYPE));
      iops.put(NativeType.SLONGLONG, new InvokeOp("Long", Long.TYPE));
      iops.put(NativeType.ULONGLONG, new InvokeOp("Long", Long.TYPE));
      iops.put(NativeType.FLOAT, new InvokeOp("Float", Float.TYPE));
      iops.put(NativeType.DOUBLE, new InvokeOp("Double", Double.TYPE));
      iops.put(NativeType.ADDRESS, new InvokeOp("Address", Long.TYPE));
      if (NumberUtil.sizeof(NativeType.SLONG) == 4) {
         iops.put(NativeType.SLONG, new InvokeOp("Int", Integer.TYPE));
         iops.put(NativeType.ULONG, new InvokeOp("Int", Integer.TYPE));
      } else {
         iops.put(NativeType.SLONG, new InvokeOp("Long", Long.TYPE));
         iops.put(NativeType.ULONG, new InvokeOp("Long", Long.TYPE));
      }

      marshalOps = Collections.unmodifiableMap(mops);
      invokeOps = Collections.unmodifiableMap(iops);
   }

   private static final class InvokeOp extends Operation {
      private InvokeOp(String methodName, Class primitiveClass) {
         super("invoke" + methodName, primitiveClass, null);
      }

      // $FF: synthetic method
      InvokeOp(String x0, Class x1, Object x2) {
         this(x0, x1);
      }
   }

   private static final class MarshalOp extends Operation {
      private MarshalOp(String methodName, Class primitiveClass) {
         super("put" + methodName, primitiveClass, null);
      }

      // $FF: synthetic method
      MarshalOp(String x0, Class x1, Object x2) {
         this(x0, x1);
      }
   }

   private abstract static class Operation {
      final String methodName;
      final Class primitiveClass;

      private Operation(String methodName, Class primitiveClass) {
         this.methodName = methodName;
         this.primitiveClass = primitiveClass;
      }

      // $FF: synthetic method
      Operation(String x0, Class x1, Object x2) {
         this(x0, x1);
      }
   }
}
