package weblogic.j2ee.descriptor.wl;

public interface DeploymentConfigOverridesBean {
   String getCommandLineOptions();

   void setCommandLineOptions(String var1);

   AppDeploymentBean[] getAppDeployments();

   AppDeploymentBean createAppDeployment(String var1);

   AppDeploymentBean lookupAppDeployment(String var1);

   LibraryBean[] getLibraries();

   LibraryBean createLibrary(String var1);

   LibraryBean lookupLibrary(String var1);
}
