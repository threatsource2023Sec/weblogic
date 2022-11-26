package weblogic.servlet.internal.dd.compliance;

import weblogic.utils.ErrorCollectionException;

public interface ComplianceChecker {
   void check(DeploymentInfo var1) throws ErrorCollectionException;

   void update(String var1);

   void setVerbose(boolean var1);
}
