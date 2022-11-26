package weblogic.j2ee.descriptor;

public interface VariableMappingBean {
   String getJavaVariableName();

   void setJavaVariableName(String var1);

   EmptyBean getDataMember();

   EmptyBean createDataMember();

   void destroyDataMember(EmptyBean var1);

   String getXmlAttributeName();

   void setXmlAttributeName(String var1);

   String getXmlElementName();

   void setXmlElementName(String var1);

   EmptyBean getXmlWildcard();

   EmptyBean createXmlWildcard();

   void destroyXmlWildcard(EmptyBean var1);

   String getId();

   void setId(String var1);
}
