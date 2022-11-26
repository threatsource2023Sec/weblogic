package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface IIOPSecurityDescriptorMBean extends XMLElementMBean {
   TransportRequirementsMBean getTransportRequirements();

   void setTransportRequirements(TransportRequirementsMBean var1);

   String getClientAuthentication();

   void setClientAuthentication(String var1);

   String getIdentityAssertion();

   void setIdentityAssertion(String var1);
}
