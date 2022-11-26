package weblogic.apache.org.apache.velocity.runtime.resource.loader;

import weblogic.apache.org.apache.velocity.runtime.RuntimeServices;
import weblogic.apache.org.apache.velocity.util.StringUtils;

public class ResourceLoaderFactory {
   public static ResourceLoader getLoader(RuntimeServices rs, String loaderClassName) throws Exception {
      ResourceLoader loader = null;

      try {
         loader = (ResourceLoader)Class.forName(loaderClassName).newInstance();
         rs.info("Resource Loader Instantiated: " + loader.getClass().getName());
         return loader;
      } catch (Exception var4) {
         rs.error("Problem instantiating the template loader.\nLook at your properties file and make sure the\nname of the template loader is correct. Here is the\nerror: " + StringUtils.stackTrace(var4));
         throw new Exception("Problem initializing template loader: " + loaderClassName + "\nError is: " + StringUtils.stackTrace(var4));
      }
   }
}
