package weblogic.deploy.service;

import java.io.Externalizable;
import java.util.List;

public interface Deployment extends Externalizable {
   String getIdentity();

   String getCallbackHandlerId();

   Version getProposedVersion();

   String[] getTargets();

   String[] getServersToBeRestarted();

   String[] getPartitionsToBeRestarted();

   String getDataTransferHandlerType();

   List getChangeDescriptors();

   void updateDeploymentTaskStatus(int var1);

   DeploymentType getDeploymentType();

   public static enum DeploymentType {
      NORMAL,
      CONFIGURATION;
   }
}
