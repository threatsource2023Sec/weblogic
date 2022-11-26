package weblogic.osgi.internal;

import java.io.IOException;
import java.net.URL;
import org.osgi.framework.Bundle;
import weblogic.utils.classloaders.SharedSource;
import weblogic.utils.classloaders.URLSource;

public class SharedURLBundleSource extends URLSource implements SharedSource {
   private final Bundle basis;

   public SharedURLBundleSource(URL url, Bundle bundle) throws IOException {
      super(url);
      this.basis = bundle;
   }

   public Class getSharedClass(String name) {
      try {
         return this.basis.loadClass(name);
      } catch (Throwable var3) {
         return null;
      }
   }
}
