package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

public interface VirtualTargetMBean extends DeploymentMBean, TargetMBean {
   String getName();

   String[] getHostNames();

   void setHostNames(String[] var1) throws InvalidAttributeValueException;

   String getUriPrefix();

   void setUriPrefix(String var1) throws InvalidAttributeValueException;

   WebServerMBean getWebServer();

   int getPortOffset();

   void setPortOffset(int var1) throws InvalidAttributeValueException;

   int getExplicitPort();

   void setExplicitPort(int var1) throws InvalidAttributeValueException;

   String getPartitionChannel();

   void setPartitionChannel(String var1);

   boolean isMoreThanOneTargetAllowed();

   void setMoreThanOneTargetAllowed(boolean var1);

   TargetMBean[] getTargets();

   void setTargets(TargetMBean[] var1) throws InvalidAttributeValueException, DistributedManagementException;
}
