package weblogic.server.embed;

import java.io.File;
import java.util.List;

public interface Deployer {
   void deployApp(String var1, File var2) throws EmbeddedServerException;

   void deployLib(String var1, File var2) throws EmbeddedServerException;

   void undeployApp(String var1) throws EmbeddedServerException;

   void undeployLib(String var1) throws EmbeddedServerException;

   void redeployApp(String var1) throws EmbeddedServerException;

   void redeployLib(String var1) throws EmbeddedServerException;

   List getDeployedApps();

   List getDeployedLibs();
}
