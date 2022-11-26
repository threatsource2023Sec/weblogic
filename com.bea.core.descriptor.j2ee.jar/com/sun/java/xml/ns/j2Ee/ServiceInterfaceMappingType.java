package com.sun.java.xml.ns.j2Ee;

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

public interface ServiceInterfaceMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ServiceInterfaceMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("serviceinterfacemappingtype0643type");

   FullyQualifiedClassType getServiceInterface();

   void setServiceInterface(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewServiceInterface();

   XsdQNameType getWsdlServiceName();

   void setWsdlServiceName(XsdQNameType var1);

   XsdQNameType addNewWsdlServiceName();

   PortMappingType[] getPortMappingArray();

   PortMappingType getPortMappingArray(int var1);

   int sizeOfPortMappingArray();

   void setPortMappingArray(PortMappingType[] var1);

   void setPortMappingArray(int var1, PortMappingType var2);

   PortMappingType insertNewPortMapping(int var1);

   PortMappingType addNewPortMapping();

   void removePortMapping(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ServiceInterfaceMappingType newInstance() {
         return (ServiceInterfaceMappingType)XmlBeans.getContextTypeLoader().newInstance(ServiceInterfaceMappingType.type, (XmlOptions)null);
      }

      public static ServiceInterfaceMappingType newInstance(XmlOptions options) {
         return (ServiceInterfaceMappingType)XmlBeans.getContextTypeLoader().newInstance(ServiceInterfaceMappingType.type, options);
      }

      public static ServiceInterfaceMappingType parse(java.lang.String xmlAsString) throws XmlException {
         return (ServiceInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServiceInterfaceMappingType.type, (XmlOptions)null);
      }

      public static ServiceInterfaceMappingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ServiceInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServiceInterfaceMappingType.type, options);
      }

      public static ServiceInterfaceMappingType parse(File file) throws XmlException, IOException {
         return (ServiceInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(file, ServiceInterfaceMappingType.type, (XmlOptions)null);
      }

      public static ServiceInterfaceMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ServiceInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(file, ServiceInterfaceMappingType.type, options);
      }

      public static ServiceInterfaceMappingType parse(URL u) throws XmlException, IOException {
         return (ServiceInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(u, ServiceInterfaceMappingType.type, (XmlOptions)null);
      }

      public static ServiceInterfaceMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ServiceInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(u, ServiceInterfaceMappingType.type, options);
      }

      public static ServiceInterfaceMappingType parse(InputStream is) throws XmlException, IOException {
         return (ServiceInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(is, ServiceInterfaceMappingType.type, (XmlOptions)null);
      }

      public static ServiceInterfaceMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ServiceInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(is, ServiceInterfaceMappingType.type, options);
      }

      public static ServiceInterfaceMappingType parse(Reader r) throws XmlException, IOException {
         return (ServiceInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(r, ServiceInterfaceMappingType.type, (XmlOptions)null);
      }

      public static ServiceInterfaceMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ServiceInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(r, ServiceInterfaceMappingType.type, options);
      }

      public static ServiceInterfaceMappingType parse(XMLStreamReader sr) throws XmlException {
         return (ServiceInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(sr, ServiceInterfaceMappingType.type, (XmlOptions)null);
      }

      public static ServiceInterfaceMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ServiceInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(sr, ServiceInterfaceMappingType.type, options);
      }

      public static ServiceInterfaceMappingType parse(Node node) throws XmlException {
         return (ServiceInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(node, ServiceInterfaceMappingType.type, (XmlOptions)null);
      }

      public static ServiceInterfaceMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (ServiceInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(node, ServiceInterfaceMappingType.type, options);
      }

      /** @deprecated */
      public static ServiceInterfaceMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ServiceInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(xis, ServiceInterfaceMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ServiceInterfaceMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ServiceInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(xis, ServiceInterfaceMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServiceInterfaceMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServiceInterfaceMappingType.type, options);
      }

      private Factory() {
      }
   }
}
