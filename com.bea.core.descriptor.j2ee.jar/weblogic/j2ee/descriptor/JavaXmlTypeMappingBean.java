package weblogic.j2ee.descriptor;

import javax.xml.namespace.QName;

public interface JavaXmlTypeMappingBean {
   String getJavaType();

   void setJavaType(String var1);

   QName getRootTypeQname();

   void setRootTypeQname(QName var1);

   String getAnonymousTypeQname();

   void setAnonymousTypeQname(String var1);

   String getQnameScope();

   void setQnameScope(String var1);

   VariableMappingBean[] getVariableMappings();

   VariableMappingBean createVariableMapping();

   void destroyVariableMapping(VariableMappingBean var1);

   String getId();

   void setId(String var1);
}
