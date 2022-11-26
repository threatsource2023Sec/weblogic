package weblogic.j2ee.descriptor;

public interface MethodParamPartsMappingBean {
   int getParamPosition();

   void setParamPosition(int var1);

   String getParamType();

   void setParamType(String var1);

   WsdlMessageMappingBean getWsdlMessageMapping();

   WsdlMessageMappingBean createWsdlMessageMapping();

   void destroyWsdlMessageMapping(WsdlMessageMappingBean var1);

   String getId();

   void setId(String var1);
}
