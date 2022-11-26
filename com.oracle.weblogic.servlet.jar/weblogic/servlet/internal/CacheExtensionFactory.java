package weblogic.servlet.internal;

import org.jvnet.hk2.annotations.Contract;
import weblogic.application.Module;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;

@Contract
public interface CacheExtensionFactory {
   CacheExtension create(Module var1, ClassLoader var2, WeblogicWebAppBean var3);
}
