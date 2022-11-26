package com.kenai.jffi;

import java.lang.reflect.Method;
import java.util.Arrays;

public final class InvokeDynamicSupport {
   private InvokeDynamicSupport() {
   }

   public static Invoker getFastNumericInvoker(CallContext callContext, long function) {
      Platform.CPU cpu = Platform.getPlatform().getCPU();
      if (!(callContext.getReturnType() instanceof Type.Builtin)) {
         return null;
      } else if ((callContext.flags & 1) != 0) {
         return null;
      } else if (callContext.getParameterCount() > 6) {
         return null;
      } else {
         boolean isFastInt = false;
         boolean isFastLong = false;
         switch (callContext.getReturnType().type()) {
            case 0:
               isFastLong = true;
               isFastInt = true;
            case 1:
            case 2:
            case 3:
            case 4:
            default:
               break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
               isFastInt = true;
               isFastLong = cpu.dataModel == 64;
               break;
            case 11:
            case 12:
               isFastLong = true;
               break;
            case 13:
               return null;
            case 14:
               isFastInt = cpu.dataModel == 32;
               isFastLong = cpu.dataModel == 64;
         }

         isFastInt &= cpu == Platform.CPU.I386 || cpu == Platform.CPU.X86_64;
         isFastLong &= cpu == Platform.CPU.I386 || cpu == Platform.CPU.X86_64;

         for(int i = 0; i < callContext.getParameterCount() && (isFastInt || isFastLong); ++i) {
            if (!(callContext.getParameterType(i) instanceof Type.Builtin)) {
               return null;
            }

            switch (callContext.getParameterType(i).type()) {
               case 5:
               case 6:
               case 7:
               case 8:
               case 9:
               case 10:
                  isFastLong &= cpu.dataModel == 64;
                  break;
               case 11:
               case 12:
                  isFastInt = false;
                  break;
               case 13:
                  return null;
               case 14:
                  isFastInt &= cpu.dataModel == 32;
                  isFastLong &= cpu.dataModel == 64;
                  break;
               default:
                  isFastLong = false;
                  isFastInt = false;
            }
         }

         Class nativeIntClass = isFastInt ? Integer.TYPE : Long.TYPE;
         String methodName = (isFastInt ? "invokeI" : (isFastLong ? "invokeL" : "invokeN")) + callContext.getParameterCount();
         if ((callContext.flags & 2) != 0 && (isFastInt || isFastLong)) {
            methodName = methodName + "NoErrno";
         }

         Class[] params = new Class[2 + callContext.getParameterCount()];
         params[0] = Long.TYPE;
         params[1] = Long.TYPE;
         Arrays.fill(params, 2, params.length, nativeIntClass);

         try {
            Method method = Foreign.class.getDeclaredMethod(methodName, params);
            JSR292 jsr292 = InvokeDynamicSupport.JSR292.INSTANCE;
            Object methodHandle = jsr292.insertArguments(jsr292.unreflect(method), 0, callContext.getAddress(), function);
            return new Invoker(method, methodHandle);
         } catch (Throwable var12) {
            return null;
         }
      }
   }

   static final class JSR292 {
      static final JSR292 INSTANCE = getInstance();
      private final Object lookup;
      private final Method unreflect;
      private final Class methodHandles;
      private final Method insertArguments;

      static boolean isAvailable() {
         return INSTANCE != null;
      }

      private static JSR292 getInstance() {
         try {
            Class lookupClass = Class.forName("java.lang.invoke.MethodHandles$Lookup");
            Class methodHandlesClass = Class.forName("java.lang.invoke.MethodHandles");
            Class methodHandleClass = Class.forName("java.lang.invoke.MethodHandle");
            Method lookupMethod = methodHandlesClass.getDeclaredMethod("lookup");
            Method unreflect = lookupClass.getDeclaredMethod("unreflect", Method.class);
            Method insertArguments = methodHandlesClass.getDeclaredMethod("insertArguments", methodHandleClass, Integer.TYPE, Object[].class);
            Object lookup = lookupMethod.invoke(methodHandlesClass);
            return new JSR292(lookup, unreflect, methodHandlesClass, insertArguments);
         } catch (Throwable var7) {
            return null;
         }
      }

      JSR292(Object lookup, Method unreflect, Class methodHandles, Method insertArguments) {
         this.lookup = lookup;
         this.unreflect = unreflect;
         this.methodHandles = methodHandles;
         this.insertArguments = insertArguments;
      }

      public Object unreflect(Method m) throws Exception {
         return this.unreflect.invoke(this.lookup, m);
      }

      public Object insertArguments(Object methodHandle, int index, Object... values) throws Exception {
         return this.insertArguments.invoke(this.methodHandles, methodHandle, index, values);
      }
   }

   public static final class Invoker {
      private final Method method;
      private final Object methodHandle;

      Invoker(Method method, Object methodHandle) {
         this.method = method;
         this.methodHandle = methodHandle;
      }

      public Object getMethodHandle() {
         return this.methodHandle;
      }

      public Method getMethod() {
         return this.method;
      }
   }
}
