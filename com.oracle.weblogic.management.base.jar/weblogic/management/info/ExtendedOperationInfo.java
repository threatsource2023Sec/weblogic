package weblogic.management.info;

import javax.management.MBeanParameterInfo;

public interface ExtendedOperationInfo {
   boolean isDynamic();

   String getName();

   MBeanParameterInfo[] getSignature();

   int getImpact();

   String getReturnType();

   String getLegalCheck();

   String[] getLegalChecks();

   String[] getLegalResponses();

   String getLegalResponse();
}
