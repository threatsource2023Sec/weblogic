package weblogic.management.runtime;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;
import weblogic.management.ManagementException;

public interface WseePolicySubjectManagerRuntimeMBean extends RuntimeMBean {
   TabularData getPolicyReferenceInfos(String var1) throws ManagementException;

   void setPolicyReferenceInfos(String var1, TabularData var2) throws ManagementException;

   void setPolicyReferenceInfo(String var1, CompositeData var2) throws ManagementException;

   void attachPolicyReference(String var1, CompositeData var2) throws ManagementException;

   void removePolicyReference(String var1, String var2) throws ManagementException;

   String getPolicyRefStatus(String var1, String var2) throws ManagementException;

   void setPolicyRefStatus(String var1, String var2, Boolean var3) throws ManagementException;

   boolean isOWSMAttachable(String var1) throws ManagementException;
}
