package com.sun.java.xml.ns.j2Ee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface WarPathType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WarPathType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("warpathtype1dbdtype");

   public static final class Factory {
      public static WarPathType newInstance() {
         return (WarPathType)XmlBeans.getContextTypeLoader().newInstance(WarPathType.type, (XmlOptions)null);
      }

      public static WarPathType newInstance(XmlOptions options) {
         return (WarPathType)XmlBeans.getContextTypeLoader().newInstance(WarPathType.type, options);
      }

      public static WarPathType parse(java.lang.String xmlAsString) throws XmlException {
         return (WarPathType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WarPathType.type, (XmlOptions)null);
      }

      public static WarPathType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WarPathType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WarPathType.type, options);
      }

      public static WarPathType parse(File file) throws XmlException, IOException {
         return (WarPathType)XmlBeans.getContextTypeLoader().parse(file, WarPathType.type, (XmlOptions)null);
      }

      public static WarPathType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WarPathType)XmlBeans.getContextTypeLoader().parse(file, WarPathType.type, options);
      }

      public static WarPathType parse(URL u) throws XmlException, IOException {
         return (WarPathType)XmlBeans.getContextTypeLoader().parse(u, WarPathType.type, (XmlOptions)null);
      }

      public static WarPathType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WarPathType)XmlBeans.getContextTypeLoader().parse(u, WarPathType.type, options);
      }

      public static WarPathType parse(InputStream is) throws XmlException, IOException {
         return (WarPathType)XmlBeans.getContextTypeLoader().parse(is, WarPathType.type, (XmlOptions)null);
      }

      public static WarPathType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WarPathType)XmlBeans.getContextTypeLoader().parse(is, WarPathType.type, options);
      }

      public static WarPathType parse(Reader r) throws XmlException, IOException {
         return (WarPathType)XmlBeans.getContextTypeLoader().parse(r, WarPathType.type, (XmlOptions)null);
      }

      public static WarPathType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WarPathType)XmlBeans.getContextTypeLoader().parse(r, WarPathType.type, options);
      }

      public static WarPathType parse(XMLStreamReader sr) throws XmlException {
         return (WarPathType)XmlBeans.getContextTypeLoader().parse(sr, WarPathType.type, (XmlOptions)null);
      }

      public static WarPathType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WarPathType)XmlBeans.getContextTypeLoader().parse(sr, WarPathType.type, options);
      }

      public static WarPathType parse(Node node) throws XmlException {
         return (WarPathType)XmlBeans.getContextTypeLoader().parse(node, WarPathType.type, (XmlOptions)null);
      }

      public static WarPathType parse(Node node, XmlOptions options) throws XmlException {
         return (WarPathType)XmlBeans.getContextTypeLoader().parse(node, WarPathType.type, options);
      }

      /** @deprecated */
      public static WarPathType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WarPathType)XmlBeans.getContextTypeLoader().parse(xis, WarPathType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WarPathType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WarPathType)XmlBeans.getContextTypeLoader().parse(xis, WarPathType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WarPathType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WarPathType.type, options);
      }

      private Factory() {
      }
   }
}
