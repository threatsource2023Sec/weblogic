package weblogic.work.concurrent.context;

import weblogic.utils.classloaders.GenericClassLoader;

public interface AppContextHelper {
   GenericClassLoader getOrCreatePartitionClassLoader(String var1);
}
