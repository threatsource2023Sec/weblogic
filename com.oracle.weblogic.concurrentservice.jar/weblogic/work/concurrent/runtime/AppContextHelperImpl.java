package weblogic.work.concurrent.runtime;

import weblogic.application.internal.ClassLoaders;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.work.concurrent.context.AppContextHelper;

public enum AppContextHelperImpl implements AppContextHelper {
   instance;

   public GenericClassLoader getOrCreatePartitionClassLoader(String partitionName) {
      return ClassLoaders.instance.getOrCreatePartitionClassLoader(partitionName);
   }
}
