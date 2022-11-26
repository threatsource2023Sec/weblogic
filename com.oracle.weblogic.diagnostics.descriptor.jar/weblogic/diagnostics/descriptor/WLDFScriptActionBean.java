package weblogic.diagnostics.descriptor;

import java.util.Properties;

public interface WLDFScriptActionBean extends WLDFNotificationBean {
   String getWorkingDirectory();

   void setWorkingDirectory(String var1);

   String getPathToScript();

   void setPathToScript(String var1);

   Properties getEnvironment();

   void setEnvironment(Properties var1);

   String[] getParameters();

   void setParameters(String[] var1);
}
