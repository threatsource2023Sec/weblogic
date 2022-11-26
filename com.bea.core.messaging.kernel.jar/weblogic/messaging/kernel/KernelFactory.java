package weblogic.messaging.kernel;

import java.util.Map;
import weblogic.messaging.kernel.internal.KernelImpl;

public class KernelFactory {
   private static final KernelFactory singleton = new KernelFactory();

   public static KernelFactory getFactory() {
      return singleton;
   }

   public Kernel createKernel(String name, Map props) throws KernelException {
      return new KernelImpl(name, props);
   }
}
