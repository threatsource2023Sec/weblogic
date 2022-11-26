package weblogic.application;

import java.util.Map;
import weblogic.utils.classloaders.ClassFinder;

/** @deprecated */
@Deprecated
public interface MergedDescriptorModule {
   Map getDescriptorMappings();

   void handleMergedFinder(ClassFinder var1);
}
