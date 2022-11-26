package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBean;

public interface SecurityIdentityMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   boolean getUseCallerIdentity();

   void setUseCallerIdentity(boolean var1);

   RunAsMBean getRunAs();

   void setRunAs(RunAsMBean var1);
}
