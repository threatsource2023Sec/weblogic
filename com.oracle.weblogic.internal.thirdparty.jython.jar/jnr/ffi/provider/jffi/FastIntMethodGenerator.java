package jnr.ffi.provider.jffi;

import com.kenai.jffi.CallContext;
import com.kenai.jffi.Invoker;
import com.kenai.jffi.Platform;
import jnr.ffi.CallingConvention;
import jnr.ffi.NativeType;
import jnr.ffi.Pointer;
import jnr.ffi.provider.ParameterType;
import jnr.ffi.provider.ResultType;
import jnr.ffi.provider.SigType;

final class FastIntMethodGenerator extends AbstractFastNumericMethodGenerator {
   private static final boolean ENABLED = Util.getBooleanProperty("jnr.ffi.fast-int.enabled", true);
   private static final int MAX_FASTINT_PARAMETERS = getMaximumFastIntParameters();
   private static final String[] signatures;
   private static final String[] methodNames = new String[]{"invokeI0", "invokeI1", "invokeI2", "invokeI3", "invokeI4", "invokeI5", "invokeI6"};

   String getInvokerMethodName(ResultType resultType, ParameterType[] parameterTypes, boolean ignoreErrno) {
      int parameterCount = parameterTypes.length;
      if (parameterCount <= MAX_FASTINT_PARAMETERS && parameterCount <= methodNames.length) {
         return methodNames[parameterCount];
      } else {
         throw new IllegalArgumentException("invalid fast-int parameter count: " + parameterCount);
      }
   }

   String getInvokerSignature(int parameterCount, Class nativeIntType) {
      if (parameterCount <= MAX_FASTINT_PARAMETERS && parameterCount <= signatures.length) {
         return signatures[parameterCount];
      } else {
         throw new IllegalArgumentException("invalid fast-int parameter count: " + parameterCount);
      }
   }

   final Class getInvokerType() {
      return Integer.TYPE;
   }

   public boolean isSupported(ResultType resultType, ParameterType[] parameterTypes, CallingConvention callingConvention) {
      int parameterCount = parameterTypes.length;
      if (!ENABLED) {
         return false;
      } else if (callingConvention.equals(CallingConvention.DEFAULT) && parameterCount <= MAX_FASTINT_PARAMETERS) {
         Platform platform = Platform.getPlatform();
         if (platform.getOS().equals(Platform.OS.WINDOWS)) {
            return false;
         } else if (!platform.getCPU().equals(Platform.CPU.I386) && !platform.getCPU().equals(Platform.CPU.X86_64)) {
            return false;
         } else {
            ParameterType[] var6 = parameterTypes;
            int var7 = parameterTypes.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               ParameterType parameterType = var6[var8];
               if (!isFastIntParameter(platform, parameterType)) {
                  return false;
               }
            }

            return isFastIntResult(platform, resultType);
         }
      } else {
         return false;
      }
   }

   static int getMaximumFastIntParameters() {
      try {
         Invoker.class.getDeclaredMethod("invokeI6", CallContext.class, Long.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
         return 6;
      } catch (Throwable var1) {
         return 0;
      }
   }

   static boolean isFastIntType(Platform platform, SigType type) {
      switch (type.getNativeType()) {
         case SCHAR:
         case UCHAR:
         case SSHORT:
         case USHORT:
         case SINT:
         case UINT:
         case SLONG:
         case ULONG:
            return NumberUtil.sizeof(type.getNativeType()) <= 4;
         default:
            return false;
      }
   }

   private static boolean isSupportedPointerParameterType(Class javaParameterType) {
      return Pointer.class.isAssignableFrom(javaParameterType);
   }

   static boolean isFastIntResult(Platform platform, ResultType resultType) {
      return isFastIntType(platform, resultType) || resultType.getNativeType() == NativeType.VOID || resultType.getNativeType() == NativeType.ADDRESS && NumberUtil.sizeof((SigType)resultType) == 4;
   }

   static boolean isFastIntParameter(Platform platform, ParameterType parameterType) {
      return isFastIntType(platform, parameterType) || parameterType.getNativeType() == NativeType.ADDRESS && NumberUtil.sizeof((SigType)parameterType) == 4 && isSupportedPointerParameterType(parameterType.effectiveJavaType());
   }

   static {
      signatures = new String[MAX_FASTINT_PARAMETERS + 1];

      for(int i = 0; i <= MAX_FASTINT_PARAMETERS; ++i) {
         StringBuilder sb = new StringBuilder();
         sb.append('(').append(CodegenUtils.ci(CallContext.class)).append(CodegenUtils.ci(Long.TYPE));

         for(int n = 0; n < i; ++n) {
            sb.append('I');
         }

         signatures[i] = sb.append(")I").toString();
      }

   }
}
