package jnr.ffi.provider.jffi;

import com.kenai.jffi.CallContext;
import com.kenai.jffi.Function;
import com.kenai.jffi.Invoker;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.provider.ParameterType;
import jnr.ffi.provider.ResultType;
import jnr.ffi.provider.ToNativeType;

abstract class BaseMethodGenerator implements MethodGenerator {
   public void generate(AsmBuilder builder, String functionName, Function function, ResultType resultType, ParameterType[] parameterTypes, boolean ignoreError) {
      Class[] javaParameterTypes = new Class[parameterTypes.length];

      for(int i = 0; i < parameterTypes.length; ++i) {
         javaParameterTypes[i] = parameterTypes[i].getDeclaredType();
      }

      SkinnyMethodAdapter mv = new SkinnyMethodAdapter(builder.getClassVisitor(), 17, functionName, CodegenUtils.sig(resultType.getDeclaredType(), javaParameterTypes), (String)null, (String[])null);
      mv.start();
      mv.getstatic(CodegenUtils.p(AbstractAsmLibraryInterface.class), "ffi", CodegenUtils.ci(Invoker.class));
      mv.aload(0);
      mv.getfield(builder.getClassNamePath(), builder.getCallContextFieldName(function.getCallContext()), CodegenUtils.ci(CallContext.class));
      mv.aload(0);
      mv.getfield(builder.getClassNamePath(), builder.getFunctionAddressFieldName(function), CodegenUtils.ci(Long.TYPE));
      LocalVariableAllocator localVariableAllocator = new LocalVariableAllocator(parameterTypes);
      this.generate(builder, mv, localVariableAllocator, function.getCallContext(), resultType, parameterTypes, ignoreError);
      mv.visitMaxs(100, localVariableAllocator.getSpaceUsed());
      mv.visitEnd();
   }

   abstract void generate(AsmBuilder var1, SkinnyMethodAdapter var2, LocalVariableAllocator var3, CallContext var4, ResultType var5, ParameterType[] var6, boolean var7);

   static LocalVariable loadAndConvertParameter(AsmBuilder builder, SkinnyMethodAdapter mv, LocalVariableAllocator localVariableAllocator, LocalVariable parameter, ToNativeType parameterType) {
      AsmUtil.load(mv, parameterType.getDeclaredType(), parameter);
      AsmUtil.emitToNativeConversion(builder, mv, parameterType);
      if (parameterType.getToNativeConverter() != null) {
         LocalVariable converted = localVariableAllocator.allocate(parameterType.getToNativeConverter().nativeType());
         mv.astore(converted);
         mv.aload(converted);
         return converted;
      } else {
         return parameter;
      }
   }

   static boolean isPostInvokeRequired(ParameterType[] parameterTypes) {
      ParameterType[] var1 = parameterTypes;
      int var2 = parameterTypes.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ParameterType parameterType = var1[var3];
         if (parameterType.getToNativeConverter() instanceof ToNativeConverter.PostInvocation) {
            return true;
         }
      }

      return false;
   }

   static void emitEpilogue(final AsmBuilder builder, final SkinnyMethodAdapter mv, final ResultType resultType, final ParameterType[] parameterTypes, final LocalVariable[] parameters, final LocalVariable[] converted, final Runnable sessionCleanup) {
      final Class unboxedResultType = AsmUtil.unboxedReturnType(resultType.effectiveJavaType());
      if (!isPostInvokeRequired(parameterTypes) && sessionCleanup == null) {
         AsmUtil.emitFromNativeConversion(builder, mv, resultType, unboxedResultType);
      } else {
         AsmUtil.tryfinally(mv, new Runnable() {
            public void run() {
               AsmUtil.emitFromNativeConversion(builder, mv, resultType, unboxedResultType);
               mv.nop();
            }
         }, new Runnable() {
            public void run() {
               BaseMethodGenerator.emitPostInvoke(builder, mv, parameterTypes, parameters, converted);
               if (sessionCleanup != null) {
                  sessionCleanup.run();
               }

            }
         });
      }

      AsmUtil.emitReturnOp(mv, resultType.getDeclaredType());
   }

   static void emitPostInvoke(AsmBuilder builder, SkinnyMethodAdapter mv, ParameterType[] parameterTypes, LocalVariable[] parameters, LocalVariable[] converted) {
      for(int i = 0; i < converted.length; ++i) {
         if (converted[i] != null && parameterTypes[i].getToNativeConverter() instanceof ToNativeConverter.PostInvocation) {
            mv.aload(0);
            AsmBuilder.ObjectField toNativeConverterField = builder.getToNativeConverterField(parameterTypes[i].getToNativeConverter());
            mv.getfield(builder.getClassNamePath(), toNativeConverterField.name, CodegenUtils.ci(toNativeConverterField.klass));
            if (!ToNativeConverter.PostInvocation.class.isAssignableFrom(toNativeConverterField.klass)) {
               mv.checkcast(ToNativeConverter.PostInvocation.class);
            }

            mv.aload(parameters[i]);
            mv.aload(converted[i]);
            if (parameterTypes[i].getToNativeContext() != null) {
               AsmUtil.getfield(mv, builder, builder.getToNativeContextField(parameterTypes[i].getToNativeContext()));
            } else {
               mv.aconst_null();
            }

            mv.invokestatic(AsmRuntime.class, "postInvoke", Void.TYPE, ToNativeConverter.PostInvocation.class, Object.class, Object.class, ToNativeContext.class);
         }
      }

   }
}
