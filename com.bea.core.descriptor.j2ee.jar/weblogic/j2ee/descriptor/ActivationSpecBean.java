package weblogic.j2ee.descriptor;

public interface ActivationSpecBean {
   String getActivationSpecClass();

   void setActivationSpecClass(String var1);

   RequiredConfigPropertyBean[] getRequiredConfigProperties();

   RequiredConfigPropertyBean createRequiredConfigProperty();

   void destroyRequiredConfigProperty(RequiredConfigPropertyBean var1);

   ConfigPropertyBean[] getConfigProperties();

   ConfigPropertyBean createConfigProperty();

   void destroyConfigProperty(ConfigPropertyBean var1);

   String getId();

   void setId(String var1);
}
