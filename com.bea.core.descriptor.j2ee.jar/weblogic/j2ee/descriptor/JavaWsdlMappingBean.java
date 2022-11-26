package weblogic.j2ee.descriptor;

public interface JavaWsdlMappingBean {
   PackageMappingBean[] getPackageMappings();

   PackageMappingBean createPackageMapping();

   void destroyPackageMapping(PackageMappingBean var1);

   JavaXmlTypeMappingBean[] getJavaXmlTypeMappings();

   JavaXmlTypeMappingBean createJavaXmlTypeMapping();

   void destroyJavaXmlTypeMapping(JavaXmlTypeMappingBean var1);

   ExceptionMappingBean[] getExceptionMappings();

   ExceptionMappingBean createExceptionMapping();

   void destroyExceptionMapping(ExceptionMappingBean var1);

   ServiceInterfaceMappingBean[] getServiceInterfaceMappings();

   ServiceInterfaceMappingBean createServiceInterfaceMapping();

   void destroyServiceInterfaceMapping(ServiceInterfaceMappingBean var1);

   ServiceEndpointInterfaceMappingBean[] getServiceEndpointInterfaceMappings();

   ServiceEndpointInterfaceMappingBean createServiceEndpointInterfaceMapping();

   void destroyServiceEndpointInterfaceMapping(ServiceEndpointInterfaceMappingBean var1);

   String getVersion();

   void setVersion(String var1);

   String getId();

   void setId(String var1);
}
