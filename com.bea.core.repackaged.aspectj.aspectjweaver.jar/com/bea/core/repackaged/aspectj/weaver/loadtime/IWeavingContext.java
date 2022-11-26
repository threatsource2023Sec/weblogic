package com.bea.core.repackaged.aspectj.weaver.loadtime;

import com.bea.core.repackaged.aspectj.weaver.tools.WeavingAdaptor;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

public interface IWeavingContext {
   Enumeration getResources(String var1) throws IOException;

   /** @deprecated */
   String getBundleIdFromURL(URL var1);

   String getClassLoaderName();

   ClassLoader getClassLoader();

   String getFile(URL var1);

   String getId();

   boolean isLocallyDefined(String var1);

   List getDefinitions(ClassLoader var1, WeavingAdaptor var2);
}
