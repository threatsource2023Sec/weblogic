package weblogic.deploy.service;

import java.io.IOException;
import java.io.InputStream;

public interface DeploymentContext extends ConfigurationContext {
   InputStream getDataAsStream(String var1, String var2, Deployment var3, String var4) throws IOException;

   boolean isRestartRequired();

   void setRestartRequired(boolean var1);
}
