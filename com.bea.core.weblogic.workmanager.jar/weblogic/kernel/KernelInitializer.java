package weblogic.kernel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class KernelInitializer {
   private static final String KERNEL_CLAZZ_NAME = "weblogic.kernel.Kernel";
   private static final String INIT_METHOD = "ensureInitialized";

   public static void initializeWebLogicKernel() {
      Class kernelClass;
      try {
         kernelClass = Class.forName("weblogic.kernel.Kernel");
      } catch (ClassNotFoundException var6) {
         return;
      } catch (NoClassDefFoundError var7) {
         return;
      }

      Method initializer;
      try {
         initializer = kernelClass.getMethod("ensureInitialized");
      } catch (NoSuchMethodException var5) {
         throw new AssertionError(var5);
      }

      try {
         initializer.invoke((Object)null);
      } catch (IllegalAccessException var3) {
         throw new AssertionError(var3);
      } catch (InvocationTargetException var4) {
         throw new AssertionError(var4);
      }
   }
}
