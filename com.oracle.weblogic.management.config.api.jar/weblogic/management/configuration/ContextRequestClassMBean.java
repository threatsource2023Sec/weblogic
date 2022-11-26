package weblogic.management.configuration;

public interface ContextRequestClassMBean extends DeploymentMBean {
   ContextCaseMBean[] getContextCases();

   ContextCaseMBean createContextCase(String var1);

   void destroyContextCase(ContextCaseMBean var1);
}
