package weblogic.j2ee.descriptor;

public interface PortComponentRefBean {
   String getServiceEndpointInterface();

   void setServiceEndpointInterface(String var1);

   boolean isEnableMtom();

   void setEnableMtom(boolean var1);

   int getMtomThreshold();

   void setMtomThreshold(int var1);

   AddressingBean getAddressing();

   AddressingBean createAddressing();

   void destroyAddressing(AddressingBean var1);

   RespectBindingBean getRespectBinding();

   RespectBindingBean createRespectBinding();

   void destroyRespectBinding(RespectBindingBean var1);

   String getPortComponentLink();

   void setPortComponentLink(String var1);

   String getId();

   void setId(String var1);
}
