package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;
import weblogic.xml.xmlnode.XMLNode;
import weblogic.xml.xmlnode.XMLNodeSet;

public interface WebServiceMBean extends XMLElementMBean {
   String getWebServiceName();

   void setWebServiceName(String var1);

   String getURI();

   void setURI(String var1);

   String getProtocol();

   void setProtocol(String var1);

   String getStyle();

   void setStyle(String var1);

   boolean getExposeWSDL();

   void setExposeWSDL(boolean var1);

   boolean getExposeHomePage();

   void setExposeHomePage(boolean var1);

   XMLNode getSecurity();

   void setSecurity(XMLNode var1);

   XMLNodeSet getTypes();

   void setTypes(XMLNodeSet var1);

   String getTargetNamespace();

   void setTargetNamespace(String var1);

   String getPortTypeName();

   void setPortTypeName(String var1);

   String getPortName();

   void setPortName(String var1);

   boolean getUseSOAP12();

   void setUseSOAP12(boolean var1);

   String getJmsURI();

   void setJmsURI(String var1);

   String getCharset();

   void setCharset(String var1);

   int getResponseBufferSize();

   void setResponseBufferSize(int var1);

   boolean getIgnoreAuthHeader();

   void setIgnoreAuthHeader(boolean var1);

   boolean getHandleAllActors();

   void setHandleAllActors(boolean var1);

   TypeMappingMBean getTypeMapping();

   void setTypeMapping(TypeMappingMBean var1);

   ComponentsMBean getComponents();

   void setComponents(ComponentsMBean var1);

   OperationsMBean getOperations();

   void setOperations(OperationsMBean var1);
}
