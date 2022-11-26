package weblogic.j2ee.descriptor;

public interface ConfigPropertyBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String getConfigPropertyName();

   void setConfigPropertyName(String var1);

   String getConfigPropertyType();

   void setConfigPropertyType(String var1);

   String getConfigPropertyValue();

   void setConfigPropertyValue(String var1);

   boolean isConfigPropertyIgnore();

   void setConfigPropertyIgnore(boolean var1);

   boolean isConfigPropertySupportsDynamicUpdates();

   void setConfigPropertySupportsDynamicUpdates(boolean var1);

   boolean isConfigPropertyConfidential();

   void setConfigPropertyConfidential(boolean var1);

   String getId();

   void setId(String var1);
}
