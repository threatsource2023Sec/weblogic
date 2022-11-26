package org.python.modules.jffi;

import com.kenai.jffi.CallingConvention;
import com.kenai.jffi.Platform;

final class FastNumericMethodGenerator extends AbstractNumericMethodGenerator {
   private static final int MAX_PARAMETERS = getMaximumFastNumericParameters();
   private static final String[] signatures;
   private static final String[] methodNames;

   String getInvokerMethodName(JITSignature signature) {
      int parameterCount = signature.getParameterCount();
      if (parameterCount <= MAX_PARAMETERS && parameterCount <= methodNames.length) {
         return methodNames[parameterCount];
      } else {
         throw new IllegalArgumentException("invalid fast-long parameter count: " + parameterCount);
      }
   }

   String getInvokerSignature(int parameterCount) {
      if (parameterCount <= MAX_PARAMETERS && parameterCount <= signatures.length) {
         return signatures[parameterCount];
      } else {
         throw new IllegalArgumentException("invalid fast-long parameter count: " + parameterCount);
      }
   }

   final Class getInvokerIntType() {
      return Long.TYPE;
   }

   public boolean isSupported(JITSignature signature) {
      int parameterCount = signature.getParameterCount();
      if (signature.getCallingConvention().equals(CallingConvention.DEFAULT) && parameterCount <= MAX_PARAMETERS) {
         Platform platform = Platform.getPlatform();
         if (platform.getOS().equals(Platform.OS.WINDOWS)) {
            return false;
         } else if (!platform.getCPU().equals(Platform.CPU.I386) && !platform.getCPU().equals(Platform.CPU.X86_64)) {
            return false;
         } else {
            for(int i = 0; i < parameterCount; ++i) {
               if (!isFastNumericParameter(platform, signature.getParameterType(i))) {
                  return false;
               }
            }

            return isFastNumericResult(platform, signature.getResultType());
         }
      } else {
         return false;
      }
   }

   static final int getMaximumFastNumericParameters() {
      try {
         com.kenai.jffi.Invoker.class.getDeclaredMethod("invokeNNNNNNrN", com.kenai.jffi.Function.class, Long.TYPE, Long.TYPE, Long.TYPE, Long.TYPE, Long.TYPE, Long.TYPE);
         return 6;
      } catch (Throwable var1) {
         return -1;
      }
   }

   private static boolean isFastNumericType(Platform platform, NativeType type) {
      switch (type) {
         case BOOL:
         case BYTE:
         case UBYTE:
         case SHORT:
         case USHORT:
         case INT:
         case UINT:
         case LONG:
         case ULONG:
         case LONGLONG:
         case ULONGLONG:
         case FLOAT:
         case DOUBLE:
            return true;
         default:
            return false;
      }
   }

   static boolean isFastNumericResult(Platform platform, NativeType type) {
      switch (type) {
         case VOID:
         case POINTER:
         case STRING:
            return true;
         default:
            return isFastNumericType(platform, type);
      }
   }

   static boolean isFastNumericParameter(Platform platform, NativeType type) {
      switch (type) {
         case POINTER:
         case BUFFER_IN:
         case BUFFER_OUT:
         case BUFFER_INOUT:
            return true;
         case STRING:
         default:
            return isFastNumericType(platform, type);
      }
   }

   static {
      signatures = buildSignatures(Long.TYPE, MAX_PARAMETERS);
      methodNames = new String[]{"invokeVrN", "invokeNrN", "invokeNNrN", "invokeNNNrN", "invokeNNNNrN", "invokeNNNNNrN", "invokeNNNNNNrN"};
   }
}
