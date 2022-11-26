package weblogic.coherence.descriptor;

import java.io.InputStream;
import java.util.List;
import weblogic.coherence.descriptor.wl.WeblogicCoherenceBean;
import weblogic.descriptor.DescriptorManager;

public abstract class CoherenceClusterDescriptorLoader {
   private static CoherenceClusterDescriptorLoader loader;

   public static void setLoader(CoherenceClusterDescriptorLoader ldr) {
      loader = ldr;
   }

   public static CoherenceClusterDescriptorLoader getLoader() {
      if (loader == null) {
         try {
            loader = (CoherenceClusterDescriptorLoader)Class.forName("weblogic.cacheprovider.coherence.WLSCoherenceClusterDescriptorLoader").newInstance();
         } catch (Exception var1) {
         }
      }

      return loader;
   }

   public abstract WeblogicCoherenceBean createCoherenceDescriptor(InputStream var1, DescriptorManager var2, List var3, boolean var4) throws Exception;

   public abstract WeblogicCoherenceBean createCoherenceDescriptor(String var1) throws Exception;
}
