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

public interface ServiceEndpointInterfaceMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ServiceEndpointInterfaceMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("serviceendpointinterfacemappingtypefcadtype");

   FullyQualifiedClassType getServiceEndpointInterface();

   void setServiceEndpointInterface(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewServiceEndpointInterface();

   XsdQNameType getWsdlPortType();

   void setWsdlPortType(XsdQNameType var1);

   XsdQNameType addNewWsdlPortType();

   XsdQNameType getWsdlBinding();

   void setWsdlBinding(XsdQNameType var1);

   XsdQNameType addNewWsdlBinding();

   ServiceEndpointMethodMappingType[] getServiceEndpointMethodMappingArray();

   ServiceEndpointMethodMappingType getServiceEndpointMethodMappingArray(int var1);

   int sizeOfServiceEndpointMethodMappingArray();

   void setServiceEndpointMethodMappingArray(ServiceEndpointMethodMappingType[] var1);

   void setServiceEndpointMethodMappingArray(int var1, ServiceEndpointMethodMappingType var2);

   ServiceEndpointMethodMappingType insertNewServiceEndpointMethodMapping(int var1);

   ServiceEndpointMethodMappingType addNewServiceEndpointMethodMapping();

   void removeServiceEndpointMethodMapping(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ServiceEndpointInterfaceMappingType newInstance() {
         return (ServiceEndpointInterfaceMappingType)XmlBeans.getContextTypeLoader().newInstance(ServiceEndpointInterfaceMappingType.type, (XmlOptions)null);
      }

      public static ServiceEndpointInterfaceMappingType newInstance(XmlOptions options) {
         return (ServiceEndpointInterfaceMappingType)XmlBeans.getContextTypeLoader().newInstance(ServiceEndpointInterfaceMappingType.type, options);
      }

      public static ServiceEndpointInterfaceMappingType parse(java.lang.String xmlAsString) throws XmlException {
         return (ServiceEndpointInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServiceEndpointInterfaceMappingType.type, (XmlOptions)null);
      }

      public static ServiceEndpointInterfaceMappingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ServiceEndpointInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServiceEndpointInterfaceMappingType.type, options);
      }

      public static ServiceEndpointInterfaceMappingType parse(File file) throws XmlException, IOException {
         return (ServiceEndpointInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(file, ServiceEndpointInterfaceMappingType.type, (XmlOptions)null);
      }

      public static ServiceEndpointInterfaceMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ServiceEndpointInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(file, ServiceEndpointInterfaceMappingType.type, options);
      }

      public static ServiceEndpointInterfaceMappingType parse(URL u) throws XmlException, IOException {
         return (ServiceEndpointInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(u, ServiceEndpointInterfaceMappingType.type, (XmlOptions)null);
      }

      public static ServiceEndpointInterfaceMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ServiceEndpointInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(u, ServiceEndpointInterfaceMappingType.type, options);
      }

      public static ServiceEndpointInterfaceMappingType parse(InputStream is) throws XmlException, IOException {
         return (ServiceEndpointInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(is, ServiceEndpointInterfaceMappingType.type, (XmlOptions)null);
      }

      public static ServiceEndpointInterfaceMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ServiceEndpointInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(is, ServiceEndpointInterfaceMappingType.type, options);
      }

      public static ServiceEndpointInterfaceMappingType parse(Reader r) throws XmlException, IOException {
         return (ServiceEndpointInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(r, ServiceEndpointInterfaceMappingType.type, (XmlOptions)null);
      }

      public static ServiceEndpointInterfaceMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ServiceEndpointInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(r, ServiceEndpointInterfaceMappingType.type, options);
      }

      public static ServiceEndpointInterfaceMappingType parse(XMLStreamReader sr) throws XmlException {
         return (ServiceEndpointInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(sr, ServiceEndpointInterfaceMappingType.type, (XmlOptions)null);
      }

      public static ServiceEndpointInterfaceMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ServiceEndpointInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(sr, ServiceEndpointInterfaceMappingType.type, options);
      }

      public static ServiceEndpointInterfaceMappingType parse(Node node) throws XmlException {
         return (ServiceEndpointInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(node, ServiceEndpointInterfaceMappingType.type, (XmlOptions)null);
      }

      public static ServiceEndpointInterfaceMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (ServiceEndpointInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(node, ServiceEndpointInterfaceMappingType.type, options);
      }

      /** @deprecated */
      public static ServiceEndpointInterfaceMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ServiceEndpointInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(xis, ServiceEndpointInterfaceMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ServiceEndpointInterfaceMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ServiceEndpointInterfaceMappingType)XmlBeans.getContextTypeLoader().parse(xis, ServiceEndpointInterfaceMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServiceEndpointInterfaceMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServiceEndpointInterfaceMappingType.type, options);
      }

      private Factory() {
      }
   }
}
