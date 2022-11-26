package weblogic.management.descriptors.webappext;

import weblogic.management.descriptors.WebElementMBean;
import weblogic.management.descriptors.webapp.SecurityRoleMBean;
import weblogic.xml.dom.DOMProcessingException;

public interface RunAsRoleAssignmentMBean extends WebElementMBean {
   String getRoleName();

   void setRoleName(String var1) throws DOMProcessingException;

   SecurityRoleMBean getSecurityRole();

   void setSecurityRole(SecurityRoleMBean var1);

   String getRunAsPrincipalName();

   void setRunAsPrincipalName(String var1);
}
