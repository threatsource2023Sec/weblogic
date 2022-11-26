package weblogic.ejb.container.compliance;

import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.utils.ErrorCollectionException;

public interface ComplianceChecker {
   void checkDeploymentInfo(DeploymentInfo var1) throws ErrorCollectionException;
}
