package weblogic.j2ee.descriptor;

import javax.xml.namespace.QName;

public interface ServiceInterfaceMappingBean {
   String getServiceInterface();

   void setServiceInterface(String var1);

   QName getWsdlServiceName();

   void setWsdlServiceName(QName var1);

   PortMappingBean[] getPortMappings();

   PortMappingBean createPortMapping();

   void destroyPortMapping(PortMappingBean var1);

   String getId();

   void setId(String var1);
}
