package weblogic.j2ee.descriptor;

public interface ActivationConfigBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   ActivationConfigPropertyBean[] getActivationConfigProperties();

   ActivationConfigPropertyBean createActivationConfigProperty();

   void destroyActivationConfigProperty(ActivationConfigPropertyBean var1);

   String getId();

   void setId(String var1);
}
