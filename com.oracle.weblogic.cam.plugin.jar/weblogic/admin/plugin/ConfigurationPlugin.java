package weblogic.admin.plugin;

import java.io.IOException;

public interface ConfigurationPlugin extends Plugin {
   void init();

   void validate(ChangeList var1) throws ValidationException;

   long getVersion(String var1) throws IOException;
}
