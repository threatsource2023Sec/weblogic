package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ServiceRefType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ServiceRefType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("servicereftype9f62type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   DisplayNameType[] getDisplayNameArray();

   DisplayNameType getDisplayNameArray(int var1);

   int sizeOfDisplayNameArray();

   void setDisplayNameArray(DisplayNameType[] var1);

   void setDisplayNameArray(int var1, DisplayNameType var2);

   DisplayNameType insertNewDisplayName(int var1);

   DisplayNameType addNewDisplayName();

   void removeDisplayName(int var1);

   IconType[] getIconArray();

   IconType getIconArray(int var1);

   int sizeOfIconArray();

   void setIconArray(IconType[] var1);

   void setIconArray(int var1, IconType var2);

   IconType insertNewIcon(int var1);

   IconType addNewIcon();

   void removeIcon(int var1);

   JndiNameType getServiceRefName();

   void setServiceRefName(JndiNameType var1);

   JndiNameType addNewServiceRefName();

   FullyQualifiedClassType getServiceInterface();

   void setServiceInterface(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewServiceInterface();

   FullyQualifiedClassType getServiceRefType();

   boolean isSetServiceRefType();

   void setServiceRefType(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewServiceRefType();

   void unsetServiceRefType();

   XsdAnyURIType getWsdlFile();

   boolean isSetWsdlFile();

   void setWsdlFile(XsdAnyURIType var1);

   XsdAnyURIType addNewWsdlFile();

   void unsetWsdlFile();

   PathType getJaxrpcMappingFile();

   boolean isSetJaxrpcMappingFile();

   void setJaxrpcMappingFile(PathType var1);

   PathType addNewJaxrpcMappingFile();

   void unsetJaxrpcMappingFile();

   XsdQNameType getServiceQname();

   boolean isSetServiceQname();

   void setServiceQname(XsdQNameType var1);

   XsdQNameType addNewServiceQname();

   void unsetServiceQname();

   PortComponentRefType[] getPortComponentRefArray();

   PortComponentRefType getPortComponentRefArray(int var1);

   int sizeOfPortComponentRefArray();

   void setPortComponentRefArray(PortComponentRefType[] var1);

   void setPortComponentRefArray(int var1, PortComponentRefType var2);

   PortComponentRefType insertNewPortComponentRef(int var1);

   PortComponentRefType addNewPortComponentRef();

   void removePortComponentRef(int var1);

   HandlerType[] getHandlerArray();

   HandlerType getHandlerArray(int var1);

   int sizeOfHandlerArray();

   void setHandlerArray(HandlerType[] var1);

   void setHandlerArray(int var1, HandlerType var2);

   HandlerType insertNewHandler(int var1);

   HandlerType addNewHandler();

   void removeHandler(int var1);

   HandlerChainsType getHandlerChains();

   boolean isSetHandlerChains();

   void setHandlerChains(HandlerChainsType var1);

   HandlerChainsType addNewHandlerChains();

   void unsetHandlerChains();

   XsdStringType getMappedName();

   boolean isSetMappedName();

   void setMappedName(XsdStringType var1);

   XsdStringType addNewMappedName();

   void unsetMappedName();

   InjectionTargetType[] getInjectionTargetArray();

   InjectionTargetType getInjectionTargetArray(int var1);

   int sizeOfInjectionTargetArray();

   void setInjectionTargetArray(InjectionTargetType[] var1);

   void setInjectionTargetArray(int var1, InjectionTargetType var2);

   InjectionTargetType insertNewInjectionTarget(int var1);

   InjectionTargetType addNewInjectionTarget();

   void removeInjectionTarget(int var1);

   XsdStringType getLookupName();

   boolean isSetLookupName();

   void setLookupName(XsdStringType var1);

   XsdStringType addNewLookupName();

   void unsetLookupName();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ServiceRefType newInstance() {
         return (ServiceRefType)XmlBeans.getContextTypeLoader().newInstance(ServiceRefType.type, (XmlOptions)null);
      }

      public static ServiceRefType newInstance(XmlOptions options) {
         return (ServiceRefType)XmlBeans.getContextTypeLoader().newInstance(ServiceRefType.type, options);
      }

      public static ServiceRefType parse(java.lang.String xmlAsString) throws XmlException {
         return (ServiceRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServiceRefType.type, (XmlOptions)null);
      }

      public static ServiceRefType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ServiceRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServiceRefType.type, options);
      }

      public static ServiceRefType parse(File file) throws XmlException, IOException {
         return (ServiceRefType)XmlBeans.getContextTypeLoader().parse(file, ServiceRefType.type, (XmlOptions)null);
      }

      public static ServiceRefType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ServiceRefType)XmlBeans.getContextTypeLoader().parse(file, ServiceRefType.type, options);
      }

      public static ServiceRefType parse(URL u) throws XmlException, IOException {
         return (ServiceRefType)XmlBeans.getContextTypeLoader().parse(u, ServiceRefType.type, (XmlOptions)null);
      }

      public static ServiceRefType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ServiceRefType)XmlBeans.getContextTypeLoader().parse(u, ServiceRefType.type, options);
      }

      public static ServiceRefType parse(InputStream is) throws XmlException, IOException {
         return (ServiceRefType)XmlBeans.getContextTypeLoader().parse(is, ServiceRefType.type, (XmlOptions)null);
      }

      public static ServiceRefType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ServiceRefType)XmlBeans.getContextTypeLoader().parse(is, ServiceRefType.type, options);
      }

      public static ServiceRefType parse(Reader r) throws XmlException, IOException {
         return (ServiceRefType)XmlBeans.getContextTypeLoader().parse(r, ServiceRefType.type, (XmlOptions)null);
      }

      public static ServiceRefType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ServiceRefType)XmlBeans.getContextTypeLoader().parse(r, ServiceRefType.type, options);
      }

      public static ServiceRefType parse(XMLStreamReader sr) throws XmlException {
         return (ServiceRefType)XmlBeans.getContextTypeLoader().parse(sr, ServiceRefType.type, (XmlOptions)null);
      }

      public static ServiceRefType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ServiceRefType)XmlBeans.getContextTypeLoader().parse(sr, ServiceRefType.type, options);
      }

      public static ServiceRefType parse(Node node) throws XmlException {
         return (ServiceRefType)XmlBeans.getContextTypeLoader().parse(node, ServiceRefType.type, (XmlOptions)null);
      }

      public static ServiceRefType parse(Node node, XmlOptions options) throws XmlException {
         return (ServiceRefType)XmlBeans.getContextTypeLoader().parse(node, ServiceRefType.type, options);
      }

      /** @deprecated */
      public static ServiceRefType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ServiceRefType)XmlBeans.getContextTypeLoader().parse(xis, ServiceRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ServiceRefType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ServiceRefType)XmlBeans.getContextTypeLoader().parse(xis, ServiceRefType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServiceRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServiceRefType.type, options);
      }

      private Factory() {
      }
   }
}
