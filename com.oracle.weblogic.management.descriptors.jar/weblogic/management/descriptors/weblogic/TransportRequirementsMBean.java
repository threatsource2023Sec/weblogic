package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface TransportRequirementsMBean extends XMLElementMBean {
   void setIntegrity(String var1);

   String getIntegrity();

   void setConfidentiality(String var1);

   String getConfidentiality();

   void setClientCertAuthentication(String var1);

   String getClientCertAuthentication();
}
