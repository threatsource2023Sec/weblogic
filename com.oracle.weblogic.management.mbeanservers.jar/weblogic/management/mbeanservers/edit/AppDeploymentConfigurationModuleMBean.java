package weblogic.management.mbeanservers.edit;

import javax.enterprise.deploy.shared.ModuleType;

public interface AppDeploymentConfigurationModuleMBean extends DescriptorMBean {
   String CONNECTOR_TYPE = ModuleType.RAR.toString();
   String EJB_TYPE = ModuleType.EJB.toString();
   String WEB_APP_TYPE = ModuleType.WAR.toString();
   String JMS_TYPE = "jms";
   String JDBC_TYPE = "jdbc";
   String GAR_TYPE = "gar";
   String INTERCEPTION_TYPE = "interception";
   String WEBSERVICE_TYPE = "webservice";
   String PERSISTENCE_TYPE = "persistence";
   String DIAGNOSTICS_TYPE = "diagnostics";

   String getName();

   String getType();

   DescriptorMBean[] getDescriptors();
}
