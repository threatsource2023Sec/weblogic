package org.apache.openjpa.ant;

import java.io.IOException;
import java.io.PrintWriter;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.lib.ant.AbstractTask;
import org.apache.openjpa.lib.conf.ConfigurationImpl;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.MetaDataTool;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.EnumeratedAttribute;

public class MetaDataToolTask extends AbstractTask {
   private static final Localizer _loc = Localizer.forPackage(MetaDataToolTask.class);
   protected MetaDataTool.Flags flags = new MetaDataTool.Flags();
   protected String fileName = null;

   public void setAction(Action act) {
      this.flags.action = act.getValue();
   }

   public void setFile(String fileName) {
      this.fileName = fileName;
   }

   protected ConfigurationImpl newConfiguration() {
      return new OpenJPAConfigurationImpl();
   }

   protected void executeOn(String[] files) throws IOException {
      ClassLoader loader = this.getClassLoader();
      if ("stdout".equals(this.fileName)) {
         this.flags.writer = new PrintWriter(System.out);
      } else if ("stderr".equals(this.fileName)) {
         this.flags.writer = new PrintWriter(System.err);
      } else if (this.fileName != null) {
         this.flags.file = Files.getFile(this.fileName, loader);
      }

      if (!MetaDataTool.run((OpenJPAConfiguration)this.getConfiguration(), files, this.flags, (MetaDataRepository)null, loader)) {
         throw new BuildException(_loc.get("bad-conf", (Object)"MetaDataToolTask").getMessage());
      }
   }

   public static class Action extends EnumeratedAttribute {
      public String[] getValues() {
         return MetaDataTool.ACTIONS;
      }
   }
}
