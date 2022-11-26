package jnr.ffi.provider.jffi;

import com.kenai.jffi.CallContext;
import com.kenai.jffi.Invoker;
import com.kenai.jffi.Platform;
import jnr.ffi.CallingConvention;
import jnr.ffi.NativeType;
import jnr.ffi.provider.ParameterType;
import jnr.ffi.provider.ResultType;
import jnr.ffi.provider.SigType;

public class FastLongMethodGenerator extends AbstractFastNumericMethodGenerator {
   private static final boolean ENABLED = Util.getBooleanProperty("jnr.ffi.fast-long.enabled", true);
   private static final int MAX_PARAMETERS = getMaximumFastLongParameters();
   private static final String[] signatures;
   private static final String[] methodNames = new String[]{"invokeL0", "invokeL1", "invokeL2", "invokeL3", "invokeL4", "invokeL5", "invokeL6"};

   String getInvokerMethodName(ResultType resultType, ParameterType[] parameterTypes, boolean ignoreErrno) {
      int parameterCount = parameterTypes.length;
      if (parameterCount <= MAX_PARAMETERS && parameterCount <= methodNames.length) {
         return methodNames[parameterCount];
      } else {
         throw new IllegalArgumentException("invalid fast-int parameter count: " + parameterCount);
      }
   }

   String getInvokerSignature(int parameterCount, Class nativeIntType) {
      if (parameterCount <= MAX_PARAMETERS && parameterCount <= signatures.length) {
         return signatures[parameterCount];
      } else {
         throw new IllegalArgumentException("invalid fast-int parameter count: " + parameterCount);
      }
   }

   Class getInvokerType() {
      return Long.TYPE;
   }

   public boolean isSupported(ResultType resultType, ParameterType[] parameterTypes, CallingConvention callingConvention) {
      int parameterCount = parameterTypes.length;
      if (!ENABLED) {
         return false;
      } else if (callingConvention == CallingConvention.DEFAULT && parameterCount <= MAX_PARAMETERS) {
         Platform platform = Platform.getPlatform();
         if (platform.getCPU() != Platform.CPU.X86_64) {
            return false;
         } else if (platform.getOS().equals(Platform.OS.WINDOWS)) {
            return false;
         } else {
            ParameterType[] var6 = parameterTypes;
            int var7 = parameterTypes.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               ParameterType parameterType = var6[var8];
               if (!isFastLongParameter(platform, parameterType)) {
                  return false;
               }
            }

            return isFastLongResult(platform, resultType);
         }
      } else {
         return false;
      }
   }

   static int getMaximumFastLongParameters() {
      try {
         Invoker.class.getDeclaredMethod("invokeL6", CallContext.class, Long.TYPE, Long.TYPE, Long.TYPE, Long.TYPE, Long.TYPE, Long.TYPE, Long.TYPE);
         return 6;
      } catch (Throwable var1) {
         return 0;
      }
   }

   private static boolean isFastLongType(Platform platform, SigType type) {
      return FastIntMethodGenerator.isFastIntType(platform, type) || type.getNativeType() == NativeType.ADDRESS && NumberUtil.sizeof(NativeType.ADDRESS) == 8 || type.getNativeType() == NativeType.SLONG || type.getNativeType() == NativeType.ULONG || type.getNativeType() == NativeType.SLONGLONG || type.getNativeType() == NativeType.ULONGLONG;
   }

   static boolean isFastLongResult(Platform platform, ResultType resultType) {
      return isFastLongType(platform, resultType) || resultType.getNativeType() == NativeType.VOID || resultType.getNativeType() == NativeType.ADDRESS && NumberUtil.sizeof(NativeType.ADDRESS) == 8;
   }

   static boolean isFastLongParameter(Platform platform, ParameterType type) {
      return isFastLongType(platform, type);
   }

   static {
      signatures = new String[MAX_PARAMETERS + 1];

      for(int i = 0; i <= MAX_PARAMETERS; ++i) {
         StringBuilder sb = new StringBuilder();
         sb.append('(').append(CodegenUtils.ci(CallContext.class)).append(CodegenUtils.ci(Long.TYPE));

         for(int n = 0; n < i; ++n) {
            sb.append('J');
         }

         signatures[i] = sb.append(")J").toString();
      }

   }
}
