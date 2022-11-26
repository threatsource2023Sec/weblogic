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

public interface PortMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PortMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("portmappingtypeccd5type");

   String getPortName();

   void setPortName(String var1);

   String addNewPortName();

   String getJavaPortName();

   void setJavaPortName(String var1);

   String addNewJavaPortName();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static PortMappingType newInstance() {
         return (PortMappingType)XmlBeans.getContextTypeLoader().newInstance(PortMappingType.type, (XmlOptions)null);
      }

      public static PortMappingType newInstance(XmlOptions options) {
         return (PortMappingType)XmlBeans.getContextTypeLoader().newInstance(PortMappingType.type, options);
      }

      public static PortMappingType parse(java.lang.String xmlAsString) throws XmlException {
         return (PortMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PortMappingType.type, (XmlOptions)null);
      }

      public static PortMappingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PortMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PortMappingType.type, options);
      }

      public static PortMappingType parse(File file) throws XmlException, IOException {
         return (PortMappingType)XmlBeans.getContextTypeLoader().parse(file, PortMappingType.type, (XmlOptions)null);
      }

      public static PortMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PortMappingType)XmlBeans.getContextTypeLoader().parse(file, PortMappingType.type, options);
      }

      public static PortMappingType parse(URL u) throws XmlException, IOException {
         return (PortMappingType)XmlBeans.getContextTypeLoader().parse(u, PortMappingType.type, (XmlOptions)null);
      }

      public static PortMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PortMappingType)XmlBeans.getContextTypeLoader().parse(u, PortMappingType.type, options);
      }

      public static PortMappingType parse(InputStream is) throws XmlException, IOException {
         return (PortMappingType)XmlBeans.getContextTypeLoader().parse(is, PortMappingType.type, (XmlOptions)null);
      }

      public static PortMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PortMappingType)XmlBeans.getContextTypeLoader().parse(is, PortMappingType.type, options);
      }

      public static PortMappingType parse(Reader r) throws XmlException, IOException {
         return (PortMappingType)XmlBeans.getContextTypeLoader().parse(r, PortMappingType.type, (XmlOptions)null);
      }

      public static PortMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PortMappingType)XmlBeans.getContextTypeLoader().parse(r, PortMappingType.type, options);
      }

      public static PortMappingType parse(XMLStreamReader sr) throws XmlException {
         return (PortMappingType)XmlBeans.getContextTypeLoader().parse(sr, PortMappingType.type, (XmlOptions)null);
      }

      public static PortMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PortMappingType)XmlBeans.getContextTypeLoader().parse(sr, PortMappingType.type, options);
      }

      public static PortMappingType parse(Node node) throws XmlException {
         return (PortMappingType)XmlBeans.getContextTypeLoader().parse(node, PortMappingType.type, (XmlOptions)null);
      }

      public static PortMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (PortMappingType)XmlBeans.getContextTypeLoader().parse(node, PortMappingType.type, options);
      }

      /** @deprecated */
      public static PortMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PortMappingType)XmlBeans.getContextTypeLoader().parse(xis, PortMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PortMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PortMappingType)XmlBeans.getContextTypeLoader().parse(xis, PortMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PortMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PortMappingType.type, options);
      }

      private Factory() {
      }
   }
}
