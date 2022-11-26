package weblogic.j2ee.descriptor;

public interface ServiceEndpointMethodMappingBean {
   String getJavaMethodName();

   void setJavaMethodName(String var1);

   String getWsdlOperation();

   void setWsdlOperation(String var1);

   EmptyBean getWrappedElement();

   EmptyBean createWrappedElement();

   void destroyWrappedElement(EmptyBean var1);

   MethodParamPartsMappingBean[] getMethodParamPartsMappings();

   MethodParamPartsMappingBean createMethodParamPartsMapping();

   void destroyMethodParamPartsMapping(MethodParamPartsMappingBean var1);

   WsdlReturnValueMappingBean getWsdlReturnValueMapping();

   WsdlReturnValueMappingBean createWsdlReturnValueMapping();

   void destroyWsdlReturnValueMapping(WsdlReturnValueMappingBean var1);

   String getId();

   void setId(String var1);
}
