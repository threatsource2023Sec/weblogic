package weblogic.j2ee.descriptor;

import javax.xml.namespace.QName;

public interface ServiceEndpointInterfaceMappingBean {
   String getServiceEndpointInterface();

   void setServiceEndpointInterface(String var1);

   QName getWsdlPortType();

   void setWsdlPortType(QName var1);

   QName getWsdlBinding();

   void setWsdlBinding(QName var1);

   ServiceEndpointMethodMappingBean[] getServiceEndpointMethodMappings();

   ServiceEndpointMethodMappingBean createServiceEndpointMethodMapping();

   void destroyServiceEndpointMethodMapping(ServiceEndpointMethodMappingBean var1);

   String getId();

   void setId(String var1);
}
