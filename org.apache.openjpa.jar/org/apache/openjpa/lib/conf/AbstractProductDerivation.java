package org.apache.openjpa.lib.conf;

import java.io.File;
import java.util.List;

public abstract class AbstractProductDerivation implements ProductDerivation {
   public String getConfigurationPrefix() {
      return null;
   }

   public void validate() throws Exception {
   }

   public ConfigurationProvider loadGlobals(ClassLoader loader) throws Exception {
      return null;
   }

   public ConfigurationProvider loadDefaults(ClassLoader loader) throws Exception {
      return null;
   }

   public ConfigurationProvider load(String resource, String anchor, ClassLoader loader) throws Exception {
      return null;
   }

   public ConfigurationProvider load(File file, String anchor) throws Exception {
      return null;
   }

   public String getDefaultResourceLocation() {
      return null;
   }

   public List getAnchorsInFile(File file) throws Exception {
      return null;
   }

   public List getAnchorsInResource(String resource) throws Exception {
      return null;
   }

   public boolean beforeConfigurationConstruct(ConfigurationProvider cp) {
      return false;
   }

   public boolean beforeConfigurationLoad(Configuration conf) {
      return false;
   }

   public boolean afterSpecificationSet(Configuration conf) {
      return false;
   }

   public void beforeConfigurationClose(Configuration conf) {
   }
}
