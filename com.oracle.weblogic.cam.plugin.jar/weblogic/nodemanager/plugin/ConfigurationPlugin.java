package weblogic.nodemanager.plugin;

import java.io.File;
import java.io.IOException;
import weblogic.admin.plugin.ChangeList;
import weblogic.admin.plugin.NMComponentTypeChangeList;
import weblogic.admin.plugin.Plugin;

public interface ConfigurationPlugin extends Plugin {
   void init(Provider var1);

   void validate(File var1, ChangeList var2) throws ValidationException;

   void commit(File var1, ChangeList var2, String[] var3) throws IOException;

   ChangeList diffConfig(File var1, ChangeList var2) throws IOException;

   ChangeList syncChangeList(File var1, ChangeList var2) throws IOException;

   NMComponentTypeChangeList getChangeListForAllFiles(String var1) throws IOException;

   File getFile(String var1, String var2) throws IOException;

   public static class ValidationException extends Exception {
      public ValidationException(String message) {
         super(message);
      }
   }
}
