package org.apache.openjpa.ant;

import java.io.IOException;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.enhance.PCEnhancer;
import org.apache.openjpa.lib.ant.AbstractTask;
import org.apache.openjpa.lib.conf.ConfigurationImpl;
import org.apache.openjpa.lib.util.BytecodeWriter;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.meta.MetaDataRepository;

public class PCEnhancerTask extends AbstractTask {
   protected PCEnhancer.Flags flags = new PCEnhancer.Flags();
   protected String dirName = null;

   public void setDirectory(String dirName) {
      this.dirName = dirName;
   }

   public void setAddDefaultConstructor(boolean addDefCons) {
      this.flags.addDefaultConstructor = addDefCons;
   }

   public void setEnforcePropertyRestrictions(boolean fail) {
      this.flags.enforcePropertyRestrictions = fail;
   }

   public void setTmpClassLoader(boolean tmpClassLoader) {
      this.flags.tmpClassLoader = tmpClassLoader;
   }

   protected ConfigurationImpl newConfiguration() {
      return new OpenJPAConfigurationImpl();
   }

   protected void executeOn(String[] files) throws IOException {
      this.flags.directory = this.dirName == null ? null : Files.getFile(this.dirName, this.getClassLoader());
      OpenJPAConfiguration conf = (OpenJPAConfiguration)this.getConfiguration();
      MetaDataRepository repos = conf.newMetaDataRepositoryInstance();
      PCEnhancer.run(conf, files, this.flags, repos, (BytecodeWriter)null, this.getClassLoader());
   }
}
