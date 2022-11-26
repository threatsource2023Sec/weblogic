package org.python.modules.jffi;

import com.kenai.jffi.CallingConvention;
import com.kenai.jffi.Platform;

final class FastIntMethodGenerator extends AbstractNumericMethodGenerator {
   private static final int MAX_PARAMETERS = getMaximumFastIntParameters();
   private static final String[] signatures;
   private static final String[] methodNames;
   private static final String[] noErrnoMethodNames;

   String getInvokerMethodName(JITSignature signature) {
      int parameterCount = signature.getParameterCount();
      if (signature.isIgnoreError() && parameterCount <= MAX_PARAMETERS && parameterCount <= noErrnoMethodNames.length) {
         return noErrnoMethodNames[signature.getParameterCount()];
      } else if (parameterCount <= MAX_PARAMETERS && parameterCount <= methodNames.length) {
         return methodNames[parameterCount];
      } else {
         throw new IllegalArgumentException("invalid fast-int parameter count: " + parameterCount);
      }
   }

   String getInvokerSignature(int parameterCount) {
      if (parameterCount <= MAX_PARAMETERS && parameterCount <= signatures.length) {
         return signatures[parameterCount];
      } else {
         throw new IllegalArgumentException("invalid fast-int parameter count: " + parameterCount);
      }
   }

   final Class getInvokerIntType() {
      return Integer.TYPE;
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
               if (!isFastIntParameter(platform, signature.getParameterType(i))) {
                  return false;
               }
            }

            return isFastIntResult(platform, signature.getResultType());
         }
      } else {
         return false;
      }
   }

   static final int getMaximumFastIntParameters() {
      try {
         com.kenai.jffi.Invoker.class.getDeclaredMethod("invokeIIIIIIrI", com.kenai.jffi.Function.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
         return 6;
      } catch (NoSuchMethodException var3) {
         try {
            com.kenai.jffi.Invoker.class.getDeclaredMethod("invokeIIIrI", com.kenai.jffi.Function.class, Integer.TYPE, Integer.TYPE, Integer.TYPE);
            return 3;
         } catch (NoSuchMethodException var2) {
            return -1;
         }
      } catch (Throwable var4) {
         return -1;
      }
   }

   private static boolean isFastIntType(Platform platform, NativeType type) {
      switch (type) {
         case BOOL:
         case BYTE:
         case UBYTE:
         case SHORT:
         case USHORT:
         case INT:
         case UINT:
            return true;
         case LONG:
         case ULONG:
            return platform.longSize() == 32;
         default:
            return false;
      }
   }

   static boolean isFastIntResult(Platform platform, NativeType type) {
      switch (type) {
         case VOID:
            return true;
         case POINTER:
         case STRING:
            return platform.addressSize() == 32;
         default:
            return isFastIntType(platform, type);
      }
   }

   static boolean isFastIntParameter(Platform platform, NativeType type) {
      switch (type) {
         case POINTER:
         case BUFFER_IN:
         case BUFFER_OUT:
         case BUFFER_INOUT:
            return platform.addressSize() == 32;
         case STRING:
         default:
            return isFastIntType(platform, type);
      }
   }

   static {
      signatures = buildSignatures(Integer.TYPE, MAX_PARAMETERS);
      methodNames = new String[]{"invokeVrI", "invokeIrI", "invokeIIrI", "invokeIIIrI", "invokeIIIIrI", "invokeIIIIIrI", "invokeIIIIIIrI"};
      noErrnoMethodNames = new String[]{"invokeNoErrnoVrI", "invokeNoErrnoIrI", "invokeNoErrnoIIrI", "invokeNoErrnoIIIrI"};
   }
}
