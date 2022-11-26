package org.apache.openjpa.ant;

import java.io.IOException;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.enhance.ApplicationIdTool;
import org.apache.openjpa.lib.ant.AbstractTask;
import org.apache.openjpa.lib.conf.ConfigurationImpl;
import org.apache.openjpa.lib.util.CodeFormat;
import org.apache.openjpa.lib.util.Files;

public class ApplicationIdToolTask extends AbstractTask {
   protected ApplicationIdTool.Flags flags = new ApplicationIdTool.Flags();
   protected String dirName = null;

   public ApplicationIdToolTask() {
      this.flags.format = new CodeFormat();
   }

   public void setDirectory(String dirName) {
      this.dirName = dirName;
   }

   public void setIgnoreErrors(boolean ignoreErrors) {
      this.flags.ignoreErrors = ignoreErrors;
   }

   public void setName(String name) {
      this.flags.name = name;
   }

   public void setSuffix(String suffix) {
      this.flags.suffix = suffix;
   }

   public void setToken(String token) {
      this.flags.token = token;
   }

   public Object createCodeFormat() {
      return this.flags.format;
   }

   protected ConfigurationImpl newConfiguration() {
      return new OpenJPAConfigurationImpl();
   }

   protected void executeOn(String[] files) throws IOException, ClassNotFoundException {
      this.flags.directory = this.dirName == null ? null : Files.getFile(this.dirName, this.getClassLoader());
      ApplicationIdTool.run((OpenJPAConfiguration)this.getConfiguration(), files, this.flags, this.getClassLoader());
   }
}
