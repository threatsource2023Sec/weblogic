package weblogic.j2ee.descriptor.wl;

public interface WebserviceDescriptionBean {
   String getWebserviceDescriptionName();

   void setWebserviceDescriptionName(String var1);

   String getWebserviceType();

   void setWebserviceType(String var1);

   String getWsdlPublishFile();

   void setWsdlPublishFile(String var1);

   PortComponentBean[] getPortComponents();

   PortComponentBean createPortComponent();

   void destroyPortComponent(PortComponentBean var1);
}
