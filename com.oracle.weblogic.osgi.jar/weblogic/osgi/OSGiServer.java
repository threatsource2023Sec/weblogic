package weblogic.osgi;

import java.io.InputStream;
import javax.naming.Context;
import weblogic.utils.classloaders.ClassFinder;

public interface OSGiServer {
   String getName();

   ClassFinder getBundleClassFinder(String var1, String var2, Context var3);

   OSGiBundle installBundle(InputStream var1, int var2) throws OSGiException;

   void startAllBundles() throws OSGiException;

   void refreshAllBundles() throws OSGiException;

   String getPartitionName();
}
