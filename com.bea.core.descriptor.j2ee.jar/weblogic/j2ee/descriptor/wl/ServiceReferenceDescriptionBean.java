package weblogic.j2ee.descriptor.wl;

public interface ServiceReferenceDescriptionBean {
   String getServiceRefName();

   void setServiceRefName(String var1);

   String getWsdlUrl();

   void setWsdlUrl(String var1);

   PropertyNamevalueBean[] getCallProperties();

   PropertyNamevalueBean createCallProperty();

   void destroyCallProperty(PropertyNamevalueBean var1);

   PortInfoBean[] getPortInfos();

   PortInfoBean createPortInfo();

   PortInfoBean lookupPortInfo(String var1);

   void destroyPortInfo(PortInfoBean var1);

   String getId();

   void setId(String var1);
}
