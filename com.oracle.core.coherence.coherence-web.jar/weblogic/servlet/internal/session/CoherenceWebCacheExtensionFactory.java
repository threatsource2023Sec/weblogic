package weblogic.servlet.internal.session;

import org.jvnet.hk2.annotations.Service;
import weblogic.application.Module;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.internal.CacheExtension;
import weblogic.servlet.internal.CacheExtensionFactory;

@Service
public class CoherenceWebCacheExtensionFactory implements CacheExtensionFactory {
   public CacheExtension create(Module module, ClassLoader classLoader, WeblogicWebAppBean wlWebAppBean) {
      return new CoherenceWebCacheExtension(module, classLoader, wlWebAppBean);
   }
}
