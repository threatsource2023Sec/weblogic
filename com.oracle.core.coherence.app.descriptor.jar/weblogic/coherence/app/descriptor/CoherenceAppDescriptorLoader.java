package weblogic.coherence.app.descriptor;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import weblogic.coherence.app.descriptor.wl.CoherenceApplicationBean;
import weblogic.coherence.app.descriptor.wl.WeblogicCohAppBean;
import weblogic.descriptor.DescriptorManager;

public abstract class CoherenceAppDescriptorLoader {
   private static CoherenceAppDescriptorLoader loader;

   public static void setLoader(CoherenceAppDescriptorLoader ldr) {
      loader = ldr;
   }

   public static CoherenceAppDescriptorLoader getLoader() {
      if (loader == null) {
         try {
            loader = (CoherenceAppDescriptorLoader)Class.forName("weblogic.coherence.container.tools.WLSCoherenceAppDescriptorLoader").newInstance();
         } catch (Exception var1) {
         }
      }

      return loader;
   }

   public abstract CoherenceApplicationBean createCoherenceAppDescriptor(InputStream var1, DescriptorManager var2, List var3, boolean var4) throws Exception;

   public abstract CoherenceApplicationBean createCoherenceAppDescriptor(URL var1) throws Exception;

   public abstract WeblogicCohAppBean createWeblogicCohAppBean(URL var1) throws Exception;
}
