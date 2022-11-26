package weblogic.nodemanager.plugin;

import java.io.File;
import java.io.IOException;
import weblogic.admin.plugin.ChangeList;
import weblogic.admin.plugin.NMComponentTypeChangeList;

public abstract class AbstractConfigurationPlugin implements ConfigurationPlugin {
   public abstract void init(Provider var1);

   public abstract void validate(File var1, ChangeList var2) throws ConfigurationPlugin.ValidationException;

   public abstract void commit(File var1, ChangeList var2, String[] var3) throws IOException;

   public abstract ChangeList diffConfig(File var1, ChangeList var2) throws IOException;

   public abstract ChangeList syncChangeList(File var1, ChangeList var2) throws IOException;

   public NMComponentTypeChangeList getChangeListForAllFiles(String compName) throws IOException {
      throw new UnsupportedOperationException(this.getClass().getName() + " does not support getChangeListForAllFiles()");
   }

   public File getFile(String compName, String relativePath) throws IOException {
      throw new UnsupportedOperationException(this.getClass().getName() + " does not support getFile()");
   }
}
