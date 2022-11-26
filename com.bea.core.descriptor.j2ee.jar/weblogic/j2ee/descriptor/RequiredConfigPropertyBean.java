package weblogic.j2ee.descriptor;

public interface RequiredConfigPropertyBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String getConfigPropertyName();

   void setConfigPropertyName(String var1);

   String getId();

   void setId(String var1);
}
