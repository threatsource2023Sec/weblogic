package weblogic.j2ee.descriptor.wl;

public interface OwsmPolicyBean {
   void setUri(String var1);

   String getUri();

   void setStatus(String var1);

   String getStatus();

   void setCategory(String var1);

   String getCategory();

   PropertyNamevalueBean[] getSecurityConfigurationProperties();

   PropertyNamevalueBean createSecurityConfigurationProperty();

   void destroySecurityConfigurationProperty(PropertyNamevalueBean var1);
}
