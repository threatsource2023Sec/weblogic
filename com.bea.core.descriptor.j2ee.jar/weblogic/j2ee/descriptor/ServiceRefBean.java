package weblogic.j2ee.descriptor;

import javax.xml.namespace.QName;

public interface ServiceRefBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String[] getDisplayNames();

   void addDisplayName(String var1);

   void removeDisplayName(String var1);

   void setDisplayNames(String[] var1);

   IconBean[] getIcons();

   IconBean createIcon();

   void destroyIcon(IconBean var1);

   String getServiceRefName();

   void setServiceRefName(String var1);

   String getServiceInterface();

   void setServiceInterface(String var1);

   String getServiceRefType();

   void setServiceRefType(String var1);

   String getWsdlFile();

   void setWsdlFile(String var1);

   String getJaxrpcMappingFile();

   void setJaxrpcMappingFile(String var1);

   QName getServiceQname();

   void setServiceQname(QName var1);

   PortComponentRefBean[] getPortComponentRefs();

   PortComponentRefBean createPortComponentRef();

   void destroyPortComponentRef(PortComponentRefBean var1);

   ServiceRefHandlerBean[] getHandlers();

   ServiceRefHandlerBean createHandler();

   void destroyHandler(ServiceRefHandlerBean var1);

   ServiceRefHandlerChainsBean getHandlerChains();

   ServiceRefHandlerChainsBean createHandlerChains();

   void destroyHandlerChains(ServiceRefHandlerChainsBean var1);

   String getMappedName();

   void setMappedName(String var1);

   InjectionTargetBean[] getInjectionTargets();

   InjectionTargetBean createInjectionTarget();

   void destroyInjectionTarget(InjectionTargetBean var1);

   String getId();

   void setId(String var1);
}
