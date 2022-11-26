package org.apache.openjpa.lib.conf;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ProductDerivation {
   int TYPE_PRODUCT = 100;
   int TYPE_FEATURE = 1000;

   int getType();

   String getConfigurationPrefix();

   void validate() throws Exception;

   ConfigurationProvider loadGlobals(ClassLoader var1) throws Exception;

   ConfigurationProvider loadDefaults(ClassLoader var1) throws Exception;

   ConfigurationProvider load(String var1, String var2, ClassLoader var3) throws Exception;

   ConfigurationProvider load(File var1, String var2) throws Exception;

   String getDefaultResourceLocation();

   List getAnchorsInFile(File var1) throws IOException, Exception;

   List getAnchorsInResource(String var1) throws Exception;

   boolean beforeConfigurationConstruct(ConfigurationProvider var1);

   boolean beforeConfigurationLoad(Configuration var1);

   boolean afterSpecificationSet(Configuration var1);

   void beforeConfigurationClose(Configuration var1);
}
