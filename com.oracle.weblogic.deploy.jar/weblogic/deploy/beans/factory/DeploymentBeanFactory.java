package weblogic.deploy.beans.factory;

import java.io.File;
import java.io.FileNotFoundException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.deploy.DeploymentData;

public interface DeploymentBeanFactory {
   AppDeploymentMBean createAppDeploymentMBean(String var1, File var2, DeploymentData var3) throws InvalidTargetException, FileNotFoundException, ManagementException;

   BasicDeploymentMBean addTargetsInDeploymentData(DeploymentData var1, BasicDeploymentMBean var2) throws InvalidTargetException, ManagementException;

   BasicDeploymentMBean removeTargetsInDeploymentData(DeploymentData var1, BasicDeploymentMBean var2) throws InvalidTargetException;

   void removeMBean(AppDeploymentMBean var1) throws ManagementException;

   DomainMBean getEditableDomain();

   void setEditableDomain(DomainMBean var1, boolean var2);

   void resetEditableDomain();

   DomainMBean getAlternateEditableDomain();

   void setAlternateEditableDomain(DomainMBean var1);

   void resetAlternateEditableDomain();

   boolean isDeployerInitiatedBeanUpdate(DescriptorBean var1, BeanUpdateEvent.PropertyUpdate var2);

   void resetDeployerInitiatedBeanUpdates();
}
