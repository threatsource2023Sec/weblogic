package weblogic.j2ee.descriptor;

public interface ContainerTransactionBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   MethodBean[] getMethods();

   MethodBean createMethod();

   void destroyMethod(MethodBean var1);

   String getTransAttribute();

   void setTransAttribute(String var1);

   String getId();

   void setId(String var1);
}
