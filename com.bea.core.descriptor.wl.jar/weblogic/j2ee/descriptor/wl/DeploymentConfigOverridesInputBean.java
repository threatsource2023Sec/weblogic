package weblogic.j2ee.descriptor.wl;

public interface DeploymentConfigOverridesInputBean {
   AppDeploymentBean[] getAppDeployments();

   AppDeploymentBean createAppDeployment(String var1);

   LibraryBean[] getLibraries();

   LibraryBean createLibrary(String var1);
}
