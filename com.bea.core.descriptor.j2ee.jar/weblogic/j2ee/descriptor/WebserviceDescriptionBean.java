package weblogic.j2ee.descriptor;

public interface WebserviceDescriptionBean {
   String getDescription();

   void setDescription(String var1);

   String getDisplayName();

   void setDisplayName(String var1);

   IconBean getIcon();

   IconBean createIcon();

   void destroyIcon(IconBean var1);

   String getWebserviceDescriptionName();

   void setWebserviceDescriptionName(String var1);

   String getWsdlFile();

   void setWsdlFile(String var1);

   String getJaxrpcMappingFile();

   void setJaxrpcMappingFile(String var1);

   PortComponentBean[] getPortComponents();

   PortComponentBean createPortComponent();

   void destroyPortComponent(PortComponentBean var1);

   String getId();

   void setId(String var1);
}
